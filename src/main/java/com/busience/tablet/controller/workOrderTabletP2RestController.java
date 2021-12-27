package com.busience.tablet.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.tablet.dto.PalDang_Product_tbl;

@RestController("workOrderTabletP2RestController")
@RequestMapping("/tablet/workOrderTabletPRest2")
public class workOrderTabletP2RestController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/pd_name_export", method = RequestMethod.GET)
	public PalDang_Product_tbl pd_name_export(HttpServletRequest request, Model model){

		return jdbctemplate.queryForObject("SELECT \r\n"
				+ "		pdpt.Material_LotNo\r\n"
				+ "		,wot.WorkOrder_ONo\r\n"
				+ "		,wot.WorkOrder_RQty\r\n"
				+ "		,wot.WorkOrder_ItemCode \r\n"
				+ "		,pit.PRODUCT_ITEM_NAME\r\n"
				+ "		,pit.PRODUCT_ITEM_CODE\r\n"
				+ "		,pit.PRODUCT_INFO_STND_1\r\n"
				+ "FROM	WorkOrder_tbl wot\r\n"
				+ "inner join PalDang_Product_tbl pdpt \r\n"
				+ "on wot.WorkOrder_ONo = pdpt.Product_LotNo\r\n"
				+ "inner join PRODUCT_INFO_TBL pit\r\n"
				+ "on wot.WorkOrder_ItemCode = pit.PRODUCT_ITEM_CODE\r\n"
				+ "where wot.WorkOrder_EquipCode = ?\r\n"
				+ "and	  wot.WorkOrder_WorkStatus = '245' limit 1" , new RowMapper<PalDang_Product_tbl>() {
					@Override
					public PalDang_Product_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
						PalDang_Product_tbl newData = new PalDang_Product_tbl();
						
						newData.setMaterial_LotNo(rs.getString("Material_LotNo"));
						newData.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
						newData.setWorkOrder_RQty(rs.getString("WorkOrder_RQty"));
						newData.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
						newData.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
						
						return newData;
					}
		},request.getParameter("eqcode"));
	}
	
}
