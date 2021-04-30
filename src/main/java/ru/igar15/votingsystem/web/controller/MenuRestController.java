package ru.igar15.votingsystem.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.igar15.votingsystem.model.Menu;
import ru.igar15.votingsystem.service.MenuService;
import ru.igar15.votingsystem.to.MenuTo;
import ru.igar15.votingsystem.util.MenuUtil;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/restaurants/{restaurantId}/menus/today";

    @Autowired
    private MenuService service;

    @ApiOperation("Get today's menu")
    @GetMapping
    public Menu getToday(@PathVariable int restaurantId) {
        log.info("get today's menu for restaurant {}", restaurantId);
        return service.getToday(restaurantId);
    }

    @ApiOperation(value = "Delete today's menu (only admin)", authorizations = @Authorization(value = "basicAuth"))
    @Secured("ROLE_ADMIN")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToday(@PathVariable int restaurantId) {
        log.info("delete today's menu for restaurant {}", restaurantId);
        service.deleteToday(restaurantId);
    }

    @ApiOperation(value = "Create today's menu (only admin)", authorizations = @Authorization(value = "basicAuth"))
    @Secured("ROLE_ADMIN")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createTodayWithLocation(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("create today's {} for restaurant {}", menuTo, restaurantId);
        Menu created = service.create(MenuUtil.createNewTodayFromTo(menuTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(restaurantId).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @ApiOperation(value = "Update today's menu (only admin)", authorizations = @Authorization(value = "basicAuth"))
    @Secured("ROLE_ADMIN")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateToday(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("update today's {} for restaurant {}", menuTo, restaurantId);
        service.updateToday(menuTo, restaurantId);
    }
}