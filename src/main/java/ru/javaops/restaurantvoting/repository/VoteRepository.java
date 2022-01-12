package ru.javaops.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.registered = :registered")
    Optional<Vote> findByUserId(int userId, LocalDate registered);

    @Query("SELECT v FROM Vote v WHERE v.registered = :registered")
    List<Vote> findAllByDate(LocalDate registered);
}