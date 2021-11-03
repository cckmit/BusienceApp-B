package com.busience.productionLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.busience.productionLX.dto.WorkOrder_tbl;

@RestController("workOrderTABRestXController")
@RequestMapping("workOrderTABRestX")
public class workOrderTABRestXController {

	@Autowired
	JdbcTemplate jdbctemplate;

	@RequestMapping(value = "/MI_Searchd", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Searchd(HttpServletRequest request) throws SQLException {
		System.out.println(request.getParameter("startDate"));
		System.out.println(request.getParameter("endDate"));
		System.out.println(request.getParameter("WorkOrder_EquipCode"));

		String startDate = request.getParameter("startDate") + " 00:00:00";
		String endDate = request.getParameter("endDate") + " 23:59:59";
		String sql = "SELECT \r\n" + "			*,\r\n" + "			a2.PRODUCT_ITEM_NAME,\r\n"
				+ "			a2.PRODUCT_INFO_STND_1\r\n" + "FROM WorkOrder_tbl_X a1\r\n"
				+ "LEFT JOIN 	PRODUCT_INFO_TBL a2 ON a1.PRODUCTION_PRODUCT_CODE = a2.PRODUCT_ITEM_CODE\r\n"
				+ "WHERE			a1.PRODUCTION_START_TIME BETWEEN ? and ?\r\n"
				+ "AND			a1.PRODUCTION_EQUIPMENT_CODE = ?\r\n" + "ORDER BY    a1.PRODUCTION_END_TIME";

		return jdbctemplate.query(sql, new RowMapper() {
			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("PRODUCTION_SERIAL_NUM"));

				data.setWorkOrder_ItemCode(rs.getString("PRODUCTION_PRODUCT_CODE"));
				data.setWorkOrder_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));

				data.setQty(rs.getString("PRODUCTION_SUM_VOLUME"));
				data.setPercent(rs.getString("PRODUCTION_SUM_BAD"));

				data.setWorkOrder_StartTime(rs.getString("PRODUCTION_START_TIME"));
				data.setWorkOrder_CompleteTime(rs.getString("PRODUCTION_END_TIME"));

				return data;
			}
		}, startDate, endDate, request.getParameter("WorkOrder_EquipCode"));
	}

	@RequestMapping(value = "/MI_Searchc", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Searchc(HttpServletRequest request) throws SQLException {
		String startDate = request.getParameter("startDate") + " 00:00:00";
		String endDate = request.getParameter("endDate") + " 23:59:59";

		String sql = "SELECT\r\n" + "			*\r\n" + "FROM		WorkOrder_tbl_X\r\n"
				+ "WHERE		PRODUCTION_EQUIPMENT_CODE = ?\r\n" + "AND		PRODUCTION_CC='S'";

		System.out.println(sql);

		return jdbctemplate.query(sql, new RowMapper() {
			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("PRODUCTION_SERIAL_NUM"));
				return data;
			}
		}, request.getParameter("WorkOrder_EquipCode"));
	}

	@RequestMapping(value = "/MI_Searche", method = RequestMethod.GET)
	public void MI_Searche(HttpServletRequest request) throws SQLException {
		String WorkOrder_EquipCode = request.getParameter("WorkOrder_EquipCode");
		String PRODUCTION_SERIAL_NUM = request.getParameter("PRODUCTION_SERIAL_NUM");

		String sql = "UPDATE WorkOrder_tbl_X SET PRODUCTION_END_TIME = NOW(),\r\n" + "PRODUCTION_CC='E',\r\n"
				+ "PRODUCTION_SUM_VOLUME=IFNULL((SELECT SUM(PRODUCTION_VOLUME) FROM PRODUCTION_MGMT_TBL_X WHERE PRODUCTION_SERIAL_NUM = ? GROUP BY PRODUCTION_SERIAL_NUM),0),\r\n"
				+ "PRODUCTION_SUM_BAD=IFNULL((SELECT SUM(PRODUCTION_BAD) FROM PRODUCTION_MGMT_TBL_X WHERE PRODUCTION_SERIAL_NUM = ? GROUP BY PRODUCTION_SERIAL_NUM),0)\r\n"
				+ "WHERE PRODUCTION_SERIAL_NUM = ?";

		jdbctemplate.update(sql, PRODUCTION_SERIAL_NUM, PRODUCTION_SERIAL_NUM, PRODUCTION_SERIAL_NUM);
	}

	@RequestMapping(value = "/MI_Searchi", method = RequestMethod.GET)
	public void MI_Searchi(HttpServletRequest request) throws SQLException {
		String WorkOrder_EquipCode = request.getParameter("WorkOrder_EquipCode");
		String PRODUCTION_PRODUCT_CODE = request.getParameter("PRODUCTION_PRODUCT_CODE");

		SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
		Date time = new Date();
		String time1 = format1.format(time);

		String sql = "INSERT INTO\r\n" + "WorkOrder_tbl_X\r\n" + "VALUES(\r\n"
				+ "CONCAT(DATE_FORMAT(NOW(),'%y%m%d'),(SELECT LPAD(COUNT(DISTINCT PRODUCTION_SERIAL_NUM),'2','0') s FROM WorkOrder_tbl_X t1 WHERE DATE_FORMAT(PRODUCTION_START_TIME,'%y%m%d') = '"
				+ time1 + "' AND PRODUCTION_EQUIPMENT_CODE='M001'),'" + PRODUCTION_PRODUCT_CODE + "','"
				+ WorkOrder_EquipCode + "'),\r\n" + "	?,\r\n" + "	?,\r\n" + "	0,\r\n" + "	0,\r\n" + "	'S',\r\n"
				+ "	NOW(),\r\n" + "	NULL,\r\n" + "	'test01'\r\n" + ")";

		jdbctemplate.update(sql, WorkOrder_EquipCode, PRODUCTION_PRODUCT_CODE);
	}

}
