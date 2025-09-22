package com.infernalwhaler.todosapp.repository;

import com.infernalwhaler.todosapp.dto.UserResponse;
import com.infernalwhaler.todosapp.model.User;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT COUNT(u) FROM User u JOIN u.authorities a WHERE a.authority = 'ROLE_ADMIN'")
    long countAdminUsers();


}
