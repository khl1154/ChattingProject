package com.clone.chat.repository;

import com.clone.chat.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = {"file"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(String userId);
}
