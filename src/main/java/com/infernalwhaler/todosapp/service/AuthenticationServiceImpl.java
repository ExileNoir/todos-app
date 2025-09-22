package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.requests.AuthenticationRequest;
import com.infernalwhaler.todosapp.dto.responses.AuthenticationResponse;
import com.infernalwhaler.todosapp.dto.requests.RegisterRequest;
import com.infernalwhaler.todosapp.model.Authority;
import com.infernalwhaler.todosapp.model.User;
import com.infernalwhaler.todosapp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 19/09/2025
 */

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     AuthenticationManager authenticationManager,
                                     JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void register(final RegisterRequest input) throws Exception {
        if (isEmailTaken(input.getEmail())) {
            throw new Exception("Email already exists");
        }
        final User user = buildNewUser(input);
        userRepository.save(user);
    }

    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private User buildNewUser(final RegisterRequest input) {
        final User user = new User();
        user.setId(0L);
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setAuthorities(initialAuthority());
        return user;
    }

    private List<Authority> initialAuthority() {
        boolean isFirstUser = userRepository.count() == 0;
        final List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));
        if (isFirstUser) {
            authorities.add(new Authority("ROLE_ADMIN"));
        }
        return authorities;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));

        final String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return new AuthenticationResponse(jwtToken);
    }
}
