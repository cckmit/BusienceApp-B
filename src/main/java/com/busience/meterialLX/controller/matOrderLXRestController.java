package com.busience.meterialLX.controller;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.materialLX.dto.OrderList_tbl;
import com.busience.materialLX.dto.OrderMaster_tbl;
import com.busience.materialLX.dto.StockMat_tbl;

@RestController("matOrderLXRestController")
@RequestMapping("matOrderLXRest")
public class matOrderLXRestController {

	@Autowired
	DataSource dataSource;

	// orderMaster
	@RequestMapping(value = "/MO_Search", method = RequestMethod.GET)
	public List<OrderMaster_tbl> MO_Search(HttpServletRequest request)
			throws ParseException, SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String sql = " SELECT \r\n" 
				+ " A.Order_mCus_No,\r\n" 
				+ " A.Order_mCode,\r\n" 
				+ " B.Cus_Name Order_mName,\r\n"
				+ " A.Order_mDate,\r\n" 
				+ " A.Order_mTotal,\r\n" 
				+ " A.Order_mDlvry_Date,\r\n"
				+ " A.Order_mRemarks,\r\n" 
				+ " A.Order_mModifier,\r\n" 
				+ " A.Order_mModify_Date,\r\n"
				+ " A.Order_mCheck\r\n" 
				+ " FROM OrderMasterLX_tbl A\r\n"
				+ " inner join Customer_tbl B on A.Order_mCode = B.Cus_Code\r\n";

