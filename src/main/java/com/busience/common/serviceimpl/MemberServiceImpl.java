package com.busience.common.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.MemberDao;
import com.busience.common.dao.MenuDao;
import com.busience.common.dto.MemberDto;
import com.busience.common.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberDao memberdao;
	
	@Autowired
	TransactionTemplate transactionTemplate;

	// 코드 조건으로 조회
	@Override
	public MemberDto getMemberView(String string) {
		final MemberDto member = memberdao.viewDao(string);
        return member;
	}
	
	//회원가입 & 가입시 메뉴 저장
	@Override
	public int userRegister(MemberDto member) {
		
		try {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					memberdao.insertUser(member);
					
					memberdao.insertMenuNewUser(member.getUSER_CODE());
				}
			});
			
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	//회원정보수정
	@Override
	public int userModify(MemberDto member) {
		return memberdao.updateUser(member);
	}

	//회원정보수정
	@Override
	public int passwordModify(MemberDto member) {
		return memberdao.updatePassword(member);
	}
}
