package com.busience.control.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.Equip_Monitoring_TBL;

@RestController
@RequestMapping("GraphicRest")
public class GraphicRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@GetMapping("/hogi_select")
	public List<Equip_Monitoring_TBL> hogi_select(HttpServletRequest request) throws SQLException {
		String sql = "SELECT * FROM Equip_Monitoring_TBL WHERE Equip_Code=?";
		
		
		return jdbctemplate.query(sql, new RowMapper<Equip_Monitoring_TBL>(){
			@Override
			public Equip_Monitoring_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				Equip_Monitoring_TBL data = new Equip_Monitoring_TBL();
				data.setEquip_Code(rs.getString(1));
				data.setEquip_Time(rs.getString(2));
				data.setHumi(rs.getString(3));
				data.setSpeed(rs.getString(4));
				data.setTemp(rs.getString(5));
				return data;
			}
		},request.getParameter("num"));
	}
	
	@GetMapping("/hogi_time_select")
	public List<Integer> hogi_time_select(HttpServletRequest request) {
		String sql = "SELECT TIMESTAMPDIFF(SECOND,Equip_Time,NOW()) time FROM Equip_Monitoring_TBL WHERE Equip_Code=?";
		
		return jdbctemplate.query(sql, new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		},request.getParameter("num"));
	}
	
}
