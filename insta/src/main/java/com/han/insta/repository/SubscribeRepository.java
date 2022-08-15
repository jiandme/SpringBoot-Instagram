package com.han.insta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.han.insta.dto.SubscribeDto;
import com.han.insta.entity.Subscribe;


public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
	
	//Subscribe findById(int Id);

	@Modifying // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!
	
	
	//@Query(value = "select u.*, s.* from user u left join fetch subscribe s on u.id = s.fromUserId where toUserId = :toUserId", nativeQuery = true)
	@Query("select distinct s from Subscribe s left join fetch s.fromUser where toUserId = :toUserId")
	List<SubscribeDto> findBytoUserId(@Param("toUserId") int toUserId);
	
	
	@Modifying
	@Query(value= "insert into Subscribe(toUserId, fromUserId, createDate) VALUES(:toUserId, :fromUserId, now())", nativeQuery = true)
	void saveSubscribe(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);
	
	@Modifying
	@Query("delete from Subscribe where toUserId = :toUserId and fromUserId = :fromUserId")
	void deleteByToUserIdAndFromUserId(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);
	
	int countByToUserId(int toUserId);
	
	boolean existsByToUserIdAndFromUserId(int toUserId, int fromUserId);
	
	List<Subscribe> findByFromUserId(int fromUserId);
	
	
	/*
	 * @Query(value = "SELECT u.id, u.name, u.profileImage," +
	 * "if((SELECT 1 FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = u.id), 1, 0) subscribeState\n"
	 * + "FROM user u INNER JOIN subscribe s\n" + "ON u.id = s.fromUserId\n" +
	 * "WHERE s.toUserId = :toUserId", nativeQuery = true) List<MappingDto>
	 * hahaha(@Param("toUserId") int toUserId, @Param("fromUserId") int fromUserId);
	 */
	
}
