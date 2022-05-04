package com.busience.standard.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.Packaging_Standard_tbl;

@RestController("packagingManageRestController")
@RequestMapping("packagingManageRest")
public class packagingManageRestController {

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/PM_Search", method = RequestMethod.GET)
	public List<Packaging_Standard_tbl> PM_Search() throws SQLException, ParseException {

		String PM_Search_sql = null;
		
		PM_Search_sql = "SELECT\r\n"
				+ "A.Packaging_Cus_Status,\r\n"
				+ "B.CHILD_TBL_TYPE Packaging_Cus_Status_Name,\r\n"
				+ "A.Packaging_Cus_Code,\r\n"
				+ "D.Cus_Name Packaging_Cus_Name,\r\n"
				+ "A.Packaging_ItemCode,\r\n"
				+ "C.PRODUCT_ITEM_NAME Packaging_ItemName,\r\n"
				+ "A.Packaging_Label_Type,\r\n"
				+ "A.Packaging_Min_Order_Qty,\r\n"
				+ "A.Packaging_Small_Unit,\r\n"
				+ "A.Packaging_Big_Box,\r\n"
				+ "A.Packaging_Rate,\r\n"
				+ "A.Packaging_Use_Status,\r\n"
				+ "A.Packaging_Modify_Date,\r\n"
				+ "A.Packaging_Modifier\r\n"
				+ "FROM Packaging_Standard_tbl A\r\n"
				+ "inner join DTL_TBL B on A.Packaging_Cus_Status = B.CHILD_TBL_NO\r\n"
				+ "inner join Product_Info_tbl C on A.Packaging_ItemCode = C.PRODUCT_ITEM_CODE\r\n"
				+ "inner join Customer_tbl D on A.Packaging_Cus_Code = D.Cus_Code\r\n";
		
		System.out.println("PM_Search_sql =" + PM_Search_sql);
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(PM_Search_sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<Packaging_Standard_tbl> list = new ArrayList<Packaging_Standard_tbl>();
		
		int i = 0;
		
		while (rs.next()) {
			Packaging_Standard_tbl data = new Packaging_Standard_tbl();
			
			data.setID(++i);
			data.setPackaging_Cus_Status(rs.getString("packaging_Cus_Status"));
			data.setPackaging_Cus_Status_Name(rs.getString("packaging_Cus_Status_Name"));
			data.setPackaging_ItemCode(rs.getString("packaging_ItemCode"));
			data.setPackaging_ItemName(rs.getString("packaging_ItemName"));
			data.setPackaging_Cus_Code(rs.getString("packaging_Cus_Code"));
			data.setPackaging_Cus_Name(rs.getString("packaging_Cus_Name"));
			data.setPackaging_Label_Type(rs.getString("packaging_Label_Type"));
			data.setPackaging_Min_Order_Qty(rs.getInt("packaging_Min_Order_Qty"));
			data.setPackaging_Small_Unit(rs.getInt("packaging_Small_Unit"));
			data.setPackaging_Big_Box(rs.getInt("packaging_Big_Box"));
			data.setPackaging_Rate(rs.getInt("packaging_Rate"));
			data.setPackaging_Use_Status(rs.getBoolean("packaging_Use_Status"));
			data.setPackaging_Modify_Date(rs.getString("packaging_Modify_Date"));
			data.setPackaging_Modifier(rs.getString("packaging_Modifier"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return list;
	}
	// PM_Save
	@RequestMapping(value="/PM_Save", method=RequestMethod.POST)
	public String PM_Save(HttpServletRequest request, Model model) throws ParseException, SQLException {
		JSONParser parser = new JSONParser();

		//data
		String data = request.getParameter("data");
		System.out.println(data);
		JSONArray arr = (JSONArray) parser.parse(data);
		
		HttpSession session = request.getSession();
		String modifier = (String) session.getAttribute("id");
		
		
		String PM_Save_sql = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql_result = null;
		
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
						
			//Sales_OrderList_tbl
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				PM_Save_sql = " insert into Packaging_Standard_tbl(\r\n"
						+ " Packaging_Cus_Status,\r\n"
						+ " Packaging_ItemCode,\r\n"
						+ " Packaging_Cus_Code,\r\n"
						+ " Packaging_Label_Type,\r\n"
						+ " Packaging_Min_Order_Qty,\r\n"
						+ " Packaging_Small_Unit,\r\n"
						+ " Packaging_Big_Box,\r\n"
						+ " Packaging_Use_Status,\r\n"
						+ " Packaging_Modify_Date,\r\n"
						+ " Packaging_Modifier\r\n	"
						+ " ) values(\r\n"
						+ " '"+obj.get("packaging_Cus_Status")+"',\r\n"
						+ " '"+obj.get("packaging_ItemCode")+"',\r\n"
						+ " '"+obj.get("packaging_Cus_Code")+"',\r\n"
						+ " '"+obj.get("packaging_Label_Type")+"',\r\n"
						+ " "+obj.get("packaging_Min_Order_Qty")+",\r\n"
						+ " "+obj.get("packaging_Small_Unit")+",\r\n"
						+ " "+obj.get("packaging_Big_Box")+",\r\n"
						+ " "+obj.get("packaging_Use_Status")+",\r\n"
						+ " now(),\r\n"
						+ " '"+modifier+"'\r\n"
						+ " )on duplicate key update\r\n"
						+ " Packaging_Label_Type = '"+obj.get("packaging_Label_Type")+"',\r\n"
						+ " Packaging_Min_Order_Qty = '"+obj.get("packaging_Min_Order_Qty")+"',\r\n"
						+ " Packaging_Small_Unit = "+obj.get("packaging_Small_Unit")+",\r\n"
						+ " Packaging_Big_Box = "+obj.get("packaging_Big_Box")+",\r\n"
						+ " Packaging_Use_Status = "+obj.get("packaging_Use_Status")+",\r\n"
						+ " Packaging_Modify_Date = now(),\r\n"
						+ " Packaging_Modifier = '"+modifier+"'";
				
				System.out.println("PM_Save_sql = " + PM_Save_sql);
				pstmt = conn.prepareStatement(PM_Save_sql);
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
	
	// PM_Delete
	@RequestMapping(value="/PM_Delete", method=RequestMethod.POST)
	public String PM_Delete(HttpServletRequest request, Model model) throws ParseException, SQLException {
		JSONParser parser = new JSONParser();
		String originData = request.getParameter("data");
		JSONArray arr = (JSONArray) parser.parse(originData);
		
		String PM_Delete_sql = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql_result = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);

				//새로추가
				PM_Delete_sql = " delete from Packaging_Standard_tbl\r\n"
						+ " where Packaging_Cus_Status = '"+obj.get("packaging_Cus_Status")+"'\r\n"
						+ " and Packaging_Cus_Code = '"+obj.get("packaging_Cus_Code")+"'\r\n"
						+ " and Packaging_ItemCode = '"+obj.get("packaging_ItemCode")+"'";
				
				System.out.println("OS_delete_sql = " + PM_Delete_sql);
				pstmt = conn.prepareStatement(PM_Delete_sql);
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
