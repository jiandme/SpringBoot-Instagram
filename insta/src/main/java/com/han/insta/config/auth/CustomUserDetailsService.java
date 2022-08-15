package com.han.insta.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.han.insta.entity.User;
import com.han.insta.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user  = userRepository.findByUsername(username);
		
		if(user == null) throw new UsernameNotFoundException("회원이 존재하지 않습니다");
		
		CustomUserDetails customUser = new CustomUserDetails(user);
		
		return customUser;
	}

	
}
