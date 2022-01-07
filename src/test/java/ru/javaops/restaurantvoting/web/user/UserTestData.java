package ru.javaops.restaurantvoting.web.user;

import ru.javaops.restaurantvoting.model.Role;
import ru.javaops.restaurantvoting.model.User;
import ru.javaops.restaurantvoting.util.JsonUtil;
import ru.javaops.restaurantvoting.web.MatcherFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int USER3_ID = 3;
    public static final int USER4_ID = 4;
    public static final int USER5_ID = 5;
    public static final int USER6_ID = 6;
    public static final int USER7_ID = 7;
    public static final int USER8_ID = 8;
    public static final int USER9_ID = 9;
    public static final int USER10_ID = 10;
    public static final int USER11_ID = 11;
    public static final int USER12_ID = 12;

    public static final int NOT_FOUND = 100;

    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String USER3_MAIL = "user3@yandex.ru";
    public static final String USER4_MAIL = "user4@yandex.ru";
    public static final String USER5_MAIL = "user5@yandex.ru";
    public static final String USER6_MAIL = "user6@yandex.ru";
    public static final String USER7_MAIL = "user7@yandex.ru";
    public static final String USER8_MAIL = "user8@yandex.ru";
    public static final String USER9_MAIL = "user9@yandex.ru";
    public static final String USER10_MAIL = "user10@yandex.ru";
    public static final String USER11_MAIL = "user11@yandex.ru";
    public static final String USER12_MAIL = "user12@yandex.ru";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User user3 = new User(USER3_ID, "User3", USER3_MAIL, "password3", Role.USER);
    public static final User user4 = new User(USER4_ID, "User4", USER4_MAIL, "password4", Role.USER);
    public static final User user5 = new User(USER5_ID, "User5", USER5_MAIL, "password5", Role.USER);
    public static final User user6 = new User(USER6_ID, "User6", USER6_MAIL, "password6", Role.USER);
    public static final User user7 = new User(USER7_ID, "User7", USER7_MAIL, "password7", Role.USER);
    public static final User user8 = new User(USER8_ID, "User8", USER8_MAIL, "password8", Role.USER);
    public static final User user9 = new User(USER9_ID, "User9", USER9_MAIL, "password9", Role.USER);
    public static final User user10 = new User(USER10_ID, "User10", USER10_MAIL, "password10", Role.USER);
    public static final User user11 = new User(USER11_ID, "User11", USER11_MAIL, "password11", Role.USER);
    public static final User user12 = new User(USER12_ID, "User12", USER12_MAIL, "password12", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
