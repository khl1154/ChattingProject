package com.clone.chat.repository;

import com.clone.chat.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.chat.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {



}
