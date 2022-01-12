package ru.javaops.restaurantvoting.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.model.Dish;
import ru.javaops.restaurantvoting.repository.DishRepository;
import ru.javaops.restaurantvoting.util.JsonUtil;
import ru.javaops.restaurantvoting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.restaurantvoting.util.TimeUtil.TODAY;
import static ru.javaops.restaurantvoting.web.dish.DishTestData.*;
import static ru.javaops.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.restaurantvoting.web.restaurant.RestaurantTestData.restaurant1;
import static ru.javaops.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.restaurantvoting.web.user.UserTestData.USER_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL + "/";

    @Autowired
    private DishRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/dishes/" + DISH1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/dishes/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/dishes/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/dishes/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForRestaurantByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/dishes")
                .param("registered", TODAY.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(fisrtRestaurantDishesForToday));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "dishes/by-date")
                .param("registered", TODAY.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(allDishesForToday));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "dishes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(allDishes));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID + "/dishes/" + DISH1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(DISH1_ID, RESTAURANT1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID + "/dishes/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID + "/dishes/" + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        updated.setId(DISH1_ID);
        updated.setRestaurant(restaurant1);
        DISH_MATCHER.assertMatch(repository.getById(DISH1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(DISH1_ID, "Invalid dish", TODAY, 10001.11);
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID + "/dishes/" + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        Dish updated = new Dish(DISH1_ID, dish2.getName(), TODAY, 150.17);
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID + "/dishes/" + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID + "/dishes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        newDish.setRestaurant(restaurant1);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getById(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, "Invalid dish", TODAY, 10001.11);
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID + "/dishes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Dish expected = new Dish(null, dish2.getName(), TODAY,150.17);
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID +"/dishes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
