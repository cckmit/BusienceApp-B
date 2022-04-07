package com.busience.productionLX.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.WorkOrder_tbl;

@RestController("workdListRestController")
@RequestMapping("workdListRest")
public class workdListRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbctemplate;

	// 세부 작업 현황
	@RequestMapping(value = "/MI_Search", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Search(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String PRODUCT_ITEM_CODE = (String) obj.get("PRODUCT_ITEM_CODE");
		String Machine_Code = (String) obj.get("Machine_Code");
		String where = "";
		List<WorkOrder_tbl> list = new ArrayList<WorkOrder_tbl>();

		String sql = "SELECT t1.*, t2.CHILD_TBL_Type WorkOrder_WorkStatusName, t4.EQUIPMENT_INFO_NAME WorkOrder_EquipName, t3.PRODUCT_ITEM_NAME WorkOrder_ItemName, t3.PRODUCT_INFO_STND_1 PRODUCT_INFO_STND_1 FROM WorkOrder_tbl t1\r\n"
				+ "INNER JOIN DTL_TBL t2 ON t1.WorkOrder_WorkStatus = t2.CHILD_TBL_NO\r\n"
				+ "INNER JOIN PRODUCT_INFO_TBL t3 ON t1.WorkOrder_ItemCode=t3.PRODUCT_ITEM_CODE\r\n"
				+ "INNER JOIN EQUIPMENT_INFO_TBL t4 ON t1.WorkOrder_EquipCode=t4.EQUIPMENT_INFO_CODE";

		if (startDate == null) {
			where = " WHERE (date(t1.WorkOrder_OrderTime) = DATE(NOW()) or date(t1.WorkOrder_CompleteOrderTime) = DATE(NOW()) or date(t1.WorkOrder_ReceiptTime) = DATE(NOW()) or date(t1.WorkOrder_StartTime) = DATE(NOW()) or date(t1.WorkOrder_CompleteTime) = DATE(NOW()) or WorkOrder_CompleteTime = NULL)";
		} else {
			where = " WHERE t1.WorkOrder_OrderTime between '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'";
		}
		if (!PRODUCT_ITEM_CODE.equals(""))
			where += " and t1.WorkOrder_ItemCode='" + PRODUCT_ITEM_CODE + "'";

		if (!Machine_Code.equals(""))
			where += " and t1.WorkOrder_EquipCode='" + Machine_Code + "'";

		where += " ORDER BY t1.WorkOrder_ONo ASC, t1.WorkOrder_RegisterTime DESC";

		sql += where;

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			WorkOrder_tbl data = new WorkOrder_tbl();
			data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
			data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
			data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
			data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
			data.setWorkOrder_EquipName(rs.getString("WorkOrder_EquipName"));
			data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
			data.setWorkOrder_RegisterTime(rs.getString("WorkOrder_RegisterTime").substring(0, 10));
			data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
			data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
			data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
			data.setWorkOrder_Worker(rs.getString("WorkOrder_Worker"));
			data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			list.add(data);
		}

		return list;
	}

}
