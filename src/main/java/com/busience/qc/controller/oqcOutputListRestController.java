package com.busience.qc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.qc.dto.OQCInspect_tbl;

@RestController("oqcOutputListRestController")
@RequestMapping("oqcOutputListRest")
public class oqcOutputListRestController {

	@Autowired
	DataSource dataSource;
	
	@RequestMapping(value = "/Search",method = RequestMethod.GET)
	public List<OQCInspect_tbl> Search(HttpServletRequest request) throws ParseException, SQLException
	{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		String OQCInspect_Prcsn_Clsfc = (String)obj.get("OQCInspect_Prcsn_Clsfc");
		if(!OQCInspect_Prcsn_Clsfc.equals("All"))
			OQCInspect_Prcsn_Clsfc = " and OQCInspect_Prcsn_Clsfc='"+OQCInspect_Prcsn_Clsfc+"'\r\n";
		else
			OQCInspect_Prcsn_Clsfc = "";
		
		String sql ="";
		
		sql = "select\r\n"
				+ "			t1.OQCInspect_OqcInNo									-- 출하검사번호\r\n"
				+ "        ,	t1.OQCInspect_Date										-- 등록일자\r\n"
				+ "        ,	t1.OQCInspect_ItemCode PRODUCT_ITEM_CODE				-- 품목코드\r\n"
				+ "        ,	t1.PRODUCT_ITEM_NAME									-- 품목명\r\n"
				+ "        ,	t1.PRODUCT_INFO_STND_1									-- 규격\r\n"
				+ "        ,	t1.OQCInspect_Worker									-- 검사자 코드\r\n"
				+ "        ,	t1.OQCInspect_Worker_Name 								-- 검사자 이름\r\n"
				+ "        ,	t1.OQCInspect_Prcsn_Clsfc								-- 처리구분 코드\r\n"
				+ "        ,	t1.OQCInspect_Prcsn_Clsfc_Name							-- 처리구분 이름\r\n"
				+ "		,	t1.OQCInspect_DQty										-- 불량수량\r\n"
				+ "        ,	t1.OQCInspect_PQty										-- 합격수량\r\n"
				+ "        ,	t1.OQCInspect_SQty										-- 특채수량\r\n"
				+ "        ,(\r\n"
				+ "				select sum(OQCInspectType_SaQty) from OQCInspectType_tbl\r\n"
				+ "                where OQCInspect_OqcInNo = OQCInspect_OqcChNo\r\n"
				+ "			) OQCInspect_SaQty										-- Sample수량\r\n"
				+ "		,	t1.OQCInspect_Problem									-- 문제점\r\n"
				+ "from	(\r\n"
				+ "			select \r\n"
				+ "					a1.*,a2.CHILD_TBL_TYPE OQCInspect_Worker_Name,a3.CHILD_TBL_TYPE OQCInspect_Prcsn_Clsfc_Name,a4.*\r\n"
				+ "			from	OQCInspect_tbl a1\r\n"
				+ "            inner join (select * from DTL_TBL where NEW_TBL_CODE='23') a2 on a1.OQCInspect_Worker = a2.CHILD_TBL_NO\r\n"
				+ "            inner join (select * from DTL_TBL where NEW_TBL_CODE='22') a3 on a1.OQCInspect_Prcsn_Clsfc = a3.CHILD_TBL_NO\r\n"
				+ "            inner join PRODUCT_INFO_TBL a4 on a1.OQCInspect_ItemCode = a4.PRODUCT_ITEM_CODE\r\n"
				+ "            where OQCInspect_Date between '"+ obj.get("startDate") + " 00:00:00' and '"+ obj.get("endDate") + " 23:59:59'\r\n"+OQCInspect_Prcsn_Clsfc
				+ "		) t1";
		
		//System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<OQCInspect_tbl> list = new ArrayList<OQCInspect_tbl>();
		
		int i = 0;
		while (rs.next()) {
			OQCInspect_tbl data = new OQCInspect_tbl();
			data.setNum(++i);
			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			//data.setOQCInspect_Lot_No(rs.getString("OQCInspect_Lot_No"));
			//data.setOQCInspect_INo(rs.getString("OQCInspect_INo"));
			data.setOQCInspect_OqcInNo(rs.getString("OQCInspect_OqcInNo"));
			data.setOQCInspect_Prcsn_Clsfc(rs.getString("OQCInspect_Prcsn_Clsfc"));
			data.setOQCInspect_DQty(rs.getString("OQCInspect_DQty"));
			data.setOQCInspect_PQty(rs.getString("OQCInspect_PQty"));
			data.setOQCInspect_SQty(rs.getString("OQCInspect_SQty"));
			data.setOQCInspect_SaQty(rs.getString("OQCInspect_SaQty"));
			data.setOQCInspect_Date(rs.getString("OQCInspect_Date"));
			data.setOQCInspect_Worker(rs.getString("OQCInspect_Worker"));
			data.setOQCInspect_Problem(rs.getString("OQCInspect_Problem"));
			data.setOQCInspect_Prcsn_Clsfc_Name(rs.getString("OQCInspect_Prcsn_Clsfc_Name"));
			data.setOQCInspect_Worker_Name(rs.getString("OQCInspect_Worker_Name"));
			
			sql = "SELECT "
					+ "t1.OQCInspectType_DQty OQCInspectType_DQty,"
					+ "OQCInspectType_IQty,CONCAT(t2.CHILD_TBL_TYPE,\" (\",t3.CHILD_TBL_TYPE,\", 불\",t1.OQCInspectType_DQty,\")\") remark FROM OQCInspectType_tbl t1 \r\n"
					+ "inner join DTL_TBL t2 on t1.OQCInspectType_Clsfc = t2.CHILD_TBL_NO \r\n"
					+ "inner join DTL_TBL t3 on t1.OQCInspectType_CRT = t3.CHILD_TBL_NO \r\n"
					+ "where t1.OQCInspect_OqcChNo='"+data.getOQCInspect_OqcInNo()+"'";			
			
			String remark = "";
			String OQCInspectType_IQty = "";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs2 = pstmt.executeQuery();
			while (rs2.next())
			{
				OQCInspectType_IQty = rs2.getString("OQCInspectType_IQty");
				if(rs2.getString("OQCInspectType_DQty").equals("0"))
					continue;
				remark += rs2.getString("remark")+" ";
			}
			data.setRemark(remark);
			data.setOQCInspectType_IQty(OQCInspectType_IQty);
			
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
}
