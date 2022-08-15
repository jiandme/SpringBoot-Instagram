package com.han.insta.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.han.insta.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	
	Image findById(int postId);
	
	@Query(value = "SELECT i.id, i.caption, i.postImage, i.createDate,i.userId, COUNT(l.imageid)likeCount,\r\n"
				+ "(select count(*) from likes where userId = :principalId and imageId = l.imageId)likeState \r\n"
				+ "from image i\r\n"
				+ "left JOIN likes l\r\n"
				+ "ON i.id = l.ImageId\r\n"
				+ "WHERE i.userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId)\r\n"
				+ "group by i.id\r\n"
				+ "ORDER BY id DESC", nativeQuery = true)
	List<Image> imageStory(@Param("principalId") int principalId, Pageable pageable);
	
	
	
	
	@Query(value="SELECT id, caption, postImage,createDate,userId, \r\n"
			  + "(Select COUNT(*) from likes where imageId = :imageId)likeCount,\r\n" 
			  + "(Select EXISTS (select id from likes where userId = :principalId and imageId = :imageId))likeState\r\n"
		 	  + "FROM image where id = :imageId", nativeQuery = true)
	Image imageDetail(@Param("imageId") int imageId, @Param("principalId") int principalId);
	
	
	@Query(value = "SELECT i.* FROM image i \r\n"
			  	 + "INNER JOIN (SELECT imageId, COUNT(imageId) likeCounts FROM likes GROUP BY imageId) c\r\n"
				 + "ON i.id = c.imageId  \r\n"
				 + "ORDER BY likeCounts DESC", nativeQuery = true)
	List<Image> imagePopular(Pageable pageable);

}
