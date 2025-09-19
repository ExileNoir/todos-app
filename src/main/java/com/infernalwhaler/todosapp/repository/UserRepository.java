package com.infernalwhaler.todosapp.repository;

import com.infernalwhaler.todosapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sdeseure
 * @project todos-app
 * @date 19/09/2025
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
