package com.epam.labtaskspringcore;

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

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import static java.util.Calendar.*;
import static java.util.Calendar.DATE;

@Slf4j
@SpringBootApplication
public class LabTaskSpringCoreApplication {
    public static void main(String[] args) {

        SpringApplication.run(LabTaskSpringCoreApplication.class, args);

        InMemoryStorage inMemoryStorage = BeanProvider.getInMemoryStorage();
        TraineeService traineeServiceWithInMemoryDao = BeanProvider.getTraineeService("IN_MEMORY");
        TrainerService trainerService = BeanProvider.getTrainerService();
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

        log.info("   .....   training 3 : should not create ");

        Training training3 = trainingService.createTraining(3, traineeServiceWithInMemoryDao.getById(4).get(),
                                                            trainer5, "personal training", PERSONAL, 30);
        trainingService.logTrainingCreationDetails(training3);
        log.info("   -----   end of training 3 \n");

        log.info("   .....   training 4 ");
        Training training4 = trainingService.createTraining(4, traineeServiceWithInMemoryDao.getById(3).get(),
                                                            trainer5, "Yoga training", YOGA, 30);
        trainingService.logTrainingCreationDetails(training4);
        log.info("   -----   end of training 4 \n");

        log.info("   .....   update trainee userId=4");
        traineeServiceWithInMemoryDao.logLastNameUpdateOfTrainee(4, "Schmidt");
        log.info("   -----   end of trainee userId=4 \n");

        log.info(" ....... task2 related part starts here  .......\n");

        TraineeService traineeServiceWithDatabaseDao = BeanProvider.getTraineeService("DATABASE");
//        TrainerService trainerServiceWithDatabaseDao = BeanProvider.getTrainerService("");
//        TrainingService trainingServiceWithDatabaseDao = BeanProvider.getTrainingService("");

//        Optional<Trainer> optionalTrainer1 = trainerServiceWithDatabaseDao.findById(1);
//        Optional<Trainer> optionalTrainer2 = trainerServiceWithDatabaseDao.findById(2);
//        Optional<Training> optionalTraining1 = trainingServiceWithDatabaseDao.findById(1);
//        Optional<Training> optionalTraining2 = trainingServiceWithDatabaseDao.findById(2);
        Optional<Trainee> optionalTrainee1 = traineeServiceWithDatabaseDao.getById(3);
        Optional<Trainee> optionalTrainee2 = traineeServiceWithDatabaseDao.getById(4);

//        optionalTrainer1.ifPresent(trainer -> log.info(trainer.toString()));
//        optionalTrainer2.ifPresent(trainer -> log.info(trainer.toString()));
//        optionalTraining1.ifPresent(training -> log.info(training.toString()));
//        optionalTraining2.ifPresent(training -> log.info(training.toString()));
        optionalTrainee1.ifPresent(trainee -> log.info(trainee.toString()));
        optionalTrainee2.ifPresent(trainee -> log.info(trainee.toString()));


        // stopped here: continue when other services finished

//        Trainee trainee_3 = new Trainee();
//        trainee_3.setFirstName("Dave");
//        trainee_3.setLastName("Miller");
//        Calendar cal = Calendar.getInstance();
//        cal.set(YEAR, 1990);
//        cal.set(MONTH, JULY);
//        cal.set(DATE, 5);
//        trainee_3.setDob(cal.getTime());
//        trainee_3.setAddress("123 Main St");
//        trainee_3.setIsActive(true);
//        trainee_3.setPassword("123456");
//        trainee_3.setUsername("Dave.Miller");
//        trainee_3.setTrainers(Set.of(optionalTrainer1.get(), optionalTrainer2.get()));
//
//        Optional<Trainee> trainee3SavedOptional = traineeServiceWithDatabaseDao.create(trainee_3);
//        trainee3SavedOptional.ifPresent(trainee -> log.info(trainee.getFirstName() + " saved"));
//
//        log.info("trainee_3 found by id of 5 :" + traineeServiceWithDatabaseDao.findById(5).toString());
//
//        Trainee trainee_4 = new Trainee();
//        trainee_4.setFirstName("Maria");
//        trainee_4.setLastName("Li");
//        trainee_4.setDobEasy(1980, 0, 25);
//        trainee_4.setAddress("55123 Main St");
//        trainee_4.setIsActive(true);
//        Optional<Trainee> trainee4SavedOptional = traineeServiceWithDatabaseDao.create(trainee_4);
//        trainee4SavedOptional.ifPresent(trainee -> log.info(trainee.toString()));
//
//        log.info("update trainee_3:");
//        Trainee trainee_3Saved = trainee3SavedOptional.get();
//        trainee_3Saved.setFirstName("John");
//        trainee_3Saved.setLastName("Doe");
//        trainee_3Saved.setPassword("1122");
//        trainee3SavedOptional = traineeServiceWithDatabaseDao.update(trainee_3Saved, trainee_3Saved.getUsername(),
        //        "123456");
//        trainee3SavedOptional.ifPresent(trainee -> log.info("Updated trainee_3: " + trainee.toString()));
//
//        log.info("getByUsername trainee_3 :");
//        trainee3SavedOptional = traineeServiceWithDatabaseDao.getByUsername("John.Doe1", "1122");
//        trainee3SavedOptional.ifPresent(trainee -> log.info(trainee.toString()));
//
//        log.info("updatePassword trainee_3 :");
//
//        trainee3SavedOptional = traineeServiceWithDatabaseDao.updatePassword(trainee_3, trainee_3.getUsername(),
        //        "1122",
        //        "2233");
//
//        log.info("activateActiveTrainee trainee_3: ");
//        boolean activatedActive = traineeServiceWithDatabaseDao.activateTrainee(trainee3SavedOptional.get(),
        //        trainee3SavedOptional.get()
//                                                                                                                       .getUsername(), "2233");
//        log.info("activateActiveTrainee trainee_3: " + activatedActive);
//
//        log.info("deactivateActiveTrainee trainee_3:");
//        boolean deactivatedActive = traineeServiceWithDatabaseDao.deactivateTrainee(trainee3SavedOptional.get(),
        //        trainee3SavedOptional.get()
//                                                                                                                           .getUsername(), "2233");
//        log.info("deactivateActiveTrainee trainee_3: " + deactivatedActive);
//
//        log.info("deactivateInactiveTrainee trainee_3:");
//        boolean deactivatedInactive = traineeServiceWithDatabaseDao.deactivateTrainee(trainee3SavedOptional.get(),
        //        trainee3SavedOptional.get()
//                                                                                                                             .getUsername(), "2233");
//        log.info("deactivateInactiveCustome trainee_3: " + deactivatedInactive);
//
//        log.info("activateInactiveTrainee trainee_3:");
//        boolean activatedInactive = traineeServiceWithDatabaseDao.activateTrainee(trainee3SavedOptional.get(),
        //        trainee3SavedOptional.get()
//                                                                                                                         .getUsername(), "2233");
//        log.info("deactivateInactiveTrainee trainee_3: " + activatedInactive);
//
//        log.info("delete trainee_1:");
//        Trainee trainee = new Trainee();
//        trainee.setFirstName("Lucy");
//        trainee.setLastName("Rodriguez");
//        trainee.setDobEasy(1980, 0, 25);
//        trainee.setAddress("55123 Main St");
//        trainee.setIsActive(true);
//        trainee.setPassword("111");
//        traineeServiceWithDatabaseDao.create(trainee);
//        log.info("Created trainee with id " + trainee.getUserId());
//        traineeServiceWithDatabaseDao.delete(trainee.getUsername(), "111");
//
//        // ----------------------------------------------------------------
//
//        log.info("create trainer");
//        log.info("update trainer");
//        log.info("updated trainer password");
//        log.info("activate active trainer");
//        log.info("deactivate active trainer");
//        log.info("deactivate inactive trainer");
//        log.info("activate inactive trainer");
//
//        log.info("create training with trainer of the same training type");
//        log.info("create training with trainer of the different training type");
//
//        // ----------------------------------------------------------------
//
//        TrainingType PILATES = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.PILATES);
//        TrainingType STRENGTH = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.STRENGTH);
//        TrainingType GROUP = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.GROUP);
//        TrainingType CARDIO = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.CARDIO);
//        TrainingType HIIT = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.HIIT);
//        TrainingType PERSONAL = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.PERSONAL);
//        TrainingType YOGA = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.YOGA);
//
//        log.info("create trainer");
//        Trainer trainer_3 = new Trainer();
//        trainer_3.setFirstName("Olivia");
//        trainer_3.setLastName("Bruno");
//        trainer_3.setPassword("123");
//        trainer_3.setIsActive(true);
//        trainer_3.setSpecialization(PILATES);
//        trainerServiceWithDatabaseDao.create(trainer_3);
//
//        log.info("update trainer: new specification YOGA");
//        trainer_3.setSpecialization(YOGA);
//        trainer_3 = trainerServiceWithDatabaseDao.update(trainer_3, trainer_3.getUsername(), "123").get();
//        log.info("updated trainer: " + trainer_3 + " with new specialization: " + trainer_3.getSpecialization());
//
//        log.info("updated trainer password: new password '321'");
//        trainer_3 = trainerServiceWithDatabaseDao.updatePassword(trainer_3, trainer_3.getUsername(), "123", "321").get();
//        log.info("updated trainer: " + trainer_3 + " with new password: " + trainer_3.getPassword());
//
//        log.info("activate active trainer");
//        boolean result = trainerServiceWithDatabaseDao.activateTrainer(trainer_3, trainer_3.getUsername(), "321");
//        log.info("activate active trainer: " + result);
//
//        log.info("deactivate active trainer");
//        boolean result2 = trainerServiceWithDatabaseDao.deactivateTrainer(trainer_3, trainer_3.getUsername(), "321");
//        log.info("deactivate active trainer: " + result2);
//
//        log.info("deactivate inactive trainer");
//        boolean result3 = trainerServiceWithDatabaseDao.deactivateTrainer(trainer_3, trainer_3.getUsername(), "321");
//        log.info("deactivate inactive trainer: " + result3);
//
//        log.info("activate inactive trainer");
//        boolean result4 = trainerServiceWithDatabaseDao.activateTrainer(trainer_3, trainer_3.getUsername(), "321");
//        log.info("deactivate inactive trainer: " + result4);
//
//        log.info("create training with trainer of the same different type");
//
//        trainee_3 = traineeServiceWithDatabaseDao.getByUsername("John.Doe1", "2233").get();
//        Training training_3 = new Training();
//        training_3.setTrainingDateEasy(2024, 5, 24);
//        training_3.setTrainingName("training number 3");
//        training_3.setTrainingDurationInMinutes(90);
//        training_3.setTrainer(trainer_3);
//        training_3.setTrainee(trainee_3);
//        training_3.setTrainingType(PILATES);
//        Optional<Training> training3_Optional = trainingServiceWithDatabaseDao.create(training_3);
//        if (training3_Optional.isEmpty()) {
//            log.info(">>>>> training not created... trainer type: " + trainer_3.getSpecialization() + " | training " +
//                             "type: " + training_3.getTrainingType());
//        } else {
//            log.error("----- training created for different types. trainer type: " + trainer_3.getSpecialization() +
//                              " | " + "training type: " + training_3.getTrainingType());
//        }
//
//        log.info("create training with trainer of the different training type");
//        Training training_4 = new Training();
//        training_4.setTrainingDateEasy(2024, 5, 25);
//        training_4.setTrainingName("training number 4");
//        training_4.setTrainingDurationInMinutes(90);
//        training_4.setTrainer(trainer_3);
//        training_4.setTrainee(trainee_3);
//        training_4.setTrainingType(YOGA);
//        Optional<Training> training4_Optional = trainingServiceWithDatabaseDao.create(training_4);
//
//
//        Optional<Training> optional = trainingServiceWithDatabaseDao.create(training_4);
//        if (optional.isEmpty()) {
//            log.error("????? training not created for same types. trainer type: " + trainer_3.getSpecialization() +
//                              " | " +
//                              "training type: " + training_4.getTrainingType());
//        } else {
//            log.info(">>>>> training created ... trainer type: " + trainer_3.getSpecialization() + " | " +
//                             "training type: " + training_4.getTrainingType());
//        }
//
//        //        log.info("create training with trainer of the different training type");
//
//
//
//        log.info("deleting trainee cascades to deleting training");
//        traineeServiceWithDatabaseDao.delete("John.Doe1", "2233");
//
//
//        log.info("\n\n>>>> END  ==============\n");

    }
}
