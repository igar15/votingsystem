package ru.igar15.rest_voting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.rest_voting_system.model.Dish;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.menu.id = :menuId")
    Optional<Dish> find(@Param("id") int id, @Param("menuId") int menuId);

    @Query("SELECT d FROM Dish d WHERE d.menu.id = :menuId ORDER BY d.name ASC")
    List<Dish> findAll(@Param("menuId") int menuId);
}