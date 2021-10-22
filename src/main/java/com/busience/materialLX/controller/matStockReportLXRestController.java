package com.busience.materialLX.controller;

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

import com.busience.standard.dto.LotMaster_tbl;

@RestController("matStockReportLXRestController")
@RequestMapping("matStockReportLXRest")
public class matStockReportLXRestController {

	@Autowired
	DataSource dataSource;

	// 현재고현황(품목)
	@RequestMapping(value = "MS_ItemListView", method = RequestMethod.GET)
	public List<LotMaster_tbl> MS_ItemListView(HttpServletRequest request) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "SELECT dt.CHILD_TBL_TYPE SM_ItemClsfc_1, \r\n"
				+ "smt.SM_Code, \r\n"
				+ "pit.PRODUCT_ITEM_NAME SM_Name, \r\n"
				+ "pit.PRODUCT_INFO_STND_1 SM_ItemSTND_1, \r\n"
				+ "dt2.CHILD_TBL_TYPE SM_ItemUNIT, \r\n"
				+ "SM_Last_Qty+SM_In_Qty-SM_Out_Qty SM_Qty\r\n"
				+ "FROM StockMatLX_tbl smt\r\n"
				+ "INNER JOIN PRODUCT_INFO_TBL pit ON smt.SM_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "LEFT OUTER JOIN DTL_TBL dt ON pit.PRODUCT_ITEM_CLSFC_1 = dt.CHILD_TBL_NO\r\n"
				+ "LEFT OUTER JOIN DTL_TBL dt2 ON pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO ";

		String where = "";

		if (obj.get("stockCheck").equals(false)) {
			where += " where SM_Code like '%" + obj.get("SM_Code") + "%'";
		}

		if (obj.get("stockCheck").equals(true) && obj.get("SM_Code") == null
				&& obj.get("SM_Code").equals("")) {
			where += " WHERE SM_Last_Qty+SM_In_Qty-SM_Out_Qty > 0";
		} else if (!obj.get("stockCheck").equals(true)) {

		} else {
			where += " where SM_Last_Qty+SM_In_Qty-SM_Out_Qty > 0 and SM_Code like '%" + obj.get("SM_Code") + "%'";
		}

		sql += where;

		sql += " group by SM_Code";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int iD = 0;
		List<LotMaster_tbl> list = new ArrayList<LotMaster_tbl>();

		while (rs.next()) {
			LotMaster_tbl data = new LotMaster_tbl();

			iD++;
			data.setID(iD);
			data.setLMaster_ItemClsfc_1(rs.getString("SM_ItemClsfc_1"));
			data.setLMaster_ItemCode(rs.getString("SM_Code"));
			data.setLMaster_ItemName(rs.getString("SM_Name"));
			data.setLMaster_ItemSTND_1(rs.getString("SM_ItemSTND_1"));
			data.setLMaster_ItemUNIT(rs.getString("SM_ItemUNIT"));
			data.setLMaster_InQty(rs.getInt("SM_Qty"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

}
