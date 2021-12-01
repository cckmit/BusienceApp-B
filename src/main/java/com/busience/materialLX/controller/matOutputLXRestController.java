package com.busience.materialLX.controller;

import java.net.UnknownHostException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.materialLX.dto.OutMat_tbl;
import com.busience.materialLX.dto.StockMat_tbl;

@RestController("matOutputLXRestController")
@RequestMapping("matOutputLXRest")
public class matOutputLXRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/MOS_Search", method = RequestMethod.GET)
	public List<StockMat_tbl> MOS_Search(HttpServletRequest request) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		System.out.println("data : " + originData);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		
		List<StockMat_tbl> list = new ArrayList<StockMat_tbl>();

		String sql = "SELECT SM_Code, pit.PRODUCT_ITEM_NAME as SM_Name, (SM_Last_Qty+SM_In_Qty-SM_Out_Qty) SM_Last_Qty\r\n"
				+ "FROM StockMatLX_tbl smlt\r\n"
				+ "INNER JOIN PRODUCT_INFO_TBL pit ON smlt.SM_Code = pit.PRODUCT_ITEM_CODE\r\n";

		String where = " WHERE SM_Last_Qty+SM_In_Qty-SM_Out_Qty > 0";

		
		if(obj.get("itemCode") != null && !obj.get("itemCode").equals("")) {
		   where += " and SM_Code like '%" + obj.get("itemCode") + "%'";
		}
		 
		
		sql += where;
		System.out.println("MRL_Search =" + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int i = 0;

		while (rs.next()) {
			StockMat_tbl data = new StockMat_tbl();

			i++;
			data.setId(i);
			data.setSM_Code(rs.getString("SM_Code"));
			data.setSM_Name(rs.getString("SM_Name"));
			data.setSM_Last_Qty(rs.getInt("SM_Last_Qty"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// matOutMat Search

	@RequestMapping(value = "/MOM_Search", method = RequestMethod.GET)
	public List<OutMat_tbl> MOM_Search(@RequestParam(value = "sm_Code", required = false) String sm_Code)
			throws ParseException, SQLException {
		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();

		String sql = " SELECT OutMat_No, OutMat_Code, pit.PRODUCT_ITEM_NAME OutMat_Name, OutMat_Qty, OutMat_Consignee, OutMat_Dept_Code, OutMat_Send_Clsfc, OutMat_Date FROM OutMatLX_tbl omt";

		String where = " LEFT OUTER JOIN PRODUCT_INFO_TBL pit ON omt.OutMat_Code = pit.PRODUCT_ITEM_CODE WHERE OutMat_Code = '" + sm_Code + "'" + " order by OutMat_No";

		sql += where;

		System.out.println("MOM_Search = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;

		while (rs.next()) {
			OutMat_tbl data = new OutMat_tbl();

			i++;
			data.setID(i);
			data.setOutMat_No(rs.getInt("outMat_No"));
			data.setOutMat_Code(rs.getString("outMat_Code"));
			data.setOutMat_Name(rs.getString("outMat_Name"));
			data.setOutMat_Qty(rs.getInt("outMat_Qty"));
			data.setOutMat_Consignee(rs.getString("outMat_Consignee"));
			data.setOutMat_Dept_Code(rs.getString("outMat_Dept_Code"));
			data.setOutMat_Send_Clsfc(rs.getString("outMat_Send_Clsfc"));
			data.setOutMat_Date(rs.getString("outMat_Date"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// orderList save
	@GetMapping("/MOM_Save")
	public String MOM_Save(HttpServletRequest request, Principal principal) throws ParseException, SQLException,
			UnknownHostException, ClassNotFoundException {
		String originData = request.getParameter("data");
		System.out.println(originData);
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		System.out.println(arr);
		String modifier = principal.getName();

		String OutMatLX_tbl_sql = null;
		String StockMatLX_tbl_sql = null;

		System.out.println("MOM_Save");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			// Sales_Output_OrderList_tbl
			for (int i = 0; i < arr.size(); i++) {
				JSONObject listobj = (JSONObject) arr.get(i);
				System.out.println(listobj);
				// 수화인의 부서는 13로 통일.
				// OutMat insert
				OutMatLX_tbl_sql = " insert into OutMatLX_tbl(\r\n" + " OutMat_No,\r\n" + " OutMat_Code,\r\n"
						+ " OutMat_Qty,\r\n" + " OutMat_Consignee," + " OutMat_Dept_Code, " + " OutMat_Send_Clsfc,\r\n" + " OutMat_Date,\r\n"
						+ " OutMat_dInsert_Time,\r\n" + " OutMat_Modifier" + ") values (" + "'" + listobj.get("id") + "', "
						+ "'" + listobj.get("outMat_Code") + "',\r\n" + "'" + listobj.get("outMat_Qty")
						+ "',\r\n" + "'" + listobj.get("outMat_Consignee") + "',\r\n" + "'13', " + "'"
						+ listobj.get("outMat_Send_Clsfc") + "', " + " now(),\r\n"
						+ "now(),\r\n" + "'" + modifier + "')";

				// StockMat에 update
				StockMatLX_tbl_sql = " update StockMatLX_tbl set" + " SM_Out_Qty = SM_Out_Qty+'" + listobj.get("outMat_Qty")
						+ "'" + " where SM_Code = '" + listobj.get("outMat_Code") + "'";

				System.out.println("OutMatLX_tbl_sql = " + OutMatLX_tbl_sql);
				pstmt = conn.prepareStatement(OutMatLX_tbl_sql);
				pstmt.executeUpdate();

				System.out.println("StockMat_tbl_sql = " + StockMatLX_tbl_sql);
				pstmt = conn.prepareStatement(StockMatLX_tbl_sql);
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
}
