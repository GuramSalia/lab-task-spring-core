package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.exception.UnauthorizedException;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final Map<String, TraineeDAO> traineeDAOMap;
    private final Map<String, TrainerDAO> trainerDAOMap;
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Setter
    @Autowired
    private TraineeDAO traineeDAO;

    @Setter
    @Autowired
    private TrainerDAO trainerDAO;

    @Autowired
    public UserService(
            Map<String, TraineeDAO> traineeDAOMap,
            Map<String, TrainerDAO> trainerDAOMap,
            TraineeService traineeService,
            TrainerService trainerService) {
        this.traineeDAOMap = traineeDAOMap;
        this.trainerDAOMap = trainerDAOMap;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    public void setTraineeDAOFromTraineeDAOMap(String nameOfDao) {
        this.traineeDAO = traineeDAOMap.get(nameOfDao);
    }

    public void setTrainerDAOFromTrainerDAOMap(String nameOfDao) {
        this.trainerDAO = trainerDAOMap.get(nameOfDao);
    }

    public boolean performAuthentication(String username, String password) {

        Optional<Trainee> traineeOptional = traineeDAO.findByUsernameAndPassword(username, password);
        Optional<Trainer> trainerOptional = trainerDAO.findByUsernameAndPassword(username, password);

        log.info("start checking with 'performAuthentication'");
        if (traineeOptional.isEmpty() && trainerOptional.isEmpty()) {
            log.error("\n\nauthentication >>> throws exception\n");
            throw new UnauthorizedException("username or password is incorrect");
        }
        log.info("\n\nauthentication >>> didn't throw exception\n");
        return true;
    }

    public void updatePassword(String username, String currentPassword, String newPassword) {

        Optional<Trainee> traineeOptional = traineeDAO.findByUsernameAndPassword(username, currentPassword);
        Optional<Trainer> trainerOptional = trainerDAO.findByUsernameAndPassword(username, currentPassword);

        if (traineeOptional.isEmpty() && trainerOptional.isEmpty()) {
            throw new UnauthorizedException("username or password is invalid");
        }

        traineeOptional.ifPresent(
                trainee -> traineeService.updatePassword(trainee, username, currentPassword, newPassword));
        trainerOptional.ifPresent(
                trainer -> trainerService.updatePassword(trainer, username, currentPassword, newPassword));
    }
}
