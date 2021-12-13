package com.busience.productionLX.controller;

import java.security.Principal;
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

@RestController("MaterialInputRestController")
@RequestMapping("MaterialInputRest")
public class MaterialInputRestController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/Current_Search", method = RequestMethod.GET)
	public List<WorkOrder_tbl> Current_Search(HttpServletRequest request, Principal principal) throws SQLException {
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
	
	@RequestMapping(value = "/MaterialInput_Insert", method = RequestMethod.GET)
	public void MaterialInput_Upsert(HttpServletRequest request, Principal principal){
		String sql = "insert PalDang_Product_tbl(Product_LotNo,Material_LotNo) values('"+request.getParameter("Product_LotNo")+"','"+request.getParameter("Material_LotNo")+"')";
		
		jdbctemplate.update(sql);
	}
	
}
