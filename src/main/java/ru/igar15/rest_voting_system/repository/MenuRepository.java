package ru.igar15.rest_voting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.rest_voting_system.model.Menu;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Optional<Menu> findByIdAndRestaurant_Id(int id, int restaurantId);

    List<Menu> findAllByRestaurant_Id(int restaurantId);
}
