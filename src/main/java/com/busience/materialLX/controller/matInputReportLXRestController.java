package com.busience.materialLX.controller;

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

import com.busience.materialLX.dto.InMat_tbl;
import com.busience.materialLX.dto.StockMat_tbl;

@RestController("matInputReportLXRestController")
@RequestMapping("matInputReportLXRest")
public class matInputReportLXRestController {

	@Autowired
	DataSource dataSource;

	// 입고 조회
	@RequestMapping(value = "/MI_ListView", method = RequestMethod.GET)
	public List<InMat_tbl> MI_ListView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		String sql = "select \r\n" + "A.InMat_Date, \r\n" + "F.CHILD_TBL_TYPE InMat_Rcv_Clsfc, \r\n"
				+ "A.InMat_Client_Code, \r\n" + "B.Cus_Name InMat_Client_Name, \r\n" + "A.InMat_Code, \r\n"
				+ "C.PRODUCT_ITEM_NAME InMat_Name, \r\n" + "C.PRODUCT_INFO_STND_1 InMat_STND_1, \r\n"
				+ "E.CHILD_TBL_TYPE InMat_UNIT, \r\n" + "A.InMat_Qty, \r\n" + "A.InMat_Unit_Price, \r\n"
				+ "A.InMat_Price, \r\n" + "A.InMat_No, \r\n" + "D.Order_lInfo_Remark InMat_Info_Remark, \r\n"
				+ "A.InMat_Order_No, \r\n" + "A.InMat_Modifier, \r\n" + "A.InMat_dInsert_Time  \r\n"
				+ "from InMatLX_tbl A \r\n" + "inner join Customer_tbl B \r\n"
				+ "on A.InMat_Client_Code = B.Cus_Code \r\n" + "inner join PRODUCT_INFO_TBL C \r\n"
				+ "on A.InMat_Code = C.PRODUCT_ITEM_CODE \r\n" + "inner join OrderListLX_tbl D\r\n"
				+ "on A.InMat_Order_No = D.Order_lCus_No and A.InMat_Code = D.Order_lCode \r\n"
				+ "left outer join DTL_TBL E\r\n" + "on C.PRODUCT_UNIT = E.CHILD_TBL_NO\r\n"
				+ "left outer join DTL_TBL F\r\n" + "on A.InMat_Rcv_Clsfc = F.CHILD_TBL_NO ";

