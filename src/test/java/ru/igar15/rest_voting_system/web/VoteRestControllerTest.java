package ru.igar15.rest_voting_system.web;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.rest_voting_system.RestaurantTestData;
import ru.igar15.rest_voting_system.TestUtil;
import ru.igar15.rest_voting_system.UserTestData;
import ru.igar15.rest_voting_system.VoteTestData;
import ru.igar15.rest_voting_system.model.Restaurant;
import ru.igar15.rest_voting_system.model.Vote;
import ru.igar15.rest_voting_system.repository.VoteRepository;
import ru.igar15.rest_voting_system.service.VoteService;
import ru.igar15.rest_voting_system.util.exception.VoteUpdateForbiddenException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.igar15.rest_voting_system.RestaurantTestData.*;
import static ru.igar15.rest_voting_system.UserTestData.*;
import static ru.igar15.rest_voting_system.VoteTestData.*;
import static ru.igar15.rest_voting_system.web.VoteRestController.*;

class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteRepository voteRepository;

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

    }

    Vote findVoteWithRestaurant(int voteId, int userId) {
        Vote vote = voteRepository.find(voteId, userId).get();
        vote.setRestaurant(Hibernate.unproxy(vote.getRestaurant(), Restaurant.class));
        return vote;
    }
}