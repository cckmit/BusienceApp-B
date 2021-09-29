package com.busience.meterialLX.controller;

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

import com.busience.standard.Dto.DTL_TBL;

@Controller
public class matInputReportLXController {

	@Autowired
	DataSource dataSource;
	
	// matInputMasterLX
	@GetMapping("matInputMasterLX")
	public String matInputMasterLX(Model model, HttpServletRequest request) throws SQLException {
		// InputClsfc
		String sql = "select * from DTL_TBL where NEW_TBL_CODE = '17' and CHILD_TBL_USE_STATUS='true'";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<DTL_TBL> inMatType = new ArrayList<DTL_TBL>();

		while (rs.next()) {
			DTL_TBL data = new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			// System.out.println(data.toString());
			inMatType.add(data);
		}

		model.addAttribute("inMatType", inMatType);

		// lastDay
		sql = "select CHILD_TBL_RMARK from DTL_TBL where NEW_TBL_CODE='2' limit 1";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String CHILD_TBL_RMARK = "";

		while (rs.next())
			CHILD_TBL_RMARK = rs.getString("CHILD_TBL_RMARK");

		// PrcsDate
		sql = "select SM_Prcs_Date from StockMatLX_tbl limit 1";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String SM_Prcs_Date = "";

		while (rs.next())
			SM_Prcs_Date = rs.getString("SM_Prcs_Date");

		// LastMonth
		sql = "select * from DTL_TBL where NEW_TBL_CODE='20' and CHILD_TBL_NUM='1' and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String LastMonth = "";

		while (rs.next())
			LastMonth = rs.getString("CHILD_TBL_RMARK");

		model.addAttribute("inMatType", inMatType);
		model.addAttribute("LastDay", CHILD_TBL_RMARK);
		model.addAttribute("PrcsDate", SM_Prcs_Date);
		model.addAttribute("LastMonth", LastMonth);
		model.addAttribute("pageName", "matInputMasterLX");
		model.addAttribute("user_name", "관리자");
		
		rs.close();
		pstmt.close();
		conn.close();

		return "materialLX/matInputLX/matInputMasterLX";
	}	
}
