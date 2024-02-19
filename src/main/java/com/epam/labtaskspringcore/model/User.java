package com.epam.labtaskspringcore.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;

public abstract class User {
    private UsernameGenerator usernameGenerator;

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    private boolean isActive;

    public User() {
        this.password = generateRandomPassword();
    }

    @Autowired
    public void setUsernameGenerator(UsernameGenerator usernameGenerator) {
        this.usernameGenerator = usernameGenerator;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.setUsername();
    }

    public String getLastName() {
        return lastName;

    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.setUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        if (usernameGenerator != null) {

            this.username = usernameGenerator.generateUsername(this);
        } else {
            throw new IllegalStateException(
                    "UsernameGenerator not set. Call setUsernameGenerator before generating username.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
