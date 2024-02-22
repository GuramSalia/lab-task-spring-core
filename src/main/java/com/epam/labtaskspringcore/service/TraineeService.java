package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TraineeService {
    private final TraineeDAO traineeDAO;
    private final UsernameGenerator usernameGenerator;

    public TraineeService(TraineeDAO traineeDAO,
                          UsernameGenerator usernameGenerator) {
        this.traineeDAO = traineeDAO;
        this.usernameGenerator = usernameGenerator;
        log.info(">>>> TraineeService initialized");
    }

    // create/update/delete/select Trainee profile
    public Trainee create(Trainee trainee) {
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        trainee.setPassword(RandomPasswordGenerator.generateRandomPassword());
        traineeDAO.create(trainee);
        log.info(">>>> Creating trainee with username: " + trainee.getUsername());
        return traineeDAO.getById(trainee.getId());
    }

    public Trainee update(Trainee trainee) {
        // update username if first or last name changed
        Trainee old = traineeDAO.getById(trainee.getId());

        if (
                !Objects.equals(old.getFirstName(), trainee.getFirstName())
                        || !Objects.equals(old.getLastName(), trainee.getLastName())

        ) {
            trainee.setUsername(usernameGenerator.generateUsername(trainee));
            log.info(">>>> updating username: " + trainee.getUsername());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        traineeDAO.update(trainee);
        log.info(">>>> Updating trainee with username: " + trainee.getUsername());
        return traineeDAO.getById(trainee.getId());
    }

    public void delete(int traineeId) {
        log.info(">>>> Deleting trainee with id: " + traineeId);
        traineeDAO.delete(traineeId);
    }

    public Trainee getById(int id) {
        log.info(">>>> Getting trainee with id: " + id);
        return traineeDAO.getById(id);}

    public List<Trainee> getTrainees() {
        log.info(">>>> Getting trainees");
        return traineeDAO.getTrainees();}
}
