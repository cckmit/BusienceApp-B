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
	
	Boolean templeLog = true;
	
	@GetMapping("/temperature_Insert")
	public void temperature_Insert(HttpServletRequest request) throws SQLException, InterruptedException{
		String equip = request.getParameter("equip");
		String value = request.getParameter("value");
		
		equip = "m001";
		
		String sql = "UPDATE Equip_Monitoring_TBL\r\n"
				+ "SET Temp = ?,\r\n"
				+ "Equip_Time = NOW()\r\n"
				+ "WHERE Equip_Code = ?";
		jdbctemplate.update(sql,value,equip);
		
		temperature_Insert_Log(equip);
	}
	
	public void temperature_Insert_Log(String equip) throws InterruptedException {
		if(templeLog)
		{
			templeLog = false;
			
			Thread.sleep(Integer.parseInt(jdbctemplate.queryForObject("SELECT CHILD_TBL_RMARK FROM DTL_TBL WHERE CHILD_TBL_NO = '301'",new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("CHILD_TBL_RMARK");
				}
			})) * 60000);
			
			String sql = "INSERT INTO Equip_Temperature_History\r\n"
					+ "VALUES(\r\n"
					+ "	(SELECT Temp FROM Equip_Monitoring_TBL WHERE Equip_Code=?),\r\n"
					+ "	NOW(),\r\n"
					+ "	?\r\n"
					+ ")";
			
			jdbctemplate.update(sql,equip,equip);
			
			templeLog = true;
		}
	}
	
	@GetMapping("/temperature_Current")
	public String temperature_Current(HttpServletRequest request) {
		try
		{
			/*
			return (String) jdbctemplate.queryForObject("SELECT \r\n"
					+ "			* \r\n"
					+ "FROM Equip_Temperature_History\r\n"
					//+ "WHERE Temp_ONo = (SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode='m001' AND WorkOrder_WorkStatus='244')\r\n"
					+ "ORDER BY Temp_Time DESC\r\n"
					+ "LIMIT 1",new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("Temp_Value");
						}
					});
			*/
			String equip = request.getParameter("equip");
			equip = "m001";
			
			return jdbctemplate.queryForObject("SELECT Temp FROM Equip_Monitoring_TBL WHERE Equip_Code=?", new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					// TODO Auto-generated method stub
					return rs.getString("Temp");
				}
			},equip);
			
		}
		catch(Exception ex)
		{
			System.out.println("아직 데이터가 없음");
			return "아직 데이터가 없음";
		}
	}
	
	@GetMapping("/temperature_Array")
	public List<Equip_Temperature_History> temperature_Array(HttpServletRequest request) {
		String equip = request.getParameter("equip");
		equip = "m001";
		
		String time = jdbctemplate.queryForObject("SELECT CHILD_TBL_RMARK FROM DTL_TBL WHERE CHILD_TBL_NO = '302'",new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CHILD_TBL_RMARK");
			}
		});
		
		String sql = "SELECT \r\n"
				+ "			*\r\n"
				+ "FROM		Equip_Temperature_History a\r\n"
				// 시간 범위
				+ "WHERE		a.Temp_Time BETWEEN DATE_SUB(NOW(), INTERVAL "+time+" MINUTE) AND NOW()\r\n"
				+ "AND Temp_EquipCode=?";
				//+ "AND		a.Temp_ONo = (SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode='m001' AND WorkOrder_WorkStatus='244')\r\n";
				// 시간 간격
				//+ "GROUP BY floor(minute(a.Temp_Time)/1),minute(a.Temp_Time)";
		
		System.out.println(sql);
		
		List<Equip_Temperature_History> list = jdbctemplate.query(sql,new RowMapper() {

					@Override
					public Equip_Temperature_History mapRow(ResultSet rs, int rowNum) throws SQLException {
						Equip_Temperature_History data = new Equip_Temperature_History();
						//data.setTemp_ONo(rs.getString("Temp_ONo"));
						//data.setTemp_Value(String.valueOf(Math.round(rs.getFloat("Temp_Value"))));
						data.setTemp_Value(rs.getString("Temp_Value"));
						data.setTemp_Time(rs.getString("Temp_Time"));
						return data;
					}
				},equip);
		
		return list;
	}
	
}
