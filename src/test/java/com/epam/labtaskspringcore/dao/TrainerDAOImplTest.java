package com.epam.labtaskspringcore.dao;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import com.epam.labtaskspringcore.utils.UsernameGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class TrainerDAOImplTest {
    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TrainerService trainerService;
    TraineeService traineeService;
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

        trainer1 = new Trainer();
        trainer1.setId(1);
        trainer1.setFirstName("Sam");
        trainer1.setLastName("Smith");
        trainer1.setSpecialization(TrainingType.YOGA);
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
    void testCreateInTrainerDAO() {
        trainer2.setId(1);
        assertEquals(trainerDAO.create(trainer2), Optional.empty());
        trainer2.setId(2);
        assertEquals(trainerDAO.create(trainer2), Optional.of(trainer2));
    }

    @Test
    void testUpdateInTrainerDAO() {
        trainer2.setId(5);
        assertEquals(trainerDAO.update(trainer2), Optional.empty());
        trainerDAO.create(trainer2);
        trainer2.setActive(true);
        assertEquals(trainerDAO.update(trainer2), Optional.of(trainer2));
    }

    @Test
    void testGetByIdInTrainerDAO() {
        assertEquals(trainerDAO.getById(1), Optional.of(trainer1));
    }

    @Test
    void testGetTrainersInTrainerDAO() {
        trainerDAO.create(trainer1);
        trainerDAO.create(trainer2);
        trainerDAO.create(trainer3);
        assertEquals(trainerDAO.getTrainers(), List.of(trainer1, trainer2, trainer3));
    }
}