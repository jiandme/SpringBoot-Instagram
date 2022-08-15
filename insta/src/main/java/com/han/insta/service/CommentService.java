package com.han.insta.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.insta.dto.CommentDto;
import com.han.insta.entity.Comment;
import com.han.insta.entity.User;
import com.han.insta.repository.CommentRepository;
import com.han.insta.repository.UserRepository;

@Service
public class CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;

	@Transactional
	public CommentDto postComment(CommentDto commentDto, int principalId) {
		
		User user = userRepository.findById(principalId);
		Comment comment = commentDto.toEntity(commentDto.getImageId(), user);
		commentRepository.save(comment);
		commentDto.setId(comment.getId());
		commentDto.setName(user.getName());
		commentDto.setUserId(principalId);
		return commentDto;
		
	}
	
	@Transactional
	public void deleteComment(int commentId) {
		
		commentRepository.deleteById(commentId);
		
	}

	
}
