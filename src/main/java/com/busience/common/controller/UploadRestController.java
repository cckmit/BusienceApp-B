package com.busience.common.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.busience.common.dto.FileDto;

@RestController
public class UploadRestController {
	
	@Value("${spring.datasource.username}")
	private String username;
			
	@PostMapping("/uploadAjax")
	public List<FileDto> uploadAjax(@RequestParam("uploadFile") MultipartFile[] uploadFile) {
			
		//폴더 경로
		String uploadFolder = "/home/hosting_users/"+username+"/tomcat/conf/files";
		 
		//폴더 만들기 
		File uploadPath = new File(uploadFolder, getFolder());
		
		//리턴할 파일목록
		List<FileDto> fileDtoList = new ArrayList<FileDto>();
		FileDto fileDto = new FileDto();
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		  
		for(MultipartFile multipartFile : uploadFile) {
			UUID uuid = UUID.randomUUID();
			String originalName = multipartFile.getOriginalFilename();
			String newName = uuid+"_"+multipartFile.getOriginalFilename();
			
			fileDto.setOriginalFileName(originalName);
			fileDto.setFileName(newName);
			fileDto.setFileSize(multipartFile.getSize());
			fileDto.setSavePath(uploadPath.toString());
				  
			File saveFile = new File(uploadPath, newName);
			  
			try {
				fileDtoList.add(fileDto);
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return fileDtoList;
	}
	
	private String getFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
		return str.replace("-", File.separator);
	}
}
