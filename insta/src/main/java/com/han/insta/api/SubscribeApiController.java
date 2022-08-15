package com.han.insta.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.han.insta.config.auth.CustomUserDetails;
import com.han.insta.service.SubscribeService;

@RestController
public class SubscribeApiController {
	
	@Autowired
	SubscribeService subscribeService;
	
	@PostMapping("/api/subscribe/{toUserId}") 
	public ResponseEntity<String> subscribe(@PathVariable int toUserId, @AuthenticationPrincipal CustomUserDetails principal){
	  
		subscribeService.saveSubscribe(toUserId, principal.getId());
		return ResponseEntity.ok().body("구독성공");
		  
	  
	}
	
	@DeleteMapping("/api/subscribe/{toUserId}") 
	public ResponseEntity<String> unSubscribe(@PathVariable int toUserId, @AuthenticationPrincipal CustomUserDetails principal){
	  
		subscribeService.deleteSubscribe(toUserId, principal.getId());
		
		return ResponseEntity.ok().body("구독해제성공");
		  
	  
	}
	 
	


}
