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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.salesLX.dto.Sales_OutMat_tbl;

@RestController("salesDeliveryReportLXRestController")
@RequestMapping("salesDeliveryReportLXRest")
public class salesDeliveryReportLXRestController {

	@Autowired
	DataSource dataSource;

	// SOC_ListView
	@RequestMapping(value = "/SOCL_Search", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SOCL_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "select \r\n" + "somt.Sales_OutMat_Date,\r\n" + "dt.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc,\r\n"
				+ "somt.Sales_OutMat_Client_Code,\r\n" + "ct.Cus_Name Sales_OutMat_Client_Name,\r\n"
				+ "somt.Sales_OutMat_Code,\r\n" + "pit.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n"
				+ "pit.PRODUCT_INFO_STND_1 Sales_OutMat_STND_1,\r\n" + "dt2.CHILD_TBL_TYPE Sales_OutMat_UNIT,\r\n"
				+ "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty,\r\n"
				+ "sum(somt.Sales_OutMat_Price) Sales_OutMat_Price \r\n " + "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join DTL_TBL dt on somt.Sales_OutMat_Send_Clsfc = dt.CHILD_TBL_NO\r\n"
				+ "inner join Customer_tbl ct on somt.Sales_OutMat_Client_Code  = ct.Cus_Code\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on somt.Sales_OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "inner join DTL_TBL dt2 on pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO";

		String where = " where somt.Sales_OutMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59'";

		if (obj.get("sales_OutMat_Client_Code") != null && !obj.get("sales_OutMat_Client_Code").equals("")) {
			where += " and Sales_OutMat_Client_Code like '%" + obj.get("sales_OutMat_Client_Code") + "%'";
		}

		if (!obj.get("sales_OutMat_Send_Clsfc").equals("all")) {
			where += " and Sales_OutMat_Send_Clsfc = '" + obj.get("sales_OutMat_Send_Clsfc") + "'";
		}

		sql += where;

		sql += " group by somt.Sales_OutMat_Client_Code, somt.Sales_OutMat_Date with rollup";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();

			if (rs.getString("sales_OutMat_Date") == null && rs.getString("sales_OutMat_Client_Code") != null) {

				data.setSales_OutMat_Send_Clsfc("Sub Total");
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);

			} else if (rs.getString("sales_OutMat_Client_Code") == null && rs.getString("sales_OutMat_Date") == null) {

				data.setSales_OutMat_Send_Clsfc("Grand Total");
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				data.setSales_OutMat_Client_Code("");
				data.setSales_OutMat_Name("");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);

			} else {

				i++;
				data.setID(i);
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				if (obj.get("sales_OutMat_Send_Clsfc").equals("all")) {
					data.setSales_OutMat_Send_Clsfc("all");
				} else {
					data.setSales_OutMat_Send_Clsfc(rs.getString("sales_OutMat_Send_Clsfc"));
				}
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_STND_1(rs.getString("sales_OutMat_STND_1"));
				data.setSales_OutMat_UNIT(rs.getString("sales_OutMat_UNIT"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SOC_DeliverySearch - SM_Prce_Date
	@RequestMapping(value = "/SOC_DeliverySearch", method = RequestMethod.GET)
	public String SOC_DeliverySearch(HttpServletRequest request, Model model) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		Connection conn = dataSource.getConnection();
		// prcs_date search
		String sql = "select Sales_SM_Prcs_Date from Sales_StockMatLX_tbl where Sales_SM_Prcs_Date ='"
				+ obj.get("RawDate") + "'";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("prcs_date search = " + sql);

		String RawDate_Flag = "";

		while (rs.next()) {
			RawDate_Flag = rs.getString("Sales_SM_Prcs_Date");
		}

		System.out.println("RawDate_Flag = " + RawDate_Flag);

		if (RawDate_Flag.equals("")) {
			System.out.println("error");
			return "DateFormat";
		} else if (!RawDate_Flag.equals("")) {
			return "Success";
		}

		rs.close();
		pstmt.close();
		conn.close();

		return RawDate_Flag;

	}

	// SOC_DeliverySearch - SM_Last_Month
	@RequestMapping(value = "/SOC_LastMonthSearch", method = RequestMethod.GET)
	public String SOC_DeliveryLastMonthSearch(HttpServletRequest request, Model model)
			throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		Connection conn = dataSource.getConnection();
		// prcs_date search
		String sql = "select Sales_YM_Prcs_Date from Sales_YearMat_tbl where Sales_YM_Prcs_Date ='" + obj.get("RawDate")
				+ "'";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("prcs_date search = " + sql);

		String RawDate_Flag = "";

		while (rs.next()) {
			RawDate_Flag = rs.getString("Sales_YM_Prcs_Date");
		}

		System.out.println("RawDate_Flag = " + RawDate_Flag);

		if (RawDate_Flag.equals("")) {
			System.out.println("error");
			return "DateFormat";
		} else if (!RawDate_Flag.equals("")) {
			return "Success";
		}

		rs.close();
		pstmt.close();
		conn.close();

		return RawDate_Flag;

	}

	// SOC_DeliveryView - list
	@RequestMapping(value = "/SOC_DeliveryView", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SOC_DeliveryView(HttpServletRequest request, Model model)
			throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		Connection conn = dataSource.getConnection();

		String sql = "select \r\n" + "somt.Sales_OutMat_No,\r\n" + "somt.Sales_OutMat_Client_Code,\r\n"
				+ "ct.Cus_Name Sales_OutMat_Client_Name,\r\n" + "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty\r\n"
				+ "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join Customer_tbl ct on somt.Sales_OutMat_Client_Code = ct.Cus_Code";

		String where = " where somt.Sales_OutMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		sql += where;

		sql += " group by Sales_OutMat_Client_Code with rollup";

		System.out.println("where = " + where);
		System.out.println(sql);

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();

		while (rs.next()) {
			if (rs.getString("sales_OutMat_Client_Code") == null) {
				Sales_OutMat_tbl data = new Sales_OutMat_tbl();

				data.setSales_OutMat_Client_Code("Sub Total");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));

				list.add(data);
			} else {
				Sales_OutMat_tbl data = new Sales_OutMat_tbl();

				i++;
				data.setID(i);
				// data.setSales_OutMat_No(rs.getInt("sales_OutMat_No"));
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Client_Name(rs.getString("sales_OutMat_Client_Name"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SOC_DeliveryCustomer
	@RequestMapping(value = "/SOC_DeliveryCustomer", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SOC_DeliveryCustomer(
			@RequestParam(value = "sales_OutMat_Client_Code", required = false) String sales_OutMat_Client_Code,
			HttpServletRequest request) throws SQLException, ParseException {
		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "select \r\n" + "somt.Sales_OutMat_No,\r\n" + "somt.Sales_OutMat_Cus_No, "
				+ "somt.Sales_OutMat_Date,\r\n" + "dt.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc_Name,\r\n"
				+ "somt.Sales_OutMat_Code,\r\n" + "somt.Sales_OutMat_Client_Code,\r\n"
				+ "pit.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n" + "pit.PRODUCT_INFO_STND_1 Sales_OutMat_STND_1,\r\n"
				+ "dt2.CHILD_TBL_TYPE Sales_OutMat_UNIT,\r\n" + "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty,\r\n"
				+ "sum(somt.Sales_OutMat_Price) Sales_OutMat_Price \r\n " + "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join DTL_TBL dt on somt.Sales_OutMat_Send_Clsfc = dt.CHILD_TBL_NO\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on somt.Sales_OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "inner join DTL_TBL dt2 on pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO";

		String where = " where somt.Sales_OutMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		where += " and somt.Sales_OutMat_Client_Code ='" + obj.get("sales_OutMat_Client_Code") + "'";

		sql += where;

		sql += " group by somt.Sales_OutMat_Cus_No, somt.Sales_OutMat_Date with rollup";

		System.out.println("where = " + where);
		System.out.println("거래처 당월 = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();
			if (rs.getString("sales_OutMat_Date") == null && (rs.getString("sales_OutMat_Cus_No") != null)) {

				data.setSales_OutMat_Send_Clsfc_Name("Sub Total");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);
				
			} else if (rs.getString("sales_OutMat_Cus_No") == null && rs.getString("sales_OutMat_Date") == null) {
				
				data.setSales_OutMat_Send_Clsfc_Name("Grand Total");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);

			} else {

				data.setSales_OutMat_Cus_No(rs.getString("sales_OutMat_Cus_No"));
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				data.setSales_OutMat_Send_Clsfc_Name(rs.getString("sales_OutMat_Send_Clsfc_Name"));
				data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_STND_1(rs.getString("sales_OutMat_STND_1"));
				data.setSales_OutMat_UNIT(rs.getString("sales_OutMat_UNIT"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));
				data.setSales_OutMat_No(rs.getInt("sales_OutMat_No"));

				list.add(data);
			}

		}

		if (list.size() > 0) {
			list.get(list.size() - 1).setSales_OutMat_Send_Clsfc_Name("");
			list.get(list.size() - 1).setSales_OutMat_Code("");
			list.get(list.size() - 1).setSales_OutMat_Name("");
			list.get(list.size() - 1).setSales_OutMat_STND_1("");
			list.get(list.size() - 1).setSales_OutMat_UNIT("");
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// SOC_DeliveryLastCustomer
	@RequestMapping(value = "/SOC_DeliveryLastCustomer", method = RequestMethod.GET)
	public List<Sales_OutMat_tbl> SOC_DeliveryLastCustomer(
			@RequestParam(value = "sales_OutMat_Client_Code", required = false) String sales_OutMat_Client_Code,
			HttpServletRequest request) throws SQLException, ParseException {
		List<Sales_OutMat_tbl> list = new ArrayList<Sales_OutMat_tbl>();
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "select \r\n" + "somt.Sales_OutMat_No,\r\n" + "somt.Sales_OutMat_Cus_No, "
				+ "somt.Sales_OutMat_Date,\r\n" + "dt.CHILD_TBL_TYPE Sales_OutMat_Send_Clsfc_Name,\r\n"
				+ "somt.Sales_OutMat_Code,\r\n" + "somt.Sales_OutMat_Client_Code,\r\n"
				+ "pit.PRODUCT_ITEM_NAME Sales_OutMat_Name,\r\n" + "pit.PRODUCT_INFO_STND_1 Sales_OutMat_STND_1,\r\n"
				+ "dt2.CHILD_TBL_TYPE Sales_OutMat_UNIT,\r\n" + "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty,\r\n"
				+ "sum(somt.Sales_OutMat_Price) Sales_OutMat_Price \r\n " + "from Sales_OutMatLX_tbl somt \r\n"
				+ "inner join DTL_TBL dt on somt.Sales_OutMat_Send_Clsfc = dt.CHILD_TBL_NO\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on somt.Sales_OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "inner join DTL_TBL dt2 on pit.PRODUCT_UNIT = dt2.CHILD_TBL_NO";

		String where = " where somt.Sales_OutMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		where += " and somt.Sales_OutMat_Client_Code ='" + obj.get("sales_OutMat_Client_Code") + "'";

		sql += where;

		sql += " group by somt.Sales_OutMat_Cus_No, somt.Sales_OutMat_Date with rollup";

		System.out.println("where = " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_OutMat_tbl data = new Sales_OutMat_tbl();
			if (rs.getString("sales_OutMat_Date") == null && (rs.getString("sales_OutMat_Cus_No") != null)) {

				data.setSales_OutMat_Send_Clsfc_Name("Sub Total");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);
			} else if (rs.getString("sales_OutMat_Cus_No") == null && (rs.getString("sales_OutMat_Date") == null)) {
				data.setSales_OutMat_Send_Clsfc_Name("Grand Total");
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));

				list.add(data);

			} else {

				data.setSales_OutMat_Cus_No(rs.getString("sales_OutMat_Cus_No"));
				data.setSales_OutMat_Date(rs.getString("sales_OutMat_Date"));
				data.setSales_OutMat_Send_Clsfc_Name(rs.getString("sales_OutMat_Send_Clsfc_Name"));
				data.setSales_OutMat_Code(rs.getString("sales_OutMat_Code"));
				data.setSales_OutMat_Client_Code(rs.getString("sales_OutMat_Client_Code"));
				data.setSales_OutMat_Name(rs.getString("sales_OutMat_Name"));
				data.setSales_OutMat_STND_1(rs.getString("sales_OutMat_STND_1"));
				data.setSales_OutMat_UNIT(rs.getString("sales_OutMat_UNIT"));
				data.setSales_OutMat_Qty(rs.getInt("sales_OutMat_Qty"));
				data.setSales_OutMat_Price(rs.getInt("sales_OutMat_Price"));
				data.setSales_OutMat_No(rs.getInt("sales_OutMat_No"));

				list.add(data);
			}

		}

		if (list.size() > 0) {
			list.get(list.size() - 1).setSales_OutMat_Send_Clsfc_Name("");
			list.get(list.size() - 1).setSales_OutMat_Code("");
			list.get(list.size() - 1).setSales_OutMat_Name("");
			list.get(list.size() - 1).setSales_OutMat_STND_1("");
			list.get(list.size() - 1).setSales_OutMat_UNIT("");
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
}
