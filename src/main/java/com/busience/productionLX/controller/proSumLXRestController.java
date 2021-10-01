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

@RestController("proSumLXRestController")
@RequestMapping("proSumLXRest")
public class proSumLXRestController {

	@Autowired
	DataSource dataSource;

	// proMachineSumSelect2
	@RequestMapping(value = "/proMachineSumSelect2", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proMachineSumSelect2(HttpServletRequest request)
			throws ParseException, SQLException, org.json.simple.parser.ParseException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String EQUIPMENT_INFO_CODE = request.getParameter("EQUIPMENT_INFO_CODE");
		String EQUIPMENT_INFO_NAME = request.getParameter("EQUIPMENT_INFO_NAME");

		String sql = "SELECT \r\n" + "A.PRODUCTION_WorkOrder_No,\r\n" + "A.PRODUCTION_WorkOrder_ONo,\r\n"
				+ "Production_PRODUCT_CODE,\r\n" + "B.PRODUCT_ITEM_NAME,\r\n"
				+ "A.PRODUCTION_Equipment_Code PRODUCTION_EQUIPMENT_CODE,\r\n"
				+ "C.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME,\r\n"
				+ "sum(A.PRODUCTION_Volume) PRODUCTION_P_Qty,\r\n"
				+ "date_format(A.PRODUCTION_Date,'%Y-%m-%d %T') PRODUCTION_Date\r\n" + "FROM PRODUCTION_MGMT_TBL2 A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on Production_PRODUCT_CODE = B.PRODUCT_ITEM_CODE\r\n"
				+ "inner join EQUIPMENT_INFO_TBL C on A.PRODUCTION_Equipment_Code = C.EQUIPMENT_INFO_CODE\r\n";

		String where = " and A.PRODUCTION_Date between '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'\r\n";

		// �����ڵ� �˻�
		if (EQUIPMENT_INFO_CODE != null) {
			where += " and A.PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_CODE + "%'"
					+ " and C.EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_NAME + "%'";
		}
		// �����? �˻�
		else {
			where += " and (C.EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_NAME + "%'"
					+ " OR A.PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_NAME + "%')";
		}
		sql += where;

		sql += " group by A.PRODUCTION_WorkOrder_ONo, A.PRODUCTION_Equipment_Code  with rollup";

		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();

		while (rs.next()) {
			if (rs.getString("PRODUCTION_EQUIPMENT_CODE") == null) {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				i++;
				data1.setId(i);
				// ���� Sub Total
				data1.setPRODUCTION_WorkOrder_ONo("Sub Total");
				data1.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
				data1.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty")); // �������?
				list.add(data1);
			} else {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				i++;
				data1.setId(i);
				data1.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_WorkOrder_ONo"));
				data1.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
				data1.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty")); // �������?
				data1.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
				data1.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data1.setPRODUCTION_Date(rs.getString("PRODUCTION_Date"));
				list.add(data1);
			}
		}
		// ���� Grand Total
		if (list.size() > 0) {
			if (list.get(list.size() - 1).getPRODUCTION_P_Qty() == list.get(list.size() - 2).getPRODUCTION_P_Qty()) {
				list.get(list.size() - 2).setPRODUCTION_WorkOrder_ONo("Grand Total");
				list.remove(list.size() - 1);
			} else {
				list.get(list.size() - 1).setPRODUCTION_WorkOrder_ONo("Grand Total");
				list.get(list.size() - 1).setPRODUCTION_EQUIPMENT_CODE("");
				list.get(list.size() - 1).setPRODUCTION_EQUIPMENT_INFO_NAME("");
			}
		}
		rs.close();
		pstmt.close();
		pstmt.close();
		conn.close();
		return list;
	}

	// proItemSumSelect2
	@RequestMapping(value = "/proItemSumSelect2", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proItemSumSelect2(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String PRODUCT_ITEM_CODE = request.getParameter("PRODUCT_ITEM_CODE");
		String PRODUCT_ITEM_NAME = request.getParameter("PRODUCT_ITEM_NAME");

		String sql = "SELECT \r\n" + "A.PRODUCTION_WorkOrder_No,\r\n" + "A.PRODUCTION_WorkOrder_ONo,\r\n"
				+ "Production_PRODUCT_CODE,\r\n" + "B.PRODUCT_ITEM_NAME,\r\n"
				+ "A.PRODUCTION_Equipment_Code production_EQUIPMENT_CODE,\r\n"
				+ "C.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME,\r\n"
				+ "sum(A.PRODUCTION_Volume) PRODUCTION_P_Qty,\r\n"
				+ "date_format(A.PRODUCTION_Date,'%Y-%m-%d %T') PRODUCTION_Date\r\n" + "FROM PRODUCTION_MGMT_TBL2 A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on Production_PRODUCT_CODE = B.PRODUCT_ITEM_CODE\r\n"
				+ "inner join EQUIPMENT_INFO_TBL C on A.PRODUCTION_Equipment_Code = C.EQUIPMENT_INFO_CODE\r\n";

		String where = " and A.PRODUCTION_Date between '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'\r\n";

		// ��ǰ�ڵ� �˻�
		if (PRODUCT_ITEM_CODE != null) {
			where += " and Production_PRODUCT_CODE like '%" + PRODUCT_ITEM_CODE + "%'"
					+ " and PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'";
		}
		// ��ǰ�� �˻�
		else {
			where += " and (PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'"
					+ " OR Production_PRODUCT_CODE like '%" + PRODUCT_ITEM_NAME + "%')";
		}
		sql += where;

		sql += " group by A.PRODUCTION_WorkOrder_ONo, Production_PRODUCT_CODE with rollup";

		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();

		while (rs.next()) {
			if (rs.getString("Production_PRODUCT_CODE") == null) {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				// ��ǰ Sub Total
				i++;
				data1.setId(i);
				data1.setPRODUCTION_WorkOrder_ONo("Sub Total");
				data1.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
				data1.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty")); // �������
				list.add(data1);
			} else {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				i++;
				data1.setId(i);
				data1.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_WorkOrder_ONo"));
				data1.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
				data1.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty")); // �������?
				data1.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
				data1.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data1.setPRODUCTION_Date(rs.getString("PRODUCTION_Date"));
				list.add(data1);
			}
		}
		// ��ǰ Grand Total
		if (list.size() > 0) {
			if (list.get(list.size() - 1).getPRODUCTION_P_Qty() == list.get(list.size() - 2).getPRODUCTION_P_Qty()) {
				list.get(list.size() - 2).setPRODUCTION_WorkOrder_ONo("Grand Total");
				list.remove(list.size() - 1);
			} else {
				list.get(list.size() - 1).setPRODUCTION_WorkOrder_ONo("Grand Total");
				list.get(list.size() - 1).setPRODUCTION_PRODUCT_CODE("");
				list.get(list.size() - 1).setPRODUCT_ITEM_NAME("");
			}
		}
		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}
}