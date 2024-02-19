package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDAOImpl implements TrainingDAO {
    private final InMemoryStorage storage;

    public TrainingDAOImpl(InMemoryStorage storage) {this.storage = storage;}
}
