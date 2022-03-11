package com.busience.material.controller;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.material.Dto.RequestList_tbl;
import com.material.Dto.RequestMaster_tbl;
import com.material.Dto.StockMat_tbl;

@RestController("matRequestRestController")
@RequestMapping("matRequestRest")
public class matRequestRestController {

	@Autowired
	SimpleDriverDataSource dataSource;
	
	@RequestMapping(value = "/MR_Search",method = RequestMethod.GET)
	public List<RequestMaster_tbl> MR_Search(HttpServletRequest request) throws ParseException, SQLException
	{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		String sql = " SELECT A.Request_mReqNo,\r\n"
				+ " A.Request_mUser,\r\n"
				+ " B.user_name,\r\n"
				+ " C.CHILD_TBL_NO dept_code,\r\n"
				+ " C.child_tbl_type dept_name,\r\n"
				+ " date_format(A.Request_mDate,'%Y-%m-%d %T') Request_mDate,\r\n"
				+ " A.Request_mRemarks,\r\n"
				+ " A.Request_mCheck,\r\n"
				+ " A.Request_mModifier,\r\n"
				+ " date_format(A.Request_mModify_Date,'%Y-%m-%d %T') Request_mModify_Date\r\n"
				+ " FROM RequestMaster_tbl A\r\n"
				+ " INNER JOIN USER_INFO_TBL B ON A.Request_mUser = B.user_code\r\n"
				+ " INNER JOIN DTL_TBL C ON B.dept_code = C.child_tbl_no\r\n";
		
		String where = "where A.Request_mDate between '" + obj.get("startDate") + " 00:00:00' and '" + obj.get("endDate") + " 23:59:59'"
				+ " and not A.Request_mCheck = 'Y'\r\n"
				+ " order by Request_mDate";
		
		sql += where;
		
		System.out.println("MR_Search ="+sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int i = 0;
		List<RequestMaster_tbl> list = new ArrayList<RequestMaster_tbl>();		
		
		while (rs.next()) {
			RequestMaster_tbl data = new RequestMaster_tbl();
			i++;
			data.setID(i);
			data.setRequest_mReqNo(rs.getString("request_mReqNo"));
			data.setRequest_mUser(rs.getString("request_mUser"));
			data.setRequest_mDate(rs.getString("request_mDate"));
			data.setRequest_mRemarks(rs.getString("request_mRemarks"));
			data.setRequest_mCheck(rs.getString("request_mCheck"));
			data.setRequest_mModifier(rs.getString("request_mModifier"));
			data.setRequest_mModify_Date(rs.getString("request_mModify_Date"));
			data.setUser_Name(rs.getString("user_Name"));
			data.setDEPT_CODE(rs.getString("dept_code"));
			data.setDEPT_NAME(rs.getString("dept_name"));
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	@RequestMapping(value = "/MRL_Search", method = RequestMethod.GET)
	public List<RequestList_tbl> MRL_Search(HttpServletRequest request) throws SQLException
	{
		String sql = " select A.*,B.PRODUCT_ITEM_NAME Request_lName \r\n"
				+ " from RequestList_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.Request_lCode = B.PRODUCT_ITEM_CODE\r\n"
				+ " WHERE A.Request_lReqNo = '"+request.getParameter("request_mReqNo")+"'\r\n"
				+ " order by Request_lNo";
		
		System.out.println("MRL_Search ="+sql);
		
		int i = 0;
		List<RequestList_tbl> list = new ArrayList<RequestList_tbl>();
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			RequestList_tbl data = new RequestList_tbl();
			
			i++;
			data.setID(i);
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
	
	@RequestMapping(value = "/MRS_Search", method = RequestMethod.GET)
	public List<StockMat_tbl> MRS_Search(
			@RequestParam(value = "request_lCode", required = false) String request_lCode) throws SQLException {
		List<StockMat_tbl> list = new ArrayList<StockMat_tbl>();

		String sql = " SELECT \r\n"
				+ " A.SM_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME SM_Name,\r\n"
				+ " B.PRODUCT_INFO_STND_1 SM_STND_1,\r\n"
				+ " A.SM_Last_Qty+A.SM_In_Qty-A.SM_Out_Qty SM_Qty\r\n"
				+ " FROM StockMat_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B ON A.SM_Code = B.PRODUCT_ITEM_CODE";
		
		String where = " where A.SM_Code = '"+request_lCode+"'";
		
		sql += where;
		System.out.println("MOS_Search ="+sql);

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
	
	@RequestMapping(value = "/MR_Print", method = RequestMethod.GET)
	public String MR_Print(HttpServletRequest request) throws SQLException
	{
		String sql = "select * from RequestMaster_tbl where Request_mReqNo='"+request.getParameter("request_lReqNo")+"'";
		
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int i = 0;
		
		while (rs.next()) 
			i = 1;
		
		System.out.println(i);
		
		if(i==0)
			return "NO";
		return "OK";
	}
	
	//orderList delete
		@RequestMapping(value = "/MRL_Delete", method = RequestMethod.POST)
		public String MRL_Delete(HttpServletRequest request, Model model)
				throws ParseException, SQLException, UnknownHostException, ClassNotFoundException, org.json.simple.parser.ParseException {
			String originData = request.getParameter("data");
			JSONParser parser = new JSONParser();
			JSONArray arr = (JSONArray) parser.parse(originData);
			
			String request_mReqNo = null;
			String RS_delete_sql = null;
			String RM_delete_sql = null;
			String ReqNo_sql = null;
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql_result = null;
			
			try {
				conn = dataSource.getConnection();
				conn.setAutoCommit(false);
				
				for(int i=0;i<arr.size();i++) {
					JSONObject obj = (JSONObject) arr.get(i);
					System.out.println(obj);
					
					request_mReqNo = (String) obj.get("request_lReqNo");
					
					RS_delete_sql = " delete from RequestList_tbl"
							+ " where Request_lNo = '"+obj.get("request_lNo")+"'"
							+ " and Request_lReqNo ='"+obj.get("request_lReqNo")+"'";
					
					System.out.println("RS_delete_sql = " + RS_delete_sql);
					pstmt = conn.prepareStatement(RS_delete_sql);
					pstmt.executeUpdate();
				}
				
				RM_delete_sql = "delete from RequestMaster_tbl\r\n"
						+ " where Request_mReqNo = '"+request_mReqNo+"'"
						+ " and not exists ("
						+ " SELECT * FROM RequestList_tbl"
						+ " WHERE Request_lReqNo = '"+request_mReqNo+"'"
						+ " )";
				
				//삭제후 list 순번을 재정리 해준다.
				ReqNo_sql = " UPDATE RequestList_tbl A, (SELECT @rank:=0) B"
						+ " SET A.Request_lNo = @rank:=@rank+1\r\n"
						+ " where A.Request_lReqNo = '"+request_mReqNo+"';";
				
				System.out.println("RM_delete_sql = " + RM_delete_sql);
				pstmt = conn.prepareStatement(RM_delete_sql);
				pstmt.executeUpdate();
				
				System.out.println("ReqNo_sql = " + ReqNo_sql);
				pstmt = conn.prepareStatement(ReqNo_sql);
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
	
	@RequestMapping(value = "/MRL_Save", method = RequestMethod.POST)
	public String MRL_Save(HttpServletRequest request) throws ParseException, SQLException{
		JSONParser parser = new JSONParser();
		
		//masterData
		String masterData = request.getParameter("masterData");
		JSONObject masterobj = (JSONObject) parser.parse(masterData);
		System.out.println(masterobj);
		
		//listData
		String listData = request.getParameter("listData");
		JSONArray listarr = (JSONArray) parser.parse(listData);

		HttpSession session = request.getSession();
		String modifier = (String) session.getAttribute("id");
		
		String ReqNo_sql = null;
		String RequestMaster_tbl_sql = null;
		String RequestList_tbl_sql = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//요청번호를 알아내야함
		String ReqNo = (String) masterobj.get("request_mReqNo");

		String sql_result = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			//요청가 없을경우 (master에 새로 저장되는경우) 요청번호를 얻어냄
			if(ReqNo.equals("")) {
				ReqNo_sql = " select concat("+masterobj.get("dept_CODE")+",'-',date_format(now(),'%y%m%d'),'-',\r\n"
						+ " lpad((select * from ("
							+ " select count(*)+1 from RequestMaster_tbl "
							+ " where left(Request_mReqNo,2) = '"+masterobj.get("dept_CODE")+"' and Request_mDate >= curdate())"
						+ " AA),2,'0'))\r\n";
				
				System.out.println("ReqNo_sql = " + ReqNo_sql);
				pstmt = conn.prepareStatement(ReqNo_sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ReqNo = rs.getString(1);
				}
			}
			
			RequestMaster_tbl_sql = " INSERT INTO RequestMaster_tbl(\r\n"
					+ " Request_mReqNo,\r\n"
					+ " Request_mUser,\r\n"
					+ " Request_mDate,\r\n"
					+ " Request_mRemarks,\r\n"
					+ " Request_mModifier,\r\n"
					+ " Request_mModify_Date\r\n"
					+ " ) VALUES(\r\n"
					+ " '"+ReqNo+"',\r\n"
					+ " '"+masterobj.get("request_mUser")+"',\r\n"
					+ " '"+masterobj.get("request_mDate")+"',\r\n"
					+ " '"+masterobj.get("request_mRemarks")+"',\r\n"
					+ " '"+modifier+"',\r\n"
					+ " now()\r\n"
					+ " ) on duplicate key\r\n"
					+ " update"
					+ " Request_mRemarks ='"+masterobj.get("request_mRemarks")+"',"
					+ " Request_mModifier ='"+modifier+"',"
					+ " Request_mModify_Date = now()";
			
			System.out.println("RequestMaster_tbl_sql ="+RequestMaster_tbl_sql);
			pstmt = conn.prepareStatement(RequestMaster_tbl_sql);
			pstmt.executeUpdate();
			
			//RequestList_tbl
			for(int i=0;i<listarr.size();i++) {
				JSONObject listobj = (JSONObject) listarr.get(i);
				System.out.println(listobj);
				
				RequestList_tbl_sql = "INSERT INTO RequestList_tbl\r\n"
						+ "(Request_lNo,\r\n"
						+ "Request_lReqNo,\r\n"
						+ "Request_lCode,\r\n"
						+ "Request_lQty,\r\n"
						+ "Request_lNot_Stocked,\r\n"
						+ "Request_lInfo_Remark,\r\n"
						+ "Request_Send_Clsfc)\r\n"
						+ "VALUES\r\n"
						+ "(\r\n"
						+ ""+listobj.get("request_lNo")+",\r\n"
						+ "'"+ReqNo+"',\r\n"
						+ "'"+listobj.get("request_lCode")+"',\r\n"
						+ ""+listobj.get("request_lQty")+",\r\n"
						+ ""+listobj.get("request_lQty")+",\r\n"
						+ "'"+listobj.get("request_lInfo_Remark")+"',\r\n"
						+ "'"+listobj.get("request_Send_Clsfc")+"'\r\n"
						+ ") on duplicate key\r\n"
						+ "update \r\n"
						+ "Request_lNo ="+listobj.get("request_lNo")+",\r\n"
						+ "Request_lQty ="+listobj.get("request_lQty")+",\r\n"
						+ "Request_lNot_Stocked ="+listobj.get("request_lQty")+",\r\n"
						+ "Request_lInfo_Remark ='"+listobj.get("request_lInfo_Remark")+"',\r\n"
						+ "Request_Send_Clsfc ='"+listobj.get("request_Send_Clsfc")+"'\r\n";

				
				System.out.println("RequestList_tbl_sql ="+RequestList_tbl_sql);
				pstmt = conn.prepareStatement(RequestList_tbl_sql);
				pstmt.executeUpdate();
			}
			
			conn.commit();
			sql_result = ReqNo;
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn!=null) {
				conn.rollback();
			}
			sql_result = "error";
		}finally {
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