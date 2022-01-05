package ru.javaops.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.javaops.restaurantvoting.error.IllegalRequestDataException;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.repository.VoteRepository;
import ru.javaops.restaurantvoting.web.AuthUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.restaurantvoting.util.TimeUtil.isTimeCorrect;

@Slf4j
public class AbstractVoteController {

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    protected RestaurantRepository restaurantRepository;

    protected void saveVotePerRestaurant(int restaurantId, AuthUser authUser) {
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

    protected List<Vote> getForToday() {
        log.info("get for today");
        return voteRepository.findAll(Sort.by(Sort.Direction.DESC, "registered"))
                .stream().filter(vote -> LocalDateTime.now().toLocalDate().equals(vote.getRegistered()))
                .collect(Collectors.toList());
    }

    protected Restaurant getRestaurant(int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalRequestDataException("Restaurant '" + id + "' was not found"));
    }
}
