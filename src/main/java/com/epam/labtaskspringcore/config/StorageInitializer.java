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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StorageInitializer {

    static class Root {
        @JsonProperty("Trainer")
        public ArrayList<Trainer> trainer;
        @JsonProperty("Trainee")
        public ArrayList<Trainee> trainee;
        @JsonProperty("Training")
        public ArrayList<Training> training;
    }

    @Value("${file.path.initialData}")
    private Resource initialDataResource;

    private final InMemoryStorage storage;

    public StorageInitializer(InMemoryStorage storage) {
        this.storage = storage;
    }

    @PostConstruct
    public void initialize() {
        log.info(">>>> Storage Initialization");

        ObjectMapper om = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(dateFormat);

        try (InputStream inputStream = initialDataResource.getInputStream()) {
            Root root = om.readValue(inputStream, Root.class);

            // Assuming you have appropriate getters in your InMemoryStorage class
            storage.setTrainers(root.trainer.stream().collect(Collectors.toMap(Trainer::getId, Function.identity())));
            storage.setTrainees(root.trainee.stream().collect(Collectors.toMap(Trainee::getId, Function.identity())));
            storage.setTrainings(root.training.stream().collect(Collectors.toMap(Training::getId,
                    Function.identity())));
        } catch (IOException e) {
            log.error("Error initializing InMemoryStorage", e);
        }

//        log.info(">>>> Storage Initialization");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        try (InputStream inputStream = initialDataResource.getInputStream()) {
//            JsonNode jsonNode = objectMapper.readTree(inputStream);
//            initializeTrainers(jsonNode.get("Trainer"));
//            initializeTrainees(jsonNode.get("Trainee"));
//            initializeTrainings(jsonNode.get("Training"));
//        } catch (IOException | ParseException e) {
//            log.error("Error initializing InMemoryStorage", e);
//        }
//    }
//
//    private void initializeTrainees(JsonNode traineeNode) throws IOException {
//        ObjectMapper om = new ObjectMapper();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        om.setDateFormat(dateFormat);
//
//        traineeNode.elements().forEachRemaining(node -> {
//            try {
//                String jsonString = om.writeValueAsString(node);
//                Trainee trainee = om.readValue(jsonString, Trainee.class);
//                storage.getTrainees().put(trainee.getId(), trainee);
//            } catch (JsonProcessingException e) {
//                log.error("Error processing Trainee JSON node: {}", node.toString(), e);
//            }
//        });
//
//        //        ObjectMapper objectMapper = new ObjectMapper();
//        //        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        //        objectMapper.setDateFormat(dateFormat);
//        //        for (JsonNode node : traineeNode) {
//        //            try {
//        //                Trainee trainee = objectMapper.treeToValue(node, Trainee.class);
//        //                storage.getTrainees().put(trainee.getId(), trainee);
//        //            } catch (JsonProcessingException e) {
//        //                log.error("Error processing Trainee JSON node: {}", node.toString(), e);
//        //            }
//        //        }
//    }
//
//    private void initializeTrainers(JsonNode trainerNode) throws JsonProcessingException {
//
//        ObjectMapper om = new ObjectMapper();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        om.setDateFormat(dateFormat);
//
//        trainerNode.elements().forEachRemaining(node -> {
//            try {
//                String jsonString = om.writeValueAsString(node);
//                Trainer trainer = om.readValue(jsonString, Trainer.class);
//                storage.getTrainers().put(trainer.getId(), trainer);
//            } catch (JsonProcessingException e) {
//                log.error("Error processing Trainee JSON node: {}", node.toString(), e);
//            }
//        });
//
//        //        ObjectMapper objectMapper = new ObjectMapper();
//        //        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        //
//        //        for (JsonNode node : trainerNode) {
//        //            try {
//        //                Trainer trainer = objectMapper.treeToValue(node, Trainer.class);
//        //                storage.getTrainers().put(trainer.getId(), trainer);
//        //            } catch (JsonProcessingException e) {
//        //                log.error("Error processing Trainer JSON node: {}", node.toString(), e);
//        //            }
//        //        }
//    }
//
//    private void initializeTrainings(JsonNode trainingNode) throws ParseException, JsonProcessingException {
//        ObjectMapper om = new ObjectMapper();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        om.setDateFormat(dateFormat);
//
//        trainingNode.elements().forEachRemaining(node -> {
//            try {
//                String jsonString = om.writeValueAsString(node);
//                Trainer training = om.readValue(jsonString, Trainer.class);
//                storage.getTrainers().put(training.getId(), training);
//            } catch (JsonProcessingException e) {
//                log.error("Error processing Trainee JSON node: {}", node.toString(), e);
//            }
//        });
//
//        //        ObjectMapper objectMapper = new ObjectMapper();
//        //
//        //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        //
//        //        objectMapper.setDateFormat(dateFormat);
//        //        for (JsonNode node : trainingNode) {
//        //            try {
//        //                Training training = objectMapper.treeToValue(node, Training.class);
//        //                storage.getTrainings().put(training.getId(), training);
//        //            } catch (JsonProcessingException e) {
//        //                log.error("Error processing Trainer JSON node: {}", node.toString(), e);
//        //            }
//        //        }
    }
}

