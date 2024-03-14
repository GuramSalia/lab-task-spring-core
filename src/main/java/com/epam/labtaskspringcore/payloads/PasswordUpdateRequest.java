package com.epam.labtaskspringcore.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordUpdateRequest extends UsernamePassword {

    @NotBlank
    private String newPassword;

    public PasswordUpdateRequest(String username, String password, String newPassword) {
        super(username, password);
        this.newPassword = newPassword;
    }
}
