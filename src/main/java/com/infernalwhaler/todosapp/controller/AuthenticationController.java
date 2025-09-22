package com.infernalwhaler.todosapp.controller;

import com.infernalwhaler.todosapp.dto.AuthenticationRequest;
import com.infernalwhaler.todosapp.dto.AuthenticationResponse;
import com.infernalwhaler.todosapp.dto.RegisterRequest;
import com.infernalwhaler.todosapp.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 19/09/2025
 */

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication REST API Endpoints",
        description = "Operations related to register & login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a user", description = "Create new user in database")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        authenticationService.register(registerRequest);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Operations related to register & login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest authRequest) throws Exception {
        return authenticationService.login(authRequest);
    }

}
