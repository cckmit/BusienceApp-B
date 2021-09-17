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
public class salesInputLXController {

	@Autowired
	DataSource dataSource;

	// salesInputLX
	@GetMapping("salesInputLX")
	public String salesInputLX(Model model, HttpServletRequest request) throws SQLException {
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

		model.addAttribute("deptList", deptList);
		model.addAttribute("pageName", "입고 관리");
		model.addAttribute("user_name", "관리자");

		rs.close();
		pstmt.close();
		conn.close();
		return "salesLX/salesInputLX";
	}
}
