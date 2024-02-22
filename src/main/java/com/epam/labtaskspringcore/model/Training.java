package com.epam.labtaskspringcore.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
public class Training {
    private int trainingId;
    private int traineeId;
    private int trainerId;
    private String name;
    private TrainingType type;
    //LocalDate still not working for reading from json 
    //@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date date;
    private int durationInMinutes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return getTrainingId() == training.getTrainingId() && getTraineeId() == training.getTraineeId() && getTrainerId() == training.getTrainerId() && getDurationInMinutes() == training.getDurationInMinutes() && Objects.equals(
                getName(),
                training.getName()) && getType() == training.getType() && Objects.equals(getDate(),
                                                                                         training.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainingId(),
                            getTraineeId(),
                            getTrainerId(),
                            getName(),
                            getType(),
                            getDate(),
                            getDurationInMinutes());
    }

    @Override
    public String toString() {
        Calendar calendar = Calendar.getInstance();
        String dateString = "null";
        if (date != null) {
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dateString = year + "-" + month + "-" + day;
        }

        return "Training{" +
                "\n  id=" + trainingId +
                ", \n  traineeId=" + traineeId +
                ", \n  trainerId=" + trainerId +
                ", \n  name='" + name + '\'' +
                ", \n  type=" + type +
                ", \n  date=" + dateString +
                ", \n  durationInMinutes=" + durationInMinutes +
                '}';
    }
}
