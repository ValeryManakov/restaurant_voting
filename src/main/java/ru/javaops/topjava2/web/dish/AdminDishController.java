package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.javaops.topjava2.util.DishUtil.checkRestaurantId;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminDishController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return getDish(id, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        getDish(id, restaurantId);
        dishRepository.deleteExisted(id);
    }

    @GetMapping
    public List<Dish> getAllForRestaurant(@PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return dishRepository.getAllForRestaurant(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        log.info("create {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        checkRestaurantId(dish, restaurantId);
        dish.setRestaurant(getRestaurant(restaurantId));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update {} with id={} for restaurant {}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        checkRestaurantId(dish, restaurantId);
        dish.setRestaurant(getRestaurant(restaurantId));
        dishRepository.save(dish);
    }

    private Dish getDish(int id, int restaurantId) {
        return dishRepository.findById(id, restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("Dish '" + id + " for restaurant '" + restaurantId + "' was not found"));
    }

    private Restaurant getRestaurant(int restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new IllegalRequestDataException("Restaurant '" + restaurantId + "' was not found"));
    }
}