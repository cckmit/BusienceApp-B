package com.busience.standard.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.busience.standard.Dto.DTL_TBL;

@Controller("BOMController")
public class BOMController {
	
	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "BOM", method = RequestMethod.GET)
	public String BOM(Model model) throws SQLException, ClassNotFoundException{
		String sql = "select * from DTL_TBL where NEW_TBL_CODE='5' order by CHILD_TBL_NUM*1";
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
		
		model.addAttribute("pageName", "BOM");
		model.addAttribute("user_name", "관리자");
		model.addAttribute("typeList", deptList);
		return "standard/BOM";
	}
	
	@RequestMapping(value = "BOMListMaster", method = RequestMethod.GET)
	public String BOMListMaster(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException{
		String sql = "select * from DTL_TBL where NEW_TBL_CODE='5' order by CHILD_TBL_NUM*1";
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
		
		model.addAttribute("pageName", "BOMList");
		model.addAttribute("user_name", "관리자");
		model.addAttribute("typeList", deptList);
		return "standard/BOMList/BOMListMaster";
	}
}
