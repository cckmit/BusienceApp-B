 package com.busience.common.service;

import com.busience.common.dto.MemberDto;

public interface MemberService {

	// 코드 조건으로 조회
    public MemberDto getMemberView(String string);
    
    //회원가입 & 가입시 메뉴 저장
    public int userRegister(MemberDto member);
    
    //회원정보수정
    public int userModify(MemberDto member);
    
    //회원정보수정
    public int passwordModify(MemberDto member);
}
