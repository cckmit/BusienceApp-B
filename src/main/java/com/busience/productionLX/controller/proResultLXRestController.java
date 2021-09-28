package com.busience.productionLX.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.production.dto.PRODUCTION_INFO_TBL;

@RestController("proResultLXRestController")
@RequestMapping("proResultLXRest")
public class proResultLXRestController {

	@Autowired
	DataSource dataSource;

	// proResultSelect
	@RequestMapping(value = "/proResultSelect", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proResultSelect(HttpServletRequest request)
			throws ParseException, SQLException, org.json.simple.parser.ParseException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String PRODUCT_ITEM_CODE = request.getParameter("PRODUCT_ITEM_CODE");
		String PRODUCT_ITEM_NAME = request.getParameter("PRODUCT_ITEM_NAME");
		String EQUIPMENT_INFO_CODE = request.getParameter("EQUIPMENT_INFO_CODE");
		String EQUIPMENT_INFO_NAME = request.getParameter("EQUIPMENT_INFO_NAME");

		String sql = "SELECT\r\n" + "A.PRODUCTION_WorkOrder_No,\r\n" + "A.PRODUCTION_WorkOrder_ONo,\r\n"
				+ "A.PRODUCTION_Product_Code,\r\n" + "B.PRODUCT_ITEM_NAME,\r\n" + "A.PRODUCTION_Volume,\r\n"
				+ "A.PRODUCTION_Equipment_Code,\r\n" + "C.EQUIPMENT_INFO_NAME,\r\n"
				+ "date_format(A.PRODUCTION_Date,'%Y-%m-%d %T') PRODUCTION_Date\r\n" + "FROM PRODUCTION_MGMT_TBL2 A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on A.PRODUCTION_Product_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ "inner join EQUIPMENT_INFO_TBL C on A.PRODUCTION_Equipment_Code = C.EQUIPMENT_INFO_CODE\r\n"
				+ "and PRODUCTION_Date between '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'";

		// 제품코드검색
		if (PRODUCT_ITEM_CODE != null) {
			sql += " and PRODUCTION_Product_Code like '%" + PRODUCT_ITEM_CODE + "%'" + " and PRODUCT_ITEM_NAME like '%"
					+ PRODUCT_ITEM_NAME + "%'";
		}

		// 설비코드검색
		if (EQUIPMENT_INFO_CODE != null) {
			sql += " and PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_CODE + "%'"
					+ " and EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_NAME + "%'";
		}

		sql += " order by PRODUCTION_Product_Code, PRODUCTION_WorkOrder_ONo, PRODUCTION_WorkOrder_No";

		System.out.println("PRODUCTION_MGMT_TBL2 = " + sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();

		while (rs.next()) {
			PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();

			data.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_WorkOrder_ONo"));
			data.setPRODUCTION_WorkOrder_No(rs.getInt("PRODUCTION_WorkOrder_No"));
			data.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_Equipment_Code"));
			data.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
			data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME"));
			data.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_Product_Code"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCTION_Date(rs.getString("PRODUCTION_Date"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}
}
