package com.busience.materialLX.controller;

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

@RestController("matOutputLXTabletRestController")
@RequestMapping("matOutputLXTabletRest")
public class matOutputLXTabletRestController {
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@RequestMapping(value = "/MOM_Save", method = RequestMethod.GET)
	public void MOM_Save(HttpServletRequest request, Model model) {
		String sql = "INSERT INTO OutMatLX_tbl\r\n"
				+ "VALUES(\r\n"
				+ "	(\r\n"
				+ "		SELECT\r\n"
				+ "					MAX(t2.OutMat_No)+1 max_value\r\n"
				+ "		FROM		OutMatLX_tbl t2\r\n"
				+ "	),\r\n"
				+ "	'"+request.getParameter("pdcode")+"',\r\n"
				+ "	'"+request.getParameter("qty")+"',\r\n"
				+ "	'"+request.getParameter("dtcode")+"',\r\n"
				+ "	'13',\r\n"
				+ "	'208',\r\n"
				+ "	NOW(),\r\n"
				+ "	NOW(),\r\n"
				+ "	'admin'\r\n"
				+ ")";
		
		jdbctemplate.update(sql);
		
		sql = " update StockMatLX_tbl set" + " SM_Out_Qty = SM_Out_Qty+'" + request.getParameter("qty")
		+ "'" + " where SM_Code = '" + request.getParameter("pdcode") + "'";
		
		jdbctemplate.update(sql);
	}
	
	@RequestMapping(value = "/Current_Save", method = RequestMethod.GET)
	public String Current_Save(HttpServletRequest request, Model model)
	{
		String sql = "select SM_Last_Qty+SM_In_Qty-SM_Out_Qty cqty from StockMatLX_tbl WHERE SM_Code = ?";
		
		return jdbctemplate.queryForObject(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("cqty");
			}
		},request.getParameter("code"));
	}
	
}
