package com.busience.salesLX.controller;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.salesLX.dto.Sales_OrderList_tbl;
import com.busience.salesLX.dto.Sales_OrderMaster_tbl;
import com.busience.salesLX.dto.Sales_OutMat_tbl;
import com.busience.salesLX.dto.Sales_StockMat_tbl;

@RestController("salesOutputLXRestController")
@RequestMapping("salesOutputLXRest")
public class salesOutputLXRestController {

	@Autowired
	DataSource dataSource;

	// SO_Search
	@RequestMapping(value = "/SO_Search", method = RequestMethod.GET)
	public List<Sales_OrderMaster_tbl> SO_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println("FO_Search obj = " + obj);

		String sql = "select \r\n" + "somt.Sales_Order_mCus_No,\r\n" + "somt.Sales_Order_mCode,\r\n"
				+ "sct.Cus_Name Sales_Order_mName,\r\n"
				+ "date_format(somt.Sales_Order_mDate,'%Y-%m-%d %T') Sales_Order_mDate,\r\n"
				+ "somt.Sales_Order_mTotal,\r\n" + "somt.Sales_Order_mDlvry_Date,\r\n"
				+ "somt.Sales_Order_mRemarks,\r\n" + "somt.Sales_Order_mModifier,\r\n"
				+ "date_format(somt.Sales_Order_mModify_Date,'%Y-%m-%d %T') Sales_Order_mModify_Date,\r\n"
				+ "somt.Sales_Order_mCheck\r\n" + "from Sales_OrderMasterLX_tbl somt \r\n"
				+ "inner join Customer_tbl sct on somt.Sales_Order_mCode = sct.Cus_Code";

		String where = " where somt.Sales_Order_mDlvry_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59'";

		if (obj.get("sales_Order_mCode") != null && !obj.get("sales_Order_mCode").equals("")) {
			where += " and Sales_Order_mCode like '%" + obj.get("sales_Order_mCode") + "%'";
		}

