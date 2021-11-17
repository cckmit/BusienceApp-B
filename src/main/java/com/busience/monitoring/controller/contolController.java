package com.busience.monitoring.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.standard.dto.DEFECT_INFO_TBL;

@Controller
public class contolController {

	@Autowired
	JdbcTemplate jdbctemplate;

	// proMonitoring
	@GetMapping("proMonitoring")
	public String proMonitoring(Model model) {
		model.addAttribute("pageName", "생산현황 모니터링");

		model.addAttribute("CHILD_TBL_TYPE", jdbctemplate
				.queryForObject("select CHILD_TBL_TYPE from DTL_TBL where CHILD_TBL_NO='247'", new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num = rs.getInt("CHILD_TBL_TYPE");

						if (num > 1 && num % 2 != 0)
							--num;

						if (num > 8)
							num = 8;

						return String.valueOf(num);
					}
				}));

		return "normal/monitoring/proMonitoring";
	}

	// defectMonitoring
	@GetMapping("defectMonitoring")
	public String defectMonitoring(Model model) {
		model.addAttribute("pageName", "불량현황 모니터링");

		model.addAttribute("CHILD_TBL_TYPE", jdbctemplate
				.queryForObject("select CHILD_TBL_TYPE from DTL_TBL where CHILD_TBL_NO='247'", new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num = rs.getInt("CHILD_TBL_TYPE");

						if (num > 1 && num % 2 != 0)
							--num;

						if (num > 8)
							num = 8;

						return String.valueOf(num);
					}
				}));

		model.addAttribute("defect_list",
				jdbctemplate.query("select *,LOWER(DEFECT_CODE) DEFECT_CODE2 from DEFECT_INFO_TBL", new RowMapper() {

					@Override
					public DEFECT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
						DEFECT_INFO_TBL data = new DEFECT_INFO_TBL();
						data.setDEFECT_CODE(rs.getString("DEFECT_CODE2"));
						data.setDEFECT_NAME(rs.getString("DEFECT_NAME"));
						data.setDEFECT_ABR(rs.getString("DEFECT_ABR"));
						data.setDEFECT_MODIFIER(rs.getString("DEFECT_MODIFIER"));
						data.setDEFECT_MODIFY_D(rs.getString("DEFECT_MODIFY_D"));
						data.setDEFECT_RMRKS(rs.getString("DEFECT_RMRKS"));
						data.setDEFECT_USE_STATUS(rs.getString("DEFECT_USE_STATUS"));
						// System.out.println(data.toString());
						return data;
					}
				}));

		return "normal/monitoring/defectMonitoring";
	}

	// workMonitoring
	@GetMapping("workMonitoring")
	public String workMonitoring(Model model) {

		model.addAttribute("CHILD_TBL_TYPE", jdbctemplate
				.queryForObject("select CHILD_TBL_TYPE from DTL_TBL where CHILD_TBL_NO='247'", new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						int num = rs.getInt("CHILD_TBL_TYPE");

						if (num > 1 && num % 2 != 0)
							--num;

						if (num > 8)
							num = 8;

						return String.valueOf(num);
					}
				}));

		return "normal/monitoring/workMonitoring";
	}

	// TemperatureMonitoring
	@GetMapping("TemperatureMonitoring")
	public String TemperatureMonitoring(Model model) {

		model.addAttribute("Min_Value", jdbctemplate.queryForObject(
				"SELECT CHILD_TBL_RMARK FROM DTL_TBL WHERE CHILD_TBL_NO='294'", new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("CHILD_TBL_RMARK");
					}
				}));

		model.addAttribute("Max_Value", jdbctemplate.queryForObject(
				"SELECT CHILD_TBL_RMARK FROM DTL_TBL WHERE CHILD_TBL_NO='295'", new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("CHILD_TBL_RMARK");
					}
				}));

		return "normal/monitoring/TemperatureMonitoring";
	}

	// equipMonitoring
	@GetMapping("equipMonitoring")
	public String equipMonitoring() {
		return "normal/monitoring/equipMonitoring";
	}

	@GetMapping("workorderoi")
	public String orderMaster() {
		return "normal/monitoring/workorderoi";
	}

}
