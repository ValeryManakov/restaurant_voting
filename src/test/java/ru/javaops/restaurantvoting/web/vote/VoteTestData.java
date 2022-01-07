package ru.javaops.restaurantvoting.web.vote;

import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.web.MatcherFactory;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingCollectionSizeComparator(Vote.class);

    public static final int INITIAL_VOTES_COUNT = 10;
    public static final int INITIAL_TODAY_VOTES_COUNT = 8;
    public static final String TIME_BEFORE_DEADLINE = "10:55";
    public static final String TIME_AFTER_DEADLINE = "11:05";
}
