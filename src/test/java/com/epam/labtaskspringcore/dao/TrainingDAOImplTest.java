package com.epam.labtaskspringcore.dao;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingService;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import com.epam.labtaskspringcore.utils.UsernameGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class TrainingDAOImplTest {
    InMemoryStorage storage;
    TraineeDAO traineeDAO;
    TrainerDAO trainerDAO;
    TrainingDAO trainingDAO;
//    TraineeService traineeService;
    TrainerService trainerService;
    TrainingService trainingService;
    UsernameGenerator usernameGenerator;
//    Trainee trainee1;
//    Trainee trainee2;
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
//        traineeService = new TraineeService(traineeDAO, usernameGenerator);
        trainerService = new TrainerService(trainerDAO, usernameGenerator);
        trainingService = new TrainingService(trainingDAO, trainerDAO);

//        trainee1 = new Trainee();
//        trainee1.setId(1);
//        trainee1.setFirstName("Sam");
//        trainee1.setLastName("Smith");
//        traineeService.create(trainee1);
//
//        trainee2 = new Trainee();
//        trainee2.setId(1);
//        trainee2.setFirstName("Sally");
//        trainee2.setLastName("Schmidt");
//        traineeService.create(trainee2);

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
        trainingService.create(training1);

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
    void testCreateInTrainingDAO() {
        training2.setId(1);
        assertEquals(trainingDAO.create(training2), Optional.empty());
        training2.setId(2);
        assertEquals(trainingDAO.create(training2).get(), training2);
    }

    @Test
    void testGetByIdInTrainingDAO() {
        trainingDAO.create(training2);
        trainingDAO.create(training3);
        assertEquals(trainingDAO.getTrainings(), List.of(training1, training2, training3));
    }
}