package ru.javaops.restaurantvoting.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.restaurantvoting.error.IllegalRequestDataException;
import ru.javaops.restaurantvoting.model.Dish;
import ru.javaops.restaurantvoting.repository.DishRepository;

import java.util.List;

@Slf4j
public class AbstractDishController {

    @Autowired
    protected DishRepository dishRepository;

    protected Dish get(int restaurantId, int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return getDish(id, restaurantId);
    }

    protected List<Dish> getAllForRestaurant(int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return dishRepository.getAllForRestaurant(restaurantId);
    }

    protected Dish getDish(int id, int restaurantId) {
        return dishRepository.findById(id, restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("Dish '" + id + " for restaurant '" + restaurantId + "' was not found"));
    }
}
