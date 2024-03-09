package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.utils.Authentication;
import com.epam.labtaskspringcore.utils.ImplementsFindByUsernameAndPassword;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    @Mock
    private UsernameGenerator usernameGenerator;

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private TraineeDAO traineeDAO;

//    @Mock
//    Authentication authentication;

    TrainerService trainerService;

    private Trainer trainer1;
    private Trainer trainer2;

    @BeforeEach
    void setUp() {
        trainer1 = new Trainer();
        trainer1.setUserId(10);
        trainer1.setFirstName("John");
        trainer1.setLastName("Doe");
        trainer1.setPassword("123");
        trainer1.setIsActive(true);

        trainer2 = new Trainer();
        trainer2.setUserId(20);
        trainer2.setFirstName("Tommy");
        trainer2.setLastName("Smith");
        trainer2.setPassword("123");
        trainer2.setIsActive(true);

        Map<String, TraineeDAO> traineeDAOMap = new HashMap<>();
        Map<String, TrainerDAO> trainerDAOMap = new HashMap<>();
        trainerDAOMap.put("TRAINER_IN_MEMORY", trainerDAO);
        trainerService = new TrainerService(trainerDAOMap, traineeDAOMap, usernameGenerator);
        trainerService.setTraineeDAO(traineeDAO);
        trainerService.setTrainerDAO(trainerDAO);
    }

    @Test
    void testCreateInTrainerService() {

        when(usernameGenerator.generateUsername(trainer1)).thenReturn("John.Doe");
        when(trainerDAO.create(trainer1)).thenReturn(Optional.of(trainer1));
        when(trainerDAO.getById(10)).thenReturn(Optional.of(trainer1));
        Optional<Trainer> result = trainerService.create(trainer1);
        assertEquals(
                result,
                trainerDAO.getById(10),
                "created trainer should be in trainers list");
    }

    @Test
    void testUpdateInTrainerService() {
        when(usernameGenerator.generateUsername(trainer1)).thenReturn("John.Doe");
        when(trainerDAO.update(trainer1)).thenReturn(Optional.of(trainer1));
        when(trainerDAO.getById(10)).thenReturn(Optional.of(trainer1));
        Optional<Trainer> result = trainerService.update(trainer1);
        assertEquals(
                result,
                trainerDAO.getById(10),
                "updated trainer should be in trainers list");
    }

//    @Test
//    void testUpdateWithDbInTrainerService() {
//
//        Optional<Trainer> trainer = Optional.of(trainer1);
//        trainer1.setUsername("John.Doe");
//
////        doReturn(Optional.ofNullable(trainer1)).when(trainerDAO).findByUsernameAndPassword("John.Doe", "123");
////        when(Authentication.isAuthenticated(trainerDAO, "John.Doe", "123")).thenReturn(true);
//
////        when(Authentication.isAuthenticated(trainerDAO, any(), any())).thenReturn(true);
////        when(trainerDAO.findByUsernameAndPassword(null, null)).thenReturn(trainer);
//        when(UserService.isInvalidUser(trainer1)).thenReturn(false);
//
//        when(usernameGenerator.generateUsername(trainer1)).thenReturn("John.Doe");
//        when(trainerDAO.update(trainer1)).thenReturn(Optional.of(trainer1));
//        when(trainerDAO.getById(10)).thenReturn(Optional.of(trainer1));
//        Optional<Trainer> result = trainerService.update(trainer1);
//
//        trainerService.update(trainer2);
//        assertEquals(
//                result,
//                trainerDAO.getById(10),
//                "updated trainer should be in trainers list");
//    }


//    @Test
//    void TestGetByIdInTrainerService() {
//        trainer2.setFirstName("Tommy");
//        trainer2.setLastName("Smith");
//        trainer2.setPassword("123");
//        trainerService.create(trainer2);
//        Optional<Trainer> trainer = trainerService.getById(trainer2.getUserId(), "Tommy.Smith", "123");
//        assertAll(
//                () -> assertEquals(trainer1, trainerService.getById(1, "Sam.Smith", "123").get(), "trainer
//                should be " +
//                        "returned"),
//                () -> assertEquals(trainer2, trainerService.getById(2, "Tommy.Smith", "123")
//                                                           .get(), "trainer should be returned")
//                 );
//    }
//
//    @Test
//    void TestGetTrainersInTrainerService() {
//        trainer2.setFirstName("Sam");
//        trainer2.setLastName("Smith");
//        trainerService.create(trainer2);
//        trainer3.setFirstName("Sammy");
//        trainer3.setLastName("Smith");
//        trainerService.create(trainer3);
//        assertEquals(3, trainerService.getTrainers().size(), "trainers should be returned");
//    }
//
//    @Test
//    void TestGetByUsernameInTrainerService() {
//        assertEquals(3, trainerService.getTrainers().size(), "trainers should be returned");
//    }
//
//    @Test
//    void TestUpdatePasswordInTrainerService() {
//        assertEquals(3, trainerService.getTrainers().size(), "trainers should be returned");
//    }
//
//    @Test
//    void TestActivateTrainerInTrainerService() {
//        assertEquals(3, trainerService.getTrainers().size(), "trainers should be returned");
//    }
//
//    @Test
//    void TestDeactivateTrainerInTrainerService() {
//        assertEquals(3, trainerService.getTrainers().size(), "trainers should be returned");
//    }
//
//    @Test
//    void TestFindUnassignedTrainersByTraineeUsernameInTrainerService() {
//        assertEquals(3, trainerService.getTrainers().size(), "trainers should be returned");
//    }













