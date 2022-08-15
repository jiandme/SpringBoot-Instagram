package com.han.insta.dto;

import org.springframework.web.multipart.MultipartFile;

import com.han.insta.entity.Image;
import com.han.insta.entity.User;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ImageUploadDto {
	
	private int id;
	private MultipartFile file;
	private String caption;
	private String postImage;
	
	public Image toEntity(int writerId, String caption, String postImage) {
		return Image.builder()
				.user(new User(writerId))
				.caption(caption)
				.postImage(postImage)
				.build();
	}
	

}

