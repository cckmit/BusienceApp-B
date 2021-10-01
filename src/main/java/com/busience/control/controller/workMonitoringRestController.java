package com.busience.control.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.production.dto.WorkOrder_tbl;

@RestController
@RequestMapping("workMonitoringRest")
public class workMonitoringRestController {

	@Autowired
	DataSource dataSource;
	
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
