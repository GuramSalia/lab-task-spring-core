package com.epam.labtaskspringcore.controller;

import com.epam.labtaskspringcore.aspect.CheckUsernamePassword;
import com.epam.labtaskspringcore.aspect.LogRestDetails;
import com.epam.labtaskspringcore.api.PasswordUpdateRequest;
import com.epam.labtaskspringcore.api.UsernamePassword;
import com.epam.labtaskspringcore.service.UserService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CheckUsernamePassword
@LogRestDetails
public class UserManagementController {

    private final UserService userService;
    private final Counter user_login_get_requests_success_counter;
    private final Counter user_login_put_requests_success_counter;

    @Autowired
    public UserManagementController(
            UserService userService,
            MeterRegistry meterRegistry) {
        this.userService = userService;
        this.user_login_get_requests_success_counter = Counter
                .builder("user_login_get_requests_success_counter")
                .description("number of successful hits: GET /user/login")
                .register(meterRegistry);
        this.user_login_put_requests_success_counter = Counter
                .builder("user_login_put_requests_success_counter")
                .description("number of successful hits: PUT /user/login")
                .register(meterRegistry);
    }

    @Operation(summary = "User Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    @GetMapping("/user/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UsernamePassword usernamePassword) {
        user_login_get_requests_success_counter.increment();
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }

    @Operation(summary = "update password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    @PutMapping("/user/login")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody PasswordUpdateRequest usernamePassword) {
        String username = usernamePassword.getUsername();
        String currentPassword = usernamePassword.getPassword();
        String newPassword = usernamePassword.getNewPassword();
        userService.updatePassword(username, currentPassword, newPassword);
        user_login_put_requests_success_counter.increment();
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }
}
