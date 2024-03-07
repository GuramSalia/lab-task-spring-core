package com.epam.labtaskspringcore.repository;

import com.epam.labtaskspringcore.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
    Optional<Trainee> findByUsername(String username);

    Optional<Trainee> findByUsernameAndPassword(String username, String password);

    //    @Query("SELECT tr FROM Trainee tr WHERE tr.user.username = :username")
    //    @Query(value = "SELECT * FROM trainee tr INNER JOIN user u ON tr.user_id = u.id WHERE u.username = :username",
    //            nativeQuery = true)

    //    @Query("SELECT tr FROM Trainee tr JOIN FETCH tr.user u WHERE u.username = :username")
    @Query(value = "SELECT * FROM TRAINEES tr JOIN GYM_USERS u ON tr.USER_ID = u.USER_ID WHERE u.USERNAME = ?1",
            nativeQuery = true)
    Optional<Trainee> findByUsernameWithQuery(String username);
}
