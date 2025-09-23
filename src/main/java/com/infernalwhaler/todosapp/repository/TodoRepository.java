package com.infernalwhaler.todosapp.repository;

import com.infernalwhaler.todosapp.model.Todo;
import com.infernalwhaler.todosapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 22/09/2025
 */

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByOwner(User owner);

    Optional<Todo> findByIdAndOwner(Long id, User owner);
}
