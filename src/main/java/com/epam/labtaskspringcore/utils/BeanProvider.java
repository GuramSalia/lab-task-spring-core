package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.config.ApplicationContextProvider;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.dao.TraineeDAODatabaseImpl;
import com.epam.labtaskspringcore.dao.TrainerDAODatabaseImpl;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingService;
import com.epam.labtaskspringcore.service.TrainingTypeService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BeanProvider {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;

    @Autowired
    public BeanProvider(
            TraineeService traineeService,
            TrainerService trainerService,
            TrainingService trainingService,
            TrainingTypeService trainingTypeService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.trainingTypeService = trainingTypeService;
    }

    private static final ApplicationContext context = ApplicationContextProvider.getContext();

    public InMemoryStorage getInMemoryStorage() {
        return context.getBean(InMemoryStorage.class);
    }

    public static TrainingTypeService getTrainingTypeService() {
        return context.getBean(TrainingTypeService.class);
    }

    public static TraineeDAODatabaseImpl getTraineeDAODatabaseImpl() {
        return context.getBean(TraineeDAODatabaseImpl.class);
    }

    public static TrainerDAODatabaseImpl getTrainerDAODatabaseImpl() {
        return context.getBean(TrainerDAODatabaseImpl.class);
    }
}
