package com.busience.productionLX.controller;

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

@RestController("tempStatusControlRestController")
@RequestMapping("tempStatusControlRest")
public class tempStatusControlRestController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/tempStatusOnChange", method = RequestMethod.GET)
	public void tempStatusOnChange(HttpServletRequest request)
	{
		String sql = "SELECT CONCAT( DATE_FORMAT(NOW(), '%Y%m%d') ,'-','"+request.getParameter("id")+"','-',(SELECT LPAD(COUNT(*)+1,2,0) FROM Equip_Temperature_History WHERE DATE_FORMAT(Temp_Time, '%y%m%d') = DATE_FORMAT(NOW(), '%y%m%d') AND Temp_EquipCode = '"+request.getParameter("id")+"')) NO";
		
		String no = jdbctemplate.queryForObject(sql,new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("NO");
			}
		});
		
		sql = "UPDATE Equip_Monitoring_TBL\r\n"
				+ "SET	 Equip_Time = NOW()\r\n"
				+ ",	 Equip_Status = '"+request.getParameter("checked")+"'\r\n"
				+ ",	 Equip_No = '"+no+"'\r\n"
				+ "WHERE	 Equip_Code = UPPER('"+request.getParameter("id")+"')";
		
		jdbctemplate.update(sql);
	}
	
	@RequestMapping(value = "/tempStatusOffChange", method = RequestMethod.GET)
	public void tempStatusOffChange(HttpServletRequest request)
	{
		String sql = "UPDATE Equip_Monitoring_TBL\r\n"
				+ "SET	 Equip_Time = NOW()\r\n"
				+ ",	 Equip_Status = '"+request.getParameter("checked")+"'\r\n"
				+ ",	 Equip_No = ''\r\n"
				+ "WHERE	 Equip_Code = UPPER('"+request.getParameter("id")+"')";
		
		jdbctemplate.update(sql);
	}
	
}
