package com.busience.productionLX.controller;

import java.security.Principal;
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

@RestController("workOrderTABRestXOController")
@RequestMapping("workOrderTABRestXO")
public class workOrderTABRestXOController {

	@Autowired
	JdbcTemplate jdbctemplate;

	@RequestMapping(value = "/MI_Searchd", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Searchd(HttpServletRequest request) throws SQLException {
		System.out.println(request.getParameter("startDate"));
		System.out.println(request.getParameter("endDate"));
		System.out.println(request.getParameter("WorkOrder_EquipCode"));

		String startDate = request.getParameter("startDate") + " 00:00:00";
		String endDate = request.getParameter("endDate") + " 23:59:59";
		
		/*
		String sql = "SELECT\r\n"
				+ "			*\r\n"
				+ "FROM\r\n"
				+ "(\r\n"
				+ "	SELECT\r\n"
				+ "			a1.*,\r\n"
				+ "			a2.PRODUCT_ITEM_NAME,\r\n"
				+ "			a2.PRODUCT_INFO_STND_1\r\n"
				+ "	FROM			WorkOrder_tbl a1\r\n"
				+ "	LEFT JOIN	PRODUCT_INFO_TBL a2 ON a1.WorkOrder_ItemCode = a2.PRODUCT_ITEM_CODE\r\n"
				+ "	WHERE		a1.WorkOrder_EquipCode='"+request.getParameter("WorkOrder_EquipCode")+"'\r\n"
				+ "	AND		a1.WorkOrder_WorkStatus='244'\r\n"
				+ ")  t1\r\n"
				+ "UNION ALL\r\n"
				+ "(\r\n"
				+ "	SELECT\r\n"
				+ "			a1.*,\r\n"
				+ "			a2.PRODUCT_ITEM_NAME,\r\n"
				+ "			a2.PRODUCT_INFO_STND_1\r\n"
				+ "	FROM			WorkOrder_tbl a1\r\n"
				+ "	LEFT JOIN	PRODUCT_INFO_TBL a2 ON a1.WorkOrder_ItemCode = a2.PRODUCT_ITEM_CODE\r\n"
				+ "	WHERE		WorkOrder_ReceiptTime BETWEEN '"+startDate+"' AND '"+endDate+"'\r\n"
				+ "	AND		a1.WorkOrder_EquipCode='"+request.getParameter("WorkOrder_EquipCode")+"'\r\n"
				+ "	AND		a1.WorkOrder_WorkStatus='245'\r\n"
				+ ")";
		*/
		
		String sql = "SELECT\r\n"
				+ "			a1.*,\r\n"
				+ "			a2.PRODUCT_ITEM_NAME,\r\n"
				+ "			a2.PRODUCT_INFO_STND_1\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_RegisterTime, '%Y-%m-%d %H:%i') WorkOrder_RegisterTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_ReceiptTime, '%Y-%m-%d %H:%i') WorkOrder_ReceiptTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_OrderTime, '%Y-%m-%d %H:%i') WorkOrder_OrderTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_StartTime, '%Y-%m-%d %H:%i') WorkOrder_StartTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_CompleteOrderTime, '%Y-%m-%d %H:%i') WorkOrder_CompleteOrderTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_CompleteTime, '%Y-%m-%d %H:%i') WorkOrder_CompleteTime2\r\n"
				+ "	FROM			WorkOrder_tbl a1\r\n"
				+ "	LEFT JOIN	PRODUCT_INFO_TBL a2 ON a1.WorkOrder_ItemCode = a2.PRODUCT_ITEM_CODE\r\n"
				+ "	WHERE		WorkOrder_ReceiptTime BETWEEN '"+startDate+"' AND '"+endDate+"'\r\n"
				+ "	AND		a1.WorkOrder_EquipCode='"+request.getParameter("WorkOrder_EquipCode")+"'\r\n"
				+ "	AND		a1.WorkOrder_WorkStatus='245'";
		
		System.out.println(sql);
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				 data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				 
				 data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				 data.setWorkOrder_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
				 data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				 
				 data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
				 data.setWorkOrder_RQty((rs.getString("WorkOrder_RQty") == null)? "0" : rs.getString("WorkOrder_RQty"));
				 
				 data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime2"));
				 data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime2"));
				 
				 data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));
				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
				//data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTimef"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));	
				
				data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
				
