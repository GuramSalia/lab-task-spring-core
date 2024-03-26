package com.epam.labtaskspringcore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@Table(name = "GYM_USERS")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Slf4j
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "FAILED_LOGIN_ATTEMPTS")
    private Integer failedLoginAttempts;

    @Column(name = "BLOCK_STATUS")
    private Boolean isLocked;

    @Column(name = "BLOCK_START_TIME")
    private LocalDateTime blockStartTime;

    public void setUsername(String username) {
        if (this.username == null) {
            this.username = username;
        } else {
            log.warn("username cannot be changed");
        }
    }
}
