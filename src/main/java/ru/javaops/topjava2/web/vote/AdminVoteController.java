package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminVoteController extends AbstractVoteController {

    static final String REST_URL = "/api/admin/votes";

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll");
        return voteRepository.findAll(Sort.by(Sort.Direction.DESC, "registered"));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveVotePerRestaurant(@RequestParam int restaurantId,
                                      @AuthenticationPrincipal AuthUser authUser) {
        super.saveVotePerRestaurant(restaurantId, authUser);
    }

    @Override
    @GetMapping("/for-today")
    public List<Vote> getForToday() {
        return super.getForToday();
    }
}
