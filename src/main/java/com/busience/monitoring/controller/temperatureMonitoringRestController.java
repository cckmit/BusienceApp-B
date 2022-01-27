package com.busience.monitoring.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.service.ProductionService;
import com.busience.monitoring.dto.Equip_Temperature_History;
import com.busience.standard.dto.DTL_TBL;
import com.busience.standard.dto.Equip_Monitoring_TBL;

@RestController
@RequestMapping
public class temperatureMonitoringRestController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@Autowired
	ProductionService productionService;
	
	Boolean templeLog = true;
	Boolean currentLog = true;
	String init_temp_no = "",current_temp_no = "20211124-M001-05";
	
	@GetMapping("bsapp_temp")
	public void temperature_Insert(HttpServletRequest request) throws SQLException, InterruptedException{
		String equip = request.getParameter("equip");
		String value = request.getParameter("value");
				
		if(Equip_Status_Check(equip))
		{
			String sql = "UPDATE Equip_Monitoring_TBL\r\n"
					+ "SET Temp = ?,\r\n"
					+ "Equip_Time = NOW()\r\n"
					+ "WHERE Equip_Code = ?";
			jdbctemplate.update(sql,value,equip);
			
			sql = "SELECT\r\n"
					+ "			Equip_No\r\n"
					+ "FROM		Equip_Monitoring_TBL			\r\n"
					+ "WHERE		Equip_Code = '"+equip+"'";
			
			init_temp_no = jdbctemplate.queryForObject(sql, new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("Equip_No");
				}
			});
			
			if(!init_temp_no.equals(current_temp_no))
			{
				current_temp_no = init_temp_no;
				currentLog = true;
			}
			
			if(currentLog)
			{
				currentLog = false;
				
				SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				Date time = new Date();
				String time1 = format1.format(time);
				
				try
				{
					sql = "INSERT INTO Equip_Temperature_History\r\n"
							+ "VALUES(\r\n"
							+ "	(SELECT Temp FROM Equip_Monitoring_TBL WHERE Equip_Code=?),\r\n"
							+ "	?,\r\n"
							+ "	?,\r\n"
							+ " ?"
							+ ")";
					
					jdbctemplate.update(sql,equip,time1,equip,current_temp_no);
					
					sql = "SELECT\r\n"
							+ "			Equip_No\r\n"
							+ "FROM		Equip_Monitoring_TBL			\r\n"
							+ "WHERE		Equip_Code = '"+equip+"'";
					
					current_temp_no = jdbctemplate.queryForObject(sql, new RowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("Equip_No");
						}
					});
				}
				catch(Exception ex)
				{
					System.out.println("중복키 발생");
				}
			}
			else
			{
				temperature_Insert_Log(equip);
			}
			
			
		}
		else
		{
			
		}
		
	}
	
	public void temperature_Insert_Log(String equip) throws InterruptedException {
		if(templeLog)
		{
			templeLog = false;
			
			int time_interval = 0; 
			
			try
			{
				time_interval = Integer.parseInt(jdbctemplate.queryForObject("SELECT CHILD_TBL_RMARK FROM DTL_TBL WHERE CHILD_TBL_NO = '301'",new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("CHILD_TBL_RMARK");
					}
				})); 
				
				Thread.sleep(time_interval * 60000);
			}
			catch(Exception ex)
			{
				System.out.println("에러: 기준정보 데이터 잘못 입력");
				return;
			}
			
			String CHILD_TBL_RMARK = jdbctemplate.queryForObject("SELECT\r\n"
					+ "			CHILD_TBL_RMARK\r\n"
					+ "FROM		DTL_TBL\r\n"
					+ "WHERE		CHILD_TBL_NO='304'", new RowMapper<String>() {

						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("CHILD_TBL_RMARK");
						}
					});
			
			if(CHILD_TBL_RMARK.equals("시"))
				CHILD_TBL_RMARK = "HOUR";
			else if(CHILD_TBL_RMARK.equals("분"))
				CHILD_TBL_RMARK = "MINUTE";
			
			try
			{
				String sql = "INSERT INTO Equip_Temperature_History\r\n"
						+ "VALUES(\r\n"
						+ "	(SELECT Temp FROM Equip_Monitoring_TBL WHERE Equip_Code=?),\r\n"
						+ "	DATE_ADD((SELECT\r\n"
						+ "			IFNULL(t1.Temp_Time,NOW())\r\n"
						+ "FROM		Equip_Temperature_History t1\r\n"
						+ "WHERE		t1.Temp_EquipCode = ?\r\n"
						+ "AND 			t1.Temp_No = '"+current_temp_no+"'\r\n"
						+ "ORDER BY t1.Temp_Time DESC\r\n"
						+ "LIMIT		1\r\n"
						+ ")\r\n"
						+ ",INTERVAL "+time_interval+" "+CHILD_TBL_RMARK+"),\r\n"
						+ "	?,\r\n"
						+ " ?"
						+ ")";
				
				jdbctemplate.update(sql,equip,equip,equip,current_temp_no);
				
				sql = "SELECT\r\n"
						+ "			Equip_No\r\n"
						+ "FROM		Equip_Monitoring_TBL			\r\n"
						+ "WHERE		Equip_Code = '"+equip+"'";
				
				current_temp_no = jdbctemplate.queryForObject(sql, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("Equip_No");
					}
				});
			}
			catch(Exception ex)
			{
				System.out.println("중복키 발생");
			}
			
			templeLog = true;
		}
	}
	
	//INFO : 송도향은 온도계가 1개이기 때문에 equip을 m001로 고정시킴
	@GetMapping("/temperature_Current")
	public String temperature_Current(HttpServletRequest request) {
		try
		{
			String equip = request.getParameter("equip");
			equip = "m001";
			
			String sql = "SELECT\r\n"
					+ "			*\r\n"
					+ "FROM		Equip_Monitoring_TBL			\r\n"
					+ "WHERE		Equip_Code = '"+equip+"'";
			
			Equip_Monitoring_TBL data = jdbctemplate.queryForObject(sql, new RowMapper<Equip_Monitoring_TBL>() {

				@Override
				public Equip_Monitoring_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
					Equip_Monitoring_TBL data = new Equip_Monitoring_TBL();
					data.setEquip_Code(rs.getString("Equip_Code"));
					data.setEquip_Time(rs.getString("Equip_Time"));
					data.setHumi(rs.getString("Humi"));
					data.setSpeed(rs.getString("Speed"));
					data.setTemp(rs.getString("Temp"));
					data.setEquip_Status(rs.getString("Equip_Status"));
					data.setEquip_No(rs.getString("Equip_No"));
					return data;
				}
			});
			
			if(data.getEquip_Status().equals("false"))
			{
				return "NONE";
			}
			else
			{
				return data.getTemp();
			}
		}
		catch(Exception ex)
		{
			return "NONE";
		}
	}
	
	public Boolean Equip_Status_Check(String equip)
	{
		String sql = "SELECT\r\n"
				+ "			IF(Equip_Status='true',TRUE,FALSE) Equip_Status\r\n"
				+ "FROM		Equip_Monitoring_TBL			\r\n"
				+ "WHERE		Equip_Code = '"+equip+"'";
		
		return jdbctemplate.queryForObject(sql, new RowMapper<Boolean>() {

			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getBoolean("Equip_Status");
			}
		});
	}
	
	//INFO : 송도향은 온도계가 1개이기 때문에 equip을 m001로 고정시킴
	@GetMapping("/temperature_Array")
	public List<Equip_Temperature_History> temperature_Array(HttpServletRequest request) {
		String equip = request.getParameter("equip");
		equip = "m001";
		
		List<Equip_Temperature_History> list = new ArrayList<Equip_Temperature_History>();
		
		if(Equip_Status_Check(equip))
		{
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
					+ "AND Temp_EquipCode=?\r\n"
					+ "AND Temp_No=?"
					+ "ORDER BY Temp_Time asc";
					//+ "AND		a.Temp_ONo = (SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_EquipCode='m001' AND WorkOrder_WorkStatus='244')\r\n";
					// 시간 간격
					//+ "GROUP BY floor(minute(a.Temp_Time)/1),minute(a.Temp_Time)";
			
			System.out.println(sql);
			
			list = jdbctemplate.query(sql,new RowMapper() {

						@Override
						public Equip_Temperature_History mapRow(ResultSet rs, int rowNum) throws SQLException {
							Equip_Temperature_History data = new Equip_Temperature_History();
							//data.setTemp_ONo(rs.getString("Temp_ONo"));
							//data.setTemp_Value(String.valueOf(Math.round(rs.getFloat("Temp_Value"))));
							data.setTemp_Value(rs.getString("Temp_Value"));
							data.setTemp_Time(rs.getString("Temp_Time"));
							return data;
						}
					},equip,current_temp_no);
		}
		
		return list;
	}
	
	@GetMapping("/temperature_Insert")
	public void temperature_Insert(){
		
		for(int j=0;j<1;j++)
		{
			for(int i=0;i<60;i++)
			{
				String sql = "INSERT INTO Equip_Temperature_History\r\n"
						+ "VALUES(\r\n"
						+ "	FLOOR(RAND() * 100),\r\n"
						+ "	'2021-12-06 "+((j<10)?"0"+j:j)+":"+((i<10)?"0"+i:i)+":00',\r\n"
						+ "	'm001',\r\n"
						+ "	'20211205-M001-01'\r\n"
						+ ")";
				
				jdbctemplate.update(sql);
			}
		}
		
		/*
		for(int a=21;a<28;a++)
		{
			for(int j=13;j<24;j++)
			{
				for(int i=0;i<60;i++)
				{
					String sql = "INSERT INTO Equip_Temperature_History\r\n"
							+ "VALUES(\r\n"
							+ "	FLOOR(RAND() * 100),\r\n"
							+ "	'2021-11-"+a+" "+((j<10)?"0"+j:j)+":"+((i<10)?"0"+i:i)+":00',\r\n"
							+ "	'm001',\r\n"
							+ "	'202111"+a+"-M001-02'\r\n"
							+ ")";
					
					jdbctemplate.update(sql);
				}
			}
		}
		*/
		
	}
	
	@GetMapping("/temperature_Equip_No_Select")
	public String temperature_Equip_No_Select()
	{
		return jdbctemplate.queryForObject("SELECT IF(Equip_No='','NONE',CONCAT((SELECT round(AVG(t2.Temp_Value),0) Value FROM Equip_Temperature_History t2 WHERE t2.Temp_No = t1.Equip_No GROUP BY t2.Temp_No),'/',Equip_No)) Equip_No FROM Equip_Monitoring_TBL t1 WHERE Equip_Code='M001'", new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("Equip_No");
			}
		});
	}
	
	@RequestMapping(value = "/History_Update_Insert_Check", method = RequestMethod.GET)
	public String History_Update_Insert_Check() {
		
		List<DTL_TBL> list = jdbctemplate.query("SELECT * FROM DTL_TBL WHERE CHILD_TBL_NO = '301' OR CHILD_TBL_NO = '304' ORDER BY CHILD_TBL_NO+0 ASC", new RowMapper() {

			@Override
			public DTL_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				DTL_TBL data = new DTL_TBL();
				data.setCHILD_TBL_RMARK(rs.getString("CHILD_TBL_RMARK"));
				return data;
			}
		});
		
		String sql = "SELECT \r\n"
				+ "			IF((DATE_FORMAT(TIMEDIFF(NOW(),Equip_Time), '%H') * 60+DATE_FORMAT(TIMEDIFF(NOW(),Equip_Time), '%i')\r\n"
				+ "			- "+list.get(0).getCHILD_TBL_RMARK()+")>="+list.get(0).getCHILD_TBL_RMARK()+",'true','false') result\r\n"
				+ "FROM Equip_Monitoring_TBL\r\n"
				+ "WHERE	Equip_Code = 'M001'";
		
		return jdbctemplate.queryForObject(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("result");
			}
		});
	}
	
	@GetMapping("/temperature_Status_Check")
	public String temperature_Status_Check()
	{
		String sql = "SELECT \r\n"
				+ "			t1.Equip_Code,	\r\n"
				+ "			t1.Equip_Time,	\r\n"
				+ "			t1.Humi,\r\n"
				+ "			t1.Speed,	\r\n"
				+ "			t1.Temp,\r\n"
				+ "			t1.Equip_Status,	\r\n"
				+ "			t1.Equip_No,\r\n"
				+ "			t2.EQUIPMENT_INFO_NAME\r\n"
				+ "FROM Equip_Monitoring_TBL t1\r\n"
				+ "INNER JOIN EQUIPMENT_INFO_TBL t2\r\n"
				+ "ON t1.Equip_Code = t2.EQUIPMENT_INFO_CODE\r\n"
				+ "Where t1.Equip_Code = 'm001'";
		
		return jdbctemplate.queryForObject(
				sql, new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("Equip_Status");
					}
		});
	}
	
	@GetMapping("/temperature_Compulsion_On")
	public void temperature_Compulsion_On() {
		currentLog = true;
	}
	
}
