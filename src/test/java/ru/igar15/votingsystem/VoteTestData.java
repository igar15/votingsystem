package ru.igar15.votingsystem;

import ru.igar15.votingsystem.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static ru.igar15.votingsystem.RestaurantTestData.restaurant1;
import static ru.igar15.votingsystem.RestaurantTestData.restaurant2;
import static ru.igar15.votingsystem.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user");

    public static final int VOTE1_ID = START_SEQ + 9;

    public static final LocalDate VOTE_TEST_DATE = LocalDate.of(2021, Month.FEBRUARY, 25);
    public static final LocalTime BEFORE_ELEVEN = LocalTime.of(10, 25);
    public static final LocalTime AFTER_ELEVEN = LocalTime.of(11, 5);

    public static Vote getNewWithRestaurant() {
        Vote vote = new Vote(null, LocalDate.now());
        vote.setRestaurant(restaurant1);
        return vote;
    }

    public static Vote getUpdated() {
        Vote vote = new Vote(VOTE1_ID, VOTE_TEST_DATE);
        vote.setRestaurant(restaurant2);
        return vote;
    }
}