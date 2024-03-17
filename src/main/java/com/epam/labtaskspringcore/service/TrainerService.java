package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.utils.Authentication;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TrainerService {
    private final Map<String, TrainerDAO> trainerDAOMap;
    private final Map<String, TraineeDAO> traineeDAOMap;
    @Setter
    private Authentication authentication;
    @Setter
    private UserService userService;

    private final UsernameGenerator usernameGenerator;

    @Setter
    private TrainerDAO trainerDAO;

    @Setter
    private TraineeDAO traineeDAO;

    @Autowired
    public TrainerService(
            Map<String, TrainerDAO> trainerDAOMap,
            Map<String, TraineeDAO> traineeDAOMap, Authentication authentication, UserService userService,
            UsernameGenerator usernameGenerator) {
        this.trainerDAOMap = trainerDAOMap;
        this.traineeDAOMap = traineeDAOMap;
        this.authentication = authentication;
        this.userService = userService;
        this.usernameGenerator = usernameGenerator;
    }

    public void setTrainerDAOFromTrainerDAOMap(String nameOfDao) {
        this.trainerDAO = trainerDAOMap.get(nameOfDao);
    }

    public void setTraineeDAOFromTraineeDAOMap(String nameOfDao) {
        this.traineeDAO = traineeDAOMap.get(nameOfDao);
    }

    public Optional<Trainer> getById(int id, String username, String password) {
        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            return Optional.empty();
        }
        log.info(">>>> Getting trainer with id: " + id);
        return trainerDAO.getById(id);
    }

    public List<Trainer> getTrainers() {
        log.info(">>>> Getting trainers");
        return trainerDAO.getTrainers();
    }

    @Transactional
    public Optional<Trainer> create(Trainer trainer) {

        try {
            trainer.setUsername(usernameGenerator.generateUsername(trainer));
            if (trainer.getPassword() == null) {
                trainer.setPassword(RandomPasswordGenerator.generateRandomPassword());
            }
            log.info(">>>> Creating trainer with username: " + trainer.getUsername());

            if (userService.isInvalidUser(trainer)) {
                log.error("invalid customer");
                return Optional.empty();
            }

            return trainerDAO.create(trainer);
        } catch (Exception e) {
            log.error("error creating customer", e);
            return Optional.empty();
        }
    }

    // InMemory implementation doesn't require 3 arguments
    public Optional<Trainer> update(Trainer trainer) {
        //changed due to new requirement: 7. Username cannot be changed
        //        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        return trainerDAO.update(trainer);
    }

    @Transactional
    public Optional<Trainer> update(Trainer trainer, String username, String password) {

        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            log.error("invalid username ^^^");
            return Optional.empty();
        }

        if (userService.isInvalidUser(trainer)) {
            log.info("invalid user :::");
            return Optional.empty();
        }

        try {
            //changed due to new requirement: 7. Username cannot be changed
            //            trainer.setUsername(usernameGenerator.generateUsername(trainer));
            log.info(">>>> Updating trainer with username: " + trainer.getUsername());
            return trainerDAO.update(trainer);
        } catch (Exception e) {
            log.error("error updating trainer", e);
            return Optional.empty();
        }
    }

    public Optional<Trainer> getByUsername(String username, String password) {

        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            return Optional.empty();
        }

        Optional<Trainer> trainerOptional = trainerDAO.findByUsername(username);
        if (trainerOptional.isEmpty()) {
            log.error("invalid username or password");
            return Optional.empty();
        }

        Trainer trainer = trainerOptional.get();

        log.info(">>>> Getting trainer using getByUsername: " + username);
        return trainerOptional;
    }

    public Optional<Trainer> getByUsername(String username) {

        Optional<Trainer> trainerOptional = trainerDAO.findByUsername(username);
        if (trainerOptional.isEmpty()) {
            log.error("invalid username or password");
            return Optional.empty();
        }

        Trainer trainer = trainerOptional.get();

        log.info(">>>> Getting trainer using getByUsername: " + username);
        return trainerOptional;
    }

    @Transactional
    public Optional<Trainer> updatePassword(Trainer trainer, String username, String currentPassword,
                                            String newPassword) {

        if (!authentication.isAuthenticated(trainerDAO, username, currentPassword)) {
            return Optional.empty();
        }

        if (userService.isInvalidUser(trainer)) {
            return Optional.empty();
        }

        try {
            trainer.setPassword(newPassword);
            return trainerDAO.update(trainer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean activateTrainer(Trainer trainer, String username, String password) {

        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            return false;
        }

        if (trainer.getIsActive()) {
            log.info("trainer is already active");
            return false;
        }

        try {
            trainer.setIsActive(true);
            trainerDAO.update(trainer);
            log.info("trainer is active");
            return true;
        } catch (Exception e) {
            log.error("couldn't activate the trainer", e);
            return false;
        }
    }

    @Transactional
    public boolean deactivateTrainer(Trainer trainer, String username, String password) {

        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            return false;
        }

        if (!trainer.getIsActive()) {
            log.info("trainer is already deactivated");
            return false;
        }

        try {
            trainer.setIsActive(false);
            trainerDAO.update(trainer);
            log.info("trainer is deactivated");
            return true;
        } catch (Exception e) {
            log.error("couldn't deactivate the trainer");
            return false;
        }
    }

    public List<Trainer> findUnassignedTrainersByTraineeUsername(String traineeUsername, String password) {

        if (!authentication.isAuthenticated(traineeDAO, traineeUsername, password)) {
            return null;
        }

        List<Integer> ids = trainerDAO.findUnassignedTrainersByTraineeUsername(traineeUsername);
        List<Trainer> trainers = new ArrayList<>();
        for (Integer id : ids) {
            Optional<Trainer> trainerOptional = trainerDAO.getById(id);
            trainerOptional.ifPresent(trainers::add);
        }

        return trainers;
    }

    public Optional<Trainer> findByUsernameAndPassword(String username, String password) {
        return trainerDAO.findByUsernameAndPassword(username, password);
    }
}
