package ru.igar15.rest_voting_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.igar15.rest_voting_system.model.Vote;
import ru.igar15.rest_voting_system.repository.VoteRepository;
import ru.igar15.rest_voting_system.util.exception.VoteUpdateForbiddenException;

import java.time.LocalTime;

import static org.junit.Assert.assertThrows;
import static ru.igar15.rest_voting_system.RestaurantTestData.*;
import static ru.igar15.rest_voting_system.UserTestData.USER1_ID;
import static ru.igar15.rest_voting_system.VoteTestData.getNew;
import static ru.igar15.rest_voting_system.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Autowired
    private VoteRepository repository;

    @Test
    public void registerNewVote() {
        Vote created = service.registerVote(RESTAURANT1_ID, USER1_ID, LocalTime.now());
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        newVote.setRestaurant(restaurant1);
        VOTE_MATCHER.assertMatch(repository.findByIdAndUser_Id(newId, USER1_ID).get(), newVote);
    }

    @Test
    public void updateVote() {
        Vote created = service.registerVote(RESTAURANT1_ID, USER1_ID, BEFORE_ELEVEN);
        int newId = created.id();
        service.registerVote(RESTAURANT2_ID, USER1_ID, BEFORE_ELEVEN);
        Vote expected = getNew();
        expected.setId(newId);
        expected.setRestaurant(restaurant2);
        VOTE_MATCHER.assertMatch(repository.findByIdAndUser_Id(newId, USER1_ID).get(), expected);
    }

    @Test
    public void updateVoteTooLate() {
        service.registerVote(RESTAURANT1_ID, USER1_ID, AFTER_ELEVEN);
        assertThrows(VoteUpdateForbiddenException.class, () -> service.registerVote(RESTAURANT2_ID, USER1_ID, AFTER_ELEVEN));
    }
}