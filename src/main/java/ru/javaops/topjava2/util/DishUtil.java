package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.HasId;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;

import java.util.Optional;

@UtilityClass
public class DishUtil {

    public static void checkRestaurantId(Dish dish, int restaurantId) {
        Restaurant restaurant = dish.getRestaurant();
        if (restaurant != null && restaurant.getId() != restaurantId)
            throw new IllegalRequestDataException("Dish must have restaurant_id=" + restaurantId);
    }
}