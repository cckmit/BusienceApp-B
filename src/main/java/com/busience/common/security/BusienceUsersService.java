package com.busience.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.busience.standard.dto.UserDto;
import com.busience.standard.service.UserService;


@Service
public class BusienceUsersService implements UserDetailsService{

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if(username == null) {
        	throw new UsernameNotFoundException("User "+username+" Not Found!");
        }

		return new BusienceSecurityUser(userService.selectUser(username));
	}
	
}
