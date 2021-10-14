package com.busience.productionLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
		WorkOrder_CompleteOrderTime
		SELECT 
					*,
					t2.PRODUCT_ITEM_NAME WorkOrder_ItemName
		FROM WorkOrder_tbl t1
		LEFT JOIN PRODUCT_INFO_TBL t2
		ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE
		where t1.WorkOrder_EquipCode = ? or date(t1.WorkOrder_CompleteTime) = DATE(NOW()) or WorkOrder_CompleteTime = NULL
		ORDER BY t1.WorkOrder_OrderTime DESC
		*/
		String sql = "SELECT \r\n"
				+ "					*,\r\n"
				+ "					t2.PRODUCT_ITEM_NAME WorkOrder_ItemName\r\n"
				+ "					,DATE_FORMAT(t1.WorkOrder_CompleteOrderTime,'%Y-%m-%d') WorkOrder_CompleteOrderTimef"
				+ "                 ,t2.PRODUCT_INFO_STND_1"
				+ "                 ,t2.PRODUCT_UNIT_PRICE"
				+ "		FROM WorkOrder_tbl t1\r\n"
				+ "		LEFT JOIN PRODUCT_INFO_TBL t2\r\n"
				+ "		ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE\r\n"
				+ "		where t1.WorkOrder_EquipCode = ? and ((date(t1.WorkOrder_OrderTime) = DATE(NOW()) or date(t1.WorkOrder_ReceiptTime) = DATE(NOW()) or date(t1.WorkOrder_StartTime) = DATE(NOW()) or date(t1.WorkOrder_CompleteTime) = DATE(NOW() ))) ORDER BY t1.WorkOrder_OrderTime DESC";
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
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTimef"));
				data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				return data;
			}
		},request.getParameter("WorkOrder_EquipCode"));
	}
	
	@RequestMapping(value = "/MI_Searchd", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Searchd(HttpServletRequest request) throws SQLException
	{
		System.out.println(request.getParameter("startDate"));
		System.out.println(request.getParameter("endDate"));
		System.out.println(request.getParameter("WorkOrder_EquipCode"));
		
		String sql = "SELECT \r\n"
				+ "					*,\r\n"
				+ "					t2.PRODUCT_ITEM_NAME WorkOrder_ItemName\r\n"
				+ "                 ,t2.PRODUCT_INFO_STND_1"
				+ "					,DATE_FORMAT(t1.WorkOrder_CompleteOrderTime,'%Y-%m-%d') WorkOrder_CompleteOrderTimef"
				+ "		FROM WorkOrder_tbl t1\r\n"
				+ "		LEFT JOIN PRODUCT_INFO_TBL t2\r\n"
				+ "		ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE\r\n"
				+ "		where t1.WorkOrder_EquipCode = ? and t1.WorkOrder_OrderTime between '"+request.getParameter("startDate")+" 00:00:00' and '"+request.getParameter("endDate")+" 23:59:59' and t1.WorkOrder_WorkStatus <> 245 ORDER BY t1.WorkOrder_OrderTime DESC";
		
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
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
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
	
	@RequestMapping(value = "/MI_Search3", method = RequestMethod.GET)
	public List<WorkOrder_tbl> MI_Search3(HttpServletRequest request) throws org.json.simple.parser.ParseException, SQLException
	{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		String startDate = (String) obj.get("startDate");
		String endDate = (String) obj.get("endDate");
		String PRODUCT_ITEM_CODE = (String) obj.get("PRODUCT_ITEM_CODE");
		String WorkOrder_EquipCode = request.getParameter("WorkOrder_EquipCode");
		String where = "";
		List<WorkOrder_tbl> list = new ArrayList<WorkOrder_tbl>();
		
		String sql = "SELECT t1.*, t2.CHILD_TBL_Type WorkOrder_WorkStatusName, t4.EQUIPMENT_INFO_NAME WorkOrder_EquipName, t3.PRODUCT_ITEM_NAME WorkOrder_ItemName, t3.PRODUCT_INFO_STND_1 PRODUCT_INFO_STND_1 FROM WorkOrder_tbl t1\r\n"
				+ "INNER JOIN DTL_TBL t2 ON t1.WorkOrder_WorkStatus = t2.CHILD_TBL_NO\r\n"
				+ "INNER JOIN PRODUCT_INFO_TBL t3 ON t1.WorkOrder_ItemCode=t3.PRODUCT_ITEM_CODE\r\n"
				+ "INNER JOIN EQUIPMENT_INFO_TBL t4 ON t1.WorkOrder_EquipCode=t4.EQUIPMENT_INFO_CODE";
		
		if(startDate == null) {
			where = " WHERE (date(t1.WorkOrder_OrderTime) = DATE(NOW()) or date(t1.WorkOrder_ReceiptTime) = DATE(NOW()) or date(t1.WorkOrder_StartTime) = DATE(NOW()) or date(t1.WorkOrder_CompleteTime) = DATE(NOW()) or WorkOrder_CompleteTime = NULL)";
		} else {
			where = " WHERE t1.WorkOrder_OrderTime between '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59' and WorkOrder_WorkStatus!=294 and WorkOrder_WorkStatus!=295";
		}
		
		where += " and t1.WorkOrder_EquipCode = ?";
		
		where += " ORDER BY t1.WorkOrder_OrderTime DESC";
		
		sql += where;
		
		System.out.println(sql);
		
		return jdbctemplate.query(sql, new RowMapper<WorkOrder_tbl>() {
			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
				data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
				data.setWorkOrder_EquipName(rs.getString("WorkOrder_EquipName"));
				data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
				data.setWorkOrder_RegisterTime(rs.getString("WorkOrder_RegisterTime").substring(0,10));
				data.setWorkOrder_ReceiptTime(rs.getString("WorkOrder_ReceiptTime"));
				data.setWorkOrder_OrderTime(rs.getString("WorkOrder_OrderTime"));
				data.setWorkOrder_CompleteOrderTime(rs.getString("WorkOrder_CompleteOrderTime").substring(0,10));
				data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
				data.setWorkOrder_CompleteTime(rs.getString("WorkOrder_CompleteTime"));
				data.setWorkOrder_WorkStatus(rs.getString("WorkOrder_WorkStatus"));
				data.setWorkOrder_Worker(rs.getString("WorkOrder_Worker"));
				data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
				return data;
			}
		},WorkOrder_EquipCode);
	}
}
