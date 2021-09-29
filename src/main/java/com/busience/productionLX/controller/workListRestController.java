package com.busience.productionLX.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("workListRestController")
@RequestMapping("workListRest")
public class workListRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/OrderUpdate", method = RequestMethod.GET)
	public String OrderUpdate(HttpServletRequest request) throws org.json.simple.parser.ParseException, SQLException {
		String workOrder_ONo = request.getParameter("workOrder_ONo");
		String workOrder_EquipCode = request.getParameter("workOrder_EquipCode");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT CHILD_TBL_NO FROM DTL_TBL where CHILD_TBL_RMARK='S'";
		con = dataSource.getConnection();
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		String CHILD_TBL_NO = "";
		while (rs.next())
			CHILD_TBL_NO = rs.getString("CHILD_TBL_NO");

		sql = "select * from WorkOrder_tbl where WorkOrder_WorkStatus='" + CHILD_TBL_NO + "' and WorkOrder_EquipCode='"
				+ workOrder_EquipCode + "'";
		con = dataSource.getConnection();
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();

		rs.last();
		int Count = rs.getRow();
		if (Count == 1) {
			rs.close();
			pstmt.close();
			con.close();

			return "OK";
		} else {
			HttpSession httpSession = request.getSession();
			String modifier = (String) httpSession.getAttribute("id");

			sql = "update WorkOrder_tbl set WorkOrder_WorkStatus='293',WorkOrder_StartTime=now(),WorkOrder_Worker='"
					+ modifier + "',WorkOrder_RQty=null,WorkOrder_CompleteTime=null" + " where workOrder_ONo='"
					+ workOrder_ONo + "'";
			System.out.println(sql);

			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();

			rs.close();
			pstmt.close();
			con.close();

			return "NO";
		}
	}

	@RequestMapping(value = "/OrderUpdate2", method = RequestMethod.GET)
	public String OrderUpdate2(HttpServletRequest request) throws SQLException {
		String workOrder_ONo = request.getParameter("workOrder_ONo");
		String workOrder_EquipCode = request.getParameter("workOrder_EquipCode");
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = null;

		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");

		String sql = "update WorkOrder_tbl set WorkOrder_WorkStatus='294',WorkOrder_CompleteTime=now(),WorkOrder_Worker='"
				+ modifier
				+ "',WorkOrder_RQty=(select sum(PRODUCTION_Volume) from PRODUCTION_MGMT_TBL2 where PRODUCTION_WorkOrder_ONo = '"
				+ workOrder_ONo + "')\r\n" + " where workOrder_ONo='" + workOrder_ONo + "'";
		pstmt = con.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		con.close();

		return "OK";
	}
}
