package com.clone.chat.repository;

import com.clone.chat.domain.Friend;
import com.clone.chat.domain.FriendInfoId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendInfoId> {
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Friend> findAllByUserId(String userId);

}
