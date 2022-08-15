package com.han.insta.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	
	private int id;
	private String name;
	private String profileImage;
	private BigInteger subscribeState;

	
	
	/*
	 * public SubscribeDto(Subscribe subscribe) { this.id =
	 * subscribe.getFromUser().getId(); this.name =
	 * subscribe.getFromUser().getName(); this.profileImage =
	 * subscribe.getFromUser().getProfileImage(); this.subscribeState = false; }
	 */
	 



	
}
