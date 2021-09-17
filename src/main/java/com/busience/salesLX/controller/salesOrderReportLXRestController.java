package com.busience.salesLX.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.salesLX.dto.Sales_OrderList_tbl;
import com.busience.salesLX.dto.Sales_OrderMaster_tbl;
import com.busience.salesLX.dto.Sales_StockMat_tbl;

@RestController("salesOrderReportLXRestController")
@RequestMapping("salesOrderReportLXRest")
public class salesOrderReportLXRestController {

	@Autowired
	DataSource dataSource;

	// salesOrderList
	@RequestMapping(value = "/SOL_Search", method = RequestMethod.GET)
	public List<Sales_OrderMaster_tbl> SOL_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "select\r\n" + "somt.Sales_Order_mCus_No,\r\n" + "somt.Sales_Order_mCode,\r\n"
				+ "ct.Cus_Name Sales_Order_mName,\r\n" + "somt.Sales_Order_mDate,\r\n" + "somt.Sales_Order_mTotal,\r\n"
				+ "somt.Sales_Order_mDlvry_Date,\r\n" + "somt.Sales_Order_mCheck,\r\n"
				+ "somt.Sales_Order_mRemarks,\r\n" + "somt.Sales_Order_mModifier,\r\n"
				+ "somt.Sales_Order_mModify_Date\r\n" + "from Sales_OrderMasterLX_tbl somt \r\n"
				+ "inner join Customer_tbl ct on somt.Sales_Order_mCode = ct.Cus_Code";

		String where = " where somt.Sales_Order_mDlvry_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59'";

		if (obj.get("sales_Order_mCode") != null && !obj.get("sales_Order_mCode").equals("")) {
			where += " and Sales_Order_mCode like '%" + obj.get("sales_Order_mCode") + "%'";
		}

		if (obj.get("sales_Order_mCheck").equals("Y")) {
			where += " and Sales_Order_mCheck = 'Y'";
		} else if (obj.get("sales_Order_mCheck").equals("N")) {
			where += " and Sales_Order_mCheck in ('I','N')";
		}

		sql += where;

		sql += " group by somt.Sales_Order_mCode, somt.Sales_Order_mDlvry_Date, somt.Sales_Order_mCus_No, somt.Sales_Order_mDate";
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_OrderMaster_tbl> list = new ArrayList<Sales_OrderMaster_tbl>();

		while (rs.next()) {
			Sales_OrderMaster_tbl data = new Sales_OrderMaster_tbl();

			data.setSales_Order_mCus_No(rs.getString("sales_Order_mCus_No"));
			data.setSales_Order_mCode(rs.getString("sales_Order_mCode"));
			data.setSales_Order_mName(rs.getString("sales_Order_mName"));
			data.setSales_Order_mDate(rs.getString("sales_Order_mDate"));
			data.setSales_Order_mTotal(rs.getInt("sales_Order_mTotal"));
			data.setSales_Order_mDlvry_Date(rs.getString("sales_Order_mDlvry_Date"));
			data.setSales_Order_mCheck(rs.getString("sales_Order_mCheck"));
			data.setSales_Order_mRemarks(rs.getString("sales_Order_mRemarks"));
			data.setSales_Order_mModifier(rs.getString("sales_Order_mModifier"));
			data.setSales_Order_mModify_Date(rs.getString("sales_Order_mModify_Date"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// salesOrderListSub
	@RequestMapping(value = "/SOLS_Search", method = RequestMethod.GET)
	public List<Sales_OrderList_tbl> SOLS_Search(
			@RequestParam(value = "sales_Order_lCus_No", required = false) String sales_Order_lCus_No)
			throws SQLException {
		List<Sales_OrderList_tbl> list = new ArrayList<Sales_OrderList_tbl>();

		String sql = "select \r\n" + "solt.Sales_Order_lNo,\r\n" + "solt.Sales_Order_lCus_No,\r\n"
				+ "pit.PRODUCT_ITEM_NAME Sales_Order_lName,\r\n" + "solt.Sales_Order_lCode,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_Order_STND_1,\r\n" + "solt.Sales_Order_lQty,\r\n"
				+ "solt.Sales_Order_lNot_Stocked,\r\n" + "solt.Sales_Order_lUnit_Price,\r\n"
				+ "solt.Sales_Order_lPrice,\r\n" + "solt.Sales_Order_lInfo_Remark,\r\n"
				+ "solt.Sales_Order_Send_Clsfc\r\n" + "from Sales_OrderListLX_tbl solt \r\n"
				+ "inner join PRODUCT_INFO_TBL pit on solt.Sales_Order_lCode = pit.PRODUCT_ITEM_CODE";

		String where = " where solt.Sales_Order_lCus_No = '" + sales_Order_lCus_No + "'"
				+ " order by Sales_Order_lNo asc";

		sql += where;
		System.out.println("SOLS_Search = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_OrderList_tbl data = new Sales_OrderList_tbl();

			data.setSales_Order_lNo(rs.getInt("sales_Order_lNo"));
			data.setSales_Order_lCus_No(rs.getString("sales_Order_lCus_No"));
			data.setSales_Order_lCode(rs.getString("sales_Order_lCode"));
			data.setSales_Order_lName(rs.getString("sales_Order_lName"));
			data.setSales_Order_STND_1(rs.getString("sales_Order_STND_1"));
			data.setSales_Order_lQty(rs.getInt("sales_Order_lQty"));
			data.setSales_Order_lNot_Stocked(rs.getInt("sales_Order_lNot_Stocked"));
			data.setSales_Order_lUnit_Price(rs.getInt("sales_Order_lUnit_Price"));
			data.setSales_Order_lPrice(rs.getInt("sales_Order_lPrice"));
			data.setSales_Order_lInfo_Remark(rs.getString("sales_Order_lInfo_Remark"));
			data.setSales_Order_Send_Clsfc(rs.getString("Sales_Order_Send_Clsfc"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// salesOrderStock
	@RequestMapping(value = "/SOSS_Search", method = RequestMethod.GET)
	public List<Sales_StockMat_tbl> SOSS_Search(
			@RequestParam(value = "sales_Order_lCode", required = false) String sales_Order_lCode) throws SQLException {
		List<Sales_StockMat_tbl> list = new ArrayList<Sales_StockMat_tbl>();

		String sql = "select \r\n" + "ssmt.Sales_SM_Code,\r\n" + "pit.PRODUCT_ITEM_NAME Sales_SM_Name,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_SM_STND_1,\r\n"
				+ "ssmt.Sales_SM_Last_Qty+ssmt.Sales_SM_In_Qty-ssmt.Sales_SM_Out_Qty Sales_SM_Qty\r\n"
				+ "from Sales_StockMatLX_tbl ssmt \r\n"
				+ "inner join PRODUCT_INFO_TBL pit on ssmt.Sales_SM_Code = pit.PRODUCT_ITEM_CODE";

		String where = " where ssmt.Sales_SM_Code = '" + sales_Order_lCode + "'";

		sql += where;
		System.out.println("SOSS_Search = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_StockMat_tbl data = new Sales_StockMat_tbl();

			data.setSales_SM_Code(rs.getString("sales_SM_Code"));
			data.setSales_SM_Name(rs.getString("sales_SM_Name"));
			data.setSales_SM_STND_1(rs.getString("sales_SM_STND_1"));
			data.setSales_SM_Qty(rs.getInt("sales_SM_Qty"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;

	}
}
