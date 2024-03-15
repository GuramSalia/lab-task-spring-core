package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.User;
import jakarta.persistence.Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class Authentication {

    public <T extends ImplementsFindByUsernameAndPassword, U extends User> boolean isAuthenticated(
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
