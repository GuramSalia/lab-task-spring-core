package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
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
public class TrainerService {
    private final Map<String, TrainerDAO> trainerDAOMap;

    private final UsernameGenerator usernameGenerator;

    @Setter
    private TrainerDAO trainerDAO;

    @Autowired
    public TrainerService(Map<String, TrainerDAO> trainerDAOMap, UsernameGenerator usernameGenerator) {
        this.trainerDAOMap = trainerDAOMap;
        this.usernameGenerator = usernameGenerator;
    }

    public void setTrainerDAOFromTrainerDAOMap(String nameOfDao) {
        this.trainerDAO = trainerDAOMap.get(nameOfDao);
    }

    public Optional<Trainer> getById(int id) {
        log.info(">>>> Getting trainer with id: " + id);
        return trainerDAO.getById(id);
    }

    public List<Trainer> getTrainers() {
        log.info(">>>> Getting trainers");
        return trainerDAO.getTrainers();
    }

    //    public Optional<Trainer> create(Trainer trainer) {
    //        trainer.setPassword(RandomPasswordGenerator.generateRandomPassword());
    //        trainer.setUsername(usernameGenerator.generateUsername(trainer));
    //        return trainerDAO.create(trainer);
    //    }

    @Transactional
    public Optional<Trainer> create(Trainer trainer) {

        try {

            trainer.setUsername(usernameGenerator.generateUsername(trainer));
            if (trainer.getPassword() == null) {
                trainer.setPassword(RandomPasswordGenerator.generateRandomPassword());
            }
            log.info(">>>> Creating trainer with username: " + trainer.getUsername());

            if (UserService.isInvalidUser(trainer)) {
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
        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        return trainerDAO.update(trainer);
    }

    @Transactional
    public Optional<Trainer> update(Trainer trainer, String username, String password) {

        if (isInvalidUsernamePassword(trainer, username, password)) {
            log.error("invalid username or password");
            return Optional.empty();
        }

        if (UserService.isInvalidUser(trainer)) {
            log.info("invalid user");
            return Optional.empty();
        }

        try {
            trainer.setUsername(usernameGenerator.generateUsername(trainer));
            log.info(">>>> Updating trainer with username: " + trainer.getUsername());
            return trainerDAO.update(trainer);
        } catch (Exception e) {
            log.error("error updating trainer", e);
            return Optional.empty();
        }
    }

    public Optional<Trainer> getByUsername(String username, String password) {

        Optional<Trainer> trainerOptional = trainerDAO.findByUsername(username);
        if (trainerOptional.isEmpty()) {
            log.error("invalid username or password");
            return Optional.empty();
        }

        Trainer trainer = trainerOptional.get();
        if (isInvalidUsernamePassword(trainer, username, trainer.getPassword())) {
            log.error("invalid username or password");
            return Optional.empty();
        }

        log.info(">>>> Getting trainer using getByUsername: " + username);
        return trainerOptional;
    }

    @Transactional
    public Optional<Trainer> updatePassword(Trainer trainer, String username, String currentPassword,
                                            String newPassword) {

        if (isInvalidUsernamePassword(trainer, username, currentPassword)) {
            log.error("invalid username or password");
            return Optional.empty();
        }

        if (UserService.isInvalidUser(trainer)) {
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

        if (isInvalidUsernamePassword(trainer, username, password)) {
            log.error("invalid username or password");
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

        if (isInvalidUsernamePassword(trainer, username, password)) {
            log.error("invalid username or password");
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



    public void logTrainerCreationDetails(Trainer trainer) {
        if (create(trainer).isEmpty()) {
            log.error("could not create trainer with userId: " + trainer.getUserId());
        } else {
            Optional<Trainer> trainerOptional = getById(trainer.getUserId());
            if (trainerOptional.isEmpty()) {
                log.error("could not get trainer width userId: " + trainer.getUserId());
            } else {
                log.info("created successfully: " + trainerOptional.get());
            }
        }
    }

    private boolean isValidUsernamePassword(Trainer trainer, String username, String password) {
        Trainer trainerInDb;
        try {
            Optional<Trainer> trainerOptional = trainerDAO.findByUsernameAndPassword(username, password);
            if (trainerOptional.isEmpty()) {
                log.error("Could not find the trainer");
                return false;
            }

            trainerInDb = trainerOptional.get();
        } catch (Exception e) {
            log.error("something went wrong", e);
            return false;
        }
        return trainerInDb.equals(trainer);
    }

    private boolean isInvalidUsernamePassword(Trainer trainer, String username, String password) {
        return !isValidUsernamePassword(trainer, username, password);
    }

    public Trainer createTrainer(
            Integer userId,
            String firstName,
            String lastName,
            boolean isActive,
            TrainingType specialization
                                ) {
        Trainer trainer = new Trainer();

        trainer.setUserId(userId);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setIsActive(isActive);
        trainer.setSpecialization(specialization);
        return trainer;
    }
}
