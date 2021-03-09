package ru.igar15.rest_voting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.rest_voting_system.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m WHERE m.id = :id AND m.restaurant.id = :restaurantId")
    Optional<Menu> find(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId ORDER BY m.date DESC")
    List<Menu> findAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId AND m.date = :date")
    Optional<Menu> findByDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);
}