package com.epam.labtaskspringcore.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;

public abstract class User {
    private UsernameGenerator usernameGenerator;

    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String username;
    @Setter
    @Getter
    private String password;

    @Getter
    @Setter
    private boolean isActive;

    public User() {
        this.password = generateRandomPassword();
    }

    @Autowired
    public void setUsernameGenerator(UsernameGenerator usernameGenerator) {
        this.usernameGenerator = usernameGenerator;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.setUsername();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.setUsername();
    }

    public void setUsername() {
        if (usernameGenerator != null) {

            this.username = usernameGenerator.generateUsername(this);
        } else {
            throw new IllegalStateException(
                    "UsernameGenerator not set. Call setUsernameGenerator before generating username.");
        }
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }
        return sb.toString();
    }
}
