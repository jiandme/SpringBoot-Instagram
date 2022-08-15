package com.han.insta.dto;

import java.util.ArrayList;
import java.util.List;

import com.han.insta.entity.Image;
import com.han.insta.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
	
	private int id;
	private String name;
	private String website;
	private String bio;
	private String profileImage;
	private List<Image> images;
	
	//추가
	private int imageCount;
	private boolean subscribeState;
	private int subscribeCount;

	public UserProfileDto EntityToDto(User user) {
		return UserProfileDto.builder()
				.id(user.getId())
				.name(user.getName())
				.website(user.getWebsite())
				.bio(user.getBio())
				.profileImage(user.getProfileImage())
				.images(imageList(user.getImages()))
				.build();
	}
	
	
	public List<Image> imageList(List<Image> images){
		
		List<Image> imageList = new ArrayList<>();
		
		for(Image image : images)
			imageList.add(new Image(image));
		return imageList;
		
	}
	

	
	

}
