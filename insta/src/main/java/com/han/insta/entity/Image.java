package com.han.insta.entity;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.han.insta.dto.ImageUploadDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name="userId", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"images"})
	private User user;
	
	//private int writerId; 
	
	private String caption; 
	
	private String postImage; 
	
	private int likeCount;

	private boolean likeState;
	
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) 
	private List<Comment> comments;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	public void updateImage(String postImage, String caption) {
		this.postImage = postImage;
		this.caption = caption;
	}
	
	
	public Image(Image image) {
		
		this.id = image.getId();
		this.caption = image.getCaption();
		this.postImage = image.getPostImage();
	}
	
	public Image(int id) {
		
		this.id = id;

	}
	
	
	public ImageUploadDto toDto() {
		return ImageUploadDto.builder()
				.id(id)
				.caption(caption)
				.postImage(postImage)
				.build();
	}



	

	


}
