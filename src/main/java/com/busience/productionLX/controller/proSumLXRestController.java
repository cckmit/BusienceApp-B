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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.PRODUCTION_INFO_TBL;

@RestController("proSumLXRestController")
@RequestMapping("proSumLXRest")
public class proSumLXRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbctemplate;

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

		sql += " group by A.PRODUCTION_WorkOrder_ONo with rollup";

		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();

		while (rs.next()) {
			if (rs.getString("PRODUCTION_WorkOrder_ONo") == null) {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();

				data1.setPRODUCTION_WorkOrder_ONo("Grand Total");
				data1.setPRODUCTION_EQUIPMENT_CODE("");
				data1.setPRODUCTION_EQUIPMENT_INFO_NAME("");
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
		/*
		 * if (list.size() > 0) { if (list.get(list.size() - 1).getPRODUCTION_P_Qty() ==
		 * list.get(list.size() - 2).getPRODUCTION_P_Qty()) { list.get(list.size() -
		 * 2).setPRODUCTION_WorkOrder_ONo("Grand Total"); list.remove(list.size() - 1);
		 * } else { list.get(list.size() -
		 * 1).setPRODUCTION_WorkOrder_ONo("Grand Total"); list.get(list.size() -
		 * 1).setPRODUCTION_EQUIPMENT_CODE(""); list.get(list.size() -
		 * 1).setPRODUCTION_EQUIPMENT_INFO_NAME(""); } }
		 */

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

		sql += " group by A.PRODUCTION_WorkOrder_ONo with rollup";

		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();

		while (rs.next()) {
			if (rs.getString("PRODUCTION_WorkOrder_ONo") == null) {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				// ��ǰ Sub Total
				data1.setPRODUCTION_WorkOrder_ONo("Grand Total");
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty"));
				data1.setPRODUCTION_PRODUCT_CODE("");
				data1.setPRODUCT_ITEM_NAME("");

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
		/*
		 * // ��ǰ Grand Total if (list.size() > 0) { if (list.get(list.size() -
		 * 1).getPRODUCTION_P_Qty() == list.get(list.size() - 2).getPRODUCTION_P_Qty())
		 * { list.get(list.size() - 2).setPRODUCTION_WorkOrder_ONo("Grand Total");
		 * list.remove(list.size() - 1); } else { list.get(list.size() -
		 * 1).setPRODUCTION_WorkOrder_ONo("Grand Total"); list.get(list.size() -
		 * 1).setPRODUCTION_PRODUCT_CODE(""); list.get(list.size() -
		 * 1).setPRODUCT_ITEM_NAME(""); } }
		 */
		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}

	// proItemSumSelect3
	@RequestMapping(value = "/proItemSumSelect3", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proItemSumSelect3(HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String PRODUCT_ITEM_CODE = request.getParameter("PRODUCT_ITEM_CODE");
		String PRODUCT_ITEM_NAME = request.getParameter("PRODUCT_ITEM_NAME");
		String where = "";

		String sql = "SELECT \r\n" + "			t1.*,\r\n"
				+ "			SUM(t1.PRODUCTION_SUM_VOLUME) PRODUCTION_VOLUME_S,\r\n"
				+ "			t2.PRODUCT_ITEM_NAME,\r\n"
				+ "			t3.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME\r\n"
				+ "FROM		WorkOrder_tbl_X t1\r\n"
				+ "LEFT JOIN 	PRODUCT_INFO_TBL t2 ON t1.Production_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "LEFT JOIN 	EQUIPMENT_INFO_TBL t3 ON t1.PRODUCTION_Equipment_Code = t3.EQUIPMENT_INFO_CODE";

		if (PRODUCT_ITEM_CODE != null) {
			where += " and Production_PRODUCT_CODE like '%" + PRODUCT_ITEM_CODE + "%'"
					+ " and PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'";
		}
		else {
			where += " and (PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'"
					+ " OR Production_PRODUCT_CODE like '%" + PRODUCT_ITEM_NAME + "%')";
		}
		where += "\r\nWHERE t1.PRODUCTION_END_TIME BETWEEN ? AND ? AND PRODUCTION_CC='E'\r\n";
		sql += where;
		sql += "group BY 	t1.PRODUCTION_PRODUCT_CODE,t1.PRODUCTION_SERIAL_NUM with rollup";
		
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();
		jdbctemplate.query(sql, new RowMapper<PRODUCTION_INFO_TBL>() {

			@Override
			public PRODUCTION_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();
				
				if (rs.getString("PRODUCTION_SERIAL_NUM") != null) {
					if (rs.getString("PRODUCTION_PRODUCT_CODE") == null)
					{
						data.setPRODUCTION_WorkOrder_ONo("Grand Total");
						data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
						data.setPRODUCTION_PRODUCT_CODE("");
						data.setPRODUCT_ITEM_NAME("");
					}
					else
					{
						data.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_SERIAL_NUM"));

						data.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
						data.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));

						data.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
						data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
						data.setPRODUCTION_Date(rs.getString("PRODUCTION_END_TIME"));

						data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
					}
					
					list.add(data);
				}
				
				return data;
			}
			
		}, startDate, endDate);
		
		//list.get(list.size()-1).setPRODUCTION_WorkOrder_ONo("Grand Total");
		return list;
	}

	// proItemSumSelect3
	@RequestMapping(value = "/proMachineSumSelect3", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proMachineSumSelect3(HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String EQUIPMENT_INFO_CODE = request.getParameter("EQUIPMENT_INFO_CODE");
		String EQUIPMENT_INFO_NAME = request.getParameter("EQUIPMENT_INFO_NAME");
		String where = "";

		String sql = "SELECT \r\n" + "			t1.*,\r\n"
				+ "			SUM(t1.PRODUCTION_SUM_VOLUME) PRODUCTION_VOLUME_S,\r\n"
				+ "			t2.PRODUCT_ITEM_NAME,\r\n"
				+ "			t3.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME\r\n"
				+ "FROM		WorkOrder_tbl_X t1\r\n"
				+ "LEFT JOIN 	PRODUCT_INFO_TBL t2 ON t1.Production_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "LEFT JOIN 	EQUIPMENT_INFO_TBL t3 ON t1.PRODUCTION_Equipment_Code = t3.EQUIPMENT_INFO_CODE";

		if (EQUIPMENT_INFO_CODE != null) {
			where += " and t1.PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_CODE + "%'"
			+ " and t3.EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_NAME + "%'";
		}
		else {
			where += " and (t3.EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_NAME + "%'"
			+ " OR t1.PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_NAME + "%')";
		}
		
		where += "\r\nWHERE t1.PRODUCTION_END_TIME BETWEEN ? AND ? AND PRODUCTION_CC='E'\r\n";
		sql += where;
		sql += "group BY 	t1.PRODUCTION_Equipment_Code,t1.PRODUCTION_SERIAL_NUM with rollup";
		
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();
		
		jdbctemplate.query(sql, new RowMapper<PRODUCTION_INFO_TBL>() {

			@Override
			public PRODUCTION_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();
				
				if (rs.getString("PRODUCTION_SERIAL_NUM") != null)
				{
					
				}
				
				if (rs.getString("PRODUCTION_EQUIPMENT_CODE") == null)
				{
					data.setPRODUCTION_WorkOrder_ONo("Grand Total");
					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
					data.setPRODUCTION_PRODUCT_CODE("");
					data.setPRODUCT_ITEM_NAME("");
				}
				else
				{
					data.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_SERIAL_NUM"));

					data.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
					data.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));

					data.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
					data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
					data.setPRODUCTION_Date(rs.getString("PRODUCTION_END_TIME"));

					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
				}
				list.add(data);
				
				return data;
			}
			
		}, startDate, endDate);
		return list;
	}
}
