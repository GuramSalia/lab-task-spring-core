package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.config.ApplicationContextProvider;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingService;
import com.epam.labtaskspringcore.service.TrainingTypeService;
import org.springframework.context.ApplicationContext;

public class BeanProvider {

    private static final ApplicationContext context = ApplicationContextProvider.getContext();

    public static InMemoryStorage getInMemoryStorage() {
        return context.getBean(InMemoryStorage.class);
    }

    public static TraineeService getTraineeService(String daoImplementationName) {
        TraineeService traineeService = context.getBean(TraineeService.class);
        traineeService.setTraineeDAOFromTraineeDAOMap(daoImplementationName);
        return traineeService;
    }

    public static TrainerService getTrainerService(String daoImplementationName) {
        TrainerService trainerService = context.getBean(TrainerService.class);
        trainerService.setTrainerDAOFromTrainerDAOMap(daoImplementationName);
        return trainerService;
    }

    public static TrainingService getTrainingService(String daoImplNameForTraining, String daoImplNameForTrainer) {
        TrainingService trainingService = context.getBean(TrainingService.class);
        trainingService.setTrainingDAOFromTrainingDAOMap(daoImplNameForTraining);
        trainingService.setTrainerDAOFromTrainerDAOMap(daoImplNameForTrainer);
        return trainingService;
    }

    public static TrainingTypeService getTrainingTypeService() {
        return context.getBean(TrainingTypeService.class);
    }
}
