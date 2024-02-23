package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.utils.RandomPasswordGenerator;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Optional<Trainee> create(Trainee trainee) {
        trainee.setUsername(usernameGenerator.generateUsername(trainee));
        trainee.setPassword(RandomPasswordGenerator.generateRandomPassword());
        log.info(">>>> Creating trainee with username: " + trainee.getUsername());
        //        traineeDAO.create(trainee);
        //        return traineeDAO.getById(trainee.getId());
        return traineeDAO.create(trainee);
    }

    public Optional<Trainee> update(Trainee trainee) {
        // update username if first or last name changed. We can do it when I can look it up in DB. now it is not
        // possible because I have one reference and there separate data which could be different. But then the
        // question remains, if it is more expensive to look-up old names in DB or just call the method to update
        // username

        trainee.setUsername(usernameGenerator.generateUsername(trainee));

        return traineeDAO.update(trainee);
    }

    public boolean delete(int traineeId) {
//        log.info(">>>> Deleting trainee with id: " + traineeId);
        return traineeDAO.delete(traineeId);
    }

    public Optional<Trainee>  getById(int id) {
        log.info(">>>> Getting trainee with id: " + id);
        return traineeDAO.getById(id);
    }

    public List<Trainee> getTrainees() {
        log.info(">>>> Getting trainees");
        return traineeDAO.getTrainees();
    }
}
