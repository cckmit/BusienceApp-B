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
import org.springframework.web.bind.annotation.RestController;

import com.busience.sales.dto.Sales_InMat_tbl;

@RestController("salesInputReportLXRestController")
@RequestMapping("salesInputReportLXRest")
public class salesInputReportLXRestController {

	@Autowired
	DataSource dataSource;

	// FI_ListView
	@RequestMapping(value = "/FIL_Search", method = RequestMethod.GET)
	public List<Sales_InMat_tbl> FI_ListView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		System.out.println(obj);

		String sql = "select\r\n" + "A.Sales_InMat_Date,\r\n" + "D.CHILD_TBL_TYPE Sales_InMat_Rcv_Clsfc,\r\n"
				+ "A.Sales_InMat_Code,\r\n" + "B.PRODUCT_ITEM_NAME Sales_InMat_Name,\r\n"
				+ "B.PRODUCT_INFO_STND_1 Sales_InMat_STND_1,\r\n" + "C.CHILD_TBL_TYPE Sales_InMat_UNIT,\r\n"
				+ "A.Sales_InMat_Qty,\r\n" + "A.Sales_InMat_Modifier,\r\n" + "A.Sales_InMat_dInsert_Time\r\n"
				+ "from Sales_InMatLX_tbl A\r\n"
				+ "inner join PRODUCT_INFO_TBL B on A.Sales_InMat_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DTL_TBL C on B.PRODUCT_UNIT = C.CHILD_TBL_NO\r\n"
				+ "left outer join DTL_TBL D on A.Sales_InMat_Rcv_Clsfc = D.CHILD_TBL_NO\r\n";

		String where = " where A.Sales_InMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59' ";

		if (obj.get("sales_InMat_Code") != null && !obj.get("sales_InMat_Code").equals("")) {
			where += " and A.Sales_InMat_Code like '%" + obj.get("sales_InMat_Code") + "%'";
		}

		if (!obj.get("sales_InMat_Rcv_Clsfc").equals("all")) {

			where += " and A.Sales_InMat_Rcv_Clsfc ='" + obj.get("sales_InMat_Rcv_Clsfc") + "'";
		}

		sql += where;

		sql += " order by A.Sales_InMat_Date";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_InMat_tbl> list = new ArrayList<Sales_InMat_tbl>();

		while (rs.next()) {
			Sales_InMat_tbl data = new Sales_InMat_tbl();

			i++;
			data.setID(i);
			data.setSales_InMat_Date(rs.getString("sales_InMat_Date"));
			data.setSales_InMat_Rcv_Clsfc(rs.getString("sales_InMat_Rcv_Clsfc"));
			data.setSales_InMat_Code(rs.getString("sales_InMat_Code"));
			data.setSales_InMat_Name(rs.getString("sales_InMat_Name"));
			data.setSales_InMat_STND_1(rs.getString("sales_InMat_STND_1"));
			data.setSales_InMat_UNIT(rs.getString("sales_InMat_UNIT"));
			data.setSales_InMat_Qty(rs.getInt("sales_InMat_Qty"));
			data.setSales_InMat_Modifier(rs.getString("sales_InMat_Modifier"));
			data.setSales_InMat_dInsert_Time(rs.getString("sales_InMat_dInsert_Time"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// FIIL_Search
	@RequestMapping(value = "/FIIL_Search", method = RequestMethod.GET)
	public List<Sales_InMat_tbl> FIIL_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println("FIIL_Search");
		System.out.println(obj);

		String sql = " select \r\n" + " A.Sales_InMat_Date, \r\n" + " C.CHILD_TBL_TYPE Sales_InMat_Rcv_Clsfc, \r\n"
				+ " A.Sales_InMat_Code, \r\n" + " B.PRODUCT_ITEM_NAME Sales_InMat_Name, \r\n"
				+ " B.PRODUCT_INFO_STND_1 Sales_InMat_STND_1, \r\n" + " D.CHILD_TBL_TYPE Sales_InMat_UNIT, \r\n"
				+ " sum(A.Sales_InMat_Qty) Sales_InMat_Qty \r\n" + " from Sales_InMatLX_tbl A \r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Sales_InMat_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ " left outer join DTL_TBL C on A.Sales_InMat_Rcv_Clsfc = C.CHILD_TBL_NO\r\n"
				+ " left outer join DTL_TBL D on B.PRODUCT_UNIT = D.CHILD_TBL_NO\r\n";

		String where = " where A.Sales_InMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59' ";

		if (obj.get("sales_InMat_Code") != null && !obj.get("sales_InMat_Code").equals("")) {
			where += " and A.Sales_InMat_Code like '%" + obj.get("sales_InMat_Code") + "%'";
		}

		if (!obj.get("sales_InMat_Rcv_Clsfc").equals("all")) {
			where += " and A.Sales_InMat_Rcv_Clsfc ='" + obj.get("sales_InMat_Rcv_Clsfc") + "'";
		}

		sql += where;

		sql += " group by A.Sales_InMat_Code, A.Sales_InMat_Date with rollup";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_InMat_tbl> list = new ArrayList<Sales_InMat_tbl>();

		while (rs.next()) {
			Sales_InMat_tbl data = new Sales_InMat_tbl();

			if (rs.getString("sales_InMat_Date") == null && rs.getString("sales_InMat_Code") != null) {

				data.setSales_InMat_Rcv_Clsfc("Sub Total");
				data.setSales_InMat_Date(rs.getString("sales_InMat_Date"));
				data.setSales_InMat_Code(rs.getString("sales_InMat_Code"));
				data.setSales_InMat_Name(rs.getString("sales_InMat_Name"));
				data.setSales_InMat_Qty(rs.getInt("sales_InMat_Qty"));

				list.add(data);

			} else if (rs.getString("sales_InMat_Code") == null && rs.getString("sales_InMat_Date") == null) {

				data.setSales_InMat_Rcv_Clsfc("Grand Total");
				data.setSales_InMat_Date(rs.getString("sales_InMat_Date"));
				data.setSales_InMat_Code("");
				data.setSales_InMat_Name("");
				data.setSales_InMat_Qty(rs.getInt("sales_InMat_Qty"));

				list.add(data);

			} else {

				i++;
				data.setID(i);
				// data.setSales_InMat_No(rs.getString("sales_InMat_No"));
				data.setSales_InMat_Date(rs.getString("sales_InMat_Date"));
				data.setSales_InMat_Rcv_Clsfc(rs.getString("sales_InMat_Rcv_Clsfc"));
				data.setSales_InMat_Code(rs.getString("sales_InMat_Code"));
				data.setSales_InMat_Name(rs.getString("sales_InMat_Name"));
				data.setSales_InMat_STND_1(rs.getString("sales_InMat_STND_1"));
				data.setSales_InMat_UNIT(rs.getString("sales_InMat_UNIT"));
				data.setSales_InMat_Qty(rs.getInt("sales_InMat_Qty"));

				list.add(data);
			}

		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

}
