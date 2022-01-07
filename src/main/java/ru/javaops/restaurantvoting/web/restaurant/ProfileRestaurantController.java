package ru.javaops.restaurantvoting.web.restaurant;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.restaurantvoting.model.Restaurant;

import java.util.List;

@RestController
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@Tag(name = "Profile Restaurant Controller")
public class ProfileRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/profile/restaurants";

    @Override
    @GetMapping("/{id}")
    @Cacheable(key = "#id")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    @Cacheable(key = "'allRestaurants'")
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/best-for-today")
    @Cacheable(key = "'bestRestaurants'")
    public List<Restaurant> getBest() {
        return super.getBest();
    }
}
