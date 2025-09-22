package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.requests.PasswordUpdateRequest;
import com.infernalwhaler.todosapp.dto.responses.UserResponse;
import com.infernalwhaler.todosapp.model.Authority;
import com.infernalwhaler.todosapp.model.User;
import com.infernalwhaler.todosapp.repository.UserRepository;
import com.infernalwhaler.todosapp.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, FindAuthenticatedUser findAuthenticatedUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser() throws AccessDeniedException {
        var user = findAuthenticatedUser.getAuthenticatedUser();

        return new UserResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList());
    }

    @Override
    public void deleteUser() throws AccessDeniedException {
        var user = findAuthenticatedUser.getAuthenticatedUser();

        if (isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself");
        }
        userRepository.delete(user);
    }

    @Override
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) throws AccessDeniedException {
        var user = findAuthenticatedUser.getAuthenticatedUser();

        if (!isOldPasswordCorrect(user.getPassword(), passwordUpdateRequest.getOldPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }

        if (!isNewPasswordConfirmed(passwordUpdateRequest.getNewPassword(), passwordUpdateRequest.getConfirmNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New passwords doe not match");
        }

        if (!isNewPasswordDifferent(passwordUpdateRequest.getOldPassword(), passwordUpdateRequest.getNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old and New passwords must be different");
        }
    }

    private boolean isLastAdmin(final User user) {
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));

        if (isAdmin) {
            long adminCount = userRepository.countAdminUsers();
            return adminCount <= 1;
        }
        return false;
    }

    private boolean isOldPasswordCorrect(final String currentEncodedPassword, final String confirmedOldPassword) {
        return passwordEncoder.matches(confirmedOldPassword,currentEncodedPassword);
    }

    private boolean isNewPasswordConfirmed(final String newPassword, final String confirmNewPassword) {
        return newPassword.equals(confirmNewPassword);
    }

    private boolean isNewPasswordDifferent(final String confirmedOldPassword, final String newPassword) {
        return !newPassword.equals(confirmedOldPassword);
    }

}
