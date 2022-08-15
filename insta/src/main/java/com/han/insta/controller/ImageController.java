package com.han.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.han.insta.config.auth.CustomUserDetails;
import com.han.insta.dto.ImageDetailDto;
import com.han.insta.dto.ImageUploadDto;
import com.han.insta.entity.Image;
import com.han.insta.service.ImageService;
import com.han.insta.utils.Script;

@Controller
public class ImageController {
	
	@Autowired
	ImageService imageService;
	
	@GetMapping("/image/upload")
	public String uploadImage() {
		return "image/upload";
	}
	
	@GetMapping("/image/upload/{postId}")
	public String updateImage(@PathVariable int postId, Model model, @AuthenticationPrincipal CustomUserDetails principal) {
		
		Image image = imageService.findById(postId);
		ImageUploadDto imageuploadDto = image.toDto();
		
		if(image.getUser().getId() != principal.getId())
			return Script.locationMsg("/user/profile/"+principal.getId(), "잘못된 접근입니다", model);
		
		model.addAttribute("image", imageuploadDto);
		
		return "image/upload";
	}
	
	
	
	@GetMapping("/image/{imageId}")
	public String detailImage(@PathVariable int imageId, @AuthenticationPrincipal CustomUserDetails principal, Model model) {
		
		ImageDetailDto imageDetailDto = imageService.detailImage(imageId, principal.getId());
		
		
		model.addAttribute("detailDto", imageDetailDto);
		
		
		return "image/detail";
	}
	
	@GetMapping({"/", "/image/story"})
	public String story() {
		return "image/story";
	}
	
	@GetMapping("/image/popular")
	public String popular() {
		
		return "image/popular";
	}
	
	


}




