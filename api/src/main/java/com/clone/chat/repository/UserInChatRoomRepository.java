package com.clone.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clone.chat.domain.User;
import com.clone.chat.domain.UserInChatRoom;

public interface UserInChatRoomRepository extends JpaRepository<UserInChatRoom, Long>{

	@Query("SELECT uc FROM UserInChatRoom uc "
			+ "JOIN uc.chatRoom c "
			+ "JOIN uc.user u "
			+ "WHERE c.name LIKE CONCAT('%',:search,'%') and u.id = :userId")
	public List<UserInChatRoom> findByUser(@Param("userId") String userId, @Param("search") String search);
}
