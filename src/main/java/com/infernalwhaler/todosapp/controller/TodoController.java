package com.infernalwhaler.todosapp.controller;

import com.infernalwhaler.todosapp.dto.requests.TodoRequest;
import com.infernalwhaler.todosapp.dto.responses.TodoResponse;
import com.infernalwhaler.todosapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

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
    @ResponseStatus(CREATED)
    public TodoResponse createTodo(@RequestBody @Valid TodoRequest todoRequest) throws AccessDeniedException {
        return todoService.createTodo(todoRequest);
    }

    @GetMapping
    @Operation(summary = "Get all todos for user", description = "Fetch all todos from signed in user")
    @ResponseStatus(OK)
    public List<TodoResponse> getAllTodos() throws AccessDeniedException {
        return todoService.getAllTodos();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update todo for user", description = "Update todo for the signed in user")
    @ResponseStatus(OK)
    public TodoResponse toggleTodoCompletion(@PathVariable @Min(1) Long id) throws AccessDeniedException {
        return todoService.toggleTodoCompletion(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete todo from user", description = "Delete todo from the signed in user")
    @ResponseStatus(NO_CONTENT)
    public void deleteTodo(@PathVariable @Min(1) Long id) throws AccessDeniedException {
        todoService.deleteTodo(id);
    }
}
