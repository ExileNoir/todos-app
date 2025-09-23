package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.responses.UserResponse;
import com.infernalwhaler.todosapp.model.Authority;
import com.infernalwhaler.todosapp.model.User;
import com.infernalwhaler.todosapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 23/09/2025
 */

@Service
public class AdminServiceImpl implements AdminService {


    private final UserRepository userRepository;


    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() throws AccessDeniedException {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::convertToUserResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse promoteToAdmin(long userId) throws AccessDeniedException {
        var userById = verifyAndGetUserById(userId);

        if (hasAdminRole(userById)) {
            throw new ResponseStatusException(BAD_REQUEST, "User is already an Admin");
        }

        userById.getAuthorities().add(new Authority("ROLE_ADMIN"));

        return convertToUserResponse(userById);
    }

    @Override
    @Transactional
    public void deleteNonAdminUser(long userId) throws AccessDeniedException {
        var userById = verifyAndGetUserById(userId);

        if (hasAdminRole(userById)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("User with id '%d' has role Admin", userId));
        }
        userRepository.deleteById(userId);
    }

    private User verifyAndGetUserById(long userId) throws AccessDeniedException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format("User with id '%d' not found", userId)));
    }

    private boolean hasAdminRole(final User user) {
        return user.getAuthorities()
                .stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    private UserResponse convertToUserResponse(final User user) {
        return new UserResponse(
                user.getId(),
                user.getLastName() + " " + user.getFirstName(),
                user.getEmail(),
                user.getAuthorities()
        );
    }
}
