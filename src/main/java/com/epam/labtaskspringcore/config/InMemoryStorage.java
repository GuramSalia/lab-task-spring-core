package com.epam.labtaskspringcore.config;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

//Create a Storage Bean: Implement a separate Spring bean responsible for storing the entities in a java map.
// maybe like DAO I need to create a separate interface InMemoryStorage which this class InMemoryStorageImpl implements?

@Slf4j
@Setter
@Getter
@Component
public class InMemoryStorage {
//    private final Logger LOG = LoggerFactory.getLogger(InMemoryStorage.class);

    //    @Value("${file.path.initialData}")
    //    private Resource initialDataResource;

    private Map<Integer, Trainee> trainees = new ConcurrentHashMap<>();
    private Map<Integer, Trainer> trainers = new ConcurrentHashMap<>();
    private Map<Integer, Training> trainings = new ConcurrentHashMap<>();

    public void clearStorage(Map<Integer, Objects> storage) {
        log.info("Clearing storage");
        storage.clear();
    }

    //    @PostConstruct
    //    private void init() {
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //
    //        try {
    //            JsonNode jsonNode = objectMapper.readTree(initialDataResource.getInputStream());
    //            initializeTrainers(jsonNode.get("Trainer"));
    //            initializeTrainees(jsonNode.get("Trainee"));
    //            initializeTrainings(jsonNode.get("Training"));
    //        } catch (IOException | ParseException e) {
    //            LOG.error("Error initializing InMemoryStorage", e);
    //        }
    //    }
    //
    //    private void initializeTrainees(JsonNode traineeNode) throws JsonProcessingException {
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //        objectMapper.setDateFormat(dateFormat);
    //        for (JsonNode node : traineeNode) {
    //            try {
    //
    //                LOG.warn("node.toString() Trainee: " + node.toString());
    //                Trainee trainee = objectMapper.treeToValue(node, Trainee.class);
    //                trainees.put(trainee.getTraineeId(), trainee);
    //            } catch (JsonProcessingException e) {
    //                LOG.error("Error processing Trainee JSON node: {}", node.toString(), e);
    //            }
    //        }
    //    }
    //
    //    private void initializeTrainers(JsonNode trainerNode) throws JsonProcessingException {
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //
    //        for (JsonNode node : trainerNode) {
    //            try {
    //
    //                LOG.debug("node.toString() Trainer: " + node.toString());
    //                Trainer trainer = objectMapper.treeToValue(node, Trainer.class);
    //                trainers.put(trainer.getTrainerId(), trainer);
    //            } catch (JsonProcessingException e) {
    //                LOG.error("Error processing Trainer JSON node: {}", node.toString(), e);
    //            }
    //        }
    //    }
    //
    //    private void initializeTrainings(JsonNode trainingNode) throws ParseException, JsonProcessingException {
    //        ObjectMapper objectMapper = new ObjectMapper();
    //        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    //        objectMapper.setDateFormat(dateFormat);
    //        for (JsonNode node : trainingNode) {
    //            try {
    //
    //                LOG.debug("node.toString() Training: " + node.toString());
    //                Training training = objectMapper.treeToValue(node, Training.class);
    //                trainings.put(training.getTrainingId(), training);
    //            } catch (JsonProcessingException e) {
    //                LOG.error("Error processing Trainer JSON node: {}", node.toString(), e);
    //            }
    //        }
    //    }
}
