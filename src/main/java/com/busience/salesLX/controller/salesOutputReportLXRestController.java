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

import com.busience.salesLX.dto.Sales_OutMat_tbl;

@RestController("salesOutputReportLXRestController")
@RequestMapping("salesOutputReportLXRest")
public class salesOutputReportLXRestController {

	@Autowired
	DataSource dataSource;

	// SOL_Search
	@RequestMapping(value = "/SOL_Search", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SOL_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		System.out.println(obj);

		String sql = "select \r\n" + "somt.Sales_OutMat_Date,\r\n" + "dt.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc,\r\n"
				+ "somt.Sales_OutMat_Client_Code,\r\n" + "ct.Cus_Name Sales_OutMat_Client_Name,\r\n"
				+ "somt.Sales_OutMat_Code,\r\n" + "pit.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_OutMat_STND_1,\r\n" + "dt2.CHILD_TBL_TYPE Sales_OutMat_UNIT,\r\n"
				+ "somt.Sales_OutMat_Qty,\r\n" + "somt.Sales_OutMat_Cus_No,\r\n" + "somt.Sales_OutMat_Modifier,\r\n"
				+ "somt.Sales_OutMat_dInsert_Time\r\n" + "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join DTL_TBL dt on somt.Sales_OutMat_Send_Clsfc = dt.CHILD_TBL_NO\r\n"
				+ "inner join Customer_tbl ct on somt.Sales_OutMat_Client_Code  = ct.Cus_Code\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on somt.Sales_OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "inner join DTL_TBL dt2 on pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO";

		String where = " where somt.Sales_OutMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59'";

		if (obj.get("sales_OutMat_Client_Code") != null && !obj.get("sales_OutMat_Client_Code").equals("")) {
			where += " and somt.Sales_OutMat_Client_Code like '%" + obj.get("sales_OutMat_Client_Code") + "%'";
		}

		if (obj.get("sales_OutMat_Code") != null && !obj.get("sales_OutMat_Code").equals("")) {
			where += " and somt.Sales_OutMat_Code like '%" + obj.get("sales_OutMat_Code") + "%'";
		}

		if (!obj.get("sales_OutMat_Send_Clsfc").equals("all")) {
			where += " and Sales_OutMat_Send_Clsfc = '" + obj.get("sales_OutMat_Send_Clsfc") + "'";
		}

		sql += where;

		sql += " and somt.Sales_OutMat_Date";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();

			i++;
			data.setID(i);
			data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
			data.setSales_OutMat_Send_Clsfc(rs.getString("sales_OutMat_Send_Clsfc"));
			data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
			data.setSales_OutMat_Client_Name(rs.getString("sales_OutMat_Client_Name"));
			data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
			data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
			data.setSales_OutMat_STND_1(rs.getString("sales_OutMat_STND_1"));
			data.setSales_OutMat_UNIT(rs.getString("sales_OutMat_UNIT"));
			data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
			data.setSales_OutMat_Cus_No(rs.getString("sales_OutMat_Cus_No"));
			data.setSales_OutMat_Modifier(rs.getString("sales_OutMat_Modifier"));
			data.setSales_OutMat_dInsert_Time(rs.getString("sales_OutMat_dInsert_Time"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;

	}

	// SOIV_Search
	@RequestMapping(value = "/SOIV_Search", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SOIV_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "select \r\n" + "somt.Sales_OutMat_Date,\r\n" + "dt.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc,\r\n"
				+ "somt.Sales_OutMat_Client_Code,\r\n" + "ct.Cus_Name Sales_OutMat_Client_Name,\r\n"
				+ "somt.Sales_OutMat_Code,\r\n" + "pit.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_OutMat_STND_1,\r\n" + "dt2.CHILD_TBL_TYPE Sales_OutMat_UNIT,\r\n"
				+ "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty\r\n" + "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join DTL_TBL dt on somt.Sales_OutMat_Send_Clsfc = dt.CHILD_TBL_NO\r\n"
				+ "inner join Customer_tbl ct on somt.Sales_OutMat_Client_Code  = ct.Cus_Code\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on somt.Sales_OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "inner join DTL_TBL dt2 on pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO";

		String where = " where somt.Sales_OutMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59'";

		if (obj.get("sales_OutMat_Code") != null && !obj.get("sales_OutMat_Code").equals("")) {
			where += " and somt.Sales_OutMat_Code like '%" + obj.get("sales_OutMat_Code") + "%'";
		}

		if (!obj.get("sales_OutMat_Send_Clsfc").equals("all")) {
			where += " and Sales_OutMat_Send_Clsfc = '" + obj.get("sales_OutMat_Send_Clsfc") + "'";
		}

		sql += where;

		sql += " group by somt.Sales_OutMat_Code, somt.Sales_OutMat_Date with rollup";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();

			if (rs.getString("sales_OutMat_Date") == null && rs.getString("sales_OutMat_Code") != null) {

				data.setSales_OutMat_Send_Clsfc("Sub Total");
				data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));

				list.add(data);

			} else if (rs.getString("sales_OutMat_Code") == null && rs.getString("sales_OutMat_Date") == null) {

				data.setSales_OutMat_Send_Clsfc("Grand Total");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));

				list.add(data);

			} else {

				i++;
				data.setID(i);
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				data.setSales_OutMat_Send_Clsfc(rs.getString("sales_OutMat_Send_Clsfc"));
				data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_STND_1(rs.getString("sales_OutMat_STND_1"));
				data.setSales_OutMat_UNIT(rs.getString("sales_OutMat_UNIT"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
}