		if (obj.get("sales_Order_mCus_No") != null && !obj.get("sales_Order_mCus_No").equals("")) {
			where += " and Sales_Order_mCus_No like '%" + obj.get("sales_Order_mCus_No") + "%'";
		}
		sql += where;
		System.out.println("where = " + where);
		System.out.println("SO_Search = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_OrderMaster_tbl> list = new ArrayList<Sales_OrderMaster_tbl>();

		while (rs.next()) {
			Sales_OrderMaster_tbl data = new Sales_OrderMaster_tbl();

			data.setSales_Order_mCus_No(rs.getString("Sales_Order_mCus_No"));
			data.setSales_Order_mCode(rs.getString("sales_Order_mCode"));
			data.setSales_Order_mName(rs.getString("sales_Order_mName"));
			data.setSales_Order_mDate(rs.getString("sales_Order_mDate"));
			data.setSales_Order_mTotal(rs.getInt("Sales_Order_mTotal"));
			data.setSales_Order_mDlvry_Date(rs.getString("sales_Order_mDlvry_Date"));
			data.setSales_Order_mRemarks(rs.getString("sales_Order_mRemarks"));
			data.setSales_Order_mModifier(rs.getString("sales_Order_mModifier"));
			data.setSales_Order_mModify_Date(rs.getString("sales_Order_mModify_Date"));
			data.setSales_Order_mCheck(rs.getString("sales_Order_mCheck"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SOS_Search
	@RequestMapping(value = "/SOS_Search", method = RequestMethod.GET)
	public List<Sales_OrderList_tbl> SOS_Search(
			@RequestParam(value = "Sales_Order_lCus_No", required = false) String Sales_Order_lCus_No)
			throws SQLException {
		List<Sales_OrderList_tbl> list = new ArrayList<Sales_OrderList_tbl>();

		String sql = "select \r\n" + "solt.Sales_Order_lNo,\r\n" + "solt.Sales_Order_lCus_No,\r\n"
				+ "pit.PRODUCT_ITEM_NAME Sales_Order_lName,\r\n" + "solt.Sales_Order_lCode,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_Order_STND_1,\r\n" + "solt.Sales_Order_lQty,\r\n"
				+ "solt.Sales_Order_lSum,\r\n" + "solt.Sales_Order_lUnit_Price,\r\n" + "solt.Sales_Order_lPrice,\r\n"
				+ "solt.Sales_Order_lNot_Stocked,\r\n"
				+ "(ssmt.Sales_SM_Last_Qty+ssmt.Sales_SM_In_Qty-ssmt.Sales_SM_Out_Qty) Sales_SM_Last_Qty,\r\n"
				+ "solt.Sales_Order_Send_Clsfc\r\n" + "from Sales_OrderListLX_tbl solt \r\n"
				+ "inner join PRODUCT_INFO_TBL pit on solt.Sales_Order_lCode = pit.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join Sales_StockMatLX_tbl ssmt on solt.Sales_Order_lCode = ssmt.Sales_SM_Code\r\n";

		String where = " where Sales_Order_lCus_No = '" + Sales_Order_lCus_No + "'" + " order by solt.Sales_Order_lNo";

		sql += where;
		System.out.println("SOS_Search = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_OrderList_tbl data = new Sales_OrderList_tbl();
			data.setSales_Order_lNo(rs.getInt("Sales_Order_lNo"));
			data.setSales_Order_lCus_No(rs.getString("sales_Order_lCus_No"));
			data.setSales_Order_lCode(rs.getString("sales_Order_lCode"));
			data.setSales_Order_lName(rs.getString("sales_Order_lName"));
			data.setSales_Order_STND_1(rs.getString("sales_Order_STND_1"));
			data.setSales_Order_lQty(rs.getInt("Sales_Order_lQty"));
			data.setSales_Order_lSum(rs.getInt("Sales_Order_lSum"));
			data.setSales_Order_lUnit_Price(rs.getInt("Sales_Order_lUnit_Price"));
			data.setSales_Order_lPrice(rs.getInt("Sales_Order_lPrice"));
			data.setSales_Order_lNot_Stocked(rs.getInt("Sales_Order_lNot_Stocked"));
			data.setSales_SM_Last_Qty(rs.getInt("sales_SM_Last_Qty"));
			data.setSales_Order_Send_Clsfc(rs.getString("sales_Order_Send_Clsfc"));

			list.add(data);

		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;

	}

	// SS_Search
	@RequestMapping(value = "/SS_Search", method = RequestMethod.GET)
	public List<Sales_StockMat_tbl> SS_Search(
			@RequestParam(value = "sales_SM_Code", required = false) String sales_SM_Code) throws SQLException {
		List<Sales_StockMat_tbl> list = new ArrayList<Sales_StockMat_tbl>();

		String SS_Search = "select\r\n" + "A.Sales_SM_Code,\r\n" + "B.PRODUCT_ITEM_NAME,\r\n"
				+ "B.PRODUCT_INFO_STND_1,\r\n"
				+ "A.Sales_SM_Last_Qty + A.Sales_SM_In_Qty - A.Sales_SM_Out_Qty Sales_SM_Qty,\r\n"
				+ "A.Sales_SM_Save_Area\r\n" + "from Sales_StockMatLX_tbl A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on A.Sales_SM_Code = B.PRODUCT_ITEM_CODE";

		SS_Search += " where Sales_SM_Code = '" + sales_SM_Code
				+ "' and A.Sales_SM_Last_Qty + A.Sales_SM_In_Qty - A.Sales_SM_Out_Qty > 0";

		System.out.println("SS_Search = " + SS_Search);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(SS_Search);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_StockMat_tbl data = new Sales_StockMat_tbl();

			data.setSales_SM_Code(rs.getString("Sales_SM_Code"));
			data.setSales_SM_Name(rs.getString("PRODUCT_ITEM_NAME"));
			data.setSales_SM_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setSales_SM_Qty(rs.getInt("Sales_SM_Qty"));
			data.setSales_SM_Save_Area(rs.getString("Sales_SM_Save_Area"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SOM_Search
	@RequestMapping(value = "/SOM_Search", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SOM_Search(HttpServletRequest request) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String SOM_Search = "select \r\n" + "somt.Sales_OutMat_No,\r\n" + "somt.Sales_OutMat_Cus_No,\r\n"
				+ "somt.Sales_OutMat_Code,\r\n" + "pit.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n"
				+ "somt.Sales_OutMat_Qty,\r\n" + "somt.Sales_OutMat_Unit_Price,\r\n" + "somt.Sales_OutMat_Price,\r\n"
				+ "somt.Sales_OutMat_Client_Code, " + "cut.Cus_Name Sales_OutMat_Client_Name,\r\n"
				+ "somt.Sales_OutMat_Send_Clsfc,\r\n" + "somt.Sales_OutMat_Date \r\n"
				+ "from Sales_OutMat_tbl somt \r\n"
				+ "inner join PRODUCT_INFO_TBL pit on somt.Sales_OutMat_Code = pit.PRODUCT_ITEM_CODE"
				+ " inner join Customer_tbl cut on somt.Sales_OutMat_Client_Code = cut.Cus_Code";

		String where = " where somt.Sales_OutMat_Cus_No = '" + obj.get("sales_OutMat_Cus_No")
				+ "' and somt.Sales_OutMat_Code = '" + obj.get("sales_OutMat_Code") + "'"
				+ " and somt.Sales_OutMat_Date >= curdate()" + " order by somt.Sales_OutMat_No";

		SOM_Search += where;
		System.out.println("SS_Search = " + SOM_Search);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(SOM_Search);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();

			data.setSales_OutMat_Cus_No(rs.getString("sales_OutMat_Cus_No"));
			data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
			data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
			data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
			data.setSales_OutMat_Unit_Price(rs.getInt("sales_OutMat_Unit_Price"));
			data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));
			data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
			data.setSales_OutMat_Client_Name(rs.getString("sales_OutMat_Client_Name"));
			data.setSales_OutMat_Send_Clsfc(rs.getString("sales_OutMat_Send_Clsfc"));
			data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SOM_Save
	@RequestMapping(value = "/SOM_Save", method = RequestMethod.POST)
	public String SOM_Save(HttpServletRequest request, Model model)
			throws ParseException, SQLException, UnknownHostException, ClassNotFoundException {
		JSONParser parser = new JSONParser();

		// masterData
		String masterData = request.getParameter("masterData");
		JSONObject masterobj = (JSONObject) parser.parse(masterData);
		System.out.println(masterobj);

		// listData
		String listData = request.getParameter("listData");
		JSONArray listarr = (JSONArray) parser.parse(listData);

		HttpSession session = request.getSession();
		String modifier = (String) session.getAttribute("id");

		String OutMat_No_sql = null;
		String OutMat_No = null;
		String Sales_OrderList_update = null;
		String sales_OutMat_insert = null;
		String sales_Stock_update = null;
		String Sales_OrderMaster_update = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			// Sales_OrderList_tbl
			for (int i = 0; i < listarr.size(); i++) {
				JSONObject listobj = (JSONObject) listarr.get(i);
				System.out.println(listobj);

				// Sales_OutMat_tbl에 들어갈 순번을 얻어냄
				OutMat_No_sql = "select count(*)+1 from Sales_OutMatLX_tbl" + " where Sales_OutMat_Cus_No = '"
						+ masterobj.get("sales_Order_mCus_No") + "'";

				System.out.println("OutMat_No_sql = " + OutMat_No_sql);
				pstmt = conn.prepareStatement(OutMat_No_sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					OutMat_No = rs.getString(1);
				}

				// Sales_OrderList_update
				Sales_OrderList_update = " update Sales_OrderListLX_tbl set " + " Sales_Order_lSum = Sales_Order_lSum+'"
						+ listobj.get("sales_OutMat_Qty") + "', "
						+ " Sales_Order_lNot_Stocked = Sales_Order_lNot_Stocked-'" + listobj.get("sales_OutMat_Qty")
						+ "', " + " Sales_Order_Send_Clsfc = '" + listobj.get("sales_OutMat_Send_Clsfc") + "' "
						+ " where Sales_Order_lCus_No = '" + masterobj.get("sales_Order_mCus_No") + "' "
						+ " and Sales_Order_lCode = '" + listobj.get("sales_OutMat_Code") + "'\r\n";

				System.out.println("Sales_OrderList_update = " + Sales_OrderList_update);
				pstmt = conn.prepareStatement(Sales_OrderList_update);
				pstmt.executeUpdate();

				// sales_OutMat_insert
				sales_OutMat_insert = " insert into Sales_OutMatLX_tbl( " + " Sales_OutMat_No, "
						+ " Sales_OutMat_Cus_No, " + " Sales_OutMat_Code, " + " Sales_OutMat_Qty, "
						+ " Sales_OutMat_Unit_Price, " + " Sales_OutMat_Price, " + " Sales_OutMat_Client_Code, "
						+ " Sales_OutMat_Send_Clsfc, " + " Sales_OutMat_Date, " + " Sales_OutMat_dInsert_Time, "
						+ " Sales_OutMat_Modifier" + ") values ( " + "'" + OutMat_No + "', " + "'"
						+ masterobj.get("sales_Order_mCus_No") + "', " + "'" + listobj.get("sales_OutMat_Code") + "', "
						+ "'" + listobj.get("sales_OutMat_Qty") + "', " + "'" + listobj.get("sales_OutMat_Unit_Price")
						+ "', " + "'" + listobj.get("sales_OutMat_Price") + "', " + "'"
						+ masterobj.get("sales_Order_mCode") + "', " + "'" + listobj.get("sales_OutMat_Send_Clsfc")
						+ "', " + "'" + listobj.get("sales_OutMat_Date") + "', " + "now(), " + "'" + modifier
						+ "')\r\n";

				System.out.println("sales_OutMat_insert = " + sales_OutMat_insert);
				pstmt = conn.prepareStatement(sales_OutMat_insert);
				pstmt.executeUpdate();

				// sales_Stock_update
				sales_Stock_update = " update Sales_StockMatLX_tbl set" + " Sales_SM_Out_Qty = Sales_SM_Out_Qty+'"
						+ listobj.get("sales_OutMat_Qty") + "'" + " where Sales_SM_Code = '"
						+ listobj.get("sales_OutMat_Code") + "'\r\n";

				System.out.println("sales_Stock_update = " + sales_Stock_update);
				pstmt = conn.prepareStatement(sales_Stock_update);
				pstmt.executeUpdate();

				// Sales_OrderMaster_update
				Sales_OrderMaster_update = " update Sales_OrderMasterLX_tbl " + " set Sales_Order_mCheck = "
						+ " (select " + " (case " + "	when sum(Sales_Order_lNot_Stocked) = 0 then 'Y' "
						+ "	when sum(Sales_Order_lSum) = 0 then 'N' " + " 	else 'I' " + "	end) "
						+ " from Sales_OrderListLX_tbl " + " where Sales_Order_lCus_No = '"
						+ masterobj.get("sales_Order_mCus_No") + "' " + " ) " + " where Sales_Order_mCus_No = '"
						+ masterobj.get("sales_Order_mCus_No") + "'\r\n";

				System.out.println("Sales_OrderMaster_update = " + Sales_OrderMaster_update);
				pstmt = conn.prepareStatement(Sales_OrderMaster_update);
				pstmt.executeUpdate();

			}

			conn.commit();
			sql_result = "success";
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return sql_result;
	}
}
