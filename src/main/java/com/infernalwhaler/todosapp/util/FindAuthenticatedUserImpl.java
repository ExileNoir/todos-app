package com.infernalwhaler.todosapp.util;

import com.infernalwhaler.todosapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

@Component
public class FindAuthenticatedUserImpl implements FindAuthenticatedUser {

    @Override
    public User getAuthenticatedUser() throws AccessDeniedException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException("Authentication required");
        }
        return (User) authentication.getPrincipal();
    }
}
