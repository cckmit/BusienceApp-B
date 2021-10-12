package com.busience.productionLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.standard.Dto.DTL_TBL;
import com.busience.standard.Dto.EQUIPMENT_INFO_TBL;

@Controller
public class workOrderTABController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@GetMapping("WorkOrderTAB")
	public String WorkOrderTAB2(Model model, HttpServletRequest request) throws SQLException {
		model.addAttribute("list",jdbctemplate.query("SELECT * FROM EQUIPMENT_INFO_TBL", new RowMapper<EQUIPMENT_INFO_TBL>() {
			@Override
			public EQUIPMENT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				EQUIPMENT_INFO_TBL data = new EQUIPMENT_INFO_TBL();
				data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
				data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
				return data;
			}
		}));
		
		model.addAttribute("list2",jdbctemplate.query("SELECT * FROM DTL_TBL WHERE NEW_TBL_CODE='29'", new RowMapper<DTL_TBL>() {
			@Override
			public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				DTL_TBL data = new DTL_TBL();
				data.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
				data.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
				return data;
			}
		}));
		
		return "normal/productionLX/work_order_TAB";
	}
}
