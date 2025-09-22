package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.requests.PasswordUpdateRequest;
import com.infernalwhaler.todosapp.dto.responses.UserResponse;

import java.nio.file.AccessDeniedException;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

public interface UserService {

    UserResponse getUser() throws AccessDeniedException;

    void deleteUser() throws AccessDeniedException;

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest) throws AccessDeniedException;
}
