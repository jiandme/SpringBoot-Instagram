package com.han.insta.service;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.han.insta.dto.ImageDetailDto;
import com.han.insta.dto.ImageUploadDto;
import com.han.insta.dto.PopularDto;
import com.han.insta.entity.Image;
import com.han.insta.repository.ImageRepository;

@Service
public class ImageService {
	
	@Autowired
	ImageRepository imageRepository;
	
	
	@Transactional
	public boolean postImage(int postId, ImageUploadDto imageUploadDto, int principalId) {

		if(imageUploadDto.getFile() != null) {
			String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
			String uploadForder= Paths.get("C:", "insta", "upload").toString();
			String profileUploadForder = Paths.get("FeedImage", today).toString();
			String uploadPath = Paths.get(uploadForder, profileUploadForder).toString();
			
			File dir = new File(uploadPath);
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			
			UUID uuid = UUID.randomUUID();
			String profileImageName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); 
			String postImage=(profileUploadForder+"\\"+profileImageName);
			
			try {
				File target = new File(uploadPath, profileImageName);
				imageUploadDto.getFile().transferTo(target);
	
			} catch (Exception e) {
				return false;
			}
			
			if(postId == 0) {
				
				
				Image image = imageUploadDto.toEntity(principalId, imageUploadDto.getCaption(), postImage);
				
				imageRepository.save(image);
				
				return true;

			}
			
			Image image = imageRepository.findById(postId);
			image.updateImage(postImage, imageUploadDto.getCaption());
			return true;
		}
		
		
		Image image = imageRepository.findById(postId);
		
		image.updateImage(image.getPostImage(), imageUploadDto.getCaption());
		
		return true;
	} 
	
	
	@Transactional
	public Image findById(int imageId) {
		Image image = imageRepository.findById(imageId);
		return image;
	}
	
	@Transactional
	public boolean deleteImage(int postId) {
		imageRepository.deleteById(postId);
		return true;
	}
	
	
	@Transactional
	public ImageDetailDto detailImage(int imageId, int principalId){
		Image image = imageRepository.imageDetail(imageId, principalId);
		ImageDetailDto imageDetailDto = new ImageDetailDto(image);
		
		return imageDetailDto;
	}
	
	@Transactional
	public List<ImageDetailDto> imageStory(int principalId, Pageable pageable){
		List<Image> images = imageRepository.imageStory(principalId, pageable);
		
		List<ImageDetailDto> imageList = new ArrayList<>();
		for(Image image : images)
			imageList.add(new ImageDetailDto(image));
		
		return imageList;
	}
	
	@Transactional
	public List<PopularDto> imagePopular(Pageable pageable){
		List<Image> images = imageRepository.imagePopular(pageable);
		List<PopularDto> popularList = new ArrayList<>();
		for(Image image : images)
			popularList.add(new PopularDto(image));
		
		return popularList;
	}
	
	
	
	
	
}
