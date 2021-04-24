package ru.igar15.votingsystem.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.igar15.votingsystem.AuthorizedUser;
import ru.igar15.votingsystem.model.Vote;
import ru.igar15.votingsystem.service.VoteService;
import springfox.documentation.annotations.ApiIgnore;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

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
    public ResponseEntity<Vote> registerVote(@RequestParam int restaurantId, @AuthenticationPrincipal @ApiIgnore AuthorizedUser authUser) {
        log.info("register today's vote from user {} for restaurant {}", authUser.getId(), restaurantId);
        Vote registered = service.registerVote(authUser.getId(), restaurantId, LocalDate.now(clock), LocalTime.now(clock));
        return ResponseEntity.ok().body(registered);
    }
}