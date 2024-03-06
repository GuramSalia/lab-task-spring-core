package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TraineeDAOInMemoryStorageImpl;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainerDAOInMemoryStorageImpl;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TraineeServiceTest {

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
        traineeDAO = new TraineeDAOInMemoryStorageImpl(storage);
        trainerDAO = new TrainerDAOInMemoryStorageImpl(storage);
        usernameGenerator = new UsernameGenerator(trainerDAO, traineeDAO);

        // new way of creating traineeService
        Map<String, TraineeDAO> traineeDAOMap = new HashMap<>();
        traineeDAOMap.put("IN_MEMORY", traineeDAO);
        traineeService = new TraineeService(traineeDAOMap, usernameGenerator);
        traineeService.setTraineeDAO(traineeDAO);

        trainerService = new TrainerService(trainerDAO, usernameGenerator);

        trainee1 = new Trainee();
        trainee1.setUserId(1);
        trainee1.setFirstName("Sam");
        trainee1.setLastName("Smith");
        trainee1.setAddress("trainee1 address");
        trainee1.setIsActive(true);
        traineeService.create(trainee1);

        trainee2 = new Trainee();
        trainee2.setUserId(2);
        trainee3 = new Trainee();
        trainee3.setUserId(3);
    }

    @AfterEach
    void tearDownAfterEach() {
        storage.clearStorage();
    }

    @Test
    void testCreateInTraineeService() {
        trainee2.setFirstName("Sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        assertAll(
                () -> assertEquals(trainee2,
                                   storage.getTrainees().get(2),
                                   "created trainee should be in trainees list"),
                () -> assertEquals("Sam.Smith1",
                                   storage.getTrainees().get(2).getUsername(),
                                   "username should be properly generated")
                 );
    }

    @Test
    void testUpdateInTraineeService() {
        trainee2.setFirstName("Sammy");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        trainee2.setFirstName("Sam");
        traineeService.update(trainee2);
        assertAll(
                () -> assertEquals(trainee2,
                                   storage.getTrainees().get(2),
                                   "updated trainee should be in trainees list"),
                () -> assertEquals("Sam.Smith1",
                                   storage.getTrainees().get(2).getUsername(),
                                   "username should be properly generated")
                 );
    }

    @Test
    void testDeleteInTraineeService() {
        trainee2.setFirstName("sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        traineeService.delete(trainee2);
        traineeService.delete(trainee1);

        assertAll(
                () -> assertNull(storage.getTrainees().get(2), "deleted trainee should not be in trainees list"),
                () -> assertNull(storage.getTrainees().get(1), "deleted trainee should not be in trainees list")
                 );
    }

    @Test
    void testGetByIdInTraineeService() {
        trainee2.setFirstName("Sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        Optional<Trainee> trainee = traineeService.getById(trainee2.getUserId());
        assertAll(
                () -> assertEquals(trainee1, traineeService.getById(1).get(), "trainee should be returned"),
                () -> assertEquals(trainee2, traineeService.getById(2).get(), "trainee should be returned")
                 );
    }

    @Test
    void testGetInTraineesService() {
        trainee2.setFirstName("Sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        trainee3.setFirstName("Sammy");
        trainee3.setLastName("Smith");
        traineeService.create(trainee3);
        assertEquals(3, traineeService.getTrainees().size(), "trainees should be returned");
    }
}