		String where = "where A.Order_mDate between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate") + " 23:59:59'\r\n" 
				+ " and not Order_mCheck = 'Y'\r\n"
				+ " order by Order_mDate;";
		
		if (obj.get("order_mCode") != null && !obj.get("order_mCode").equals("")) {
			where += " and Order_mCode like '%" + obj.get("order_mCode") + "%'";
		}

		sql += where;

		System.out.println("MO_Search = " + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<OrderMaster_tbl> list = new ArrayList<OrderMaster_tbl>();

		while (rs.next()) {
			OrderMaster_tbl data = new OrderMaster_tbl();

			data.setOrder_mCus_No(rs.getString("order_mCus_No"));
			data.setOrder_mCode(rs.getString("order_mCode"));
			data.setOrder_mName(rs.getString("order_mName"));
			data.setOrder_mDate(rs.getString("order_mDate"));
			data.setOrder_mTotal(rs.getInt("order_mTotal"));
			data.setOrder_mDlvry_Date(rs.getString("order_mDlvry_Date"));
			data.setOrder_mRemarks(rs.getString("order_mRemarks"));
			data.setOrder_mModifier(rs.getString("order_mModifier"));
			data.setOrder_mModify_Date(rs.getString("order_mModify_Date"));
			data.setOrder_mCheck(rs.getString("order_mCheck"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// orderList
	@RequestMapping(value = "/MOL_Search", method = RequestMethod.GET)
	public List<OrderList_tbl> MOL_Search(@RequestParam(value = "order_lCus_No", required = false) String order_lCus_No)
			throws SQLException {
		List<OrderList_tbl> list = new ArrayList<OrderList_tbl>();

		String sql = " SELECT \r\n" 
				+ " A.Order_lNo,\r\n" 
				+ " A.Order_lCus_No,\r\n"
				+ " B.PRODUCT_ITEM_NAME Order_lName,\r\n" 
				+ " A.Order_lCode,\r\n"
				+ " B.PRODUCT_INFO_STND_1 Order_STND_1,\r\n" 
				+ " A.Order_lQty, \r\n" 
				+ " A.Order_lUnit_Price,\r\n"
				+ " A.Order_lPrice,\r\n" 
				+ " A.Order_lNot_Stocked,\r\n" 
				+ " A.Order_Rcv_Clsfc,\r\n"
				+ " A.Order_lInfo_Remark\r\n" 
				+ " FROM OrderListLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Order_lCode = B.PRODUCT_ITEM_CODE";

		String where = " where Order_lCus_No = '" + order_lCus_No + "'" + "Order by Order_lNo";

		sql += where;
		System.out.println("MOL_Search =" + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			OrderList_tbl data = new OrderList_tbl();
			
			data.setOrder_lNo(rs.getInt("order_lNo"));
			data.setOrder_lCus_No(rs.getString("order_lCus_No"));
			data.setOrder_lCode(rs.getString("order_lCode"));
			data.setOrder_lName(rs.getString("order_lName"));
			data.setOrder_STND_1(rs.getString("order_STND_1"));
			data.setOrder_lQty(rs.getInt("order_lQty"));
			data.setOrder_lUnit_Price(rs.getInt("order_lUnit_Price"));
			data.setOrder_lPrice(rs.getInt("order_lPrice"));
			data.setOrder_lNot_Stocked(rs.getInt("order_lNot_Stocked"));
			data.setOrder_Rcv_Clsfc(rs.getString("order_Rcv_Clsfc"));
			data.setOrder_lInfo_Remark(rs.getString("order_lInfo_Remark"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// MOS_Search
	@RequestMapping(value = "/MOS_Search", method = RequestMethod.GET)
	public List<StockMat_tbl> MOS_Search(@RequestParam(value = "order_lCode", required = false) String order_lCode) throws SQLException {
		List<StockMat_tbl> list = new ArrayList<StockMat_tbl>();

		String sql = " SELECT \r\n" 
				+ " A.SM_Code,\r\n" 
				+ " B.PRODUCT_ITEM_NAME SM_Name,\r\n"
				+ " B.PRODUCT_INFO_STND_1 SM_STND_1,\r\n" 
				+ " A.SM_Last_Qty+A.SM_In_Qty-A.SM_Out_Qty SM_Qty\r\n"
				+ " FROM StockMatLX_tbl A\r\n" 
				+ " inner join PRODUCT_INFO_TBL B ON A.SM_Code = B.PRODUCT_ITEM_CODE";

		String where = " where A.SM_Code = '" + order_lCode + "'";

		sql += where;
		System.out.println("MOS_Search =" + sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			StockMat_tbl data = new StockMat_tbl();

			data.setSM_Code(rs.getString("sm_Code"));
			data.setSM_Name(rs.getString("sm_Name"));
			data.setSM_STND_1(rs.getString("sm_STND_1"));
			data.setSM_Qty(rs.getInt("sm_Qty"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	// MO_Update
	@GetMapping("/MO_Update")
	public String MO_Update(HttpServletRequest request, Principal principal) throws Exception {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		String modifier = principal.getName();
		
		String sql = " insert into OrderMasterLX_tbl(\r\n" 
				+ " Order_mCus_No,\r\n" 
				+ " Order_mCode,\r\n"
				+ " Order_mDate,\r\n" 
				+ " Order_mTotal,\r\n" 
				+ " Order_mDlvry_Date,\r\n" 
				+ " Order_mRemarks,\r\n"
				+ " Order_mModifier,\r\n" 
				+ " Order_mModify_Date\r\n" 
				+ ") values (\r\n" 
				+ "'" + obj.get("order_mCus_No") + "',\r\n" 
				+ "'" + obj.get("order_mCode") + "',\r\n" 
				+ "'" + obj.get("order_mDate") + "',\r\n" 
				+ "" + obj.get("order_mTotal") + ",\r\n" 
				+ "'" + obj.get("order_mDlvry_Date") + "',\r\n" 
				+ "'" + obj.get("order_mRemarks") + "',\r\n" 
				+ "'" + modifier + "',\r\n" 
				+ " now()\r\n" 
				+ " ) on duplicate key\r\n" 
				+ " update" 
				+ " Order_mTotal ='" + obj.get("order_mTotal") + "'," 
				+ " Order_mDlvry_Date ='" + obj.get("order_mDlvry_Date") + "'," 
				+ " Order_mRemarks ='" + obj.get("order_mRemarks") + "'," 
				+ " Order_mModifier ='" + modifier + "',"
				+ " Order_mModify_Date = now()";


		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql_result = null;
		try {
			conn = dataSource.getConnection();
			
			System.out.println("MO_Update = " + sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			sql_result = "success";
		} catch (SQLException e) {
			e.printStackTrace();
			sql_result = "error";
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sql_result;
	}
	
	// orderMaster delete
	@RequestMapping("/MO_Delete")
	public String MO_Delete(HttpServletRequest request, Model model) throws ParseException, SQLException {
		JSONParser parser = new JSONParser();

		String data = request.getParameter("data");
		JSONObject obj = (JSONObject) parser.parse(data);
		
		String OM_delete_sql = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			//master데이터에 속한 list데이터가 없는경우 master데이터도 삭제한다.
			OM_delete_sql = "delete from OrderMasterLX_tbl"
					+ " where Order_mCus_No = '" + obj.get("order_mCus_No") + "'";
			
			System.out.println("OM_delete_sql = " + OM_delete_sql);
			pstmt = conn.prepareStatement(OM_delete_sql);
			pstmt.executeUpdate();

			conn.commit();
			sql_result = "success";
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn!=null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		
		return sql_result;
	}

	// orderList save
	@GetMapping("/MOL_Save")
	public String MOL_Save(HttpServletRequest request, Principal principal) throws Exception {
		JSONParser parser = new JSONParser();
		
		//masterdata
		String masterData = request.getParameter("masterData");
		JSONObject masterobj = (JSONObject) parser.parse(masterData);

		//listData
		String listData = request.getParameter("listData");
		JSONArray listarr = (JSONArray) parser.parse(listData);
		
		String modifier = principal.getName();
		String Cus_No_sql = null;
		String OrderMasterLX_tbl_sql = null;
		String OrderListLX_tbl_sql = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql_result = null;
		
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			//수주번호를 알아내야함
			String Cus_No = (String) masterobj.get("order_mCus_No");
			
			//수주번호가 없을경우 (ordermaster에 새로 저장되는경우) 수주번호를 얻어냄
			if(Cus_No.equals("")) {
				Cus_No_sql = "select concat('"+masterobj.get("order_mCode")+"','-',date_format(now(),'%y%m%d'),'-',"
						+ " lpad("
						+ " (select * from ("
							+ "select count(*)+1 from OrderMasterLX_tbl"
							+ " where Order_mCode = '"+masterobj.get("order_mCode")+"' and Order_mDate >= curdate()) AA),"
						+ " 2,'0'))\r\n";
				
				System.out.println("Cus_No_sql = " + Cus_No_sql);
				pstmt = conn.prepareStatement(Cus_No_sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Cus_No = rs.getString(1);
				}
			}
			
			
			OrderMasterLX_tbl_sql = " insert into OrderMasterLX_tbl(\r\n" 
					+ " Order_mCus_No,\r\n" 
					+ " Order_mCode,\r\n"
					+ " Order_mDate,\r\n" 
					+ " Order_mTotal,\r\n" 
					+ " Order_mDlvry_Date,\r\n" 
					+ " Order_mRemarks,\r\n"
					+ " Order_mModifier,\r\n" 
					+ " Order_mModify_Date\r\n" 
					+ ") values (\r\n" 
					+ "'" + Cus_No + "',\r\n" 
					+ "'" + masterobj.get("order_mCode") + "',\r\n" 
					+ "'" + masterobj.get("order_mDate") + "',\r\n" 
					+ "" + masterobj.get("order_mTotal") + ",\r\n" 
					+ "'" + masterobj.get("order_mDlvry_Date") + "',\r\n" 
					+ "'" + masterobj.get("order_mRemarks") + "',\r\n" 
					+ "'" + modifier + "',\r\n" 
					+ " now()\r\n" 
					+ " ) on duplicate key update\r\n" 
					+ " Order_mTotal ='" + masterobj.get("order_mTotal") + "'," 
					+ " Order_mDlvry_Date ='" + masterobj.get("order_mDlvry_Date") + "'," 
					+ " Order_mRemarks ='" + masterobj.get("order_mRemarks") + "'," 
					+ " Order_mModifier ='" + modifier + "',"
					+ " Order_mModify_Date = now()";
			
			System.out.println("OrderMasterLX_tbl_sql = " + OrderMasterLX_tbl_sql);
			pstmt = conn.prepareStatement(OrderMasterLX_tbl_sql);
			pstmt.executeUpdate();
			
			for(int i=0;i<listarr.size();i++) {
				JSONObject listobj = (JSONObject) listarr.get(i);
				System.out.println(listobj);
				
				OrderListLX_tbl_sql = "insert into OrderListLX_tbl(\r\n" 
						+ " Order_lNo,\r\n" 
						+ " Order_lCus_No,\r\n" 
						+ " Order_lCode,\r\n"
						+ " Order_lQty,\r\n" 
						+ " Order_lSum,\r\n" 
						+ " Order_lUnit_Price,\r\n" 
						+ " Order_lPrice,\r\n"
						+ " Order_lNot_Stocked,\r\n" 
						+ " Order_Rcv_Clsfc,\r\n" 
						+ " Order_lInfo_Remark" 
						+ ") values (\r\n" 
						+ "'" + listobj.get("order_lNo") + "',\r\n" 
						+ "'" + Cus_No + "',\r\n" 
						+ "'" + listobj.get("order_lCode") + "',\r\n" 
						+ "'" + listobj.get("order_lQty") + "',\r\n" 
						+ "'0',\r\n" 
						+ "'" + listobj.get("order_lUnit_Price") + "',\r\n" 
						+ "'" + listobj.get("order_lPrice") + "',\r\n" 
						+ "'" + listobj.get("order_lQty") + "',\r\n" 
						+ "'" + listobj.get("order_Rcv_Clsfc") + "',\r\n" 
						+ "'" + listobj.get("order_lInfo_Remark") + "'\r\n" 
						+ " ) on duplicate key\r\n" 
						+ " update" 
						+ " Order_lNo = '" + listobj.get("order_lNo") + "',"
						+ " Order_lQty = '"	+ listobj.get("order_lQty") + "'," 
						+ " Order_lUnit_Price = '" + listobj.get("order_lUnit_Price") + "',"
						+ " Order_lPrice = '" + listobj.get("order_lPrice") + "'," 
						+ " Order_lNot_Stocked = '"	+ listobj.get("order_lQty") + "'," 
						+ " Order_Rcv_Clsfc = '" + listobj.get("order_Rcv_Clsfc") + "',"
						+ " Order_lInfo_Remark = '" + listobj.get("order_lInfo_Remark") + "'";

				System.out.println("OrderListLX_tbl_sql = " + OrderListLX_tbl_sql);
				pstmt = conn.prepareStatement(OrderListLX_tbl_sql);
				pstmt.executeUpdate();
			}
			
		
			conn.commit();
			sql_result = Cus_No;
		} catch (SQLException e) {
			e.printStackTrace();
			sql_result = "error";
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sql_result;
	}

	// orderList delete
	@RequestMapping(value = "/MOL_Delete", method = RequestMethod.POST)
	public String MOL_Delete(HttpServletRequest request, Model model) throws ParseException, SQLException {
		JSONParser parser = new JSONParser();

		//data
		String data = request.getParameter("data");
		JSONArray arr = (JSONArray) parser.parse(data);
		
		String Order_lCus_No = null;
		String OS_delete_sql = null;
		String Cus_No_sql = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				Order_lCus_No = (String) obj.get("order_lCus_No");
				
				//새로추가
				OS_delete_sql = "delete from OrderListLX_tbl"
						+ " where Order_lNo = '" + obj.get("order_lNo") + "'"
						+ " and Order_lCus_No = '" + obj.get("order_lCus_No") + "'";
				
				System.out.println("OS_delete_sql = " + OS_delete_sql);
				pstmt = conn.prepareStatement(OS_delete_sql);
				pstmt.executeUpdate();
			}
			
			//삭제후 list 순번을 재정리 해준다.
			Cus_No_sql = " UPDATE OrderListLX_tbl A, (SELECT @rank:=0) B"
					+ " SET A.Order_lNo = @rank:=@rank+1\r\n"
					+ " where A.Order_lCus_No = '"+Order_lCus_No+"';";

			
			System.out.println("Cus_No_sql = " + Cus_No_sql);
			pstmt = conn.prepareStatement(Cus_No_sql);
			pstmt.executeUpdate();
			
			conn.commit();
			sql_result = "success";
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn!=null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		
		return sql_result;
	}
}