		String where = " where A.InMat_Date between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate")
				+ " 23:59:59' ";

		if (obj.get("inMat_Client_Name") != null && !obj.get("inMat_Client_Name").equals("")) {
			where += " and Cus_Name like '%" + obj.get("inMat_Client_Name") + "%'";
		}

		if (obj.get("inMat_Code") != null && !obj.get("inMat_Code").equals("")) {
			where += " and InMat_Code like '%" + obj.get("inMat_Code") + "%'";
		}

		System.out.println(!obj.get("inMat_Rcv_Clsfc").equals("all"));
		if (!obj.get("inMat_Rcv_Clsfc").equals("all")) {

			where += " and InMat_Rcv_Clsfc ='" + obj.get("inMat_Rcv_Clsfc") + "'";
		}

		sql += where;

		sql += " order by A.InMat_Date";
		System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();

		while (rs.next()) {
			InMat_tbl data = new InMat_tbl();

			i++;
			data.setID(i);
			data.setInMat_Date(rs.getString("inMat_Date"));
			data.setInMat_Rcv_Clsfc(rs.getString("inMat_Rcv_Clsfc"));
			data.setInMat_Client_Code(rs.getString("inMat_Client_Code"));
			data.setInMat_Client_Name(rs.getString("inMat_Client_Name"));
			data.setInMat_Code(rs.getString("inMat_Code"));
			data.setInMat_Name(rs.getString("inMat_Name"));
			data.setInMat_STND_1(rs.getString("inMat_STND_1"));
			data.setInMat_UNIT(rs.getString("inMat_UNIT"));
			data.setInMat_Qty(rs.getInt("inMat_Qty"));
			data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
			data.setInMat_Price(rs.getInt("inMat_Price"));
			data.setInMat_Info_Remark(rs.getString("inMat_Info_Remark"));
			data.setInMat_Order_No(rs.getString("inMat_Order_No"));
			data.setInMat_Modifier(rs.getString("inMat_Modifier"));
			data.setInMat_dInsert_Time(rs.getString("inMat_dInsert_Time"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// 입고 현황(품목)
	@RequestMapping(value = "/MI_ItemView", method = RequestMethod.GET)
	public List<InMat_tbl> MI_ItemView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		String sql = "select \r\n" + "A.InMat_Date, \r\n" + "F.CHILD_TBL_TYPE InMat_Rcv_Clsfc, \r\n"
				+ "A.InMat_Client_Code, \r\n" + "B.Cus_Name InMat_Client_Name, \r\n" + "A.InMat_Code, \r\n"
				+ "C.PRODUCT_ITEM_NAME InMat_Name, \r\n" + "C.PRODUCT_INFO_STND_1 InMat_STND_1, \r\n"
				+ "E.CHILD_TBL_TYPE InMat_UNIT, \r\n" + "sum(A.InMat_Qty) InMat_Qty, \r\n"
				+ "sum(A.InMat_Unit_Price) InMat_Unit_Price, \r\n" + "sum(A.InMat_Price) InMat_Price, \r\n"
				+ "A.InMat_No, \r\n" + "A.InMat_Order_No\r\n" + "from InMatLX_tbl A \r\n"
				+ "inner join Customer_tbl B \r\n" + "on A.InMat_Client_Code = B.Cus_Code \r\n"
				+ "inner join PRODUCT_INFO_TBL C \r\n" + "on A.InMat_Code = C.PRODUCT_ITEM_CODE \r\n"
				+ "inner join OrderListLX_tbl D\r\n" + "on A.InMat_Order_No = D.Order_lCus_No \r\n"
				+ "left outer join DTL_TBL E\r\n" + "on C.PRODUCT_UNIT = E.CHILD_TBL_NO\r\n"
				+ "left outer join DTL_TBL F\r\n" + "on A.InMat_Rcv_Clsfc = F.CHILD_TBL_NO";

		String where = " where A.InMat_Date between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate")
				+ " 23:59:59' ";

		if (obj.get("inMat_Code") != null && !obj.get("inMat_Code").equals("")) {
			where += " and InMat_Code like '%" + obj.get("inMat_Code") + "%'";
		}

		if (!obj.get("inMat_Rcv_Clsfc").equals("all")) {
			where += " and InMat_Rcv_Clsfc ='" + obj.get("inMat_Rcv_Clsfc") + "'";
		}

		sql += where;

		sql += " group by InMat_Code, InMat_Date with rollup";

		// System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();

		while (rs.next()) {

			if (rs.getString("inMat_Date") == null && rs.getString("inMat_Code") != null) {
				InMat_tbl data = new InMat_tbl();

				data.setInMat_Rcv_Clsfc("Sub Total");
				data.setInMat_Code(rs.getString("inMat_Code"));
				data.setInMat_Name(rs.getString("inMat_Name"));
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);

			} else if (rs.getString("inMat_Code") == null && rs.getString("inMat_Date") == null) {
				InMat_tbl data = new InMat_tbl();

				data.setInMat_Rcv_Clsfc("Grand Total");
				data.setInMat_Code("");
				data.setInMat_Name("");
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);

			} else {

				InMat_tbl data = new InMat_tbl();

				i++;
				data.setID(i);
				// data.setInMat_No(rs.getString("InMat_No"));
				data.setInMat_Date(rs.getString("inMat_Date"));
				data.setInMat_Rcv_Clsfc(rs.getString("inMat_Rcv_Clsfc"));
				data.setInMat_Client_Code(rs.getString("inMat_Client_Code"));
				data.setInMat_Client_Name(rs.getString("inMat_Client_Name"));
				data.setInMat_Code(rs.getString("inMat_Code"));
				data.setInMat_Name(rs.getString("inMat_Name"));
				data.setInMat_STND_1(rs.getString("inMat_STND_1"));
				data.setInMat_UNIT(rs.getString("inMat_UNIT"));
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));
				data.setInMat_Order_No(rs.getString("inMat_Order_No"));

				list.add(data);
			}

		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// 입고 현황(거래처)
	@RequestMapping(value = "/MI_CustomerView", method = RequestMethod.GET)
	public List<InMat_tbl> MI_CustomerView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		String sql = "select \r\n" + "A.InMat_Date, \r\n" + "F.CHILD_TBL_TYPE InMat_Rcv_Clsfc, \r\n"
				+ "A.InMat_Client_Code, \r\n" + "B.Cus_Name InMat_Client_Name, \r\n" + "A.InMat_Code, \r\n"
				+ "C.PRODUCT_ITEM_NAME InMat_Name, \r\n" + "C.PRODUCT_INFO_STND_1 InMat_STND_1, \r\n"
				+ "E.CHILD_TBL_TYPE InMat_UNIT, \r\n" + "sum(A.InMat_Qty) InMat_Qty, \r\n"
				+ "sum(A.InMat_Unit_Price) InMat_Unit_Price, \r\n" + "sum(A.InMat_Price) InMat_Price, \r\n"
				+ "A.InMat_No, \r\n" + "A.InMat_Order_No\r\n" + "from InMatLX_tbl A \r\n"
				+ "inner join Customer_tbl B \r\n" + "on A.InMat_Client_Code = B.Cus_Code \r\n"
				+ "inner join PRODUCT_INFO_TBL C \r\n" + "on A.InMat_Code = C.PRODUCT_ITEM_CODE \r\n"
				+ "inner join OrderListLX_tbl D\r\n" + "on A.InMat_Order_No = D.Order_lCus_No \r\n"
				+ "left outer join DTL_TBL E\r\n" + "on C.PRODUCT_UNIT = E.CHILD_TBL_NO\r\n"
				+ "left outer join DTL_TBL F\r\n" + "on A.InMat_Rcv_Clsfc = F.CHILD_TBL_NO";

		String where = " where A.InMat_Date between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate")
				+ " 23:59:59' ";

		if (obj.get("inMat_Client_Code") != null && !obj.get("inMat_Client_Code").equals("")) {
			where += " and InMat_Client_Code like '%" + obj.get("inMat_Client_Code") + "%'";
		}

		if (!obj.get("inMat_Rcv_Clsfc").equals("all")) {
			where += " and InMat_Rcv_Clsfc ='" + obj.get("inMat_Rcv_Clsfc") + "'";
		}

		sql += where;

		sql += " group by InMat_Client_Code, InMat_Date with rollup";

		// System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();

		while (rs.next()) {

			if (rs.getString("inMat_Date") == null && rs.getString("inMat_Client_Code") != null) {
				InMat_tbl data = new InMat_tbl();

				data.setInMat_Rcv_Clsfc("Sub Total");
				data.setInMat_Client_Code(rs.getString("inMat_Client_Code"));
				data.setInMat_Client_Name(rs.getString("inMat_Client_Name"));
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);

			} else if (rs.getString("inMat_Client_Code") == null && rs.getString("inMat_Date") == null) {
				InMat_tbl data = new InMat_tbl();

				data.setInMat_Rcv_Clsfc("Grand Total");
				data.setInMat_Client_Code("");
				data.setInMat_Client_Name("");
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);

			} else {

				InMat_tbl data = new InMat_tbl();

				i++;
				data.setID(i);
				data.setInMat_No(rs.getInt("inMat_No"));
				data.setInMat_Date(rs.getString("inMat_Date"));
				data.setInMat_Rcv_Clsfc(rs.getString("inMat_Rcv_Clsfc"));
				data.setInMat_Client_Code(rs.getString("inMat_Client_Code"));
				data.setInMat_Client_Name(rs.getString("inMat_Client_Name"));
				data.setInMat_Code(rs.getString("inMat_Code"));
				data.setInMat_Name(rs.getString("inMat_Name"));
				data.setInMat_STND_1(rs.getString("inMat_STND_1"));
				data.setInMat_UNIT(rs.getString("inMat_UNIT"));
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));
				data.setInMat_Order_No(rs.getString("inMat_Order_No"));

				list.add(data);
			}

		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// 납품명세서(당월 - 처리연월)
	@RequestMapping(value = "/MI_DeliverySearch", method = RequestMethod.GET)
	public String MI_DeliverySearch(HttpServletRequest request, Model model) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		Connection conn = dataSource.getConnection();
		// 占쏙옙占� 占쏙옙占싱븝옙 처占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙치占싹댐옙 占쏙옙 占싯삼옙
		String sql = "select SM_Prcs_Date from StockMatLX_tbl where SM_Prcs_Date='" + obj.get("RawDate") + "'";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		// System.out.println("처占쏙옙占쏙옙占쏙옙 占싯삼옙 : " + sql);

		String RawDate_Flag = "";

		while (rs.next()) {
			RawDate_Flag = rs.getString("SM_Prcs_Date");
		}

		// System.out.println("RawDate_Flag :" + RawDate_Flag);

		if (RawDate_Flag.equals("")) {
			// System.out.println("占쏙옙占쏙옙占싱억옙");
			return "DateFormat";
		} else if (!RawDate_Flag.equals("")) {
			return "Success";
		}

		rs.close();

		return RawDate_Flag;

	}

	// 납품명세서(전월 - 처리연월)
	@RequestMapping(value = "/MI_LastMonthSearch", method = RequestMethod.GET)
	public String MI_LastMonthSearch(HttpServletRequest request, Model model) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		Connection conn = dataSource.getConnection();
		// 占쏙옙占� 占쏙옙占싱븝옙 처占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙치占싹댐옙 占쏙옙 占싯삼옙
		String sql = "select YM_Prcs_Date from YearMat_tbl where YM_Prcs_Date='" + obj.get("RawDate") + "'";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		// System.out.println("처占쏙옙占쏙옙占쏙옙 占싯삼옙 : " + sql);

		String RawDate_Flag = "";
		String sql_result = null;

		while (rs.next()) {
			RawDate_Flag = rs.getString("YM_Prcs_Date");
		}

		// System.out.println("RawDate_Flag :" + RawDate_Flag);

		if (RawDate_Flag.equals("")) {
			System.out.println("error");
			sql_result = "DateFormat";
		} else if (!RawDate_Flag.equals("")) {
			sql_result = "Success";
		}

		rs.close();

		return sql_result;

	}

	// 납품명세서(당월)
	@RequestMapping(value = "/MI_DeliveryView", method = RequestMethod.GET)
	public List<InMat_tbl> MI_DeliveryView(HttpServletRequest request, StockMat_tbl stockMat, Model model)
			throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		Connection conn = dataSource.getConnection();

		String sql = "select \r\n" + "A.InMat_No,\r\n" + "A.InMat_Client_Code,\r\n"
				+ "B.Cus_Name InMat_Client_Name,\r\n" + "A.InMat_Qty,\r\n" + "sum(A.InMat_Price) InMat_Price \r\n"
				+ "from InMat_tbl A\r\n" + "inner join Customer_tbl B\r\n" + "on A.InMat_Client_Code = B.Cus_Code \r\n";

		String where = " where A.InMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		if (obj.get("DateSearch") != null && !obj.get("DateSearch").equals("")) {
			where += " and Order_mCus_No like '%" + obj.get("order_mCus_No") + "%'";
		}

		sql += where;

		sql += " group by InMat_Client_Code with rollup";
		// System.out.println("where : " + where);
		System.out.println("MI_DeliveryView = " + sql);

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();

		while (rs.next()) {

			if (rs.getString("inMat_Client_Code") == null) {
				InMat_tbl data = new InMat_tbl();

				// data.setInMat_No("");
				data.setInMat_Client_Code("Sub Total");
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);

			} else {
				InMat_tbl data = new InMat_tbl();

				i++;
				data.setID(i);
				// data.setInMat_No(rs.getString("inMat_No"));
				data.setInMat_Client_Code(rs.getString("inMat_Client_Code"));
				data.setInMat_Client_Name(rs.getString("inMat_Client_Name"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);
			}

		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// 납품명세서(당월)
	@RequestMapping(value = "/MI_DeliveryItem", method = RequestMethod.GET)
	public List<InMat_tbl> MI_DeliveryItem(
			@RequestParam(value = "inMat_Client_Code", required = false) String InMat_Client_Code,
			HttpServletRequest request) throws ParseException, SQLException {
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String sql = "select \r\n" + "A.InMat_No,\r\n" + "A.InMat_Date, \r\n" + "E.CHILD_TBL_TYPE InMat_Rcv_Clsfc,\r\n"
				+ "A.InMat_Code,\r\n" + "A.InMat_Client_Code,\r\n" + "C.PRODUCT_ITEM_NAME InMat_Name,\r\n"
				+ "C.PRODUCT_INFO_STND_1 InMat_STND_1,\r\n" + "D.CHILD_TBL_TYPE InMat_UNIT,\r\n"
				+ "sum(A.InMat_Qty) InMat_Qty,\r\n" + "sum(A.InMat_Unit_Price) InMat_Unit_Price, \r\n"
				+ "sum(A.InMat_Price) InMat_Price \r\n" + "from InMatLX_tbl A\r\n" + "inner join PRODUCT_INFO_TBL C\r\n"
				+ "on A.InMat_Code = C.PRODUCT_ITEM_CODE \r\n" + "left outer join DTL_TBL D\r\n"
				+ "on C.PRODUCT_UNIT = D.CHILD_TBL_NO\r\n" + "left outer join DTL_TBL E\r\n"
				+ "on A.InMat_Rcv_Clsfc = E.CHILD_TBL_NO";

		String where = " where A.InMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		where += " and InMat_Client_Code='" + obj.get("inMat_Client_Code") + "'";

		sql += where;

		sql += " group by InMat_Code with rollup";
		// System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			if (rs.getString("inMat_Code") == null) {
				InMat_tbl data = new InMat_tbl();

				data.setInMat_Code("Sub Total");
				// data.setInMat_Date("");
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);

			} else {
				InMat_tbl data = new InMat_tbl();

				data.setInMat_No(rs.getInt("inMat_No"));
				data.setInMat_Date(rs.getString("inMat_Date"));
				data.setInMat_Rcv_Clsfc(rs.getString("inMat_Rcv_Clsfc"));
				data.setInMat_Code(rs.getString("inMat_Code"));
				data.setInMat_Client_Code(rs.getString("inMat_Client_Code"));
				data.setInMat_Name(rs.getString("inMat_Name"));
				data.setInMat_STND_1(rs.getString("inMat_STND_1"));
				data.setInMat_UNIT(rs.getString("inMat_UNIT"));
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	// 납품명세서(전월)
	@RequestMapping(value = "/MI_DeliveryLastItem", method = RequestMethod.GET)
	public List<InMat_tbl> MI_DeliveryLastItem(
			@RequestParam(value = "inMat_Client_Code", required = false) String InMat_Client_Code,
			HttpServletRequest request) throws ParseException, SQLException {
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String sql = "select \r\n" + "A.InMat_No,\r\n" + "A.InMat_Date, \r\n" + "E.CHILD_TBL_TYPE InMat_Rcv_Clsfc,\r\n"
				+ "A.InMat_Code,\r\n" + "A.InMat_Client_Code,\r\n" + "C.PRODUCT_ITEM_NAME InMat_Name,\r\n"
				+ "C.PRODUCT_INFO_STND_1 InMat_STND_1,\r\n" + "D.CHILD_TBL_TYPE InMat_UNIT,\r\n"
				+ "sum(A.InMat_Qty) InMat_Qty,\r\n" + "sum(A.InMat_Unit_Price) InMat_Unit_Price, \r\n"
				+ "sum(A.InMat_Price) InMat_Price \r\n" + "from InMat_tbl A\r\n" + "inner join PRODUCT_INFO_TBL C\r\n"
				+ "on A.InMat_Code = C.PRODUCT_ITEM_CODE \r\n" + "left outer join DTL_TBL D\r\n"
				+ "on C.PRODUCT_UNIT = D.CHILD_TBL_NO\r\n" + "left outer join DTL_TBL E\r\n"
				+ "on A.InMat_Rcv_Clsfc = E.CHILD_TBL_NO";

		String where = " where A.InMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		where += " and InMat_Client_Code='" + obj.get("inMat_Client_Code") + "'";

		sql += where;

		sql += " group by InMat_Code with rollup";
		// System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			if (rs.getString("inMat_Code") == null) {
				InMat_tbl data = new InMat_tbl();

				data.setInMat_Code("Sub Total");
				// data.setInMat_Date("");
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);

			} else {
				InMat_tbl data = new InMat_tbl();

				data.setInMat_No(rs.getInt("inMat_No"));
				data.setInMat_Date(rs.getString("inMat_Date"));
				data.setInMat_Rcv_Clsfc(rs.getString("inMat_Rcv_Clsfc"));
				data.setInMat_Code(rs.getString("inMat_Code"));
				data.setInMat_Client_Code(rs.getString("inMat_Client_Code"));
				data.setInMat_Name(rs.getString("inMat_Name"));
				data.setInMat_STND_1(rs.getString("inMat_STND_1"));
				data.setInMat_UNIT(rs.getString("inMat_UNIT"));
				data.setInMat_Qty(rs.getInt("inMat_Qty"));
				data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
				data.setInMat_Price(rs.getInt("inMat_Price"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
}
