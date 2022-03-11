package com.busience.material.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.material.dto.InMat_tbl;

@RestController("matInReturnLXRestController")
@RequestMapping("matInReturnLXRest")
public class matInReturnLXRestController {

	@Autowired
	DataSource dataSource;
	
	//MIRI_Search
	@RequestMapping(value = "/MIRI_Search", method = RequestMethod.GET)
	public List<InMat_tbl> MIRI_Search(HttpServletRequest request, Model model) throws ParseException, SQLException{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		
		List<InMat_tbl> list = new ArrayList<InMat_tbl>();
		
		System.out.println(obj);
		
		String where = "";
		
		String sql = " SELECT\r\n"
				+ " A.InMat_No,\r\n"
				+ " A.InMat_Order_No,\r\n"
				+ " A.InMat_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME InMat_Name,\r\n"
				+ " sum(A.InMat_Qty) InMat_Qty,\r\n"
				+ " A.InMat_Unit_Price,\r\n"
				+ " A.InMat_Price,\r\n"
				+ " smt.SM_Last_Qty+smt.SM_In_Qty-smt.SM_Out_Qty InMat_Stock_Qty,\r\n"
				+ " A.InMat_Client_Code,\r\n"
				+ " D.Cus_Name InMat_Client_Name,\r\n"
				+ " A.InMat_Rcv_Clsfc,\r\n"
				+ " E.CHILD_TBL_TYPE InMat_Rcv_Clsfc_Name,\r\n"
				+ " date_format(A.InMat_Date,'%Y-%m-%d %T') InMat_Date,\r\n"
				+ " date_format(A.InMat_dInsert_Time,'%Y-%m-%d %T') InMat_dInsert_Time,\r\n"
				+ " A.InMat_Modifier\r\n"
				+ " FROM InMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.InMat_Code = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join Customer_tbl D on A.InMat_Client_Code = D.Cus_Code\r\n"
				+ " inner join DTL_TBL E on A.InMat_Rcv_Clsfc = E.CHILD_TBL_NO\r\n"
				+ " inner join StockMatLX_tbl smt on A.InMat_Code = smt.SM_Code\r\n";
		
				
		if (obj.get("inMat_Code") != null && !obj.get("inMat_Code").equals("")) {
			where += " and A.InMat_Code like '%" + obj.get("inMat_Code") + "%'";
		} 
		
		if (obj.get("inMat_Client_Code") != null && !obj.get("inMat_Client_Code").equals("")) {
			where += " and A.InMat_Client_Code like '%" + obj.get("inMat_Client_Code") + "%'";
		} 
		
		where += " GROUP BY InMat_Order_No, InMat_Code";
		
		sql += where;
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		
		
		int i = 0;
		
		while (rs.next()) {
			InMat_tbl data = new InMat_tbl();
			
			i++;
			data.setID(i);
			data.setInMat_Order_No(rs.getString("inMat_Order_No"));
			data.setInMat_Code(rs.getString("inMat_Code"));
			data.setInMat_Name(rs.getString("inMat_Name"));
			data.setInMat_Qty(rs.getInt("inMat_Qty"));
			data.setInReturn_Qty(0);
			data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
			data.setInMat_Price(rs.getInt("inMat_Price"));
			data.setInMat_Stock_Qty(rs.getInt("inMat_Stock_Qty"));
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
		
		model.addAttribute("value", list);
		
		return list;
	}
	
	//MIRS_Search
	@RequestMapping(value = "/MIRS_Search", method = RequestMethod.GET)
	public List<InMat_tbl> MIRS_Search(HttpServletRequest request) throws org.json.simple.parser.ParseException, SQLException{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(originData);
		System.out.println(obj);
		
		String sql = " SELECT\r\n"
				+ " A.InMat_No,\r\n"
				+ " A.InMat_Order_No,\r\n"
				+ " A.InMat_Code,\r\n"
				+ " B.PRODUCT_ITEM_NAME InMat_Name,\r\n"
				+ " A.InMat_Qty InReturn_Qty,\r\n"
				+ " A.InMat_Unit_Price,\r\n"
				+ " A.InMat_Client_Code,\r\n"
				+ " C.Cus_Name InMat_Client_Name,\r\n"
				+ " A.InMat_Rcv_Clsfc,\r\n"
				+ " D.CHILD_TBL_TYPE InMat_Rcv_Clsfc_Name,\r\n"
				+ " date_format(A.InMat_Date,'%Y-%m-%d %T') InMat_Date,\r\n"
				+ " date_format(A.InMat_dInsert_Time,'%Y-%m-%d %T') InMat_dInsert_Time,\r\n"
				+ " A.InMat_Modifier\r\n"
				+ " FROM InMatLX_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.InMat_Code = B.PRODUCT_ITEM_CODE \r\n"
				+ " inner join Customer_tbl C on A.InMat_Client_Code = C.Cus_Code\r\n"
				+ " inner join DTL_TBL D on A.InMat_Rcv_Clsfc = D.CHILD_TBL_NO  \r\n";
		
		String where = " where A.InMat_dInsert_Time between '"+obj.get("startDate")+" 00:00:00' and '"+obj.get("endDate")+" 23:59:59' \r\n";
				
		if (obj.get("inMat_Code") != null && !obj.get("inMat_Code").equals("")) {
			where += " and A.InMat_Code like '%" + obj.get("inMat_Code") + "%'";
		}
		
		if (obj.get("inMat_Client_Code") != null && !obj.get("inMat_Client_Code").equals("")) {
			where += " and A.InMat_Client_Code like '%" + obj.get("inMat_Client_Code") + "%'";
		}
		
		where += " and A.InMat_Rcv_Clsfc = '207'";
		
		sql += where;
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<InMat_tbl> list = new ArrayList<InMat_tbl>();
		
		int i = 0;
		
		while (rs.next()) {
			InMat_tbl data = new InMat_tbl();
			
			i++;
			data.setID(i);
			data.setInMat_Order_No(rs.getString("inMat_Order_No"));
			data.setInMat_Code(rs.getString("inMat_Code"));
			data.setInMat_Name(rs.getString("inMat_Name"));
			data.setInReturn_Qty(rs.getInt("inReturn_Qty"));
			data.setInMat_Unit_Price(rs.getInt("inMat_Unit_Price"));
			data.setInMat_Price(rs.getInt("inReturn_Qty")*rs.getInt("inMat_Unit_Price"));
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
	
	//MIRI_Save
	@GetMapping("/MIRI_Save")
	public String MIRI_Save(HttpServletRequest request, Principal principal) throws org.json.simple.parser.ParseException, SQLException{
		String originData = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		
		String modifier = principal.getName();
		
		String InMat_tbl_sql = null;
		String StockMat_tbl_sql = null;
		
		Connection conn = null;                                    

		PreparedStatement pstmt = null;
		
		String sql_result = null;
		
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			for(int i=0;i<arr.size();i++) {
				
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
			
	
				//InMatLX_tbl에 insert
				InMat_tbl_sql = " insert into InMatLX_tbl(\r\n"
						+ " InMat_No,\r\n"
						+ " InMat_Order_No,\r\n"
						+ " InMat_Code,\r\n"
						+ " InMat_Qty,\r\n"
						+ " InMat_Unit_Price,\r\n"
						+ " inMat_Price,\r\n"
						+ " InMat_Client_Code,\r\n"
						+ " InMat_Date,\r\n"
						+ " InMat_Rcv_Clsfc,\r\n"
						+ " InMat_dInsert_Time,\r\n"
						+ " InMat_Modifier\r\n"
						+ " ) value (\r\n"
						+ " (select InMat_No+1 from InMatLX_tbl A "
							+ "	where A.InMat_Order_No = '"+obj.get("inMat_Order_No")+"' "
							+ "	and A.InMat_Code = '"+obj.get("inMat_Code")+"' "
							+ "	order by InMat_No DESC LIMIT 1),\r\n"
						+ " '"+obj.get("inMat_Order_No")+"',\r\n"
						+ " '"+obj.get("inMat_Code")+"',\r\n"
						+ " -"+obj.get("inReturn_Qty")+",\r\n"
						+ " "+obj.get("inMat_Unit_Price")+",\r\n"
						+ " -"+obj.get("inReturn_Qty")+"*"+obj.get("inMat_Unit_Price")+",\r\n"
						+ " '"+obj.get("inMat_Client_Code")+"',\r\n"
						+ " '"+obj.get("inMat_Date")+"',\r\n"
						+ "	'207',\r\n"
						+ " now(),\r\n"
						+ " '"+modifier+"')";
	
				//StockMatLX_tbl에 update
				StockMat_tbl_sql = " update StockMatLX_tbl set" 
						+ " SM_In_Qty = SM_In_Qty-"+obj.get("inReturn_Qty")+""
						+ " where SM_Code = '"+obj.get("inMat_Code")+"'";
				
				
				System.out.println("InMat_tbl_sql = " + InMat_tbl_sql);
				pstmt = conn.prepareStatement(InMat_tbl_sql);
				pstmt.executeUpdate();
				
				System.out.println("StockMat_tbl_sql = " + StockMat_tbl_sql);
				pstmt = conn.prepareStatement(StockMat_tbl_sql);		 
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
}
