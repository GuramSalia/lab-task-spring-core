package com.epam.labtaskspringcore.model;

import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Trainee extends User {
    private int traineeId;
    private String address;
    private Date dob;

    @Override
    public String toString() {
        String isActiveString = isActive() ? "true" : "false";
        return "Customer{" +
                "\n  id=" + traineeId +
                ", \n  name='" + getFirstName() + '\'' + getLastName() + '\'' +
                ", \n  username='" + getUsername() + '\'' +
                ", \n  password='" + getPassword() + '\'' +
                ", \n  Address='" + getAddress() +'\'' +
                ", \n  dob='" + getDob() +'\'' +
                ", \n  isActive='" + isActiveString +'\'' +
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
