package com.epam.labtaskspringcore.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TraineeUpdateRequest extends UsernamePassword {

    //    a.	Request
    //        I.	Username (required)
    //        II.    Password (required)
    //        III.    First Name (required)
    //        III.	Last Name (required)
    //        IV.	Date of Birth (optional)
    //        V.	Address (optional)
    //        VI.	Is Active (required)


    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private Boolean isActive;

    public TraineeUpdateRequest(
            String username,
            String password,
            String firstName,
            String lastName,
            Date dateOfBirth,
            String address,
            boolean isActive) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
    }


}
