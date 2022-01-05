package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;

import java.util.List;

@RestController
@RequestMapping(value = ProfileDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileDishController {

    static final String REST_URL = "/api/profile/restaurants/{restaurantId}/dishes";

    @Autowired
    private DishRepository dishRepository;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return getDish(id, restaurantId);
    }

    @GetMapping
    public List<Dish> getAllForRestaurant(@PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return dishRepository.getAllForRestaurant(restaurantId);
    }

    private Dish getDish(int id, int restaurantId) {
        return dishRepository.findById(id, restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("Dish '" + id + " for restaurant '" + restaurantId + "' was not found"));
    }
}
