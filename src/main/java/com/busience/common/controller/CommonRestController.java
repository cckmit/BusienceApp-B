package com.busience.common.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.domain.Menu;
import com.busience.common.service.Menu_Service;
import com.busience.standard.Dto.DTL_TBL;

@RestController
public class CommonRestController {
	
	@Autowired
	Menu_Service menu_Service;
	
	@Autowired
	DataSource dataSource;
	
	// 공통코드 찾기
	@RequestMapping(value = "/dtl_tbl_select", method = {RequestMethod.GET,RequestMethod.POST})
	public List<DTL_TBL> dtl_tbl_select(HttpServletRequest request) throws SQLException {
		String sql = "select * from DTL_TBL where NEW_TBL_CODE='"+request.getParameter("NEW_TBL_CODE")+"' order by CHILD_TBL_NUM*1";
		//System.out.println(request.getParameter("NEW_TBL_CODE"));
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<DTL_TBL> deptList = new ArrayList<DTL_TBL>();
		
		while (rs.next()) 
		{
			DTL_TBL data =new DTL_TBL();
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
			data.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS"));
			deptList.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return deptList;
	}
	// 공통코드 찾기
	@RequestMapping(value = "/manager_select", method = {RequestMethod.GET,RequestMethod.POST})
	public List<DTL_TBL> manager_select(HttpServletRequest request) throws SQLException {
		String sql = "select * from DTL_TBL where NEW_TBL_CODE='"+request.getParameter("NEW_TBL_CODE")+"'" + " and CHILD_TBL_NUM NOT IN('3','4','5') and CHILD_TBL_USE_STATUS='true'";
		//System.out.println(request.getParameter("NEW_TBL_CODE"));
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<DTL_TBL> managerList = new ArrayList<DTL_TBL>();
		
		while (rs.next()) 
		{
			DTL_TBL data =new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			//System.out.println(data.toString());
			managerList.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return managerList;
	}
	// 공통코드 찾기
	@GetMapping("/parentMenuSelect")
	public List<DTL_TBL> parentMenuSelect(Principal principal) throws SQLException {
		
		String modifier = principal.getName();
		
		String sql = "SELECT \r\n"
				+ "distinct cast(B.Menu_Parent_No as unsigned ) CHILD_TBL_NUM,\r\n"
				+ "C.CHILD_TBL_TYPE\r\n"
				+ "FROM User_Menu_tbl A\r\n"
				+ "inner join Menu_tbl B on A.Program_Code = B.Menu_Code\r\n"
				+ "inner join (\r\n"
				+ "	select * from DTL_TBL where NEW_TBL_CODE = 16\r\n"
				+ ") C on cast(B.Menu_Parent_No as unsigned ) = C.CHILD_TBL_NUM\r\n"
				+ "where  A.User_Code = '"+modifier+"';";
		
		//System.out.println(request.getParameter("NEW_TBL_CODE"));
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<DTL_TBL> deptList = new ArrayList<DTL_TBL>();
		
		while (rs.next()) {
			DTL_TBL data =new DTL_TBL();
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			deptList.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return deptList;
	}
	// 공통코드 찾기
	@GetMapping("/childMenuSelect")
	public List<Menu> childMenuSelect(Principal principal) throws SQLException {
		
		String modifier = principal.getName();
		
		String sql = "SELECT \r\n"
				+ "B.Menu_Code,\r\n"
				+ "B.Menu_Parent_No,\r\n"
				+ "B.Menu_Child_No,\r\n"
				+ "B.Menu_Name,\r\n"
				+ "B.Menu_PageName\r\n"
				+ "FROM User_Menu_tbl A\r\n"
				+ "inner join Menu_tbl B on A.Program_Code = B.Menu_Code\r\n"
				+ "where A.User_Code = '"+modifier+"'";
		
		//System.out.println(request.getParameter("NEW_TBL_CODE"));
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Menu> menuList = new ArrayList<Menu>();
		
		while (rs.next()) {
			Menu data = new Menu();
			data.setMenu_Code(rs.getString("menu_Code"));
			data.setMenu_Parent_No(rs.getString("menu_Parent_No"));
			data.setMenu_Child_No(rs.getString("menu_Child_No"));
			data.setMenu_Name(rs.getString("menu_Name"));
			data.setMenu_PageName(rs.getString("menu_PageName"));
			menuList.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return menuList;
	}
	
	// 모든 회원 조회
	@GetMapping("/menuList")
	public ResponseEntity<List<Menu>> getAllmembers() {
		List<Menu> member = menu_Service.findAll();
		System.out.println(member);
		return new ResponseEntity<List<Menu>>(member, HttpStatus.OK);
	}
	
}
