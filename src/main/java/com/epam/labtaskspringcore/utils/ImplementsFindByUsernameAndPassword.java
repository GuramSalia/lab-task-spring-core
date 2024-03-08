package com.epam.labtaskspringcore.utils;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.User;

import java.util.Optional;

public interface ImplementsFindByUsernameAndPassword {
    public <T extends User> Optional<T> findByUsernameAndPassword(String username, String password);
}
