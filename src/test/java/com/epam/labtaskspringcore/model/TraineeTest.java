package com.epam.labtaskspringcore.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("when having Trainee instance")
class TraineeTest {
    private Trainee trainee1;
    private Trainee trainee2;
    private Trainee trainee3;
    private Trainee trainee4;

    @BeforeEach
    void setUp() {
        trainee1 = new Trainee();
        trainee2 = new Trainee();
        trainee3 = new Trainee();
        trainee4 = new Trainee();
    }

    @Nested
    @DisplayName("when testing getters and setters")
    class TestSettersAndGetters {

        @Test
        void getFirstName() {
            trainee1.setFirstName("John");
            trainee2.setFirstName("George");
            trainee3.setFirstName("Sam");
            trainee4.setFirstName("Kate");
            assertAll(
                    () -> assertEquals("John", trainee1.getFirstName()),
                    () -> assertEquals("George", trainee2.getFirstName()),
                    () -> assertEquals("Sam", trainee3.getFirstName()),
                    () -> assertEquals("Kate", trainee4.getFirstName())
                     );
        }

        @Test
        void getLastName() {
            trainee1.setLastName("Doe");
            trainee2.setLastName("Fisher");
            trainee3.setLastName("Smith");
            trainee4.setLastName("James");
            assertAll(
                    () -> assertEquals("Doe", trainee1.getLastName()),
                    () -> assertEquals("Fisher", trainee2.getLastName()),
                    () -> assertEquals("Smith", trainee3.getLastName()),
                    () -> assertEquals("James", trainee4.getLastName())
                     );
        }

        @Test
        void getUsername() {
            trainee1.setUsername("John.Doe");
            trainee2.setUsername("George.Fisher");
            trainee3.setUsername("Sam.Smith");
            trainee4.setUsername("Kate.James");
            assertAll(
                    () -> assertEquals("John.Doe", trainee1.getUsername()),
                    () -> assertEquals("George.Fisher", trainee2.getUsername()),
                    () -> assertEquals("Sam.Smith", trainee3.getUsername()),
                    () -> assertEquals("Kate.James", trainee4.getUsername())
                     );
        }

        @Test
        void getPassword() {
            trainee1.setPassword("1234567890");
            trainee2.setPassword("abcdefghij");
            trainee3.setPassword("klmnopqrst");
            trainee4.setPassword("ababababab");
            assertAll(
                    () -> assertEquals("1234567890", trainee1.getPassword()),
                    () -> assertEquals("abcdefghij", trainee2.getPassword()),
                    () -> assertEquals("klmnopqrst", trainee3.getPassword()),
                    () -> assertEquals("ababababab", trainee4.getPassword())
                     );
        }

        @Test
        void isActive() {
            trainee1.setActive(true);
            trainee2.setActive(false);
            trainee3.setActive(true);
            trainee4.setActive(false);
            assertAll(
                    () -> assertEquals(true, trainee1.isActive()),
                    () -> assertEquals(false, trainee2.isActive()),
                    () -> assertEquals(true, trainee3.isActive()),
                    () -> assertEquals(false, trainee4.isActive())
                     );
        }

        @Test
        void getId() {
            trainee1.setId(1);
            trainee2.setId(2);
            trainee3.setId(3);
            trainee4.setId(4);

            assertAll(
                    () -> assertEquals(1, trainee1.getId()),
                    () -> assertEquals(2, trainee2.getId()),
                    () -> assertEquals(3, trainee3.getId()),
                    () -> assertEquals(4, trainee4.getId())
                     );
        }

        @Test
        void getAddress() {
            trainee1.setAddress("address 1");
            trainee2.setAddress("address 2");
            trainee3.setAddress("address 3");
            trainee4.setAddress("address 4");
            assertAll(
                    () -> assertEquals("address 1", trainee1.getAddress()),
                    () -> assertEquals("address 2", trainee2.getAddress()),
                    () -> assertEquals("address 3", trainee3.getAddress()),
                    () -> assertEquals("address 4", trainee4.getAddress())
                     );
        }
    }
}