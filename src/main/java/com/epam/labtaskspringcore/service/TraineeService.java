package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import jakarta.transaction.Transactional;
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

    private final Map<String, TraineeDAO> traineeDAOMap;

    private final UsernameGenerator usernameGenerator;
    @Setter
    private TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(Map<String, TraineeDAO> traineeDAOMap, UsernameGenerator usernameGenerator) {
        this.traineeDAOMap = traineeDAOMap;
        this.usernameGenerator = usernameGenerator;
        log.info(">>>> TraineeService initialized");
    }

    public void setTraineeDAOFromTraineeDAOMap(String nameOfDao) {
        this.traineeDAO = traineeDAOMap.get(nameOfDao);
    }

    public Optional<Trainee> getById(int id) {
        log.info(">>>> Getting trainee with id: " + id);
        return traineeDAO.getById(id);
    }

    //    public Optional<Trainee> create(Trainee trainee) {
    //        trainee.setUsername(usernameGenerator.generateUsername(trainee));
    //        trainee.setPassword(RandomPasswordGenerator.generateRandomPassword());
    //        log.info(">>>> Creating trainee with username: " + trainee.getUsername());
    //        return traineeDAO.create(trainee);
    //    }

    @Transactional
    public Optional<Trainee> create(Trainee trainee) {
        log.info(">>>> training service create()");

        try {
            trainee.setUsername(usernameGenerator.generateUsername(trainee));
            if (trainee.getPassword() == null) {
                trainee.setPassword(RandomPasswordGenerator.generateRandomPassword());
            }
            log.info(">>>> Creating trainee with username: " + trainee.getUsername());

            if (UserService.isInvalidUser(trainee)) {
                log.error("invalid trainee");
                return Optional.empty();
            }

            return traineeDAO.create(trainee);
        } catch (Exception e) {
            log.error("error creating trainee", e);
            return Optional.empty();
        }
    }

    // InMemory implementation doesn't require 3 arguments
    public Optional<Trainee> update(Trainee trainee) {
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        return traineeDAO.update(trainee);
    }

    // InMemory implementation doesn't require 3 arguments
    public boolean delete(Trainee trainee) {
        log.info(">>>> Deleting trainee with id: " + trainee.getUserId());
        return traineeDAO.delete(trainee);
    }

    @Transactional
    public Optional<Trainee> update(Trainee trainee, String username, String password) {

        if (isInvalidUsernamePassword(trainee, username, password)) {
            log.error("invalid username or password");
            return Optional.empty();
        }

        if (UserService.isInvalidUser(trainee)) {
            log.info("invalid user");
            return Optional.empty();
        }
        try {
            trainee.setUsername(usernameGenerator.generateUsername(trainee));
            log.info(">>>> Updating trainee with username: " + trainee.getUsername());
            return traineeDAO.update(trainee);
        } catch (Exception e) {
            log.error("error updating trainee", e);
            return Optional.empty();
        }
    }




        public Optional<Trainee> getByUsername(String username, String password) {

            Optional<Trainee> traineeOptional = traineeDAO.findByUsername(username);
            if (traineeOptional.isEmpty()) {
                log.error("invalid username or password");
                return Optional.empty();
            }

            Trainee trainee = traineeOptional.get();
            if (isInvalidUsernamePassword(trainee, username, trainee.getPassword())) {
                log.error("invalid username or password");
                return Optional.empty();
            }

            log.info(">>>> Getting trainee using getByUsername: : " + username);
            return traineeOptional;
        }

        @Transactional
        public Optional<Trainee> updatePassword(Trainee trainee, String username, String currentPassword, String
        newPassword) {

            if (isInvalidUsernamePassword(trainee, username, currentPassword)) {
                log.error("invalid username or password");
                return Optional.empty();
            }

            if (UserService.isInvalidUser(trainee)) {
                return Optional.empty();
            }

            try {
                trainee.setPassword(newPassword);
                return traineeDAO.update(trainee);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        @Transactional
        public boolean activateTrainee(Trainee trainee, String username, String password) {

            if (isInvalidUsernamePassword(trainee, username, password)) {
                log.error("invalid username or password");
                return false;
            }

            if (trainee.getIsActive()) {
                log.info("trainee is already active");
                return false;
            }

            try {
                trainee.setIsActive(true);
                traineeDAO.update(trainee);
                log.info("trainee is active");
                return true;
            } catch (Exception e) {
                log.error("couldn't activate the trainee", e);
                return false;
            }
        }

        @Transactional
        public boolean deactivateTrainee(Trainee trainee, String username, String password) {

            if (isInvalidUsernamePassword(trainee, username, password)) {
                log.error("invalid username or password");
                return false;
            }

            if (!trainee.getIsActive()) {
                log.info("trainee is already deactivated");
                return false;
            }

            try {
                trainee.setIsActive(false);
                traineeDAO.update(trainee);
                log.info("trainee is deactivated");
                return true;
            } catch (Exception e) {
                log.error("couldn't deactivate the trainee");
                return false;
            }
        }

        @Transactional
        public boolean delete(String username, String password) {
            Optional<Trainee> optionalTrainee = getByUsername(username, password);
            if (optionalTrainee.isEmpty()) {
                log.error("wrong username or password");
                return false;
            }
            Trainee trainee = optionalTrainee.get();
            if (isInvalidUsernamePassword(trainee, username, password)) {
                log.error("wrong username or password");
                return false;
            }
            try {
                log.info(">>>> Deleting trainee with username: " + username);
                traineeDAO.delete(trainee);
                return true;
            } catch (Exception e) {
                log.error("couldn't delete the trainee");
                return false;
            }
        }

    public List<Trainee> getTrainees() {
        log.info(">>>> Getting trainees");
        return traineeDAO.getTrainees();
    }

    private boolean isValidUsernamePassword(Trainee trainee, String username, String password) {
        Trainee traineeInDb;
        try {
            Optional<Trainee> traineeOptional = traineeDAO.findByUsernameAndPassword(username, password);
            if (traineeOptional.isEmpty()) {
                log.error("Could not find the trainee");
                return false;
            }
            traineeInDb = traineeOptional.get();
        } catch (Exception e) {
            log.error("something went wrong", e);
            return false;
        }
        return traineeInDb.equals(trainee);
    }

    private boolean isInvalidUsernamePassword(Trainee trainee, String username, String password) {
        return !isValidUsernamePassword(trainee, username, password);
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
