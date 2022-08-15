package com.han.insta.config.auth;


import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.han.insta.entity.User;
import com.han.insta.repository.UserRepository;


@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oauth2User = super.loadUser(userRequest);

		
		Map<String, Object> userInfo = oauth2User.getAttributes();
		
		String username = "facebook_"+(String) userInfo.get("id");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String email = (String) userInfo.get("email");
		String name = (String) userInfo.get("name");
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) { // 회원이 아닐시
			User user = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.name(name)
					.role("ROLE_USER")
					.build();
			userRepository.save(user);
			return new CustomUserDetails(user);
		}else { // 회원일시
			return new CustomUserDetails(userEntity);
		}
	
	}
}
