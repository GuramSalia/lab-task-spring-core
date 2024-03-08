package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class Authentication {

    public static <T extends ImplementsFindByUsernameAndPassword, U extends User> boolean isAuthenticated(
            T t, String username, String password
                                                                                                         ) {
        U userInDb;
        try {
            Optional<U> uOptional = t.findByUsernameAndPassword(username, password);
            if (uOptional.isEmpty()) {
                log.error("wrong username or password");
                return false;
            }
            userInDb = uOptional.get();
        } catch (Exception e) {
            log.error("something went wrong", e);
            return false;
        }
        return userInDb.getPassword().equals(password);
    }
}
