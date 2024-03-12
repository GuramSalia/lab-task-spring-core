package com.epam.labtaskspringcore.payloads;

import lombok.Data;



@Data
public class PasswordUpdateRequest {
    private String username;
    private String oldPassword;
    private String newPassword;

    public PasswordUpdateRequest(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
