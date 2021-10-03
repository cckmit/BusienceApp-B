package com.busience.standard.controller;

import java.net.UnknownHostException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.domain.Member;
import com.busience.common.persistence.MemberRepository;
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

	@RequestMapping(value = "/userManageRestSelect", method = RequestMethod.GET)
	public List<USER_INFO_TBL> userManageRestSelect(HttpServletRequest request) throws SQLException {
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

		int i = 0;
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
	@Transactional
	@PostMapping("/userManageInsert")
	public String userManageInsert(Member member, Principal principal) {
		System.out.println("userManageInsert");
		
		String encryptPw = pwEncoder.encode(member.getUSER_PASSWORD());
		
		member.setUSER_PASSWORD(encryptPw);
		member.setUSER_MODIFIER(principal.getName());
		
		repo.save(member);
		
		return "Success";
	}
	
	// update
	@Transactional
	@PutMapping("/userManageUpdateTest")
	public String userManageUpdateTest(Member member, Principal principal) {
		System.out.println("userManageUpdateTest");
		
		repo.findById(member.getUSER_CODE()).ifPresent(origin -> {
			origin.setUSER_NAME(member.getUSER_NAME());
			origin.setCOMPANY(member.getCOMPANY());
			origin.setUSER_USE_STATUS(member.getUSER_USE_STATUS());
			origin.setUSER_TYPE(member.getUSER_TYPE());
			origin.setDEPT_CODE(member.getDEPT_CODE());
			
			System.out.println(origin);
			//repo.save(origin);
		});
		
		return "Success";
	}

	// ����
	@RequestMapping(value = "/userManageUpdate", method = RequestMethod.POST)
	public String userManageUpdate(HttpServletRequest request, Model model)
			throws SQLException, ParseException, UnknownHostException, ClassNotFoundException {
		String data = request.getParameter("data");
		//System.out.println("update data : " + data);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		//System.out.println(obj.toJSONString());
		// Ư�� USER_CODE�� ���� ���� ��ȸ
		String sql = "SELECT USER_TYPE FROM USER_INFO_TBL where USER_CODE='" + obj.get("USER_CODE") + "'";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			// System.out.println(obj.get("USER_TYPE"));
			// System.out.println(rs.getString("USER_TYPE"));
			// �޴� ���� USER_TYPE�� ����� ���� ������ USER_TYPE�� �������� �ʴٸ�, �޴� ������ USER ���� ����
			// ���� �̸��� ���� ���� ����
			if (!obj.get("USER_TYPE").equals(rs.getString("USER_TYPE"))) {
				sql = "delete from MENU_MGMT_TBL where MENU_USER_CODE='" + obj.get("USER_CODE") + "'";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();

				sql = "INSERT INTO MENU_MGMT_TBL";
				sql += " SELECT T1.USER_CODE, T3.RIGHTS_PROGRAM_CODE, 'true', 'true', 'true', T3.RIGHTS_MGMT_USE_STATUS   FROM USER_INFO_TBL T1";
				sql += " INNER JOIN RIGHTS_MGMT_TBL T3 ON T1.USER_TYPE = T3.RIGHTS_USER_TYPE";
				sql += " WHERE USER_CODE = '" + obj.get("USER_CODE") + "'";

				// System.out.println("����� �޴� ���� : " + sql);

				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
			}
		}
		
		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");

		sql = "update USER_INFO_TBL set ";
		sql += "USER_NAME='" + obj.get("USER_NAME") + "'";
		sql += ",COMPANY='" + obj.get("COMPANY") + "'";
		sql += ",USER_TYPE='" + obj.get("USER_TYPE") + "'";
		sql += ",USER_MODIFIER='" + modifier + "'";
		sql += ",USER_USE_STATUS='" + obj.get("USER_USE_STATUS") + "'";
		sql += ",DEPT_CODE='" + obj.get("DEPT_CODE") + "'";
		sql += ",USER_MODIFY_D=now()";
		sql += " where USER_CODE='" + obj.get("USER_CODE") + "'";

		//System.out.println(sql);

		// HomeController.LogInsert("", "2. Update", sql, request);

		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		//pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		rs.close();
		pstmt.close();
		conn.close();

		return "Success";
	}

	@RequestMapping(value = "/userManagePW", method = RequestMethod.POST)
	public String userManagePW(HttpServletRequest request, Model model)
			throws SQLException, UnknownHostException, ClassNotFoundException {
		String USER_CODE = request.getParameter("update_user_CODE");

		// System.out.println(USER_CODE);

		String sql = "update USER_INFO_TBL set ";
		sql += "USER_PASSWORD=hex(aes_encrypt('1234','a'))";
		sql += " where USER_CODE='" + USER_CODE + "'";

		//HomeController.LogInsert("", "7. Pw Reset", sql, request);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();

		return USER_CODE;
	}

}