//    InMemoryStorage storage;
//    TraineeDAO traineeDAO;
//    TrainerDAO trainerDAO;
//    TraineeService traineeService;
//    TrainerService trainerService;
//    UsernameGenerator usernameGenerator;
//    Trainer trainer1;
//    Trainer trainer2;
//    Trainer trainer3;
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
//        traineeService = new TraineeService(traineeDAOMap, usernameGenerator);
//        traineeService.setTraineeDAO(traineeDAO);
//
//        // new way of creating trainerService
//        Map<String, TrainerDAO> trainerDAOMap = new HashMap<>();
//        trainerDAOMap.put("TRAINER_IN_MEMORY", trainerDAO);
//        trainerService = new TrainerService(trainerDAOMap, traineeDAOMap, usernameGenerator);
//        trainerService.setTrainerDAO(trainerDAO);
//
//        TrainingType YOGA = new TrainingType();
//        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);
//
//        trainer1 = new Trainer();
//        trainer1.setUserId(10);
//        trainer1.setFirstName("Sam");
//        trainer1.setLastName("Smith");
//        trainer1.setPassword("123");
//        trainer1.setSpecialization(YOGA);
//        trainer1.setIsActive(true);
//        trainerService.create(trainer1);
//
//        trainer2 = new Trainer();
//        trainer2.setUserId(20);
//
//        trainer3 = new Trainer();
//        trainer3.setUserId(30);
//    }
//
//    @AfterEach
//    void tearDownAfterEach() {
//        storage.clearStorage();
//    }
//
//    @Test
//    void testCreateInTrainerService() {
//        trainer2.setFirstName("Sam");
//        trainer2.setLastName("Smith");
//        trainerService.create(trainer2);
//        assertAll(
//                () -> assertEquals(trainer2,
//                                   trainerDAO.getById(20).get(),
//                                   "created trainer should be in trainers list")
//
////                () -> assertEquals("Sam.Smith1",
////                                   storage.getTrainers().get(2).getUsername(),
////                                   "username should be properly generated")
//                 );
//    }
//
//    @Test
//    void testUpdateInTrainerService() {
//        trainer2.setFirstName("Sammy");
//        trainer2.setLastName("Smith");
//        trainerService.create(trainer2);
//        trainer2.setFirstName("Sam");
//        trainerService.update(trainer2);
//        assertAll(
//                () -> assertEquals(trainer2,
//                                   trainerDAO.getById(20).get(),
//                                   "updated trainer should be in trainers list"),
//                () -> assertEquals("Sam.Smith1",
//                                   storage.getTrainers().get(2).getUsername(),
//                                   "username should be properly generated")
//                 );
//    }
//
//    @Test
//    void TestGetByIdInTrainerService() {
//        trainer2.setFirstName("Tommy");
//        trainer2.setLastName("Smith");
//        trainer2.setPassword("123");
//        trainerService.create(trainer2);
//        Optional<Trainer> trainer = trainerService.getById(trainer2.getUserId(), "Tommy.Smith", "123");
//        assertAll(
//                () -> assertEquals(trainer1, trainerService.getById(1, "Sam.Smith", "123").get(), "trainer
//                should be " +
//                        "returned"),
//                () -> assertEquals(trainer2, trainerService.getById(2, "Tommy.Smith", "123").get(), "trainer
//                should be returned")
//                 );
//    }
//
//    @Test
//    void TestGetTrainersInTrainerService() {
//        trainer2.setFirstName("Sam");
//        trainer2.setLastName("Smith");
//        trainerService.create(trainer2);
//        trainer3.setFirstName("Sammy");
//        trainer3.setLastName("Smith");
//        trainerService.create(trainer3);
//        assertEquals(3, trainerService.getTrainers().size(), "trainers should be returned");
//    }
}