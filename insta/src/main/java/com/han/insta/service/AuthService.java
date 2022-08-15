package com.han.insta.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.han.insta.dto.SignupDto;
import com.han.insta.entity.User;
import com.han.insta.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public Map<String, String> validHandling(Errors errors) {
		Map<String, String> validResult = new HashMap<>();
		for(FieldError error : errors.getFieldErrors()) {
			validResult.put("valid_"+error.getField(), error.getDefaultMessage());
		}
		return validResult;
	}
	
	@Transactional
	public boolean findUser(String username) {
		return userRepository.existsByUsername(username);
	}
	
	@Transactional
	public User findByUser(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Transactional
	public void userSignup(SignupDto signupDto) {
		String encPassword = bCryptPasswordEncoder.encode(signupDto.getPassword());
		signupDto.setPassword(encPassword);
		User user = signupDto.toEntity();
		userRepository.save(user);
		
	}
	
	
	
}
