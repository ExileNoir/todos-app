package com.infernalwhaler.todosapp.service;

import com.infernalwhaler.todosapp.dto.requests.TodoRequest;
import com.infernalwhaler.todosapp.dto.responses.TodoResponse;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */
@Service
public interface TodoService {

    TodoResponse createTodo(TodoRequest todoRequest) throws AccessDeniedException;

    List<TodoResponse> getAllTodos() throws AccessDeniedException;

    TodoResponse toggleTodoCompletion(long id) throws AccessDeniedException;

    void deleteTodo(long id) throws AccessDeniedException;
}
