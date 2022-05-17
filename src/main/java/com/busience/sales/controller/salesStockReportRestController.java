package com.busience.sales.controller;

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

import com.busience.sales.dto.Sales_LotMaster_tbl;

@RestController("salesStockReportRestController")
@RequestMapping("salesStockReportRest")
public class salesStockReportRestController {

	@Autowired
	DataSource dataSource;

	// FS_ListView (LOT)
	@RequestMapping(value = "FS_ListView", method = RequestMethod.GET)
	public List<Sales_LotMaster_tbl> FS_ListView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "select\r\n" + "	dt.CHILD_TBL_TYPE Sales_LMaster_ItemClsfc_1,\r\n"
				+ "	slmt.Sales_LMaster_ItemCode,\r\n" + "	slmt.Sales_LMaster_LotNo,\r\n"
				+ "	pit.PRODUCT_ITEM_NAME Sales_LMaster_ItemName,\r\n"
				+ "	pit.PRODUCT_INFO_STND_1 Sales_LMaster_ItemSTND_1,\r\n"
				+ "	dt2.CHILD_TBL_TYPE Sales_LMaster_ItemUNIT,\r\n" + "	slmt2.SLM_Qty Sales_LMaster_SLMQty,\r\n"
				+ "	slmt.Sales_LMaster_LotNo,\r\n" + "	slmt.Sales_LMaster_InQty\r\n" + "from\r\n"
				+ "	Sales_LotMaster_tbl slmt\r\n" + "inner join PRODUCT_INFO_TBL pit on\r\n"
				+ "	slmt.Sales_LMaster_ItemCode = pit.PRODUCT_ITEM_CODE\r\n" + "left outer join DTL_TBL dt on\r\n"
				+ "	pit.PRODUCT_ITEM_CLSFC_1 = dt.CHILD_TBL_NO\r\n" + "left outer join DTL_TBL dt2 on\r\n"
				+ "	pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO\r\n" + "left outer join (\r\n" + "	select\r\n"
				+ "		Sales_LMaster_ItemCode,\r\n" + "		sum(Sales_LMaster_InQty) SLM_Qty\r\n" + "	from\r\n"
				+ "		Sales_LotMaster_tbl\r\n" + "	group by\r\n" + "		Sales_LMaster_ItemCode) slmt2 on\r\n"
				+ "	slmt.Sales_LMaster_ItemCode = slmt2.Sales_LMaster_ItemCode";

		String where = " where slmt.Sales_LMaster_InQty > 0";

		if (obj.get("sales_LMaster_LotNo") != null && !obj.get("sales_LMaster_LotNo").equals("")) {
			where += " and slmt.Sales_LMaster_LotNo like '%" + obj.get("sales_LMaster_LotNo") + "%'";
		}

		if (obj.get("sales_LMaster_ItemCode") != null && !obj.get("sales_LMaster_ItemCode").equals("")) {
			where += " and slmt.Sales_LMaster_ItemCode like '%" + obj.get("sales_LMaster_ItemCode") + "%'";
		}

		sql += where;

		sql += " order by slmt.Sales_LMaster_ItemCode, slmt.Sales_LMaster_LotNo ";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int iD = 0;
		String itemNull = null;
		List<Sales_LotMaster_tbl> list = new ArrayList<Sales_LotMaster_tbl>();

