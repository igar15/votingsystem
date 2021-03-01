package ru.igar15.rest_voting_system;

import ru.igar15.rest_voting_system.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator("user");

    public static final LocalTime BEFORE_ELEVEN = LocalTime.of(10, 25);
    public static final LocalTime AFTER_ELEVEN = LocalTime.of(11, 5);

    public static Vote getNew() {
        return new Vote(LocalDate.now());
    }

    public static Vote getUpdated() {
        Vote vote = new Vote(LocalDate.now());
        vote.setRestaurant(RestaurantTestData.restaurant2);
        return vote;
    }
}