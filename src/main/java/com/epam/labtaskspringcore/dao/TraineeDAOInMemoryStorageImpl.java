package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Profile("dev_in_memory")
@Repository("TRAINEE_IN_MEMORY")
public class TraineeDAOInMemoryStorageImpl implements TraineeDAO {
    private final Map<Integer, Trainee> trainees;

    public TraineeDAOInMemoryStorageImpl(InMemoryStorage storage) {this.trainees = storage.getTrainees();}

    public Optional<Trainee> create(Trainee trainee) {
        log.info("<<>>>> TraineeDAOInMemoryStorageImpl create()");

        int id = trainee.getUserId();
        if (trainees.containsKey(id)) {
            log.error("Trainee with id {} already exists", id);
            return Optional.empty();
        } else {
            trainees.put(id, trainee);
            return Optional.of(trainees.get(id));
        }
    }

    public Optional<Trainee> update(Trainee trainee) {
        int id = trainee.getUserId();
        if (trainees.containsKey(id)) {
            trainees.put(id, trainee);
            return Optional.of(trainees.get(id));
        } else {
            log.error("Trainee with id {} does not exist", id);
            return Optional.empty();
        }
    }

    public boolean delete(Trainee trainee) {
        trainees.remove(trainee.getUserId());
        // when connected to DB need to check if deletion was successful in DB.
        return !trainees.containsKey(trainee.getUserId());
    }

    public Optional<Trainee> getById(int id) {return Optional.ofNullable(trainees.get(id));}

    @Override
    public List<Trainee> getTrainees() {return new ArrayList<>(trainees.values());}

    @Override
    public Optional<Trainee> findByUsername(String username) {
        log.info("'findByUsername' from TraineeDaoInMemoryStorageImpl");
        for (Trainee currentTrainee : trainees.values()) {
            if (currentTrainee.getUsername().equals(username)) {
                return Optional.of(currentTrainee);
            }
        }

        return Optional.empty();

    }

    @Override
    public Optional<Trainee> findByUsernameAndPassword(String username, String password) {
        log.info("\n\n'trainees: \n" + trainees);
        log.info("\n\n'findByUsernameAndPassword' from TraineeDaoInMemoryStorageImpl\n");
        log.info("\n\nREQUESTED username='" + username +", password='" + password+"\n");

        for (Trainee currentTrainee : trainees.values()) {
            String traineeUsername = currentTrainee.getUsername();
            String traineePassword = currentTrainee.getPassword();
            log.info("\n\ntraineeUsername=" + traineeUsername +", traineePassword=" + traineePassword+"\n");
            if (currentTrainee.getUsername().equals(username) && currentTrainee.getPassword().equals(password)) {
                return Optional.of(currentTrainee);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Trainee> findByUsernameWithQuery(String username) {
        log.info("'findWithUsername' from TraineeDaoInMemoryStorageImpl");
        return findByUsername(username);
    }
}
