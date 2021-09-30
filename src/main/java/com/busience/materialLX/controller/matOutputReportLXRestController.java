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

import com.busience.materialLX.dto.OutMat_tbl;

@RestController("matOutputReportLXRestController")
@RequestMapping("matOutputReportLXRest")
public class matOutputReportLXRestController {

	@Autowired
	DataSource dataSource;

	// 출고 조회
	@RequestMapping(value = "MO_ListView", method = RequestMethod.GET)
	public List<OutMat_tbl> MO_ListView(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		String sql = "select\r\n" + " omt.OutMat_Date,\r\n" + "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
				+ " omt.OutMat_Consignee,\r\n" + " omt.OutMat_Dept_Code,\r\n"
				+ "	dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n" + "	omt.OutMat_Code,\r\n"
				+ "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n" + "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n"
				+ "	dt3.CHILD_TBL_TYPE OutMat_UNIT,\r\n" + "	omt.OutMat_Qty,\r\n" + "	omt.OutMat_Modifier,\r\n"
				+ "	omt.OutMat_dInsert_Time\r\n" + "from\r\n" + "	OutMatLX_tbl omt\r\n"
				+ "inner join DTL_TBL dt on\r\n" + "	omt.OutMat_Dept_Code = dt.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL dt2 on\r\n" + "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on\r\n" + "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DTL_TBL dt3 on\r\n" + "	pit.PRODUCT_UNIT = dt3.CHILD_TBL_NO";

		String where = " where omt.OutMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59' ";

		if (obj.get("outMat_Code") != null && !obj.get("outMat_Code").equals("")) {
			where += " and OutMat_Code like '%" + obj.get("outMat_Code") + "%'";
		}

		if (!obj.get("outMat_Send_Clsfc_Name").equals("all")) {
			where += " and OutMat_Send_Clsfc = '" + obj.get("outMat_Send_Clsfc_Name") + "'";
		}

		if (!obj.get("outMat_Dept_Name").equals("all")) {
			where += " and OutMat_Dept_Code = '" + obj.get("outMat_Dept_Name") + "'";
		}

		sql += where;

		sql += " order by omt.OutMat_Date";

		System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int iD = 0;
		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();

		while (rs.next()) {
			OutMat_tbl data = new OutMat_tbl();

			iD++;
			data.setID(iD);
			data.setOutMat_Date(rs.getString("OutMat_Date"));
			data.setOutMat_Send_Clsfc_Name(rs.getString("OutMat_Send_Clsfc_Name"));
			data.setOutMat_Consignee(rs.getString("OutMat_Consignee"));
			data.setOutMat_Dept_Code(rs.getString("OutMat_Dept_Code"));
			data.setOutMat_Dept_Name(rs.getString("OutMat_Dept_Name"));
			data.setOutMat_Code(rs.getString("OutMat_Code"));
			data.setOutMat_Name(rs.getString("OutMat_Name"));
			data.setOutMat_STND_1(rs.getString("OutMat_STND_1"));
			data.setOutMat_UNIT(rs.getString("OutMat_UNIT"));
			data.setOutMat_Qty(rs.getInt("OutMat_Qty"));
			data.setOutMat_Modifier(rs.getString("OutMat_Modifier"));
			data.setOutMat_dInsert_Time(rs.getString("OutMat_dInsert_Time"));

			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// 출고현황(품목)
	@RequestMapping(value = "/MO_ItemView", method = RequestMethod.GET)
	public List<OutMat_tbl> MO_ItemView(HttpServletRequest request) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		System.out.println(originData);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		String sql = "select\r\n" + " omt.OutMat_Date,\r\n" + "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
				+ " omt.OutMat_Consignee,\r\n" + "	omt.OutMat_Dept_Code,\r\n"
				+ "	dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n" + "	omt.OutMat_Code,\r\n"
				+ "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n" + "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n"
				+ "	dt3.CHILD_TBL_TYPE OutMat_UNIT,\r\n" + "	sum(omt.OutMat_Qty) OutMat_Qty \r\n" + " from\r\n"
				+ " OutMatLX_tbl omt\r\n" + "inner join DTL_TBL dt on \r\n"
				+ "	omt.OutMat_Dept_Code = dt.CHILD_TBL_NO\r\n" + "inner join DTL_TBL dt2 on\r\n"
				+ "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n" + "inner join PRODUCT_INFO_TBL pit on\r\n"
				+ "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n" + " left outer join DTL_TBL dt3 on\r\n"
				+ "	pit.PRODUCT_UNIT = dt3.CHILD_TBL_NO";

		String where = " where omt.OutMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59' ";

		if (obj.get("outMat_Code") != null && !obj.get("outMat_Code").equals("")) {
			where += " and OutMat_Code like '%" + obj.get("outMat_Code") + "%'";
		}

		if (!obj.get("outMat_Send_Clsfc").equals("all")) {
			where += " and OutMat_Send_Clsfc ='" + obj.get("outMat_Send_Clsfc") + "'";
		}

		sql += where;

		sql += " group by omt.OutMat_Code, OutMat_Date with rollup";

		System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();

		while (rs.next()) {

			if (rs.getString("outMat_Date") == null && rs.getString("outMat_Code") != null) {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name("Sub Total");
				data.setOutMat_Code(rs.getString("outMat_Code"));
				data.setOutMat_Name(rs.getString("outMat_Name"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);

			} else if (rs.getString("outMat_Code") == null && rs.getString("outMat_Date") == null) {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name("Grand Total");
				data.setOutMat_Code(rs.getString("outMat_Code"));
				data.setOutMat_Name(rs.getString("outMat_Name"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);

			} else {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name(rs.getString("outMat_Send_Clsfc_Name"));
				data.setOutMat_Dept_Code(rs.getString("outMat_Dept_Code"));
				data.setOutMat_Dept_Name(rs.getString("outMat_Dept_Name"));
				data.setOutMat_Consignee(rs.getString("outMat_Consignee"));
				data.setOutMat_Code(rs.getString("outMat_Code"));
				data.setOutMat_Name(rs.getString("outMat_Name"));
				data.setOutMat_STND_1(rs.getString("outMat_STND_1"));
				data.setOutMat_UNIT(rs.getString("outMat_UNIT"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;

	}

	// 출고현황(부서)
	@RequestMapping(value = "/MO_DeptView", method = RequestMethod.GET)
	public List<OutMat_tbl> MO_DeptView(HttpServletRequest request) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		String sql = "select\r\n" + " omt.OutMat_Date,\r\n" + "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
				+ "	omt.OutMat_Dept_Code,\r\n" + "	dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n"
				+ " omt.OutMat_Consignee,\r\n" + " omt.OutMat_Code,\r\n" + "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n"
				+ "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n" + "	dt3.CHILD_TBL_TYPE OutMat_UNIT,\r\n"
				+ " sum(omt.OutMat_Qty) OutMat_Qty\r\n" + " from\r\n" + " OutMatLX_tbl omt\r\n"
				+ "inner join DTL_TBL dt on\r\n" + "	omt.OutMat_Dept_Code = dt.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL dt2 on\r\n" + "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on\r\n" + "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DTL_TBL dt3 on\r\n" + "	pit.PRODUCT_UNIT = dt3.CHILD_TBL_NO";

		String where = " where omt.OutMat_Date between '" + obj.get("startDate") + " 00:00:00' and '"
				+ obj.get("endDate") + " 23:59:59' ";

		if (!obj.get("outMat_Send_Clsfc").equals("all")) {
			where += " and OutMat_Send_Clsfc = '" + obj.get("outMat_Send_Clsfc") + "'";
		}

		if (!obj.get("outMat_Dept_Name").equals("all")) {
			where += " and OutMat_Dept_Code = '" + obj.get("outMat_Dept_Name") + "'";
		}

		sql += where;

		sql += " group by omt.OutMat_Dept_Code, OutMat_Date with rollup";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();

		while (rs.next()) {

			if (rs.getString("outMat_Date") == null && rs.getString("outMat_Dept_Code") != null) {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name("Sub Total");
				data.setOutMat_Dept_Code(rs.getString("outMat_Dept_Code"));
				data.setOutMat_Dept_Name(rs.getString("OutMat_Dept_Name"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);

			} else if (rs.getString("outMat_Dept_Code") == null && rs.getString("outMat_Date") == null) {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name("Grand Total");
				data.setOutMat_Dept_Code("");
				data.setOutMat_Dept_Name("");
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);

			} else {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name(rs.getString("outMat_Send_Clsfc_Name"));
				data.setOutMat_Dept_Code(rs.getString("outMat_Dept_Code"));
				data.setOutMat_Dept_Name(rs.getString("outMat_Dept_Name"));
				data.setOutMat_Consignee(rs.getString("outMat_Consignee"));
				data.setOutMat_Code(rs.getString("outMat_Code"));
				data.setOutMat_Name(rs.getString("outMat_Name"));
				data.setOutMat_STND_1(rs.getString("outMat_STND_1"));
				data.setOutMat_UNIT(rs.getString("outMat_UNIT"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);
			}
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// MO_DeliverySearch - SM_Prcs_Date
	@RequestMapping(value = "/MO_DeliverySearch", method = RequestMethod.GET)
	public String MO_DeliverySearch(HttpServletRequest request, Model model) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		Connection conn = dataSource.getConnection();
		// 占쏙옙占� 占쏙옙占싱븝옙 처占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙치占싹댐옙 占쏙옙 占싯삼옙
		String sql = "select SM_Prcs_Date from StockMatLX_tbl where SM_Prcs_Date='" + obj.get("RawDate") + "'";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("MO_DeliverySearch_Prcs_Date : " + sql);

		String RawDate_Flag = "";

		while (rs.next()) {
			RawDate_Flag = rs.getString("SM_Prcs_Date");
		}

		System.out.println("RawDate_Flag :" + RawDate_Flag);

		if (RawDate_Flag.equals("")) {
			//System.out.println("占쏙옙占쏙옙占싱억옙");
			return "DateFormat";
		} else if (!RawDate_Flag.equals("")) {
			return "Success";
		}

		rs.close();
		pstmt.close();
		conn.close();

		return RawDate_Flag;
	}

	// MO_DeliverySearch - SM_Prcs_Date
	@RequestMapping(value = "/MO_LastMonthSearch", method = RequestMethod.GET)
	public String MO_LastMonthSearch(HttpServletRequest request, Model model) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		Connection conn = dataSource.getConnection();
		String sql = "select YM_Prcs_Date from YearMat_tbl where YM_Prcs_Date='" + obj.get("RawDate") + "'";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("MO_DeliverySearch_Prcs_Date : " + sql);

		String RawDate_Flag = "";

		while (rs.next()) {
			RawDate_Flag = rs.getString("YM_Prcs_Date");
		}

		System.out.println("RawDate_Flag :" + RawDate_Flag);

		if (RawDate_Flag.equals("")) {
			//System.out.println("占쏙옙占쏙옙占싱억옙");
			return "DateFormat";
		} else if (!RawDate_Flag.equals("")) {
			return "Success";
		}

		rs.close();
		pstmt.close();
		conn.close();

		return RawDate_Flag;
	}

	// MO_DeliveryView list
	@RequestMapping(value = "/MO_DeliveryView", method = RequestMethod.GET)
	public List<OutMat_tbl> MO_DeliveryView(HttpServletRequest request, Model model)
			throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);

		Connection conn = dataSource.getConnection();

		String sql = "select \r\n" + "OutMat_No,\r\n" + "OutMat_Dept_Code,\r\n"
				+ "dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n" + "sum(OutMat_Qty) OutMat_Qty\r\n"
				+ "from OutMatLX_tbl omt\r\n" + "inner join DTL_TBL dt on omt.OutMat_Dept_Code = dt.CHILD_TBL_NO";

		String where = " where omt.OutMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		sql += where;

		sql += " group by OutMat_Dept_Code with rollup";
		System.out.println("where : " + where);
		System.out.println(sql);

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();

		while (rs.next()) {
			if (rs.getString("outMat_Dept_Code") == null) {

				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Dept_Code("Sub Total");
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);
			} else {

				OutMat_tbl data = new OutMat_tbl();

				i++;
				data.setID(i);
				// data.setOutMat_No(rs.getInt("outMat_No"));
				data.setOutMat_Dept_Code(rs.getString("outMat_Dept_Code"));
				data.setOutMat_Dept_Name(rs.getString("outMat_Dept_Name"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);
			}

		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// MO_DeliveryView Item
	@RequestMapping(value = "/MO_DeliveryItem", method = RequestMethod.GET)
	public List<OutMat_tbl> MO_DeliveryItem(
			@RequestParam(value = "outMat_Dept_Code", required = false) String OutMat_Dept_Code,
			HttpServletRequest request) throws ParseException, SQLException {
		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String sql = "select\r\n" + "	omt.OutMat_No,\r\n" + "	omt.OutMat_Date,\r\n"
				+ "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n" + "	omt.OutMat_Code,\r\n"
				+ "	omt.OutMat_Dept_Code,\r\n" + "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n"
				+ "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n" + "	dt3.CHILD_TBL_TYPE OutMat_UNIT,\r\n"
				+ "	sum(omt.OutMat_Qty) OutMat_Qty\r\n" + "from\r\n" + "	OutMatLX_tbl omt\r\n"
				+ "inner join DTL_TBL dt2 on\r\n" + "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on\r\n" + "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DTL_TBL dt3 on\r\n" + "	pit.PRODUCT_UNIT = dt3.CHILD_TBL_NO";

		String where = " where omt.OutMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		where += " and omt.OutMat_Dept_Code='" + obj.get("outMat_Dept_Code") + "'";

		sql += where;

		sql += " group by omt.OutMat_Code, omt.OutMat_Date with rollup";
		System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("---");
		while (rs.next()) {
			if (rs.getString("outMat_Date") == null && (rs.getString("outMat_Code") != null)) {

				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date("Sub Total");
				data.setOutMat_Code(rs.getString("outMat_Code"));
				data.setOutMat_Name(rs.getString("outMat_Name"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);

			} else if (rs.getString("outMat_Code") == null && rs.getString("outMat_Date") == null) {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date("Grand Total");
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

			} else {

				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_No(rs.getInt("outMat_No"));
				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name(rs.getString("outMat_Send_Clsfc_Name"));
				data.setOutMat_Code(rs.getString("outMat_Code"));
				data.setOutMat_Dept_Code(rs.getString("outMat_Dept_Code"));
				data.setOutMat_Name(rs.getString("outMat_Name"));
				data.setOutMat_STND_1(rs.getString("outMat_STND_1"));
				data.setOutMat_UNIT(rs.getString("outMat_UNIT"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				System.out.println(data.toString());

				list.add(data);
			}

		}
		System.out.println("---");
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	// 부서별명세서(전월)
	@RequestMapping(value = "/MO_DeliveryLastItem", method = RequestMethod.GET)
	public List<OutMat_tbl> MO_DeliveryLastItem(
			@RequestParam(value = "outMat_Dept_Code", required = false) String OutMat_Dept_Code,
			HttpServletRequest request) throws ParseException, SQLException {
		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String sql = "select\r\n" + "	omt.OutMat_No,\r\n" + "	omt.OutMat_Date,\r\n"
				+ "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n" + "	omt.OutMat_Code,\r\n"
				+ "	omt.OutMat_Dept_Code,\r\n" + "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n"
				+ "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n" + "	dt3.CHILD_TBL_TYPE OutMat_UNIT,\r\n"
				+ "	sum(omt.OutMat_Qty) OutMat_Qty\r\n" + "from\r\n" + "	OutMatLX_tbl omt\r\n"
				+ "inner join DTL_TBL dt2 on\r\n" + "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n"
				+ "inner join PRODUCT_INFO_TBL pit on\r\n" + "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DTL_TBL dt3 on\r\n" + "	pit.PRODUCT_UNIT = dt3.CHILD_TBL_NO";

		String where = " where omt.OutMat_Date between '" + obj.get("PrcsDate") + "01 00:00:00' and '"
				+ obj.get("PrcsDate") + obj.get("LastDay") + " 23:59:59' ";

		where += " and omt.OutMat_Dept_Code='" + obj.get("outMat_Dept_Code") + "'";

		sql += where;

		sql += " group by omt.OutMat_Code, omt.OutMat_Date with rollup";
		System.out.println("where : " + where);
		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		System.out.println("---");
		while (rs.next()) {
			if (rs.getString("outMat_Date") == null && (rs.getString("outMat_Code") != null)) {

				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date("Sub Total");
				data.setOutMat_Code(rs.getString("outMat_Code"));
				data.setOutMat_Name(rs.getString("outMat_Name"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);

			} else if (rs.getString("outMat_Code") == null && rs.getString("outMat_Date") == null) {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date("Grand Total");
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

			} else {

				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_No(rs.getInt("outMat_No"));
				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name(rs.getString("outMat_Send_Clsfc_Name"));
				data.setOutMat_Code(rs.getString("outMat_Code"));
				data.setOutMat_Dept_Code(rs.getString("outMat_Dept_Code"));
				data.setOutMat_Name(rs.getString("outMat_Name"));
				data.setOutMat_STND_1(rs.getString("outMat_STND_1"));
				data.setOutMat_UNIT(rs.getString("outMat_UNIT"));
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				System.out.println(data.toString());

				list.add(data);
			}

		}
		System.out.println("---");
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
}