		while (rs.next()) {

			if (!rs.getString("sales_LMaster_ItemCode").equals(itemNull)) {
				Sales_LotMaster_tbl data = new Sales_LotMaster_tbl();

				iD++;
				data.setID(iD);
				data.setSales_LMaster_ItemClsfc_1(rs.getString("sales_LMaster_ItemClsfc_1"));
				data.setSales_LMaster_ItemCode(rs.getString("sales_LMaster_ItemCode"));
				data.setSales_LMaster_ItemName(rs.getString("sales_LMaster_ItemName"));
				data.setSales_LMaster_ItemSTND_1(rs.getString("sales_LMaster_ItemSTND_1"));
				data.setSales_LMaster_SLMQty(rs.getInt("sales_LMaster_SLMQty"));
				data.setSales_LMaster_LotNo(rs.getString("sales_LMaster_LotNo"));
				data.setSales_LMaster_InQty(rs.getInt("sales_LMaster_InQty"));

				itemNull = rs.getString("sales_LMaster_ItemCode");
				list.add(data);

			} else {
				Sales_LotMaster_tbl data = new Sales_LotMaster_tbl();

				iD++;
				data.setID(iD);
				data.setSales_LMaster_LotNo(rs.getString("sales_LMaster_LotNo"));
				data.setSales_LMaster_InQty(rs.getInt("sales_LMaster_InQty"));

				list.add(data);
			}

		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// FS_ItemListView (Item)
	@RequestMapping(value = "FS_ItemListView", method = RequestMethod.GET)
	public List<Sales_LotMaster_tbl> FS_ItemListView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "SELECT dt.CHILD_TBL_TYPE Sales_SM_ItemClsfc_1, \r\n" + "smt.Sales_SM_Code, \r\n"
				+ "pit.PRODUCT_ITEM_NAME Sales_SM_Name, \r\n" + "pit.PRODUCT_INFO_STND_1 Sales_SM_ItemSTND_1, \r\n"
				+ "dt2.CHILD_TBL_TYPE Sales_SM_ItemUNIT, \r\n"
				+ "Sales_SM_Last_Qty+Sales_SM_In_Qty-Sales_SM_Out_Qty Sales_SM_Qty\r\n"
				+ "FROM Sales_StockMat_tbl smt\r\n"
				+ "INNER JOIN PRODUCT_INFO_TBL pit ON smt.Sales_SM_Code = pit.PRODUCT_ITEM_CODE\r\n" + "LEFT OUTER\r\n"
				+ "JOIN DTL_TBL dt ON pit.PRODUCT_ITEM_CLSFC_1 = dt.CHILD_TBL_NO\r\n" + "LEFT OUTER\r\n"
				+ "JOIN DTL_TBL dt2 ON pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO ";

		String where = "";

		if (obj.get("sales_stockCheck").equals(false)) {
			where += " where Sales_SM_Code like '%" + obj.get("sales_LMaster_ItemCode") + "%'";
		}

		if (obj.get("sales_stockCheck").equals(true) && obj.get("sales_LMaster_ItemCode") == null
				&& obj.get("sales_LMaster_ItemCode").equals("")) {
			where += " where Sales_SM_Last_Qty+Sales_SM_In_Qty-Sales_SM_Out_Qty > 0";
		} else if (!obj.get("sales_stockCheck").equals(true)) {

		} else {
			where += " where Sales_SM_Last_Qty+Sales_SM_In_Qty-Sales_SM_Out_Qty > 0 and Sales_SM_Code like '%"
					+ obj.get("sales_LMaster_ItemCode") + "%'";
		}

		sql += where;

		sql += " group by Sales_SM_Code";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int iD = 0;
		List<Sales_LotMaster_tbl> list = new ArrayList<Sales_LotMaster_tbl>();

		while (rs.next()) {
			Sales_LotMaster_tbl data = new Sales_LotMaster_tbl();

			iD++;
			data.setID(iD);
			data.setSales_LMaster_ItemClsfc_1(rs.getString("Sales_SM_ItemClsfc_1"));
			data.setSales_LMaster_ItemCode(rs.getString("Sales_SM_Code"));
			data.setSales_LMaster_ItemName(rs.getString("Sales_SM_Name"));
			data.setSales_LMaster_ItemSTND_1(rs.getString("Sales_SM_ItemSTND_1"));
			data.setSales_LMaster_ItemUNIT(rs.getString("Sales_SM_ItemUNIT"));
			data.setSales_LMaster_InQty(rs.getInt("Sales_SM_Qty"));

			list.add(data);

		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

}
