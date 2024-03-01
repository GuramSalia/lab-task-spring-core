package com.epam.labtaskspringcore.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class User {

    private String firstName = "";
    private String lastName = "";
    private String username = "";
    private String password = "";
    private boolean active = false;
}
