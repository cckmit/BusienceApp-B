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
	public int userManageInsert(UserDto UserDto, Principal principal) {

		String encryptPw = pwEncoder.encode(UserDto.getUser_Password());
		
		UserDto.setUser_Password(encryptPw);
		UserDto.setUser_Modifier(principal.getName());

		return userService.userRegister(UserDto);
	}
	
	// update
	@PutMapping("/userManageUpdate")
	public int userManageUpdate(UserDto UserDto, Principal principal) {

		UserDto.setUser_Modifier(principal.getName());

		return userService.userModify(UserDto);
	}

	@PutMapping("/userManagePW")
	public int userManagePW(UserDto UserDto, Principal principal) {
		
		String encryptPw = pwEncoder.encode(UserDto.getUser_Password());
		
		UserDto.setUser_Password(encryptPw);
		UserDto.setUser_Modifier(principal.getName());

		return userService.passwordModify(UserDto);
	}

}
