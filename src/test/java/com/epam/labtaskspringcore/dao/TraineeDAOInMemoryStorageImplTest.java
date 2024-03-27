package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.UserValidatorService;
import com.epam.labtaskspringcore.utils.Authentication;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ExtendWith(MockitoExtension.class)
        //@Disabled
class TraineeDAOInMemoryStorageImplTest {

    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TrainerService trainerService;
    TraineeService traineeService;
    Authentication authentication;
    UserValidatorService userValidatorService;
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
        authentication = new Authentication(new BCryptPasswordEncoder());
        userValidatorService = new UserValidatorService();

        // new way of creating traineeService
        Map<String, TraineeDAO> traineeDAOMap = new HashMap<>();
        traineeDAOMap.put("TRAINEE_IN_MEMORY", traineeDAO);
        traineeService = new TraineeService(traineeDAO, authentication, userValidatorService, usernameGenerator, new BCryptPasswordEncoder());
//        traineeService.setTraineeDAO(traineeDAO);

        // new way of creating trainerService
        Map<String, TrainerDAO> trainerDAOMap = new HashMap<>();
        trainerDAOMap.put("TRAINER_IN_MEMORY", trainerDAO);

        trainerService = new TrainerService(trainerDAO, traineeDAO, authentication, userValidatorService,
                                            usernameGenerator);
//        trainerService.setTrainerDAO(trainerDAO);

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
    void testCreateInTraineeDAO() {
        trainee1.setUserId(7);
        assertEquals(traineeDAO.create(trainee1), Optional.of(trainee1));

    }

//    @Test
//    void testUpdateInTraineeDAO() {
//        trainee2.setUserId(5);
//        assertEquals(traineeDAO.update(trainee2), Optional.empty());
//        traineeDAO.create(trainee2);
//        trainee2.setIsActive(true);
//        assertEquals(traineeDAO.update(trainee2), Optional.of(trainee2));
//    }

    @Test
    void testDeleteInTraineeDAO() {
        assertTrue(traineeDAO.delete(trainee1));
    }

//    @Test
//    void testGetByIdInTraineeDAO() {
//        assertEquals(traineeDAO.getById(1), Optional.of(trainee1));
//    }
//
//    @Test
//    void testGetInTraineesDAO() {
//        traineeDAO.create(trainee1);
//        traineeDAO.create(trainee2);
//        traineeDAO.create(trainee3);
//        assertEquals(traineeDAO.getTrainees(), List.of(trainee1, trainee2, trainee3));
//    }
}