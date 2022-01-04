package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.web.AuthUser;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.topjava2.util.TimeUtil.isTimeCorrect;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminVoteController {

    static final String REST_URL = "/api/admin/votes";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll");
        return voteRepository.findAll(Sort.by(Sort.Direction.DESC, "registered"));
    }

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

    @GetMapping("/current")
    public List<Vote> getForCurrentDate() {
        log.info("getCurrent");
        return voteRepository.findAll(Sort.by(Sort.Direction.DESC, "registered"))
                .stream().filter(vote -> LocalDateTime.now().toLocalDate().equals(vote.getRegistered()))
                .collect(Collectors.toList());
    }

    private Restaurant getRestaurant(int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalRequestDataException("Restaurant '" + id + "' was not found"));
    }
}
