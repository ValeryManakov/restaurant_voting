package ru.javaops.restaurantvoting.web.vote;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.web.AuthUser;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Profile Vote Controller")
public class ProfileVoteController extends AbstractVoteController {

    static final String REST_URL = "/api/profile/votes";

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveVotePerRestaurant(@RequestParam int restaurantId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime localTime,
                                      @AuthenticationPrincipal AuthUser authUser) {
        super.saveVotePerRestaurant(restaurantId, localTime, authUser);
    }

    @Override
    @GetMapping("/for-today")
    public List<Vote> getForToday() {
        return super.getForToday();
    }
}
