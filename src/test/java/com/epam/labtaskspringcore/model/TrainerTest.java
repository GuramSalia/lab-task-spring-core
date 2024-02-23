package com.epam.labtaskspringcore.model;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
class TrainerTest {

    private Trainer trainer1;
    private Trainer trainer2;
    private Trainer trainer3;
    private Trainer trainer4;

    @BeforeEach
    void setUp() {
        trainer1 = new Trainer();
        trainer2 = new Trainer();
        trainer3 = new Trainer();
        trainer4 = new Trainer();
    }


    @Nested
    @DisplayName("when testing getters and setters")
    class TestSettersAndGetters {
        @Test
        void testSettersFirstName() {
            trainer1.setFirstName("John");
            trainer2.setFirstName("George");
            trainer3.setFirstName("Sam");
            trainer4.setFirstName("Kate");
            assertAll(
                    () -> assertEquals("John", trainer1.getFirstName()),
                    () -> assertEquals("George", trainer2.getFirstName()),
                    () -> assertEquals("Sam", trainer3.getFirstName()),
                    () -> assertEquals("Kate", trainer4.getFirstName())
                     );
        }

        @Test
        void testSettersLastName() {
            trainer1.setLastName("Doe");
            trainer2.setLastName("Fisher");
            trainer3.setLastName("Smith");
            trainer4.setLastName("James");
            assertAll(
                    () -> assertEquals("Doe", trainer1.getLastName()),
                    () -> assertEquals("Fisher", trainer2.getLastName()),
                    () -> assertEquals("Smith", trainer3.getLastName()),
                    () -> assertEquals("James", trainer4.getLastName())
                     );
        }

        @Test
        void testSettersUsername() {
            trainer1.setUsername("John.Doe");
            trainer2.setUsername("George.Fisher");
            trainer3.setUsername("Sam.Smith");
            trainer4.setUsername("Kate.James");
            assertAll(
                    () -> assertEquals("John.Doe", trainer1.getUsername()),
                    () -> assertEquals("George.Fisher", trainer2.getUsername()),
                    () -> assertEquals("Sam.Smith", trainer3.getUsername()),
                    () -> assertEquals("Kate.James", trainer4.getUsername())
                     );
        }

        @Test
        void testSettersPassword() {
            trainer1.setPassword("1234567890");
            trainer2.setPassword("abcdefghij");
            trainer3.setPassword("klmnopqrst");
            trainer4.setPassword("ababababab");
            assertAll(
                    () -> assertEquals("1234567890", trainer1.getPassword()),
                    () -> assertEquals("abcdefghij", trainer2.getPassword()),
                    () -> assertEquals("klmnopqrst", trainer3.getPassword()),
                    () -> assertEquals("ababababab", trainer4.getPassword())
                     );
        }

        @Test
        void testSettersActive() {
            trainer1.setActive(true);
            trainer2.setActive(false);
            trainer3.setActive(true);
            trainer4.setActive(false);
            assertAll(
                    () -> assertTrue(trainer1.isActive()),
                    () -> assertFalse(trainer2.isActive()),
                    () -> assertTrue(trainer3.isActive()),
                    () -> assertFalse(trainer4.isActive())
                     );
        }

        @Test
        void testSettersId() {
            trainer1.setId(1);
            trainer2.setId(2);
            trainer3.setId(3);
            trainer4.setId(4);

            assertAll(
                    () -> assertEquals(1, trainer1.getId()),
                    () -> assertEquals(2, trainer2.getId()),
                    () -> assertEquals(3, trainer3.getId()),
                    () -> assertEquals(4, trainer4.getId())
                     );
        }

        @Test
        void testSettersSpecialization() {
            trainer1.setSpecialization(TrainingType.YOGA);
            trainer2.setSpecialization(TrainingType.PERSONAL);
            trainer3.setSpecialization(TrainingType.CARDIO);
            trainer4.setSpecialization(TrainingType.STRENGTH);
            assertAll(
                    () -> assertEquals(TrainingType.YOGA, trainer1.getSpecialization()),
                    () -> assertEquals(TrainingType.PERSONAL, trainer2.getSpecialization()),
                    () -> assertEquals(TrainingType.CARDIO, trainer3.getSpecialization()),
                    () -> assertEquals(TrainingType.STRENGTH, trainer4.getSpecialization())
                     );

        }
    }

//    @Test
//    void setFirstName() {
//    }
//
//    @Test
//    void setLastName() {
//    }
//
//    @Test
//    void setUsername() {
//    }
//
//    @Test
//    void setPassword() {
//    }
//
//    @Test
//    void setActive() {
//    }
//
//    @Test
//    void setId() {
//    }
//
//    @Test
//    void setSpecialization() {
//    }
}