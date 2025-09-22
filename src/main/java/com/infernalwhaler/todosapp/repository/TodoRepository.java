package com.infernalwhaler.todosapp.repository;

import com.infernalwhaler.todosapp.model.Todo;
import com.infernalwhaler.todosapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByOwner(User owner);
}
