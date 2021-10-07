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

import com.busience.system.Dto.MENU_MGMT_TBL;

@RestController("menuManageRestController")
@RequestMapping("menuManageRest")
public class menuManageRestController {

	@Autowired
	DataSource dataSource;

	@GetMapping("/MMS_Search")
	public List<MENU_MGMT_TBL> view(HttpServletRequest request) throws SQLException{
		List<MENU_MGMT_TBL> list = new ArrayList<MENU_MGMT_TBL>();
		
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
			MENU_MGMT_TBL data = new MENU_MGMT_TBL();
			data.setMENU_USER_CODE(rs.getString("MENU_USER_CODE"));
			data.setMENU_PROGRAM_CODE(rs.getString("MENU_PROGRAM_CODE"));
			data.setMENU_READ_USE_STATUS(rs.getString("MENU_READ_USE_STATUS"));
			data.setMENU_WRITE_USE_STATUS(rs.getString("MENU_WRITE_USE_STATUS"));
			data.setMENU_DEL_USE_STATUS(rs.getString("MENU_delete_USE_STATUS"));
			data.setMENU_MGMT_USE_STATU(rs.getString("MENU_MGMT_USE_STATUS"));
			data.setMENU_PROGRAM_NAME(rs.getString("Menu_Name"));
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
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);

		String sql;
		
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
				
				sql = "UPDATE `RIGHTS_MGMT_TBL`"
						+ "SET "
						+ "Menu_Read_Use_Status = '"+obj.get("menu_Read_Use_Status")+"'"
						+ "Menu_Write_Use_Status = '"+obj.get("menu_Write_Use_Status")+"'"
						+ "Menu_Delete_Use_Status = '"+obj.get("menu_DEL_USE_STATUS")+"'"
						+ "Menu_MGMT_Use_Status = '"+obj.get("menu_MGMT_Use_Status")+"'"
						+ "WHERE Menu_User_Code = 'test01'"
						+ "AND Menu_Program_Code = '"+obj.get("menu_PROGRAM_Code")+"'";

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
