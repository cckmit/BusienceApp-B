package com.busience.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.busience.common.dto.MemberDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusienceSecurityUser extends User{
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	private MemberDto member;
	
	public BusienceSecurityUser(MemberDto member) {
		
		super(member.getUSER_CODE(), member.getUSER_PASSWORD(), makeGrantedAuthority(member.getUSER_TYPE()));

		this.member = member;
	}

	private static List<GrantedAuthority> makeGrantedAuthority(String string) {
		
		List<GrantedAuthority> list = new ArrayList<>();

		if(string != null) {
			list.add(new SimpleGrantedAuthority(ROLE_PREFIX+"ADMIN"));
		}
		
		return list;
	}
}
