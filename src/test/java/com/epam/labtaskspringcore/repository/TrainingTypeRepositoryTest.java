package com.epam.labtaskspringcore.repository;

import com.epam.labtaskspringcore.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TrainingTypeRepositoryTest {

    @Autowired
    TrainingTypeRepository trainingTypeRepository;

    @Test
    void findByTrainingType() {
        TrainingType foundTrainingType =
                trainingTypeRepository.findByTrainingType(TrainingType.TrainingTypeEnum.CARDIO);

        assertNotNull(foundTrainingType);
    }
}