package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.dao.TrainerDAOImpl;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TrainerService {
    private final TrainerDAO trainerDAO;
    private final UsernameGenerator usernameGenerator;

    public TrainerService(TrainerDAO trainerDAO, UsernameGenerator usernameGenerator) {
        this.trainerDAO = trainerDAO;
        this.usernameGenerator = usernameGenerator;
    }

    // create/update/select Trainer profile
    public void create(Trainer trainer) {
        trainer.setPassword(RandomPasswordGenerator.generateRandomPassword());
        trainer.setUsername(usernameGenerator.generateUsername(trainer));
        trainerDAO.create(trainer);
    }

    public void update(Trainer trainer) {
        // update username if first or last name changed?
        Trainer old = trainerDAO.getById(trainer.getTrainerId());
        if (
                !Objects.equals(old.getFirstName(), trainer.getFirstName())
                        || !Objects.equals(old.getLastName(), trainer.getLastName())
        ) {
            trainer.setUsername(usernameGenerator.generateUsername(trainer));
        }
        trainerDAO.update(trainer);
    }

    public Trainer getById(int id) {return trainerDAO.getById(id);}

    public List<User> getTrainers() {return trainerDAO.getTrainers();}
}
