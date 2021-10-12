package com.busience.materialLX.controller;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.materialLX.dto.InMat_tbl;
import com.busience.materialLX.dto.OutMat_tbl;

@RestController("matOutReturnLXRestController")
@RequestMapping("matOutReturnLXRest")
public class matOutReturnLXRestController {

	@Autowired
	DataSource dataSource;
	
	// MORI_Search
	@RequestMapping(value = "/MORI_Search", method = RequestMethod.GET)
	public List<OutMat_tbl> MORI_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();

		String sql = " SELECT\r\n"
				+ " A.OutMat_No,\r\n"
				+ " A.OutMat_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME OutMat_Name,\r\n"
				+ " sum(OutMat_Qty) OutMat_Qty,\r\n"
				+ " A.OutMat_Send_Clsfc,\r\n"
				+ " E.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
				+ " A.OutMat_Consignee,\r\n"
				+ " A.OutMat_Date,\r\n"
				+ " A.OutMat_dInsert_Time,\r\n"
				+ " A.OutMat_Modifier\r\n"
				+ " FROM OutMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.OutMat_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join DTL_TBL E on A.OutMat_Send_Clsfc = E.CHILD_TBL_NO\r\n";
				
		//품목별 검색
		if (obj.get("outMat_Code") != null && !obj.get("outMat_Code").equals("")) {
			sql += " where A.OutMat_Code like '%" + obj.get("outMat_Code") + "%'";
		}
		
		sql += " group by A.OutMat_Code having OutMat_Qty > 0\r\n";
		
		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			OutMat_tbl data = new OutMat_tbl();
			
