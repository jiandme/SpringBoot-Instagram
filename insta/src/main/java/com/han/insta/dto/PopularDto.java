package com.han.insta.dto;

import com.han.insta.entity.Image;

import lombok.Data;


@Data
public class PopularDto {
	
	private int imageId;
	private String postImage;

	public PopularDto(Image image) {
		this.imageId = image.getId();
		this.postImage = image.getPostImage();
	

	}
	
}

