package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainingDAO;
import com.epam.labtaskspringcore.exception.TrainingNotCreatedException;
import com.epam.labtaskspringcore.exception.TrainingNotFoundException;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.model.TrainingType;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TrainingService {
    private final Map<String, TrainingDAO> trainingDAOMap;
    private final Map<String, TrainerDAO> trainerDAOMap;

    @Autowired
    public TrainingService(
            Map<String, TrainingDAO> trainingDAOMap,
            Map<String, TrainerDAO> trainerDAOMap) {
        this.trainingDAOMap = trainingDAOMap;
        this.trainerDAOMap = trainerDAOMap;
    }

    @Setter
    @Autowired
    private TrainingDAO trainingDAO;

    @Setter
    @Autowired
    private TrainerDAO trainerDAO;

    public void setTrainingDAOFromTrainingDAOMap(String nameOfDao) {
        this.trainingDAO = trainingDAOMap.get(nameOfDao);
    }

    public void setTrainerDAOFromTrainerDAOMap(String nameOfDao) {
        this.trainerDAO = trainerDAOMap.get(nameOfDao);
    }

    public Training getById(int id) {
        log.info(">>>> Getting training with id: " + id);

        Optional<Training> trainingOptional = trainingDAO.getById(id);
        if (trainingOptional.isEmpty()) {
            log.error("Cannot get training with id: " + id);
            throw new TrainingNotFoundException("training not found");
        }
        return trainingOptional.get();
    }

    public List<Training> getTrainingList() {return trainingDAO.getTrainings();}

    @Transactional
    public Training create(Training training) {

        TrainingType trainingType = training.getTrainingType();
        TrainingType trainerSpecialization;
        Optional<Trainer> optionalTrainer = Optional.ofNullable(training.getTrainer());

        if (optionalTrainer.isEmpty()) {
            log.info("There is no such trainer as indicated by training");
            throw new TrainingNotCreatedException("no trainer indicated by training");
        }

        Optional<Trainer> trainerFromDb = trainerDAO.findByUsername(optionalTrainer.get().getUsername());
        if (trainerFromDb.isEmpty()) {
            log.error("Cannot create training, because the trainer does not exist");
            throw new TrainingNotCreatedException("There is no such trainer as indicated by training");
        }

        trainerSpecialization = optionalTrainer.get().getSpecialization();

        // Is no longer the requirement since TASK3-REST, have to create training with just a trainer.
        // maybe we should give the training the same trainer Training Type?

        if (areMismatchingTrainingTypes(trainingType, trainerSpecialization)) {
            log.error("cannot create training, because the trainer has a different specialization");
            throw new TrainingNotCreatedException("cannot create training, because the trainer has a different " +
                                                          "specialization");
        }

        Optional<Training> trainingOptional = trainingDAO.create(training);
        if (trainingOptional.isEmpty()) {
            log.error("Cannot create training");
            throw new TrainingNotCreatedException("Cannot create training");
        }

        log.info(">>>> Creating training: " + training.getTrainingName());
        return trainingOptional.get();
    }

    public List<Training> getTrainingsByTraineeAndOtherFilters(
            String traineeUsername,
            Date startDate,
            Date endDate,
            String trainerUsername,
            String trainingTypeName) {
        List<Training> trainingsByTrainee = trainingDAO.getTrainingsByTraineeAndOtherFilters(
                traineeUsername, startDate, endDate, trainerUsername, trainingTypeName);
        log.info("\n\n>>>> Getting trainings by trainee: " + trainingsByTrainee.toString());
        log.info("\n\n" + trainingDAO.getClass());
        return trainingsByTrainee;
    }

    public List<Training> getTrainingsByTrainerAndOtherFilters(
            String trainerUsername,
            Date startDate,
            Date endDate,
            String traineeUsername) {
        List<Training> trainingsByTrainer = trainingDAO.getTrainingsByTrainerAndOtherFilters(
                trainerUsername, startDate, endDate, traineeUsername);
        log.info("\n\n>>>> Getting trainings by trainer: " + trainingsByTrainer.toString());
        log.info("\n\n" + trainingDAO.getClass());
        log.info("\n\ntraining 1" + trainingDAO.getTrainings());
        return trainingsByTrainer;
    }

    private boolean areMismatchingTrainingTypes(TrainingType type1, TrainingType type2) {
        boolean matching = false;

        if (type1 != null && type2 != null) {
            matching = type1.equals(type2);
        }

        if (type1 == null && type2 == null) {
            matching = true;
        }
        return !matching;
    }
}
