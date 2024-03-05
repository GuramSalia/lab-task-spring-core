package com.epam.labtaskspringcore.service;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TraineeDAOImpl;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainerDAOImpl;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import com.epam.labtaskspringcore.utils.UsernameGeneratorImpl;
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
        usernameGenerator = new UsernameGeneratorImpl(trainerDAO, traineeDAO);
        traineeService = new TraineeService(traineeDAO, usernameGenerator);
        trainerService = new TrainerService(trainerDAO, usernameGenerator);


        TrainingType YOGA = new TrainingType();
        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);

        trainer1 = new Trainer();
        trainer1.setId(1);
        trainer1.setFirstName("Sam");
        trainer1.setLastName("Smith");
        trainer1.setSpecialization(YOGA);
        trainer1.setActive(true);
        trainerService.create(trainer1);

        trainer2 = new Trainer();
        trainer2.setId(2);
        trainer3 = new Trainer();
        trainer3.setId(3);
    }

    @AfterEach
    void tearDownAfterEach() {
        storage.clearStorage();
    }

    @Test
    void testCreateInTrainerService() {
        trainer2.setFirstName("Sam");
        trainer2.setLastName("Smith");
        trainerService.create(trainer2);
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
        trainerService.create(trainer2);
        trainer2.setFirstName("Sam");
        trainerService.update(trainer2);
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
        trainerService.create(trainer2);
        Optional<Trainer> trainer = trainerService.getById(trainer2.getId());
        assertAll(
                () -> assertEquals(trainer1, trainerService.getById(1).get(), "trainer should be returned"),
                () -> assertEquals(trainer2, trainerService.getById(2).get(), "trainer should be returned")
                 );
    }

    @Test
    void TestGetTrainersInTrainerService() {
        trainer2.setFirstName("Sam");
        trainer2.setLastName("Smith");
        trainerService.create(trainer2);
        trainer3.setFirstName("Sammy");
        trainer3.setLastName("Smith");
        trainerService.create(trainer3);
        assertEquals(3, trainerService.getTrainers().size(), "trainers should be returned");
    }
}