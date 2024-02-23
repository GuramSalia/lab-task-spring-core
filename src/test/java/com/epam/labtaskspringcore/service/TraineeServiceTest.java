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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
class TraineeServiceTest {
    //
    //    @Mock
    //    private TraineeDAO traineeDAO;
    //
    //    @Mock
    //    private UsernameGenerator usernameGenerator;
    //
    //    @InjectMocks
    //    private TraineeService traineeService;
    //
    //    @BeforeEach
    //    void setUp() {
    //        MockitoAnnotations.openMocks(this);
    //    }
    //
    //    @Test
    //    void testCreateTrainee() {
    ////        // Arrange
    //        Trainee trainee = new Trainee();
    //        trainee.setFirstName("John");
    //        trainee.setLastName("Doe");
    //
    //        when(usernameGenerator.generateUsername(trainee)).thenReturn("John.Doe1");
    //        when(traineeDAO.create(trainee)).thenReturn(Optional.of(trainee));
    //
    //        // Act
    //        Optional<Trainee> result = traineeService.create(trainee);
    //
    //        // Assert
    //        assertNotNull(result, "Result should not be null");
    //        assertEquals(trainee, result.orElse(null), "Created trainee should be returned");
    //        assertEquals("John.Doe1", trainee.getUsername(), "Username should be properly generated");
    //
    //        // Verify that the methods were called
    //        verify(usernameGenerator, times(1)).generateUsername(trainee);
    //        verify(traineeDAO, times(1)).create(trainee);
    //    }

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
    void testCreateTraineeService() {
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
    void testUpdateTraineeService() {
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
    void testDeleteTraineeService() {
        trainee2.setFirstName("sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        traineeService.delete(trainee2.getId());
        traineeService.delete(trainee1.getId());

        assertAll(
                () -> assertNull(storage.getTrainees().get(2), "deleted trainee should not be in trainees list"),
                () -> assertNull(storage.getTrainees().get(1), "deleted trainee should not be in trainees list")
                 );
    }

    @Test
    void testGetByIdService() {
        trainee2.setFirstName("Sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        Optional<Trainee> trainee = traineeService.getById(trainee2.getId());
        assertAll(
                () -> assertEquals(trainee1, traineeService.getById(1).get(), "trainee should be returned"),
                () -> assertEquals(trainee2, traineeService.getById(2).get(), "trainee should be returned")
                 );
    }

    @Test
    void testGetTraineesService() {
        trainee2.setFirstName("Sam");
        trainee2.setLastName("Smith");
        traineeService.create(trainee2);
        trainee3.setFirstName("Sammy");
        trainee3.setLastName("Smith");
        traineeService.create(trainee3);
        assertEquals(3, traineeService.getTrainees().size(), "trainees should be returned");
    }
}