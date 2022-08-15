package com.han.insta.api;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.han.insta.config.auth.CustomUserDetails;
import com.han.insta.dto.CommentDto;
import com.han.insta.service.AuthService;
import com.han.insta.service.CommentService;

@RestController
public class CommentApiController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/api/comment")
	public ResponseEntity<?> postComment(@Valid @RequestBody CommentDto commentDto, Errors errors, @AuthenticationPrincipal CustomUserDetails principal){
		
		
		JsonObject jsonObj = new JsonObject();

		if(errors.hasErrors()) {
			
			//유효성검사에 실패한 필드와 메시지를 저장
			Map<String, String> validResult = authService.validHandling(errors);
			
			//필드를 key값으로 에러메시지 저장
			for(String key : validResult.keySet()) {
				jsonObj.addProperty(key, validResult.get(key));
			}
			
			return ResponseEntity.badRequest().body(jsonObj);
			
		}

		commentDto = commentService.postComment(commentDto,principal.getId()); 
		return ResponseEntity.ok().body(commentDto);
	}
	
	
	@DeleteMapping("/api/comment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable int commentId, int userId, @AuthenticationPrincipal CustomUserDetails principal){
		
		if(userId != principal.getId())
			return ResponseEntity.badRequest().body("잘못된 접근입니다");
		
		commentService.deleteComment(commentId);
		return ResponseEntity.ok().body("댓글삭제성공");
	}
	
	
	

}
