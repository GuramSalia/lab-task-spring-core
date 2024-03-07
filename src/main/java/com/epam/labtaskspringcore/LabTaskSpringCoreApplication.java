package com.epam.labtaskspringcore;

import com.epam.labtaskspringcore.config.InMemoryStorage;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Slf4j
@SpringBootApplication
public class LabTaskSpringCoreApplication {
    public static void main(String[] args) {

        SpringApplication.run(LabTaskSpringCoreApplication.class, args);

        InMemoryStorage inMemoryStorage = BeanProvider.getInMemoryStorage();
        TrainerService trainerService = BeanProvider.getTrainerService("TRAINER_IN_MEMORY");
        TraineeService traineeServiceWithInMemoryDao = BeanProvider
                .getTraineeService("TRAINEE_IN_MEMORY");
        TrainingService trainingService = BeanProvider
                .getTrainingService("TRAINING_IN_MEMORY", "TRAINER_IN_MEMORY");
        TrainingTypeService trainingTypeService = BeanProvider.getTrainingTypeService();

        TrainingType PILATES = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.PILATES);
        TrainingType STRENGTH = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.STRENGTH);
        TrainingType GROUP = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.GROUP);
        TrainingType CARDIO = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.CARDIO);
        TrainingType HIIT = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.HIIT);
        TrainingType PERSONAL = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.PERSONAL);
        TrainingType YOGA = trainingTypeService.getTrainingType(TrainingType.TrainingTypeEnum.YOGA);

        //        TrainingType YOGA = new TrainingType();
        //        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);
        //        TrainingType PERSONAL = new TrainingType();
        //        PERSONAL.setTrainingType(TrainingType.TrainingTypeEnum.PERSONAL);

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

        log.info(" ....... TASK-2 RELATED PART STARTS HERE  .......\n\n");

        TraineeService traineeServiceWithDatabaseDao = BeanProvider.getTraineeService("TRAINEE_DATABASE");
        TrainerService trainerServiceWithDatabaseDao = BeanProvider
                .getTrainerService("TRAINER_DATABASE");
        TrainingService trainingServiceWithDatabaseDao = BeanProvider
                .getTrainingService("TRAINING_DATABASE", "TRAINER_DATABASE");

        Optional<Trainer> optionalTrainer1 = trainerServiceWithDatabaseDao.getById(1);
        Optional<Trainer> optionalTrainer2 = trainerServiceWithDatabaseDao.getById(2);
        Optional<Training> optionalTraining1 = trainingServiceWithDatabaseDao.getById(1);
        Optional<Training> optionalTraining2 = trainingServiceWithDatabaseDao.getById(2);
        Optional<Trainee> optionalTrainee1 = traineeServiceWithDatabaseDao.getById(3);
        Optional<Trainee> optionalTrainee2 = traineeServiceWithDatabaseDao.getById(4);

        log.info("list of TRAINEES from DB: \n");
        optionalTrainee1.ifPresent(trainee -> log.info(trainee.toString()));
        optionalTrainee2.ifPresent(trainee -> log.info(trainee.toString()));
        log.info("list of TRAINERS from DB: \n");
        optionalTrainer1.ifPresent(trainer -> log.info(trainer.toString()));
        optionalTrainer2.ifPresent(trainer -> log.info(trainer.toString()));
        log.info("list of TRAINEES from DB: \n");
        optionalTraining1.ifPresent(training -> log.info(training.toString()));
        optionalTraining2.ifPresent(training -> log.info(training.toString()));

        // stopped here: continue when other services finished
        log.info("   .....   With DB - create trainee 3");
        Trainee trainee_3 = new Trainee();
        Helper.setUpTrainee_3(trainee_3);
        trainee_3.setTrainers(Set.of(optionalTrainer1.get(), optionalTrainer2.get()));
        Optional<Trainee> trainee3SavedOptional = traineeServiceWithDatabaseDao.create(trainee_3);
        trainee3SavedOptional.ifPresent(trainee -> log.info(trainee.getFirstName() + " saved"));
        log.info("trainee_3 found by id of 5 :" + traineeServiceWithDatabaseDao.getById(5).toString());
        log.info("   -----   end  \n");

        log.info("   .....   With DB - create trainee 4");
        Trainee trainee_4 = new Trainee();
        Helper.setUpTrainee_4(trainee_4);
        Optional<Trainee> trainee4SavedOptional = traineeServiceWithDatabaseDao.create(trainee_4);
        trainee4SavedOptional.ifPresent(trainee -> log.info(trainee.toString()));
        log.info("   -----   end  \n");

        log.info("   .....   With DB - update trainee_3");
        Trainee trainee_3Saved = trainee3SavedOptional.get();
        Helper.updateTrainee_3Saved(trainee_3Saved);
        trainee3SavedOptional = traineeServiceWithDatabaseDao.update(
                trainee_3Saved, trainee_3Saved.getUsername(), "123456");
        trainee3SavedOptional.ifPresent(trainee -> log.info("Updated trainee_3: " + trainee.toString()));
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
        boolean activatedActive = traineeServiceWithDatabaseDao
                .activateTrainee(trainee3SavedOptional.get(), trainee3SavedOptional.get().getUsername(), "2233");
        log.info("activateActiveTrainee trainee_3: " + activatedActive);

        log.info("deactivateActiveTrainee trainee_3:");
        boolean deactivatedActive = traineeServiceWithDatabaseDao
                .deactivateTrainee(trainee3SavedOptional.get(), trainee3SavedOptional.get().getUsername(), "2233");
        log.info("deactivateActiveTrainee trainee_3: " + deactivatedActive);

        log.info("deactivateInactiveTrainee trainee_3:");
        boolean deactivatedInactive = traineeServiceWithDatabaseDao
                .deactivateTrainee(trainee3SavedOptional.get(), trainee3SavedOptional.get().getUsername(), "2233");
        log.info("deactivateInactiveCustome trainee_3: " + deactivatedInactive);

        log.info("activateInactiveTrainee trainee_3:");
        boolean activatedInactive = traineeServiceWithDatabaseDao
                .activateTrainee(trainee3SavedOptional.get(), trainee3SavedOptional.get().getUsername(), "2233");
        log.info("deactivateInactiveTrainee trainee_3: " + activatedInactive);

        log.info("   .....   With DB - delete trainee_Lucy");
        Trainee lucy = new Trainee();
        Helper.createLucy(lucy);
        traineeServiceWithDatabaseDao.create(lucy);
        log.info("Created trainee with id " + lucy.getUserId());
        traineeServiceWithDatabaseDao.delete(lucy.getUsername(), "111");

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

        log.info("   .....   With DB - create trainer Olivia");
        Trainer olivia = new Trainer();
        Helper.updateOlivia(olivia);
        olivia.setSpecialization(PILATES);
        trainerServiceWithDatabaseDao.create(olivia);

        log.info(" .....   With DB - update trainer Olivia, new specification YOGA");
        olivia.setSpecialization(YOGA);
        olivia = trainerServiceWithDatabaseDao.update(olivia, olivia.getUsername(), "123").get();
        log.info("updated trainer: " + olivia + " with new specialization: " + olivia
                .getSpecialization());

        log.info("updated trainer password: new password '321'");
        olivia = trainerServiceWithDatabaseDao
                .updatePassword(olivia, olivia.getUsername(), "123", "321").get();
        log.info("updated trainer: " + olivia + " with new password: " + olivia.getPassword());

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

        log.info("create training with trainer of the same different type");

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
        Optional<Training> training4_Optional = trainingServiceWithDatabaseDao.create(training_4);
        Optional<Training> optional = trainingServiceWithDatabaseDao.create(training_4);
        Helper.logResultOfTraing4Creation(optional, olivia, training_4);

        log.info("deleting trainee cascades to deleting training");
        traineeServiceWithDatabaseDao.delete("John.Doe1", "2233");

        log.info("\n\n>>>> START with JPQL  ==============\n");

        log.info("list of trainers:  \n\n" + trainerServiceWithDatabaseDao.findUnassignedTrainersByTraineeUsername(
                "John.Doe"));
        log.info("trainee with username:  \n\n" + traineeServiceWithDatabaseDao.findByUsernameWithQuery("John.Doe"));

        String traineeUsername = "John.Doe";
        String trainerUsername = "Tim.Smith";
        Calendar cal = Calendar.getInstance();
        cal.set(2020,Calendar.APRIL, 25);
        Date fromDate = cal.getTime();
        cal.set(2025,Calendar.APRIL, 25);
        Date toDate = cal.getTime();
        java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
        java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
        TrainingType cardio = CARDIO;


        log.info("trainee with username:  \n\n" + trainingServiceWithDatabaseDao.getTrainingsByTraineeAndOtherFilters(
                traineeUsername, sqlFromDate, sqlToDate, trainerUsername, "CARDIO"));

        log.info("\n\n>>>> END  ==============\n");
    }
}
