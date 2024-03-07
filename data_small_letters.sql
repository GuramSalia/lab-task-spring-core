-- Insert Trainers
INSERT INTO gym_users (first_name, last_name, username, password, is_active) VALUES
('Tim', 'Smith', 'Tim.Smith', '123', TRUE),
('Sam', 'Jones', 'Sam.Jones', '123', TRUE),
('John', 'Doe', 'John.Doe', '123', TRUE),
('Jane', 'Jameson', 'Jane.Smith', '123', TRUE);

INSERT INTO trainees (date_of_birth, address, user_id) VALUES
(DATE '2000-02-21', 'address of TRAINEE 1', 3),
(DATE '2000-01-21', 'address of TRAINEE 2', 4);

-- Insert TrainingTypes
INSERT INTO training_types (training_type_name) VALUES
('CARDIO'),
('STRENGTH'),
('HIIT'),
('YOGA'),
('PILATES'),
('GROUP'),
('PERSONAL');

-- Insert Trainers
INSERT INTO trainers (user_id, training_type_id) VALUES
(1, 1), -- Tim is a CARDIO trainer
(2, 2); -- Sam is a STRENGTH trainer

-- Insert Trainings
INSERT INTO trainings (trainee_id, trainer_id, training_name, training_type_id, training_date, training_duration) VALUES
(3, 1, 'Cardio Workout', 1, DATE '2024-02-21', 60),
(4, 2, 'Strength Training', 2, DATE '2024-02-22', 45);

INSERT INTO trainees_trainers (trainee_id, trainer_id) VALUES
(3, 1),
(3, 2),
(4, 1);
