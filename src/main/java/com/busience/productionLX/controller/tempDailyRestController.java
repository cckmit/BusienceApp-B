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
import com.busience.standard.dto.DTL_TBL;

@RestController("tempDailyRestController")
@RequestMapping("tempDailyRest")
public class tempDailyRestController {
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	String StartTime = "",EndTime="";

	@RequestMapping(value = "/History_Daily_List", method = RequestMethod.GET)
	public List<Equip_Temperature_History> History_Daily_List(HttpServletRequest request)
	{
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		/*
		String sql = "SELECT \r\n"
				+ "			t1.Temp_No,\r\n"
				+ "			round(AVG(t1.Temp_Value),0) Value,\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1) StartTime,\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1) EndTime\r\n"
				+ "FROM		Equip_Temperature_History t1\r\n"
				+ "WHERE		t1.Temp_Time BETWEEN ? AND ?\r\n"
				+ "GROUP BY t1.Temp_No";
		*/
		
		String sql = "SELECT \r\n"
				+ "			t1.Temp_No,\r\n"
				+ "			round(AVG(t1.Temp_Value),0) Value,\r\n"
				+ "			DATE_FORMAT((SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1),'%Y-%m-%d %H:%i') StartTime,\r\n"
				+ "			IF(Temp_No = (SELECT Equip_No FROM Equip_Monitoring_TBL WHERE Equip_Code='M001'),'',DATE_FORMAT((SELECT Temp_Time FROM Equip_Temperature_History t3 WHERE t3.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1),'%Y-%m-%d %H:%i')) EndTime\r\n"
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
				float fvalue = rs.getFloat("Value");
				int ivalue = (int)fvalue;
				data.setTemp_Value(String.valueOf(ivalue));
				
				return data;
			}
		},startDate,endDate);
	}
	
	@RequestMapping(value = "/History_DetailView", method = RequestMethod.GET)
	public List<Equip_Temperature_History> History_DetailView(HttpServletRequest request) {
		
		Integer num = jdbctemplate.queryForObject("SELECT CHILD_TBL_RMARK FROM DTL_TBL WHERE CHILD_TBL_NO='303'", new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("CHILD_TBL_RMARK");
			}
		});
		
		String sql = "SELECT\r\n"
				+ "			\r\n"
				+ "			TIMEDIFF((SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1),\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1)\r\n"
				+ "			) 'Time'/*,\r\n"
				+ "			DATE_FORMAT(\r\n"
				+ "			TIMEDIFF((SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1),\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1)\r\n"
				+ "			)\r\n"
				+ "			,'%h'\r\n"
				+ "			)*60+\r\n"
				+ "			DATE_FORMAT(\r\n"
				+ "			TIMEDIFF((SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1),\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1)\r\n"
				+ "			)\r\n"
				+ "			,'%i'\r\n"
				+ "			) '시간을 분으로 변경',\r\n"
				+ "			\r\n"
				+ "			FLOOR(\r\n"
				+ "				(DATE_FORMAT(\r\n"
				+ "					TIMEDIFF((SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1),\r\n"
				+ "					(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1)\r\n"
				+ "					)\r\n"
				+ "					,'%h'\r\n"
				+ "				)*60+\r\n"
				+ "				DATE_FORMAT(\r\n"
				+ "					TIMEDIFF((SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1),\r\n"
				+ "					(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1)\r\n"
				+ "					)\r\n"
				+ "					,'%i'\r\n"
				+ "				))/"+num+"\r\n"
				+ "			) 'devideTime'*/\r\n"
				+ "FROM		Equip_Temperature_History t1\r\n"
				+ "WHERE		t1.Temp_No = '"+request.getParameter("Temp_No")+"'\r\n"
				+ "GROUP BY t1.Temp_No";
		
		String sdevideTime = "00:00:00";
		
		try
		{
			sdevideTime = jdbctemplate.queryForObject(sql, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("Time");
				}
			});
		}
		catch(Exception ex)
		{
			System.out.println("아직 데이터가 안들어와서 에러처리됨");
		}
		
		String[] timeSet = sdevideTime.split(":");
		
		Float devideTime = ( (Float.valueOf(timeSet[0])>0)?Integer.valueOf(timeSet[0])*60:0 ) + Float.valueOf(timeSet[1]);
		
		jdbctemplate.query("SELECT\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time DESC LIMIT 1) EndTime,\r\n"
				+ "			(SELECT Temp_Time FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Temp_No ORDER BY Temp_Time ASC LIMIT 1) StartTime\r\n"
				+ "FROM		Equip_Temperature_History t1\r\n"
				+ "WHERE		t1.Temp_No = ?\r\n"
				+ "GROUP BY t1.Temp_No", new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				StartTime = rs.getString("StartTime");
				EndTime = rs.getString("EndTime");
				
				return null;
			}
		},request.getParameter("Temp_No"));
		
		//System.out.println(request.getParameter("tempDaily"));
		
		if(devideTime >= num || request.getParameter("tempDaily") != null)
		{
			devideTime /= num;
			
			sql = "";
			int initde = (int) Math.ceil(devideTime);
			
			for(int i=1;i<=num;i++)
			{
				sql += "SELECT\r\n"
						+ "			TRUNCATE(AVG(Temp_Value),0) Temp_Value,\r\n"
						+ "			Temp_No,\r\n"
						+ (i==1?"DATE_FORMAT('"+StartTime+"','%y%m%d %H:%i') StartTime,\r\n":"			DATE_FORMAT(DATE_ADD('"+StartTime+"', INTERVAL "+(devideTime-initde)+" MINUTE),'%y%m%d %H:%i') StartTime,\r\n")
						+ "			DATE_ADD('"+StartTime+"', INTERVAL "+devideTime+" MINUTE) EndTime\r\n"
						+ "FROM		Equip_Temperature_History t1\r\n"
						+ "WHERE		t1.Temp_No = '"+request.getParameter("Temp_No")+"' AND\r\n"
						+ "Temp_Time BETWEEN "
						// + "DATE_ADD('"+StartTime+"', INTERVAL "+devideTime+" MINUTE) "
						+ (i==1?"'"+StartTime+"'":"DATE_ADD('"+StartTime+"', INTERVAL "+(devideTime-initde)+" MINUTE)\r\n")
						+ " AND DATE_ADD('"+StartTime+"', INTERVAL "+devideTime+" MINUTE)\r\n"
						+ "GROUP BY t1.Temp_No	\r\n"
						+ ((i == num) ? "" : "UNION\r\n");
				
				devideTime += initde;
			}
			
			return jdbctemplate.query(sql, new RowMapper() {

				@Override
				public Equip_Temperature_History mapRow(ResultSet rs, int rowNum) throws SQLException {
					Equip_Temperature_History data = new Equip_Temperature_History();
					data.setTemp_No(rs.getString("Temp_No"));
					data.setStartTime(rs.getString("StartTime"));
					data.setEndTime(rs.getString("EndTime"));
					float fvalue = rs.getFloat("Temp_Value");
					int ivalue = (int) fvalue;
					data.setTemp_Value(String.valueOf(ivalue));
					
					return data;
				}
			});
		}
		else
		{
			sql = "SELECT \r\n"
					+ "			*,\r\n"
					+ "         DATE_FORMAT(Temp_Time,'%y%m%d %H:%i') SStartTime\r\n"
					+ "FROM		Equip_Temperature_History a\r\n"
					// 시간 범위
					+ "WHERE		a.Temp_Time BETWEEN DATE_SUB(NOW(), INTERVAL "+num+" MINUTE) AND NOW()\r\n"
					+ "AND Temp_No=?\r\n"
					+ "ORDER BY Temp_Time asc";
					//+ "AND		a.Temp_ONo = (SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode='m001' AND WorkOrder_WorkStatus='244')\r\n";
					// 시간 간격
					//+ "GROUP BY floor(minute(a.Temp_Time)/1),minute(a.Temp_Time)";
			
			System.out.println(sql);
			
			return jdbctemplate.query(sql,new RowMapper() {

						@Override
						public Equip_Temperature_History mapRow(ResultSet rs, int rowNum) throws SQLException {
							Equip_Temperature_History data = new Equip_Temperature_History();
							//data.setTemp_ONo(rs.getString("Temp_ONo"));
							//data.setTemp_Value(String.valueOf(Math.round(rs.getFloat("Temp_Value"))));
							data.setTemp_Value(rs.getString("Temp_Value"));
							data.setStartTime(rs.getString("SStartTime"));
							
							return data;
						}
					},request.getParameter("Temp_No"));
		}
		
	}
	
	@RequestMapping(value = "/History_Temp_BottomTop", method = RequestMethod.GET)
	public String History_Temp_BottomTop(){
		return jdbctemplate.queryForObject("SELECT CHILD_TBL_RMARK FROM DTL_TBL WHERE CHILD_TBL_NO ='306'", new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CHILD_TBL_RMARK");
			}
		});
	}
}
