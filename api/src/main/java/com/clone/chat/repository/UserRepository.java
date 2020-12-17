package com.clone.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.chat.domain.User;

public interface UserRepository extends JpaRepository<User, String> {



}
