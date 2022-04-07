package com.busience.productionLX.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;
import com.busience.productionLX.service.WorkOrderService;
import com.busience.salesLX.dto.Sales_OrderMasterList_tbl;
import com.busience.salesLX.dto.Sales_StockMat_tbl;

@RestController("workOrderRestController")
@RequestMapping("workOrderRest")
public class workOrderRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	WorkOrderService workOrderService;
	
	@Autowired
	JdbcTemplate jdbctemplate;

	// 위 그리드 재고수량
	@RequestMapping(value = "/MI_Search1", method = RequestMethod.GET)
	public List<Sales_StockMat_tbl> MI_Search1(HttpServletRequest request) throws SQLException {
		String workOrder_ItemCode = request.getParameter("workOrder_ItemCode");
		String sql = "select t1.*,t2.* from Sales_StockMatLX_tbl t1 inner join PRODUCT_INFO_TBL t2 on t1.Sales_SM_Code = t2.PRODUCT_ITEM_CODE where Sales_SM_Code='"
				+ workOrder_ItemCode + "'";

		System.out.println(sql);

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<Sales_StockMat_tbl> list = new ArrayList<Sales_StockMat_tbl>();
		while (rs.next()) {
			Sales_StockMat_tbl data = new Sales_StockMat_tbl();
			data.setSales_SM_Code(rs.getString("Sales_SM_Code"));
			data.setSales_SM_Name(rs.getString("PRODUCT_ITEM_NAME"));
			data.setSales_SM_Last_Qty(rs.getInt("Sales_SM_Last_Qty"));
			data.setSales_SM_In_Qty(rs.getInt("Sales_SM_In_Qty"));
			data.setSales_SM_Out_Qty(rs.getInt("Sales_SM_Out_Qty"));
			data.setSales_SM_Prcs_Date(rs.getString("Sales_SM_Prcs_Date"));
			list.add(data);
		}
		
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null) {
			conn.setAutoCommit(true);
			conn.close();
		}

		return list;
	}

	// 수주현황
	// ERROR : 작업지시 - 작업지시완료일을 입력하면 뒤에 조회되는 기능 : Sales_OrderMasterLX_tbl에 데이터가 많이 삭제되서 조회가 안됨 ex) A01001 코드가 없음
	@RequestMapping(value = "/MI_Search2", method = RequestMethod.GET)
	public List<Sales_OrderMasterList_tbl> MI_Search2(HttpServletRequest request) throws SQLException, ParseException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);

		String endDate = (String) obj.get("endDate");

		String sql = "select * from Sales_OrderMasterLX_tbl t1\r\n"
				+ "inner join Sales_OrderListLX_tbl t2 on t1.Sales_Order_mCus_No = t2.Sales_Order_lCus_No\r\n"
				+ "inner join Customer_tbl t3 on t1.Sales_Order_mCode=t3.Cus_Code\r\n"
				+ "inner join PRODUCT_INFO_TBL t4 on t2.Sales_Order_lCode = t4.PRODUCT_ITEM_CODE\r\n" + "where \r\n"
				+ "t1.Sales_Order_mDlvry_Date >= " + "'" + endDate + "'\r\n";

		if ((String) obj.get("Sales_Order_lCode") != null) {
			sql += "and\r\n";
			sql += "t2.Sales_Order_lCode =\r\n";
			sql += "'" + (String) obj.get("Sales_Order_lCode") + "'";
		}

		System.out.println(sql);

		List<Sales_OrderMasterList_tbl> list = new ArrayList<Sales_OrderMasterList_tbl>();

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Sales_OrderMasterList_tbl data = new Sales_OrderMasterList_tbl();
			data.setSales_Order_mCus_No(rs.getString("Sales_Order_mCus_No"));
			data.setSales_Order_mCode(rs.getString("Sales_Order_mCode"));
			data.setSales_Order_mName(rs.getString("Cus_Name"));
			data.setSales_Order_mDate(rs.getString("Sales_Order_mDate"));
			data.setSales_Order_mTotal(rs.getInt("Sales_Order_mTotal"));
			data.setSales_Order_mDlvry_Date(rs.getString("Sales_Order_mDlvry_Date"));
			data.setSales_Order_mRemarks(rs.getString("Sales_Order_mRemarks"));
			data.setSales_Order_mCheck(rs.getString("Sales_Order_mCheck"));
			data.setSales_Order_mModifier(rs.getString("Sales_Order_mModifier"));
			data.setSales_Order_mModify_Date(rs.getString("Sales_Order_mModify_Date"));
			data.setSales_Order_lNo(rs.getInt("Sales_Order_lNo"));
			data.setSales_Order_lCus_No(rs.getString("Sales_Order_lCus_No"));
			data.setSales_Order_lCode(rs.getString("Sales_Order_lCode"));
			data.setSales_Order_lName(rs.getString("PRODUCT_ITEM_NAME"));
			data.setSales_Order_lQty(rs.getInt("Sales_Order_lQty"));
			data.setSales_Order_lSum(rs.getInt("Sales_Order_lSum"));
			data.setSales_Order_lUnit_Price(rs.getInt("Sales_Order_lUnit_Price"));
			data.setSales_Order_lPrice(rs.getInt("Sales_Order_lPrice"));
			data.setSales_Order_lNot_Stocked(rs.getInt("Sales_Order_lNot_Stocked"));
			data.setSales_Order_Send_Clsfc(rs.getString("Sales_Order_Send_Clsfc"));
			data.setSales_Order_lInfo_Remark(rs.getString("Sales_Order_lInfo_Remark"));
			list.add(data);
		}
		
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null) {
			conn.setAutoCommit(true);
			conn.close();
		}

		return list;
	}

	//작업지시 미접수 리스트
	@GetMapping("/workOrderSelect")
	public List<WorkOrderDto> workOrderSelect(SearchDto searchDto) {
		return workOrderService.workOrderSelect(searchDto);
	}
	
	//작업지시 등록된 리스트
	@GetMapping("/workOrderSubSelect")
	public List<WorkOrderDto> workOrderSubSelect(SearchDto searchDto) {
		return workOrderService.workOrderSubSelect(searchDto);
	}
	
	//작업지시 등록
	@PostMapping("/workOrderRegister")
	public int workOrderRegister(@RequestBody List<WorkOrderDto> workOrderDtoList, Principal principal) {
		return workOrderService.workOrderRegister(workOrderDtoList, principal.getName());
	}
	
	//작업지시 테이블 설비 검색
	@GetMapping("/workOrderChoice")
	public List<WorkOrderDto> workOrderChoiceSelect(SearchDto searchDto) {
		return workOrderService.workOrderChoiceSelectDao(searchDto);
	}
	
	//작업지시 삭제
	@PostMapping("/workOrderDelete")
	public int workOrderDelete(WorkOrderDto workOrderDto) {
		return workOrderService.workOrderDelete(workOrderDto);
	}
}
