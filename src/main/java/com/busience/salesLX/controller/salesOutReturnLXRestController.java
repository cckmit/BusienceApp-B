package com.busience.salesLX.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.salesLX.dto.Sales_OutMat_tbl;
import com.busience.standard.dto.EQUIPMENT_INFO_TBL;

@RestController("salesOutReturnLXRestController")
@RequestMapping("salesOutReturnLXRest")
public class salesOutReturnLXRestController {

	@Autowired
	DataSource dataSource;

	// SORI_Search
	@RequestMapping(value = "/SORI_Search", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SORI_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = " SELECT\r\n" + " A.Sales_OutMat_No,\r\n" + " A.Sales_OutMat_Cus_No,\r\n"
				+ " A.Sales_OutMat_Code,\r\n" + " sum(A.Sales_OutMat_Qty) Sales_OutMat_Qty,\r\n"
				+ " A.Sales_OutMat_Unit_Price,\r\n" + " A.Sales_OutMat_Price,\r\n"
				+ " B.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n" + " A.Sales_OutMat_Client_Code,\r\n"
				+ " C.Cus_Name Sales_OutMat_Client_Name,\r\n" + " A.Sales_OutMat_Send_Clsfc,\r\n"
				+ " D.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc_Name,\r\n"
				+ " date_format(A.Sales_OutMat_Date,'%Y-%m-%d %T') Sales_OutMat_Date,\r\n"
				+ " date_format(A.Sales_OutMat_dInsert_Time,'%Y-%m-%d %T') Sales_OutMat_dInsert_Time,\r\n"
				+ " A.Sales_OutMat_Modifier\r\n" + " FROM Sales_OutMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Sales_OutMat_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join Customer_tbl C on A.Sales_OutMat_Client_Code = C.Cus_Code\r\n"
				+ " inner join DTL_TBL D on A.Sales_OutMat_Send_Clsfc = D.CHILD_TBL_NO\r\n";

		String where = " where A.Sales_OutMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59' \r\n";

		if (obj.get("sales_OutMat_Code") != null && !obj.get("sales_OutMat_Code").equals("")) {
			where += " and Sales_OutMat_Code like '%" + obj.get("sales_OutMat_Code") + "%'";
		}

		if (obj.get("sales_OutMat_Client_Code") != null && !obj.get("sales_OutMat_Client_Code").equals("")) {
			where += " and Sales_OutMat_Client_Code like '%" + obj.get("sales_OutMat_Client_Code") + "%'";
		}

		where += " group by A.Sales_OutMat_Cus_No\r\n" + " having Sales_OutMat_Qty>0";

		sql += where;
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		int i = 0;

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();
			i++;
			data.setID(i);
			data.setSales_OutMat_No(rs.getInt("sales_OutMat_No"));
			data.setSales_OutMat_Cus_No(rs.getString("sales_OutMat_Cus_No"));
			data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
			data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
			data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
			data.setSales_OutReturn_Qty(0);
			data.setSales_OutMat_Unit_Price(rs.getInt("sales_OutMat_Unit_Price"));
			data.setSales_OutMat_Price(rs.getInt("Sales_OutMat_Price"));
			data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
			data.setSales_OutMat_Client_Name(rs.getString("sales_OutMat_Client_Name"));
			data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
			data.setSales_OutMat_Send_Clsfc(rs.getString("sales_OutMat_Send_Clsfc"));
			data.setSales_OutMat_Send_Clsfc_Name(rs.getString("sales_OutMat_Send_Clsfc_Name"));
			data.setSales_OutMat_dInsert_Time(rs.getString("sales_OutMat_dInsert_Time"));
			data.setSales_OutMat_Modifier(rs.getString("sales_OutMat_Modifier"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SORI_Save
	@GetMapping("/SORI_Save")
	public String SORI_Save(HttpServletRequest request, EQUIPMENT_INFO_TBL Equipment, Principal principal)
			throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		// 접속자 정보
		String modifier = principal.getName();

		String Sales_OutMat_tbl_sql = null;
		String Sales_StockMatLX_tbl_sql = null;

		Connection conn = null;

		PreparedStatement pstmt = null;

		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			for (int i = 0; i < arr.size(); i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);

				// Sales_OutMat_tbl에 insert
				Sales_OutMat_tbl_sql = " insert into Sales_OutMatLX_tbl(\r\n" + " Sales_OutMat_No,\r\n"
						+ " Sales_OutMat_Cus_No, \r\n" + " Sales_OutMat_Code,\r\n" + " Sales_OutMat_Qty,\r\n"
						+ "	Sales_OutMat_Unit_Price,\r\n" + " Sales_OutMat_Price,\r\n"
						+ "	Sales_OutMat_Client_Code,\r\n" + " Sales_OutMat_Date,\r\n" + " Sales_OutMat_Send_Clsfc,\r\n"
						+ " Sales_OutMat_dInsert_Time,\r\n" + " Sales_OutMat_Modifier\r\n" + " ) value (\r\n"
						+ " (select Sales_OutMat_No+1 FROM Sales_OutMatLX_tbl A" + "	where A.Sales_OutMat_Cus_No = '"
						+ obj.get("sales_OutMat_Cus_No") + "'" + "	order by Sales_OutMat_No DESC LIMIT 1),\r\n" + " '"
						+ obj.get("sales_OutMat_Cus_No") + "',\r\n" + " '" + obj.get("sales_OutMat_Code") + "',\r\n"
						+ " -" + obj.get("sales_OutReturn_Qty") + ",\r\n" + " " + obj.get("sales_OutMat_Unit_Price")
						+ ",\r\n" + " -" + obj.get("sales_OutReturn_Qty") + "*" + obj.get("sales_OutMat_Unit_Price")
						+ ",\r\n" + " '" + obj.get("sales_OutMat_Client_Code") + "',\r\n" + " '"
						+ obj.get("sales_OutMat_Date") + "',\r\n" + "	'194',\r\n" + " now(),\r\n" + " '" + modifier
						+ "')";

				System.out.println("Sales_OutMat_tbl_sql = " + Sales_OutMat_tbl_sql);
				pstmt = conn.prepareStatement(Sales_OutMat_tbl_sql);
				pstmt.executeUpdate();

				// Sales_StockMatLX_tbl에 update
				Sales_StockMatLX_tbl_sql = " update Sales_StockMatLX_tbl set" + " Sales_SM_Out_Qty = Sales_SM_Out_Qty-"
						+ obj.get("sales_OutReturn_Qty") + "" + " where Sales_SM_Code = '"
						+ obj.get("sales_OutMat_Code") + "'";

				System.out.println("Sales_StockMatLX_tbl_sql = " + Sales_StockMatLX_tbl_sql);
				pstmt = conn.prepareStatement(Sales_StockMatLX_tbl_sql);
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

	// SORS_Search
	@RequestMapping(value = "/SORS_Search", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SORS_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = " SELECT\r\n" + " A.Sales_OutMat_No,\r\n" + " A.Sales_OutMat_Cus_No,\r\n"
				+ " A.Sales_OutMat_Code,\r\n" + " A.Sales_OutMat_Qty sales_OutReturn_Qty,\r\n"
				+ " A.Sales_OutMat_Unit_Price,\r\n" + " A.Sales_OutMat_Price,\r\n"
				+ " B.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n" + " A.Sales_OutMat_Client_Code,\r\n"
				+ " C.Cus_Name Sales_OutMat_Client_Name,\r\n" + " A.Sales_OutMat_Send_Clsfc,\r\n"
				+ " D.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc_Name,\r\n"
				+ " date_format(A.Sales_OutMat_Date,'%Y-%m-%d %T') Sales_OutMat_Date,\r\n"
				+ " date_format(A.Sales_OutMat_dInsert_Time,'%Y-%m-%d %T') Sales_OutMat_dInsert_Time,\r\n"
				+ " A.Sales_OutMat_Modifier\r\n" + " FROM Sales_OutMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Sales_OutMat_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join Customer_tbl C on A.Sales_OutMat_Client_Code = C.Cus_Code\r\n"
				+ " inner join DTL_TBL D on A.Sales_OutMat_Send_Clsfc = D.CHILD_TBL_NO\r\n";

		String where = " where A.Sales_OutMat_dInsert_Time between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59' \r\n";

		if (obj.get("sales_OutMat_Code") != null && !obj.get("sales_OutMat_Code").equals("")) {
			where += " and Sales_OutMat_Code like '%" + obj.get("sales_OutMat_Code") + "%'";
		}

		if (obj.get("sales_OutMat_Client_Code") != null && !obj.get("sales_OutMat_Client_Code").equals("")) {
			where += " and Sales_OutMat_Client_Code like '%" + obj.get("sales_OutMat_Client_Code") + "%'";
		}
		where += " and Sales_OutMat_Send_Clsfc = '194'";

		sql += where;
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		int i = 0;

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();

			i++;
			data.setID(i);
			data.setSales_OutMat_No(rs.getInt("sales_OutMat_No"));
			data.setSales_OutMat_Cus_No(rs.getString("sales_OutMat_Cus_No"));
			data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
			data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
			data.setSales_OutReturn_Qty(rs.getInt("sales_OutReturn_Qty"));
			data.setSales_OutMat_Unit_Price(rs.getInt("sales_OutMat_Unit_Price"));
			data.setSales_OutMat_Price(rs.getInt("Sales_OutMat_Price"));
			data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
			data.setSales_OutMat_Client_Name(rs.getString("sales_OutMat_Client_Name"));
			data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
			data.setSales_OutMat_Send_Clsfc(rs.getString("sales_OutMat_Send_Clsfc"));
			data.setSales_OutMat_Send_Clsfc_Name(rs.getString("sales_OutMat_Send_Clsfc_Name"));
			data.setSales_OutMat_dInsert_Time(rs.getString("sales_OutMat_dInsert_Time"));
			data.setSales_OutMat_Modifier(rs.getString("sales_OutMat_Modifier"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}
}
