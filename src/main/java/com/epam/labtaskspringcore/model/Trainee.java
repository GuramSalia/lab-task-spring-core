package com.epam.labtaskspringcore.model;

import java.util.Date;
import java.util.Objects;

public class Trainee extends User {
    private long traineeId;
    private String Address;
    private Date dob;

    public long getTraineeId() {return traineeId;}

    public void setTraineeId(long traineeId) {this.traineeId = traineeId;}

    public String getAddress() {return Address;}

    public void setAddress(String address) {this.Address = address;}

    public Date getDob() {return dob;}

    public void setDob(Date dob) {this.dob = dob;}

    @Override
    public String toString() {
        return "Trainer{" +
                "traineeId=" + traineeId +
                ", first-name='" + getFirstName() + '\'' +
                ", last-name='" + getLastName() + '\'' +
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
