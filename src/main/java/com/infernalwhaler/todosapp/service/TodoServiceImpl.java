package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.requests.TodoRequest;
import com.infernalwhaler.todosapp.dto.responses.TodoResponse;
import com.infernalwhaler.todosapp.model.Todo;
import com.infernalwhaler.todosapp.repository.TodoRepository;
import com.infernalwhaler.todosapp.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional
    public TodoResponse createTodo(final TodoRequest todoRequest) throws AccessDeniedException {
        var currentUser = findAuthenticatedUser.getAuthenticatedUser();

        var savedTodo = todoRepository.save(
                new Todo(
                        todoRequest.getTitle(),
                        todoRequest.getDescription(),
                        todoRequest.getPriority(),
                        false,
                        currentUser));

        return convertToTodoResponse(savedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() throws AccessDeniedException {
        var currentUser = findAuthenticatedUser.getAuthenticatedUser();

        return todoRepository.findByOwner(currentUser)
                .stream()
                .map(this::convertToTodoResponse)
                .toList();
    }

    private TodoResponse convertToTodoResponse(final Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete());
    }
}
