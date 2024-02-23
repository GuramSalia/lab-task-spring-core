package com.epam.labtaskspringcore.dao;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
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
class TraineeDAOImplTest {
    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TrainerService trainerService;
    TraineeService traineeService;
    UsernameGenerator usernameGenerator;
    Trainee trainee1;
    Trainee trainee2;
    Trainee trainee3;

    @BeforeEach
    void setUpBeforeEach() {
        storage = new InMemoryStorage();
        traineeDAO = new TraineeDAOImpl(storage);
        trainerDAO = new TrainerDAOImpl(storage);
        usernameGenerator = new UsernameGeneratorImpl(trainerDAO, traineeDAO);
        traineeService = new TraineeService(traineeDAO, usernameGenerator);
        trainerService = new TrainerService(trainerDAO, usernameGenerator);

        trainee1 = new Trainee();
        trainee1.setId(1);
        trainee1.setFirstName("Sam");
        trainee1.setLastName("Smith");
        trainee1.setAddress("trainee1 address");
        trainee1.setActive(true);
        traineeService.create(trainee1);

        trainee2 = new Trainee();
        trainee2.setId(2);
        trainee3 = new Trainee();
        trainee3.setId(3);
    }

    @AfterEach
    void tearDownAfterEach() {
        storage.clearStorage();
    }


    @Test
    void testCreateInTraineeDAO() {
        trainee2.setId(1);
        assertEquals(traineeDAO.create(trainee2), Optional.empty());
        trainee2.setId(2);
        assertEquals(traineeDAO.create(trainee2), Optional.of(trainee2));
    }

    @Test
    void testUpdateInTraineeDAO() {
        trainee2.setId(5);
        assertEquals(traineeDAO.update(trainee2), Optional.empty());
        traineeDAO.create(trainee2);
        trainee2.setActive(true);
        assertEquals(traineeDAO.update(trainee2), Optional.of(trainee2));
    }

    @Test
    void testDeleteInTraineeDAO() {
        assertTrue(traineeDAO.delete(1));
    }

    @Test
    void testGetByIdInTraineeDAO() {
        assertEquals(traineeDAO.getById(1), Optional.of(trainee1));
    }

    @Test
    void testGetInTraineesDAO() {
        traineeDAO.create(trainee1);
        traineeDAO.create(trainee2);
        traineeDAO.create(trainee3);
        assertEquals(traineeDAO.getTrainees(), List.of(trainee1, trainee2, trainee3));
    }
}