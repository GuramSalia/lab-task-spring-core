package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.exception.UnauthorizedException;
import com.epam.labtaskspringcore.exception.UserNotCreatedException;
import com.epam.labtaskspringcore.exception.UserNotFoundException;
import com.epam.labtaskspringcore.exception.UserNotUpdatedException;
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
    private UserValidatorService userValidatorService;
    private final UsernameGenerator usernameGenerator;

    @Setter
    @Autowired
    private TrainerDAO trainerDAO;

    @Setter
    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    public TrainerService(
            Map<String, TrainerDAO> trainerDAOMap,
            Map<String, TraineeDAO> traineeDAOMap,
            Authentication authentication,
            UserValidatorService userValidatorService,
            UsernameGenerator usernameGenerator) {
        this.trainerDAOMap = trainerDAOMap;
        this.traineeDAOMap = traineeDAOMap;
        this.authentication = authentication;
        this.userValidatorService = userValidatorService;
        this.usernameGenerator = usernameGenerator;
    }

    public void setTrainerDAOFromTrainerDAOMap(String nameOfDao) {
        this.trainerDAO = trainerDAOMap.get(nameOfDao);
    }

    public void setTraineeDAOFromTraineeDAOMap(String nameOfDao) {
        this.traineeDAO = traineeDAOMap.get(nameOfDao);
    }

    public Trainer getById(int id, String username, String password) {
        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            throw new UnauthorizedException("no trainer with such username or password");
        }
        log.info(">>>> Getting trainer with id: " + id);
        Optional<Trainer> trainerOptional = trainerDAO.getById(id);
        if (trainerOptional.isEmpty()) {
            throw new UserNotFoundException("no such trainer");
        }
        return trainerOptional.get();
    }

    private Trainer getById(int id) {

        log.info(">>>> Getting trainer with id: " + id);
        Optional<Trainer> trainerOptional = trainerDAO.getById(id);
        if (trainerOptional.isEmpty()) {
            throw new UserNotFoundException("no such trainer");
        }
        return trainerOptional.get();
    }

    public List<Trainer> getTrainers() {
        log.info(">>>> Getting trainers");
        return trainerDAO.getTrainers();
    }

    @Transactional
    public Trainer create(Trainer trainer) {
        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        if (trainer.getPassword() == null) {
            trainer.setPassword(RandomPasswordGenerator.generateRandomPassword());
        }
        log.info(">>>> Creating trainer with username: " + trainer.getUsername());
        if (userValidatorService.isInvalidUser(trainer)) {
            log.error("invalid customer");
            throw new IllegalStateException("invalid user");
        }
        Optional<Trainer> trainerOptional = trainerDAO.create(trainer);
        if (trainerOptional.isEmpty()) {
            throw new UserNotCreatedException("no such trainer");
        }
        return trainerOptional.get();
    }

    // InMemory implementation doesn't require 3 arguments
    public Trainer update(Trainer trainer) {
        //changed due to new requirement: 7. Username cannot be changed
        //        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        Optional<Trainer> trainerOptional = trainerDAO.update(trainer);
        if (trainerOptional.isEmpty()) {
            throw new UserNotUpdatedException("error updating trainer");
        }
        return trainerOptional.get();
    }

    @Transactional
    public Trainer update(Trainer trainer, String username, String password) {

        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            log.error("invalid username ^^^");
            throw new UnauthorizedException("no trainer with such username or password");
        }

        if (userValidatorService.isInvalidUser(trainer)) {
            log.info("invalid user :::");
            throw new IllegalStateException("invalid user");
        }

        //changed due to new requirement: 7. Username cannot be changed
        //            trainer.setUsername(usernameGenerator.generateUsername(trainer));
        log.info(">>>> Updating trainer with username: " + trainer.getUsername());
        Optional<Trainer> trainerOptional = trainerDAO.update(trainer);
        if (trainerOptional.isEmpty()) {
            throw new UserNotUpdatedException("error updating trainer");
        }
        return trainerOptional.get();
    }

    public Trainer findByUsernameAndPassword(String username, String password) {

        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            throw new UnauthorizedException("no trainer with such username or password");
        }

        log.info(">>>> Getting trainer using getByUsername: " + username);
        Optional<Trainer> trainerOptional = trainerDAO.findByUsername(username);
        if (trainerOptional.isEmpty()) {
            log.error("invalid username or password");
            throw new UserNotFoundException("no such trainer");
        }

        return trainerOptional.get();
    }

    public Trainer findByUsername(String username) {
        log.info(">>>> Getting trainer using getByUsername: " + username);
        Optional<Trainer> trainerOptional = trainerDAO.findByUsername(username);
        if (trainerOptional.isEmpty()) {
            log.error("invalid username or password");
            throw new UserNotFoundException("no such trainer");
        }

        return trainerOptional.get();
    }

    @Transactional
    public Trainer updatePassword(
            Trainer trainer,
            String username,
            String currentPassword,
            String newPassword) {
        log.info(">>>> Updating trainer with username: " + trainer.getUsername());
        if (!authentication.isAuthenticated(trainerDAO, username, currentPassword)) {
            throw new UnauthorizedException("no trainer with such username or password");
        }

        if (userValidatorService.isInvalidUser(trainer)) {
            throw new IllegalStateException("invalid user");
        }

        trainer.setPassword(newPassword);

        return update(trainer);
    }

    @Transactional
    public boolean activateTrainer(Trainer trainer, String username, String password) {

        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            throw new UnauthorizedException("no trainer with such username or password");
        }

        if (trainer.getIsActive()) {
            log.info("trainer is already active");
            return false;
        }

        trainer.setIsActive(true);
        update(trainer);
        log.info("trainer is active");
        return true;
    }

    @Transactional
    public boolean deactivateTrainer(Trainer trainer, String username, String password) {

        if (!authentication.isAuthenticated(trainerDAO, username, password)) {
            throw new UnauthorizedException("no trainer with such username or password");
        }

        if (!trainer.getIsActive()) {
            log.info("trainer is already deactivated");
            return false;
        }

        trainer.setIsActive(false);
        update(trainer);
        log.info("trainer is deactivated");
        return true;
    }

    public List<Trainer> findUnassignedTrainersByTraineeUsername(String traineeUsername, String password) {

        if (!authentication.isAuthenticated(traineeDAO, traineeUsername, password)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }

        List<Integer> ids = trainerDAO.findIdsOfUnassignedTrainersByTraineeUsername(traineeUsername);
        List<Trainer> trainers = new ArrayList<>();
        for (Integer id : ids) {
            Trainer trainer = getById(id);
            trainers.add(trainer);
        }

        return trainers;
    }

//    public Trainer findByUsernameAndPassword(String username, String password) {
//
//        Optional<Trainer> trainerOptional = trainerDAO.findByUsernameAndPassword(username, password);
//        if (trainerOptional.isEmpty()) {
//            throw new UnauthorizedException("wrong username or password");
//        }
//
//        return trainerOptional.get();
//    }
}
