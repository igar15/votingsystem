package ru.igar15.votingsystem.web;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.igar15.votingsystem.TestUtil;
import ru.igar15.votingsystem.VoteTestData;
import ru.igar15.votingsystem.config.WebConfig;
import ru.igar15.votingsystem.model.Restaurant;
import ru.igar15.votingsystem.model.Vote;
import ru.igar15.votingsystem.repository.VoteRepository;
import ru.igar15.votingsystem.util.exception.VoteUpdateForbiddenException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT1_ID;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT2_ID;
import static ru.igar15.votingsystem.UserTestData.USER_ID;
import static ru.igar15.votingsystem.VoteTestData.*;
import static ru.igar15.votingsystem.util.ValidationUtil.getRootCause;
import static ru.igar15.votingsystem.web.VoteRestController.REST_URL;

class VoteRestControllerTest extends AbstractControllerTest {

    @Configuration
    @Import(WebConfig.class)
    static class ClockConfig {
        @Bean
        public Clock clock() {
            return new VoteTestData.MockClock();
        }
    }

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private Clock clock;

    @Test
    void create() throws Exception{
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT1_ID)))
                .andExpect(status().isOk());

        Vote created = TestUtil.readFromJson(action, Vote.class);
        int newId = created.id();
        Vote newVote = VoteTestData.getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(findVoteWithRestaurant(newId, USER_ID), newVote);
    }

    @Test
    void update() throws Exception {
        ((MockClock) clock).setCurrentDateTime(LocalDateTime.of(2021, Month.FEBRUARY, 25, 10, 15));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT2_ID)))
                .andExpect(status().isOk());
        VOTE_MATCHER.assertMatch(findVoteWithRestaurant(VOTE1_ID, USER_ID), VoteTestData.getUpdated());
    }

    @Test
    void updateFailed() throws Exception {
        ((MockClock) clock).setCurrentDateTime(LocalDateTime.of(2021, Month.FEBRUARY, 25, 11, 15));
        assertThrows(VoteUpdateForbiddenException.class, () -> {
            try {
                perform(MockMvcRequestBuilders.post(REST_URL)
                        .param("restaurantId", String.valueOf(RESTAURANT2_ID)));
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }

    private Vote findVoteWithRestaurant(int voteId, int userId) {
        Vote vote = voteRepository.find(voteId, userId).get();
        vote.setRestaurant(Hibernate.unproxy(vote.getRestaurant(), Restaurant.class));
        return vote;
    }
}