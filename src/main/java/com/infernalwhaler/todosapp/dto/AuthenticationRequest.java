package com.infernalwhaler.todosapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

public class AuthenticationRequest {

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 4, max = 30, message = "Password must be at least 4 characters long")
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
