package com.busience.productionLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.WorkOrder_tbl;
import com.busience.salesLX.dto.Sales_StockMat_tbl;

@RestController("workOrderStartBRestController")
@RequestMapping("workOrderStartBRest")
public class workOrderStartBRestController {
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@GetMapping("/workOrderStartInit")
	public List<Sales_StockMat_tbl> workOrderStartInit(Model model, HttpServletRequest request){
		String sql = "SELECT\r\n"
				+ "				a1.*,\r\n"
				+ "				a2.PRODUCT_ITEM_NAME,\r\n"
				+ "				a2.PRODUCT_INFO_STND_1\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_RegisterTime, '%Y-%m-%d %H:%i') WorkOrder_RegisterTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_ReceiptTime, '%Y-%m-%d %H:%i') WorkOrder_ReceiptTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_OrderTime, '%Y-%m-%d %H:%i') WorkOrder_OrderTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_StartTime, '%Y-%m-%d %H:%i') WorkOrder_StartTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_CompleteOrderTime, '%Y-%m-%d %H:%i') WorkOrder_CompleteOrderTime2\r\n"
				+ "				,DATE_FORMAT(a1.WorkOrder_CompleteTime, '%Y-%m-%d %H:%i') WorkOrder_CompleteTime2\r\n"
				+ "FROM			WorkOrder_tbl a1\r\n"
				+ "LEFT JOIN	PRODUCT_INFO_TBL a2 ON a1.WorkOrder_ItemCode = a2.PRODUCT_ITEM_CODE\r\n"
				+ "WHERE			a1.WorkOrder_EquipCode='"+request.getParameter("eqselect")+"'\r\n"
				+ "AND			a1.WorkOrder_WorkStatus='244'";
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				 data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				 
				 data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				 data.setWorkOrder_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
				 data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				 
				 data.setWorkOrder_PQty((rs.getString("WorkOrder_PQty") == null)? "0" : rs.getString("WorkOrder_PQty"));
				 data.setWorkOrder_RQty((rs.getString("WorkOrder_RQty") == null)? "0" : rs.getString("WorkOrder_RQty"));
				 
				 data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime2"));
				 data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime2"));
				 data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
				
				return data;
			}
			
		});
	}
	
	@GetMapping("/workOrderSumQty")
	public Float workOrderSumQty(Model model, HttpServletRequest request)
	{
		String sql = "SELECT SUM(PRODUCTION_Volume) sum FROM PRODUCTION_MGMT_TBL2 a1 WHERE a1.PRODUCTION_WorkOrder_ONo=(SELECT t1.WorkOrder_ONo FROM WorkOrder_tbl t1 WHERE t1.WorkOrder_EquipCode='"+request.getParameter("eqselect")+"' AND t1.WorkOrder_WorkStatus='244')";
		
		sql = "SELECT\r\n"
				+ "			SUM(PRODUCTION_Volume) sum\r\n"
				+ "FROM		PRODUCTION_MGMT_TBL2 t1\r\n"
				+ "INNER JOIN WorkOrder_tbl t2\r\n"
				+ "ON t1.PRODUCTION_WorkOrder_ONo = t2.WorkOrder_ONo\r\n"
				+ "WHERE\r\n"
				+ "t2.WorkOrder_EquipCode = '"+request.getParameter("eqselect")+"' AND\r\n"
				+ "t2.WorkOrder_WorkStatus = '245' AND\r\n"
				+ "DATE_FORMAT(t2.WorkOrder_CompleteTime, \"%Y-%c-%e\") = DATE_FORMAT(NOW(), \"%Y-%c-%e\")";
		
		Float sum = jdbctemplate.queryForObject(sql, new RowMapper<Float>() {

			@Override
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum");
			}
		});
		
		return sum;
	}
	
	@GetMapping("/workOrderCurrentQty")
	public Float workOrderCurrentQty(Model model, HttpServletRequest request)
	{
		/*
		 * String sql =
		 * "SELECT * FROM PRODUCTION_MGMT_TBL2 a1 WHERE a1.PRODUCTION_WorkOrder_ONo=(SELECT t1.WorkOrder_ONo FROM WorkOrder_tbl t1 WHERE t1.WorkOrder_EquipCode='"
		 * +request.getParameter("eqselect")
		 * +"' AND t1.WorkOrder_WorkStatus='244') ORDER BY PRODUCTION_Date DESC LIMIT 1"
		 * ;
		 */
		
		String sql = "SELECT SUM(PRODUCTION_Volume) sum FROM PRODUCTION_MGMT_TBL2 a1 WHERE a1.PRODUCTION_WorkOrder_ONo=(SELECT t1.WorkOrder_ONo FROM WorkOrder_tbl t1 WHERE t1.WorkOrder_EquipCode='"+request.getParameter("eqselect")+"' AND t1.WorkOrder_WorkStatus='244')";
		
		Float CurrentQty = jdbctemplate.queryForObject(sql, new RowMapper<Float>() {

			@Override
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum");
			}
		});
		
		return CurrentQty;
	}
	
}
