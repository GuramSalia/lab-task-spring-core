package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.utils.Authentication;
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
import java.util.Set;

@Slf4j
@Service
public class TraineeService {

    private final Map<String, TraineeDAO> traineeDAOMap;
    @Setter
    private Authentication authentication;
    @Setter
    private UserService userService;
    private final UsernameGenerator usernameGenerator;
    @Setter
    private TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(Map<String, TraineeDAO> traineeDAOMap, Authentication authentication,
                          UserService userService, UsernameGenerator usernameGenerator) {
        this.traineeDAOMap = traineeDAOMap;
        this.authentication = authentication;
        this.userService = userService;
        this.usernameGenerator = usernameGenerator;
        log.info(">>>> TraineeService initialized");
    }

    public void setTraineeDAOFromTraineeDAOMap(String nameOfDao) {
        this.traineeDAO = traineeDAOMap.get(nameOfDao);
    }

    public Optional<Trainee> getById(int id, String username, String password) {
        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            return Optional.empty();
        }
        log.info(">>>> Getting trainee with id: " + id);
        return traineeDAO.getById(id);
    }

    @Transactional
    public Optional<Trainee> create(Trainee trainee) {
        log.info(">>>> training service create()");

        try {
            trainee.setUsername(usernameGenerator.generateUsername(trainee));
            if (trainee.getPassword() == null) {
                trainee.setPassword(RandomPasswordGenerator.generateRandomPassword());
            }
            log.info(">>>> Creating trainee with username: " + trainee.getUsername());

            if (userService.isInvalidUser(trainee)) {
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

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            return Optional.empty();
        }

        if (userService.isInvalidUser(trainee)) {
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

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            return Optional.empty();
        }

        Optional<Trainee> traineeOptional = traineeDAO.findByUsername(username);

        log.info(">>>> Getting trainee using getByUsername: : " + username);
        return traineeOptional;
    }

    @Transactional
    public Optional<Trainee> updatePassword(Trainee trainee, String username, String currentPassword, String
            newPassword) {

        if (!authentication.isAuthenticated(traineeDAO, username, currentPassword)) {
            return Optional.empty();
        }

        if (userService.isInvalidUser(trainee)) {
            return Optional.empty();
        }

        try {
            trainee.setPassword(newPassword);
            return traineeDAO.update(trainee);
        } catch (Exception e) {
            log.error("couldn't update password");
            return Optional.empty();
        }
    }

    @Transactional
    public boolean activateTrainee(Trainee trainee, String username, String password) {

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
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

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
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

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            return false;
        }

        Optional<Trainee> optionalTrainee = traineeDAO.findByUsername(username);
        if (optionalTrainee.isEmpty()) {
            log.error("wrong username or password");
            return false;
        }
        Trainee trainee = optionalTrainee.get();

        try {
            log.info(">>>> Deleting trainee with username: " + username);
//            traineeDAO.delete(trainee);
//            return true;
            return traineeDAO.delete(trainee);
        } catch (Exception e) {
            log.error("couldn't delete the trainee");
            return false;
        }
    }

    public List<Trainee> getTrainees() {
        log.info(">>>> Getting trainees");
        return traineeDAO.getTrainees();
    }

    @Transactional
    public Optional<Trainee> setTrainers(Trainee trainee, String username, String password, Set<Trainer> trainers) {

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            return Optional.empty();
        }

        if (userService.isInvalidUser(trainee)) {
            log.info("invalid user");
            return Optional.empty();
        }

        try {
            trainee.setTrainers(trainers);
            return traineeDAO.update(trainee);
        } catch (Exception e) {
            log.error("couldn't update trainers");
            return Optional.empty();
        }
    }

    public Optional<Trainee> findByUsernameWithQuery(String username, String password) {
        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            return Optional.empty();
        }
        return traineeDAO.findByUsernameWithQuery(username);
    }

    public Optional<Trainee> findByUsernameAndPassword(String username, String password) {
        return traineeDAO.findByUsernameAndPassword(username, password);
    }
}
