package com.epam.labtaskspringcore.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

// Initialize Storage with Prepared Data from File: Implement a Spring bean post-processor to initialize the storage
// with data from a file during application start.

@Component
public class StorageInitializer {

//    @Value("${file.path}")
//    private String filePath;

    @Value("${file.path.trainers}")
    private String trainersFile;
    @Value("${file.path.trainees}")
    private String traineesFile;
    @Value("${file.path.trainings}")
    private String trainingsFile;
    private final InMemoryStorage storage;

    public StorageInitializer(InMemoryStorage storage) {this.storage = storage;}

    @PostConstruct
    public void initializeDataFromFile() {

    }
    // Constructor injection of InMemoryStorage bean

    // Implement postProcessAfterInitialization method to read data from the file

    //    // Example map structure
    //    {
    //        "Trainer": [TrainerEntity1, TrainerEntity2, ...],
    //        "Trainee": [TraineeEntity1, TraineeEntity2, ...],
    //        "Training": [TrainingEntity1, TrainingEntity2, ...],
    //        // ...
    //    }
}

