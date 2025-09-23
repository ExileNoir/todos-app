package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.requests.TodoRequest;
import com.infernalwhaler.todosapp.dto.responses.TodoResponse;
import com.infernalwhaler.todosapp.model.Todo;
import com.infernalwhaler.todosapp.repository.TodoRepository;
import com.infernalwhaler.todosapp.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @Override
    @Transactional
    public TodoResponse toggleTodoCompletion(final long id) throws AccessDeniedException {
        var currentUser = findAuthenticatedUser.getAuthenticatedUser();

        var todoByIdAndOwner = todoRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format("Todo with '%d' not Found", id)));
        todoByIdAndOwner.setComplete(!todoByIdAndOwner.isComplete());

        var updatedTodo = todoRepository.save(todoByIdAndOwner);
        return convertToTodoResponse(updatedTodo);
    }

    @Override
    @Transactional
    public void deleteTodo(long id) throws AccessDeniedException {
        var currentUser = findAuthenticatedUser.getAuthenticatedUser();

        var todoByIdAndOwner = todoRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format("Todo with '%d' not Found", id)));

        todoRepository.delete(todoByIdAndOwner);
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
