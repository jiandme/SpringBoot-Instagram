package com.han.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.han.insta.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	

}
