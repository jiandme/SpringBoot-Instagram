package com.han.insta.dto;

import java.util.ArrayList;
import java.util.List;

import com.han.insta.entity.Comment;
import com.han.insta.entity.Image;

import lombok.Data;


@Data
public class ImageDetailDto {
	
	private int imageId;
	private String name;
	private String profileImage;
	private String caption;
	private String postImage;
	private int likeCount;
	private boolean likeState;
	private List<CommentDto> commentDto;
	

	
	public ImageDetailDto(Image image) {
		this.imageId = image.getId();
		this.name = image.getUser().getName();
		this.profileImage = image.getUser().getProfileImage();
		this.caption = image.getCaption();
		this.postImage = image.getPostImage();
		this.likeCount = image.getLikeCount();
		this.likeState = image.isLikeState();
		this.commentDto = commentList(image.getComments());
	

	}
	
	public List<CommentDto> commentList(List<Comment> comments){
		
		List<CommentDto> commentList = new ArrayList<>();
		
		for(Comment comment : comments)
			commentList.add(new CommentDto(comment));
		return commentList;
		
	}
	
}

