package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    public boolean isValidUser(User user) {
        if (user == null) {
            log.error("Customer is null");
            return false;
        }

        boolean valid = true;
        if (user.getFirstName() == null) {
            valid = false;
            log.error("First name cannot be null");
        }
        if (user.getLastName() == null) {
            valid = false;
            log.error("Last name cannot be null");
        }
        if (user.getUsername() == null) {
            valid = false;
            log.error("Username cannot be null");
        }
        if (user.getPassword() == null) {
            valid = false;
            log.error("Password cannot be null");
        }
        if (user.getIsActive() == null) {
            valid = false;
            log.error("isActive cannot be null");
        }

        return valid;
    }

    public boolean isInvalidUser(User user) {
        return !isValidUser(user);
    }

    public User findUserByUsername(String username) {
        log.info("'findUserByUsername' method is not implemented in UserService");
        return null;
    }

}
