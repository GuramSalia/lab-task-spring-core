package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Optional;

import static java.util.Calendar.*;

@Slf4j
public class Helper {

    public static void setUpTrainee_3(Trainee trainee_3) {
        trainee_3.setFirstName("Dave");
        trainee_3.setLastName("Miller");
        Calendar cal = Calendar.getInstance();
        cal.set(YEAR, 1990);
        cal.set(MONTH, JULY);
        cal.set(DATE, 5);
        trainee_3.setDob(cal.getTime());
        trainee_3.setAddress("123 Main St");
        trainee_3.setIsActive(true);
        trainee_3.setPassword("123456");
        trainee_3.setUsername("Dave.Miller");
    }

    public static void setUpTrainee_4(Trainee trainee_4) {
        trainee_4.setFirstName("Maria");
        trainee_4.setLastName("Li");
        trainee_4.setDobEasy(1980, 0, 25);
        trainee_4.setAddress("55123 Main St");
        trainee_4.setIsActive(true);
    }

    public static void updateTrainee_3Saved(Trainee trainee_3Saved) {

        trainee_3Saved.setFirstName("John");
        trainee_3Saved.setLastName("Doe");
        trainee_3Saved.setPassword("1122");
    }

    public static void createLucy(Trainee trainee) {
        trainee.setFirstName("Lucy");
        trainee.setLastName("Rodriguez");
        trainee.setDobEasy(1980, 0, 25);
        trainee.setAddress("55123 Main St");
        trainee.setIsActive(true);
        trainee.setPassword("111");
    }

    public static void updateOlivia(Trainer olivia) {
        olivia.setFirstName("Olivia");
        olivia.setLastName("Bruno");
        olivia.setPassword("123");
        olivia.setIsActive(true);
    }

    public static void setUpTraining_3(Training training_3, Trainer olivia, Trainee trainee3) {
        training_3.setTrainingDateEasy(2024, 5, 24);
        training_3.setTrainingName("training number 3");
        training_3.setTrainingDurationInMinutes(90);
        training_3.setTrainer(olivia);
        training_3.setTrainee(trainee3);
    }

    public static void setUpTraining_4(Training training_4, Trainer olivia, Trainee trainee_3) {
        training_4.setTrainingDateEasy(2024, 5, 25);
        training_4.setTrainingName("training number 4");
        training_4.setTrainingDurationInMinutes(90);
        training_4.setTrainer(olivia);
        training_4.setTrainee(trainee_3);
    }

    public static void logResultOfTraing4Creation(Optional<Training> optional, Trainer olivia, Training training_4) {
        if (optional.isEmpty()) {
            log.error("????? training not created for same types. trainer type: " + olivia
                    .getSpecialization() +
                              " | " +
                              "training type: " + training_4.getTrainingType());
        } else {
            log.info(">>>>> training created ... trainer type: " + olivia.getSpecialization() + " | " +
                             "training type: " + training_4.getTrainingType());
        }
    }

    public static void logResultOfTraing3Creation(Optional<Training> training3_Optional, Trainer olivia,
                                                  Training training_3) {
        if (training3_Optional.isEmpty()) {
            log.info(">>>>> training not created... trainer type: " + olivia.getSpecialization() + " | training" +
                             "type: " + training_3.getTrainingType());
        } else {
            log.error("----- training created for different types. trainer type: " + olivia
                    .getSpecialization() +
                              " | " + "training type: " + training_3.getTrainingType());
        }
    }
}
