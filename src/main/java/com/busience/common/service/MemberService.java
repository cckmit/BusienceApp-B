package com.busience.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busience.common.domain.Member;
import com.busience.common.mapper.MemberMapper;
import com.busience.common.mapper.MenuMapper;

@Service
public class MemberService {

	private MemberMapper memberMapper;
	
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	@Autowired
	MenuMapper menuMapper;
	
	// 코드 조건으로 조회
    public List<Member> getAllmember(String string) {
        final List<Member> memberList = memberMapper.findByCode(string);
        return memberList;
    }
    
    //회원가입 & 가입시 메뉴 저장
    @Transactional
    public int userRegister(Member member) {
    	System.out.println("=========회원가입==========");
    	int result = memberMapper.insertUser(member) & menuMapper.insertMenuNewUser(member.getUSER_CODE());

    	return result;
    }
    
    //회원정보수정
    public int userModify(Member member) {
    	return memberMapper.updateUser(member);
    }
    
    //회원정보수정
    public int passwordModify(Member member) {
    	return memberMapper.updatePassword(member);
    }
}
