package com.han.insta.api;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.han.insta.config.auth.CustomUserDetails;
import com.han.insta.dto.SubscribeDto;
import com.han.insta.dto.UserUpdateDto;
import com.han.insta.entity.User;
import com.han.insta.repository.UserRepository;
import com.han.insta.service.AuthService;
import com.han.insta.service.SubscribeService;
import com.han.insta.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	SubscribeService subscribeService;
	
	@Autowired
	UserRepository userrepository;
	
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@PutMapping("/api/user/{id}")
	public ResponseEntity<?> userupdate(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, Errors errors, @AuthenticationPrincipal CustomUserDetails customUser) {
		
	

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
		
		
		User user = userService.updateUser(userUpdateDto);
		
		customUser.setPassword(user.getPassword());
		
		return ResponseEntity.ok().body(jsonObj);
		
	}
	
	
	@PutMapping("/api/user/profileImage/{id}") 
	public ResponseEntity<String> updateProfileImage(@PathVariable int id, MultipartFile profileImageFile){
	  
		
		  if(userService.updateProfileImage(id, profileImageFile)) { 
			  return ResponseEntity.ok().body("프로필 사진 수정에 성공하였습니다"); 
		  }
		  
		  else {
			  return ResponseEntity.badRequest().body("프로필 사진 수정에 실패하였습니다");
		  }
	  
	  
	}
	
	
	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal CustomUserDetails principal){
		
		List<SubscribeDto> subscribeDto = subscribeService.subscribeList(pageUserId, principal.getId());
		
		return ResponseEntity.ok().body(subscribeDto); 
	}
	 


}
