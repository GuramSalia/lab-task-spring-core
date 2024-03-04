package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TraineeService {
    private final TraineeDAO traineeDAO;
    private final UsernameGenerator usernameGenerator;

    public TraineeService(TraineeDAO traineeDAO, UsernameGenerator usernameGenerator) {
        this.traineeDAO = traineeDAO;
        this.usernameGenerator = usernameGenerator;
        log.info(">>>> TraineeService initialized");
    }

    public Optional<Trainee> create(Trainee trainee) {
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        trainee.setPassword(RandomPasswordGenerator.generateRandomPassword());
        log.info(">>>> Creating trainee with username: " + trainee.getUsername());
        return traineeDAO.create(trainee);
    }

    public Optional<Trainee> update(Trainee trainee) {
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        return traineeDAO.update(trainee);
    }

    public boolean delete(int traineeId) {
        log.info(">>>> Deleting trainee with id: " + traineeId);
        return traineeDAO.delete(traineeId);
    }

    public Optional<Trainee> getById(int id) {
        log.info(">>>> Getting trainee with id: " + id);
        return traineeDAO.getById(id);
    }

    public List<Trainee> getTrainees() {
        log.info(">>>> Getting trainees");
        return traineeDAO.getTrainees();
    }
}
