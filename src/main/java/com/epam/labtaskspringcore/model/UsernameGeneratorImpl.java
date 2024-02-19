package com.epam.labtaskspringcore.model;

import com.epam.labtaskspringcore.service.TraineeService;
import com.epam.labtaskspringcore.service.TrainerService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsernameGeneratorImpl implements UsernameGenerator{
//    static Logger LOG = LoggerFactory.getLogger(UsernameGeneratorImpl.class);
    Logger LOG = LoggerFactory.getLogger(getClass());

    static TrainerService trainerService;
    static TraineeService traineeService;

    public  String generateUsername(User user) {
        LOG.info("generateUsername");
        String base = user.getFirstName() + "." + user.getLastName();
        int counter = 1;
        StringBuilder username = new StringBuilder().append(base);
        while (
                !isUnique(username.toString(), trainerService.getTrainers()) ||
                !isUnique(username.toString(),traineeService.getTrainees())
        ) {
            username = new StringBuilder().append(base).append(counter);
            counter++;
        }

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
        LOG.info("Created UsernameGeneratorImpl bean");
        LOG.debug("Created UsernameGeneratorImpl bean");
        LOG.error("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
