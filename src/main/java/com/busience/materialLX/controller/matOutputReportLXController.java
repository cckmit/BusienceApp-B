package com.busience.materialLX.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.busience.standard.Dto.DTL_TBL;

@Controller
public class matOutputReportLXController {

	@Autowired
	DataSource dataSource;
	
	// matOutputMasterLX
	@GetMapping("matOutputMasterLX")
	public String matOutputMasterLX(Model model) throws SQLException {

		// OutputClsfc
		String sql = "select * from DTL_TBL where NEW_TBL_CODE = '18' and CHILD_TBL_USE_STATUS='true'";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<DTL_TBL> outMatType = new ArrayList<DTL_TBL>();

		while (rs.next()) {
			DTL_TBL data = new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			System.out.println("outMatType = " + data.toString());
			outMatType.add(data);
		}

		model.addAttribute("outMatType", outMatType);

		// deptName
		sql = "select * from DTL_TBL where NEW_TBL_CODE = '3' and CHILD_TBL_NUM > 0 and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		List<DTL_TBL> outMatDept = new ArrayList<DTL_TBL>();

		while (rs.next()) {
			DTL_TBL data = new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			System.out.println("outMatDept = " + data.toString());
			outMatDept.add(data);
		}

		// PrcsDate
		sql = "select * from DTL_TBL where NEW_TBL_CODE='20' and CHILD_TBL_NUM='3' and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String PrcsDate = "";

		while (rs.next())
			PrcsDate = rs.getString("CHILD_TBL_RMARK");

		// LastDate
		sql = "select * from DTL_TBL where NEW_TBL_CODE='20' and CHILD_TBL_NUM='1' and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String LastDate = "";

		while (rs.next())
			LastDate = rs.getString("CHILD_TBL_RMARK");

		// lastDay
		sql = "select CHILD_TBL_RMARK from DTL_TBL where NEW_TBL_CODE='2' limit 1";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String LastDay = "";

		while (rs.next())
			LastDay = rs.getString("CHILD_TBL_RMARK");

		model.addAttribute("outMatType", outMatType);
		model.addAttribute("outMatDept", outMatDept);
		model.addAttribute("PrcsDate", PrcsDate);
		model.addAttribute("LastDate", LastDate);
		model.addAttribute("LastDay", LastDay);
		model.addAttribute("pageName", "matOutputMasterLX");
		model.addAttribute("user_name", "관리자");
		
		rs.close();
		pstmt.close();
		conn.close();

		return "materialLX/matOutputLX/matOutputMasterLX";
	}
}
