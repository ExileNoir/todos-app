package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.AuthenticationRequest;
import com.infernalwhaler.todosapp.dto.AuthenticationResponse;
import com.infernalwhaler.todosapp.dto.RegisterRequest;
import com.infernalwhaler.todosapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */
@TestPropertySource("/application-test.properties")
@SpringBootTest
class AuthenticationServiceImplTest {


    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    @Sql(scripts = "/data.sql")
    void setUp() {
    }


    @Test
    void register() throws Exception {
        var registerRequest = new RegisterRequest("Constance", "Deseure", "coco@gmail.com", "root1");

        authenticationService.register(registerRequest);

        assertNotNull(userRepository.findByEmail(registerRequest.getEmail()));
        assertEquals("Deseure", registerRequest.getLastName());
    }

    @Test
    void register_alreadyExists() throws Exception {
        var registerRequest = new RegisterRequest("Steven", "Deseure", "SD@gmail.com", "root1");

        authenticationService.register(registerRequest);
        assertThrows(Exception.class, () -> {
            authenticationService.register(registerRequest);
        });

        assertNotNull(userRepository.findByEmail(registerRequest.getEmail()));
        assertEquals("Deseure", registerRequest.getLastName());
    }

    @Test
    void login() throws Exception {
        var request = new AuthenticationRequest("coco@gmail.com", "root1");

        AuthenticationResponse login = authenticationService.login(request);

        assertNotNull(login.getToken());
    }

    @Test
    void login_nok() throws Exception {
        var request = new AuthenticationRequest("coco@gmail.com", "root");

        assertThrows(BadCredentialsException.class, () -> authenticationService.login(request));
    }
}