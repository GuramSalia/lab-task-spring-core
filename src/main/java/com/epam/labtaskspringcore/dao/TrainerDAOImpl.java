package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;

public class TrainerDAOImpl implements TrainerDAO {
    private final InMemoryStorage storage;

    public TrainerDAOImpl(InMemoryStorage storage) {this.storage = storage;}
}
