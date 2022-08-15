package com.han.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.han.insta.config.auth.CustomUserDetails;
import com.han.insta.dto.UserProfileDto;
import com.han.insta.entity.User;
import com.han.insta.repository.ImageRepository;
import com.han.insta.service.AuthService;
import com.han.insta.service.UserService;
import com.han.insta.utils.Script;

@Controller
public class UserController {
	
	@Autowired
	AuthService authService;

	@Autowired
	UserService userService;
	
	@Autowired
	ImageRepository imageRepository;
	
	
	@GetMapping("user/update")
	public void updateDefault(@AuthenticationPrincipal CustomUserDetails customUser, Model model) {

		updateForm(customUser.getId(),customUser, model);
	}
	
	@GetMapping("user/profile")
	public String profileDefault(@AuthenticationPrincipal CustomUserDetails customUser, Model model) {

		return "redirect:/user/profile/"+customUser.getId();
	}

	
	
	
	@GetMapping("user/update/{id}")
	public String updateForm(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails customUser, Model model) {
		
		System.out.println(id);
		if(id != customUser.getId()) return Script.locationMsg("/user/profile/"+customUser.getId(), "잘못된 접근입니다", model);
		
		User user = authService.findByUser(customUser.getUsername());
		
		model.addAttribute("user",user);
		
		return "user/update";
	}
	
	@GetMapping("/user/profile/{id}")
	public String profile(@PathVariable int id, Model model, @AuthenticationPrincipal CustomUserDetails principal) {
		
		UserProfileDto profileDto = userService.findById(id, principal.getId());
		
		
		model.addAttribute("profileDto",profileDto);
		
		
		return "user/profile";
	}
	
	

}
