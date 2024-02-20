package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.User;
import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import com.epam.labtaskspringcore.utils.UsernameGenerator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Getter
@Setter
public class UsernameGeneratorImpl implements UsernameGenerator {
//    static Logger LOG = LoggerFactory.getLogger(UsernameGeneratorImpl.class);
    Logger LOG = LoggerFactory.getLogger(getClass());

    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

    public UsernameGeneratorImpl(TrainerDAO trainerDAO, TraineeDAO traineeDAO) {
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
    }

    public  String generateUsername(User user) {

        String base = user.getFirstName() + "." + user.getLastName();
        int counter = 1;
        StringBuilder username = new StringBuilder().append(base);
        while (
                !isUnique(username.toString(), trainerDAO.getTrainers()) ||
                !isUnique(username.toString(),traineeDAO.getTrainees())
        ) {
            username = new StringBuilder().append(base).append(counter);
            counter++;
        }
        LOG.info("generateUsername: " + username.toString());
        return username.toString();
    }

    private  <T extends User> boolean isUnique(String username, List<T> users) {
        boolean result = true;
        for (T user : users) {
            if (user.getUsername().equals(username)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @PostConstruct
    public void init() {
        LOG.info("Postconstruct started ");
    }
}
