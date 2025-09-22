package com.infernalwhaler.todosapp.controller;

import com.infernalwhaler.todosapp.dto.PasswordUpdateRequest;
import com.infernalwhaler.todosapp.dto.UserResponse;
import com.infernalwhaler.todosapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User REST API Endpoints", description = "Operations related to info about current user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    @Operation(summary = "User information",description = "Cet current user info")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserInfo() throws AccessDeniedException {
        return userService.getUser();
    }

    @DeleteMapping
    @Operation(summary = "Delete user",description = "Delete current user account")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser() throws AccessDeniedException {
        userService.deleteUser();
    }

    @PutMapping("/password")
    @Operation(summary = "Password update",description = "Change user password after verification")
    @ResponseStatus(HttpStatus.OK)
    public void passwordUpdate(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) throws AccessDeniedException {
        userService.updatePassword(passwordUpdateRequest);
    }


}
