package com.infernalwhaler.todosapp.util;

import com.infernalwhaler.todosapp.model.User;

import java.nio.file.AccessDeniedException;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

public interface FindAuthenticatedUser {

    User getAuthenticatedUser() throws AccessDeniedException;
}
