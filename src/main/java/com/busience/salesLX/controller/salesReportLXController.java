package com.busience.salesLX.controller;

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
public class salesReportLXController {

	@Autowired
	DataSource dataSource;

	// fgoodsInputMaster
	@GetMapping("salesInputLXMaster")
	public String salesInputMaster(Model model, HttpServletRequest request)
			throws SQLException, ClassNotFoundException {

		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		sql = "select * from DTL_TBL where NEW_TBL_CODE = '17' and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		List<DTL_TBL> deptList = new ArrayList<DTL_TBL>();

		while (rs.next()) {
			DTL_TBL data = new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			deptList.add(data);
		}

		model.addAttribute("InputType", deptList);

		rs.close();
		pstmt.close();
		conn.close();
		return "salesLX/salesInput/salesInputMaster";
	}

	// salesOrderList
	@GetMapping("salesOrderListLX")
	public String salesOrderList(Model model, HttpServletRequest request) {
		
		model.addAttribute("pageName", "수주 조회");
		model.addAttribute("user_name", "관리자");
		
		return "salesLX/salesOrderList";
	}

	// fgoodsOutputMaster
	@GetMapping("salesOutputLXMaster")
	public String salesOutputMaster(Model model, HttpServletRequest request) throws SQLException {

		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		sql = "select * from DTL_TBL where NEW_TBL_CODE = '19' and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		List<DTL_TBL> outMatList = new ArrayList<DTL_TBL>();

		while (rs.next()) {
			DTL_TBL data = new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			outMatList.add(data);
		}

		model.addAttribute("OutputType", outMatList);

		rs.close();
		pstmt.close();
		conn.close();

		return "salesLX/salesOutput/salesOutputMaster";
	}

	// sgoodsOutputMaster
	@GetMapping("salesDeliveryLXMaster")
	public String salesDeliveryMaster(Model model, HttpServletRequest request) throws SQLException {

		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		sql = "select * from DTL_TBL where NEW_TBL_CODE = '19' and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		List<DTL_TBL> outMatList = new ArrayList<DTL_TBL>();

		while (rs.next()) {
			DTL_TBL data = new DTL_TBL();
			data.setNEW_TBL_CODE(rs.getString("NEW_TBL_CODE"));
			data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			outMatList.add(data);
		}

		// PrcsDate
		sql = "select * from DTL_TBL where NEW_TBL_CODE='20' and CHILD_TBL_NUM='3' and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String PrcsDate = "";

		while (rs.next())
			PrcsDate = rs.getString("CHILD_TBL_RMARK");

		// LastMonth
		sql = "select * from DTL_TBL where NEW_TBL_CODE='20' and CHILD_TBL_NUM='1' and CHILD_TBL_USE_STATUS='true'";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String LastMonth = "";

		while (rs.next())
			LastMonth = rs.getString("CHILD_TBL_RMARK");

		// lastDay
		sql = "select CHILD_TBL_RMARK from DTL_TBL where NEW_TBL_CODE='2' limit 1";
		conn = dataSource.getConnection();
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		String LastDay = "";

		while (rs.next())
			LastDay = rs.getString("CHILD_TBL_RMARK");

		model.addAttribute("OutputType", outMatList);
		model.addAttribute("PrcsDate", PrcsDate);
		model.addAttribute("LastMonth", LastMonth);
		model.addAttribute("LastDay", LastDay);

		rs.close();
		pstmt.close();
		conn.close();

		return "salesLX/salesDelivery/salesDeliveryMaster";
	}

	// fgoodsInoutList
	@GetMapping("salesInoutListLX")
	public String salesInoutList(Model model, HttpServletRequest request) {
		return "salesLX/salesInoutList";
	}

	// fgoodsStockMaster
	@GetMapping("salesStockLXMaster")
	public String salesStockMaster(Model model, HttpServletRequest request) throws SQLException {
		return "salesLX/salesStock/salesStockMaster";
	}
}
