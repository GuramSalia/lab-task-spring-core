package com.epam.labtaskspringcore.service;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TraineeDAOImpl;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainerDAOImpl;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class TrainerServiceTest {
    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TraineeService traineeService;
    TrainerService trainerService;
    UsernameGenerator usernameGenerator;
    Trainer trainer1;
    Trainer trainer2;
    Trainer trainer3;

    @BeforeEach
    void setUpBeforeEach() {
        storage = new InMemoryStorage();
        traineeDAO = new TraineeDAOImpl(storage);
        trainerDAO = new TrainerDAOImpl(storage);
        usernameGenerator = new UsernameGenerator(trainerDAO, traineeDAO);
        traineeService = new TraineeService(traineeDAO, usernameGenerator);
        trainerService = new TrainerService(trainerDAO, usernameGenerator);


        TrainingType YOGA = new TrainingType();
        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);

        trainer1 = new Trainer();
        trainer1.setUserId(1);
        trainer1.setFirstName("Sam");
        trainer1.setLastName("Smith");
        trainer1.setSpecialization(YOGA);
        trainer1.setIsActive(true);
        trainerService.createWithDao(trainer1);

        trainer2 = new Trainer();
        trainer2.setUserId(2);
        trainer3 = new Trainer();
        trainer3.setUserId(3);
    }

    @AfterEach
    void tearDownAfterEach() {
        storage.clearStorage();
    }

    @Test
    void testCreateInTrainerService() {
        trainer2.setFirstName("Sam");
        trainer2.setLastName("Smith");
        trainerService.createWithDao(trainer2);
        assertAll(
                () -> assertEquals(trainer2,
                                   storage.getTrainers().get(2),
                                   "created trainer should be in trainers list"),
                () -> assertEquals("Sam.Smith1",
                                   storage.getTrainers().get(2).getUsername(),
                                   "username should be properly generated")
                 );
    }

    @Test
    void testUpdateInTrainerService() {
        trainer2.setFirstName("Sammy");
        trainer2.setLastName("Smith");
        trainerService.createWithDao(trainer2);
        trainer2.setFirstName("Sam");
        trainerService.updateWithDao(trainer2);
        assertAll(
                () -> assertEquals(trainer2,
                                   storage.getTrainers().get(2),
                                   "updated trainer should be in trainers list"),
                () -> assertEquals("Sam.Smith1",
                                   storage.getTrainers().get(2).getUsername(),
                                   "username should be properly generated")
                 );
    }

    @Test
    void TestGetByIdInTrainerService() {
        trainer2.setFirstName("Sam");
        trainer2.setLastName("Smith");
        trainerService.createWithDao(trainer2);
        Optional<Trainer> trainer = trainerService.getByIdWithDao(trainer2.getUserId());
        assertAll(
                () -> assertEquals(trainer1, trainerService.getByIdWithDao(1).get(), "trainer should be returned"),
                () -> assertEquals(trainer2, trainerService.getByIdWithDao(2).get(), "trainer should be returned")
                 );
    }

    @Test
    void TestGetTrainersInTrainerService() {
        trainer2.setFirstName("Sam");
        trainer2.setLastName("Smith");
        trainerService.createWithDao(trainer2);
        trainer3.setFirstName("Sammy");
        trainer3.setLastName("Smith");
        trainerService.createWithDao(trainer3);
        assertEquals(3, trainerService.getTrainersWithDao().size(), "trainers should be returned");
    }
}