package com.epam.labtaskspringcore.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class User {
    //    private UsernameGenerator usernameGenerator;

    // Q: would it be better to initiate first, last name and password with "" ?

    private String firstName = "";
    private String lastName = "";
    private String username = "";
    private String password = "";
    private boolean active = false;

}
