package com.han.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.han.insta.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	boolean existsByUsername(String username);
	
	User findByUsername(String username);
	
	User findById(int id);
}
