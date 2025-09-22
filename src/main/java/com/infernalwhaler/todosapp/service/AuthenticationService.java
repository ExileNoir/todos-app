package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.AuthenticationRequest;
import com.infernalwhaler.todosapp.dto.AuthenticationResponse;
import com.infernalwhaler.todosapp.dto.RegisterRequest;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 19/09/2025
 */

public interface AuthenticationService {

    void register(RegisterRequest input) throws Exception;

    AuthenticationResponse login(AuthenticationRequest request);
}
