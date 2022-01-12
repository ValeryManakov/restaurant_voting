package ru.javaops.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.restaurant.id = :restaurantId")
    Optional<Dish> findById(int id, int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.registered=:registered ORDER BY d.name ASC")
    List<Dish> getAllForRestaurantByDate(int restaurantId, LocalDate registered);

    @Query("SELECT d FROM Dish d WHERE d.registered=:registered ORDER BY d.restaurant.id ASC")
    List<Dish> getAllByDate(LocalDate registered);

    @Query("SELECT d FROM Dish d ORDER BY d.registered DESC, d.restaurant.id ASC")
    List<Dish> getAll();
}
