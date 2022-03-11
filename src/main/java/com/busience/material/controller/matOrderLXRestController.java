package com.busience.material.controller;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.material.dto.OrderListDto;
import com.busience.material.dto.OrderMasterDto;
import com.busience.material.dto.StockMatDto;
import com.busience.material.service.MatOrderService;

@RestController("matOrderLXRestController")
@RequestMapping("matOrderLXRest")
public class matOrderLXRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	MatOrderService matOrderService;

	@GetMapping("MOM_Search")
	public List<OrderMasterDto> MOM_Search(SearchDto searchDto){
		return matOrderService.matOrderMasterSelect(searchDto);
	}
	
	@GetMapping("MOL_Search")
	public List<OrderListDto> MOL_Search(SearchDto searchDto){
		return matOrderService.matOrderListSelect(searchDto);
	}
	
	@GetMapping("MOS_Search")
	public List<StockMatDto> MOS_Search(SearchDto searchDto){
		return matOrderService.stockMatSelect(searchDto);
	}
	
	@PostMapping("MOM_Insert")
	public int MOM_Insert(OrderMasterDto orderMasterDto, Principal principal) {
		return matOrderService.matOrderMasterInsert(orderMasterDto, principal.getName());
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
			OM_delete_sql = "delete from OrderMaster_tbl"
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
		String OrderMaster_tbl_sql = null;
		String OrderList_tbl_sql = null;
		
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
							+ "select count(*)+1 from OrderMaster_tbl"
							+ " where Order_mCode = '"+masterobj.get("order_mCode")+"' and Order_mDate >= curdate()) AA),"
						+ " 2,'0'))\r\n";
				
				System.out.println("Cus_No_sql = " + Cus_No_sql);
				pstmt = conn.prepareStatement(Cus_No_sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Cus_No = rs.getString(1);
				}
			}
			
			
			OrderMaster_tbl_sql = " insert into OrderMaster_tbl(\r\n" 
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
			
			System.out.println("OrderMaster_tbl_sql = " + OrderMaster_tbl_sql);
			pstmt = conn.prepareStatement(OrderMaster_tbl_sql);
			pstmt.executeUpdate();
			
			for(int i=0;i<listarr.size();i++) {
				JSONObject listobj = (JSONObject) listarr.get(i);
				System.out.println(listobj);
				
				OrderList_tbl_sql = "insert into OrderList_tbl(\r\n" 
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

				System.out.println("OrderList_tbl_sql = " + OrderList_tbl_sql);
				pstmt = conn.prepareStatement(OrderList_tbl_sql);
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
				OS_delete_sql = "delete from OrderList_tbl"
						+ " where Order_lNo = '" + obj.get("order_lNo") + "'"
						+ " and Order_lCus_No = '" + obj.get("order_lCus_No") + "'";
				
				System.out.println("OS_delete_sql = " + OS_delete_sql);
				pstmt = conn.prepareStatement(OS_delete_sql);
				pstmt.executeUpdate();
			}
			
			//삭제후 list 순번을 재정리 해준다.
			Cus_No_sql = " UPDATE OrderList_tbl A, (SELECT @rank:=0) B"
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