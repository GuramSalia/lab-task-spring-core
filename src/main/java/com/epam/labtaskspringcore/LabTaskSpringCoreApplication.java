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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabTaskSpringCoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(LabTaskSpringCoreApplication.class, args);

        InMemoryStorage inMemoryStorage = ApplicationContextProvider.getContext().getBean(InMemoryStorage.class);

        System.out.println("\n\t ==============  START  ==============\n");
        inMemoryStorage.getTrainees().values().stream().forEach(System.out::println);
        System.out.println("\n .... ... \n");
        inMemoryStorage.getTrainers().values().stream().forEach(System.out::println);
        System.out.println("\n .... ... \n");
        inMemoryStorage.getTrainings().values().stream().forEach(System.out::println);
        System.out.println("\n .... ... \n");

        TrainerService trainerService = ApplicationContextProvider.getContext().getBean(TrainerService.class);
        TraineeService traineeService = ApplicationContextProvider.getContext().getBean(TraineeService.class);
        TrainingService trainingService = ApplicationContextProvider.getContext().getBean(TrainingService.class);

        System.out.println("\n \ttrainer 3 ");
        Trainer trainer3 = new Trainer();
        trainer3.setId(3);
        trainer3.setFirstName("John");
        trainer3.setLastName("Doe");
        trainer3.setActive(true);
        trainer3.setSpecialization(TrainingType.YOGA);
        System.out.println(trainerService.create(trainer3));
        System.out.println("\t end of trainer 3 \n");

        System.out.println("\n \ttrainer 4 ");
        Trainer trainer4 = new Trainer();
        trainer4.setId(4);
        trainer4.setFirstName("John");
        trainer4.setLastName("Doe");
        trainer4.setActive(true);
        trainer4.setSpecialization(TrainingType.YOGA);
        System.out.println(trainerService.create(trainer4));
        System.out.println("\t end of trainer 4 \n");

        System.out.println("\n \ttraining 3 ");
        Training training3 = new Training();
        training3.setId(3);
        training3.setTraineeId(2);
        training3.setTrainerId(3);
        training3.setName("personal training");
        training3.setType(TrainingType.PERSONAL);
        training3.setDurationInMinutes(30);
        System.out.println(trainingService.create(training3));
        System.out.println("\tend of training 3 \n");


        System.out.println("\n \ttraining 4 ");
        Training training4 = new Training();
        training4.setId(4);
        training4.setTraineeId(4);
        training4.setTrainerId(3);
        training4.setName("Yoga training");
        training4.setType(TrainingType.YOGA);
        training4.setDurationInMinutes(30);
        System.out.println(trainingService.create(training4));
        System.out.println("\tend of training 4 \n");


        // updated trainee with id=2
        System.out.println("\n \ttrainee id=2");
        Trainee trainee2 = traineeService.getById(2);
        trainee2.setLastName("Schmidt");
        System.out.println(traineeService.update(trainee2));
        System.out.println("\tend of trainee id=2 \n");

    }
}