				return data;
			}
			
		});
	}
	
	@RequestMapping(value = "/MI_Searchd2", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Searchd2(HttpServletRequest request) throws SQLException {
		System.out.println(request.getParameter("startDate"));
		System.out.println(request.getParameter("endDate"));
		System.out.println(request.getParameter("WorkOrder_EquipCode"));

		String startDate = request.getParameter("startDate") + " 00:00:00";
		String endDate = request.getParameter("endDate") + " 23:59:59";
		
		String sql = "SELECT\r\n"
				+ "			*\r\n"
				+ "FROM\r\n"
				+ "(\r\n"
				+ "	SELECT\r\n"
				+ "			a1.*,\r\n"
				+ "			a2.PRODUCT_ITEM_NAME,\r\n"
				+ "			a2.PRODUCT_INFO_STND_1\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_RegisterTime, '%Y-%m-%d %H:%i') WorkOrder_RegisterTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_ReceiptTime, '%Y-%m-%d %H:%i') WorkOrder_ReceiptTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_OrderTime, '%Y-%m-%d %H:%i') WorkOrder_OrderTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_StartTime, '%Y-%m-%d %H:%i') WorkOrder_StartTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_CompleteOrderTime, '%Y-%m-%d %H:%i') WorkOrder_CompleteOrderTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_CompleteTime, '%Y-%m-%d %H:%i') WorkOrder_CompleteTime2\r\n"
				+ "	FROM			WorkOrder_tbl a1\r\n"
				+ "	LEFT JOIN	PRODUCT_INFO_TBL a2 ON a1.WorkOrder_ItemCode = a2.PRODUCT_ITEM_CODE\r\n"
				+ "	WHERE		a1.WorkOrder_EquipCode='"+request.getParameter("WorkOrder_EquipCode")+"'\r\n"
				+ "	AND		a1.WorkOrder_WorkStatus='244'\r\n"
				+ ")  t1\r\n"
				+ "UNION ALL\r\n"
				+ "(\r\n"
				+ "	SELECT\r\n"
				+ "			a1.*,\r\n"
				+ "			a2.PRODUCT_ITEM_NAME,\r\n"
				+ "			a2.PRODUCT_INFO_STND_1\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_RegisterTime, '%Y-%m-%d %H:%i') WorkOrder_RegisterTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_ReceiptTime, '%Y-%m-%d %H:%i') WorkOrder_ReceiptTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_OrderTime, '%Y-%m-%d %H:%i') WorkOrder_OrderTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_StartTime, '%Y-%m-%d %H:%i') WorkOrder_StartTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_CompleteOrderTime, '%Y-%m-%d %H:%i') WorkOrder_CompleteOrderTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_CompleteTime, '%Y-%m-%d %H:%i') WorkOrder_CompleteTime2\r\n"
				+ "	FROM			WorkOrder_tbl a1\r\n"
				+ "	LEFT JOIN	PRODUCT_INFO_TBL a2 ON a1.WorkOrder_ItemCode = a2.PRODUCT_ITEM_CODE\r\n"
				+ "	WHERE		a1.WorkOrder_EquipCode='"+request.getParameter("WorkOrder_EquipCode")+"'\r\n"
				+ "	AND		a1.WorkOrder_WorkStatus<>'244'\r\n"
				+ "	AND		a1.WorkOrder_WorkStatus<>'245'\r\n"
				//+ "	AND		a1.WorkOrder_ReceiptTime >= (DATE_FORMAT(DATE_ADD(NOW(), INTERVAL -2 DAY),'%Y-%m-%d'))\r\n"
				+ " AND		a1.WorkOrder_RegisterTime >= DATE_FORMAT(NOW(),'%Y-%m-%d')"
				+ ")";
		
		System.out.println(sql);
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				 
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				 
				data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
				data.setWorkOrder_RQty((rs.getString("WorkOrder_RQty") == null)? "0" : rs.getString("WorkOrder_RQty"));
				 
				data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime2"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime2"));
				 
				data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime2"));
				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime2"));
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime2"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));	
				
				return data;
			}
			
		});
	}

	@RequestMapping(value = "/MI_Searchc", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Searchc(HttpServletRequest request) throws SQLException {
		String startDate = request.getParameter("startDate") + " 00:00:00";
		String endDate = request.getParameter("endDate") + " 23:59:59";

		String sql = "SELECT\r\n" + "			*\r\n" + "FROM		WorkOrder_tbl\r\n"
				+ "WHERE		WorkOrder_EquipCode = ?\r\n" + "AND		WorkOrder_WorkStatus='244'";

		System.out.println(sql);

		return jdbctemplate.query(sql, new RowMapper() {
			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				return data;
			}
		}, request.getParameter("WorkOrder_EquipCode"));
	}

	@RequestMapping(value = "/MI_Searche", method = RequestMethod.GET)
	public void MI_Searche(HttpServletRequest request) throws SQLException {
		String WorkOrder_EquipCode = request.getParameter("WorkOrder_EquipCode");
		String PRODUCTION_SERIAL_NUM = request.getParameter("PRODUCTION_SERIAL_NUM");
		String WorkOrder_WorkStatus = request.getParameter("WorkOrder_WorkStatus");

		/*
		String sql = "UPDATE WorkOrder_tbl_X SET PRODUCTION_END_TIME = NOW(),\r\n" + "PRODUCTION_CC='E',\r\n"
				+ "PRODUCTION_SUM_VOLUME=IFNULL((SELECT SUM(PRODUCTION_VOLUME) FROM PRODUCTION_MGMT_TBL_X WHERE PRODUCTION_SERIAL_NUM = ? GROUP BY PRODUCTION_SERIAL_NUM),0),\r\n"
				+ "PRODUCTION_SUM_BAD=IFNULL((SELECT SUM(PRODUCTION_BAD) FROM PRODUCTION_MGMT_TBL_X WHERE PRODUCTION_SERIAL_NUM = ? GROUP BY PRODUCTION_SERIAL_NUM),0)\r\n"
				+ "WHERE PRODUCTION_SERIAL_NUM = ?";

		jdbctemplate.update(sql, PRODUCTION_SERIAL_NUM, PRODUCTION_SERIAL_NUM, PRODUCTION_SERIAL_NUM);
		*/
		
		String sql = "";
		
		if(WorkOrder_WorkStatus.equals("243"))
		{
			sql = "UPDATE WorkOrder_tbl SET WorkOrder_WorkStatus = '243',\r\n"
					+ "WorkOrder_StartTime = NULL,\r\n"
					+ "WorkOrder_CompleteTime = NULL\r\n"
					+ "WHERE WorkOrder_ONo = ? AND WorkOrder_EquipCode=? AND WorkOrder_WorkStatus='244'";
			
			jdbctemplate.update(sql, PRODUCTION_SERIAL_NUM, WorkOrder_EquipCode);
		}
		else
		{
			sql = "UPDATE WorkOrder_tbl SET WorkOrder_WorkStatus = '245',\r\n"
					+ "WorkOrder_PQty = IFNULL((SELECT SUM(PRODUCTION_Volume) FROM PRODUCTION_MGMT_TBL2 WHERE PRODUCTION_WorkOrder_ONo=?),0),\r\n"
					+ "WorkOrder_RQty = IFNULL((SELECT SUM(PRODUCTION_Volume) FROM PRODUCTION_MGMT_TBL2 WHERE PRODUCTION_WorkOrder_ONo=?),0),\r\n"
					+ "WorkOrder_CompleteTime = NOW()\r\n"
					+ "WHERE WorkOrder_ONo = ? AND WorkOrder_EquipCode=? AND WorkOrder_WorkStatus='244'";
			
			jdbctemplate.update(sql, PRODUCTION_SERIAL_NUM, PRODUCTION_SERIAL_NUM, PRODUCTION_SERIAL_NUM, WorkOrder_EquipCode);
			
			sql = "DELETE\r\n"
					+ "FROM		WorkOrder_tbl\r\n"
					+ "WHERE 0 = (SELECT t1.WorkOrder_RQty FROM (SELECT WorkOrder_RQty FROM WorkOrder_tbl  WHERE WorkOrder_EquipCode='"+WorkOrder_EquipCode+"' AND WorkOrder_WorkStatus='245' ORDER BY WorkOrder_RegisterTime DESC LIMIT 1) t1)\r\n"
					+ "AND	WorkOrder_ONo = (SELECT t2.WorkOrder_ONo FROM (SELECT WorkOrder_ONo FROM WorkOrder_tbl  WHERE WorkOrder_EquipCode='"+WorkOrder_EquipCode+"' AND WorkOrder_WorkStatus='245' ORDER BY WorkOrder_RegisterTime DESC LIMIT 1) t2)";
			
			System.out.println(sql);
			
			jdbctemplate.update(sql);
		}
	}
	
	@RequestMapping(value = "/MI_Searche2", method = RequestMethod.GET)
	public void MI_Searche2(HttpServletRequest request) throws SQLException{
		String WorkOrder_EquipCode = request.getParameter("WorkOrder_EquipCode");
		
		String sql = "SELECT IFNULL((SELECT SUM(t1.PRODUCTION_Volume) FROM PRODUCTION_MGMT_TBL2 t1 WHERE t1.PRODUCTION_WorkOrder_ONo = (SELECT t2.WorkOrder_ONo FROM WorkOrder_tbl t2 WHERE t2.WorkOrder_EquipCode=? AND t2.WorkOrder_WorkStatus='244')),0) hap";
	
		Integer hap = jdbctemplate.queryForObject(sql, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getInt("hap");
			}
		},WorkOrder_EquipCode);
		
		sql = "UPDATE WorkOrder_tbl SET WorkOrder_WorkStatus = '245',\r\n"
				+ "WorkOrder_PQty = ?,\r\n"
				+ "WorkOrder_RQty = ?,\r\n"
				+ "WorkOrder_CompleteTime = NOW()\r\n"
				+ "WHERE WorkOrder_EquipCode=? AND WorkOrder_WorkStatus='244'";
		
		jdbctemplate.update(sql, hap, hap, WorkOrder_EquipCode);
		
		sql = "DELETE\r\n"
				+ "FROM		WorkOrder_tbl\r\n"
				+ "WHERE 0 = (SELECT t1.WorkOrder_RQty FROM (SELECT WorkOrder_RQty FROM WorkOrder_tbl  WHERE WorkOrder_EquipCode='"+WorkOrder_EquipCode+"' AND WorkOrder_WorkStatus='245' ORDER BY WorkOrder_RegisterTime DESC LIMIT 1) t1)\r\n"
				+ "AND	WorkOrder_ONo = (SELECT t2.WorkOrder_ONo FROM (SELECT WorkOrder_ONo FROM WorkOrder_tbl  WHERE WorkOrder_EquipCode='"+WorkOrder_EquipCode+"' AND WorkOrder_WorkStatus='245' ORDER BY WorkOrder_RegisterTime DESC LIMIT 1) t2)";
		
		jdbctemplate.update(sql);
	}

	@RequestMapping(value = "/MI_Searchi", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Searchi(HttpServletRequest request, Principal principal) throws SQLException {
		String WorkOrder_EquipCode = request.getParameter("WorkOrder_EquipCode");
		String PRODUCTION_PRODUCT_CODE = request.getParameter("PRODUCTION_PRODUCT_CODE");

		String sql = "SELECT COUNT(*) count FROM WorkOrder_tbl WHERE WorkOrder_EquipCode='"+WorkOrder_EquipCode+"' AND WorkOrder_WorkStatus = '244'";
		
		Integer count = jdbctemplate.queryForObject(sql, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("count");
			}
		});
		
		if(count == 0)
		{
			sql = "INSERT INTO WorkOrder_tbl\r\n"
					+ "(\r\n"
					+ "	WorkOrder_No,\r\n"
					+ "	WorkOrder_ONo,\r\n"
					+ "	WorkOrder_ItemCode,\r\n"
					+ "	WorkOrder_EquipCode,\r\n"
					+ "	WorkOrder_RegisterTime,\r\n"
					+ "	WorkOrder_ReceiptTime,\r\n"
					+ "	WorkOrder_OrderTime,\r\n"
					+ "	WorkOrder_StartTime,\r\n"
					+ "	WorkOrder_CompleteOrderTime,\r\n"
					+ "	WorkOrder_WorkStatus,\r\n"
					+ "	WorkOrder_Worker,\r\n"
					+ "	WorkOrder_Remark\r\n"
					+ ")\r\n"
					+ "VALUES(\r\n"
					+ "	(SELECT count(*)+1 WorkOrder_ONo FROM WorkOrder_tbl a1 where LEFT(WorkOrder_ONo,8) = CURDATE()),\r\n"
					+ "	CONCAT(\r\n"
					+ "	date_format(CURDATE(),\"%Y%m%d\"),\r\n"
					+ "	'-"+PRODUCTION_PRODUCT_CODE+"-',\r\n"
					+ "	LPAD((SELECT count(*)+1 WorkOrder_ONo FROM WorkOrder_tbl a1 where LEFT(WorkOrder_ONo,8) = CURDATE()),'2','0')\r\n"
					+ "	),\r\n"
					+ "	'"+PRODUCTION_PRODUCT_CODE+"',\r\n"
					+ "	'"+WorkOrder_EquipCode+"',\r\n"
					+ "	DATE_ADD(NOW(), INTERVAL -2 DAY),\r\n"
					+ "	DATE_ADD(NOW(), INTERVAL -1 MINUTE),\r\n"
					+ "	DATE_ADD(NOW(), INTERVAL -1 DAY),\r\n"
					+ "	NOW(),\r\n"
					+ "	DATE_ADD(NOW(), INTERVAL +1 DAY),\r\n"
					+ "	'244',\r\n"
					+ "	'"+principal.getName()+"',\r\n"
					+ "	'AUTO'\r\n"
					+ ")";
			
			System.out.println(sql);
			
			jdbctemplate.update(sql);
		}
		
		return jdbctemplate.query("SELECT t1.*,t2.PRODUCT_ITEM_NAME,t2.PRODUCT_INFO_STND_1 FROM WorkOrder_tbl t1 LEFT JOIN PRODUCT_INFO_TBL t2 ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE WHERE t1.WorkOrder_EquipCode=? AND t1.WorkOrder_WorkStatus='244'", new RowMapper() {
			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				 
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				 
				data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
				data.setWorkOrder_RQty((rs.getString("WorkOrder_RQty") == null)? "0" : rs.getString("WorkOrder_RQty"));
				
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));
				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
				
				data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				
				return data;
			}
		}, request.getParameter("WorkOrder_EquipCode"));
	}

}
