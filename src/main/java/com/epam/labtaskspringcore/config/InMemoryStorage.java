package com.epam.labtaskspringcore.config;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Create a Storage Bean: Implement a separate Spring bean responsible for storing the entities in a java map.
// maybe like DAO I need to create a separate interface InMemoryStorage which this class InMemoryStorageImpl implements?

@Setter
@Getter
@Component
public class InMemoryStorage {
    private final Logger LOG = LoggerFactory.getLogger(InMemoryStorage.class);

    private  Map<Integer, Trainee> trainees = new ConcurrentHashMap<>();
    private  Map<Integer, Trainer> trainers = new ConcurrentHashMap<>();
    private  Map<Integer, Training> trainings = new ConcurrentHashMap<>();






}
