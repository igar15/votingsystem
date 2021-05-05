package ru.igar15.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.votingsystem.model.Restaurant;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findAllByOrderByNameAscAddressAsc();

    List<Restaurant> findAllByNameContainingIgnoreCaseOrderByNameAscAddressAsc(String name);
}