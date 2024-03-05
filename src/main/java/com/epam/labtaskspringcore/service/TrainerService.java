package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.model.User;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainerService {
    private final TrainerDAO trainerDAO;
    private final UsernameGenerator usernameGenerator;

    public TrainerService(TrainerDAO trainerDAO, UsernameGenerator usernameGenerator) {
        this.trainerDAO = trainerDAO;
        this.usernameGenerator = usernameGenerator;
    }

    public Optional<Trainer> createWithDao(Trainer trainer) {
        trainer.setPassword(RandomPasswordGenerator.generateRandomPassword());
        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        return trainerDAO.create(trainer);
    }

    public Optional<Trainer> updateWithDao(Trainer trainer) {
        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        return trainerDAO.update(trainer);
    }

    public Optional<Trainer> getByIdWithDao(int id) {
        log.info(">>>> Getting trainer with id: " + id);
        return trainerDAO.getById(id);
    }

    public List<User> getTrainersWithDao() {
        log.info(">>>> Getting trainers");
        return trainerDAO.getTrainers();
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

    public void logTrainerCreationDetails(Trainer trainer) {
        if (createWithDao(trainer).isEmpty()) {
            log.error("could not create trainer with userId: " + trainer.getUserId());
        } else {
            Optional<Trainer> trainerOptional = getByIdWithDao(trainer.getUserId());
            if (trainerOptional.isEmpty()) {
                log.error("could not get trainer width userId: " + trainer.getUserId());
            } else {
                log.info("created successfully: " + trainerOptional.get());
            }
        }
    }
}
