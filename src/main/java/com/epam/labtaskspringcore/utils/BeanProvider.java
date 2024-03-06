package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.config.ApplicationContextProvider;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingService;
import org.springframework.context.ApplicationContext;

public class BeanProvider {

    private static final ApplicationContext context = ApplicationContextProvider.getContext();

    public static InMemoryStorage getInMemoryStorage() {
        return context.getBean(InMemoryStorage.class);
    }

    public static TrainerService getTrainerService() {
        return context.getBean(TrainerService.class);
    }

    public static TraineeService getTraineeService(String daoImplementationName) {
        TraineeService traineeService = context.getBean(TraineeService.class);
        traineeService.setTraineeDAOFromTraineeDAOs(daoImplementationName);
        return traineeService;
    }

    public static TrainingService getTrainingService() {
        return context.getBean(TrainingService.class);
    }
}
