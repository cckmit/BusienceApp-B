package com.busience.monitoring.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.productionLX.dto.WorkOrder_tbl;

@RestController
@RequestMapping("workMonitoringRest")
public class workMonitoringRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@GetMapping("/WorkOrder_select")
	public List<WorkOrder_tbl> WorkOrder_select(HttpServletRequest request) throws SQLException{
		
		String sql = "SELECT\r\n"
				+ "			*\r\n"
				+ "			,(SELECT PRODUCT_ITEM_NAME FROM PRODUCT_INFO_TBL WHERE PRODUCT_ITEM_CODE = WorkOrder_ItemCode) PRODUCT_ITEM_NAME\r\n"
				+ "			,(SELECT EQUIPMENT_INFO_NAME FROM EQUIPMENT_INFO_TBL WHERE EQUIPMENT_INFO_CODE = WorkOrder_EquipCode) EQUIPMENT_INFO_NAME\r\n"
				+ "FROM\r\n"
				+ "(\r\n"
				+ "	SELECT\r\n"
				+ "			*\r\n"
				+ "	FROM		EQUIPMENT_INFO_TBL\r\n"
				+ "	WHERE		EQUIPMENT_INFO_CODE = '"+request.getParameter("WorkOrder_EquipCode")+"'\r\n"
				+ ")  t1\r\n"
				+ "LEFT JOIN\r\n"
				+ "(\r\n"
				+ "	SELECT\r\n"
				+ "				*\r\n"
				+ "	FROM		WorkOrder_tbl\r\n"
				+ "	WHERE		WorkOrder_EquipCode = '"+request.getParameter("WorkOrder_EquipCode")+"'\r\n"
				+ "	AND		WorkOrder_WorkStatus = 244\r\n"
				+ ")  t2\r\n"
				+ "ON t1.EQUIPMENT_INFO_CODE = t2.WorkOrder_EquipCode";
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				
				data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
				data.setWorkOrder_ItemCode(rs.getString("WorkOrder_ItemCode"));
				data.setWorkOrder_ItemName( (rs.getString("PRODUCT_ITEM_NAME").length() >= 8)? rs.getString("PRODUCT_ITEM_NAME").substring(0, 8): rs.getString("PRODUCT_ITEM_NAME") );
				data.setWorkOrder_RQty( (rs.getString("WorkOrder_RQty")==null)?"0":rs.getString("WorkOrder_RQty") );
				
				data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
				data.setWorkOrder_EquipName(rs.getString("EQUIPMENT_INFO_NAME"));
				
				return data;
			}
		});
	}
	
	@GetMapping("/WorkOrder_select2")
	public List<WorkOrder_tbl> WorkOrder_select2(HttpServletRequest request){
		String sql = "SELECT\r\n"
				+ "	(\r\n"
				+ "		SELECT\r\n"
				+ "					if(TIMESTAMPDIFF(SECOND, PRODUCTION_Date, NOW())>10,'non-moving','moving')\r\n"
				+ "		FROM		PRODUCTION_MGMT_TBL2\r\n"
				+ "		WHERE		PRODUCTION_WorkOrder_ONo = (SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_WorkStatus='244' AND WorkOrder_EquipCode=?)\r\n"
				+ "		ORDER BY PRODUCTION_Date DESC\r\n"
				+ "		LIMIT 1	\r\n"
				+ "	)  moving_flag,\r\n"
				+ "	(\r\n"
				+ "		SELECT\r\n"
				+ "					t2.PRODUCT_ITEM_NAME\r\n"
				+ "		FROM		WorkOrder_tbl t1\r\n"
				+ "		INNER JOIN PRODUCT_INFO_TBL t2 ON t1.WorkOrder_ItemCode = t2.PRODUCT_ITEM_CODE\r\n"
				+ "		WHERE		WorkOrder_WorkStatus='244' AND WorkOrder_EquipCode=?\r\n"
				+ "	)  PRODUCT_ITEM_NAME,\r\n"
				+ "	(\r\n"
				+ "		SELECT\r\n"
				+ "					SUM(PRODUCTION_Volume)\r\n"
				+ "		FROM		PRODUCTION_MGMT_TBL2\r\n"
				+ "		WHERE		PRODUCTION_WorkOrder_ONo = (SELECT WorkOrder_ONo FROM WorkOrder_tbl WHERE WorkOrder_WorkStatus='244' AND WorkOrder_EquipCode=?)\r\n"
				+ "	)  SUM_PRODUCTION_Volume,\r\n"
				+ "	(\r\n"
				+ "		SELECT	\r\n"
				+ "					WorkOrder_PQty\r\n"
				+ "		FROM		WorkOrder_tbl\r\n"
				+ "		WHERE		WorkOrder_EquipCode = ? AND WorkOrder_WorkStatus = '244'\r\n"
				+ "	) Order_Volume"
				+ ",(\r\n"
				+ "		SELECT	\r\n"
				+ "					WorkOrder_ONo\r\n"
				+ "		FROM		WorkOrder_tbl\r\n"
				+ "		WHERE		WorkOrder_EquipCode = ? AND WorkOrder_WorkStatus = '244'\r\n"
				+ "	) WorkOrder_ONo";
		
		return jdbctemplate.query(sql, new RowMapper() {

			@Override
			public WorkOrder_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkOrder_tbl data = new WorkOrder_tbl();
				
				data.setWorkOrder_No(rs.getString("WorkOrder_ONo"));
				data.setWorkOrder_ONo(rs.getString("moving_flag"));
				
				data.setWorkOrder_ItemName((rs.getString("PRODUCT_ITEM_NAME").length() > 10)? rs.getString("PRODUCT_ITEM_NAME").substring(0, 10): rs.getString("PRODUCT_ITEM_NAME"));
				data.setQty((rs.getString("SUM_PRODUCTION_Volume")==null)?"0":rs.getString("SUM_PRODUCTION_Volume"));
				data.setWorkOrder_PQty( (rs.getString("Order_Volume")==null)?data.getQty():String.valueOf(rs.getInt("Order_Volume")) );
				
				if(rs.getString("Order_Volume")==null)
				{
					data.setPercent("100");
				}
				else
				{
					float percent = Float.parseFloat(data.getQty()) / Float.parseFloat(data.getWorkOrder_PQty());
					percent *= 100;
					
					data.setPercent(String.valueOf(Math.round(percent)));
				}
				
				return data;
			}
		},request.getParameter("code"),request.getParameter("code"),request.getParameter("code"),request.getParameter("code"),request.getParameter("code"));
	}
	
	@GetMapping("/moni")
	public WorkOrder_tbl moni(HttpServletRequest request) throws SQLException {
		String WorkOrder_EquipCode = request.getParameter("WorkOrder_EquipCode");
		String sql = "select\r\n"
				+ "			t1.WorkOrder_ONo								-- �۾����ù�ȣ\r\n"
				+ "		,	t1.WorkOrder_StartTime"	
				+ "		,	t1.WorkOrder_EquipCode"	
				+ "		,	t1.WorkOrder_PQty\r\n"
				+ "		,	ROUND((sum(t2.PRODUCTION_Volume)/t1.WorkOrder_PQty) * 100,1) percent -- �޼���\r\n"
				+ "        ,	sum(t2.PRODUCTION_Volume) WorkOrder_RQty		-- ���귮\r\n"
				+ "        ,   t3.OQCInspect_DQty								-- �ҷ�\r\n"
				+ "        ,	t4.PRODUCT_ITEM_NAME WorkOrder_ItemName							-- ��ǰ��\r\n"
				+ "		,	'"+WorkOrder_EquipCode+"' dbdata_flag\r\n"	
				+ "from	WorkOrder_tbl	t1\r\n"
				+ "inner join	PRODUCTION_MGMT_TBL2 t2 \r\n"
				+ "on t1.WorkOrder_ONo = t2.PRODUCTION_WorkOrder_ONo \r\n"
				+ "left outer join	OQCInspect_tbl t3\r\n"
				+ "on t1.WorkOrder_ONo = t3.OQCInspect_OqcInNo \r\n"
				+ "inner join PRODUCT_INFO_TBL t4\r\n"
				+ "on t1.WorkOrder_ItemCode = t4.PRODUCT_ITEM_CODE\r\n"
				+ "where t1.WorkOrder_WorkStatus='293' and t1.WorkOrder_EquipCode='"+WorkOrder_EquipCode+"'";
		
		//System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		WorkOrder_tbl data = null;
		
		while (rs.next()) {
			data = new WorkOrder_tbl();
			data.setDbdata_flag(rs.getString("dbdata_flag"));
			data.setWorkOrder_StartTime(rs.getString("WorkOrder_StartTime"));
			data.setWorkOrder_ONo(rs.getString("WorkOrder_ONo"));
			data.setWorkOrder_EquipCode(rs.getString("WorkOrder_EquipCode"));
			data.setWorkOrder_PQty(rs.getString("WorkOrder_PQty"));
			data.setPercent(rs.getString("percent"));
			data.setWorkOrder_RQty(rs.getString("WorkOrder_RQty"));
			data.setOQCInspect_DQty(rs.getString("OQCInspect_DQty"));
			data.setWorkOrder_ItemName(rs.getString("WorkOrder_ItemName"));
		}
		
		return data;
	}
	
}
