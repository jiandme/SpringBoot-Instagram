package com.han.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.han.insta.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
	
	@Modifying
	@Query(value = "INSERT INTO likes(imageId, userId, createDate) VALUES(:imageId, :userId, now())", nativeQuery = true)
	void insertLikes(@Param("imageId") int imageId, @Param("userId") int userId);
	
	
	void deleteByImageIdAndUserId(int imageId, int userId);
	
}
