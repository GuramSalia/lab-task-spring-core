package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.utils.Authentication;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    @Mock
    private UsernameGenerator usernameGenerator;

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    Authentication authentication;

    @Mock
    private UserService userService;

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
        userService = new UserService();
        trainerService = new TrainerService(
                trainerDAOMap, traineeDAOMap, authentication, userService, usernameGenerator);
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

    @Test
    void testUpdateWithDbInTrainerService() {

        Optional<Trainer> trainer = Optional.of(trainer1);
        trainer1.setUsername("John.Doe");

        when(authentication.isAuthenticated(trainerDAO, "John.Doe", "123")).thenReturn(true);

        when(usernameGenerator.generateUsername(trainer1)).thenReturn("John.Doe");
        when(trainerDAO.update(trainer1)).thenReturn(Optional.of(trainer1));
        when(trainerDAO.getById(10)).thenReturn(Optional.of(trainer1));
        Optional<Trainer> result = trainerService.update(trainer1);

        trainerService.update(trainer1, "John.Doe", "123");
        assertEquals(
                result,
                trainerDAO.getById(10),
                "updated trainer should be in trainers list");
    }

    @Test
    void TestGetByIdInTrainerService() {
        trainer1.setUsername("John.Doe");
        trainer2.setUsername("Tommy.Smith");

        when(trainerDAO.getById(10)).thenReturn(Optional.of(trainer1));
        when(authentication.isAuthenticated(trainerDAO, "John.Doe", "123")).thenReturn(true);
        when(trainerDAO.getById(20)).thenReturn(Optional.of(trainer2));
        when(authentication.isAuthenticated(trainerDAO, "Tommy.Smith", "123")).thenReturn(true);
        assertAll(
                () -> assertEquals(
                        Optional.of(trainer1),
                        trainerService.getById(10, "John.Doe", "123"),
                        "trainer should be returned"),
                () -> assertEquals(
                        Optional.of(trainer2),
                        trainerService.getById(20, "Tommy.Smith", "123"),
                        "trainer should be returned")
                 );
    }

    @Test
    void TestGetTrainersInTrainerService() {
        when(trainerDAO.getTrainers()).thenReturn(List.of(trainer1, trainer2));
        assertEquals(2, trainerService.getTrainers().size(), "trainers should be returned");
        assertEquals(trainer1, trainerService.getTrainers().get(0), "trainers should be returned");
        assertEquals(trainer2, trainerService.getTrainers().get(1), "trainers should be returned");
    }

    @Test
    void TestGetByUsernameInTrainerService() {
        when(authentication.isAuthenticated(trainerDAO, "John.Doe", "123")).thenReturn(true);
        when(trainerDAO.findByUsername("John.Doe")).thenReturn(Optional.of(trainer1));
        Optional<Trainer> result = trainerService.getByUsername("John.Doe", "123");
        assertEquals(result, Optional.of(trainer1), "trainers should be returned");
    }

    @Test
    void TestUpdatePasswordInTrainerService() {
        trainer1.setUsername("John.Doe");
        when(authentication.isAuthenticated(trainerDAO, "John.Doe", "123")).thenReturn(true);
        when(trainerDAO.update(trainer1)).thenReturn(Optional.of(trainer1));
        Optional<Trainer> result = trainerService.updatePassword(trainer1, "John.Doe", "123", "321");
        assertEquals(result, Optional.of(trainer1), "trainers should be returned");
    }

    @Test
    void TestActivateTrainerInTrainerService() {
        trainer1.setUsername("John.Doe");
        when(authentication.isAuthenticated(trainerDAO, "John.Doe", "123")).thenReturn(true);
        boolean result = trainerService.activateTrainer(trainer1, "John.Doe", "123");
        assertFalse(result, "should be false because trainer is already activated");
        trainer1.setIsActive(false);
        result = trainerService.activateTrainer(trainer1, "John.Doe", "123");
        assertTrue(result, "should be true because trainer is already activated");
    }

    @Test
    void TestDeactivateTrainerInTrainerService() {
        trainer1.setUsername("John.Doe");
        when(authentication.isAuthenticated(trainerDAO, "John.Doe", "123")).thenReturn(true);
        boolean result = trainerService.deactivateTrainer(trainer1, "John.Doe", "123");
        assertTrue(result, "should be true because trainer is already activated");
        result = trainerService.deactivateTrainer(trainer1, "John.Doe", "123");
        assertFalse(result, "should be false because trainer is already activated");
    }

    @Test
    void TestFindUnassignedTrainersByTraineeUsernameInTrainerService() {
        when(authentication.isAuthenticated(traineeDAO, "Tim.Doe", "123")).thenReturn(true);
        when(trainerDAO.findIdsOfUnassignedTrainersByTraineeUsername("Tim.Doe")).thenReturn(List.of(10, 20));
        when(trainerDAO.getById(10)).thenReturn(Optional.of(trainer1));
        when(trainerDAO.getById(20)).thenReturn(Optional.of(trainer2));
        List<Trainer> trainers = new ArrayList<Trainer>();
        trainers.add(trainer1);
        trainers.add(trainer2);
        List<Trainer> result = trainerService.findUnassignedTrainersByTraineeUsername("Tim.Doe", "123");
        assertEquals(result, trainers, "trainers should be returned");
    }
}