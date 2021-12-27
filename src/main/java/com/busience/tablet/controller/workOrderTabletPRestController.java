package com.busience.tablet.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("workOrderTabletPRestController")
@RequestMapping("/tablet/workOrderTabletPRest")
public class workOrderTabletPRestController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/pd_name_export", method = RequestMethod.GET)
	public String pd_name_export(HttpServletRequest request, Model model){
		System.out.println(request.getParameter("pcode"));
		
		return jdbctemplate.queryForObject("SELECT EQUIPMENT_INFO_NAME FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE=?", new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("EQUIPMENT_INFO_NAME");
			}
		},request.getParameter("pcode"));
	}
	
}
