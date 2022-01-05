package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    protected VoteRepository voteRepository;

    protected Restaurant get(int id) {
        log.info("get {}", id);
        return getRestaurant(id);
    }

    protected List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    protected List<Restaurant> getBest() {
        log.info("getBest");
        Map<String, Integer> votesCountPerRestaurant = voteRepository.findAll(Sort.by(Sort.Direction.DESC, "registered"))
                .stream().filter(vote -> LocalDateTime.now().toLocalDate().equals(vote.getRegistered()))
                .collect(Collectors.groupingBy(vote -> vote.getRestaurant().getName(), Collectors.summingInt(vote -> 1)));
        Integer maxCount = votesCountPerRestaurant.values().stream()
                .max(Comparator.comparingInt(count -> count)).orElse(null);
        return votesCountPerRestaurant.keySet().stream()
                .filter(name -> votesCountPerRestaurant.get(name).equals(maxCount))
                .map(name -> restaurantRepository.getByName(name)).collect(Collectors.toList());
    }

    protected Restaurant getRestaurant(int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalRequestDataException("Restaurant '" + id + "' was not found"));
    }
}
