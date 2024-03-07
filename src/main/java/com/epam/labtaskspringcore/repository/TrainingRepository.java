package com.epam.labtaskspringcore.repository;

import com.epam.labtaskspringcore.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Integer> {

    // should I use in param Date from util or from sql
    @Query(value = "SELECT TRAINING_ID FROM TRAININGS t WHERE t.TRAINEE_ID = (SELECT tr.USER_ID FROM TRAINEES tr JOIN" +
            " GYM_USERS u ON tr.USER_ID = u.USER_ID WHERE u.USERNAME = ?1) AND t.TRAINING_DATE BETWEEN " +
            "?2 AND ?3 AND t.TRAINER_ID = (SELECT gu.USER_ID FROM GYM_USERS gu WHERE gu.USERNAME " +
            "= ?4) AND t.TRAINING_TYPE_ID = (SELECT tt.TRAINING_TYPE_ID FROM TRAINING_TYPES tt WHERE tt" +
            ".TRAINING_TYPE_NAME = ?5)", nativeQuery = true)
    List<Integer> findIdsByTraineeAndTrainerAndType(
            @Param("traineeUsername") String traineeUsername,
            @Param("startDate") Date startDate,
            @Param("endDate") Date  endDate,
            @Param("trainerUsername") String trainerUsername,
            @Param("trainingTypeName") String trainingTypeName);
}

