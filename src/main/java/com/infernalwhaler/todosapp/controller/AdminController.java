package com.infernalwhaler.todosapp.controller;

import com.infernalwhaler.todosapp.dto.responses.UserResponse;
import com.infernalwhaler.todosapp.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 23/09/2025
 */

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin REST API Endpoints", description = "Operations related to an admin")
public class AdminController {

    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    @ResponseStatus(OK)
    public List<UserResponse> getAllUsers() throws AccessDeniedException {
        return adminService.getAllUsers();
    }

    @PutMapping("/{userId}/role")
    @Operation(summary = "Promote a user to admin", description = "Promote a user to admin role")
    @ResponseStatus(OK)
    public UserResponse promoteToAdmin(@PathVariable @Min(1) long userId) throws AccessDeniedException {
        return adminService.promoteToAdmin(userId);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user", description = "Delete a non-admin user from the system")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable @Min(1) long userId) throws AccessDeniedException {
        adminService.deleteNonAdminUser(userId);
    }
}
