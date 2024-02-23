package com.epam.labtaskspringcore.service;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.*;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import com.epam.labtaskspringcore.utils.UsernameGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class TrainingServiceTest {

    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TrainingDAO trainingDAO;
    TraineeService traineeService;
    TrainerService trainerService;
    TrainingService trainingService;
    UsernameGenerator usernameGenerator;
    Trainee trainee1;
    Trainee trainee2;
    Trainer trainer1;
    Trainer trainer2;

    Training training1;
    Training training2;
    Training training3;

    @BeforeEach
    void setUpBeforeEach() {
        storage = new InMemoryStorage();
        traineeDAO = new TraineeDAOImpl(storage);
        trainerDAO = new TrainerDAOImpl(storage);
        trainingDAO = new TrainingDAOImpl(storage);
        usernameGenerator = new UsernameGeneratorImpl(trainerDAO, traineeDAO);
        traineeService = new TraineeService(traineeDAO, usernameGenerator);
        trainerService = new TrainerService(trainerDAO, usernameGenerator);
        trainingService = new TrainingService(trainingDAO, trainerDAO);

        trainee1 = new Trainee();
        trainee1.setId(1);
        trainee1.setFirstName("Sam");
        trainee1.setLastName("Smith");
        traineeService.create(trainee1);

        trainee2 = new Trainee();
        trainee2.setId(1);
        trainee2.setFirstName("Sally");
        trainee2.setLastName("Schmidt");
        traineeService.create(trainee2);

        trainer1 = new Trainer();
        trainer1.setId(1);
        trainer1.setFirstName("John");
        trainer1.setLastName("Doe");
        trainer1.setSpecialization(TrainingType.YOGA);
        trainerService.create(trainer1);

        trainer2 = new Trainer();
        trainer2.setId(2);
        trainer2.setFirstName("Bob");
        trainer2.setLastName("Brown");
        trainer2.setSpecialization(TrainingType.CARDIO);
        trainerService.create(trainer2);

        training1 = new Training();
        training1.setId(1);
        training1.setName("training1");
        training1.setType(TrainingType.YOGA);
        training1.setDurationInMinutes(25);
        training1.setTraineeId(1);
        training1.setTrainerId(1);


        training2 = new Training();
        training2.setId(2);
        training3 = new Training();
        training3.setId(3);
    }

    @AfterEach
    void tearDownAfterEach() {
        storage.clearStorage();
    }

    @Test
    void testCreateInTrainingService() {
        trainingService.create(training1);
        assertEquals(training1, storage.getTrainings().get(1), "created training should be in trainings list");
        training2.setType(TrainingType.HIIT);
        training2.setTrainerId(2);
        trainingService.create(training2);
        assertTrue(Optional.ofNullable(storage.getTrainings().get(2)).isEmpty(), "name should be properly generated");
    }

    @Test
    void TestGetByIdInTrainingService() {
        trainingService.create(training1);
        training2.setType(TrainingType.HIIT);
        trainer2.setSpecialization(TrainingType.HIIT);
        training2.setTrainerId(2);
        trainingService.create(training2);
        Optional<Trainer> trainer = trainerService.getById(trainer2.getId());
        assertAll(
                () -> assertEquals(trainer1, trainerService.getById(1).get(), "training should be returned"),
                () -> assertEquals(trainer2, trainerService.getById(2).get(), "training should be returned")
                 );
    }
}