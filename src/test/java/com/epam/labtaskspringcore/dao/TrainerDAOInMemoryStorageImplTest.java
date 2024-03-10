package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.UserService;
import com.epam.labtaskspringcore.utils.Authentication;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainerDAOInMemoryStorageImplTest {
    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TrainerService trainerService;
    TraineeService traineeService;
    Authentication authentication;
    UserService userService;
    UsernameGenerator usernameGenerator;
    Trainer trainer1;
    Trainer trainer2;
    Trainer trainer3;

    @BeforeEach
    void setUpBeforeEach() {

        storage = new InMemoryStorage();
        traineeDAO = new TraineeDAOInMemoryStorageImpl(storage);
        trainerDAO = new TrainerDAOInMemoryStorageImpl(storage);
        usernameGenerator = new UsernameGenerator(trainerDAO, traineeDAO);
        authentication = new Authentication();
        userService = new UserService();

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

        TrainingType YOGA = new TrainingType();
        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);

        trainer1 = new Trainer();
        trainer1.setUserId(1);
        trainer1.setFirstName("Sam");
        trainer1.setLastName("Smith");
        trainer1.setSpecialization(YOGA);
        trainer1.setIsActive(true);
        trainerService.create(trainer1);

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
    void testCreateInTrainerDAO() {
        trainer2.setUserId(1);
        assertEquals(trainerDAO.create(trainer2), Optional.empty());
        trainer2.setUserId(2);
        assertEquals(trainerDAO.create(trainer2), Optional.of(trainer2));
    }

    @Test
    void testUpdateInTrainerDAO() {
        trainer2.setUserId(5);
        assertEquals(trainerDAO.update(trainer2), Optional.empty());
        trainerDAO.create(trainer2);
        trainer2.setIsActive(true);
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