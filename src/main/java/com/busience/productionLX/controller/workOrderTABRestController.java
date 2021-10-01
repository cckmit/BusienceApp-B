package com.busience.productionLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.WorkOrder_tbl;
import com.busience.standard.Dto.EQUIPMENT_INFO_TBL;

@RestController("workOrderTABRestController")
@RequestMapping("workOrderTABRest")
public class workOrderTABRestController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/MI_Search1", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Search1(HttpServletRequest request) throws SQLException
	{
		/*
		
		SELECT 
					*,
					t2.PRODUCT_ITEM_NAME WorkOrder_ItemName
		FROM WorkOrder_tbl t1
		LEFT JOIN PRODUCT_INFO_TBL t2
		ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE
		where t1.WorkOrder_EquipCode = ?
		*/
		String sql = "SELECT \r\n"
				+ "					*,\r\n"
				+ "					t2.PRODUCT_ITEM_NAME WorkOrder_ItemName\r\n"
				+ "		FROM WorkOrder_tbl t1\r\n"
				+ "		LEFT JOIN PRODUCT_INFO_TBL t2\r\n"
				+ "		ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE\r\n"
				+ "		where t1.WorkOrder_EquipCode = ?";
		System.out.println(sql);
		return jdbctemplate.query(sql, new RowMapper<WorkOrder_tbl>() {
			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
				data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				return data;
			}
		},request.getParameter("WorkOrder_EquipCode"));
	}
	
	@RequestMapping(value = "/MI_Search2", method = RequestMethod.GET)
	public WorkOrder_tbl MI_Search2(HttpServletRequest request) throws SQLException
	{
		return jdbctemplate.queryForObject("SELECT \r\n"
				+ "					*,\r\n"
				+ "					t2.PRODUCT_ITEM_NAME WorkOrder_ItemName\r\n"
				+ "		FROM WorkOrder_tbl t1\r\n"
				+ "		LEFT JOIN PRODUCT_INFO_TBL t2\r\n"
				+ "		ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE where workOrder_ONo='"+request.getParameter("workOrder_ONo")+"'", new RowMapper<WorkOrder_tbl>() {
			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime"));
				data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				return data;
			}
		});
	}
	
	@RequestMapping(value = "/EQUIPMENT_Export", method = RequestMethod.GET)
	public List<EQUIPMENT_INFO_TBL> EQUIPMENT_Export(HttpServletRequest request) throws SQLException
	{
		return jdbctemplate.query("SELECT * FROM EQUIPMENT_INFO_TBL", new RowMapper<EQUIPMENT_INFO_TBL>() {
			@Override
			public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
				data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
				data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
				return data;
			}
		});
	}
}
