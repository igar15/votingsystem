package ru.igar15.votingsystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.igar15.votingsystem.model.Menu;
import ru.igar15.votingsystem.service.MenuService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.igar15.votingsystem.util.ValidationUtil.assureIdConsistent;
import static ru.igar15.votingsystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/restaurants";

    @Autowired
    private MenuService service;

    @Secured("ROLE_ADMIN")
    @GetMapping("/{restaurantId}/menus")
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return service.getAll(restaurantId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{restaurantId}/menus/{menuId}")
    public Menu get(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("get menu {} for restaurant {}", menuId, restaurantId);
        return service.get(menuId, restaurantId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{restaurantId}/menus/by")
    public Menu getByDate(@PathVariable int restaurantId,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getByDate {} for restaurant {}", date, restaurantId);
        return service.getByDate(restaurantId, date);
    }

    @GetMapping("/{restaurantId}/menus/today")
    public Menu getToday(@PathVariable int restaurantId) {
        log.info("getToday for restaurant {}", restaurantId);
        return service.getToday(restaurantId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{restaurantId}/menus/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("delete menu {} for restaurant {}", menuId, restaurantId);
        service.delete(menuId, restaurantId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("create {} for restaurant {}", menu, restaurantId);
        checkNew(menu);
        Menu created = service.create(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + restaurantId + "/menus" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/{restaurantId}/menus/today", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createTodayWithLocation(@RequestBody Menu menu, @PathVariable int restaurantId) {
        log.info("create today {} for restaurant {}", menu, restaurantId);
        checkNew(menu);
        menu.setDate(LocalDate.now());
        Menu created = service.create(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + restaurantId + "/menus/today")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/{restaurantId}/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("update {} for restaurant {}", menu, restaurantId);
        assureIdConsistent(menu, menuId);
        service.update(menu, restaurantId);
    }
}