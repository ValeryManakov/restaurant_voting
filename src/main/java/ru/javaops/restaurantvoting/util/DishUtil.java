package ru.javaops.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.javaops.restaurantvoting.error.IllegalRequestDataException;
import ru.javaops.restaurantvoting.model.Dish;
import ru.javaops.restaurantvoting.model.Restaurant;

@UtilityClass
public class DishUtil {

    public static void checkRestaurantId(Dish dish, int restaurantId) {
        Restaurant restaurant = dish.getRestaurant();
        if (restaurant != null && restaurant.getId() != null && restaurant.getId() != restaurantId)
            throw new IllegalRequestDataException("Dish must have restaurant_id=" + restaurantId);
    }
}