package ru.javaops.restaurantvoting.web.dish;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.restaurantvoting.error.IllegalRequestDataException;
import ru.javaops.restaurantvoting.model.Dish;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.restaurantvoting.util.DishUtil.checkRestaurantId;
import static ru.javaops.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "dishes")
@Tag(name = "Admin Dish Controller")
public class AdminDishController extends AbstractDishController {

    static final String REST_URL = "/api/admin/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    @GetMapping("/{restaurantId}/dishes/{id}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }

    @DeleteMapping("/{restaurantId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        getDish(id, restaurantId);
        dishRepository.deleteExisted(id);
    }

    @Override
    @GetMapping("/{restaurantId}/dishes")
    public List<Dish> getAllForRestaurantByDate(@PathVariable int restaurantId,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registered) {
        return super.getAllForRestaurantByDate(restaurantId, registered);
    }

    @GetMapping("/dishes/by-date")
    public List<Dish> getAllByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registered) {
        return dishRepository.getAllByDate(registered);
    }

    @GetMapping("/dishes")
    public List<Dish> getAll() {
        return dishRepository.getAll();
    }

    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
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

    @PutMapping(value = "/{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update {} with id={} for restaurant {}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        checkRestaurantId(dish, restaurantId);
        dish.setRestaurant(getRestaurant(restaurantId));
        dishRepository.save(dish);
    }

    private Restaurant getRestaurant(int restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new IllegalRequestDataException("Restaurant '" + restaurantId + "' was not found"));
    }
}