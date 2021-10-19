package com.busience.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.busience.common.domain.Member;
import com.busience.common.mapper.MemberMapper;

@Service
public class MemberService {

	private MemberMapper memberMapper;
	
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	// 코드 조건으로 조회
    public List<Member> getAllmember(String string) {
        final List<Member> memberList = memberMapper.findByCode(string);
        return memberList;
    }
}
