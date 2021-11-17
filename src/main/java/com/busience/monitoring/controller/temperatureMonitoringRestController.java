package com.busience.monitoring.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.monitoring.dto.Equip_Temperature_History;
import com.busience.productionLX.dto.WorkOrder_tbl;

@RestController
@RequestMapping("temperatureMonitoringRestController")
public class temperatureMonitoringRestController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@GetMapping("/temperature_Insert")
	public void temperature_Insert(HttpServletRequest request) throws SQLException{
		String equip = request.getParameter("equip");
		String value = request.getParameter("value");
		
		String sql = "INSERT INTO Equip_Temperature_History\r\n"
				+ "VALUES(\r\n"
				+ "	(SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode=? AND WorkOrder_WorkStatus='244'),\r\n"
				+ "	?,\r\n"
				+ "	NOW()\r\n"
				+ ");";
		
		jdbctemplate.update(sql,request.getParameter("equip"),request.getParameter("value"));
	}
	
	@GetMapping("/temperature_Current")
	public String temperature_Current(HttpServletRequest request) {
		return (String) jdbctemplate.queryForObject("SELECT \r\n"
				+ "			* \r\n"
				+ "FROM Equip_Temperature_History\r\n"
				+ "WHERE Temp_ONo = (SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode='m001' AND WorkOrder_WorkStatus='244')\r\n"
				+ "ORDER BY Temp_Time DESC\r\n"
				+ "LIMIT 1",new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("Temp_Value");
					}
				});
	}
	
	@GetMapping("/temperature_Array")
	public List<Equip_Temperature_History> temperature_Array(HttpServletRequest request) {
		String sql = "SELECT \r\n"
				+ "			*\r\n"
				+ "FROM		Equip_Temperature_History a\r\n"
				// 시간 범위
				+ "WHERE		a.Temp_Time BETWEEN DATE_SUB(NOW(), INTERVAL 10 MINUTE) AND NOW()\r\n"
				+ "AND		a.Temp_ONo = (SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode='m001' AND WorkOrder_WorkStatus='244')\r\n";
				// 시간 간격
				//+ "GROUP BY floor(minute(a.Temp_Time)/1),minute(a.Temp_Time)";
		
		List<Equip_Temperature_History> list = jdbctemplate.query(sql,new RowMapper() {

					@Override
					public Equip_Temperature_History mapRow(ResultSet rs, int rowNum) throws SQLException {
						Equip_Temperature_History data = new Equip_Temperature_History();
						data.setTemp_ONo(rs.getString("Temp_ONo"));
						data.setTemp_Value(String.valueOf(Math.round(rs.getFloat("Temp_Value"))));
						data.setTemp_Time(rs.getString("Temp_Time"));
						return data;
					}
				});
		
		return list;
	}
	
}
