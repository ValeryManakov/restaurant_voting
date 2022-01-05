package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.web.AuthUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.topjava2.util.TimeUtil.isTimeCorrect;

@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileVoteController {

    static final String REST_URL = "/api/profile/votes";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestParam int restaurantId,
                     @AuthenticationPrincipal AuthUser authUser) {
        log.info("save vote for restaurant {}", restaurantId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        Vote vote = voteRepository.findByUserId(authUser.id(), currentDateTime.toLocalDate()).orElse(null);
        if (vote == null) {
            vote = new Vote();
        } else if (!isTimeCorrect(currentDateTime.toLocalTime())) {
            throw new IllegalRequestDataException("It is after 11:00");
        }
        vote.setRegistered(currentDateTime.toLocalDate());
        vote.setUser(authUser.getUser());
        vote.setRestaurant(getRestaurant(restaurantId));
        voteRepository.save(vote);
    }

    @GetMapping("/for-today")
    public List<Vote> getForToday() {
        log.info("get for today");
        return voteRepository.findAll(Sort.by(Sort.Direction.DESC, "registered"))
                .stream().filter(vote -> LocalDateTime.now().toLocalDate().equals(vote.getRegistered()))
                .collect(Collectors.toList());
    }

    private Restaurant getRestaurant(int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalRequestDataException("Restaurant '" + id + "' was not found"));
    }
}
