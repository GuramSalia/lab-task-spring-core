package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.User;
import jakarta.persistence.Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class Authentication {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Authentication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public <T extends ImplementsFindByUsernameAndPassword, U extends User> boolean isAuthenticated(
            T t, String username, String password) {
        U userInDb;
        Optional<U> uOptional = t.findByUsername(username);
        if (uOptional.isEmpty()) {
            log.error("wrong username or password");
            return false;
        }

        userInDb = uOptional.get();
        return passwordEncoder.matches(password, userInDb.getPassword());
    }
}
