package com.busience.persistence;

import org.springframework.data.repository.CrudRepository;

import com.busience.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {
	
}
