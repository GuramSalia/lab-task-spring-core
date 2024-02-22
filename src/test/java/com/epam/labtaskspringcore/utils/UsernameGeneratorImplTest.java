package com.epam.labtaskspringcore.utils;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TraineeDAOImpl;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainerDAOImpl;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("When generating username with generateUsername method")
class UsernameGeneratorImplTest {
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

    @BeforeAll
    static void setUp() {

        log.error(">>>> test setup");
    }

    @BeforeEach
    void setUpBeforeEach() {
        storage = new InMemoryStorage();
        traineeDAO = new TraineeDAOImpl(storage);
        trainerDAO = new TrainerDAOImpl(storage);
        usernameGenerator = new UsernameGeneratorImpl(trainerDAO, traineeDAO);
        traineeService = new TraineeService(traineeDAO, usernameGenerator);
        trainerService = new TrainerService(trainerDAO, usernameGenerator);

        Trainee trainee1 = new Trainee();
        trainee1.setTraineeId(1);
        trainee1.setFirstName("Sam");
        trainee1.setLastName("Smith");
        trainee1.setAddress("trainee1 address");
        trainee1.setActive(true);
        traineeService.create(trainee1);
        //        log.info("created trainee: " + trainee1);

        trainee2 = new Trainee();
        trainee2.setTraineeId(2);
        trainee3 = new Trainee();
        trainee3.setTraineeId(3);

        Trainer trainer1 = new Trainer();
        trainer1.setTrainerId(1);
        trainer1.setFirstName("John");
        trainer1.setLastName("Doe");
        trainer1.setSpecialization(TrainingType.YOGA);
        trainer1.setActive(true);
        trainerService.create(trainer1);
        //        log.info("created trainer: " + trainer1);

        trainer2 = new Trainer();
        trainer2.setTrainerId(2);
        trainer3 = new Trainer();
        trainer3.setTrainerId(3);
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

    }
    @Disabled
    @Test
    @DisplayName("when first.last name repeats in trainers")
    void testTraineeUsernameRepeatsInTrainers() {
        trainee2.setFirstName("John");
        trainee2.setLastName("Doe");
        traineeService.create(trainee2);
        trainee2.setAddress("some address");
        traineeService.update(trainee2);
        log.info(trainee2.getUsername());

        assertEquals("John.Doe1",
                     trainee2.getUsername(),
                     "username should be added int incrementally until it is unique in trainers");
    }

    @Nested
    @DisplayName("for trainer")
    class TestUsernameGeneratorForTrainer {
        @Test
        @DisplayName("when first.last name is unique in trainees and trainers")
        void testTrainerUniqueName() {

        }

        @Test
        @DisplayName("when first.last name repeats in trainees")
        void testTrainerUsernameRepeatsInTrainees() {

        }

        @Test
        @DisplayName("when first.last name repeats in trainers")
        void testTrainerUsernameRepeatsInTrainers() {

        }
    }
}