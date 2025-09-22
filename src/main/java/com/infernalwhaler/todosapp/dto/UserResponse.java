package com.infernalwhaler.todosapp.dto;

import com.infernalwhaler.todosapp.model.Authority;

import java.util.List;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

public record UserResponse(long id, String fullName, String email, List<Authority> authorities) {
}
