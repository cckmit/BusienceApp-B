package com.busience.production.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("workOrderStartBRestController")
@RequestMapping("workOrderStartBRest")
public class workOrderStartBRestController {
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@GetMapping("/workOrderSumQty")
	public Float workOrderSumQty(Model model, HttpServletRequest request)
	{
		String sql = "SELECT SUM(PRODUCTION_Volume) sum FROM PRODUCTION_MGMT_TBL2 a1 WHERE a1.PRODUCTION_WorkOrder_ONo=("
				+ "SELECT t1.WorkOrder_ONo FROM WorkOrder_tbl t1 WHERE t1.WorkOrder_EquipCode='"+request.getParameter("eqselect")+"' AND t1.WorkOrder_WorkStatus='244')"
						+ "and PRODUCTION_Equipment_Code = '"+request.getParameter("eqselect")+"'";
		
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
		
		String sql = "SELECT SUM(PRODUCTION_Volume) sum FROM PRODUCTION_MGMT_TBL2 a1 WHERE a1.PRODUCTION_WorkOrder_ONo=("
				+ "SELECT t1.WorkOrder_ONo FROM WorkOrder_tbl t1 WHERE t1.WorkOrder_EquipCode='"+request.getParameter("eqselect")+"' AND t1.WorkOrder_WorkStatus='244')"
				+ "and PRODUCTION_Equipment_Code = '"+request.getParameter("eqselect")+"'";
		
		Float CurrentQty = jdbctemplate.queryForObject(sql, new RowMapper<Float>() {

			@Override
			public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getFloat("sum");
			}
		});
		
		return CurrentQty;
	}
	
}
