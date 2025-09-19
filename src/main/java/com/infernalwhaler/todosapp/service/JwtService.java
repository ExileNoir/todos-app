package com.infernalwhaler.todosapp.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 19/09/2025
 */

public interface JwtService {

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(Map<String, Object> claims, UserDetails userDetails);
}
