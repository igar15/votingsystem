package ru.igar15.votingsystem.web;

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

import java.net.URI;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/restaurants";

    @Autowired
    private MenuService service;

    @GetMapping("/{restaurantId}/menus/today")
    public Menu getToday(@PathVariable int restaurantId) {
        log.info("get today menu for restaurant {}", restaurantId);
        return service.getToday(restaurantId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{restaurantId}/menus/today")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToday(@PathVariable int restaurantId) {
        log.info("delete today menu for restaurant {}", restaurantId);
        service.deleteToday(restaurantId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/{restaurantId}/menus/today", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createTodayWithLocation(@RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("create today {} for restaurant {}", menuTo, restaurantId);
        Menu created = service.create(MenuUtil.createNewFromTo(menuTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + restaurantId + "/menus/today")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{restaurantId}/menus/today", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateToday(@RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        log.info("update today {} for restaurant {}", menuTo, restaurantId);
        service.updateToday(menuTo, restaurantId);
    }
}