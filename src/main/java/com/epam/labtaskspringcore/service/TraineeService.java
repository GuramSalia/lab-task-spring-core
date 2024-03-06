package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TraineeService {
    //    private final TraineeDAO traineeDAO;


    private final Map<String, TraineeDAO> traineeDAOs;

    private final UsernameGenerator usernameGenerator;
    @Setter
    private TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(Map<String, TraineeDAO> traineeDAOs, UsernameGenerator usernameGenerator) {
        this.traineeDAOs = traineeDAOs;
        this.usernameGenerator = usernameGenerator;
        log.info(">>>> TraineeService initialized");
    }

    public void setTraineeDAOFromTraineeDAOs(String nameOfDao) {
        this.traineeDAO = traineeDAOs.get(nameOfDao);
    }

    //    public TraineeService(TraineeDAO traineeDAO, UsernameGenerator usernameGenerator) {
    //        this.traineeDAO = traineeDAO;
    //        this.usernameGenerator = usernameGenerator;
    //        log.info(">>>> TraineeService initialized");
    //    }

    public Optional<Trainee> create(Trainee trainee) {
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        trainee.setPassword(RandomPasswordGenerator.generateRandomPassword());
        log.info(">>>> Creating trainee with username: " + trainee.getUsername());
        return traineeDAO.create(trainee);
    }

    public Optional<Trainee> update(Trainee trainee) {
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        return traineeDAO.update(trainee);
    }

    public boolean delete(Trainee trainee) {
        log.info(">>>> Deleting trainee with id: " + trainee.getUserId());
        return traineeDAO.delete(trainee);
    }

    public Optional<Trainee> getById(int id) {
        log.info(">>>> Getting trainee with id: " + id);
        return traineeDAO.getById(id);
    }

    public List<Trainee> getTrainees() {
        log.info(">>>> Getting trainees");
        return traineeDAO.getTrainees();
    }

    public void logLastNameUpdateOfTrainee(int traineeId, String traineeNewLastName) {
        if (getById(traineeId).isEmpty()) {
            log.error("trainee with userId=" + traineeId + " does not exists");
        } else {
            Trainee trainee = getById(traineeId).get();
            trainee.setLastName(traineeNewLastName);
            Optional<Trainee> traineeOptional = update(trainee);
            if (traineeOptional.isEmpty()) {
                log.error("could not update trainee with userId=" + traineeId);
            } else {
                log.info("Successfully updated" + getById(traineeId).get());
            }
        }
    }
}
