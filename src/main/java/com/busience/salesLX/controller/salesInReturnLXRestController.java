package com.busience.salesLX.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.busience.sales.dto.Sales_InMat_tbl;

@RestController("salesInReturnLXRestController")
@RequestMapping("salesInReturnLXRest")
public class salesInReturnLXRestController {

	@Autowired
	DataSource dataSource;

	// SIRI_Search
	@RequestMapping(value = "/SIRI_Search", method = RequestMethod.GET)
	public List<Sales_InMat_tbl> SIRI_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = " SELECT \r\n" + "A.Sales_SM_Code,\r\n" + "B.PRODUCT_ITEM_NAME,\r\n"
				+ "A.Sales_SM_Last_Qty+A.Sales_SM_In_Qty-A.Sales_SM_Out_Qty Sales_SM_Qty\r\n"
				+ "FROM Sales_StockMatLX_tbl A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on A.Sales_SM_Code = B.PRODUCT_ITEM_CODE\r\n";

		if (obj.get("sales_InMat_Code") != null && !obj.get("sales_InMat_Code").equals("")) {
			sql += " where Sales_InMat_Code like '%" + obj.get("sales_InMat_Code") + "%'";
		}

		sql += " and A.Sales_SM_Last_Qty+A.Sales_SM_In_Qty-A.Sales_SM_Out_Qty > 0";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_InMat_tbl> list = new ArrayList<Sales_InMat_tbl>();

		while (rs.next()) {
			Sales_InMat_tbl data = new Sales_InMat_tbl();

			data.setSales_InMat_Code(rs.getString("Sales_SM_Code"));
			data.setSales_InMat_Name(rs.getString("PRODUCT_ITEM_NAME"));
			data.setSales_InMat_Qty(rs.getInt("Sales_SM_Qty"));
			data.setSales_InReturn_Qty(0);
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SIRI_Save
	@GetMapping("/SIRI_Save")
	public String SIRI_Save(HttpServletRequest request, Model model) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);

		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");

		String Sales_InMatLX_tbl_sql = null;
		String Sales_StockMat_tbl_sql = null;

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			for (int i = 0; i < arr.size(); i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);

				// 입고테이블에 insert
				Sales_InMatLX_tbl_sql = " insert into Sales_InMatLX_tbl(\r\n" + " Sales_InMat_Code,\r\n"
						+ " Sales_InMat_Qty,\r\n" + " Sales_InMat_Date,\r\n" + " Sales_InMat_Rcv_Clsfc,\r\n"
						+ " Sales_InMat_dInsert_Time,\r\n" + " Sales_InMat_Modifier\r\n" + " ) value (\r\n" + " '"
						+ obj.get("sales_InMat_Code") + "',\r\n" + " -" + obj.get("sales_InReturn_Qty") + ",\r\n"
						+ " now(),\r\n" + "	'175',\r\n" + " now(),\r\n" + " '" + modifier + "')";

				System.out.println("Sales_InMatLX_tbl_sql = " + Sales_InMatLX_tbl_sql);
				pstmt = conn.prepareStatement(Sales_InMatLX_tbl_sql);
				pstmt.executeUpdate();

				// StockMat테이블에 update
				Sales_StockMat_tbl_sql = " update Sales_StockMatLX_tbl set" + " Sales_SM_In_Qty = Sales_SM_In_Qty-"
						+ obj.get("sales_InReturn_Qty") + "" + " where Sales_SM_Code = '" + obj.get("sales_InMat_Code")
						+ "'";

				System.out.println("Sales_StockMat_tbl_sql = " + Sales_StockMat_tbl_sql);
				pstmt = conn.prepareStatement(Sales_StockMat_tbl_sql);
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

	// SIRS_Search
	@RequestMapping(value = "/SIRS_Search", method = RequestMethod.GET)
	public List<Sales_InMat_tbl> SIRS_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = " SELECT\r\n" + " A.Sales_InMat_Code,\r\n" + " B.PRODUCT_ITEM_NAME Sales_InMat_Name,\r\n"
				+ " A.Sales_InMat_Qty Sales_InReturn_Qty,\r\n" + " A.Sales_InMat_Rcv_Clsfc,\r\n"
				+ " C.CHILD_TBL_TYPE sales_InMat_Rcv_Clsfc_Name,\r\n"
				+ " date_format(A.Sales_InMat_Date,'%Y-%m-%d %T') Sales_InMat_Date,\r\n"
				+ " date_format(A.Sales_InMat_dInsert_Time,'%Y-%m-%d %T') Sales_InMat_dInsert_Time,\r\n"
				+ " A.Sales_InMat_Modifier\r\n" + " FROM Sales_InMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Sales_InMat_Code = B.PRODUCT_ITEM_CODE \r\n"
				+ " inner join DTL_TBL C on A.Sales_InMat_Rcv_Clsfc = C.CHILD_TBL_NO \r\n";

		String where = " where A.Sales_InMat_dInsert_Time between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59' \r\n";

		if (obj.get("sales_InMat_Code") != null && !obj.get("sales_InMat_Code").equals("")) {
			where += " and Sales_InMat_Code like '%" + obj.get("sales_InMat_Code") + "%'";
		}
		where += " and A.Sales_InMat_Rcv_Clsfc = '175'";

		sql += where;
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_InMat_tbl> list = new ArrayList<Sales_InMat_tbl>();

		int i = 0;

		while (rs.next()) {
			Sales_InMat_tbl data = new Sales_InMat_tbl();

			data.setSales_InMat_Code(rs.getString("sales_InMat_Code"));
			data.setSales_InMat_Name(rs.getString("sales_InMat_Name"));
			data.setSales_InReturn_Qty(rs.getInt("sales_InReturn_Qty"));
			data.setSales_InMat_Date(rs.getString("sales_InMat_Date"));
			data.setSales_InMat_Rcv_Clsfc(rs.getString("sales_InMat_Rcv_Clsfc"));
			data.setSales_InMat_Rcv_Clsfc_Name(rs.getString("sales_InMat_Rcv_Clsfc_Name"));
			data.setSales_InMat_dInsert_Time(rs.getString("sales_InMat_dInsert_Time"));
			data.setSales_InMat_Modifier(rs.getString("sales_InMat_Modifier"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}
}
