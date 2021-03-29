package ru.igar15.rest_voting_system;

import ru.igar15.rest_voting_system.model.Vote;

import java.time.*;

import static ru.igar15.rest_voting_system.RestaurantTestData.restaurant1;
import static ru.igar15.rest_voting_system.RestaurantTestData.restaurant2;
import static ru.igar15.rest_voting_system.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user");

    public static final int VOTE1_ID = START_SEQ + 8;

    public static final LocalTime BEFORE_ELEVEN = LocalTime.of(10, 25);
    public static final LocalTime AFTER_ELEVEN = LocalTime.of(11, 5);

    public static Vote getNew() {
        Vote vote = new Vote(LocalDate.now());
        vote.setRestaurant(restaurant1);
        return vote;
    }

    public static Vote getUpdated() {
        Vote vote = new Vote(VOTE1_ID, LocalDate.of(2021, Month.FEBRUARY, 25));
        vote.setRestaurant(restaurant2);
        return vote;
    }

    public static class MockClock extends Clock {
        private LocalDateTime currentDateTime = LocalDateTime.now();

        @Override
        public ZoneId getZone() {
            return ZoneId.of("UTC");
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return currentDateTime.toInstant(ZoneOffset.UTC);
        }

        public void setCurrentDateTime(LocalDateTime currentDateTime) {
            this.currentDateTime = currentDateTime;
        }
    }
}