package com.busience.production.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.production.dto.PRODUCTION_INFO_TBL;
import com.busience.production.dto.ProductionMgmtDto;
import com.busience.production.service.ProductionMgmtService;
import com.busience.tablet.dto.CrateLotDto;

@RestController("proSumRestController")
@RequestMapping("proSumRest")
public class proSumRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ProductionMgmtService productionMgmtService;

	@Autowired
	JdbcTemplate jdbctemplate;
	
	// 생산 실적 관리(설비별)
	@GetMapping("/proMachineSumSelect2")
	public List<CrateLotDto> proMachineSumSelect(SearchDto searchDto) {
		return productionMgmtService.proMachineSumList(searchDto);
	}

	// 생산 실적 관리(제품별)
	@GetMapping("/proItemSumSelect2")
	public List<CrateLotDto> proItemSumSelect(SearchDto searchDto) {
		return productionMgmtService.proItemSumList(searchDto);
	}
	
	// 마스크 실적 현황
	@GetMapping("/proMaskSumSelect")
	public List<CrateLotDto> proMaskSumSelect(SearchDto searchDto) {
		return productionMgmtService.proMaskSumDao(searchDto);
	}
	
	// 생산 포장 현황
	@GetMapping("/proPackingSumSelect")
	public List<CrateLotDto> proPackingSumSelect(SearchDto searchDto) {
		return productionMgmtService.proPackingDao(searchDto);
	}

	// proItemSumSelect3
	@RequestMapping(value = "/proItemSumSelect3", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proItemSumSelect3(HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String PRODUCT_ITEM_CODE = request.getParameter("PRODUCT_ITEM_CODE");
		String PRODUCT_ITEM_NAME = request.getParameter("PRODUCT_ITEM_NAME");
		String where = "";

		String sql = "SELECT \r\n" + "			t1.*,\r\n"
				+ "			SUM(t1.PRODUCTION_SUM_VOLUME) PRODUCTION_VOLUME_S,\r\n"
				+ "			t2.PRODUCT_ITEM_NAME,\r\n"
				+ "			t3.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME\r\n"
				+ "FROM		WorkOrder_tbl_X t1\r\n"
				+ "LEFT JOIN 	PRODUCT_INFO_TBL t2 ON t1.Production_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "LEFT JOIN 	EQUIPMENT_INFO_TBL t3 ON t1.PRODUCTION_Equipment_Code = t3.EQUIPMENT_INFO_CODE";

		if (PRODUCT_ITEM_CODE != null) {
			where += " and Production_PRODUCT_CODE like '%" + PRODUCT_ITEM_CODE + "%'"
					+ " and PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'";
		}
		else {
			where += " and (PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_NAME + "%'"
					+ " OR Production_PRODUCT_CODE like '%" + PRODUCT_ITEM_NAME + "%')";
		}
		where += "\r\nWHERE t1.PRODUCTION_END_TIME BETWEEN ? AND ? AND PRODUCTION_CC='E'\r\n";
		sql += where;
		sql += "group BY 	t1.PRODUCTION_PRODUCT_CODE,t1.PRODUCTION_SERIAL_NUM with rollup";
		
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();
		jdbctemplate.query(sql, new RowMapper<PRODUCTION_INFO_TBL>() {

			@Override
			public PRODUCTION_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();
				
				if (rs.getString("PRODUCTION_SERIAL_NUM") != null) {
					if (rs.getString("PRODUCTION_PRODUCT_CODE") == null)
					{
						data.setPRODUCTION_WorkOrder_ONo("Grand Total");
						data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
						data.setPRODUCTION_PRODUCT_CODE("");
						data.setPRODUCT_ITEM_NAME("");
					}
					else
					{
						data.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_SERIAL_NUM"));

						data.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
						data.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));

						data.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
						data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
						data.setPRODUCTION_Date(rs.getString("PRODUCTION_END_TIME"));

						data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
					}
					
					list.add(data);
				}
				
				return data;
			}
			
		}, startDate, endDate);
		
		//list.get(list.size()-1).setPRODUCTION_WorkOrder_ONo("Grand Total");
		return list;
	}

	// proItemSumSelect3
	@RequestMapping(value = "/proMachineSumSelect3", method = RequestMethod.GET)
	public List<PRODUCTION_INFO_TBL> proMachineSumSelect3(HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String EQUIPMENT_INFO_CODE = request.getParameter("EQUIPMENT_INFO_CODE");
		String EQUIPMENT_INFO_NAME = request.getParameter("EQUIPMENT_INFO_NAME");
		String where = "";

		String sql = "SELECT \r\n" + "			t1.*,\r\n"
				+ "			SUM(t1.PRODUCTION_SUM_VOLUME) PRODUCTION_VOLUME_S,\r\n"
				+ "			t2.PRODUCT_ITEM_NAME,\r\n"
				+ "			t3.EQUIPMENT_INFO_NAME PRODUCTION_EQUIPMENT_INFO_NAME\r\n"
				+ "FROM		WorkOrder_tbl_X t1\r\n"
				+ "LEFT JOIN 	PRODUCT_INFO_TBL t2 ON t1.Production_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "LEFT JOIN 	EQUIPMENT_INFO_TBL t3 ON t1.PRODUCTION_Equipment_Code = t3.EQUIPMENT_INFO_CODE";

		if (EQUIPMENT_INFO_CODE != null) {
			where += " and t1.PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_CODE + "%'"
			+ " and t3.EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_NAME + "%'";
		}
		else {
			where += " and (t3.EQUIPMENT_INFO_NAME like '%" + EQUIPMENT_INFO_NAME + "%'"
			+ " OR t1.PRODUCTION_Equipment_Code like '%" + EQUIPMENT_INFO_NAME + "%')";
		}
		
		where += "\r\nWHERE t1.PRODUCTION_END_TIME BETWEEN ? AND ? AND PRODUCTION_CC='E'\r\n";
		sql += where;
		sql += "group BY 	t1.PRODUCTION_Equipment_Code,t1.PRODUCTION_SERIAL_NUM with rollup";
		
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();
		
		jdbctemplate.query(sql, new RowMapper<PRODUCTION_INFO_TBL>() {

			@Override
			public PRODUCTION_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();
				
				if (rs.getString("PRODUCTION_SERIAL_NUM") != null)
				{
					
				}
				
				if (rs.getString("PRODUCTION_EQUIPMENT_CODE") == null)
				{
					data.setPRODUCTION_WorkOrder_ONo("Grand Total");
					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
					data.setPRODUCTION_PRODUCT_CODE("");
					data.setPRODUCT_ITEM_NAME("");
				}
				else
				{
					data.setPRODUCTION_WorkOrder_ONo(rs.getString("PRODUCTION_SERIAL_NUM"));

					data.setPRODUCTION_EQUIPMENT_CODE(rs.getString("PRODUCTION_EQUIPMENT_CODE"));
					data.setPRODUCTION_EQUIPMENT_INFO_NAME(rs.getString("PRODUCTION_EQUIPMENT_INFO_NAME"));

					data.setPRODUCTION_PRODUCT_CODE(rs.getString("PRODUCTION_PRODUCT_CODE"));
					data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
					data.setPRODUCTION_Date(rs.getString("PRODUCTION_END_TIME"));

					data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME_S"));
				}
				list.add(data);
				
				return data;
			}
			
		}, startDate, endDate);
		return list;
	}
}
