package com.epam.labtaskspringcore;

import com.epam.labtaskspringcore.config.ApplicationContextProvider;
import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingService;
import com.epam.labtaskspringcore.utils.BeanProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@Slf4j
@SpringBootApplication
public class LabTaskSpringCoreApplication {
    public static void main(String[] args) {

        SpringApplication.run(LabTaskSpringCoreApplication.class, args);

        InMemoryStorage inMemoryStorage = BeanProvider.getInMemoryStorage();
        TrainerService trainerService = BeanProvider.getTrainerService();
        TraineeService traineeService = BeanProvider.getTraineeService();
        TrainingService trainingService = BeanProvider.getTrainingService();

        TrainingType YOGA = new TrainingType();
        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);
        TrainingType PERSONAL = new TrainingType();
        PERSONAL.setTrainingType(TrainingType.TrainingTypeEnum.PERSONAL);

        log.info(".......  START  .......\n");
        log.info("list of TRAINEES:");
        inMemoryStorage.getTrainees().values().stream().forEach(x -> log.info(x.toString()));
        log.info("list of TRAINERS:");
        inMemoryStorage.getTrainers().values().stream().forEach(x -> log.info(x.toString()));
        log.info("list of TRAININGS:");
        inMemoryStorage.getTrainings().values().stream().forEach(x -> log.info(x.toString()));
        log.info(" ------ END of inMemoryStorage lists\n");

        log.info("   .....   trainer userId=5 ");
        Trainer trainer5 = trainerService.createTrainer(5, "John", "Doe", true, YOGA);
        trainerService.logTrainerCreationDetails(trainer5);
        log.info("   -----   end of trainer userId=5 \n");

        log.info("   .....   trainer userId=6 ");
        Trainer trainer6 = trainerService.createTrainer(6, "John", "Doe", true, YOGA);
        trainerService.logTrainerCreationDetails(trainer6);
        log.info("   -----   end of trainer userId=6 \n");

        log.info("   .....   training 3 ");

        Training training3 = trainingService.createTraining(3, traineeService.getById(4).get(),
                trainer5, "personal training", PERSONAL, 30);
        trainingService.logTrainingCreationDetails(training3);
        log.info("   -----   end of training 3 \n");

        log.info("   .....   training 4 ");
        Training training4 = trainingService.createTraining(4, traineeService.getById(3).get(),
                trainer5, "Yoga training", YOGA, 30);
        trainingService.logTrainingCreationDetails(training4);
        log.info("   -----   end of training 4 \n");

        log.info("   .....   update trainee userId=4");
        traineeService.logLastNameUpdateOfTrainee(4, "Schmidt");
        log.info("   -----   end of trainee userId=4 \n");

        log.info(" ....... task2 related part starts here part end here .......\n");


    }
}
