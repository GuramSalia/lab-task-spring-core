package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TrainerService {
    private final TrainerDAO trainerDAO;
    private final UsernameGenerator usernameGenerator;

    public TrainerService(TrainerDAO trainerDAO, UsernameGenerator usernameGenerator) {
        this.trainerDAO = trainerDAO;
        this.usernameGenerator = usernameGenerator;
    }

    // create/update/select Trainer profile
    public Trainer create(Trainer trainer) {
        trainer.setPassword(RandomPasswordGenerator.generateRandomPassword());
        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        trainerDAO.create(trainer);
        log.info(">>>> Creating trainer with username: " + trainer.getUsername());
        return trainerDAO.getById(trainer.getId());

    }

    public Trainer update(Trainer trainer) {
        // update username if first or last name changed?
        Trainer old = trainerDAO.getById(trainer.getId());
        if (
                !Objects.equals(old.getFirstName(), trainer.getFirstName())
                        || !Objects.equals(old.getLastName(), trainer.getLastName())
        ) {
            trainer.setUsername(usernameGenerator.generateUsername(trainer));
        }
        trainerDAO.update(trainer);
        log.info(">>>> Updating trainer with username: " + trainer.getUsername());
        return trainerDAO.getById(trainer.getId());
    }

    public Trainer getById(int id) {
        log.info(">>>> Getting trainer with id: " + id);
        return trainerDAO.getById(id);}

    public List<User> getTrainers() {
        log.info(">>>> Getting trainers");
        return trainerDAO.getTrainers();}
}
