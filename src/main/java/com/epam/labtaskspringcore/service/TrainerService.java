package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    // create/update/select Trainer profile
    public Optional<Trainer> create(Trainer trainer) {
        trainer.setPassword(RandomPasswordGenerator.generateRandomPassword());
        trainer.setUsername(usernameGenerator.generateUsername(trainer));
//        trainerDAO.create(trainer);
//        log.info(">>>> Creating trainer with username: " + trainer.getUsername());
        return trainerDAO.create(trainer);

    }

    public Optional<Trainer> update(Trainer trainer) {
        // update username if first or last name changed. We can do it when I can look it up in DB. now it is not
        // possible because I have one reference and there separate data which could be different. But then the
        // question remains, if it is more expensive to look-up old names in DB or just call the method to update
        // username
        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        return trainerDAO.update(trainer);
    }

    public Optional<Trainer> getById(int id) {
        log.info(">>>> Getting trainer with id: " + id);
        return trainerDAO.getById(id);}

    public List<User> getTrainers() {
        log.info(">>>> Getting trainers");
        return trainerDAO.getTrainers();}
}
