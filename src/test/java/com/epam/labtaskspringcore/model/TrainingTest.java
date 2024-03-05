package com.epam.labtaskspringcore.model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TrainingTest {

    private Training training1;
    private Training training2;
    private Training training3;
    private Training training4;

    @BeforeEach
    void setUp() {
        training1 = new Training();
        training2 = new Training();
        training3 = new Training();
        training4 = new Training();
    }

    @Test
    void testSettersGetId() {
        training1.setId(1);
        training2.setId(2);
        training3.setId(3);
        training4.setId(4);

        assertAll(
                () -> assertEquals(1, training1.getId()),
                () -> assertEquals(2, training2.getId()),
                () -> assertEquals(3, training3.getId()),
                () -> assertEquals(4, training4.getId())
                 );
    }

    @Test
    void testSettersGetTraineeId() {
        training1.setTraineeId(1);
        training2.setTraineeId(2);
        training3.setTraineeId(3);
        training4.setTraineeId(4);

        assertAll(
                () -> assertEquals(1, training1.getTraineeId()),
                () -> assertEquals(2, training2.getTraineeId()),
                () -> assertEquals(3, training3.getTraineeId()),
                () -> assertEquals(4, training4.getTraineeId())
                 );
    }

    @Test
    void testSettersGetTrainerId() {
        training1.setTrainerId(1);
        training2.setTrainerId(2);
        training3.setTrainerId(3);
        training4.setTrainerId(4);

        assertAll(
                () -> assertEquals(1, training1.getTrainerId()),
                () -> assertEquals(2, training2.getTrainerId()),
                () -> assertEquals(3, training3.getTrainerId()),
                () -> assertEquals(4, training4.getTrainerId())
                 );
    }

    @Test
    void testSettersGetName() {
        training1.setName("Training 1");
        training2.setName("Training 2");
        training3.setName("Training 3");
        training4.setName("Training 4");
        assertAll(
                () -> assertEquals("Training 1", training1.getName()),
                () -> assertEquals("Training 2", training2.getName()),
                () -> assertEquals("Training 3", training3.getName()),
                () -> assertEquals("Training 4", training4.getName())
                 );
    }

    @Test
    void testSettersGetType() {

        TrainingType YOGA = new TrainingType();
        YOGA.setTrainingType(TrainingType.TrainingTypeEnum.YOGA);
        TrainingType PERSONAL = new TrainingType();
        PERSONAL.setTrainingType(TrainingType.TrainingTypeEnum.PERSONAL);
        TrainingType CARDIO = new TrainingType();
        CARDIO.setTrainingType(TrainingType.TrainingTypeEnum.CARDIO);
        TrainingType GROUP = new TrainingType();
        GROUP.setTrainingType(TrainingType.TrainingTypeEnum.GROUP);



        training1.setType(YOGA);
        training2.setType(PERSONAL);
        training3.setType(GROUP);
        training4.setType(CARDIO);
        assertAll(
                () -> assertEquals(YOGA, training1.getType()),
                () -> assertEquals(PERSONAL, training2.getType()),
                () -> assertEquals(GROUP, training3.getType()),
                () -> assertEquals(CARDIO, training4.getType())
                 );
    }


    @Test
    void testSettersGetDurationInMinutes() {
        training1.setDurationInMinutes(10);
        training2.setDurationInMinutes(20);
        training3.setDurationInMinutes(30);
        training4.setDurationInMinutes(40);
        assertAll(
                () -> assertEquals(10, training1.getDurationInMinutes()),
                () -> assertEquals(20, training2.getDurationInMinutes()),
                () -> assertEquals(30, training3.getDurationInMinutes()),
                () -> assertEquals(40, training4.getDurationInMinutes())
                 );
    }
}