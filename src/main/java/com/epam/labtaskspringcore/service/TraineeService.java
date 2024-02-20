package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TraineeDAOImpl;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TraineeService {
    private final TraineeDAO traineeDAO;
    private final UsernameGenerator usernameGenerator;

    public TraineeService(TraineeDAO traineeDAO,
                          UsernameGenerator usernameGenerator) {
        this.traineeDAO = traineeDAO;
        this.usernameGenerator = usernameGenerator;
    }

    // create/update/delete/select Trainee profile
    public void create(Trainee trainee) {
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        trainee.setPassword(RandomPasswordGenerator.generateRandomPassword());
        traineeDAO.create(trainee);
    }

    public void update(Trainee trainee) {
        // update username if first or last name changed
        Trainee old = traineeDAO.getById(trainee.getTraineeId());
        if (
                !Objects.equals(old.getFirstName(), trainee.getFirstName())
                        || !Objects.equals(old.getLastName(), trainee.getLastName())
        ) {
            trainee.setUsername(usernameGenerator.generateUsername(trainee));
        }
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        traineeDAO.update(trainee);
    }

    public void delete(int traineeId) {traineeDAO.delete(traineeId);}

    public Trainee getById(int id) {return traineeDAO.getById(id);}

    public List<Trainee> getTrainees() {return traineeDAO.getTrainees();}
}
