package com.clone.chat.redisRepository;

import java.util.List;

import com.clone.chat.domain.Room;
import com.clone.chat.domain.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRoomRepository extends CrudRepository<UserRoom, Long> {

//	@Query("SELECT uc FROM UserRoom uc "
//			+ "JOIN uc.chatRoom c "
//			+ "JOIN uc.user u "
//			+ "WHERE c.name LIKE CONCAT('%',:search,'%') and u.id = :userId")
//	public List<UserRoom> findByUser(@Param("userId") String userId, @Param("search") String search);

//	public List<UserRoom> findByUserId(String userId);
//	public List<UserRoom> findByUserIdIn(List<String> userIds);

    	@Query("" +
			"select a from UserRoom a where a.id = :userId" +
			"")
    public List<UserRoom> findAllByUserId(@Param("userId") String userId);

    public List<UserRoom> findAllByRoom(Room roomId);

    @Query("" +
            "select a from UserRoom a join a.room b where b.id = :roomId" +
            "")
    public List<UserRoom> findAllByRoom(@Param("roomId") Long roomId);

//    @Query("" +
//            "select a from UserRoom a join a.room b " +
//            "")
//    public List<UserRoom> findAllByRoom(Long userId);
}
