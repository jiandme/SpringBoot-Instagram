package com.han.insta.entity;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 50, unique = true, nullable = false)
	private String username;
	
	
	//@JsonIgnore
	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	private String phone;
	
	private String gender;
	
	private String profileImage;
	
	private String website;

	private String bio;
	
	private String role;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"user"})
	private List<Image> images; 

	
	@CreationTimestamp 
	private Timestamp createDate;
	
	public void update(String password, String name, String phone, String gender, String website, String bio, String email) {
		
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.website = website;
        this.bio = bio;
        this.email = email;
	
	}
	
	public void updateProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public User(int id) {
		this.id = id;
	}


	


}
