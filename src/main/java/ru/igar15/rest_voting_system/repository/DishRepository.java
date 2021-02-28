package ru.igar15.rest_voting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.rest_voting_system.model.Dish;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    Optional<Dish> findByIdAndMenu_Id(int id, int menuId);

    List<Dish> findAllByMenu_IdOrderByNameAsc(int menuId);
}