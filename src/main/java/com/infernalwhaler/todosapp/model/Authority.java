package com.infernalwhaler.todosapp.model;

import jakarta.persistence.Embeddable;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 18/09/2025
 */

@Embeddable
public class Authority implements GrantedAuthority {

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority() {
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

