package com.busience.common.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.DTL_TBL;

@RestController
public class HomeRestController {

	@Autowired
	DataSource dataSource;
	
	public static List<DTL_TBL> dtl_tbl_select(DataSource dataSource,String number) throws SQLException, ClassNotFoundException
	{
		String sql = "select * from DTL_TBL where NEW_TBL_CODE='"+number+"'";
		
		List<DTL_TBL> deptList = new ArrayList<DTL_TBL>();
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) 
		{
			DTL_TBL data =new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			data.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
			//System.out.println(data.toString());
			deptList.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return deptList;
	}
}
