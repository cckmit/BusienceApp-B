package com.busience.common.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.busience.common.domain.Member;
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
		
		List<Member> mmap = memberService.getAllmember(username);
		
		Member member = new Member();
		
		member.setUSER_CODE(mmap.get(0).getUSER_CODE());
		member.setUSER_PASSWORD(mmap.get(0).getUSER_PASSWORD());
		member.setUSER_TYPE(mmap.get(0).getUSER_TYPE());
		member.setUSER_NAME(mmap.get(0).getUSER_NAME());
		
        
		UserDetails obj = null; 

		BusienceSecurityUser busienceSecurityUser = new BusienceSecurityUser(member);
		
		obj = busienceSecurityUser;
	
		return obj;
	}
	
}