			data.setOutMat_No(rs.getInt("OutMat_No"));
			data.setOutMat_Code(rs.getString("OutMat_Code"));
			data.setOutMat_Qty(rs.getInt("OutMat_Qty"));
			data.setOutReturn_Qty(0);
			data.setOutMat_Send_Clsfc(rs.getString("OutMat_Send_Clsfc"));
			data.setOutMat_Send_Clsfc_Name(rs.getString("OutMat_Send_Clsfc_Name"));
			data.setOutMat_Consignee(rs.getString("OutMat_Consignee"));
			data.setOutMat_Date(rs.getString("OutMat_Date"));
			data.setOutMat_dInsert_Time(rs.getString("OutMat_dInsert_Time"));
			data.setOutMat_Modifier(rs.getString("OutMat_Modifier"));
			data.setOutMat_Name(rs.getString("OutMat_Name"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	// MORS_Search
	@RequestMapping(value = "/MORS_Search", method = RequestMethod.GET)
	public List<OutMat_tbl> MORS_Search(HttpServletRequest request) throws ParseException, SQLException {
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		List<OutMat_tbl> list = new ArrayList<OutMat_tbl>();

		String sql = " SELECT\r\n"
				+ " A.OutMat_No,\r\n"
				+ " A.OutMat_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME OutMat_Name,\r\n"
				+ " A.OutMat_Qty OutReturn_Qty,\r\n"
				+ " A.OutMat_Send_Clsfc,\r\n"
				+ " E.CHILD_TBL_TYPE OutMat_Send_Clsfc_Name,\r\n"
				+ " A.OutMat_Consignee,\r\n"
				+ " A.OutMat_Date,\r\n"
				+ " A.OutMat_dInsert_Time,\r\n"
				+ " A.OutMat_Modifier\r\n"
				+ " FROM OutMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.OutMat_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join DTL_TBL E on A.OutMat_Send_Clsfc = E.CHILD_TBL_NO\r\n";
				
		
		String where = " where A.OutMat_dInsert_Time between '"+obj.get("startDate")+" 00:00:00' and '"+obj.get("endDate")+" 23:59:59' \r\n";
		
		//품목별 검색
		if (obj.get("outMat_Code") != null && !obj.get("outMat_Code").equals("")) {
			where += " and A.OutMat_Code like '%" + obj.get("outMat_Code") + "%'";
		}
		
		where += " and A.OutMat_Send_Clsfc = '210'";
		
		
		sql += where;
		
		System.out.println(sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			OutMat_tbl data = new OutMat_tbl();
			
			data.setOutMat_No(rs.getInt("OutMat_No"));
			data.setOutMat_Code(rs.getString("OutMat_Code"));
			data.setOutReturn_Qty(rs.getInt("OutReturn_Qty"));
			data.setOutMat_Send_Clsfc(rs.getString("OutMat_Send_Clsfc"));
			data.setOutMat_Send_Clsfc_Name(rs.getString("OutMat_Send_Clsfc_Name"));
			data.setOutMat_Consignee(rs.getString("OutMat_Consignee"));
			data.setOutMat_Date(rs.getString("OutMat_Date"));
			data.setOutMat_dInsert_Time(rs.getString("OutMat_dInsert_Time"));
			data.setOutMat_Modifier(rs.getString("OutMat_Modifier"));
			data.setOutMat_Name(rs.getString("OutMat_Name"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	//MORSL_Search
	@RequestMapping(value = "/MORSL_Search", method = RequestMethod.GET)
	public List<InMat_tbl> MORSL_Search(HttpServletRequest request) throws org.json.simple.parser.ParseException, SQLException{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();
		
		String sql = " SELECT\r\n"
				+ " A.InMat_No,\r\n"
				+ " A.InMat_Order_No,\r\n"
				+ " A.InMat_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME InMat_Name,\r\n"
				+ " (C.SM_Last_Qty+C.SM_In_Qty-C.SM_Out_Qty) InMat_Qty,\r\n"
				+ " A.InMat_Unit_Price,\r\n"
				+ " A.InMat_Price,\r\n"
				+ " A.InMat_Client_Code,\r\n"
				+ " D.Cus_Name InMat_Client_Name,\r\n"
				+ " A.InMat_Rcv_Clsfc,\r\n"
				+ " E.CHILD_TBL_TYPE InMat_Rcv_Clsfc_Name,\r\n"
				+ " A.InMat_Date,\r\n"
				+ " A.InMat_dInsert_Time,\r\n"
				+ " A.InMat_Modifier\r\n"
				+ " FROM InMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.InMat_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ " left outer join StockMatLX_tbl C ON A.InMat_Code = C.SM_Code\r\n"
				+ " inner join Customer_tbl D on A.InMat_Client_Code = D.Cus_Code\r\n"
				+ " inner join DTL_TBL E on A.InMat_Rcv_Clsfc = E.CHILD_TBL_NO\r\n";
		
		String where = " where C.SM_Last_Qty+C.SM_In_Qty-C.SM_Out_Qty > 0 and A.InMat_Rcv_Clsfc <> '175'\r\n";
				
		if (obj.get("inMat_Code") != null && !obj.get("inMat_Code").equals("")) {
			where += " and A.InMat_Code like '%" + obj.get("inMat_Code") + "%'";
		}
		
		
		sql += where;
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int i = 0;
		
		while (rs.next()) {
			InMat_tbl data = new InMat_tbl();
			
			i++;
			data.setInMat_No(i);
			data.setInMat_Order_No(rs.getString("inMat_Order_No"));
			data.setInMat_Code(rs.getString("inMat_Code"));
			data.setInMat_Name(rs.getString("inMat_Name"));
			data.setInMat_Qty(rs.getInt("inMat_Qty"));
			data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
			data.setInMat_Price(rs.getInt("inMat_Price"));
			data.setInMat_Client_Code(rs.getString("inMat_Client_Code"));
			data.setInMat_Client_Name(rs.getString("inMat_Client_Name"));
			data.setInMat_Date(rs.getString("inMat_Date"));
			data.setInMat_Rcv_Clsfc(rs.getString("inMat_Rcv_Clsfc"));
			data.setInMat_Rcv_Clsfc_Name(rs.getString("inMat_Rcv_Clsfc_Name"));
			data.setInMat_dInsert_Time(rs.getString("inMat_dInsert_Time"));
			data.setInMat_Modifier(rs.getString("inMat_Modifier"));
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//MORI_Save
	@GetMapping("/MORI_Save")
	public String MORI_Save(HttpServletRequest request, Principal principal) throws ParseException, SQLException{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		
		String modifier = principal.getName();
		
		String OutMatLX_tbl_sql = null;
		String StockMatLX_tbl_sql = null;
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql_result = null;

		try {
			conn.setAutoCommit(false);

			for (int i = 0; i < arr.size(); i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
								
				// 출고 테이블에서 반품하려는 랏번호의 다음 순번을 구해준다.
				String sql = "SELECT MAX(OutMat_No)+1 OutMat_No\r\n"
						+ " FROM OutMatLX_tbl\r\n"
						+ " WHERE OutMat_Code ='"+ obj.get("outMat_Code") + "'";
				
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				//순번
				int OutMat_No = 0;
				while(rs.next()) {
					OutMat_No = rs.getInt("OutMat_No");
				}
				
				
				// 출고 테이블에 저장한다.
				OutMatLX_tbl_sql = "INSERT INTO OutMatLX_tbl\r\n"
						+ "(OutMat_No,\r\n"
						+ "OutMat_Code,\r\n"
						+ "OutMat_Qty,\r\n"
						+ "OutMat_Date,\r\n"
						+ "OutMat_Send_Clsfc,\r\n"
						+ "OutMat_Consignee,\r\n"
						+ "OutMat_dInsert_Time,\r\n"
						+ "OutMat_Modifier\r\n"
						+ ")VALUES(\r\n"
						+ "(SELECT OutMat_No+1 FROM OutMatLX_tbl omt "
						+ "WHERE OutMat_Code = '" + obj.get("outMat_Code")+ "' "
						+ " ORDER BY OutMat_No DESC LIMIT 1),\r\n"
						+ "'"+ obj.get("outMat_Code") + "',\r\n"
						+ " -"+ obj.get("outReturn_Qty") + ",\r\n"
						+ "'"+obj.get("outMat_Date")+"',\r\n"
						+ "'183',\r\n"
						+ "'"+ obj.get("outMat_Consignee") + "',\r\n"
						+ " now(),\r\n"
						+ "'"+ modifier + "'"
						+ ")";
				
				System.out.println("OutMatLX_tbl_sql = " + OutMatLX_tbl_sql);
				pstmt = conn.prepareStatement(OutMatLX_tbl_sql);
				pstmt.executeUpdate();
				
				//StockMat_tbl 테이블에 업데이트
				StockMatLX_tbl_sql = "UPDATE StockMatLX_tbl\r\n"
					+ " SET SM_Out_Qty=SM_Out_Qty-"+obj.get("outReturn_Qty")
					+ " WHERE SM_Code = '"+obj.get("outMat_Code")+"'";
				
				System.out.println("StockMatLX_tbl_sql = " + StockMatLX_tbl_sql);
				pstmt = conn.prepareStatement(StockMatLX_tbl_sql);
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
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		
		return sql_result;
	}
	//MORSL_Save
	@RequestMapping(value = "/MORSL_Save", method = RequestMethod.GET)
	public String MORSL_Save(HttpServletRequest request, Principal principal) throws SQLException, ParseException{
		String originData = request.getParameter("data");
		
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		
		String modifier = principal.getName();
		
		String OutMatLX_tbl_sql = null;
		String InMatLX_tbl_sql = null;
		String StockMatLX_tbl_sql = null;
		String SalesLX_tbl_sql = null;
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql_result = null;
		
		try{
			//트占쏙옙占쏙옙占� 占쏙옙占쏙옙
			conn.setAutoCommit(false);
			
			if(arr.size() > 0){
				for (int i = 0; i < arr.size(); i++){
					JSONObject obj = (JSONObject) arr.get(i);
										
					// 출고 테이블에서 반품하려는 랏번호의 다음 순번을 구해준다.
					String sql = "SELECT MAX(OutMat_No)+1 OutMat_No FROM OutMatLX_tbl"
							+ " WHERE OutMat_Code ='"+ obj.get("sales_Code") + "'";
					
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					int OutMat_No = 0;
					
					while(rs.next()) {
						OutMat_No = rs.getInt("OutMat_No");
					}
					
					if(OutMat_No==0) {
						OutMat_No = 1;
					}
					
					// 출고 테이블에 저장한다.
					OutMatLX_tbl_sql = "INSERT INTO OutMatLX_tbl\r\n"
							+ "(OutMat_No,\r\n"
							+ "OutMat_Code,\r\n"
							+ "OutMat_Qty,\r\n"
							+ "OutMat_Send_Clsfc,\r\n"
							+ "OutMat_Date,\r\n"
							+ "OutMat_dInsert_Time,\r\n"
							+ "OutMat_Modifier)\r\n"
							+ "VALUES(\r\n"
							+ "(SELECT OutMat_No+1 FROM OutMatLX_tbl omt "
							+ "WHERE OutMat_Code = '" + obj.get("sales_Code")+ "' "
							+ " ORDER BY OutMat_No DESC LIMIT 1),\r\n"
							+ "'"+ obj.get("sales_Code") + "',\r\n"
							+ ""+ obj.get("sales_Qty") + ",\r\n"
							+ "'193',\r\n"
							+ "'"+obj.get("inMat_Date")+"',\r\n"
							+ "now(),"
							+ "'"+ modifier + "'"
							+ ")";
					
					System.out.println("OutMatLX_tbl_sql ="+ OutMatLX_tbl_sql);
					pstmt = conn.prepareStatement(OutMatLX_tbl_sql);
					pstmt.executeUpdate();
					
					// 입고테이블 LX에 insert
					InMatLX_tbl_sql = " insert into InMatLX_tbl(\r\n"
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
							+ " InMat_Modifier\r\n"
							+ " ) value (\r\n"
							+ " (select imtsb.InMat_No+1 from InMatLX_tbl imtsb"
								+ "	WHERE imtsb.InMat_Code = '" + obj.get("sales_Code")+ "'"
								+ "	and imtsb.InMat_Date = '" + obj.get("inMat_Date") + "'"
								+ "	order by imtsb.InMat_No DESC LIMIT 1),\r\n"
							+ "(SELECT imt.InMat_Order_No FROM InMatLX_tbl imt "
								+ "WHERE imt.InMat_Code = '" + obj.get("sales_Code")+ "'" + " and imt.InMat_Date = '" + obj.get("inMat_Date") + "'"
								+ " ORDER BY imt.InMat_No DESC LIMIT 1),\r\n"
							+ " '"+obj.get("sales_Code")+"',\r\n"
							+ " -"+obj.get("sales_Qty")+",\r\n"
							+ " "+obj.get("sales_Unit_Price")+",\r\n"
							+ " -"+obj.get("sales_Qty")+"*"+obj.get("sales_Unit_Price")+",\r\n"
							+ " '"+obj.get("sales_Client_Code")+"',\r\n"
							+ " '"+obj.get("inMat_Date")+"',\r\n"
							+ "	'175',\r\n"
							+ " now(),\r\n"
							+ " '"+modifier+"')";
					
					
					pstmt = conn.prepareStatement(InMatLX_tbl_sql);
					pstmt.executeUpdate();
					
					StockMatLX_tbl_sql = "UPDATE StockMatLX_tbl SET SM_Out_Qty=SM_Out_Qty+"+obj.get("sales_Qty")
									+" WHERE SM_Code = '"+obj.get("sales_Code")+"'";
	
					System.out.println("StockMatLX_tbl_sql ="+StockMatLX_tbl_sql);
					pstmt = conn.prepareStatement(StockMatLX_tbl_sql);
					pstmt.execute();
					
					SalesLX_tbl_sql = "INSERT INTO SalesLX_tbl(\r\n"
							+ "`Sales_No`,\r\n"
							+ "`Sales_Num`,\r\n"
							+ "`Sales_Code`,\r\n"
							+ "`Sales_Client_Code`,\r\n"
							+ "`Sales_Qty`,\r\n"
							+ "`Sales_Unit_Price`,\r\n"
							+ "`Sales_Price`,\r\n"
							+ "`Sales_Send_Clsfc`,\r\n"
							+ "`Sales_Modifier`,\r\n"
							+ "`Sales_Modify_Date`)\r\n"
							+ "VALUES\r\n"
							+ "("
							+ ""+ OutMat_No + "\r\n,\r\n"
							+ "concat(date_format(now(),'%y%m%d'),'"+obj.get("sales_Code")+"',lpad("+OutMat_No+",2,'0')),"
							+ "'"+ obj.get("sales_Code") + "',\r\n"
							+ "'"+ obj.get("sales_Client_Code") + "',\r\n"
							+ ""+ obj.get("sales_Qty") + ",\r\n"
							+ "'"+ obj.get("sales_Unit_Price") + "',\r\n"
							+ "'"+ obj.get("sales_Price") + "',\r\n"
							+ "'193',\r\n"
							+ "'"+ modifier + "',\r\n"
							+ "now())";
					
					System.out.println("SalesLX_tbl_sql ="+ SalesLX_tbl_sql);
					pstmt = conn.prepareStatement(SalesLX_tbl_sql);
					pstmt.executeUpdate();
				}
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
