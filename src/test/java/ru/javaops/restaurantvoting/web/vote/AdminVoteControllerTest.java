package ru.javaops.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.repository.VoteRepository;
import ru.javaops.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.restaurantvoting.web.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.restaurantvoting.web.user.UserTestData.USER_MAIL;
import static ru.javaops.restaurantvoting.web.vote.VoteTestData.*;

class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminVoteController.REST_URL;

    @Autowired
    private VoteRepository repository;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(new Vote[INITIAL_VOTES_COUNT]));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/for-today"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(new Vote[INITIAL_TODAY_VOTES_COUNT]));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createBeforeDeadLine() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "1")
                .param("localTime",TIME_BEFORE_DEADLINE))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(repository.findAll(), new Vote[INITIAL_VOTES_COUNT + 1]);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createAfterDeadLine() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "1")
                .param("localTime",TIME_AFTER_DEADLINE))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(repository.findAll(), new Vote[INITIAL_VOTES_COUNT + 1]);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateBeforeDeadLine() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "1")
                .param("localTime",TIME_BEFORE_DEADLINE))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(repository.findAll(), new Vote[INITIAL_VOTES_COUNT + 1]);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2")
                .param("localTime",TIME_BEFORE_DEADLINE))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(repository.findAll(), new Vote[INITIAL_VOTES_COUNT + 1]);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateAfterDeadLine() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "1")
                .param("localTime",TIME_BEFORE_DEADLINE))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(repository.findAll(), new Vote[INITIAL_VOTES_COUNT + 1]);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2")
                .param("localTime",TIME_AFTER_DEADLINE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        VOTE_MATCHER.assertMatch(repository.findAll(), new Vote[INITIAL_VOTES_COUNT + 1]);
    }
}
