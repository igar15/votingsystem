package ru.igar15.votingsystem.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.igar15.votingsystem.AuthorizedUser;
import ru.igar15.votingsystem.model.User;
import ru.igar15.votingsystem.service.UserService;
import ru.igar15.votingsystem.to.UserTo;
import ru.igar15.votingsystem.util.UserUtil;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;

import static ru.igar15.votingsystem.util.ValidationUtil.assureIdConsistent;
import static ru.igar15.votingsystem.util.ValidationUtil.checkNew;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/profile";

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation(value = "Get user profile data", authorizations = @Authorization(value = "basicAuth"))
    @GetMapping
    public User get(@AuthenticationPrincipal @ApiIgnore AuthorizedUser authUser) {
        log.info("get {}", authUser.getId());
        return service.get(authUser.getId());
    }

    @ApiOperation(value = "Delete user profile", authorizations = @Authorization(value = "basicAuth"))
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal @ApiIgnore AuthorizedUser authUser) {
        log.info("delete {}", authUser.getId());
        service.delete(authUser.getId());
    }

    @ApiOperation(value = "Register new user")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("create {}", userTo);
        checkNew(userTo);
        User created = service.create(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @ApiOperation(value = "Update user profile data", authorizations = @Authorization(value = "basicAuth"))
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal @ApiIgnore AuthorizedUser authUser) {
        log.info("update {} with id={}", userTo, authUser.getId());
        assureIdConsistent(userTo, authUser.getId());
        service.update(userTo);
    }

    @ApiOperation(value = "Login user")
    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        log.info("login user {}", email);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email.toLowerCase(), password));
        return service.getByEmail(email);
    }
}