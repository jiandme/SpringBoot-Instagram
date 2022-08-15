package com.han.insta.service;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.han.insta.dto.UserProfileDto;
import com.han.insta.dto.UserUpdateDto;
import com.han.insta.entity.User;
import com.han.insta.repository.SubscribeRepository;
import com.han.insta.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SubscribeRepository subscribeRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@Transactional
	public User updateUser(UserUpdateDto userUpdateDto) {
		String encPassword = bCryptPasswordEncoder.encode(userUpdateDto.getPassword());
		userUpdateDto.setPassword(encPassword);
		User user = userRepository.findByUsername(userUpdateDto.getUsername());
		user.update(userUpdateDto.getPassword(), userUpdateDto.getName(), userUpdateDto.getPhone(), userUpdateDto.getGender(), userUpdateDto.getWebsite(), userUpdateDto.getBio(), userUpdateDto.getEmail());
		
		return user;
		
	}
	
	@Transactional
	public UserProfileDto findById(int profileUserId, int principalId) {
		User user = userRepository.findById(profileUserId);
		UserProfileDto profileDto = new UserProfileDto().EntityToDto(user);
		profileDto.setSubscribeCount(subscribeRepository.countByToUserId(profileUserId));
		profileDto.setImageCount(user.getImages().size());
		profileDto.setSubscribeState(subscribeRepository.existsByToUserIdAndFromUserId(profileUserId, principalId));

		
		
		return profileDto;
	}
	
	@Transactional
	public boolean updateProfileImage(int id, MultipartFile profileImage) {
		

		String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadForder= Paths.get("C:", "insta", "upload").toString();
		String profileUploadForder = Paths.get("profileImage", today).toString();
		String uploadPath = Paths.get(uploadForder, profileUploadForder).toString();
		
		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}
		
		
		UUID uuid = UUID.randomUUID();
		String profileImageName = uuid+"_"+profileImage.getOriginalFilename(); 
		
		try {
			File target = new File(uploadPath, profileImageName);
			profileImage.transferTo(target);

		} catch (Exception e) {
			return false;
		}
		
		User user = userRepository.findById(id);
				
		user.updateProfileImage(profileUploadForder+"\\"+profileImageName);
		
		
		return true;
	}


	
	
	
}
