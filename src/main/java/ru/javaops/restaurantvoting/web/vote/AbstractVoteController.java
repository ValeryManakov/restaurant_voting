package ru.javaops.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.restaurantvoting.error.IllegalRequestDataException;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.repository.VoteRepository;
import ru.javaops.restaurantvoting.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javaops.restaurantvoting.util.TimeUtil.TODAY;
import static ru.javaops.restaurantvoting.util.TimeUtil.isTimeCorrect;

@Slf4j
public class AbstractVoteController {

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    protected RestaurantRepository restaurantRepository;

    protected void saveVotePerRestaurant(int restaurantId, LocalTime localTime, AuthUser authUser) {
        log.info("save vote for restaurant {}", restaurantId);
        Vote vote = voteRepository.findByUserId(authUser.id(), TODAY).orElse(null);
        if (vote == null) {
            vote = new Vote();
        } else if (!isTimeCorrect(localTime)) {
            throw new IllegalRequestDataException("You've already voted, you can't change your vote after 11:00");
        }
        vote.setRegistered(TODAY);
        vote.setUser(authUser.getUser());
        vote.setRestaurant(getRestaurant(restaurantId));
        voteRepository.save(vote);
    }

    protected List<Vote> getForToday() {
        log.info("get for today");
        return voteRepository.findAllByDate(TODAY);
    }

    protected Restaurant getRestaurant(int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalRequestDataException("Restaurant '" + id + "' was not found"));
    }
}
