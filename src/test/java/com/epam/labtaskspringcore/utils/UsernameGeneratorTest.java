package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TraineeDAOInMemoryStorageImpl;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainerDAOInMemoryStorageImpl;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("When generating username with generateUsername method")
class UsernameGeneratorTest {
    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TrainerService trainerService;
    TraineeService traineeService;
    UsernameGenerator usernameGenerator;
    Trainee trainee2;
    Trainee trainee3;
    Trainer trainer2;
    Trainer trainer3;

    @BeforeEach
    void setUpBeforeEach() {
        storage = new InMemoryStorage();
        traineeDAO = new TraineeDAOInMemoryStorageImpl(storage);
        trainerDAO = new TrainerDAOInMemoryStorageImpl(storage);
        usernameGenerator = new UsernameGenerator(trainerDAO, traineeDAO);

        // new way of creating traineeService
        Map<String, TraineeDAO> traineeDAOMap = new HashMap<>();
        traineeDAOMap.put("IN_MEMORY", traineeDAO);
        traineeService = new TraineeService(traineeDAOMap, usernameGenerator);
        traineeService.setTraineeDAO(traineeDAO);

        trainerService = new TrainerService(trainerDAO, usernameGenerator);

        TrainingType YOGA = new TrainingType();
        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);

        Trainee trainee1 = new Trainee();
        trainee1.setUserId(1);
        trainee1.setFirstName("Sam");
        trainee1.setLastName("Smith");
        trainee1.setAddress("trainee1 address");
        trainee1.setIsActive(true);
        traineeService.create(trainee1);
        //        log.info("created trainee: " + trainee1);

        trainee2 = new Trainee();
        trainee2.setUserId(2);
        trainee3 = new Trainee();
        trainee3.setUserId(3);

        Trainer trainer1 = new Trainer();
        trainer1.setUserId(1);
        trainer1.setFirstName("John");
        trainer1.setLastName("Doe");
        trainer1.setSpecialization(YOGA);
        trainer1.setIsActive(true);
        trainerService.create(trainer1);
        //        log.info("created trainer: " + trainer1);

        trainer2 = new Trainer();
        trainer2.setUserId(2);
        trainer3 = new Trainer();
        trainer3.setUserId(3);
    }

    @AfterEach
    void tearDownAfterEach() {
        storage.clearStorage();
    }

    @Nested
    @DisplayName("for trainee")
    class TestUsernameGeneratorForTrainee {
        @Test
        @DisplayName("when first.last name is unique in trainees and trainers")
        void testTraineeUniqueName() {
            trainee2.setFirstName("George");
            trainee2.setLastName("Fisher");
            traineeService.create(trainee2);

            assertEquals("George.Fisher",
                         trainee2.getUsername(),
                         "username should be of format 'firstName.lastName'");
        }

        @Test
        @DisplayName("when first.last name repeats in trainees")
        void testTraineeUsernameRepeatsInTrainees() {
            trainee2.setFirstName("Sam");
            trainee2.setLastName("Smith");
            traineeService.create(trainee2);

            assertEquals("Sam.Smith1",
                         trainee2.getUsername(),
                         "username should be added int incrementally until it is unique in trainees");
        }

        @Test
        @DisplayName("when first.last name repeats in trainers")
        void testTraineeUsernameRepeatsInTrainers() {
            trainee2.setFirstName("John");
            trainee2.setLastName("Doe");
            traineeService.create(trainee2);
            trainee2.setAddress("some address");
            traineeService.update(trainee2);

            assertEquals("John.Doe1",
                         trainee2.getUsername(),
                         "username should be added int incrementally until it is unique");
        }
    }

    @Nested
    @DisplayName("for trainer")
    class TestUsernameGeneratorForTrainer {
        @Test
        @DisplayName("when first.last name is unique in trainees and trainers")
        void testTrainerUniqueName() {
            trainer3.setFirstName("George");
            trainer3.setLastName("Fisher");
            trainerService.create(trainer3);
            trainer3.setIsActive(true);
            trainerService.update(trainer3);

            assertEquals("George.Fisher",
                         trainer3.getUsername(),
                         "username should be of format 'firstName.lastName'");
        }

        @Test
        @DisplayName("when first.last name repeats in trainees")
        void testTrainerUsernameRepeatsInTrainees() {
            trainer3.setFirstName("Sam");
            trainer3.setLastName("Smith");
            trainerService.create(trainer3);
            trainer3.setIsActive(true);
            trainerService.update(trainer3);

            assertEquals("Sam.Smith1",
                         trainer3.getUsername(),
                         "username should be added int incrementally until it is unique");
        }

        @Test
        @DisplayName("when first.last name repeats in trainers")
        void testTrainerUsernameRepeatsInTrainers() {
            trainer3.setFirstName("John");
            trainer3.setLastName("Doe");
            trainerService.create(trainer3);
            trainer3.setIsActive(true);
            trainerService.update(trainer3);

            assertEquals("John.Doe1",
                         trainer3.getUsername(),
                         "username should be added int incrementally until it is unique");
        }
    }
}