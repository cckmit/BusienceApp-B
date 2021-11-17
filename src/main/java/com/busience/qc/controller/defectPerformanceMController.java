package com.busience.qc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.productionLX.dto.PRODUCTION_INFO_TBL;

@Controller
public class defectPerformanceMController {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	int no = 1;
	String init_numflag = "";
	String WorkOrder_ONo = "";
	
	@GetMapping("/tablet/defectPerformanceM")
	public String defectPerformanceM(Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		
		/*
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://xeonic11.cafe24.com:3306/xeonic11","xeonic11","gil45200!");
		
		String sql = "SELECT * FROM PRODUCTION_MGMT_TBL WHERE PRODUCTION_MODIFY_D BETWEEN \"2021-03-01 00:00:00\" AND  \"2021-09-01 00:00:00\"";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			jdbctemplate.update("insert into PRODUCTION_MGMT_TBL_X values(?,?,?,?,?,?,?,?,?,?,?)",rs.getString("PRODUCTION_SERIAL_NUM"),rs.getInt("PRODUCTION_INFO_NUM")
					,rs.getString("PRODUCTION_EQUIPMENT_CODE"),rs.getString("PRODUCTION_MOLD_INFO_CODE"),rs.getString("PRODUCTION_PRODUCT_CODE"),rs.getString("PRODUCTION_DEFECT_CODE")
					,rs.getInt("PRODUCTION_VOLUME"),rs.getInt("PRODUCTION_BAD"),rs.getString("PRODUCTION_MODIFY_D"),rs.getString("PRODUCTION_CC"),rs.getString("PRODUCTION_USER_CODE"));
		}
		
		if(rs != null ) rs.close();
		if(pstmt != null )pstmt.close();
		if(con != null ) con.close();
		*/
		
		/*
		String sql = "SELECT \r\n"
				+ "SUBSTRING(PRODUCTION_SERIAL_NUM,1,6) numflag,\r\n"
				+ "CONCAT('20',SUBSTRING(PRODUCTION_SERIAL_NUM,1,6),'-',PRODUCTION_PRODUCT_CODE,'-') WorkOrder_ONo,\r\n"
				+ "PRODUCTION_PRODUCT_CODE WorkOrder_ItemCode,\r\n"
				+ "PRODUCTION_EQUIPMENT_CODE WorkOrder_EquipCode,SUM(PRODUCTION_VOLUME) WorkOrder_PQty,\r\n"
				+ "DATE_SUB(PRODUCTION_MODIFY_D,INTERVAL 1 DAY) WorkOrder_RegisterTime,\r\n"
				+ "DATE_ADD(DATE_SUB(PRODUCTION_MODIFY_D,INTERVAL 1 DAY),INTERVAL 1 HOUR) WorkOrder_ReceiptTime,\r\n"
				+ "DATE_ADD(DATE_SUB(PRODUCTION_MODIFY_D,INTERVAL 1 DAY),INTERVAL 2 HOUR) WorkOrder_OrderTime,\r\n"
				+ "PRODUCTION_MODIFY_D WorkOrder_StartTime,\r\n"
				+ "DATE_ADD((SELECT PRODUCTION_MODIFY_D FROM PRODUCTION_MGMT_TBL_X WHERE PRODUCTION_SERIAL_NUM= t1.PRODUCTION_SERIAL_NUM ORDER BY PRODUCTION_MODIFY_D desc LIMIT 1),INTERVAL 1 DAY) WorkOrder_CompleteOrderTime,\r\n"
				+ "(SELECT PRODUCTION_MODIFY_D FROM PRODUCTION_MGMT_TBL_X WHERE PRODUCTION_SERIAL_NUM= t1.PRODUCTION_SERIAL_NUM ORDER BY PRODUCTION_MODIFY_D desc LIMIT 1) WorkOrder_CompleteTime,\r\n"
				+ "PRODUCTION_SERIAL_NUM,\r\n"
				+ "PRODUCTION_INFO_NUM,\r\n"
				+ "PRODUCTION_EQUIPMENT_CODE,\r\n"
				+ "PRODUCTION_MOLD_INFO_CODE,\r\n"
				+ "PRODUCTION_PRODUCT_CODE,\r\n"
				+ "PRODUCTION_DEFECT_CODE,\r\n"
				+ "PRODUCTION_VOLUME,\r\n"
				+ "PRODUCTION_BAD,\r\n"
				+ "PRODUCTION_MODIFY_D,\r\n"
				+ "PRODUCTION_CC,\r\n"
				+ "PRODUCTION_USER_CODE \r\n"
				+ "FROM PRODUCTION_MGMT_TBL_X t1 GROUP BY PRODUCTION_SERIAL_NUM";
		
		
		
		jdbctemplate.query(sql, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String numflag = rs.getString("numflag");
				if(!init_numflag.equals(numflag))
				{
					no = 1;
					init_numflag = numflag;
				}
				else
				{
					no++;
				}
				//System.out.println(rs.getString("WorkOrder_ONo")+"0"+no);
				WorkOrder_ONo = rs.getString("WorkOrder_ONo")+"0"+no;
				
				
				
				jdbctemplate.update("INSERT INTO\r\n"
						+ "WorkOrder_tbl\r\n"
						+ "VALUES(\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?\r\n"
						+ ")"
						,no
						,WorkOrder_ONo
						,"A0100"+no
						,rs.getString("WorkOrder_EquipCode")
						,rs.getString("WorkOrder_PQty")
						,rs.getString("WorkOrder_PQty")
						,rs.getString("WorkOrder_RegisterTime")
						,rs.getString("WorkOrder_ReceiptTime")
						,rs.getString("WorkOrder_OrderTime")
						,rs.getString("WorkOrder_StartTime")
						,rs.getString("WorkOrder_CompleteOrderTime")
						,rs.getString("WorkOrder_CompleteTime")
						,"245"
						,"admin"
						,""
						);
				
				jdbctemplate.query("SELECT * FROM PRODUCTION_MGMT_TBL_X WHERE PRODUCTION_SERIAL_NUM = ?",new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						jdbctemplate.update("INSERT INTO PRODUCTION_MGMT_TBL2\r\n"
								+ "(PRODUCTION_WorkOrder_ONo, PRODUCTION_Equipment_Code, PRODUCTION_Volume)\r\n"
								+ "VALUES(\r\n"
								+ "?,\r\n"
								+ "?,\r\n"
								+ "?\r\n"
								+ ")"
								,WorkOrder_ONo
								,rs.getString("PRODUCTION_EQUIPMENT_CODE")
								,rs.getString("PRODUCTION_VOLUME")
								);
						
						return null;
					}
				},rs.getString("PRODUCTION_SERIAL_NUM"));
				
				return null;
			}
			
		});
		
		20210302-A70001-01
		20210302-A70002-02
		20210312-A01059-01
		20210312-A01052-02
		20210329-A70001-01
		20210329-A70002-02
		20210412-A01052-01
		20210412-A01059-02
		20210426-A70001-01
		20210426-A70002-02
		20210510-A01052-01
		20210510-A01059-02
		20210524-A70002-01
		20210524-A70001-02
		20210607-A01059-01
		20210607-A01052-02
		20210621-A70002-01
		20210621-A70001-02
		20210705-A01059-01
		20210705-A01052-02
		20210719-A70001-01
		20210719-A70002-02
		20210802-A01059-01
		20210802-A01052-02
		20210816-A70001-01
		20210816-A70002-02
		20210830-A01059-01
		20210830-A01052-02
		
		*/
		
		return "normal/qc/defectPerformanceM";
	}
	
}
