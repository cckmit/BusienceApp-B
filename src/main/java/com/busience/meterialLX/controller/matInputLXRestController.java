package com.busience.meterialLX.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.materialLX.dto.InMat_tbl;
import com.busience.materialLX.dto.OrderList_tbl;
import com.busience.materialLX.dto.OrderMaster_tbl;

@RestController("matInputLXRestController")
@RequestMapping("matInputLXRest")
public class matInputLXRestController {

	@Autowired
	DataSource dataSource;
	
	//MI_Search
	@RequestMapping(value = "/MI_Search",method = RequestMethod.GET)
	public List<OrderMaster_tbl> MI_Search(HttpServletRequest request) throws ParseException, SQLException, org.json.simple.parser.ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		
		String sql = " SELECT\r\n"
				+ " A.Order_mCus_No,\r\n"
				+ " A.Order_mCode,\r\n"
				+ " B.Cus_Name Order_mName,\r\n"
				+ " date_format(A.Order_mDate,'%Y-%m-%d %T') Order_mDate,\r\n"
				+ " A.Order_mTotal,\r\n"
				+ " A.Order_mDlvry_Date,\r\n"
				+ " A.Order_mRemarks,\r\n"
				+ " A.Order_mModifier,\r\n"
				+ " date_format(A.Order_mModify_Date,'%Y-%m-%d %T') Order_mModify_Date,\r\n"
				+ " A.Order_mCheck\r\n"
				+ " FROM OrderMasterLX_tbl A\r\n"
				+ " inner join Customer_tbl B on A.Order_mCode = B.Cus_Code\r\n";
		
