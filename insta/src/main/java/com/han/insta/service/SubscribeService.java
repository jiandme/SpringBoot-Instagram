package com.han.insta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.han.insta.dto.SubscribeDto;
import com.han.insta.entity.Subscribe;
import com.han.insta.entity.User;
import com.han.insta.repository.SubscribeRepository;

@Service
public class SubscribeService {
	
	@Autowired
	SubscribeRepository subscribeRepository;
	@Autowired
	EntityManager em;
	
	@Transactional
	public void saveSubscribe(int toUserId, int principalId) {
		User user = new User(principalId);
		Subscribe subscribe = new Subscribe(toUserId, user);
		
		subscribeRepository.save(subscribe);

	}

	
	
	@Transactional
	public void deleteSubscribe(int toUserId, int principalId) {
		
		subscribeRepository.deleteByToUserIdAndFromUserId(toUserId, principalId);

		
	}


	@Transactional(readOnly = true)
	public List<SubscribeDto> subscribeList(int toUserId, int principalId) {
		
		
		
		/*
		 * List<SubscribeDto> subscribeDto =
		 * subscribeRepository.findBytoUserId(toUserId); List<Subscribe> subscribeList =
		 * subscribeRepository.findByFromUserId(principalId);
		 * 
		 * 
		 * for(Subscribe sub : subscribeList) { for(SubscribeDto dto : subscribeDto) {
		 * if(sub.getToUserId()==dto.getId()) dto.setSubscribeState(true); }
		 * 
		 * }
		 */
			 
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.name, u.profileImage, ");
		sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.fromUserId ");
		sb.append("WHERE s.toUserId = ?"); 


		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, toUserId);
		
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDto =  result.list(query, SubscribeDto.class);
		  
		return subscribeDto;
		 

	}
	
	
}
