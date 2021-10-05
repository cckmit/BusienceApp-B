package com.busience.standard.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.domain.Member;
import com.busience.common.mapper.Menu_Mapper;
import com.busience.common.persistence.MemberRepository;
import com.busience.common.service.Menu_Service;
import com.busience.standard.Dto.USER_INFO_TBL;

@RestController("userManageRestController")
@RequestMapping("userManageRest")
public class userManageRestController {

	@Autowired
	PasswordEncoder pwEncoder;
	
	@Autowired
	MemberRepository repo;
	
	@Autowired
	DataSource dataSource;
	
	private Menu_Service menu_Service;
	
	public userManageRestController(Menu_Service menu_Service) {
		this.menu_Service = menu_Service;
	}
	
	@GetMapping("/userManageRestSelect")
	public List<USER_INFO_TBL> userManageRestSelect() throws SQLException {
		List<USER_INFO_TBL> list = new ArrayList<USER_INFO_TBL>();

		String sql = "SELECT \r\n"
				+ "A.USER_CODE,\r\n"
				+ "A.USER_NAME,\r\n"
				+ "A.USER_TYPE,\r\n"
				+ "B.CHILD_TBL_TYPE USER_TYPE_NAME,\r\n"
				+ "A.COMPANY,\r\n"
				+ "C.CHILD_TBL_TYPE COMPANY_NAME,\r\n"
				+ "A.DEPT_CODE,\r\n"
				+ "D.CHILD_TBL_TYPE DEPT_NAME,\r\n"
				+ "A.USER_MODIFIER,\r\n"
				+ "A.USER_MODIFY_D,\r\n"
				+ "A.USER_USE_STATUS,\r\n"
				+ "A.USER_REGDTATE\r\n"
				+ " FROM USER_INFO_TBL A\r\n"
				+ "left outer join DTL_TBL B on A.USER_TYPE = B.CHILD_TBL_NO\r\n"
				+ "left outer join DTL_TBL C on A.COMPANY = C.CHILD_TBL_NO\r\n"
				+ "left outer join DTL_TBL D on A.DEPT_CODE = D.CHILD_TBL_NO;\r\n";

		//System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			USER_INFO_TBL data = new USER_INFO_TBL();

			data.setUSER_CODE(rs.getString("user_CODE"));
			data.setDEPT_CODE(rs.getString("dept_CODE"));
			data.setUSER_USE_STATUS(rs.getString("user_USE_STATUS"));
			data.setCOMPANY(rs.getString("company"));
			data.setUSER_TYPE(rs.getString("user_TYPE"));
			data.setUSER_MODIFY_D(rs.getString("user_MODIFY_D"));
			data.setUSER_NAME(rs.getString("user_NAME"));
			data.setUSER_TYPE_NAME(rs.getString("user_TYPE_NAME"));
			data.setUSER_MODIFIER(rs.getString("user_MODIFIER"));
			data.setCOMPANY_NAME(rs.getString("company_NAME"));
			data.setDEPT_NAME(rs.getString("dept_NAME"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// insert
	@PostMapping("/userManageInsert")
	public String userManageInsert(Member member, Principal principal) {

		String encryptPw = pwEncoder.encode(member.getUSER_PASSWORD());
		
		member.setUSER_PASSWORD(encryptPw);
		member.setUSER_MODIFIER(principal.getName());
		
		repo.save(member);
		
		//사용자 메뉴 생성
		menu_Service.insertMenuNewUser(member.getUSER_CODE());
		
		return "Success";
	}
	
	// update
	@Transactional
	@PutMapping("/userManageUpdate")
	public String userManageUpdate(Member member) {
		
		repo.findById(member.getUSER_CODE()).ifPresent(origin -> {
			origin.setUSER_NAME(member.getUSER_NAME());
			origin.setCOMPANY(member.getCOMPANY());
			origin.setUSER_USE_STATUS(member.getUSER_USE_STATUS());
			origin.setUSER_TYPE(member.getUSER_TYPE());
			origin.setDEPT_CODE(member.getDEPT_CODE());

			repo.save(origin);
		});
		
		return "Success";
	}

	@Transactional
	@PutMapping("/userManagePW")
	public String userManagePW(Member member) {
		
		String encryptPw = pwEncoder.encode(member.getUSER_PASSWORD());
		
		repo.findById(member.getUSER_CODE()).ifPresent(origin -> {
			origin.setUSER_PASSWORD(encryptPw);
			
			repo.save(origin);
		});
		
		return "Success";
	}

}
