package com.epam.labtaskspringcore.config;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Create a Storage Bean: Implement a separate Spring bean responsible for storing the entities in a java map.

// maybe like DAO I need to create a separate interface InMemoryStorage which this class InMemoryStorageImpl implements?

@Component
public class InMemoryStorage {
    private final Logger LOG = LoggerFactory.getLogger(InMemoryStorage.class);

    //    private Map<String, List<Object>> storageMap = new HashMap<>();

    private Map<String, List<?>> storageMap = new HashMap<>();

    public <T> void addItems(Class<T> providedClass, List<T> items) {
        String className = providedClass.getSimpleName();
        storageMap.put(className, new ArrayList<>(items));
    }

    public <T> void addItem(T item) {
        Class<?> itemClass = item.getClass();
        String className = itemClass.getSimpleName();

        if (storageMap.containsKey(className)) {
            // If the key already exists, append the item to the existing list
            List<T> itemList = (List<T>) storageMap.get(className);
            itemList.add(item);
        } else {
            // If the key doesn't exist, create a new list with the item
            List<T> itemList = new ArrayList<>();
            itemList.add(item);
            storageMap.put(className, itemList);
        }
    }

    public <T> List<T> getItems(Class<T> providedClass) {
        String className = providedClass.getSimpleName();
        return (List<T>) storageMap.getOrDefault(className, new ArrayList<>());
    }

    @PostConstruct
    public void dosomthing() {
        LOG.error(" @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
    }

    @PreDestroy
    public void destroyStorage() {
        LOG.info("destroyed InMemoryStorage");
        LOG.error("destroyed InMemoryStorage");
        LOG.debug("destroyed InMemoryStorage");
    }


//
//    private List<Trainer> trainers = new ArrayList<>();
//    private List<Trainee> trainees = new ArrayList<>();
//    private List<Training> trainings = new ArrayList<>();
//
//    public List<Trainer> getTrainers() {
//        return trainers;
//    }
//
//    public void setTrainers(List<Trainer> trainers) {
//        this.trainers = trainers;
//    }
//
//    public List<Trainee> getTrainees() {
//        return trainees;
//    }
//
//    public void setTrainees(List<Trainee> trainees) {
//        this.trainees = trainees;
//    }
//
//    public List<Training> getTrainings() {
//        return trainings;
//    }
//
//    public void setTrainings(List<Training> trainings) {
//        this.trainings = trainings;
//    }
//
//    public void addTrainer(Trainer trainer) {
//        trainers.add(trainer);
//    }
//
//    public void removeTrainer(Trainer trainer) {
//        trainers.remove(trainer);
//    }
//
//    public void addTrainee(Trainee trainee) {
//        trainees.add(trainee);
//    }
//    public void removeTrainee(Trainee trainee) {
//        trainees.remove(trainee);
//    }
//
//    public void addTraining(Training training) {
//        trainings.add(training);
//    }
//
//    public void removeTraining(Training training) {
//        trainings.remove(training);
//    }

}
