package com.busience.common.persistence;

import org.springframework.data.repository.CrudRepository;

import com.busience.common.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {
	
}
