package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.*;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
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
        usernameGenerator = new UsernameGenerator(trainerDAO, traineeDAO);
        traineeService = new TraineeService(traineeDAO, usernameGenerator);
        trainerService = new TrainerService(trainerDAO, usernameGenerator);
        trainingService = new TrainingService(trainingDAO, trainerDAO);

        TrainingType YOGA = new TrainingType();
        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);
        TrainingType CARDIO = new TrainingType();
        CARDIO.setTrainingType(TrainingType.TrainingTypeEnum.CARDIO);

        trainee1 = new Trainee();
        trainee1.setUserId(1);
        trainee1.setFirstName("Sam");
        trainee1.setLastName("Smith");
        traineeService.createWithDao(trainee1);

        trainee2 = new Trainee();
        trainee2.setUserId(1);
        trainee2.setFirstName("Sally");
        trainee2.setLastName("Schmidt");
        traineeService.createWithDao(trainee2);

        trainer1 = new Trainer();
        trainer1.setUserId(1);
        trainer1.setFirstName("John");
        trainer1.setLastName("Doe");
        trainer1.setSpecialization(YOGA);
        trainerService.createWithDao(trainer1);

        trainer2 = new Trainer();
        trainer2.setUserId(2);
        trainer2.setFirstName("Bob");
        trainer2.setLastName("Brown");
        trainer2.setSpecialization(CARDIO);
        trainerService.createWithDao(trainer2);

        training1 = new Training();
        training1.setTrainingId(1);
        training1.setTrainingName("training1");
        training1.setTrainingType(YOGA);
        training1.setTrainingDurationInMinutes(25);
        training1.setTrainee(trainee1);
        training1.setTrainer(trainer1);


        training2 = new Training();
        training2.setTrainingId(2);
        training3 = new Training();
        training3.setTrainingId(3);
    }

    @AfterEach
    void tearDownAfterEach() {
        storage.clearStorage();
    }

    @Test
    void testCreateInTrainingService() {

        TrainingType HIIT = new TrainingType();
        HIIT.setTrainingType(TrainingType.TrainingTypeEnum.HIIT);

        trainingService.createWithDao(training1);
        assertEquals(training1, storage.getTrainings().get(1), "created training should be in trainings list");
        training2.setTrainingType(HIIT);
        training2.setTrainer(trainer2);
        trainingService.createWithDao(training2);
        assertTrue(Optional.ofNullable(storage.getTrainings().get(2)).isEmpty(), "name should be properly generated");
    }

    @Test
    void TestGetByIdInTrainingService() {

        TrainingType HIIT = new TrainingType();
        HIIT.setTrainingType(TrainingType.TrainingTypeEnum.HIIT);

        trainingService.createWithDao(training1);
        training2.setTrainingType(HIIT);
        trainer2.setSpecialization(HIIT);
        training2.setTrainer(trainer2);
        trainingService.createWithDao(training2);
        Optional<Trainer> trainer = trainerService.getByIdWithDao(trainer2.getUserId());
        assertAll(
                () -> assertEquals(trainer1, trainerService.getByIdWithDao(1).get(), "training should be returned"),
                () -> assertEquals(trainer2, trainerService.getByIdWithDao(2).get(), "training should be returned")
                 );
    }
}