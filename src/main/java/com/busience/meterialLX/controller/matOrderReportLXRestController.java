package com.busience.meterialLX.controller;
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

import com.busience.materialLX.dto.OrderList_tbl;
import com.busience.materialLX.dto.OrderMaster_tbl;
import com.busience.materialLX.dto.StockMat_tbl;

@RestController("matOrderLXReportRestController")
@RequestMapping("matOrderLXReportRest")
public class matOrderReportLXRestController {

	@Autowired
	DataSource dataSource;

	// OrderMaster
	@RequestMapping(value = "/MOL_Search", method = RequestMethod.GET)
	public List<OrderMaster_tbl> MOL_Search(HttpServletRequest request) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String sql = "select \r\n"
				+ "A.Order_mCus_No,\r\n"
				+ "A.Order_mCode,\r\n"
				+ "B.Cus_Name Order_mName,\r\n"
				+ "date_format(A.Order_mDate,'%Y-%m-%d %T') Order_mDate,\r\n"
				+ "A.Order_mTotal,\r\n"
				+ "A.Order_mDlvry_Date,\r\n"
				+ "A.Order_mCheck,\r\n"
				+ "A.Order_mRemarks,\r\n"
				+ "A.Order_mModifier,\r\n"
				+ "date_format(A.Order_mModify_Date,'%Y-%m-%d %T') Order_mModify_Date\r\n"
				+ "from OrderMasterLX_tbl A\r\n"
				+ "inner join Customer_tbl B on A.Order_mCode = B.Cus_Code ";

		String where = "where A.Order_mDlvry_Date between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate")
				+ " 23:59:59'";

		if (obj.get("order_mCode") != null && !obj.get("order_mCode").equals("")) {
			where += " and Order_mCode like '%" + obj.get("order_mCode") + "%'";
		}

		sql += where;

		sql += " group by A.Order_mCode, A.Order_mDlvry_Date, A.Order_mCus_No, A.Order_mDate";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;

		List<OrderMaster_tbl> list = new ArrayList<OrderMaster_tbl>();

		while (rs.next()) {
			OrderMaster_tbl data = new OrderMaster_tbl();
			i++;
			data.setId(i);
			data.setOrder_mCus_No(rs.getString("order_mCus_No"));
			data.setOrder_mCode(rs.getString("order_mCode"));
			data.setOrder_mName(rs.getString("order_mName"));
			data.setOrder_mDate(rs.getString("order_mDate"));
			data.setOrder_mTotal(rs.getInt("order_mTotal"));
			data.setOrder_mDlvry_Date(rs.getString("order_mDlvry_Date"));
			data.setOrder_mCheck(rs.getString("order_mCheck"));
			data.setOrder_mRemarks(rs.getString("order_mRemarks"));
			data.setOrder_mModifier(rs.getString("order_mModifier"));
			data.setOrder_mModify_Date(rs.getString("order_mModify_Date"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;

	}

	// orderList
	@RequestMapping(value = "/MOLS_Search", method = RequestMethod.GET)
	public List<OrderList_tbl> MOLS_Search(
		@RequestParam(value = "order_lCus_No", required = false) String order_lCus_No) throws SQLException {
		List<OrderList_tbl> list = new ArrayList<OrderList_tbl>();

		String sql = "SELECT \r\n"
				+ " A.Order_lNo,\r\n"
				+ " A.Order_lCus_No,\r\n"
				+ " B.PRODUCT_ITEM_NAME Order_lName,\r\n"
				+ " A.Order_lCode,\r\n"
				+ " B.PRODUCT_INFO_STND_1 Order_STND_1,\r\n"
				+ " A.Order_lQty, \r\n"
				+ " A.Order_lUnit_Price,\r\n"
				+ " A.Order_lPrice,\r\n"
				+ " A.Order_lNot_Stocked,\r\n"
				+ " A.Order_lInfo_Remark\r\n"
				+ " FROM OrderListLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Order_lCode = B.PRODUCT_ITEM_CODE";

		String where = "  where Order_lCus_No = '" + order_lCus_No + "'";

		sql += where;
		System.out.println("MOLS_Search  = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			OrderList_tbl data = new OrderList_tbl();
			data.setOrder_lNo(rs.getInt("order_lNo"));
			data.setOrder_lCus_No(rs.getString("order_lCus_No"));
			data.setOrder_lCode(rs.getString("order_lCode"));
			data.setOrder_lName(rs.getString("order_lName"));
			data.setOrder_STND_1(rs.getString("order_STND_1"));
			data.setOrder_lQty(rs.getInt("order_lQty"));
			data.setOrder_lNot_Stocked(rs.getInt("order_lNot_Stocked"));
			data.setOrder_lUnit_Price(rs.getInt("order_lUnit_Price"));
			data.setOrder_lPrice(rs.getInt("order_lPrice"));
			data.setOrder_lInfo_Remark(rs.getString("order_lInfo_Remark"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//OrderStock
	@RequestMapping(value = "/MOLSS_Search", method = RequestMethod.GET)
	public List<StockMat_tbl> MOLSS_Search(
		@RequestParam(value = "order_lCode", required = false) String order_lCode) throws SQLException {
		List<StockMat_tbl> list = new ArrayList<StockMat_tbl>();

		String sql = " SELECT \r\n"
				+ " A.SM_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME SM_Name,\r\n"
				+ " B.PRODUCT_INFO_STND_1 SM_STND_1,\r\n"
				+ " A.SM_Out_Qty-A.SM_In_Qty SM_Qty\r\n"
				+ " FROM StockMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B ON A.SM_Code = B.PRODUCT_ITEM_CODE";
		
		String where = " where A.SM_Code = '"+order_lCode+"'";
		
		sql += where;
		System.out.println("MOLSS_Search ="+sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int i = 0;
		
		while (rs.next()) {
			StockMat_tbl data = new StockMat_tbl();
			i++;
			data.setId(i);
			data.setSM_Code(rs.getString("sm_Code"));
			data.setSM_Name(rs.getString("sm_Name"));
			data.setSM_STND_1(rs.getString("sm_STND_1"));
			data.setSM_Qty(rs.getInt("sm_Qty"));
			
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
}