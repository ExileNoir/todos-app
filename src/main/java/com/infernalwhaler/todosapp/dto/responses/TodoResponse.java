package com.infernalwhaler.todosapp.dto.responses;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

public record TodoResponse(long id,
                           String title,
                           String description,
                           int priority,
                           boolean complete) {
}
