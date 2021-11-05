package com.busience.qc.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.qc.dto.OQCInspect_tbl;
import com.busience.standard.dto.DEFECT_INFO_TBL;

@RestController
@RequestMapping("defectInsertRest")
public class defectInsertRestController {
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/Search",method = RequestMethod.GET)
	public List<OQCInspect_tbl> Search(HttpServletRequest request) throws ParseException
	{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		String sql = "SELECT\r\n"
				+ "			t1.WorkOrder_ONo\r\n"
				+ "			,t1.WorkOrder_ItemCode\r\n"
				+ "			,t1.PRODUCT_ITEM_NAME\r\n"
				+ "			,t1.PRODUCT_INFO_STND_1\r\n"
				+ "			,t1.WorkOrder_EquipCode\r\n"
				+ "			,t1.EQUIPMENT_INFO_NAME\r\n"
				+ "			,t1.WorkOrder_RQty\r\n"
				+ "			,t1.WorkOrder_PQty\r\n"
				+ "			,sum(t2.Defect_DQty) Defect_DQty\r\n"
				+ "			,t1.CHILD_TBL_NO\r\n"
				+ "			,t1.CHILD_TBL_TYPE\r\n"
				+ "			,t1.WorkOrder_CompleteTime\r\n"
				+ "FROM\r\n"
				+ "(\r\n"
				+ "	SELECT\r\n"
				+ "			*\r\n"
				+ "	FROM			WorkOrder_tbl a1\r\n"
				+ "	Left JOIN	PRODUCT_INFO_TBL a2 ON a1.WorkOrder_ItemCode = a2.PRODUCT_ITEM_CODE\r\n"
				+ "	LEFT JOIN	EQUIPMENT_INFO_TBL a3 ON a1.WorkOrder_EquipCode = a3.EQUIPMENT_INFO_CODE\r\n"
				+ "	LEFT JOIN	DTL_TBL a4 ON a1.WorkOrder_WorkStatus = a4.CHILD_TBL_NO\r\n"
				+ "	WHERE			a1.WorkOrder_WorkStatus = 245\r\n"
				+ "	AND			a1.WorkOrder_RQty > 0\r\n"
				+ "	AND			a1.WorkOrder_CompleteTime between '"+obj.get("startDate")+"' and '"+obj.get("endDate")+"'\r\n"
				+ ")	t1\r\n"
				+ "LEFT JOIN (\r\n"
				+ "	select\r\n"
				+ "				*\r\n"
				+ "	FROM		defectPerformance\r\n"
				+ ")	t2\r\n"
				+ "ON	t1.WorkOrder_ONo = t2.Defect_ONo\r\n"
				+ "GROUP BY t1.WorkOrder_ONo";
		
		System.out.println(sql);
		
		return jdbctemplate.query(sql, new RowMapper<OQCInspect_tbl>() {
			@Override
			public OQCInspect_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				OQCInspect_tbl data = new OQCInspect_tbl();
				data.setOQCInspect_OqcInNo(rs.getString("WorkOrder_ONo"));
				
				data.setPRODUCT_ITEM_CODE(rs.getString("WorkOrder_ItemCode"));
				data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				
				data.setCus_Code(rs.getString("WorkOrder_EquipCode"));
				data.setCus_Name(rs.getString("EQUIPMENT_INFO_NAME"));
				
				data.setOQCInspect_PQty(rs.getString("WorkOrder_PQty"));
				data.setOQCInspect_SaQty(rs.getString("WorkOrder_RQty"));
				data.setOQCInspect_DQty(rs.getString("Defect_DQty"));
				
				data.setOQCInspect_Prcsn_Clsfc(rs.getString("CHILD_TBL_NO"));
				data.setOQCInspect_Prcsn_Clsfc_Name(rs.getString("CHILD_TBL_TYPE"));
				data.setOQCInspect_Date(rs.getString("WorkOrder_CompleteTime"));
				
				return data;
			}
		});
	}
	
	@RequestMapping(value = "/Upsert",method = RequestMethod.GET)
	public void Upsert(HttpServletRequest request) throws ParseException
	{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONArray dataList = (JSONArray)parser.parse(originData);
		
		dataList.forEach(n->{
			JSONObject obj = (JSONObject)n;
			
			
			String sql = "INSERT INTO defectPerformance\r\n"
					+ "VALUES(?,?,?,now())\r\n"
					+ "ON DUPLICATE KEY\r\n"
					+ "UPDATE Defect_DQty = ?,Defect_TestTime=now()";
			
			jdbctemplate.update(sql,obj.get("defect_USE_STATUS"),obj.get("defect_CODE"),obj.get("defect_ABR"),obj.get("defect_ABR"));
		});
	}
	
	@RequestMapping(value = "/DEFECT_INFO_TBL_Load",method = RequestMethod.GET)
	public List<DEFECT_INFO_TBL> DEFECT_INFO_TBL_Load(HttpServletRequest request) throws ParseException
	{
		String sql = "SELECT\r\n"
				+ "			*\r\n"
				+ "			,(SELECT Defect_DQty FROM defectPerformance WHERE Defect_ONo=? AND Defect_CODE=t1.DEFECT_CODE) DEFECT_QTY\r\n"
				+ "FROM		DEFECT_INFO_TBL t1";
		
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
