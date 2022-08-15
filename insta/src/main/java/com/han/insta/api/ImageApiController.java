package com.han.insta.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.han.insta.config.auth.CustomUserDetails;
import com.han.insta.dto.ImageDetailDto;
import com.han.insta.dto.ImageUploadDto;
import com.han.insta.dto.PopularDto;
import com.han.insta.entity.Image;
import com.han.insta.repository.ImageRepository;
import com.han.insta.service.ImageService;

@RestController
public class ImageApiController {
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	ImageRepository imageRepository;
	
	@PostMapping("/api/image") 
	public ResponseEntity<String> imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal CustomUserDetails principal){
		

		if(!imageService.postImage(0, imageUploadDto, principal.getId()))
			return ResponseEntity.badRequest().body("게시글 작성에 실패하였습니다");


		return ResponseEntity.ok().body("게시글 작성이 완료되었습니다"); 
		
	}
	
	@PutMapping("/api/image/{postId}") 
	public ResponseEntity<String> imageUpdate(@PathVariable int postId, ImageUploadDto imageUploadDto, @AuthenticationPrincipal CustomUserDetails principal){
		
		Image image = imageService.findById(postId);
		if(image.getUser().getId() != principal.getId())
			return ResponseEntity.badRequest().body("잘못된 접근입니다");
		if(!imageService.postImage(postId, imageUploadDto, principal.getId()))
			return ResponseEntity.badRequest().body("게시글 수정에 실패하였습니다");

		return ResponseEntity.ok().body("게시글 수정이 완료되었습니다"); 
	}
	
	@DeleteMapping("/api/image/{postId}") 
	public ResponseEntity<String> imageDelete(@PathVariable int postId, @AuthenticationPrincipal CustomUserDetails principal){
		
		Image image = imageService.findById(postId);
		
		if(image.getUser().getId() != principal.getId())
			return ResponseEntity.badRequest().body("잘못된 접근입니다");
		
		
		if(!imageService.deleteImage(postId))
			return ResponseEntity.badRequest().body("게시글 삭제에 실패하였습니다");


		return ResponseEntity.ok().body("게시글 삭제가 완료되었습니다"); 
	}
	
	
	@GetMapping("/api/image/story")
	public List<ImageDetailDto> imageStory(@AuthenticationPrincipal CustomUserDetails principal, @PageableDefault(size=3) Pageable pageable){
		List<ImageDetailDto> images =  imageService.imageStory(principal.getId(), pageable);
		
		return images;
	}
	
	@GetMapping("/api/image/popular")
	public List<PopularDto> imagePopular(@PageableDefault(size=6) Pageable pageable){
		List<PopularDto> images =  imageService.imagePopular(pageable);
		return images;
	}
	
	


}
