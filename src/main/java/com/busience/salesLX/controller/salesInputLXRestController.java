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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.sales.dto.Sales_InMat_tbl;

@RestController("salesInputLXRestController")
@RequestMapping("salesInputLXRest")
public class salesInputLXRestController {

	@Autowired
	DataSource dataSource;

	// FI_Search
	@RequestMapping(value = "/SI_Search", method = RequestMethod.GET)
	public List<Sales_InMat_tbl> SI_Search()
			throws ParseException, SQLException, org.json.simple.parser.ParseException {

		String sql = " SELECT A.*,\r\n" + " B.PRODUCT_ITEM_NAME Sales_InMat_Name\r\n" + " FROM Sales_InMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Sales_InMat_Code = B.PRODUCT_ITEM_CODE\r\n";

		String where = " where Sales_InMat_Date >= curdate()\r\n" + " order by Sales_InMat_Date desc";

		sql += where;
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_InMat_tbl> list = new ArrayList<Sales_InMat_tbl>();

		while (rs.next()) {
			Sales_InMat_tbl data = new Sales_InMat_tbl();

			data.setSales_InMat_No(rs.getInt("sales_InMat_No"));
			data.setSales_InMat_Code(rs.getString("sales_InMat_Code"));
			data.setSales_InMat_Name(rs.getString("sales_InMat_Name"));
			data.setSales_InMat_Qty(rs.getInt("sales_InMat_Qty"));
			data.setSales_InMat_Date(rs.getString("sales_InMat_Date"));
			data.setSales_InMat_Rcv_Clsfc(rs.getString("Sales_InMat_Rcv_Clsfc"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SI_Save
	@RequestMapping(value = "/SI_Save", method = RequestMethod.POST)
	public String SI_Save(HttpServletRequest request, Model model) throws ParseException, SQLException,
			UnknownHostException, ClassNotFoundException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");

		String InMat_tbl_sql = null;
		String StockMat_tbl_sql = null;

		// 입고테이블에 insert
		InMat_tbl_sql = " insert into Sales_InMatLX_tbl(\r\n" + " Sales_InMat_Code,\r\n" + " Sales_InMat_Qty,\r\n"
				+ " Sales_InMat_Date,\r\n" + " Sales_InMat_Rcv_Clsfc,\r\n" + " Sales_InMat_dInsert_Time,\r\n"
				+ " Sales_InMat_Modifier\r\n" + " ) value (\r\n" + " '" + obj.get("sales_InMat_Code") + "',\r\n" + " "
				+ obj.get("sales_InMat_Qty") + ",\r\n" + " now(),\r\n" + "	'" + obj.get("sales_InMat_Rcv_Clsfc")
				+ "',\r\n" + " now(),\r\n" + " '" + modifier + "')";

		// StockMat테이블에 update
		StockMat_tbl_sql = " update Sales_StockMatLX_tbl set" + " Sales_SM_In_Qty = Sales_SM_In_Qty+'"
				+ obj.get("sales_InMat_Qty") + "'" + " where Sales_SM_Code = '" + obj.get("sales_InMat_Code") + "'";

		Connection conn = null;

		PreparedStatement pstmt = null;

		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			System.out.println("InMat_tbl_sql = " + InMat_tbl_sql);
			pstmt = conn.prepareStatement(InMat_tbl_sql);
			pstmt.executeUpdate();

			System.out.println("StockMat_tbl_sql = " + StockMat_tbl_sql);
			pstmt = conn.prepareStatement(StockMat_tbl_sql);
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
