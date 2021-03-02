package ru.igar15.rest_voting_system.service;

import org.springframework.stereotype.Service;
import ru.igar15.rest_voting_system.model.Restaurant;
import ru.igar15.rest_voting_system.model.User;
import ru.igar15.rest_voting_system.model.Vote;
import ru.igar15.rest_voting_system.repository.RestaurantRepository;
import ru.igar15.rest_voting_system.repository.UserRepository;
import ru.igar15.rest_voting_system.repository.VoteRepository;
import ru.igar15.rest_voting_system.util.exception.VoteUpdateForbiddenException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Vote registerVote(int restaurantId, int userId, LocalDate date, LocalTime time) {
        Optional<Vote> voteOptional = voteRepository.findByDateAndUser_Id(date, userId);
        return voteOptional.map(vote -> update(vote, restaurantId, time)).orElseGet(() -> create(restaurantId, userId, date));
    }

    private Vote create(int restaurantId, int userId, LocalDate date) {
        Vote vote = new Vote(date);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        User user = userRepository.getOne(userId);
        vote.setRestaurant(restaurant);
        vote.setUser(user);
        return voteRepository.save(vote);
    }

    private Vote update(Vote vote, int restaurantId, LocalTime time) {
        if (isUpdateForbidden(time)) {
            throw new VoteUpdateForbiddenException("It is too late to change your vote");
        }
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        vote.setRestaurant(restaurant);
        return voteRepository.save(vote);
    }

    private boolean isUpdateForbidden(LocalTime time) {
        return time.isAfter(LocalTime.of(11, 0));
    }
}