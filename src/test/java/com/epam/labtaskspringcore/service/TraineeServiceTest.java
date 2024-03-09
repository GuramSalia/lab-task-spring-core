package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TraineeDAOInMemoryStorageImpl;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainerDAOInMemoryStorageImpl;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.utils.Authentication;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Disabled
class TraineeServiceTest {

    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TrainerService trainerService;
    TraineeService traineeService;
    @Mock
    Authentication authentication;

    @Mock
    private UserService userService;
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
        traineeService = new TraineeService(traineeDAOMap, authentication, userService, usernameGenerator);
        traineeService.setTraineeDAO(traineeDAO);

        // new way of creating trainerService
        Map<String, TrainerDAO> trainerDAOMap = new HashMap<>();
        trainerDAOMap.put("TRAINER_IN_MEMORY", trainerDAO);
        trainerService = new TrainerService(trainerDAOMap, traineeDAOMap, authentication, userService,
                                            usernameGenerator);
        trainerService.setTrainerDAO(trainerDAO);

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
    @Disabled
    void testCreateInTraineeService() {

        trainee2.setUserId(8);
        trainee2.setFirstName("Sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);

        log.info("<<<<<>>>>>>>>>>");
        log.info(storage.getTrainees().get(8).getFirstName().toString());

        assertAll(
                () -> assertEquals(trainee2,
                                   storage.getTrainees().get(8),
                                   "created trainee should be in trainees list"),
                () -> assertEquals("Sam.Smith1",
                                   storage.getTrainees().get(8).getUsername(),
                                   "username should be properly generated")
                 );
    }

    @Test
    @Disabled
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
    @Disabled
    void testGetByIdInTraineeService() {
        trainee2.setFirstName("Sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        Optional<Trainee> trainee = traineeService.getById(trainee2.getUserId(), "Sam.Smith", "123");
        assertAll(
                () -> assertEquals(trainee1, traineeDAO.getById(1).get(), "trainee should be " +
                        "returned"),
                () -> assertEquals(trainee2, traineeDAO.getById(2).get(), "trainee should be returned")
                 );
    }

    @Test
    @Disabled
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