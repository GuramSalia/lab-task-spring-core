package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.payloads.PasswordUpdateRequest;
import com.epam.labtaskspringcore.payloads.UsernamePassword;
import com.epam.labtaskspringcore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CheckUsernamePassword
@LogRestDetails
public class UserManagementController {

    private final UserService userService;

    @Autowired
    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsernamePassword usernamePassword) {
        // response code 204 because there is no content
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }

    @Operation(summary = "update password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    @PutMapping("/user/login")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdateRequest usernamePassword) {

        String username = usernamePassword.getUsername();
        String currentPassword = usernamePassword.getPassword();
        String newPassword = usernamePassword.getNewPassword();

        userService.updatePassword(username, currentPassword, newPassword);

        // response code 204 because there is no content
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }
}
