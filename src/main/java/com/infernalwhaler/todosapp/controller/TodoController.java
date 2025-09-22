package com.infernalwhaler.todosapp.controller;

import com.infernalwhaler.todosapp.dto.requests.TodoRequest;
import com.infernalwhaler.todosapp.dto.responses.TodoResponse;
import com.infernalwhaler.todosapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo REST API Endpoints", description = "Operations for managing user todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @Operation(summary = "Create todo for user", description = "Create todo for the singed in user")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse createTodo(@RequestBody @Valid TodoRequest todoRequest) throws AccessDeniedException {
        return todoService.createTodo(todoRequest);
    }

    @GetMapping
    @Operation(summary = "Get all todos for user", description = "Fetch all todos from signed in user")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoResponse> getAllTodos() throws AccessDeniedException {
        return todoService.getAllTodos();
    }
}
