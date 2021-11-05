package com.busience.monitoring.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.PRODUCTION_INFO_TBL;
import com.busience.standard.dto.DefectPerformance;

@RestController
@RequestMapping("defectMonitoringRest")
public class defectMonitoringRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@GetMapping("/defect_view")
	public List<DefectPerformance> defect_view(HttpServletRequest request){
		System.out.println(request.getParameter("startDate"));
		System.out.println(request.getParameter("endDate"));
		System.out.println(request.getParameter("WorkOrder_EquipCode"));
		
		String sql = "SELECT \r\n"
				+ "		Defect_ONo,\r\n"
				+ "		t2.WorkOrder_PQty,\r\n"
				+ "		t2.WorkOrder_RQty,\r\n"
				+ "		SUM(IF(Defect_Code='D001',Defect_DQty,0)) D001,\r\n"
				+ "		SUM(IF(Defect_Code='D002',Defect_DQty,0)) D002,\r\n"
				+ "		SUM(IF(Defect_Code='D003',Defect_DQty,0)) D003,\r\n"
				+ "		SUM(IF(Defect_Code='D004',Defect_DQty,0)) D004,\r\n"
				+ "		SUM(IF(Defect_Code='D005',Defect_DQty,0)) D005,\r\n"
				+ "		SUM(IF(Defect_Code='D997',Defect_DQty,0)) D997,\r\n"
				+ "		Defect_TestTime\r\n"
				+ "		,SUM(Defect_DQty)\r\n"
				+ "FROM defectPerformance t1\r\n"
				+ "INNER JOIN(\r\n"
				+ "SELECT\r\n"
				+ "			*\r\n"
				+ "FROM			WorkOrder_tbl\r\n"
				+ "WHERE		WorkOrder_CompleteTime BETWEEN '"+request.getParameter("startDate")+" 00:00:00' AND '"+request.getParameter("endDate")+" 23:59:59'\r\n"
				+ "AND		WorkOrder_EquipCode='"+request.getParameter("WorkOrder_EquipCode")+"'\r\n"
				+ ") t2 \r\n"
				+ "ON t1.Defect_ONo = t2.WorkOrder_ONo\r\n"
				+ "GROUP BY Defect_ONo";
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public DefectPerformance mapRow(ResultSet rs, int rowNum) throws SQLException {
				DefectPerformance data = new DefectPerformance();
				data.setDefect_ONo(rs.getString(1));
				data.setWorkOrder_PQty(rs.getString(2));
				data.setWorkOrder_RQty(rs.getString(3));
				data.setD001(rs.getString(4));
				data.setD002(rs.getString(5));
				data.setD003(rs.getString(6));
				data.setD004(rs.getString(7));
				data.setD005(rs.getString(8));
				data.setD997(rs.getString(9));
				data.setDefect_TestTime(rs.getString(10));
				data.setDefect_DQty(rs.getString(11));
				return data;
			}
		});
	}
	
	@GetMapping("/unit1")
	public List<PRODUCTION_INFO_TBL> unit1() throws SQLException {
		List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		
		System.out.println(date);
		
		Connection conn = dataSource.getConnection();
		
		String sql = "";
		/*
		생산량을 포함한 조회
		sql = "select\r\n"
				+ "				MID(t1.PRODUCTION_SERIAL_NUM,7,4) AS        TIME                  										-- 시간\r\n"
				+ "			,   t2.PRODUCT_ITEM_NAME                        PRODUCT_ITEM_NAME         									-- 모델명\r\n"
				+ "            ,	if(sum(PRODUCTION_VOLUME)=0,'',cast(sum(PRODUCTION_VOLUME) as signed integer))	PRODUCTION_VOLUME		-- 생산수량\r\n"
				+ "            ,	if(sum(PRODUCTION_BAD)=0,'',cast(sum(PRODUCTION_BAD) as signed integer))	PRODUCTION_DEFECT_CODE		-- 불량수량\r\n"
				+ "            ,	t1.PRODUCTION_DEFECT_CODE					PRODUCTION_DEFECT_CODE2										-- 불량코드\r\n"
				+ "            ,	ifnull(t3.DEFECT_NAME,'')								DEFECT_NAME										-- 불량명\r\n"
				+ "from   PRODUCTION_MGMT_TBL   as t1\r\n"
				+ "inner join PRODUCT_INFO_TBL t2 on t1.PRODUCTION_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DEFECT_INFO_TBL t3 ON t1.PRODUCTION_DEFECT_CODE = t3.DEFECT_CODE\r\n"
				+ "where date(t1.PRODUCTION_MODIFY_D) = ? and t1.PRODUCTION_EQUIPMENT_CODE = 'M001'\r\n"
				+ "group by PRODUCTION_SERIAL_NUM, PRODUCTION_EQUIPMENT_CODE, PRODUCTION_MOLD_INFO_CODE,\r\n"
				+ "PRODUCTION_PRODUCT_CODE,PRODUCTION_USER_CODE, PRODUCTION_DEFECT_CODE";
		*/
		
		// 생산량을 빼고 난 후에 조회
		sql = "select\r\n"
				+ "            MID(t1.PRODUCTION_SERIAL_NUM,7,4) AS        TIME                                                -- 시간\r\n"
				+ "         ,   t2.PRODUCT_ITEM_NAME                        PRODUCT_ITEM_NAME                                    -- 모델명\r\n"
				+ "            ,   if(sum(PRODUCTION_VOLUME)=0,'',cast(sum(PRODUCTION_VOLUME) as signed integer))   PRODUCTION_VOLUME      -- 생산수량\r\n"
				+ "            ,   if(sum(PRODUCTION_BAD)=0,'',cast(sum(PRODUCTION_BAD) as signed integer))   PRODUCTION_DEFECT_CODE      -- 불량수량\r\n"
				+ "            ,   t1.PRODUCTION_DEFECT_CODE               PRODUCTION_DEFECT_CODE2                              -- 불량코드\r\n"
				+ "            ,   ifnull(t3.DEFECT_NAME,'')                        DEFECT_NAME                              -- 불량명\r\n"
				+ "from   PRODUCTION_MGMT_TBL   as t1\r\n"
				+ "inner join PRODUCT_INFO_TBL t2 on t1.PRODUCTION_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DEFECT_INFO_TBL t3 ON t1.PRODUCTION_DEFECT_CODE = t3.DEFECT_CODE\r\n"
				+ "inner join (select PRODUCTION_SERIAL_NUM from PRODUCTION_MGMT_TBL\r\n"
				+ "where  PRODUCTION_DEFECT_CODE <> '' and\r\n"
				+ "date(PRODUCTION_MODIFY_D) = ? and PRODUCTION_EQUIPMENT_CODE = 'M001'\r\n"
				+ "group by PRODUCTION_SERIAL_NUM) F ON t1.PRODUCTION_SERIAL_NUM = F.PRODUCTION_SERIAL_NUM\r\n"
				+ "\r\n"
				+ "group by t1.PRODUCTION_SERIAL_NUM, PRODUCTION_EQUIPMENT_CODE, PRODUCTION_MOLD_INFO_CODE,\r\n"
				+ "PRODUCTION_PRODUCT_CODE,PRODUCTION_USER_CODE, PRODUCTION_DEFECT_CODE;";
		
		//System.out.println(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, date);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();
			// 시간
			StringBuffer timeSet = new StringBuffer(rs.getString("TIME"));
			timeSet.insert(2, ":");
			
			data.setTIME(timeSet.toString());
			// 모델명
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			// 생산수량
			data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME"));
			// 불량수량
			data.setPRODUCTION_DEFECT_CODE(rs.getString("PRODUCTION_DEFECT_CODE"));
			// 불량명칭
			data.setPRODUCTION_DEFECT_NAME(rs.getString("DEFECT_NAME"));
			
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}
	
	@GetMapping("/unit2")
	public List<PRODUCTION_INFO_TBL> unit2() throws SQLException {
List<PRODUCTION_INFO_TBL> list = new ArrayList<PRODUCTION_INFO_TBL>();
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		
		Connection conn = dataSource.getConnection();
		
		String sql = "";
		
		/*
		생산량을 포함한 조회
		sql = "select\r\n"
				+ "				MID(t1.PRODUCTION_SERIAL_NUM,7,4) AS        TIME                  										-- 시간\r\n"
				+ "			,   t2.PRODUCT_ITEM_NAME                        PRODUCT_ITEM_NAME         									-- 모델명\r\n"
				+ "            ,	if(sum(PRODUCTION_VOLUME)=0,'',cast(sum(PRODUCTION_VOLUME) as signed integer))	PRODUCTION_VOLUME		-- 생산수량\r\n"
				+ "            ,	if(sum(PRODUCTION_BAD)=0,'',cast(sum(PRODUCTION_BAD) as signed integer))	PRODUCTION_DEFECT_CODE		-- 불량수량\r\n"
				+ "            ,	t1.PRODUCTION_DEFECT_CODE					PRODUCTION_DEFECT_CODE2										-- 불량코드\r\n"
				+ "            ,	ifnull(t3.DEFECT_NAME,'')								DEFECT_NAME										-- 불량명\r\n"
				+ "from   PRODUCTION_MGMT_TBL   as t1\r\n"
				+ "inner join PRODUCT_INFO_TBL t2 on t1.PRODUCTION_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DEFECT_INFO_TBL t3 ON t1.PRODUCTION_DEFECT_CODE = t3.DEFECT_CODE\r\n"
				+ "where date(t1.PRODUCTION_MODIFY_D) = ? and t1.PRODUCTION_EQUIPMENT_CODE = 'M002'\r\n"
				+ "group by PRODUCTION_SERIAL_NUM, PRODUCTION_EQUIPMENT_CODE, PRODUCTION_MOLD_INFO_CODE,\r\n"
				+ "PRODUCTION_PRODUCT_CODE,PRODUCTION_USER_CODE, PRODUCTION_DEFECT_CODE";
		*/
		// 생산량을 빼고 난 후에 조회
		sql = "select\r\n"
						+ "            MID(t1.PRODUCTION_SERIAL_NUM,7,4) AS        TIME                                                -- 시간\r\n"
						+ "         ,   t2.PRODUCT_ITEM_NAME                        PRODUCT_ITEM_NAME                                    -- 모델명\r\n"
						+ "            ,   if(sum(PRODUCTION_VOLUME)=0,'',cast(sum(PRODUCTION_VOLUME) as signed integer))   PRODUCTION_VOLUME      -- 생산수량\r\n"
						+ "            ,   if(sum(PRODUCTION_BAD)=0,'',cast(sum(PRODUCTION_BAD) as signed integer))   PRODUCTION_DEFECT_CODE      -- 불량수량\r\n"
						+ "            ,   t1.PRODUCTION_DEFECT_CODE               PRODUCTION_DEFECT_CODE2                              -- 불량코드\r\n"
						+ "            ,   ifnull(t3.DEFECT_NAME,'')                        DEFECT_NAME                              -- 불량명\r\n"
						+ "from   PRODUCTION_MGMT_TBL   as t1\r\n"
						+ "inner join PRODUCT_INFO_TBL t2 on t1.PRODUCTION_PRODUCT_CODE = t2.PRODUCT_ITEM_CODE\r\n"
						+ "left outer join DEFECT_INFO_TBL t3 ON t1.PRODUCTION_DEFECT_CODE = t3.DEFECT_CODE\r\n"
						+ "inner join (select PRODUCTION_SERIAL_NUM from PRODUCTION_MGMT_TBL\r\n"
						+ "where  PRODUCTION_DEFECT_CODE <> '' and\r\n"
						+ "date(PRODUCTION_MODIFY_D) = ? and PRODUCTION_EQUIPMENT_CODE = 'M002'\r\n"
						+ "group by PRODUCTION_SERIAL_NUM) F ON t1.PRODUCTION_SERIAL_NUM = F.PRODUCTION_SERIAL_NUM\r\n"
						+ "\r\n"
						+ "group by t1.PRODUCTION_SERIAL_NUM, PRODUCTION_EQUIPMENT_CODE, PRODUCTION_MOLD_INFO_CODE,\r\n"
						+ "PRODUCTION_PRODUCT_CODE,PRODUCTION_USER_CODE, PRODUCTION_DEFECT_CODE;";
		
		//System.out.println(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, date);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			PRODUCTION_INFO_TBL data = new PRODUCTION_INFO_TBL();
			// 시간
			StringBuffer timeSet = new StringBuffer(rs.getString("TIME"));
			timeSet.insert(2, ":");
						
			data.setTIME(timeSet.toString());
			// 모델명
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			// 생산수량
			data.setPRODUCTION_P_Qty(rs.getInt("PRODUCTION_VOLUME"));
			// 불량수량
			data.setPRODUCTION_DEFECT_CODE(rs.getString("PRODUCTION_DEFECT_CODE"));
			// 불량명칭
			data.setPRODUCTION_DEFECT_NAME(rs.getString("DEFECT_NAME"));
			
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}
	
}
