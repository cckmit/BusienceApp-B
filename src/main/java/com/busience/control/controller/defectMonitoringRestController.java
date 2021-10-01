package com.busience.control.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.production.dto.PRODUCTION_INFO_TBL;

@RestController
@RequestMapping("defectMonitoringRest")
public class defectMonitoringRestController {

	@Autowired
	DataSource dataSource;
	
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
