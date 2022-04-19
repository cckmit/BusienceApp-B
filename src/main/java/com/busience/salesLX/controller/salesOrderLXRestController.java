package com.busience.salesLX.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.salesLX.dto.SalesOrderListDto;
import com.busience.salesLX.dto.SalesOrderMasterDto;
import com.busience.salesLX.dto.Sales_OrderList_tbl;
import com.busience.salesLX.dto.Sales_OrderMaster_tbl;
import com.busience.salesLX.dto.Sales_StockMat_tbl;
import com.busience.salesLX.service.SalesOrderListService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController("salesOrderLXRestController")
@RequestMapping("salesOrderLXRest")
public class salesOrderLXRestController {

	@Autowired
	DataSource dataSource;

	@Autowired
	SalesOrderListService salesOrderService;

	// SalesOrderMaster select
	@GetMapping("/SO_Search")
	public List<SalesOrderMasterDto> salesOrderMasterSelectDao(SearchDto searchDto) {
		return salesOrderService.salesOrderMasterSelectDao(searchDto);
	}

	// SalesOrderList select
	@GetMapping("/SOL_Search")
	public List<SalesOrderListDto> salesOrderListSelectDao(SearchDto searchDto) {
		return salesOrderService.salesOrderListSelectDao(searchDto);
	}

	// save
	@PostMapping("/SOL_Save")
	public int SO_Save(@RequestParam("masterData") String masterData, @RequestParam("subData") String subData,
			Principal principal) {
		
		System.out.println("masterData=" + masterData);
		System.out.println("subData=" + subData);
		ObjectMapper mapper = new ObjectMapper();

		try {
			SalesOrderMasterDto salesOrderMasterDto = mapper.readValue(masterData, SalesOrderMasterDto.class);

			List<SalesOrderListDto> salesOrderListDtoList = Arrays
					.asList(mapper.readValue(subData, SalesOrderListDto[].class));

			return salesOrderService.salesOrderInsertUpdate(salesOrderMasterDto, salesOrderListDtoList,
					principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// salesOrderList delete
	@DeleteMapping("/SOL_Delete")
	public int SOL_Delete(@RequestBody List<SalesOrderListDto> salesOrderDtoList) {
		return salesOrderService.salesOrderListDeleteDao(salesOrderDtoList);				
	}

	/*
	 * // salesOrderListLX delete
	 * 
	 * @RequestMapping(value = "/SOL_Delete", method = RequestMethod.GET) public
	 * String SOL_Delete(HttpServletRequest request, Model model) throws
	 * ParseException, SQLException { JSONParser parser = new JSONParser();
	 * 
	 * // data String data = request.getParameter("data"); JSONArray arr =
	 * (JSONArray) parser.parse(data);
	 * 
	 * String sales_Order_mCus_No = null; String OS_delete_sql = null; String
	 * OM_delete_sql = null; String Stock_delete_Sql = null; String Cus_No_sql =
	 * null;
	 * 
	 * Connection conn = null; PreparedStatement pstmt = null; String sql_result =
	 * null;
	 * 
	 * try { conn = dataSource.getConnection(); conn.setAutoCommit(false); for (int
	 * i = 0; i < arr.size(); i++) { JSONObject obj = (JSONObject) arr.get(i);
	 * System.out.println(obj);
	 * 
	 * sales_Order_mCus_No = (String) obj.get("sales_Order_lCus_No");
	 * 
	 * // 새로추가 OS_delete_sql = "delete from Sales_OrderListLX_tbl" +
	 * " where Sales_Order_lNo = '" + obj.get("sales_Order_lNo") + "'" +
	 * " and Sales_Order_lCus_No = '" + obj.get("sales_Order_lCus_No") + "'";
	 * 
	 * System.out.println("OS_delete_sql = " + OS_delete_sql); pstmt =
	 * conn.prepareStatement(OS_delete_sql); pstmt.executeUpdate();
	 * 
	 * }
	 * 
	 * // master데이터에 속한 list데이터가 없는경우 master데이터도 삭제한다. OM_delete_sql =
	 * "delete from Sales_OrderMasterLX_tbl" + " where Sales_Order_mCus_No = '" +
	 * sales_Order_mCus_No + "'" +
	 * " and not exists (SELECT * FROM Sales_OrderListLX_tbl" +
	 * " WHERE sales_Order_lCus_No = '" + sales_Order_mCus_No + "'" + " )";
	 * 
	 * // 삭제후 list 순번을 재정리 해준다. Cus_No_sql =
	 * " UPDATE Sales_OrderListLX_tbl A, (SELECT @rank:=0) B" +
	 * " SET A.Sales_Order_lNo = @rank:=@rank+1\r\n" +
	 * " where A.Sales_Order_lCus_No = '" + sales_Order_mCus_No + "';";
	 * 
	 * System.out.println("OM_delete_sql = " + OM_delete_sql); pstmt =
	 * conn.prepareStatement(OM_delete_sql); pstmt.executeUpdate();
	 * 
	 * System.out.println("Cus_No_sql = " + Cus_No_sql); pstmt =
	 * conn.prepareStatement(Cus_No_sql); pstmt.executeUpdate();
	 * 
	 * conn.commit(); sql_result = "success"; } catch (SQLException e) {
	 * e.printStackTrace(); if (conn != null) { conn.rollback(); } sql_result =
	 * "error"; } finally { if (pstmt != null) { pstmt.close(); } if (conn != null)
	 * { conn.close(); } }
	 * 
	 * return sql_result; }
	 */
}
