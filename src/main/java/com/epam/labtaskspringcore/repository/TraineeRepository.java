package com.epam.labtaskspringcore.repository;

import com.epam.labtaskspringcore.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
    Optional<Trainee> findByUsername(String username);

    Optional<Trainee> findByUsernameAndPassword(String username, String password);
}
