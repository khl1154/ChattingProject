package com.clone.chat.repository;

import com.clone.chat.domain.Friend;
import com.clone.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, String> {


    List<Friend> findByUserId(String id);
}
