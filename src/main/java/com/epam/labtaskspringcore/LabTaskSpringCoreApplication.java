package com.epam.labtaskspringcore;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.service.TrainingService;
import com.epam.labtaskspringcore.service.TrainingTypeService;
import com.epam.labtaskspringcore.utils.BeanProvider;
import com.epam.labtaskspringcore.utils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@Slf4j
@SpringBootApplication
public class LabTaskSpringCoreApplication {
    public static void main(String[] args) {

        SpringApplication.run(LabTaskSpringCoreApplication.class, args);

        TrainingTypeService trainingTypeService = BeanProvider.getTrainingTypeService();
        TrainingType PILATES = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.PILATES);
        TrainingType YOGA = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.YOGA);

        log.info(" ....... TASK-2 RELATED PART STARTS HERE  .......\n\n");

        TraineeService traineeServiceWithDatabaseDao = BeanProvider
                .getTraineeService("TRAINEE_DATABASE");
        TrainerService trainerServiceWithDatabaseDao = BeanProvider
                .getTrainerService("TRAINER_DATABASE", "TRAINEE_DATABASE");
        TrainingService trainingServiceWithDatabaseDao = BeanProvider
                .getTrainingService("TRAINING_DATABASE", "TRAINER_DATABASE");

        Optional<Trainer> optionalTrainer1 = trainerServiceWithDatabaseDao.getById(1, "Tim.Smith", "123");
        Optional<Trainer> optionalTrainer2 = trainerServiceWithDatabaseDao.getById(2, "Sam.Jones", "123");
        Optional<Training> optionalTraining1 = trainingServiceWithDatabaseDao.getById(1);
        Optional<Training> optionalTraining2 = trainingServiceWithDatabaseDao.getById(2);
        Optional<Trainee> optionalTrainee1 = traineeServiceWithDatabaseDao.getById(3, "John.Doe", "123");
        Optional<Trainee> optionalTrainee2 = traineeServiceWithDatabaseDao.getById(4, "Jane.Smith", "123");

        log.info("list of TRAINEES from DB: \n");
        optionalTrainee1.ifPresent(trainee -> log.info(trainee.toString()));
        optionalTrainee2.ifPresent(trainee -> log.info(trainee.toString()));
        log.info("list of TRAINERS from DB: \n");
        optionalTrainer1.ifPresent(trainer -> log.info(trainer.toString()));
        optionalTrainer2.ifPresent(trainer -> log.info(trainer.toString()));
        log.info("list of TRAINEES from DB: \n");
        optionalTraining1.ifPresent(training -> log.info(training.toString()));
        optionalTraining2.ifPresent(training -> log.info(training.toString()));

        log.info("   .....   With DB - create trainee 3");
        Trainee trainee_3 = new Trainee();
        Helper.setUpTrainee_3(trainee_3);
        if (optionalTrainer1.isPresent() && optionalTrainer2.isPresent()) {
            trainee_3.setTrainers(Set.of(optionalTrainer1.get(), optionalTrainer2.get()));
        } else {
            log.info("optionalTrainer1 or optionalTrainer2 is not present");
        }
        Optional<Trainee> trainee3SavedOptional = traineeServiceWithDatabaseDao.create(trainee_3);
        trainee3SavedOptional.ifPresent(trainee -> log.info(trainee.getFirstName() + " saved"));
        String traineeDave = traineeServiceWithDatabaseDao.getById(5, "Dave.Miller", "123456").toString();
        log.info("trainee_3 found by id of 5 :" + traineeDave);
        log.info("   -----   end  \n");

        log.info("   .....   With DB - create trainee 4");
        Trainee trainee_4 = new Trainee();
        Helper.setUpTrainee_4(trainee_4);
        Optional<Trainee> trainee4SavedOptional = traineeServiceWithDatabaseDao.create(trainee_4);
        trainee4SavedOptional.ifPresent(trainee -> log.info(trainee.toString()));
        log.info("   -----   end  \n");

        log.info("   .....   With DB - update trainee_3");
        if (trainee3SavedOptional.isPresent()) {
            Trainee trainee_3Saved = trainee3SavedOptional.get();
            Helper.updateTrainee_3Saved(trainee_3Saved);
            trainee3SavedOptional = traineeServiceWithDatabaseDao.update(
                    trainee_3Saved, trainee_3Saved.getUsername(), "123456");
            trainee3SavedOptional.ifPresent(trainee -> log.info("Updated trainee_3: " + trainee));
        } else {
            log.error("trainee3SavedOptional is empty");
        }

        log.info("   -----   end  \n");

        log.info("   .....   With DB - getByUsername trainee_3");
        trainee3SavedOptional = traineeServiceWithDatabaseDao.getByUsername("John.Doe1", "1122");
        trainee3SavedOptional.ifPresent(trainee -> log.info(trainee.toString()));
        log.info("   -----   end  \n");

        log.info("   .....   With DB - updatePassword trainee_3");
        trainee3SavedOptional = traineeServiceWithDatabaseDao
                .updatePassword(trainee_3, trainee_3.getUsername(), "1122", "2233");
        log.info("   -----   end  \n");

        log.info("   .....   With DB - activateActiveTrainee trainee_3");
        log.info("activateActiveTrainee trainee_3: ");
        if (trainee3SavedOptional.isPresent()) {
            boolean activatedActive = traineeServiceWithDatabaseDao
                    .activateTrainee(trainee3SavedOptional.get(), trainee3SavedOptional.get().getUsername(), "2233");
            log.info("activateActiveTrainee trainee_3: " + activatedActive);
        } else {
            log.error("trainee3SavedOptional is empty");
        }

        log.info("deactivateActiveTrainee trainee_3:");
        if (trainee3SavedOptional.isPresent()) {
            boolean deactivatedActive = traineeServiceWithDatabaseDao
                    .deactivateTrainee(trainee3SavedOptional.get(), trainee3SavedOptional.get().getUsername(), "2233");
            log.info("deactivateActiveTrainee trainee_3: " + deactivatedActive);
        } else {
            log.error("trainee3SavedOptional is empty");
        }

        log.info("deactivateInactiveTrainee trainee_3:");
        if (trainee3SavedOptional.isPresent()) {
            boolean deactivatedInactive = traineeServiceWithDatabaseDao
                    .deactivateTrainee(trainee3SavedOptional.get(), trainee3SavedOptional.get().getUsername(), "2233");
            log.info("deactivateInactiveTrainee trainee_3: " + deactivatedInactive);
        } else {
            log.error("trainee3SavedOptional is empty");
        }

        log.info("activateInactiveTrainee trainee_3:");
        if (trainee3SavedOptional.isPresent()) {
            boolean activatedInactive = traineeServiceWithDatabaseDao
                    .activateTrainee(trainee3SavedOptional.get(), trainee3SavedOptional.get().getUsername(), "2233");
            log.info("activateInactiveTrainee trainee_3: " + activatedInactive);
        } else {
            log.error("trainee3SavedOptional is empty");
        }

        log.info("   .....   With DB - delete trainee_Lucy");
        Trainee lucy = new Trainee();
        Helper.createLucy(lucy);
        traineeServiceWithDatabaseDao.create(lucy);
        log.info("Created trainee with id " + lucy.getUserId());
        traineeServiceWithDatabaseDao.delete(lucy.getUsername(), "111");

        log.info("   .....   With DB - create trainer Olivia");
        Trainer olivia = new Trainer();
        Helper.updateOlivia(olivia);
        olivia.setSpecialization(PILATES);
        trainerServiceWithDatabaseDao.create(olivia);

        log.info(" .....   With DB - update trainer Olivia, new specification YOGA");
        olivia.setSpecialization(YOGA);
        Optional<Trainer> oliviaOptional = trainerServiceWithDatabaseDao.update(olivia, olivia.getUsername(), "123");
        if (oliviaOptional.isPresent()) {
            olivia = oliviaOptional.get();
            log.info("updated trainer: " + olivia + " with new specialization: " + olivia.getSpecialization());
        }

        log.info("updated trainer password: new password '321'");
        oliviaOptional = trainerServiceWithDatabaseDao
                .updatePassword(olivia, olivia.getUsername(), "123", "321");
        if (oliviaOptional.isPresent()) {
            olivia = oliviaOptional.get();
            log.info("updated trainer: " + olivia + " with new password: " + olivia.getPassword());
        }

        log.info("activate active trainer");
        boolean result = trainerServiceWithDatabaseDao
                .activateTrainer(olivia, olivia.getUsername(), "321");
        log.info("activate active trainer: " + result);

        log.info("deactivate active trainer");
        boolean result2 = trainerServiceWithDatabaseDao
                .deactivateTrainer(olivia, olivia.getUsername(), "321");
        log.info("deactivate active trainer: " + result2);

        log.info("deactivate inactive trainer");
        boolean result3 = trainerServiceWithDatabaseDao
                .deactivateTrainer(olivia, olivia.getUsername(), "321");
        log.info("deactivate inactive trainer: " + result3);

        log.info("activate inactive trainer");
        boolean result4 = trainerServiceWithDatabaseDao
                .activateTrainer(olivia, olivia.getUsername(), "321");
        log.info("deactivate inactive trainer: " + result4);

        log.info("create training with trainer of the same type");

        Optional<Trainee> trainee_3Optional = traineeServiceWithDatabaseDao.getByUsername("John.Doe1", "2233");
        if (trainee_3Optional.isPresent()) {
            trainee_3 = trainee_3Optional.get();
        }

        Training training_3 = new Training();
        Helper.setUpTraining_3(training_3, olivia, trainee_3);
        training_3.setTrainingType(PILATES);
        Optional<Training> training3_Optional = trainingServiceWithDatabaseDao.create(training_3);
        Helper.logResultOfTraing3Creation(training3_Optional, olivia, training_3);

        log.info("create training with trainer of the different training type");
        Training training_4 = new Training();
        Helper.setUpTraining_4(training_4, olivia, trainee_3);
        training_4.setTrainingType(YOGA);
        trainingServiceWithDatabaseDao.create(training_4);
        Optional<Training> optional = trainingServiceWithDatabaseDao.create(training_4);
        Helper.logResultOfTraing4Creation(optional, olivia, training_4);

        log.info("deleting trainee cascades to deleting training");
        traineeServiceWithDatabaseDao.delete("John.Doe1", "2233");

        log.info("\n\n>>>> START with JPQL  ==============\n");

        log.info("list of trainers:\n\n"
                         + trainerServiceWithDatabaseDao.findUnassignedTrainersByTraineeUsername("John.Doe", "123"));

        log.info("trainee with username:\n\n"
                         + traineeServiceWithDatabaseDao.findByUsernameWithQuery("John.Doe", "123"));

        String traineeUsername = "John.Doe";
        String trainerUsername = "Tim.Smith";
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.APRIL, 25);
        Date fromDate = cal.getTime();
        cal.set(2025, Calendar.APRIL, 25);
        Date toDate = cal.getTime();
        java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
        java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());

        log.info("trainee with username:  \n\n" + trainingServiceWithDatabaseDao.getTrainingsByTraineeAndOtherFilters(
                traineeUsername,
                sqlFromDate,
                sqlToDate,
                trainerUsername,
                "CARDIO"));

        log.info("trainer with username:  \n\n" + trainingServiceWithDatabaseDao.getTrainingsByTrainerAndOtherFilters(
                trainerUsername,
                sqlFromDate,
                sqlToDate,
                traineeUsername));

        // in db, I have trainers with USER_IDs 1 (Tim.Smith, 123), 2 (Sam.Jones, 123) and 8 (Olivia.Bruno, 321).
        // Trainee with id 4 (Jane.Smith, 123) has one trainer with USER_ID 1
        // get trainee with id 4 and set trainers containing trainers with USER_ID: 2 and 8
        Trainee jane = null;
        Trainer sam = null;
        olivia = null;

        Optional<Trainee> janeOptional = traineeServiceWithDatabaseDao.getByUsername("Jane.Smith", "123");
        if (janeOptional.isPresent()) {
            jane = janeOptional.get();
        }

        Optional<Trainer> samOptional = trainerServiceWithDatabaseDao.getByUsername("Sam.Jones", "123");
        if (samOptional.isPresent()) {
            sam = samOptional.get();
        }

        oliviaOptional = trainerServiceWithDatabaseDao.getByUsername("Olivia.Bruno", "321");
        if (oliviaOptional.isPresent()) {
            olivia = oliviaOptional.get();
        }

        log.info("jane: " + jane);
        log.info("sam: " + sam);
        log.info("olivia: " + olivia);

        Set<Trainer> trainers = new HashSet<>();
        trainers.add(sam);
        trainers.add(olivia);
        assert jane != null;
        jane.setTrainers(trainers);

        Optional<Trainee> updatedJaneOptional = traineeServiceWithDatabaseDao.update(jane, jane.getUsername(), "123");
        if (updatedJaneOptional.isPresent()) {
            jane = updatedJaneOptional.get();
            log.info("updated trainee: " + jane);
            log.info("updated trainee trainers: " + jane.getTrainers());
        }
        log.info("\n\n>>>> END  ==============\n\n");

        log.info(" ....... TASK-3 RELATED PART STARTS HERE  .......\n\n");

        Optional<Trainer> trainerTimOptional = trainerServiceWithDatabaseDao.findByUsernameAndPassword("Tim.Smith",
                                                                                                       "123");
        if (trainerTimOptional.isPresent()) {
            Trainer trainerTim = trainerTimOptional.get();
            log.info("trainerTim: " + trainerTim);

            trainerTim.setUsername("NEW.USERNAME");
            trainerTimOptional = trainerServiceWithDatabaseDao.update(trainerTim, "Tim.Smith",
                                                                      "123");
            if (trainerTimOptional.isPresent()) {
                trainerTim = trainerTimOptional.get();
                log.info("updated trainer: " + trainerTim);
            }
        }

        log.info("\n\n swagger at: 'http://localhost:8080/swagger-ui/index.html'\n\n");
        log.info("\n\n api-docs at: 'http://localhost:8080/v3/api-docs'");

        log.info("\n\n>>>> END of TASK-3 RELATED PART  ==============\n\n");


    }
}
