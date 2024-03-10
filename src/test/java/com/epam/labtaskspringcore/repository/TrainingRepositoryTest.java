package com.epam.labtaskspringcore.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TrainingRepositoryTest {

    @Autowired
    TrainingRepository trainingRepository;

    java.sql.Date sqlDateFrom;
    java.sql.Date sqlDateTo;

    @BeforeEach
    void setUp() {
        Calendar cal = Calendar.getInstance();
        cal.set(1980, Calendar.MARCH, 8);
        Date dateFrom = cal.getTime();
        cal.set(2050, Calendar.MARCH, 8);
        Date dateTo = cal.getTime();
        sqlDateFrom = new java.sql.Date(dateFrom.getTime());
        sqlDateTo = new java.sql.Date(dateTo.getTime());
    }

    @Test
    void findIdsByTraineeAndTrainerAndType() {
        List<Integer> trainingIds = trainingRepository.findIdsByTraineeAndTrainerAndType(
                "Jane.Smith",
                sqlDateFrom,
                sqlDateTo,
                "Sam.Jones",
                "STRENGTH");

        assertEquals(List.of(2),trainingIds,  "training should be returned");
    }

    @Test
    void findIdsByTrainerAndTrainerAndType() {
        List<Integer> trainingIds = trainingRepository.findIdsByTrainerAndTrainerAndType(
                "Sam.Jones",
                sqlDateFrom,
                sqlDateTo,
                "Jane.Smith");

        assertEquals(List.of(2),trainingIds,  "training should be returned");
    }
}