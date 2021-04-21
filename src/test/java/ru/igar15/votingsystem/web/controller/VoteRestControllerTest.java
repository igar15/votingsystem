package ru.igar15.votingsystem.web.controller;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.igar15.votingsystem.VoteTestData;
import ru.igar15.votingsystem.model.Restaurant;
import ru.igar15.votingsystem.model.Vote;
import ru.igar15.votingsystem.repository.VoteRepository;
import ru.igar15.votingsystem.util.exception.VoteUpdateForbiddenException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT1_ID;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT2_ID;
import static ru.igar15.votingsystem.TestUtil.readFromJson;
import static ru.igar15.votingsystem.TestUtil.userHttpBasic;
import static ru.igar15.votingsystem.UserTestData.USER_ID;
import static ru.igar15.votingsystem.UserTestData.user;
import static ru.igar15.votingsystem.VoteTestData.*;
import static ru.igar15.votingsystem.util.ValidationUtil.getRootCause;
import static ru.igar15.votingsystem.web.controller.VoteRestController.REST_URL;

@ExtendWith(MockitoExtension.class)
class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private VoteRestController controller;

    @Autowired
    private VoteRepository voteRepository;

    @Mock
    private Clock clock;

    @Test
    void create() throws Exception{
        setupClock(clock, LocalDateTime.now());

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user))
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andExpect(status().isOk());

        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        Vote newVote = getNewWithRestaurant();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(findVoteWithRestaurant(newId, USER_ID), newVote);
    }

    @Test
    void createUnAuth() throws Exception{
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update() throws Exception {
        setupClock(clock, LocalDateTime.of(VOTE_TEST_DATE, BEFORE_ELEVEN));

        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user))
                .param("restaurantId", String.valueOf(RESTAURANT2_ID)))
                .andExpect(status().isOk());
        VOTE_MATCHER.assertMatch(findVoteWithRestaurant(VOTE1_ID, USER_ID), getUpdated());
    }

    @Test
    void updateFailed() throws Exception {
        setupClock(clock, LocalDateTime.of(VOTE_TEST_DATE, AFTER_ELEVEN));

        assertThrows(VoteUpdateForbiddenException.class, () -> {
            try {
                perform(MockMvcRequestBuilders.post(REST_URL)
                        .with(userHttpBasic(user))
                        .param("restaurantId", String.valueOf(RESTAURANT2_ID)));
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }

    private void setupClock(Clock clock, LocalDateTime dateTime) {
        Mockito.when(clock.getZone()).thenReturn(ZoneId.of("UTC"));
        Mockito.when(clock.instant()).thenReturn(dateTime.toInstant(ZoneOffset.UTC));
    }

    private Vote findVoteWithRestaurant(int voteId, int userId) {
        Vote vote = voteRepository.find(voteId, userId);
        // The transaction is not closed yet, so it needs to be manually unproxy
        vote.setRestaurant(Hibernate.unproxy(vote.getRestaurant(), Restaurant.class));
        return vote;
    }
}