package com.epam.labtaskspringcore.payloads;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UsernamePassword {
    String username;
    String password;

    public UsernamePassword(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
