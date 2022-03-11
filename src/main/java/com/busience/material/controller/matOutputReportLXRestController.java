package com.busience.material.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OutMat_tbl;
import com.busience.salesLX.dto.Sales_OutMat_tbl;

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
		//System.out.println(obj);

		String sql = "select\r\n" + " omt.OutMat_Date,\r\n" + "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
				+ " omt.OutMat_Consignee,\r\n" + " dt3.CHILD_TBL_TYPE OutMat_Consignee_Name,\r\n" + " omt.OutMat_Dept_Code,\r\n"
				+ "	dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n" + "	omt.OutMat_Code,\r\n"
				+ "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n" + "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n"
				+ "	dt4.CHILD_TBL_TYPE OutMat_UNIT,\r\n" + "	omt.OutMat_Qty,\r\n" + "	omt.OutMat_Modifier,\r\n"
				+ "	omt.OutMat_dInsert_Time\r\n" + "from\r\n" + "	OutMat_tbl omt\r\n"
				+ "inner join DTL_TBL dt on\r\n" + "	omt.OutMat_Dept_Code = dt.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL dt2 on\r\n" + "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL dt3 on\r\n" + "	omt.OutMat_Consignee = dt3.CHILD_TBL_NO\r\n"
				+ "inner join Product_Info_tbl pit on\r\n" + "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DTL_TBL dt4 on\r\n" + "	pit.PRODUCT_UNIT = dt4.CHILD_TBL_NO";

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

		//System.out.println("where : " + where);
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
			data.setOutMat_Consignee_Name(rs.getString("OutMat_Consignee_Name"));
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
	
	// 출고 조회
	@RequestMapping(value = "tablet/MO_ListView", method = RequestMethod.GET)
	public List<OutMat_tbl> tablet_MO_ListView(HttpServletRequest request) throws ParseException, SQLException {
			String originData = request.getParameter("data");
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(originData);
			//System.out.println(obj);

			String sql = "select\r\n" + " omt.OutMat_Date,\r\n" + "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
					+ " omt.OutMat_Consignee,\r\n" + " dt3.CHILD_TBL_TYPE OutMat_Consignee_Name,\r\n" + " omt.OutMat_Dept_Code,\r\n"
					+ "	dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n" + "	omt.OutMat_Code,\r\n"
					+ "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n" + "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n"
					+ "	dt4.CHILD_TBL_TYPE OutMat_UNIT,\r\n" + "	omt.OutMat_Qty,\r\n" + "	omt.OutMat_Modifier,\r\n"
					+ "	omt.OutMat_dInsert_Time\r\n" + "from\r\n" + "	OutMat_tbl omt\r\n"
					+ "inner join DTL_TBL dt on\r\n" + "	omt.OutMat_Dept_Code = dt.CHILD_TBL_NO\r\n"
					+ "inner join DTL_TBL dt2 on\r\n" + "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n"
					+ "inner join DTL_TBL dt3 on\r\n" + "	omt.OutMat_Consignee = dt3.CHILD_TBL_NO\r\n"
					+ "inner join Product_Info_tbl pit on\r\n" + "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
					+ "left outer join DTL_TBL dt4 on\r\n" + "	pit.PRODUCT_UNIT = dt4.CHILD_TBL_NO";

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

			sql += " order by omt.OutMat_Date desc";

			//System.out.println("where : " + where);
			//System.out.println(sql);

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
				data.setOutMat_Consignee_Name(rs.getString("OutMat_Consignee_Name"));
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
		//System.out.println(obj);
		String sql = "select\r\n" + " omt.OutMat_Date,\r\n" + "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
				+ " omt.OutMat_Consignee,\r\n" + " dt3.CHILD_TBL_TYPE OutMat_Consignee_Name,\r\n" + " omt.OutMat_Dept_Code,\r\n"
				+ "	dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n" + "	omt.OutMat_Code,\r\n"
				+ "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n" + "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n"
				+ "	dt4.CHILD_TBL_TYPE OutMat_UNIT,\r\n" + "	sum(omt.OutMat_Qty) OutMat_Qty \r\n" + " from\r\n"
				+ " OutMat_tbl omt\r\n" + "inner join DTL_TBL dt on \r\n"
				+ "	omt.OutMat_Dept_Code = dt.CHILD_TBL_NO\r\n" + "inner join DTL_TBL dt2 on\r\n"
				+ "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n" + "inner join Product_Info_tbl pit on\r\n"
				+ "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n" + "inner join DTL_TBL dt3 on\r\n" + " omt.OutMat_Consignee = dt3.CHILD_TBL_NO\r\n" + " left outer join DTL_TBL dt4 on\r\n"
				+ "	pit.PRODUCT_UNIT = dt4.CHILD_TBL_NO";

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

		//System.out.println("where : " + where);
		//System.out.println(sql);

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
				data.setOutMat_Code("");
				data.setOutMat_Name("");
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);

			} else {
				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Date(rs.getString("outMat_Date"));
				data.setOutMat_Send_Clsfc_Name(rs.getString("outMat_Send_Clsfc_Name"));
				data.setOutMat_Dept_Code(rs.getString("outMat_Dept_Code"));
				data.setOutMat_Dept_Name(rs.getString("outMat_Dept_Name"));
				data.setOutMat_Consignee(rs.getString("outMat_Consignee"));
				data.setOutMat_Consignee_Name(rs.getString("outMat_Consignee_Name"));
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
		//System.out.println(obj);
		String sql = "select\r\n" + " omt.OutMat_Date,\r\n" + "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
				+ "	omt.OutMat_Dept_Code,\r\n" + "	dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n"
				+ " omt.OutMat_Consignee,\r\n" + " dt3.CHILD_TBL_TYPE OutMat_Consignee_Name,\r\n" + " omt.OutMat_Code,\r\n" + "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n"
				+ "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n" + "	dt4.CHILD_TBL_TYPE OutMat_UNIT,\r\n"
				+ " sum(omt.OutMat_Qty) OutMat_Qty\r\n" + " from\r\n" + " OutMat_tbl omt\r\n"
				+ "inner join DTL_TBL dt on\r\n" + "	omt.OutMat_Dept_Code = dt.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL dt2 on\r\n" + "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL dt3 on\r\n" + "	omt.OutMat_Consignee = dt3.CHILD_TBL_NO\r\n"
				+ "inner join Product_Info_tbl pit on\r\n" + "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DTL_TBL dt4 on\r\n" + "	pit.PRODUCT_UNIT = dt4.CHILD_TBL_NO";

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

		//System.out.println(sql);

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
				data.setOutMat_Consignee_Name(rs.getString("outMat_Consignee_Name"));
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

	// MO_DeliveryView list
	// 부서별 명세서
	@GetMapping("/MO_DeliverySearch")
	public List<OutMat_tbl> MO_DeliverySearch(SearchDto searchDto) throws SQLException {

		Connection conn = dataSource.getConnection();

		String sql = "select \r\n" + "OutMat_No,\r\n" + "OutMat_Dept_Code,\r\n"
				+ "dt.CHILD_TBL_TYPE OutMat_Dept_Name,\r\n" + "sum(OutMat_Qty) OutMat_Qty\r\n"
				+ "from OutMat_tbl omt\r\n" + "inner join DTL_TBL dt on omt.OutMat_Dept_Code = dt.CHILD_TBL_NO";

		String where = " where omt.OutMat_Date >= '" + searchDto.getStartDate() + "' and omt.OutMat_Date < '"
				+ searchDto.getEndDate() + "'";

		sql += where;

		sql += " group by OutMat_Dept_Code with rollup";
		//System.out.println("where : " + where);
		//System.out.println(sql);

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		System.out.println("dept_Search = " + sql);

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
	// 부서별 명세서
	@GetMapping("/MO_DeliveryItem")
	public List<OutMat_tbl> MO_DeliveryItem(SearchDto searchDto) throws SQLException {

		String sql = "select\r\n" + "	omt.OutMat_No,\r\n" + "	omt.OutMat_Date,\r\n"
				+ "	dt2.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n" + "	omt.OutMat_Code,\r\n"
				+ "	omt.OutMat_Dept_Code,\r\n" + "	pit.PRODUCT_ITEM_NAME OutMat_Name,\r\n"
				+ "	pit.PRODUCT_INFO_STND_1 Outmat_STND_1,\r\n" + "	dt3.CHILD_TBL_TYPE OutMat_UNIT,\r\n"
				+ "	sum(omt.OutMat_Qty) OutMat_Qty\r\n" + "from\r\n" + "	OutMat_tbl omt\r\n"
				+ "inner join DTL_TBL dt2 on\r\n" + "	omt.OutMat_Send_Clsfc = dt2.CHILD_TBL_NO\r\n"
				+ "inner join Product_Info_tbl pit on\r\n" + "	omt.OutMat_Code = pit.PRODUCT_ITEM_CODE\r\n"
				+ "left outer join DTL_TBL dt3 on\r\n" + "	pit.PRODUCT_UNIT = dt3.CHILD_TBL_NO";

		String where = " where omt.OutMat_Date >= '" + searchDto.getStartDate() + "' and omt.OutMat_Date < '"
				+ searchDto.getEndDate() + "'";

		where += " and omt.OutMat_Dept_Code='" + searchDto.getDeptCode() + "'";

		sql += where;

		sql += " group by omt.OutMat_Code with rollup";
		//System.out.println("where : " + where);
		//System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();
		
		System.out.println("list_Search = " + sql);
		
		//System.out.println("---");
		while (rs.next()) {
			if (rs.getString("outMat_Code") == null) {

				OutMat_tbl data = new OutMat_tbl();

				data.setOutMat_Code("Sub Total");
				data.setOutMat_Qty(rs.getInt("outMat_Qty"));

				list.add(data);

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

				//System.out.println(data.toString());

				list.add(data);
			}

		}
		//System.out.println("---");
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	// MOSL_Search
	@GetMapping("/MOSL_Search")
	public List<Sales_OutMat_tbl> MOSL_Search(SearchDto searchDto) throws SQLException {
		System.out.println(searchDto);
		
		Connection conn = dataSource.getConnection();

		String sql = "select \r\n"
				+ "somt.Sales_OutMat_No,\r\n"
				+ "somt.Sales_OutMat_Client_Code,\r\n"
				+ "ct.Cus_Name Sales_OutMat_Client_Name,\r\n"
				+ "sum(somt.Sales_OutMat_Qty) Sales_OutMat_Qty\r\n"
				+ "from Sales_OutMat_tbl somt \r\n"
				+ "inner join Customer_tbl ct on somt.Sales_OutMat_Client_Code = ct.Cus_Code\r\n"
				+ " where somt.Sales_OutMat_Date >= '" + searchDto.getStartDate() + "'\r\n"
				+ " and somt.Sales_OutMat_Date < '" + searchDto.getEndDate() + "'\r\n";

		sql += " group by Sales_OutMat_Client_Code";

		System.out.println("SOC_DeliveryView = " + sql);

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
}
