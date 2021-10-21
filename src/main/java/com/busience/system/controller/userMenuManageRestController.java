package com.busience.system.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.system.Dto.Menu_tbl;

@RestController("userMenuManageRestController")
@RequestMapping("userMenuManageRest")
public class userMenuManageRestController {

	@Autowired
	DataSource dataSource;

	//userMenuSearch
	@GetMapping("/userMenuSearch")
	public List<Menu_tbl> userMenuSearch(Principal principal) throws SQLException {
		
		String userCode = principal.getName();

		String sql = "select\r\n"
				+ "A.Menu_Code,\r\n"
				+ "B.CHILD_TBL_TYPE,\r\n"
				+ "A.Menu_Name,\r\n"
				+ "C.User_Code\r\n"
				+ "from (\r\n"
				+ "	select \r\n"
				+ "    Menu_Code,\r\n"
				+ "	cast(Menu_Parent_No as unsigned ) Menu_Parent_No,\r\n"
				+ "	Menu_Name\r\n"
				+ "	from Menu_tbl\r\n"
				+ ") A\r\n"
				+ "inner join DTL_TBL B on A.Menu_Parent_No = B.CHILD_TBL_NUM\r\n"
				+ "left outer join (\r\n"
				+ " SELECT * FROM User_Menu_tbl where User_Code = '"+userCode+"'\r\n"
				+ ") C on A.Menu_Code = C.Program_Code\r\n"
				+ "where B.NEW_TBL_CODE = '16'\r\n"
				+ "order by Menu_Code;";
		
		System.out.println("userMenuSearch = " + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Menu_tbl> list = new ArrayList<Menu_tbl>();
		
		while (rs.next()) {
			Menu_tbl userMenuData = new Menu_tbl();
					
			userMenuData.setMenu_Code(rs.getString("Menu_Code"));
			userMenuData.setMenu_Parent_Name(rs.getString("CHILD_TBL_TYPE"));
			userMenuData.setMenu_Name(rs.getString("Menu_Name"));
			userMenuData.setUser_Code(rs.getString("User_Code"));
			list.add(userMenuData);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//userMenuInsert
	@GetMapping("/userMenuInsert")
	public String userMenuInsert(HttpServletRequest request,Principal principal) throws SQLException, ParseException {
		String data = request.getParameter("data");	
		JSONParser parser = new JSONParser();
		JSONArray arr  = (JSONArray) parser.parse(data);
		
		String sql = null;
		String userCode = principal.getName();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			//Sales_OrderList_tbl
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				sql = "insert into User_Menu_tbl values(\r\n"
					+ "'"+userCode+"',\r\n"
					+ "'"+obj.get("menu_Code")+"')";

				System.out.println("sql = " + sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
			}
			
			conn.commit();
			sql_result = "success";
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn!=null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		
		return sql_result;
	}
	
	//userMenuDelete
	@GetMapping("/userMenuDelete")
	public String userMenuDelete(HttpServletRequest request) throws SQLException, ParseException {
		String data = request.getParameter("data");	
		JSONParser parser = new JSONParser();
		JSONArray arr  = (JSONArray) parser.parse(data);
		
		String sql = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			//Sales_OrderList_tbl
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				sql = "delete from User_Menu_tbl\r\n"
						+ "where User_Code = '"+obj.get("user_Code")+"'\r\n"
						+ "and Program_Code = '"+obj.get("menu_Code")+"'";

				System.out.println("sql = " + sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
			}
			
			conn.commit();
			sql_result = "success";
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn!=null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		
		return sql_result;
	}
}
