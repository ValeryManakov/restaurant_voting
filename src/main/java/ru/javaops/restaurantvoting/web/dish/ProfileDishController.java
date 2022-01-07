package ru.javaops.restaurantvoting.web.dish;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.restaurantvoting.model.Dish;

import java.util.List;

@RestController
@RequestMapping(value = ProfileDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "dishes")
@Tag(name = "Profile Dish Controller")
public class ProfileDishController extends AbstractDishController {

    static final String REST_URL = "/api/profile/restaurants/{restaurantId}/dishes";

    @Override
    @GetMapping("/{id}")
    @Cacheable(key = "#id")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }

    @Override
    @GetMapping
    @Cacheable(key = "'restaurant' + #restaurantId")
    public List<Dish> getAllForRestaurant(@PathVariable int restaurantId) {
        return super.getAllForRestaurant(restaurantId);
    }
}
