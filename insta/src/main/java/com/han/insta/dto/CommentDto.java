package com.han.insta.dto;

import javax.validation.constraints.NotBlank;

import com.han.insta.entity.Comment;
import com.han.insta.entity.Image;
import com.han.insta.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CommentDto {
	
	private int id;
	
	@NotBlank(message = "댓글을 입력해주세요")
	private String content;
	
	private int imageId;
	
	private int userId;
	
	private String name;
	
	public Comment toEntity(int imageId, User user) {
		return Comment.builder()
				.content(content)
				.image(new Image(imageId))
				.user(user)
				.build();
	}
	
	public CommentDto(Comment comment) {
		this.id = comment.getId();
		this.content = comment.getContent();
		this.imageId = comment.getImage().getId();
		this.userId = comment.getUser().getId();
		this.name = comment.getUser().getName();
	}
	

}
