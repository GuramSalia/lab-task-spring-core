package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.dao.TraineeDAO;
import com.epam.labtaskspringcore.dao.TrainerDAO;
import com.epam.labtaskspringcore.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Getter
@Setter
public class UsernameGeneratorImpl implements UsernameGenerator {

    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;

    public UsernameGeneratorImpl(TrainerDAO trainerDAO, TraineeDAO traineeDAO) {
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
    }

    public String generateUsername(User user) {

        String base = user.getFirstName() + "." + user.getLastName();
        int counter = 1;
        StringBuilder username = new StringBuilder().append(base);
        while (
                !isUnique(user, username.toString(), trainerDAO.getTrainers()) ||
                        !isUnique(user, username.toString(), traineeDAO.getTrainees())
        ) {
            username = new StringBuilder().append(base).append(counter);
            counter++;
        }
        log.info(">>>> Generating username");
        return username.toString();
    }


    private <T extends User, V extends User> boolean isUnique(V targetUser, String username, List<T> users) {
        boolean result = true;
        for (T user : users) {
            if (user.getUsername().equals(username) && !user.equals(targetUser)) {
                result = false;
                break;
            }
        }
        return result;
    }


//    private <T extends User> boolean isUnique(String username, List<T> users) {
//        boolean result = true;
//        for (T user : users) {
//            if (user.getUsername().equals(username)) {
//                result = false;
//                break;
//            }
//        }
//        return result;
//    }
}
