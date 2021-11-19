package com.busience.productionLX.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.monitoring.dto.Equip_Temperature_History;
import com.busience.productionLX.dto.PRODUCTION_INFO_TBL;

@RestController("tempDailyRestController")
@RequestMapping("tempDailyRest")
public class tempDailyRestController {
	
	@Autowired
	JdbcTemplate jdbctemplate;

	@RequestMapping(value = "/History_Daily_List", method = RequestMethod.GET)
	public List<Equip_Temperature_History> History_Daily_List(HttpServletRequest request)
	{
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		String sql = "SELECT\r\n"
				+ "			round(AVG(Temp_Value),1) Value,\r\n"
				+ "			DATE_FORMAT(Temp_Time, '%Y-%m-%d') Time\r\n"
				+ "FROM		Equip_Temperature_History\r\n"
				+ "WHERE		Temp_Time BETWEEN ? AND ?\r\n"
				+ "GROUP BY DATE_FORMAT(Temp_Time, '%Y-%m-%d')";
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public Equip_Temperature_History mapRow(ResultSet rs, int rowNum) throws SQLException {
				Equip_Temperature_History data = new Equip_Temperature_History();
				data.setTemp_Time(rs.getString("Time"));
				data.setTemp_Value(rs.getString("Value"));
				return data;
			}
		},startDate,endDate);
	}
	
}
