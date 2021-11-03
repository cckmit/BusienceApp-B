package com.busience.salesLX.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.salesLX.dto.Sales_OrderList_tbl;
import com.busience.salesLX.dto.Sales_OrderMaster_tbl;
import com.busience.salesLX.dto.Sales_StockMat_tbl;

@RestController("salesOrderLXRestController")
@RequestMapping("salesOrderLXRest")
public class salesOrderLXRestController {

	@Autowired
	DataSource dataSource;

	// salesOrder
	@RequestMapping(value = "/SO_Search", method = RequestMethod.GET)
	public List<Sales_OrderMaster_tbl> SO_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String SO_Search_sql = "select \r\n" + "somt.Sales_Order_mCus_No,\r\n" + "somt.Sales_Order_mCode,\r\n"
				+ "sct.Cus_Name Sales_Order_mName,\r\n" + "somt.Sales_Order_mDate,\r\n" + "somt.Sales_Order_mTotal,\r\n"
				+ "somt.Sales_Order_mDlvry_Date,\r\n" + "somt.Sales_Order_mRemarks,\r\n"
				+ "somt.Sales_Order_mModifier,\r\n" + "somt.Sales_Order_mModify_Date,\r\n"
				+ "somt.Sales_Order_mCheck\r\n" + "from Sales_OrderMasterLX_tbl somt \r\n"
				+ "inner join Customer_tbl sct on somt.Sales_Order_mCode = sct.Cus_Code \r\n";

		String where = " where somt.Sales_Order_mDate between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59'" + " and not Sales_Order_mCheck = 'Y'";

		if (obj.get("sales_Order_mCode") != null && !obj.get("sales_Order_mCode").equals("")) {
			where += " and Sales_Order_mCode like '%" + obj.get("sales_Order_mCode") + "%'";
		}

