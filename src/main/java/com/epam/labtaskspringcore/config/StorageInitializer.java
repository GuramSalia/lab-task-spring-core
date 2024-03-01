package com.epam.labtaskspringcore.config;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
            storage.setTrainings(root.training.stream()
                                              .collect(Collectors.toMap(Training::getId, Function.identity())));
        } catch (IOException e) {
            log.error("Error initializing InMemoryStorage", e);
        }
    }
}

