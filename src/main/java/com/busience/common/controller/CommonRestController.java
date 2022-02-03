package com.busience.common.controller;

import java.io.File;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.busience.common.dto.DtlDto;
import com.busience.common.dto.FileDto;
import com.busience.common.dto.MenuDto;
import com.busience.common.dto.TestCheckDto;
import com.busience.common.service.DtlService;
import com.busience.common.service.MenuService;
import com.busience.common.service.ProductionService;
import com.busience.common.service.TestCheckService;

@RestController
public class CommonRestController {
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	DtlService dtlService;
	
	@Autowired
	TestCheckService testCheckService;
	
	@Autowired
	ProductionService productionService;
	
	// 공통코드 찾기 (All)
	@GetMapping("/dtl_tbl_select")
	public List<DtlDto> dtl_tbl_select(HttpServletRequest request) {
		return dtlService.getAllDtl(Integer.parseInt(request.getParameter("NEW_TBL_CODE")));
	}
	
	// 공통코드 찾기 (true)
	@GetMapping("/dtlTrueSelect")
	public List<DtlDto> dtlTrueSelect(DtlDto dtlDto) {
		return dtlService.getDtl(dtlDto.getNEW_TBL_CODE());
	}
	
	//하위 메뉴 List
	@GetMapping("/menuList")
	public List<MenuDto> menuList(Principal principal) {
		return menuService.menuList(principal.getName());
	}
	
	@GetMapping("/bsapp2")
	public int bsapp2(String equip, int value) {
		return productionService.insertProduction(equip, value);
	}

	@GetMapping("tablet/testCheck")
	public List<TestCheckDto> testCheck() {
		return testCheckService.TestCheckList();
	}
	
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
