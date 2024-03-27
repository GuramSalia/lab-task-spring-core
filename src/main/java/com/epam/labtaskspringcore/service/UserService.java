package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.exception.UnauthorizedException;
import com.epam.labtaskspringcore.exception.UserBlockedException;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private static final int MAX_FAILED_ATTEMPTS = 2;
    private static final int BLOCK_DURATION_MINUTES = 1;

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            TraineeService traineeService,
            TraineeDAO traineeDAO,
            TrainerService trainerService,
            TrainerDAO trainerDAO,
            PasswordEncoder passwordEncoder) {
        this.traineeService = traineeService;
        this.traineeDAO = traineeDAO;
        this.trainerService = trainerService;
        this.trainerDAO = trainerDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean performAuthentication(String username, String password) {
        Optional<Trainee> traineeOptional = traineeDAO.findByUsername(username);
        Optional<Trainer> trainerOptional = trainerDAO.findByUsername(username);
        if (traineeOptional.isEmpty() && trainerOptional.isEmpty()) {
            throw new UnauthorizedException("username or password is incorrect");
        }

        if (traineeOptional.isPresent()) {
            Trainee trainee = traineeOptional.get();
            return authenticate(trainee, password);
        }

        return authenticate(trainerOptional.get(), password);
    }

    private boolean authenticate(User user, String password) {
        log.info("\n\n+++++++++++++++++++++++++ withing authenticate method\n");
        boolean isBlocked = user.getIsBlocked();
        boolean correctPasswordProvided = passwordEncoder.matches(password, user.getPassword());

        if (!isBlocked && correctPasswordProvided) {
            log.info("\n\n++++++++++++++++++++++ not blocked, correct password provided\n");
            if (user.getFailedLoginAttempts() > 0) {
                user.setFailedLoginAttempts(0);
                saveUser(user);
            }
            return true;
        }

        if (isBlocked) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime blockedUntil = user.getBlockStartTime().plusMinutes(BLOCK_DURATION_MINUTES);

            // blocked time not expired
            if (now.isBefore(blockedUntil)) {
                log.info("\n\n++++++++++++++++++++++ isBlocked , blocked not expired\n");
                StringBuilder sb = new StringBuilder();
                sb.append("user is still blocked. Try again when ")
                  .append(BLOCK_DURATION_MINUTES)
                  .append(" minutes passed since it was last blocked");
                throw new UserBlockedException(sb.toString());
            }

            // blocked time expired
            user.setIsBlocked(false);
            if (!correctPasswordProvided) {
                log.info("\n\n++++++++++++++++++++++ isBlocked , blocked expired, incorrect password\n");
                user.setFailedLoginAttempts(1);
                saveUser(user);
                throw new UnauthorizedException("usename or password incorrect");
            }

            log.info("\n\n++++++++++++++++++++++ isBlocked , blocked expired, correct password\n");
            user.setBlockStartTime(null);
            user.setFailedLoginAttempts(0);
            saveUser(user);
            return true;
        }

        int newFailAttempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(newFailAttempts);
        if (newFailAttempts >= MAX_FAILED_ATTEMPTS) {
            user.setIsBlocked(true);
            user.setBlockStartTime(LocalDateTime.now());
            saveUser(user);
            throw new UserBlockedException("user blocked for " + BLOCK_DURATION_MINUTES + " minutes");
        }
        saveUser(user);
        throw new UnauthorizedException("username or password incorrect");
    }

    public void updatePassword(String username, String currentPassword, String newPassword) {

        Optional<Trainee> traineeOptional = traineeDAO.findByUsername(username);
        Optional<Trainer> trainerOptional = trainerDAO.findByUsername(username);

        if (traineeOptional.isEmpty() && trainerOptional.isEmpty()) {
            throw new UnauthorizedException("username or password is invalid");
        }

        traineeOptional.ifPresent(
                trainee -> traineeService.updatePassword(trainee, username, currentPassword, newPassword));
        trainerOptional.ifPresent(
                trainer -> trainerService.updatePassword(trainer, username, currentPassword, newPassword));
    }

    private void saveUser(User user) {
        log.warn("\n\n+++++++++++++++++++ Saving user\n");
        if (user instanceof Trainee) {
            traineeDAO.update((Trainee) user);
        } else if (user instanceof Trainer) {
            trainerDAO.update((Trainer) user);
        }
    }
}
