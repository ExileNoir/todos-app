package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.responses.UserResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 23/09/2025
 */

public interface AdminService {

    List<UserResponse> getAllUsers() throws AccessDeniedException;

    UserResponse promoteToAdmin(long userId) throws AccessDeniedException;

    void deleteNonAdminUser(long userId) throws AccessDeniedException;
}
