package com.busience.standard.controller;

import java.net.UnknownHostException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.DEFECT_INFO_TBL;

@RestController("defectManageRestController")
@RequestMapping("defectManageRest")
public class defectManageRestController {

	@Autowired
	DataSource dataSource;
	
	@GetMapping("/view")
	public List<DEFECT_INFO_TBL> view() throws SQLException
	{
		List<DEFECT_INFO_TBL> list = new ArrayList<DEFECT_INFO_TBL>();
		
		String sql = "select \r\n"
				+ "		t1.DEFECT_CODE,\r\n"
				+ "        t1.DEFECT_NAME,\r\n"
				+ "        t1.DEFECT_ABR,\r\n"
				+ "        t1.DEFECT_RMRKS,\r\n"
				+ "        t1.DEFECT_MODIFY_D,\r\n"
				+ "        t2.USER_NAME DEFECT_MODIFIER,\r\n"
				+ "        t1.DEFECT_USE_STATUS\r\n"
				+ "from DEFECT_INFO_TBL t1\r\n"
				+ "inner join USER_INFO_TBL t2 on t1.DEFECT_MODIFIER = t2.USER_CODE";
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int i = 0;
		while (rs.next()) {
			DEFECT_INFO_TBL data = new DEFECT_INFO_TBL();
			i++;
			data.setId(i);
			data.setDEFECT_CODE(rs.getString("DEFECT_CODE"));
			data.setDEFECT_NAME(rs.getString("DEFECT_NAME"));
			data.setDEFECT_ABR(rs.getString("DEFECT_ABR"));
			data.setDEFECT_MODIFIER(rs.getString("DEFECT_MODIFIER"));
			data.setDEFECT_MODIFY_D(rs.getString("DEFECT_MODIFY_D"));
			data.setDEFECT_RMRKS(rs.getString("DEFECT_RMRKS"));
			data.setDEFECT_USE_STATUS(rs.getString("DEFECT_USE_STATUS"));
			//System.out.println(data.toString());
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	@PostMapping("delete")
	public void delete(HttpServletRequest request) throws ParseException, SQLException, UnknownHostException, ClassNotFoundException
	{
		String no = request.getParameter("DEFECT_CODE");
		
		String sql = "delete from DEFECT_INFO_TBL where DEFECT_CODE = '"+no+"'";
		
		//HomeController.LogInsert("", "3. Delete", sql, request);
		//System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}
	
	@PostMapping("/insert")
	public String insert(HttpServletRequest request, Principal principal) throws SQLException
	{
		String DEFECT_CODE = request.getParameter("DEFECT_CODE");
		String DEFECT_NAME = request.getParameter("DEFECT_NAME");
		String DEFECT_ABR = request.getParameter("DEFECT_ABR");
		String DEFECT_USE_STATUS = request.getParameter("DEFECT_USE_STATUS");
		String DEFECT_RMRKS = request.getParameter("DEFECT_RMRKS");
		
		if(DEFECT_CODE.equals(""))
			return "None";
		
		Connection conn = dataSource.getConnection();
		String checkSql = "select DEFECT_CODE from DEFECT_INFO_TBL where DEFECT_CODE='"+DEFECT_CODE+"'";
		//System.out.println(checkSql);
		PreparedStatement pstmt = conn.prepareStatement(checkSql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			String check_DEFECT_CODE = rs.getString("DEFECT_CODE");
			
			if(check_DEFECT_CODE.length() > 0)
				return "Overlap";
		}
		
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		String datestr = sdf.format(date.getTime());
		
		String modifier = principal.getName();
		
		String sql = "insert into DEFECT_INFO_TBL(DEFECT_CODE,DEFECT_NAME,DEFECT_ABR,DEFECT_RMRKS,DEFECT_USE_STATUS,DEFECT_MODIFY_D,DEFECT_MODIFIER) values ('";
		sql += DEFECT_CODE;
		sql += "','"+DEFECT_NAME;
		sql += "','"+DEFECT_ABR;
		sql += "','"+DEFECT_RMRKS;
		sql += "','"+DEFECT_USE_STATUS;
		sql += "','"+datestr;
		sql += "','"+modifier+"')";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		
		return "Success";
	}
	
	@PostMapping("/update")
	public String update(HttpServletRequest request, Principal principal) throws SQLException
	{
		String DEFECT_CODE = request.getParameter("DEFECT_CODE");
		String DEFECT_NAME = request.getParameter("DEFECT_NAME");
		String DEFECT_ABR = request.getParameter("DEFECT_ABR");
		String DEFECT_USE_STATUS = request.getParameter("DEFECT_USE_STATUS");
		String DEFECT_RMRKS = request.getParameter("DEFECT_RMRKS");
		
		System.out.println(DEFECT_CODE);
		
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		String datestr = sdf.format(date.getTime());
		
		String modifier = principal.getName();
		
		String sql = "update DEFECT_INFO_TBL set DEFECT_NAME='"+DEFECT_NAME;
		sql += "',DEFECT_ABR='"+DEFECT_ABR;
		sql += "',DEFECT_USE_STATUS='"+DEFECT_USE_STATUS;
		sql += "',DEFECT_RMRKS='"+DEFECT_RMRKS;
		sql += "',DEFECT_MODIFIER='"+ modifier;
		sql += "',DEFECT_MODIFY_D='"+datestr;
		sql += "' where DEFECT_CODE='"+DEFECT_CODE+"'";
		
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		
		return "Success";
	}
	
}
