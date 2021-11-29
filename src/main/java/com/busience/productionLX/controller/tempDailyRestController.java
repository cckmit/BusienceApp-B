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
		
		String sql = "SELECT \r\n"
				+ "			t1.Temp_No,\r\n"
				+ "			round(AVG(t1.Temp_Value),1) Value,\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1) StartTime,\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1) EndTime\r\n"
				+ "FROM		Equip_Temperature_History t1\r\n"
				+ "WHERE		t1.Temp_Time BETWEEN ? AND ?\r\n"
				+ "GROUP BY t1.Temp_No";
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public Equip_Temperature_History mapRow(ResultSet rs, int rowNum) throws SQLException {
				Equip_Temperature_History data = new Equip_Temperature_History();
				data.setTemp_No(rs.getString("Temp_No"));
				data.setStartTime(rs.getString("StartTime"));
				data.setEndTime(rs.getString("EndTime"));
				data.setTemp_Value(rs.getString("Value"));
				return data;
			}
		},startDate,endDate);
	}
	
}
