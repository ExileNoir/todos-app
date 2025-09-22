package com.infernalwhaler.todosapp.dto;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
