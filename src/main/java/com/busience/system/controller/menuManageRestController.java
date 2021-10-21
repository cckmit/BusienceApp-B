package com.busience.system.controller;

import java.net.UnknownHostException;
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

import com.busience.system.dto.Menu_MGMT_tbl;

@RestController("menuManageRestController")
@RequestMapping("menuManageRest")
public class menuManageRestController {

	@Autowired
	DataSource dataSource;

	@GetMapping("/MMS_Search")
	public List<Menu_MGMT_tbl> view(HttpServletRequest request) throws SQLException{
		List<Menu_MGMT_tbl> list = new ArrayList<Menu_MGMT_tbl>();
		
		String sql = "SELECT\r\n"
				+ "A.Menu_User_Code,\r\n"
				+ "A.Menu_Program_Code,\r\n"
				+ "B.Menu_Name,\r\n"
				+ "A.Menu_Read_Use_Status,\r\n"
				+ "A.Menu_Write_Use_Status,\r\n"
				+ "A.Menu_Delete_Use_Status,\r\n"
				+ "A.Menu_MGMT_Use_Status\r\n"
				+ " FROM Menu_MGMT_tbl A\r\n"
				+ "inner join Menu_tbl B on A.Menu_Program_Code = B.Menu_Code\r\n"
				+ "where Menu_User_Code = '"+request.getParameter("MENU_USER_CODE")+"'";
		
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Menu_MGMT_tbl data = new Menu_MGMT_tbl();
			data.setMenu_User_Code(rs.getString("Menu_User_Code"));
			data.setMenu_Program_Code(rs.getString("Menu_Program_Code"));
			data.setMenu_Program_Name(rs.getString("Menu_Name"));
			data.setMenu_Read_Use_Status(rs.getString("Menu_Read_Use_Status"));
			data.setMenu_Write_Use_Status(rs.getString("Menu_Write_Use_Status"));
			data.setMenu_Delete_Use_Status(rs.getString("Menu_Delete_Use_Status"));
			data.setMenu_MGMT_Use_Status(rs.getString("Menu_MGMT_Use_Status"));
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	// MM_Update
	@GetMapping("/MM_Update")
	public String MM_Update(HttpServletRequest request) throws ParseException, SQLException, UnknownHostException, ClassNotFoundException {
		JSONParser parser = new JSONParser();
		
		String data = request.getParameter("data");
		JSONArray arr = (JSONArray) parser.parse(data);
		
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
				
				sql = "UPDATE `Menu_MGMT_tbl`\r\n"
						+ "SET \r\n"
						+ "Menu_Read_Use_Status = "+obj.get("menu_Read_Use_Status")+",\r\n"
						+ "Menu_Write_Use_Status = "+obj.get("menu_Write_Use_Status")+",\r\n"
						+ "Menu_Delete_Use_Status = "+obj.get("menu_Delete_Use_Status")+",\r\n"
						+ "Menu_MGMT_Use_Status = "+obj.get("menu_MGMT_Use_Status")+"\r\n"
						+ "where Menu_User_Code = '"+obj.get("menu_User_Code")+"'\r\n"
						+ "AND Menu_Program_Code = '"+obj.get("menu_Program_Code")+"'";

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
