package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingService;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        usernameGenerator = new UsernameGenerator(trainerDAO, traineeDAO);
        //        traineeService = new TraineeService(traineeDAO, usernameGenerator);
        trainerService = new TrainerService(trainerDAO, usernameGenerator);
        trainingService = new TrainingService(trainingDAO, trainerDAO);

        TrainingType YOGA = new TrainingType();
        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);
        TrainingType CARDIO = new TrainingType();
        CARDIO.setTrainingType(TrainingType.TrainingTypeEnum.CARDIO);

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
        //        training1.setTrainee(1);
        training1.setTrainer(trainer1);
        trainingService.createWithDao(training1);

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
    void testCreateInTrainingDAO() {
        training2.setTrainingId(1);
        assertEquals(trainingDAO.create(training2), Optional.empty());
        training2.setTrainingId(2);
        assertEquals(trainingDAO.create(training2).get(), training2);
    }

    @Test
    void testGetByIdInTrainingDAO() {
        trainingDAO.create(training2);
        trainingDAO.create(training3);
        assertEquals(trainingDAO.getTrainings(), List.of(training1, training2, training3));
    }
}