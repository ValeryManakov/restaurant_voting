package ru.javaops.restaurantvoting.web.restaurant;

import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final String RESTAURANT1_NAME = "Farfor";
    public static final String RESTAURANT2_NAME = "Geronimo";
    public static final String RESTAURANT3_NAME = "Spinasse";

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, RESTAURANT1_NAME);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, RESTAURANT2_NAME);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, RESTAURANT3_NAME);

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated");
    }
}
