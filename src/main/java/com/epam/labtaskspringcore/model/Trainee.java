package com.epam.labtaskspringcore.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
public class Trainee extends User {
    private int traineeId;
    private String address;
    //LocalDate still not working for reading from json
    //@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date dob;

    @Override
    public String toString() {
        String isActiveString = isActive() ? "true" : "false";
        Calendar calendar = Calendar.getInstance();
        String dobString = "null";
        if (dob != null) {
            calendar.setTime(dob);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dobString = year + "-" + month + "-" + day;
        }
        return "Customer{" +
                "\n  id=" + traineeId +
                ", \n  name='" + getFirstName() + ' ' + getLastName() + '\'' +
                ", \n  username='" + getUsername() + '\'' +
                ", \n  password='" + getPassword() + '\'' +
                ", \n  Address='" + getAddress() + '\'' +
                ", \n  dob=" + dobString +
                ", \n  isActive='" + isActiveString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return getTraineeId() == trainee.getTraineeId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTraineeId(), getFirstName(), getLastName());
    }
}
