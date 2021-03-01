package ru.igar15.rest_voting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.rest_voting_system.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByIdAndUser_Id(int id, int userId);

    Optional<Vote> findByDateAndUser_Id(LocalDate date, int userId);
}