		SO_Search_sql += where;
		System.out.println("SO_Search_sql =" + SO_Search_sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(SO_Search_sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_OrderMaster_tbl> list = new ArrayList<Sales_OrderMaster_tbl>();

		while (rs.next()) {
			Sales_OrderMaster_tbl data = new Sales_OrderMaster_tbl();
			i++;
			data.setID(i);
			data.setSales_Order_mCus_No(rs.getString("sales_order_mCus_No"));
			data.setSales_Order_mCode(rs.getString("sales_order_mCode"));
			data.setSales_Order_mName(rs.getString("sales_order_mName"));
			data.setSales_Order_mDate(rs.getString("sales_order_mDate"));
			data.setSales_Order_mTotal(rs.getInt("sales_order_mTotal"));
			data.setSales_Order_mDlvry_Date(rs.getString("sales_order_mDlvry_Date"));
			data.setSales_Order_mRemarks(rs.getString("sales_order_mRemarks"));
			data.setSales_Order_mModifier(rs.getString("sales_order_mModifier"));
			data.setSales_Order_mModify_Date(rs.getString("sales_order_mModify_Date"));
			data.setSales_Order_mCheck(rs.getString("sales_order_mCheck"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;

	}

	// salesOrderListLX
	@RequestMapping(value = "/SOL_Search", method = RequestMethod.GET)
	public List<Sales_OrderList_tbl> SOL_Search(
			@RequestParam(value = "sales_Order_lCus_No", required = false) String sales_Order_lCus_No)
			throws SQLException {
		List<Sales_OrderList_tbl> list = new ArrayList<Sales_OrderList_tbl>();
		System.out.println(sales_Order_lCus_No);
		String SOL_Search_sql = "select \r\n" + "solt.Sales_Order_lNo,\r\n" + "solt.Sales_Order_lCus_No,\r\n"
				+ "pit.PRODUCT_ITEM_NAME Sales_Order_lName,\r\n" + "solt.Sales_Order_lCode,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_Order_STND_1,\r\n" + "solt.Sales_Order_lQty,\r\n"
				+ "solt.Sales_Order_lSum,\r\n" + "solt.Sales_Order_lUnit_Price,\r\n" + "solt.Sales_Order_lPrice,\r\n"
				+ "solt.Sales_Order_lNot_Stocked,\r\n" + "solt.Sales_Order_Send_Clsfc,\r\n"
				+ "solt.Sales_Order_lInfo_Remark\r\n" + "from Sales_OrderListLX_tbl solt \r\n"
				+ "inner join PRODUCT_INFO_TBL pit on solt.Sales_Order_lCode = pit.PRODUCT_ITEM_CODE";

		String where = " where Sales_Order_lCus_No = '" + sales_Order_lCus_No + "'" + " Order by Sales_Order_lNo";

		SOL_Search_sql += where;
		System.out.println("SOL_Search_sql = " + SOL_Search_sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(SOL_Search_sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_OrderList_tbl data = new Sales_OrderList_tbl();
			data.setSales_Order_lNo(rs.getInt("sales_order_lNo"));
			data.setSales_Order_lCus_No(rs.getString("sales_order_lCus_No"));
			data.setSales_Order_lCode(rs.getString("sales_Order_lCode"));
			data.setSales_Order_lName(rs.getString("sales_Order_lName"));
			data.setSales_Order_STND_1(rs.getString("sales_Order_STND_1"));
			data.setSales_Order_lQty(rs.getInt("sales_Order_lQty"));
			data.setSales_Order_lUnit_Price(rs.getInt("sales_Order_lUnit_Price"));
			data.setSales_Order_lPrice(rs.getInt("sales_Order_lPrice"));
			data.setSales_Order_lNot_Stocked(rs.getInt("sales_Order_lNot_Stocked"));
			data.setSales_Order_Send_Clsfc(rs.getString("sales_Order_Send_Clsfc"));
			data.setSales_Order_lInfo_Remark(rs.getString("sales_Order_lInfo_Remark"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SOS_Search
	@RequestMapping(value = "/SOS_Search", method = RequestMethod.GET)
	public List<Sales_StockMat_tbl> SOS_Search(
			@RequestParam(value = "sales_Order_lCode", required = false) String sales_Order_lCode) throws SQLException {
		List<Sales_StockMat_tbl> list = new ArrayList<Sales_StockMat_tbl>();

		String SOS_Search_sql = "select \r\n" + "ssmt.Sales_SM_Code,\r\n" + "pit.PRODUCT_ITEM_NAME Sales_SM_Name,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_SM_STND_1,\r\n"
				+ "ssmt.Sales_SM_Last_Qty+ssmt.Sales_SM_In_Qty-ssmt.Sales_SM_Out_Qty Sales_SM_Qty\r\n"
				+ "from Sales_StockMatLX_tbl ssmt \r\n"
				+ "inner join PRODUCT_INFO_TBL pit on ssmt.Sales_SM_Code = pit.PRODUCT_ITEM_CODE";

		String where = " where ssmt.Sales_SM_Code = '" + sales_Order_lCode + "'";

		SOS_Search_sql += where;
		System.out.println("SOS_Search_sql = " + SOS_Search_sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(SOS_Search_sql);
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

	// salesOrderListLX save
	@GetMapping("/SOL_Save")
	public String SOL_Save(HttpServletRequest request, Principal principal) throws ParseException, SQLException {
		JSONParser parser = new JSONParser();

		// masterData
		String masterData = request.getParameter("masterData");
		JSONObject masterobj = (JSONObject) parser.parse(masterData);
		System.out.println(masterobj);

		// listData
		String listData = request.getParameter("listData");
		JSONArray listarr = (JSONArray) parser.parse(listData);

		// 접속자 정보
		String modifier = principal.getName();

		String Cus_No_sql = null;
		String Sales_OrderMasterLX_tbl_sql = null;
		String Sales_OrderListLX_tbl_sql = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 수주번호를 알아내야함
		String Cus_No = (String) masterobj.get("sales_Order_mCus_No");

		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			// 수주번호가 없을경우 (ordermaster에 새로 저장되는경우) 수주번호를 얻어냄
			if (Cus_No.equals("")) {
				Cus_No_sql = "select concat('" + masterobj.get("sales_Order_mCode")
						+ "','-',date_format(now(),'%y%m%d'),'-'," + " lpad(" + " (select * from ("
						+ "select count(*)+1 from Sales_OrderMasterLX_tbl" + " where Sales_Order_mCode = '"
						+ masterobj.get("sales_Order_mCode") + "' and Sales_Order_mDate >= curdate()) AA),"
						+ " 2,'0'))\r\n";

				System.out.println("Cus_No_sql = " + Cus_No_sql);
				pstmt = conn.prepareStatement(Cus_No_sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					Cus_No = rs.getString(1);
				}
			}

			// Sales_OrderMasterLX_tbl
			Sales_OrderMasterLX_tbl_sql = "INSERT INTO Sales_OrderMasterLX_tbl(\r\n" + " Sales_Order_mCus_No,\r\n"
					+ " Sales_Order_mCode,\r\n" + " Sales_Order_mDate,\r\n" + " Sales_Order_mTotal,\r\n"
					+ " Sales_Order_mDlvry_Date,\r\n" + " Sales_Order_mRemarks,\r\n" + " Sales_Order_mModifier,\r\n"
					+ " Sales_Order_mModify_Date\r\n" + " )VALUES(" + " '" + Cus_No + "',\r\n" + " '"
					+ masterobj.get("sales_Order_mCode") + "',\r\n" + " '" + masterobj.get("sales_Order_mDate")
					+ "',\r\n" + " " + masterobj.get("sales_Order_mTotal") + ",\r\n" + " '"
					+ masterobj.get("sales_Order_mDlvry_Date") + "',\r\n" + " '" + masterobj.get("sales_Order_mRemarks")
					+ "',\r\n" + " '" + modifier + "',\r\n" + " now()\r\n" + " ) on duplicate key update\r\n"
					+ " Sales_Order_mDate = '" + masterobj.get("sales_Order_mDate") + "',\r\n"
					+ " Sales_Order_mTotal = '" + masterobj.get("sales_Order_mTotal") + "',\r\n"
					+ " Sales_Order_mDlvry_Date = '" + masterobj.get("sales_Order_mDlvry_Date") + "',\r\n"
					+ " Sales_Order_mRemarks = '" + masterobj.get("sales_Order_mRemarks") + "',\r\n"
					+ " Sales_Order_mModifier = '" + modifier + "',\r\n" + " Sales_Order_mModify_Date = now()";

			System.out.println("Sales_OrderMasterLX_tbl_sql = " + Sales_OrderMasterLX_tbl_sql);
			pstmt = conn.prepareStatement(Sales_OrderMasterLX_tbl_sql);
			pstmt.executeUpdate();

			
			// Sales_OrderListLX_tbl
			for (int i = 0; i < listarr.size(); i++) {
				JSONObject listobj = (JSONObject) listarr.get(i);
				System.out.println(listobj);

				Sales_OrderListLX_tbl_sql = "insert into Sales_OrderListLX_tbl(\r\n" + " Sales_Order_lNo,\r\n"
						+ " Sales_Order_lCus_No,\r\n" + " Sales_Order_lCode,\r\n" + " Sales_Order_lQty,\r\n"
						+ " Sales_Order_lSum,\r\n" + " Sales_Order_lUnit_Price,\r\n" + " Sales_Order_lPrice,\r\n"
						+ " Sales_Order_lNot_Stocked,\r\n" + " Sales_Order_Send_Clsfc,\r\n"
						+ " Sales_Order_lInfo_Remark" + ") values (\r\n" + "'" + listobj.get("sales_Order_lNo")
						+ "',\r\n" + "'" + Cus_No + "',\r\n" + "'" + listobj.get("sales_Order_lCode") + "',\r\n" + "'"
						+ listobj.get("sales_Order_lQty") + "',\r\n" + "'0',\r\n" + "'"
						+ listobj.get("sales_Order_lUnit_Price") + "',\r\n" + "'" + listobj.get("sales_Order_lPrice")
						+ "',\r\n" + "'" + listobj.get("sales_Order_lQty") + "',\r\n" + "'"
						+ listobj.get("sales_Order_Send_Clsfc") + "',\r\n" + "'"
						+ listobj.get("sales_Order_lInfo_Remark") + "'\r\n" + " ) on duplicate key\r\n" + " update"
						+ " Sales_Order_lNo = '" + listobj.get("sales_Order_lNo") + "'," + " Sales_Order_lQty = '"
						+ listobj.get("sales_Order_lQty") + "'," + " Sales_Order_lUnit_Price = '"
						+ listobj.get("sales_Order_lUnit_Price") + "'," + " Sales_Order_lPrice = '"
						+ listobj.get("sales_Order_lPrice") + "'," + " Sales_Order_lNot_Stocked = '"
						+ listobj.get("sales_Order_lQty") + "'," + " Sales_Order_Send_Clsfc = '"
						+ listobj.get("sales_Order_Send_Clsfc") + "'," + " Sales_Order_lInfo_Remark = '"
						+ listobj.get("sales_Order_lInfo_Remark") + "'";

				System.out.println("Sales_OrderListLX_tbl_sql = " + Sales_OrderListLX_tbl_sql);
				pstmt = conn.prepareStatement(Sales_OrderListLX_tbl_sql);
				pstmt.executeUpdate();
			}
			
			conn.commit();
			sql_result = Cus_No;
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return sql_result;
	}

	// salesOrderListLX delete
	@RequestMapping(value = "/SOL_Delete", method = RequestMethod.POST)
	public String SOL_Delete(HttpServletRequest request, Model model) throws ParseException, SQLException {
		JSONParser parser = new JSONParser();

		// data
		String data = request.getParameter("data");
		JSONArray arr = (JSONArray) parser.parse(data);

		String sales_Order_mCus_No = null;
		String OS_delete_sql = null;
		String OM_delete_sql = null;
		String Stock_delete_Sql = null;
		String Cus_No_sql = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			for (int i = 0; i < arr.size(); i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);

				sales_Order_mCus_No = (String) obj.get("sales_Order_lCus_No");

				// 새로추가
				OS_delete_sql = "delete from Sales_OrderListLX_tbl" + " where Sales_Order_lNo = '"
						+ obj.get("sales_Order_lNo") + "'" + " and Sales_Order_lCus_No = '"
						+ obj.get("sales_Order_lCus_No") + "'";

				System.out.println("OS_delete_sql = " + OS_delete_sql);
				pstmt = conn.prepareStatement(OS_delete_sql);
				pstmt.executeUpdate();
				
				
			}

			// master데이터에 속한 list데이터가 없는경우 master데이터도 삭제한다.
			OM_delete_sql = "delete from Sales_OrderMasterLX_tbl" + " where Sales_Order_mCus_No = '"
					+ sales_Order_mCus_No + "'" + " and not exists (SELECT * FROM Sales_OrderListLX_tbl"
					+ " WHERE sales_Order_lCus_No = '" + sales_Order_mCus_No + "'" + " )";

			// 삭제후 list 순번을 재정리 해준다.
			Cus_No_sql = " UPDATE Sales_OrderListLX_tbl A, (SELECT @rank:=0) B"
					+ " SET A.Sales_Order_lNo = @rank:=@rank+1\r\n" + " where A.Sales_Order_lCus_No = '"
					+ sales_Order_mCus_No + "';";
			

			System.out.println("OM_delete_sql = " + OM_delete_sql);
			pstmt = conn.prepareStatement(OM_delete_sql);
			pstmt.executeUpdate();

			System.out.println("Cus_No_sql = " + Cus_No_sql);
			pstmt = conn.prepareStatement(Cus_No_sql);
			pstmt.executeUpdate();

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
