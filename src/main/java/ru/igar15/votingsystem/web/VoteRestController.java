package ru.igar15.votingsystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.igar15.votingsystem.model.Vote;
import ru.igar15.votingsystem.service.VoteService;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

import static ru.igar15.votingsystem.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/votes";

    @Autowired
    private VoteService service;

    @Autowired
    private Clock clock;

    @PostMapping
    public ResponseEntity<Vote> registerVote(@RequestParam int restaurantId) {
        int userId = authUserId();
        log.info("register vote from {} for restaurant {}", userId, restaurantId);
        Vote registered = service.registerVote(userId, restaurantId, LocalDate.now(clock), LocalTime.now(clock));
        return ResponseEntity.ok().body(registered);
    }
}