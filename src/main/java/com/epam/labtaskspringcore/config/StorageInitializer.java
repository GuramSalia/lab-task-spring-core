package com.epam.labtaskspringcore.config;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// Initialize Storage with Prepared Data from File: Implement a Spring bean post-processor to initialize the storage
// with data from a file during application start.

@Slf4j
@Component
public class StorageInitializer {
//    private final Logger LOG = LoggerFactory.getLogger(InMemoryStorage.class);

    @Value("${file.path.initialData}")
    private Resource initialDataResource;

    private final InMemoryStorage storage;

    public StorageInitializer(InMemoryStorage storage) {
        this.storage = storage;
    }

    @PostConstruct
    public void initialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            JsonNode jsonNode = objectMapper.readTree(initialDataResource.getInputStream());
            initializeTrainers(jsonNode.get("Trainer"));
            initializeTrainees(jsonNode.get("Trainee"));
            initializeTrainings(jsonNode.get("Training"));
        } catch (IOException | ParseException e) {
            log.error("Error initializing InMemoryStorage", e);
        }
    }

    private void initializeTrainees(JsonNode traineeNode) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.setDateFormat(dateFormat);
        for (JsonNode node : traineeNode) {
            try {

//                log.warn("node.toString() Trainee: " + node.toString());
                Trainee trainee = objectMapper.treeToValue(node, Trainee.class);
                storage.getTrainees().put(trainee.getTraineeId(), trainee);
            } catch (JsonProcessingException e) {
                log.error("Error processing Trainee JSON node: {}", node.toString(), e);
            }
        }
    }

    private void initializeTrainers(JsonNode trainerNode) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        for (JsonNode node : trainerNode) {
            try {

//                log.debug("node.toString() Trainer: " + node.toString());
                Trainer trainer = objectMapper.treeToValue(node, Trainer.class);
                storage.getTrainers().put(trainer.getTrainerId(), trainer);
            } catch (JsonProcessingException e) {
                log.error("Error processing Trainer JSON node: {}", node.toString(), e);
            }
        }
    }

    private void initializeTrainings(JsonNode trainingNode) throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);
        for (JsonNode node : trainingNode) {
            try {

//                log.debug("node.toString() Training: " + node.toString());
                Training training = objectMapper.treeToValue(node, Training.class);
                storage.getTrainings().put(training.getTrainingId(), training);
            } catch (JsonProcessingException e) {
                log.error("Error processing Trainer JSON node: {}", node.toString(), e);
            }
        }
    }
}

