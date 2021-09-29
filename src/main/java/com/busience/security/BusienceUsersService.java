package com.busience.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.busience.persistence.MemberRepository;

import lombok.extern.java.Log;


@Service
@Log
public class BusienceUsersService implements UserDetailsService{
	
	@Autowired
	MemberRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return repo.findById(username)
				.filter(m -> m != null)
				.map(m -> new BusienceSecurityUser(m)).get();
	}
	
}
