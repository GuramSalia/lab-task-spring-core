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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@Slf4j
@SpringBootApplication
public class LabTaskSpringCoreApplication {
    public static void main(String[] args) {

        SpringApplication.run(LabTaskSpringCoreApplication.class, args);

        InMemoryStorage inMemoryStorage = ApplicationContextProvider.getContext().getBean(InMemoryStorage.class);

        log.info("\n\n>>>> START  ==============\n");
        inMemoryStorage.getTrainees().values().stream().forEach(x -> log.info(x.toString()));
        log.info("^^^^ TRAINEES\n");
        inMemoryStorage.getTrainers().values().stream().forEach(x -> log.info(x.toString()));
        log.info("^^^^ TRAINERS\n");
        inMemoryStorage.getTrainings().values().stream().forEach(x -> log.info(x.toString()));
        log.info("^^^^ TRAININGS\n");

        TrainerService trainerService = ApplicationContextProvider.getContext().getBean(TrainerService.class);
        TraineeService traineeService = ApplicationContextProvider.getContext().getBean(TraineeService.class);
        TrainingService trainingService = ApplicationContextProvider.getContext().getBean(TrainingService.class);

        log.info("\n \ttrainer 3 ");
        Trainer trainer3 = new Trainer();
        trainer3.setId(3);
        trainer3.setFirstName("John");
        trainer3.setLastName("Doe");
        trainer3.setActive(true);
        trainer3.setSpecialization(TrainingType.YOGA);
        if (trainerService.create(trainer3).isEmpty()) {
            log.error("could not create trainer");
        } else {
            Optional<Trainer> trainer3Optional = trainerService.getById(3);
            if (trainer3Optional.isEmpty()) {
                log.error("could not get trainer3");
            } else {
                log.info(trainer3Optional.get().toString());
            }
        }
        log.info("\t end of trainer 3 \n");

        log.info("\n \ttrainer 4 ");
        Trainer trainer4 = new Trainer();
        trainer4.setId(4);
        trainer4.setFirstName("John");
        trainer4.setLastName("Doe");
        trainer4.setActive(true);
        trainer4.setSpecialization(TrainingType.YOGA);
        if (trainerService.create(trainer4).isEmpty()) {
            log.error("could not create trainer");
        } else {
            Optional<Trainer> trainer4Optional = trainerService.getById(3);
            if (trainer4Optional.isEmpty()) {
                log.error("could not get trainer3");
            } else {
                log.info(trainer4Optional.get().toString());
            }
        }
        log.info("\t end of trainer 4 \n");

        log.info("\n \ttraining 3 ");
        Training training3 = new Training();
        training3.setId(3);
        training3.setTraineeId(2);
        training3.setTrainerId(3);
        training3.setName("personal training");
        training3.setType(TrainingType.PERSONAL);
        training3.setDurationInMinutes(30);
        if (trainingService.create(training3).isEmpty()) {
            log.error("could not create training");
        } else {
            Optional<Training> training3Optional = trainingService.getById(3);
            if (training3Optional.isEmpty()) {
                log.error("could not get training3");
            } else {
                log.info(training3Optional.get().toString());
            }
        }
        log.info("\tend of training 3 \n");

        log.info("\n \ttraining 4 ");
        Training training4 = new Training();
        training4.setId(4);
        training4.setTraineeId(4);
        training4.setTrainerId(3);
        training4.setName("Yoga training");
        training4.setType(TrainingType.YOGA);
        training4.setDurationInMinutes(30);
        if (trainingService.create(training4).isEmpty()) {
            log.error("could not create training");
        } else {
            Optional<Training> training4Optional = trainingService.getById(4);
            if (training4Optional.isEmpty()) {
                log.error("could not get training3");
            } else {
                log.info(training4Optional.get().toString());
            }
        }
        log.info("\tend of training 4 \n");

        // updated trainee with id=2
        log.info("\n trainee id=2");
        if (traineeService.getById(2).isEmpty()) {
            log.error("No training with id=2 exists");
        } else {
            Trainee trainee2 = traineeService.getById(2).get();
            trainee2.setLastName("Schmidt");
            if (traineeService.update(trainee2).isEmpty()) {
                log.error("could not update trainee2");
            } else {
                log.info(traineeService.getById(2).get().toString());
            }
        }
        log.info("\tend of trainee id=2 \n");
    }
}
