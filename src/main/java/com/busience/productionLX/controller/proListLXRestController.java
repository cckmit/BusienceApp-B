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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.PRODUCTION_INFO_TBL;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.service.ProductionMgmtService;

@RestController("proListLXRestController")
@RequestMapping("proListLXRest")
public class proListLXRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@Autowired
	ProductionMgmtService productionMgmtService; 

	@GetMapping("/proItemTestSelect")
	public List<ProductionMgmtDto> proItemTestSelect(SearchDto searchDto) {
		System.out.println(searchDto);
		List<ProductionMgmtDto> list = productionMgmtService.proItemList(searchDto);
		System.out.println(list);
		return list;
	}
	
	// proMachineListSelect2
	@RequestMapping(value = "/proMachineListSelect2", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proMachineListSelect2(HttpServletRequest request)
			throws ParseException, SQLException, org.json.simple.parser.ParseException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String EQUIPMENT_INFO_CODE = request.getParameter("EQUIPMENT_INFO_CODE");
		String EQUIPMENT_INFO_NAME = request.getParameter("EQUIPMENT_INFO_NAME");

		String sql = "SELECT \r\n"
				+ "A.PRODUCTION_WorkOrder_No,\r\n"
				+ "A.PRODUCTION_WorkOrder_ONo,\r\n"
				+ "A.PRODUCTION_Product_Code,\r\n"
				+ "B.PRODUCT_ITEM_NAME,\r\n"
				+ "A.PRODUCTION_Equipment_Code production_EQUIPMENT_CODE,\r\n"
				+ "C.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME,\r\n"
				+ "sum(A.PRODUCTION_Volume) PRODUCTION_P_Qty,\r\n"
				+ "date_format(A.PRODUCTION_Date,'%Y-%m-%d %T') PRODUCTION_Date\r\n"
				+ "FROM (select * from PRODUCTION_MGMT_TBL2 where PRODUCTION_Date >= '"+startDate+" 00:00:00' and PRODUCTION_Date < '"+endDate+" 23:59:59') A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on A.PRODUCTION_Product_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ "inner join EQUIPMENT_INFO_TBL C on A.PRODUCTION_Equipment_Code = C.EQUIPMENT_INFO_CODE\r\n";

		String where = "where ";

		if (EQUIPMENT_INFO_CODE != null) {
			where += " (A.PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_CODE + "%'"
					+ " OR C.EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_CODE + "%')";
		} else {
			where += " (C.EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_NAME + "%'"
					+ " OR A.PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_NAME + "%')";
		}

		sql += where;

		sql += " group by A.PRODUCTION_Equipment_Code, A.PRODUCTION_WorkOrder_No with rollup";

		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();

		while (rs.next()) {

			if (rs.getInt("PRODUCTION_WorkOrder_No") == 0) {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				i++;
				data1.setId(i);
				data1.setPRODUCTION_WorkOrder_ONo("Sub Total");
				// �����հ�
				data1.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
				data1.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty")); // �������?
				list.add(data1);
			} else {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				i++;
				data1.setId(i);
				data1.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_WorkOrder_ONo"));
				data1.setPRODUCTION_WorkOrder_No(rs.getInt("PRODUCTION_WorkOrder_No"));
				data1.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
				data1.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty")); // �������?
				data1.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
				data1.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data1.setPRODUCTION_Date(rs.getString("PRODUCTION_Date"));
				list.add(data1);
			}
		}
		// ���� �� �հ�
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
		conn.close();
		return list;
	}

	// proItemListSelect2
	@RequestMapping(value = "/proItemListSelect2", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proItemListSelect2(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String PRODUCT_ITEM_CODE = request.getParameter("PRODUCT_ITEM_CODE");
		String PRODUCT_ITEM_NAME = request.getParameter("PRODUCT_ITEM_NAME");

		String sql = "SELECT \r\n" + "A.PRODUCTION_WorkOrder_No,\r\n" + "A.PRODUCTION_WorkOrder_ONo,\r\n"
				+ "A.PRODUCTION_Product_Code,\r\n" + "B.PRODUCT_ITEM_NAME,\r\n"
				+ "A.PRODUCTION_Equipment_Code production_EQUIPMENT_CODE,\r\n"
				+ "C.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME,\r\n"
				+ "sum(A.PRODUCTION_Volume) PRODUCTION_P_Qty,\r\n"
				+ "date_format(A.PRODUCTION_Date,'%Y-%m-%d %T') PRODUCTION_Date\r\n" 
				+ "FROM (select * from PRODUCTION_MGMT_TBL2 where PRODUCTION_Date >= '"+startDate+" 00:00:00' and PRODUCTION_Date < '"+endDate+" 23:59:59') A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on A.PRODUCTION_Product_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ "inner join EQUIPMENT_INFO_TBL C on A.PRODUCTION_Equipment_Code = C.EQUIPMENT_INFO_CODE\r\n";

		String where = "where ";

		// ��ǰ�ڵ� �˻�
		if (PRODUCT_ITEM_CODE != null) {
			where += " A.PRODUCTION_Product_Code like '%" + PRODUCT_ITEM_CODE + "%'"
					+ " and B.PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'";
		}
		// ��ǰ�� �˻�
		else {
			where += " (B.PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'"
					+ " OR A.PRODUCTION_Product_Code like '%" + PRODUCT_ITEM_NAME + "%')";
		}

		sql += where;

		sql += " group by A.PRODUCTION_Product_Code, A.PRODUCTION_WorkOrder_No with rollup";

		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();

		while (rs.next()) {

			if (rs.getInt("PRODUCTION_WorkOrder_No") == 0) {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				i++;
				data1.setId(i);
				data1.setPRODUCTION_WorkOrder_ONo("Sub Total");
				// ��ǰ�� �հ�
				data1.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
				data1.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty")); // �������?
				list.add(data1);
			} else {
				PRODUCTION_INFO_TBL data1 = new PRODUCTION_INFO_TBL();
				i++;
				data1.setId(i);
				data1.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_WorkOrder_ONo"));
				data1.setPRODUCTION_WorkOrder_No(rs.getInt("PRODUCTION_WorkOrder_No"));
				data1.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
				data1.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));
				data1.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_P_Qty")); // �������?
				data1.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
				data1.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data1.setPRODUCTION_Date(rs.getString("PRODUCTION_Date"));
				list.add(data1);
			}
		}
		// ��ǰ �� �հ�
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
		conn.close();
		return list;
	}

	// proItemListSelect2
	@RequestMapping(value = "/proItemListSelect3", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proItemListSelect3(HttpServletRequest request)
			throws org.json.simple.parser.ParseException, SQLException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String PRODUCT_ITEM_CODE = request.getParameter("PRODUCT_ITEM_CODE");
		String PRODUCT_ITEM_NAME = request.getParameter("PRODUCT_ITEM_NAME");
		String where = "";

		String sql = "SELECT\r\n" + "			A.*,SUM(A.PRODUCTION_VOLUME) PRODUCTION_VOLUME_S,\r\n"
				+ "			B.PRODUCT_ITEM_NAME,\r\n"
				+ "			C.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME\r\n" + "FROM		\r\n" + "(\r\n"
				+ "		SELECT \r\n" + "				*\r\n" + "		FROM\r\n"
				+ "				PRODUCTION_MGMT_TBL_X\r\n" + "		WHERE PRODUCTION_MODIFY_D BETWEEN ? and ?\r\n"
				+ ") A\r\n" + "left join PRODUCT_INFO_TBL B on Production_PRODUCT_CODE = B.PRODUCT_ITEM_CODE\r\n"
				+ "left join EQUIPMENT_INFO_TBL C on A.PRODUCTION_Equipment_Code = C.EQUIPMENT_INFO_CODE";

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

		sql += " group BY A.PRODUCTION_PRODUCT_CODE, A.PRODUCTION_INFO_NUM with rollup";

		return jdbctemplate.query(sql, new RowMapper<PRODUCTION_INFO_TBL>() {
			@Override
			public PRODUCTION_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();

				if (rs.getString("PRODUCTION_PRODUCT_CODE") == null && rs.getString("PRODUCTION_INFO_NUM") == null) {
					data.setPRODUCTION_WorkOrder_ONo("Grand Total");
					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
					data.setPRODUCTION_PRODUCT_CODE("");
					data.setPRODUCT_ITEM_NAME("");
				} else if (rs.getString("PRODUCTION_INFO_NUM") == null) {
					data.setPRODUCTION_WorkOrder_ONo("Sub Total");
					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
					data.setPRODUCTION_PRODUCT_CODE("");
					data.setPRODUCT_ITEM_NAME("");
				} else {
					data.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_SERIAL_NUM"));
					data.setPRODUCTION_WorkOrder_No(rs.getInt("PRODUCTION_INFO_NUM"));

					data.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
					data.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));

					data.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
					data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
					data.setPRODUCTION_Date(rs.getString("PRODUCTION_MODIFY_D"));

					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
				}

				return data;
			}
		}, startDate, endDate);
	}

	// proMachineListSelect2
	@RequestMapping(value = "/proMachineListSelect3", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proMachineListSelect3(HttpServletRequest request)
			throws ParseException, SQLException, org.json.simple.parser.ParseException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String EQUIPMENT_INFO_CODE = request.getParameter("EQUIPMENT_INFO_CODE");
		String EQUIPMENT_INFO_NAME = request.getParameter("EQUIPMENT_INFO_NAME");
		String where = "";

		String sql = "SELECT\r\n" + "			A.*,SUM(A.PRODUCTION_VOLUME) PRODUCTION_VOLUME_S,\r\n"
				+ "			B.PRODUCT_ITEM_NAME,\r\n"
				+ "			C.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME\r\n" + "FROM		\r\n" + "(\r\n"
				+ "		SELECT \r\n" + "				*\r\n" + "		FROM\r\n"
				+ "				PRODUCTION_MGMT_TBL_X\r\n" + "		WHERE PRODUCTION_MODIFY_D BETWEEN ? and ?\r\n"
				+ ") A\r\n" + "left join PRODUCT_INFO_TBL B on Production_PRODUCT_CODE = B.PRODUCT_ITEM_CODE\r\n"
				+ "left join EQUIPMENT_INFO_TBL C on A.PRODUCTION_Equipment_Code = C.EQUIPMENT_INFO_CODE";

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

		sql += " group BY A.PRODUCTION_EQUIPMENT_CODE, A.PRODUCTION_INFO_NUM with rollup";

		return jdbctemplate.query(sql, new RowMapper<PRODUCTION_INFO_TBL>() {
			@Override
			public PRODUCTION_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();
				
				
				if (rs.getString("PRODUCTION_EQUIPMENT_CODE") == null && rs.getString("PRODUCTION_INFO_NUM") == null)
				{
					data.setPRODUCTION_WorkOrder_ONo("Grand Total");
					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
					data.setPRODUCTION_PRODUCT_CODE("");
					data.setPRODUCT_ITEM_NAME("");
				}
				else if (rs.getString("PRODUCTION_INFO_NUM") == null)
				{
					data.setPRODUCTION_WorkOrder_ONo("Sub Total");
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
					data.setPRODUCTION_Date(rs.getString("PRODUCTION_MODIFY_D"));

					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
				}

				return data;
			}
		}, startDate, endDate);
	}

}
