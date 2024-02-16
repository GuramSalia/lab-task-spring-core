package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;

public class TraineeDAOImpl implements TraineeDAO {
    private final InMemoryStorage storage;

    public TraineeDAOImpl(InMemoryStorage storage) {this.storage = storage;}



}
