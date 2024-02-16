package com.epam.labtaskspringcore.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Create a Storage Bean: Implement a separate Spring bean responsible for storing the entities in a java map.

// maybe like DAO I need to create a separate interface InMemoryStorage which this class InMemoryStorageImpl implements?

@Component
public class InMemoryStorage {
    private Map<String, List<Object>> storageMap = new HashMap<>();

//    // Example map structure
//    {
//        "Trainer": [TrainerEntity1, TrainerEntity2, ...],
//        "Trainee": [TraineeEntity1, TraineeEntity2, ...],
//        "Training": [TrainingEntity1, TrainingEntity2, ...],
//        // ...
//    }

    // Implement methods for storing and retrieving data

    // ?
    //    public void put(String key, Object value) {
    //        storageMap.put(key, value);
    //    }
}