		String where = " where A.Order_mDlvry_Date between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate") + " 23:59:59' ";
		
		if (obj.get("order_mCode") != null && !obj.get("order_mCode").equals("")) {
			where += " and Order_mCode like '%" + obj.get("order_mCode") + "%'";
		}
		if (obj.get("order_mCus_No") != null && !obj.get("order_mCus_No").equals("")) {
			where += " and Order_mCus_No like '%" + obj.get("order_mCus_No") + "%'";
		}
		
		sql += where;
		System.out.println(sql);
		
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

	//MIS_Search
	@RequestMapping(value = "/MIS_Search", method = RequestMethod.GET)
	public List<OrderList_tbl> MIS_Search(
			@RequestParam(value = "order_lCus_No", required = false) String order_lCus_No) throws SQLException {
		List<OrderList_tbl> list = new ArrayList<OrderList_tbl>();

		String sql = " SELECT \r\n"
				+ " A.Order_lNo,\r\n"
				+ " A.Order_lCus_No,\r\n"
				+ " B.PRODUCT_ITEM_NAME Order_lName,\r\n"
				+ " A.Order_lCode,\r\n"
				+ " B.PRODUCT_INFO_STND_1 Order_STND_1,\r\n"
				+ " A.Order_lQty, \r\n"
				+ " A.Order_lSum, \r\n"
				+ " A.Order_lUnit_Price,\r\n"
				+ " A.Order_lPrice,\r\n"
				+ " A.Order_lNot_Stocked,\r\n"
				+ "	B.PRODUCT_BASIC_WAREHOUSE,\r\n"
				+ " B.PRODUCT_SAVE_AREA,\r\n"
				+ " A.Order_Rcv_Clsfc,\r\n"
				+ " A.Order_lInfo_Remark\r\n"
				+ " FROM OrderListLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Order_lCode = B.PRODUCT_ITEM_CODE\r\n";
		
		String where = " where Order_lCus_No = '"+order_lCus_No+"'"
				+ " order by A.Order_lNo";
		
		sql += where;
		System.out.println("MIS_Search ="+sql);

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
			data.setOrder_lSum(rs.getInt("order_lSum"));
			data.setOrder_lUnit_Price(rs.getInt("order_lUnit_Price"));
			data.setOrder_lPrice(rs.getInt("order_lPrice"));
			data.setOrder_lNot_Stocked(rs.getInt("order_lNot_Stocked"));
			data.setOrder_Basic_WareHouse(rs.getString("PRODUCT_BASIC_WAREHOUSE"));
			data.setOrder_Save_Area(rs.getString("PRODUCT_SAVE_AREA"));
			data.setOrder_Rcv_Clsfc(rs.getString("order_Rcv_Clsfc"));
			data.setOrder_lInfo_Remark(rs.getString("order_lInfo_Remark"));
			
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}

	//MIM_Search
	@RequestMapping(value = "/MIM_Search", method = RequestMethod.GET)
	public List<InMat_tbl> MIM_Search(
			@RequestParam(value = "inMat_Order_No", required = false) String inMat_Order_No) throws SQLException {
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();

		String sql = " SELECT\r\n"
				+ " A.InMat_No,\r\n"
				+ " A.InMat_Order_No,\r\n"
				+ " A.InMat_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME InMat_Name,\r\n"
				+ " A.InMat_Qty,\r\n"
				+ " A.InMat_Unit_Price,\r\n"
				+ " A.InMat_Price ,\r\n"
				+ " A.InMat_Rcv_Clsfc,\r\n"
				+ " date_format(A.InMat_Date,'%Y-%m-%d %T') InMat_Date\r\n"
				+ " FROM InMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.InMat_Code = B.PRODUCT_ITEM_CODE\r\n";
		
		String where = " where A.InMat_Order_No = '"+inMat_Order_No+"' and A.InMat_Rcv_Clsfc like '17%' and A.InMat_Date >= curdate()"
				+ "	order by A.inMat_No;";
		
		sql += where;
		System.out.println("MIM_Search ="+sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			InMat_tbl data = new InMat_tbl();
			data.setInMat_No(rs.getInt("inMat_No"));
			data.setInMat_Order_No(rs.getString("inMat_Order_No"));
			data.setInMat_Code(rs.getString("inMat_Code"));
			data.setInMat_Name(rs.getString("inMat_Name"));
			data.setInMat_Qty(rs.getInt("inMat_Qty"));
			data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
			data.setInMat_Price(rs.getInt("inMat_Price"));
			data.setInMat_Rcv_Clsfc(rs.getString("inMat_Rcv_Clsfc"));
			data.setInMat_Date(rs.getString("inMat_Date"));
			
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	// MIM_Save
	@GetMapping("/MIM_Save")
	public String MIM_Save(HttpServletRequest request, Principal principal) throws Exception {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		
		String modifier = principal.getName();
		
		String InMatLX_tbl_sql = null;
		String OrderList_sql = null;
		String StockMatLX_tbl_sql = null;
		String OrderMaster_sql = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			//Sales_OrderList_tbl
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				//입고테이블에 insert
				InMatLX_tbl_sql = " insert into InMatLX_tbl (\r\n"
						+ " InMat_No,\r\n"
						+ " InMat_Order_No,\r\n"
						+ " InMat_Code,\r\n"
						+ " InMat_Qty,\r\n"
						+ " InMat_Unit_Price,\r\n"
						+ " InMat_Price,\r\n"
						+ " InMat_Client_Code,\r\n"
						+ " InMat_Date,\r\n"
						+ " InMat_Rcv_Clsfc,\r\n"
						+ " InMat_dInsert_Time,\r\n"
						+ " InMat_Modifier"
						+ " ) value (\r\n"
						+ " '"+obj.get("inMat_No")+"',\r\n"
						+ " '"+obj.get("inMat_Order_No")+"',\r\n"
						+ " '"+obj.get("inMat_Code")+"',\r\n"
						+ " '"+obj.get("inMat_Qty")+"',\r\n"
						+ " '"+obj.get("inMat_Unit_Price")+"',\r\n"
						+ " '"+obj.get("inMat_Price")+"',\r\n"
						+ " '"+obj.get("inMat_Client_Code")+"',\r\n"
						+ " '"+obj.get("inMat_Date")+"',\r\n"
						+ " '"+obj.get("inMat_Rcv_Clsfc")+"',\r\n"
						+ " now(),\r\n"
						+ " '"+modifier+"')\r\n";
				
				//orderlist테이블에 수량 update
				OrderList_sql = " UPDATE OrderListLX_tbl SET\r\n"
						+ " Order_lSum = Order_lSum + "+obj.get("inMat_Qty")+",\r\n"
						+ " Order_lUnit_Price = "+obj.get("inMat_Unit_Price")+",\r\n"
						+ " Order_lPrice = "+obj.get("inMat_Qty")+"*"+obj.get("inMat_Unit_Price")+",\r\n"
						+ " Order_lNot_Stocked = Order_lNot_Stocked-"+obj.get("inMat_Qty")+"\r\n"
						+ " WHERE Order_lCus_No = '"+obj.get("inMat_Order_No") +"'"
						+ " and Order_lCode = '"+obj.get("inMat_Code")+"'";
				
				//StockMat테이블에 update
				StockMatLX_tbl_sql = " update StockMatLX_tbl set" 
						+ " SM_In_Qty = SM_In_Qty+"+obj.get("inMat_Qty")
						+ " where SM_Code = '"+obj.get("inMat_Code")+"'";
				
				//OrderMaster 테이블에 입고상태 update
				OrderMaster_sql = " update OrderMasterLX_tbl\r\n"
						+ " set Order_mCheck = \r\n"
						+ " (select \r\n"
						+ "	(case \r\n"
							+ "	when sum(Order_lQty) = sum(Order_lSum) then 'Y'\r\n"
							+ "	when sum(Order_lSum) = 0 then 'N' \r\n"
							+ "	else 'I'\r\n"
						+ "	end)\r\n"
						+ "	from OrderListLX_tbl \r\n"
						+ "	where Order_lCus_No = '"+obj.get("inMat_Order_No")+"'\r\n"
						+ " )\r\n"
						+ " where Order_mCus_No = '"+obj.get("inMat_Order_No")+"'";

				System.out.println("InMatLX_tbl_sql = " + InMatLX_tbl_sql);
				pstmt = conn.prepareStatement(InMatLX_tbl_sql);
				pstmt.executeUpdate();
				
				System.out.println("OrderList_sql = " + OrderList_sql);
				pstmt = conn.prepareStatement(OrderList_sql);
				pstmt.executeUpdate();
				
				System.out.println("StockMatLX_tbl_sql = " + StockMatLX_tbl_sql);
				pstmt = conn.prepareStatement(StockMatLX_tbl_sql);		 
				pstmt.executeUpdate();
				
				System.out.println("OrderMaster_sql = " + OrderMaster_sql);
				pstmt = conn.prepareStatement(OrderMaster_sql);
				pstmt.executeUpdate();

			}
			
			conn.commit();
			sql_result = "success";
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn!=null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if(rs!=null) {
				rs.close();
			}
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
