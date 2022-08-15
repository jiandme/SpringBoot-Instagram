package com.han.insta.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.han.insta.config.exception.CustomApiException;
import com.han.insta.repository.LikesRepository;

@Service
public class LikesService {
	
	@Autowired
	LikesRepository likesRepository;
	
	
	  @Transactional 
	  public void saveLikes(int imageId, int userId) { 
		  try {
			 likesRepository.insertLikes(imageId, userId); 
		} catch (Exception e) {
			throw new CustomApiException("이미 좋아요 상태 입니다");
		}
	  }
	 
	@Transactional
	public void deleteLikes(int imageId, int userId) {
		likesRepository.deleteByImageIdAndUserId(imageId, userId);
	}


	
	
	
}
