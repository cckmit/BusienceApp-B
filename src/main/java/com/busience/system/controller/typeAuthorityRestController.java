package com.busience.system.controller;

import java.net.UnknownHostException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.DTL_TBL;
import com.busience.system.Dto.RIGHTS_MGMT_TBL;

@RestController("typeAuthorityRestController")
@RequestMapping("typeAuthorityRest")
public class typeAuthorityRestController {

	@Autowired
	DataSource dataSource;

	//TAM_Search
	@GetMapping("/TAM_Search")
	public List<DTL_TBL> TAM_Search() throws ParseException, SQLException {

		String sql = "SELECT\r\n"
				+ "CHILD_TBL_NO,\r\n"
				+ "NEW_TBL_CODE,\r\n"
				+ "CHILD_TBL_NUM,\r\n"
				+ "CHILD_TBL_TYPE,\r\n"
				+ "CHILD_TBL_RMARK,\r\n"
				+ "CHILD_TBL_USE_STATUS\r\n"
				+ "FROM DTL_TBL\r\n"
				+ "where NEW_TBL_CODE = '1';";

		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<DTL_TBL> menuList = new ArrayList<DTL_TBL>();

		while (rs.next()) {
			DTL_TBL data = new DTL_TBL();
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
			data.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS"));
			menuList.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return menuList;
	}
	
	//TAS_Search
	@GetMapping("/TAS_Search")
	public List<RIGHTS_MGMT_TBL> TAS_Search(HttpServletRequest request) throws ParseException, SQLException {

		String sql = "SELECT \r\n"
				+ "A.Rights_User_Type,\r\n"
				+ "A.Rights_Program_Code,\r\n"
				+ "B.Menu_Name,\r\n"
				+ "A.Rights_MGMT_Use_Status\r\n"
				+ "FROM Rights_MGMT_tbl A\r\n"
				+ "inner join Menu_tbl B on A.Rights_Program_Code = B.Menu_Code\r\n"
				+ "where Rights_User_Type = '"+request.getParameter("RIGHTS_USER_TYPE")+"'";

		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<RIGHTS_MGMT_TBL> menuList = new ArrayList<RIGHTS_MGMT_TBL>();

		while (rs.next()) {
			RIGHTS_MGMT_TBL data = new RIGHTS_MGMT_TBL();
			data.setRights_User_Type(rs.getString("Rights_User_Type"));
			data.setRights_Program_Code(rs.getString("Rights_Program_Code"));
			data.setRights_Program_Name(rs.getString("Menu_Name"));
			data.setRights_MGMT_Use_Status(rs.getString("Rights_MGMT_Use_Status"));
			menuList.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return menuList;
	}
	
	// TA_Update
	@GetMapping("/TA_Update")
	public String TA_Update(HttpServletRequest request) throws ParseException, SQLException, UnknownHostException, ClassNotFoundException {
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
				
				sql = "UPDATE `Rights_MGMT_tbl`"
						+ "SET "
						+ "RIGHTS_MGMT_USE_STATUS = "+obj.get("rights_MGMT_Use_Status")+""
						+ " WHERE RIGHTS_USER_TYPE = '"+obj.get("rights_User_Type")+"'"
						+ " AND RIGHTS_PROGRAM_CODE = '"+obj.get("rights_Program_Code")+"'";

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
