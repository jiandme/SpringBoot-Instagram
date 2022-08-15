package com.han.insta.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.han.insta.config.auth.CustomUserDetails;
import com.han.insta.service.LikesService;

@RestController
public class LikesApiController {
	
	@Autowired
	LikesService likesService;
	
	@PostMapping("/api/image/{imageId}/likes")
	public ResponseEntity<String> likes(@PathVariable int imageId, @AuthenticationPrincipal CustomUserDetails principal){
		
		likesService.saveLikes(imageId, principal.getId());
		return ResponseEntity.ok().body("좋아요성공");
	}
	
	@DeleteMapping("/api/image/{imageId}/likes")
	public ResponseEntity<String> unLikes(@PathVariable int imageId, @AuthenticationPrincipal CustomUserDetails principal){
		likesService.deleteLikes(imageId, principal.getId());
		return ResponseEntity.ok().body("좋아요해제성공");
	}

}
