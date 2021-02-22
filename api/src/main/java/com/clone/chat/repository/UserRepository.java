package com.clone.chat.repository;

import com.clone.chat.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(value = "User.friends", attributePaths = "file", type = EntityGraph.EntityGraphType.LOAD)
    public Optional<User> findById(String id);
    @EntityGraph(attributePaths = "file", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByIdEquals(String userId);

    @Query("select a from User a where a.id in :userIds")
    Set<User> findAllById(@Param("userIds") Set<String> userIds);
}
