package com.busience.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.busience.common.dto.MemberDto;
import com.busience.common.service.MemberService;

import lombok.extern.java.Log;


@Service
@Log
public class BusienceUsersService implements UserDetailsService{

	@Autowired
	MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(username == null) {
        	throw new UsernameNotFoundException("User "+username+" Not Found!");
        }
		
		MemberDto mmap = memberService.getMemberView(username);
		
		MemberDto member = new MemberDto();
		
		member.setUSER_CODE(mmap.getUSER_CODE());
		member.setUSER_PASSWORD(mmap.getUSER_PASSWORD());
		member.setUSER_TYPE(mmap.getUSER_TYPE());
		member.setUSER_NAME(mmap.getUSER_NAME());
        
		UserDetails obj = null; 

		BusienceSecurityUser busienceSecurityUser = new BusienceSecurityUser(member);
		
		obj = busienceSecurityUser;
	
		return obj;
	}
	
}
