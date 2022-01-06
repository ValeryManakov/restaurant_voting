package ru.javaops.restaurantvoting.web.dish;

import ru.javaops.restaurantvoting.model.Dish;
import ru.javaops.restaurantvoting.web.MatcherFactory;

import static ru.javaops.restaurantvoting.web.restaurant.RestaurantTestData.restaurant1;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;
    public static final int NOT_FOUND = 100;

    public static final Dish dish1 = new Dish(DISH1_ID, "Chicken noodles", 150.13);
    public static final Dish dish2 = new Dish(DISH2_ID, "Gyro", 112.50);
    public static final Dish dish3 = new Dish(DISH3_ID, "Meatballs", 171.25);
    public static final Dish dish4 = new Dish(DISH4_ID, "Onion soup", 143.18);

    static {
        dish1.setRestaurant(restaurant1);
        dish2.setRestaurant(restaurant1);
        dish3.setRestaurant(restaurant1);
        dish4.setRestaurant(restaurant1);
    }

    public static Dish getNew() {
        return new Dish(null, "New dish", 300.25);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Updated dish", 211.17);
    }
}
