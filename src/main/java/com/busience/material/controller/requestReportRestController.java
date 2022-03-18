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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.material.dto.RequestListDto;
import com.busience.material.dto.RequestMasterDto;
import com.busience.material.dto.StockMat_tbl;

@RestController("requestReportRestController")
@RequestMapping("requestReportRest")
public class requestReportRestController {

	@Autowired
	DataSource dataSource;
	
	
	// RequestList
	@RequestMapping(value="/MRL_Search",method=RequestMethod.GET)
	public List<RequestMasterDto> MRL_Search(HttpServletRequest request) throws ParseException, SQLException {
		
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		String sql = " SELECT"
				+ " A.*,"
				+ " B.user_name,"
				+ " C.child_tbl_type dept_name\r\n"
				+ " FROM RequestMaster_tbl A\r\n"
				+ " INNER JOIN USER_INFO_TBL B ON A.Request_mUser = B.user_code\r\n"
				+ " INNER JOIN DTL_TBL C ON B.dept_code = C.child_tbl_no\r\n";
		
		String where = "where A.Request_mDate between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate") + " 23:59:59' "
				+ " and not A.Request_mCheck = 'Y'";
		
		sql += where;
		
		System.out.println("MRL_Search = " + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<RequestMasterDto> list = new ArrayList<RequestMasterDto>();		
		
		while (rs.next()) {
			RequestMasterDto data = new RequestMasterDto();
			data.setRequest_mReqNo(rs.getString("request_mReqNo"));
			data.setRequest_mUser(rs.getString("request_mUser"));
			data.setRequest_mDate(rs.getString("request_mDate"));
			data.setRequest_mRemarks(rs.getString("request_mRemarks"));
			data.setRequest_mCheck(rs.getString("request_mCheck"));
			data.setRequest_mModifier(rs.getString("request_mModifier"));
			data.setRequest_mModify_Date(rs.getString("request_mModify_Date"));
			data.setUser_Name(rs.getString("user_Name"));
			data.setDEPT_NAME(rs.getString("DEPT_NAME"));
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
		
	}
	
	// RequestListSub
	@RequestMapping(value="/MRLS_Search", method=RequestMethod.GET)
	public List<RequestListDto> MRLS_Search(HttpServletRequest request) throws SQLException {

		String sql = " select A.*,B.PRODUCT_ITEM_NAME Request_lName \r\n"
				+ " from RequestList_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Request_lCode = B.PRODUCT_ITEM_CODE\r\n"
				+ " WHERE A.Request_lReqNo = '"+request.getParameter("request_mReqNo")+"'\r\n"
				+ " order by Request_lNo";
		
		System.out.println("MRL_Search ="+sql);
		
		List<RequestListDto> list = new ArrayList<RequestListDto>();
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			RequestListDto data = new RequestListDto();
		
			data.setRequest_lNo(rs.getInt("request_lNo"));
			data.setRequest_lReqNo(rs.getString("request_lReqNo"));
			data.setRequest_lCode(rs.getString("request_lCode"));
			data.setRequest_lQty(rs.getInt("request_lQty"));
			data.setRequest_lInfo_Remark(rs.getString("request_lInfo_Remark"));
			data.setRequest_Send_Clsfc(rs.getString("request_Send_Clsfc"));
			data.setRequest_lName(rs.getString("request_lName"));
			
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
		
	}
	// RequestListStock
	@RequestMapping(value="/MRLSS_Search", method=RequestMethod.GET)
	public List<StockMat_tbl> MRLSS_Search(
			@RequestParam(value="request_lCode", required=false) String request_lCode) throws SQLException {
		List<StockMat_tbl> list = new ArrayList<StockMat_tbl>();
		
		String sql = " SELECT \r\n"
				+ " A.SM_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME SM_Name,\r\n"
				+ " B.PRODUCT_INFO_STND_1 SM_STND_1,\r\n"
				+ " A.SM_Last_Qty+SM_In_Qty-A.SM_Out_Qty SM_Qty\r\n"
				+ " FROM StockMat_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B ON A.SM_Code = B.PRODUCT_ITEM_CODE";
		
		String where = " where A.SM_Code = '"+request_lCode+"'";
		
		sql += where;
		System.out.println("MRLSS_Search = " + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int i = 0;
		while (rs.next()) {
			StockMat_tbl data = new StockMat_tbl();
			i++;
			data.setId(i);
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
}
