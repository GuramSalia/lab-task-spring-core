package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.utils.Authentication;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {
    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private UsernameGenerator usernameGenerator;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    TraineeService traineeService;

    private Trainee trainee1;
    private Trainee trainee2;

    @BeforeEach
    void setUpBeforeEach() {
        trainee1 = new Trainee();
        trainee1.setUserId(10);
        trainee1.setFirstName("John");
        trainee1.setLastName("Doe");
        trainee1.setPassword("123");
        trainee1.setIsActive(true);

        trainee2 = new Trainee();
        trainee2.setUserId(20);
        trainee2.setFirstName("Tommy");
        trainee2.setLastName("Smith");
        trainee2.setPassword("123");
        trainee2.setIsActive(true);

        Map<String, TraineeDAO> traineeDAOMap = new HashMap<>();
        traineeDAOMap.put("IN_MEMORY", traineeDAO);

        //        authentication = mock(Authentication.class);
        //        userService = new UserService();

        traineeService = new TraineeService(traineeDAOMap, authentication, userService,
                                            usernameGenerator);

        traineeService.setTraineeDAO(traineeDAO);
    }

    @Test
    void testCreateInTraineeService() {

        log.info("1....");
        when(usernameGenerator.generateUsername(trainee1)).thenReturn("John.Doe");
        when(traineeDAO.create(trainee1)).thenReturn(Optional.of(trainee1));
        when(traineeDAO.getById(10)).thenReturn(Optional.of(trainee1));
        log.info("2....");
        Optional<Trainee> result = traineeService.create(trainee1);
        log.info("3....");
        assertEquals(
                result,
                traineeDAO.getById(10),
                "created trainee should be in trainers list");
    }

    @Test
    void testUpdateInTraineeService() {
        when(usernameGenerator.generateUsername(trainee1)).thenReturn("John.Doe");
        when(traineeDAO.update(trainee1)).thenReturn(Optional.of(trainee1));
        when(traineeDAO.getById(10)).thenReturn(Optional.of(trainee1));
        Optional<Trainee> result = traineeService.update(trainee1);
        assertEquals(
                result,
                traineeDAO.getById(10),
                "updated trainee should be in trainees list");
    }

    @Test
    void testUpdateWithDbInTraineeService() {

        Optional<Trainee> trainee = Optional.of(trainee1);
        trainee1.setUsername("John.Doe");

        when(authentication.isAuthenticated(traineeDAO, "John.Doe", "123")).thenReturn(true);

        when(usernameGenerator.generateUsername(trainee1)).thenReturn("John.Doe");
        when(traineeDAO.update(trainee1)).thenReturn(Optional.of(trainee1));
        when(traineeDAO.getById(10)).thenReturn(Optional.of(trainee1));
        Optional<Trainee> result = traineeService.update(trainee1);

        traineeService.update(trainee1, "John.Doe", "123");
        assertEquals(
                result,
                traineeDAO.getById(10),
                "updated trainee should be in trainees list");
    }

    @Test
    void testDeleteInTraineeService() {

        when(traineeDAO.delete(trainee1)).thenReturn(true);
        boolean result = traineeService.delete(trainee1);
        assertTrue(result, "delete should return true");
    }

    @Test
    void testDeleteWithDbInTraineeService() {

        trainee1.setUsername("John.Doe");
        when(authentication.isAuthenticated(traineeDAO, "John.Doe", "123")).thenReturn(true);
        when(traineeDAO.findByUsername("John.Doe")).thenReturn(Optional.of(trainee1));
        when(traineeDAO.delete(trainee1)).thenReturn(true);
        boolean result = traineeService.delete("John.Doe", "123");
        assertTrue(result, "delete should return true");
    }

    @Test
    void TestGetByIdInTraineeService() {
        trainee1.setUsername("John.Doe");
        trainee2.setUsername("Tommy.Smith");

        when(traineeDAO.getById(10)).thenReturn(Optional.of(trainee1));
        when(authentication.isAuthenticated(traineeDAO, "John.Doe", "123")).thenReturn(true);
        when(traineeDAO.getById(20)).thenReturn(Optional.of(trainee2));
        when(authentication.isAuthenticated(traineeDAO, "Tommy.Smith", "123")).thenReturn(true);
        assertAll(
                () -> assertEquals(
                        Optional.of(trainee1),
                        traineeService.getById(10, "John.Doe", "123"),
                        "trainee should be returned"),
                () -> assertEquals(
                        Optional.of(trainee2),
                        traineeService.getById(20, "Tommy.Smith", "123"),
                        "trainee should be returned")
                 );
    }

    @Test
    void TestGetTraineesInTrainerService() {
        when(traineeDAO.getTrainees()).thenReturn(List.of(trainee1, trainee2));
        assertEquals(2, traineeService.getTrainees().size(), "trainees should be returned");
        assertEquals(trainee1, traineeService.getTrainees().get(0), "trainees should be returned");
        assertEquals(trainee2, traineeService.getTrainees().get(1), "trainees should be returned");
    }

    @Test
    void TestGetByUsernameInTraineeService() {
        when(authentication.isAuthenticated(traineeDAO, "John.Doe", "123")).thenReturn(true);
        when(traineeDAO.findByUsername("John.Doe")).thenReturn(Optional.of(trainee1));
        Optional<Trainee> result = traineeService.getByUsername("John.Doe", "123");
        assertEquals(result, Optional.of(trainee1), "trainees should be returned");
    }

    @Test
    void TestUpdatePasswordInTraineeService() {
        trainee1.setUsername("John.Doe");
        when(authentication.isAuthenticated(traineeDAO, "John.Doe", "123")).thenReturn(true);
        when(traineeDAO.update(trainee1)).thenReturn(Optional.of(trainee1));
        Optional<Trainee> result = traineeService.updatePassword(trainee1, "John.Doe", "123", "321");
        assertEquals(result, Optional.of(trainee1), "trainees should be returned");
    }

    @Test
    void TestActivateTraineeInTraineeService() {
        trainee1.setUsername("John.Doe");
        when(authentication.isAuthenticated(traineeDAO, "John.Doe", "123")).thenReturn(true);
        boolean result = traineeService.activateTrainee(trainee1, "John.Doe", "123");
        assertFalse(result, "should be false because trainee is already activated");
        trainee1.setIsActive(false);
        result = traineeService.activateTrainee(trainee1, "John.Doe", "123");
        assertTrue(result, "should be true because trainee is already activated");
    }

    @Test
    void TestDeactivateTraineeInTraineeService() {
        trainee1.setUsername("John.Doe");
        when(authentication.isAuthenticated(traineeDAO, "John.Doe", "123")).thenReturn(true);
        boolean result = traineeService.deactivateTrainee(trainee1, "John.Doe", "123");
        assertTrue(result, "should be true because trainee is already activated");
        result = traineeService.deactivateTrainee(trainee1, "John.Doe", "123");
        assertFalse(result, "should be false because trainee is already activated");
    }

    //    InMemoryStorage storage;
    //    TraineeDAO traineeDAO;
    //    TrainerDAO trainerDAO;
    //    TrainerService trainerService;
    //    TraineeService traineeService;
    //    @Mock
    //    Authentication authentication;
    //
    //    @Mock
    //    private UserService userService;
    //    UsernameGenerator usernameGenerator;
    //    Trainee trainee1;
    //    Trainee trainee2;
    //    Trainee trainee3;
    //
    //    @BeforeEach
    //    void setUpBeforeEach() {
    //        storage = new InMemoryStorage();
    //        traineeDAO = new TraineeDAOInMemoryStorageImpl(storage);
    //        trainerDAO = new TrainerDAOInMemoryStorageImpl(storage);
    //        usernameGenerator = new UsernameGenerator(trainerDAO, traineeDAO);
    //
    //        // new way of creating traineeService
    //        Map<String, TraineeDAO> traineeDAOMap = new HashMap<>();
    //        traineeDAOMap.put("IN_MEMORY", traineeDAO);
    //        traineeService = new TraineeService(traineeDAOMap, authentication, userService, usernameGenerator);
    //        traineeService.setTraineeDAO(traineeDAO);
    //
    //        // new way of creating trainerService
    //        Map<String, TrainerDAO> trainerDAOMap = new HashMap<>();
    //        trainerDAOMap.put("TRAINER_IN_MEMORY", trainerDAO);
    //        trainerService = new TrainerService(trainerDAOMap, traineeDAOMap, authentication, userService,
    //                                            usernameGenerator);
    //        trainerService.setTrainerDAO(trainerDAO);
    //
    //        trainee1 = new Trainee();
    //        trainee1.setUserId(1);
    //        trainee1.setFirstName("Sam");
    //        trainee1.setLastName("Smith");
    //        trainee1.setAddress("trainee1 address");
    //        trainee1.setIsActive(true);
    //        traineeService.create(trainee1);
    //
    //        trainee2 = new Trainee();
    //        trainee2.setUserId(2);
    //        trainee3 = new Trainee();
    //        trainee3.setUserId(3);
    //    }
    //
    //    @AfterEach
    //    void tearDownAfterEach() {
    //        storage.clearStorage();
    //    }
    //
    //    @Test
    //    @Disabled
    //    void testCreateInTraineeService() {
    //
    //        trainee2.setUserId(8);
    //        trainee2.setFirstName("Sam");
    //        trainee2.setLastName("Smith");
    //        traineeService.create(trainee2);
    //
    //        log.info("<<<<<>>>>>>>>>>");
    //        log.info(storage.getTrainees().get(8).getFirstName().toString());
    //
    //        assertAll(
    //                () -> assertEquals(trainee2,
    //                                   storage.getTrainees().get(8),
    //                                   "created trainee should be in trainees list"),
    //                () -> assertEquals("Sam.Smith1",
    //                                   storage.getTrainees().get(8).getUsername(),
    //                                   "username should be properly generated")
    //                 );
    //    }
    //
    //    @Test
    //    @Disabled
    //    void testUpdateInTraineeService() {
    //        trainee2.setFirstName("Sammy");
    //        trainee2.setLastName("Smith");
    //        traineeService.create(trainee2);
    //        trainee2.setFirstName("Sam");
    //        traineeService.update(trainee2);
    //        assertAll(
    //                () -> assertEquals(trainee2,
    //                                   storage.getTrainees().get(2),
    //                                   "updated trainee should be in trainees list"),
    //                () -> assertEquals("Sam.Smith1",
    //                                   storage.getTrainees().get(2).getUsername(),
    //                                   "username should be properly generated")
    //                 );
    //    }
    //
    //    @Test
    //    void testDeleteInTraineeService() {
    //        trainee2.setFirstName("sam");
    //        trainee2.setLastName("Smith");
    //        traineeService.create(trainee2);
    //        traineeService.delete(trainee2);
    //        traineeService.delete(trainee1);
    //
    //        assertAll(
    //                () -> assertNull(storage.getTrainees().get(2), "deleted trainee should not be in trainees list"),
    //                () -> assertNull(storage.getTrainees().get(1), "deleted trainee should not be in trainees list")
    //                 );
    //    }
    //
    //    @Test
    //    @Disabled
    //    void testGetByIdInTraineeService() {
    //        trainee2.setFirstName("Sam");
    //        trainee2.setLastName("Smith");
    //        traineeService.create(trainee2);
    //        Optional<Trainee> trainee = traineeService.getById(trainee2.getUserId(), "Sam.Smith", "123");
    //        assertAll(
    //                () -> assertEquals(trainee1, traineeDAO.getById(1).get(), "trainee should be " +
    //                        "returned"),
    //                () -> assertEquals(trainee2, traineeDAO.getById(2).get(), "trainee should be returned")
    //                 );
    //    }
    //
    //    @Test
    //    @Disabled
    //    void testGetInTraineesService() {
    //        trainee2.setFirstName("Sam");
    //        trainee2.setLastName("Smith");
    //        traineeService.create(trainee2);
    //        trainee3.setFirstName("Sammy");
    //        trainee3.setLastName("Smith");
    //        traineeService.create(trainee3);
    //        assertEquals(3, traineeService.getTrainees().size(), "trainees should be returned");
    //    }
}