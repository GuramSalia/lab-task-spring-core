package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.api.UsernamePassword;
import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.exception.*;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.utils.Authentication;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class TraineeService {

    private final Authentication authentication;
    private final UserValidatorService userValidatorService;
    private final UsernameGenerator usernameGenerator;
    private final TraineeDAO traineeDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TraineeService(
            TraineeDAO traineeDAO,
            Authentication authentication,
            UserValidatorService userValidatorService,
            UsernameGenerator usernameGenerator,
            PasswordEncoder passwordEncoder) {
        this.traineeDAO = traineeDAO;
        this.authentication = authentication;
        this.userValidatorService = userValidatorService;
        this.usernameGenerator = usernameGenerator;
        this.passwordEncoder = passwordEncoder;
        log.info(">>>> TraineeService initialized");
    }

    public Trainee getById(int id, String username, String password) {
        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }
        log.info(">>>> Getting trainee with id: " + id);
        Optional<Trainee> optionalTrainee = traineeDAO.getById(id);
        if (optionalTrainee.isEmpty()) {
            throw new UserNotFoundException("no trainee with such id");
        }
        return optionalTrainee.get();
    }

    @Transactional
    public UsernamePassword create(Trainee trainee) {
        log.info(">>>> training service create()");

        trainee.setIsBlocked(false);
        trainee.setFailedLoginAttempts(0);
        trainee.setBlockStartTime(null);
        trainee.setUsername(usernameGenerator.generateUsername(trainee));

        String generateRandomPassword = RandomPasswordGenerator.generateRandomPassword();
        UsernamePassword usernamePassword = new UsernamePassword(trainee.getUsername(), generateRandomPassword);
        trainee.setPassword(passwordEncoder.encode(generateRandomPassword));

        log.info(">>>> Creating trainee with username: " + trainee.getUsername());

        if (userValidatorService.isInvalidUser(trainee)) {
            log.error("invalid trainee");
            throw new IllegalStateException("invalid user");
        }

        Optional<Trainee> traineeOptional = traineeDAO.create(trainee);
        if (traineeOptional.isEmpty()) {
            throw new UserNotCreatedException("error creating trainee");
        }

        return usernamePassword;
    }

    // InMemory implementation doesn't require 3 arguments
    public boolean delete(Trainee trainee) {
        log.info(">>>> Deleting trainee with username: " + trainee.getUsername());
        return traineeDAO.delete(trainee);
    }

    @Transactional
    public boolean delete(String username, String password) {

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }
        Trainee trainee = findByUsername(username);
        return traineeDAO.delete(trainee);
    }

    // InMemory implementation doesn't require 3 arguments
    public Trainee update(Trainee trainee) {

        if (userValidatorService.isInvalidUser(trainee)) {
            log.info("invalid user");
            throw new IllegalStateException("invalid user");
        }

        Optional<Trainee> traineeOptional = traineeDAO.update(trainee);
        if (traineeOptional.isEmpty()) {
            throw new UserNotUpdatedException("error updating trainee");
        }
        return traineeOptional.get();
    }

    @Transactional
    public Trainee update(Trainee trainee, String username, String password) {

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }

        if (userValidatorService.isInvalidUser(trainee)) {
            log.info("invalid user");
            throw new IllegalStateException("invalid user");
        }

        Optional<Trainee> traineeOptional = traineeDAO.update(trainee);
        if (traineeOptional.isEmpty()) {
            throw new UserNotUpdatedException("error updating trainee");
        }
        return traineeOptional.get();
    }

    public Trainee findByUsername(String username) {

        Optional<Trainee> traineeOptional = traineeDAO.findByUsername(username);

        if (traineeOptional.isEmpty()) {
            throw new UserNotFoundException("no such trainee");
        }

        log.info(">>>> Getting trainee using getByUsername: : " + username);
        return traineeOptional.get();
    }

    // Is this not redundant, because we only encoded password in db?
    public Trainee findByUsernameAndPassword(String username, String password) {
        Optional<Trainee> traineeOptional = traineeDAO.findByUsernameAndPassword(username, password);
        if (traineeOptional.isEmpty()) {
            throw new UnauthorizedException("wrong username or password");
        }
        return traineeOptional.get();
    }

    @Transactional
    public Optional<Trainee> updatePassword(Trainee trainee, String username, String currentPassword, String
            newPassword) {
        log.info(">>>> Updating trainee with username: " + trainee.getUsername());

        if (!authentication.isAuthenticated(traineeDAO, username, currentPassword)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }

        if (userValidatorService.isInvalidUser(trainee)) {
            throw new IllegalStateException("invalid user");
        }

        trainee.setPassword(passwordEncoder.encode(newPassword));
        return traineeDAO.update(trainee);
    }

    @Transactional
    public boolean activateTrainee(Trainee trainee, String username, String password) {

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }

        if (trainee.getIsActive()) {
            log.info("trainee is already active");
            return false;
        }

        trainee.setIsActive(true);
        traineeDAO.update(trainee);
        log.info("trainee is active");
        return true;
    }

    @Transactional
    public boolean deactivateTrainee(Trainee trainee, String username, String password) {

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }

        if (!trainee.getIsActive()) {
            log.info("trainee is already deactivated");
            return false;
        }

        trainee.setIsActive(false);
        traineeDAO.update(trainee);
        log.info("trainee is deactivated");
        return true;
    }

    public List<Trainee> getTrainees() {
        log.info(">>>> Getting trainees");
        return traineeDAO.getTrainees();
    }

    @Transactional
    public Trainee setTrainers(Trainee trainee, String username, String password, Set<Trainer> trainers) {

        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }

        if (userValidatorService.isInvalidUser(trainee)) {
            log.info("invalid user");
            throw new IllegalStateException("invalid user");
        }

        trainee.setTrainers(trainers);
        return update(trainee);
    }

    public Trainee findByUsernameWithQuery(String username, String password) {
        if (!authentication.isAuthenticated(traineeDAO, username, password)) {
            throw new UnauthorizedException("no trainee with such username or password");
        }
        Optional<Trainee> optionalTrainee = traineeDAO.findByUsernameWithQuery(username);
        if (optionalTrainee.isEmpty()) {
            throw new UserNotFoundException("no such trainee");
        }
        return optionalTrainee.get();
    }
}
