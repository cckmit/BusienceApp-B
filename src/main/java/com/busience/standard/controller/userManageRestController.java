package com.busience.standard.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.UserDto;
import com.busience.standard.service.UserService;

@RestController("userManageRestController")
@RequestMapping("userManageRest")
public class userManageRestController {

	@Autowired
	PasswordEncoder pwEncoder;
	
	@Autowired
	UserService userService;
			
	@GetMapping("/userManageRestSelect")
	public List<UserDto> userManageRestSelect() {
		return userService.selectUserList();
	}

	// insert
	@PostMapping("/userManageInsert")
	public String userManageInsert(UserDto UserDto, Principal principal) {

		String encryptPw = pwEncoder.encode(UserDto.getUSER_PASSWORD());
		
		UserDto.setUSER_PASSWORD(encryptPw);
		UserDto.setUSER_MODIFIER(principal.getName());
		
		//유저등록
		userService.userRegister(UserDto);
		
		return "Success";
	}
	
	// update
	@PutMapping("/userManageUpdate")
	public String userManageUpdate(UserDto UserDto, Principal principal) {

		UserDto.setUSER_MODIFIER(principal.getName());
		
		userService.userModify(UserDto);
		
		return "Success";
	}

	@PutMapping("/userManagePW")
	public String userManagePW(UserDto UserDto, Principal principal) {
		
		String encryptPw = pwEncoder.encode(UserDto.getUSER_PASSWORD());
		
		UserDto.setUSER_PASSWORD(encryptPw);
		UserDto.setUSER_MODIFIER(principal.getName());
		
		userService.passwordModify(UserDto);
		
		return "Success";
	}

}
