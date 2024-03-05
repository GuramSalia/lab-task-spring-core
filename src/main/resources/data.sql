-- Insert Trainers
INSERT INTO GYM_USERS (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, IS_ACTIVE) VALUES
('Tim', 'Smith', 'Tim.Smith', '123', TRUE),
('Sam', 'Jones', 'Sam.Jones', '123', TRUE),
('John', 'Doe', 'John.Doe', '123', TRUE),
('Jane', 'Smith', 'Jane.Smith', '123', TRUE);

INSERT INTO CUSTOMERS (DATE_OF_BIRTH, ADDRESS, USER_ID) VALUES
(DATE '2000-02-21', 'address of customer 1', 3),
(DATE '2000-01-21', 'address of customer 2', 4);

-- Insert TrainingTypes
INSERT INTO TRAINING_TYPES (TRAINING_TYPE_NAME) VALUES
('CARDIO'),
('STRENGTH'),
('HIIT'),
('YOGA'),
('PILATES'),
('GROUP'),
('PERSONAL');

-- Insert Trainers
INSERT INTO TRAINERS (USER_ID, TRAINING_TYPE_ID) VALUES
(1, 1), -- Tim is a CARDIO trainer
(2, 2); -- Sam is a STRENGTH trainer

-- Insert Trainings
INSERT INTO TRAININGS (CUSTOMER_ID, TRAINER_ID, TRAINING_NAME, TRAINING_TYPE_ID, TRAINING_DATE, TRAINING_DURATION) VALUES
(3, 1, 'Cardio Workout', 1, DATE '2024-02-21', 60),
(4, 2, 'Strength Training', 2, DATE '2024-02-22', 45);

INSERT INTO CUSTOMERS_TRAINERS (CUSTOMER_ID, TRAINER_ID) VALUES
(3, 1),
(3, 2),
(4, 1);
