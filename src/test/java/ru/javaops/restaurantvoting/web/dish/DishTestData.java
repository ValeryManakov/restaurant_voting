package ru.javaops.restaurantvoting.web.dish;

import ru.javaops.restaurantvoting.model.Dish;
import ru.javaops.restaurantvoting.web.MatcherFactory;

import java.util.Arrays;
import java.util.List;

import static ru.javaops.restaurantvoting.util.TimeUtil.TODAY;
import static ru.javaops.restaurantvoting.web.restaurant.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;

    public static final int DISH5_ID = 5;
    public static final int DISH6_ID = 6;
    public static final int DISH7_ID = 7;
    public static final int DISH8_ID = 8;
    public static final int DISH9_ID = 9;
    public static final int DISH10_ID = 10;
    public static final int DISH11_ID = 11;
    public static final int DISH12_ID = 12;
    public static final int DISH13_ID = 13;
    public static final int DISH14_ID = 14;
    public static final int DISH15_ID = 15;

    public static final int NOT_FOUND = 100;

    public static final Dish dish1 = new Dish(DISH1_ID, "Dish01", TODAY, 100.00);
    public static final Dish dish2 = new Dish(DISH2_ID, "Dish02", TODAY, 100.50);
    public static final Dish dish3 = new Dish(DISH3_ID, "Dish03", TODAY, 101.00);
    public static final Dish dish4 = new Dish(DISH4_ID, "Dish04", TODAY, 101.50);

    public static final Dish dish5 = new Dish(DISH5_ID, "Dish05", TODAY, 102.00);
    public static final Dish dish6 = new Dish(DISH6_ID, "Dish06", TODAY, 102.50);
    public static final Dish dish7 = new Dish(DISH7_ID, "Dish07", TODAY, 103.00);
    public static final Dish dish8 = new Dish(DISH8_ID, "Dish08", TODAY, 103.50);
    public static final Dish dish9 = new Dish(DISH9_ID, "Dish09", TODAY, 104.00);
    public static final Dish dish10 = new Dish(DISH10_ID, "Dish10", TODAY, 104.50);
    public static final Dish dish11 = new Dish(DISH11_ID, "Dish11", TODAY, 105.00);
    public static final Dish dish12 = new Dish(DISH12_ID, "Dish12", TODAY, 105.50);
    public static final Dish dish13 = new Dish(DISH13_ID, "Dish13", TODAY.minusDays(1), 106.00);
    public static final Dish dish14 = new Dish(DISH14_ID, "Dish14", TODAY.minusDays(1), 106.50);
    public static final Dish dish15 = new Dish(DISH15_ID, "Dish15", TODAY.minusDays(1), 107.00);

    static {
        dish1.setRestaurant(restaurant1);
        dish2.setRestaurant(restaurant1);
        dish3.setRestaurant(restaurant1);
        dish4.setRestaurant(restaurant1);

        dish5.setRestaurant(restaurant2);
        dish6.setRestaurant(restaurant2);
        dish7.setRestaurant(restaurant2);
        dish8.setRestaurant(restaurant2);
        dish9.setRestaurant(restaurant3);
        dish10.setRestaurant(restaurant3);
        dish11.setRestaurant(restaurant3);
        dish12.setRestaurant(restaurant3);
        dish13.setRestaurant(restaurant1);
        dish14.setRestaurant(restaurant2);
        dish15.setRestaurant(restaurant3);
    }

    public static List<Dish> fisrtRestaurantDishesForToday = Arrays.asList(dish1, dish2, dish3, dish4);
    public static List<Dish> allDishesForToday = Arrays.asList(dish1, dish2, dish3, dish4, dish5, dish6, dish7, dish8, dish9, dish10, dish11, dish12);
    public static List<Dish> allDishes = Arrays.asList(dish1, dish2, dish3, dish4, dish5, dish6, dish7, dish8, dish9, dish10, dish11, dish12, dish13, dish14, dish15);

    public static Dish getNew() {
        return new Dish(null, "New dish", TODAY, 300.25);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Updated dish", TODAY, 211.17);
    }
}
