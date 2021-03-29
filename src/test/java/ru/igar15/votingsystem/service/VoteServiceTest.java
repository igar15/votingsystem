package ru.igar15.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.igar15.votingsystem.model.Vote;
import ru.igar15.votingsystem.repository.VoteRepository;
import ru.igar15.votingsystem.util.exception.VoteUpdateForbiddenException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT1_ID;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT2_ID;
import static ru.igar15.votingsystem.UserTestData.USER_ID;
import static ru.igar15.votingsystem.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Autowired
    private VoteRepository repository;

    @Test
    public void create() {
        Vote created = service.registerVote(USER_ID, RESTAURANT1_ID, LocalDate.now(), LocalTime.now());
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(repository.find(newId, USER_ID).get(), newVote);
    }

    @Test
    public void update() {
        service.registerVote(USER_ID, RESTAURANT2_ID, LocalDate.of(2021, Month.FEBRUARY, 25), BEFORE_ELEVEN);
        VOTE_MATCHER.assertMatch(repository.find(VOTE1_ID, USER_ID).get(), getUpdated());
    }

    @Test
    public void updateFailed() {
        assertThrows(VoteUpdateForbiddenException.class,
                () -> service.registerVote(USER_ID, RESTAURANT2_ID, LocalDate.of(2021, Month.FEBRUARY, 25), AFTER_ELEVEN));
    }
}