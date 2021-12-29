package com.busience.tablet.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrder_tbl;
import com.busience.qc.dto.DefectDto;
import com.busience.tablet.dto.PalDang_Product_tbl;

@RestController("workOrderSeyeonRestController")
@RequestMapping("/tablet/workOrderSeyeonRest")
public class workOrderSeyeonRestController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/WorkOrder_ONo_export", method = RequestMethod.GET)
	public WorkOrder_tbl WorkOrder_ONo_export(HttpServletRequest request, Model model)
	{
		try {
			return jdbctemplate.queryForObject("SELECT \r\n"
					+ "			t1.*,\r\n"
					+ "			t2.PRODUCT_ITEM_NAME WorkOrder_ItemName,\r\n"
					+ "			t2.PRODUCT_INFO_STND_1\r\n"
					+ "FROM 		WorkOrder_tbl t1\r\n"
					+ "INNER JOIN PRODUCT_INFO_TBL t2\r\n"
					+ "ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE\r\n"
					+ "WHERE		t1.WorkOrder_WorkStatus = '244'\r\n"
					+ "AND		t1.WorkOrder_EquipCode = ?", new RowMapper<WorkOrder_tbl>() {
						@Override
						public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
							WorkOrder_tbl data = new WorkOrder_tbl();
							data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
							data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
							data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
							data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
							data.setWorkOrder_RQty(rs.getString("WorkOrder_RQty"));
							data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
							data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
							data.setWorkOrder_Remark(rs.getString("WorkOrder_Remark"));
							return data;
						}
					},request.getParameter("eqcode"));
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	@RequestMapping(value = "/WorkOrder_2rd_AutoInsert", method = RequestMethod.GET)
	public void WorkOrder_2rd_AutoInsert(HttpServletRequest request, Model model)
	{
		try
		{
			jdbctemplate.queryForObject("SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode='M002' AND WorkOrder_WorkStatus='244'", new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					System.out.println(rs.getString("WorkOrder_ONo"));
					
					return null;
				}
			});
		}
		catch(Exception ex)
		{
			String sql = "INSERT INTO WorkOrder_tbl\r\n"
					+ "(	WorkOrder_No, \r\n"
					+ "	WorkOrder_ONo, \r\n"
					+ "	WorkOrder_ItemCode, \r\n"
					+ "	WorkOrder_EquipCode, \r\n"
					+ "	WorkOrder_RegisterTime, \r\n"
					+ "	WorkOrder_ReceiptTime, \r\n"
					+ "	WorkOrder_OrderTime, \r\n"
					+ "	WorkOrder_StartTime, \r\n"
					+ "	WorkOrder_CompleteOrderTime, \r\n"
					+ "	WorkOrder_WorkStatus, \r\n"
					+ "	WorkOrder_Worker, \r\n"
					+ "	WorkOrder_Remark)\r\n"
					+ "VALUES((SELECT WorkOrder_No FROM WorkOrder_tbl a2 WHERE WorkOrder_EquipCode='M001' AND WorkOrder_WorkStatus='244'),\r\n"
					+ " (SELECT WorkOrder_ONo FROM WorkOrder_tbl a2 WHERE WorkOrder_EquipCode='M001' AND WorkOrder_WorkStatus='244'), \r\n"
					+ " (SELECT WorkOrder_ItemCode FROM WorkOrder_tbl a3 WHERE WorkOrder_EquipCode='M001' AND WorkOrder_WorkStatus='244'), \r\n"
					+ " 'M002', \r\n"
					+ " DATE_ADD(NOW(), INTERVAL -2 DAY),\r\n"
					+ " DATE_ADD(NOW(), INTERVAL -1 MINUTE),\r\n"
					+ " DATE_ADD(NOW(), INTERVAL -1 DAY),\r\n"
					+ " NOW(),\r\n"
					+ " DATE_ADD(NOW(), INTERVAL +1 DAY),\r\n"
					+ " '244',\r\n"
					+ " 'admin',\r\n"
					+ " 'AUTO'\r\n"
					+ " )";
			
			jdbctemplate.update(sql);
		}
	}
	
	@GetMapping("/workOrderCurrentQty")
	public Float workOrderCurrentQty(Model model, HttpServletRequest request)
	{
		String sql = "SELECT sum(PRODUCTION_Volume) PRODUCTION_Volume FROM PRODUCTION_MGMT_TBL2 a1 WHERE a1.PRODUCTION_WorkOrder_ONo='"+request.getParameter("WorkOrder_ONo")+"' AND a1.PRODUCTION_Equipment_Code='"+request.getParameter("WorkOrder_EquipCode")+"'";
		
		Float CurrentQty = (float) 0;
		
		try
		{
			CurrentQty = jdbctemplate.queryForObject(sql, new RowMapper<Float>() {

				@Override
				public Float mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getFloat("PRODUCTION_Volume");
				}
			});
		}
		catch(Exception ex)
		{
			return (float) 0;
		}
		
		
		return CurrentQty;
	}
	
	Boolean DefectInsert_flag = false;
	
	@GetMapping("/workOrderDefectInsert")
	public void workOrderDefectInsert(Model model, HttpServletRequest request)
	{
		String sql = "update WorkOrder_tbl set WorkOrder_RQty=?,WorkOrder_PQty=? where WorkOrder_ONo=? and WorkOrder_EquipCode='M002'";
		jdbctemplate.update(sql,request.getParameter("Defect_Qty"),request.getParameter("Defect_Qty"),request.getParameter("Defect_ONo"));
		
		DefectInsert_flag = true;
		
		jdbctemplate.query("SELECT DEFECT_CODE FROM DEFECT_INFO_TBL", new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String sql = "";
				
				if(DefectInsert_flag)
				{
					DefectInsert_flag = false;
					sql = "INSERT INTO defect_tbl\r\n"
							+ "(Defect_ONo, Defect_Code, Defect_Qty, Defect_Insert_Time)\r\n"
							+ "VALUES('"+request.getParameter("Defect_ONo")+"', '"+rs.getString("DEFECT_CODE")+"' , '"+request.getParameter("Defect_Qty")+"', CURRENT_TIMESTAMP)";
				}
				else
				{
					sql = "INSERT INTO defect_tbl\r\n"
							+ "(Defect_ONo, Defect_Code, Defect_Qty, Defect_Insert_Time)\r\n"
							+ "VALUES('"+request.getParameter("Defect_ONo")+"', '"+rs.getString("DEFECT_CODE")+"' , '0', CURRENT_TIMESTAMP)";
				}
				
				jdbctemplate.update(sql);
				
				return null;
			}
		});
		
		DefectInsert_flag = true;
	}
	
	@GetMapping("/workOrderUpdate")
	public void workOrderUpdate(Model model, HttpServletRequest request)
	{
		String sql = "SELECT * FROM PRODUCTION_MGMT_TBL2 WHERE PRODUCTION_Equipment_Code = 'M002' AND PRODUCTION_WorkOrder_ONo = ? ORDER BY PRODUCTION_Date DESC";
		
		List<ProductionMgmtDto> list1 = jdbctemplate.query(sql, new RowMapper<ProductionMgmtDto>() {
			@Override
			public ProductionMgmtDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductionMgmtDto data = new ProductionMgmtDto();
				data.setPRODUCTION_WorkOrder_No(rs.getInt("PRODUCTION_WorkOrder_No"));
				data.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_WorkOrder_ONo"));
				return data;
			}
		},request.getParameter("workOrder_ONo_M2"));
		
		if(list1.size() > 0)
		{
			String workOrder_ONo_Qtys = request.getParameter("workOrder_ONo_Qty");
			int workOrder_ONo_Qty = Integer.parseInt(workOrder_ONo_Qtys);
			
			for(int i=0;i<workOrder_ONo_Qty;i++)
			{
				sql = "update PRODUCTION_MGMT_TBL2 set PRODUCTION_WorkOrder_ONo='"+request.getParameter("workOrder_ONo_M1")+"' where PRODUCTION_WorkOrder_ONo='"+list1.get(i).getPRODUCTION_WorkOrder_ONo()+"' AND PRODUCTION_WorkOrder_No='"+list1.get(i).getPRODUCTION_WorkOrder_No()+"'";
				jdbctemplate.update(sql);
			}
		}
	}
	
}
