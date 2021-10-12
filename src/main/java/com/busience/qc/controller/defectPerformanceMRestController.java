package com.busience.qc.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.busience.standard.Dto.DEFECT_INFO_TBL;

@RestController
@RequestMapping("defectPerformanceMRest")
public class defectPerformanceMRestController {
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/Defect_Export_Init",method = RequestMethod.GET)
	public List<DEFECT_INFO_TBL> Defect_Export(HttpServletRequest request)
	{
		return Defect_Export();
	}

	private List<DEFECT_INFO_TBL> Defect_Export() {
		return jdbctemplate.query("select * from DEFECT_INFO_TBL", new RowMapper<DEFECT_INFO_TBL>() {
			@Override
			public DEFECT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				DEFECT_INFO_TBL data = new DEFECT_INFO_TBL();
				data.setDEFECT_CODE(rs.getString("DEFECT_CODE"));
				data.setDEFECT_NAME(rs.getString("DEFECT_NAME"));
				data.setDEFECT_ABR(rs.getString("DEFECT_ABR"));
				data.setDEFECT_MODIFIER(rs.getString("DEFECT_MODIFIER"));
				data.setDEFECT_MODIFY_D(rs.getString("DEFECT_MODIFY_D"));
				data.setDEFECT_RMRKS(rs.getString("DEFECT_RMRKS"));
				data.setDEFECT_USE_STATUS(rs.getString("DEFECT_USE_STATUS"));
				return data;
			}
		});
	}
	
	@RequestMapping(value = "/DEFECT_INFO_TBL_Load",method = RequestMethod.GET)
	public List<DEFECT_INFO_TBL> DEFECT_INFO_TBL_Load(HttpServletRequest request) throws ParseException
	{
		String sql = "SELECT\r\n"
				+ "			*\r\n"
				+ "			,(SELECT Defect_DQty FROM defectPerformance WHERE Defect_ONo=? AND Defect_CODE=t1.DEFECT_CODE) DEFECT_QTY\r\n"
				+ "FROM		DEFECT_INFO_TBL t1\r\n"
				+ "order by DEFECT_QTY desc";
		
		return jdbctemplate.query(sql, new RowMapper<DEFECT_INFO_TBL>() {
			@Override
			public DEFECT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				DEFECT_INFO_TBL data = new DEFECT_INFO_TBL();
				
				data.setDEFECT_USE_STATUS(request.getParameter("oqcinspect_OqcInNo"));
				data.setDEFECT_CODE(rs.getString("DEFECT_CODE"));
				data.setDEFECT_NAME(rs.getString("DEFECT_NAME"));
				data.setDEFECT_ABR(rs.getString("DEFECT_QTY"));
				
				return data;
			}
		},request.getParameter("oqcinspect_OqcInNo"));
	}
	
}
