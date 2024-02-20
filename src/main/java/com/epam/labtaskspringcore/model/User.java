package com.epam.labtaskspringcore.model;

import com.epam.labtaskspringcore.utils.UsernameGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;

@Setter
@Getter
public abstract class User {
    //    private UsernameGenerator usernameGenerator;

    // Q: would it be better to initiate first, last name and password with "" ?

    private String firstName = "";
    private String lastName = "";
    private String username;
    private String password;
    private boolean isActive;

    // move initialization of password and username to the service layer

    //    public User() {
    //        this.password = generateRandomPassword();
    //    }
    //    @Autowired
    //    public void setUsernameGenerator(UsernameGenerator usernameGenerator) {
    //        this.usernameGenerator = usernameGenerator;
    //    }
    //    public void setFirstName(String firstName) {
    //        this.firstName = firstName;
    //        this.setUsername();
    //    }
    //
    //    public void setLastName(String lastName) {
    //        this.lastName = lastName;
    //        this.setUsername();
    //    }
    //    public void setUsername() {
    //        if (usernameGenerator != null) {
    //
    //            this.username = usernameGenerator.generateUsername(this);
    //        } else {
    //            throw new IllegalStateException(
    //                    "UsernameGenerator not set. Call setUsernameGenerator before generating username.");
    //        }
    //    }
    //
    //    private String generateRandomPassword() {
    //        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    //        SecureRandom random = new SecureRandom();
    //        StringBuilder sb = new StringBuilder(10);
    //        for (int i = 0; i < 10; i++) {
    //            int randomIndex = random.nextInt(characters.length());
    //            sb.append(characters.charAt(randomIndex));
    //        }
    //        return sb.toString();
    //    }
}
