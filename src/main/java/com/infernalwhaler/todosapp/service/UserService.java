package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.UserResponse;

import java.nio.file.AccessDeniedException;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

public interface UserService {

    UserResponse getUser() throws AccessDeniedException;
}
