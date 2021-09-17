package com.busience.standard.controller;

import java.net.UnknownHostException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.BOM_tbl;
import com.busience.standard.Dto.PRODUCT_INFO_TBL;

@RestController("BOMRestController")
@RequestMapping("BOMRest")
public class BOMRestController {
	@Autowired
	DataSource dataSource;
	
	//BOMitemList
	@RequestMapping(value = "/BOMitemList",method = RequestMethod.GET)
	public List<PRODUCT_INFO_TBL> BOMitemList(
			@RequestParam(value = "PRODUCT_ITEM_CODE", required = false) String PRODUCT_ITEM_CODE,
			@RequestParam(value = "Item_Type", required = false) String Item_Type) throws SQLException{
		List<PRODUCT_INFO_TBL> list = new ArrayList<PRODUCT_INFO_TBL>();
		
		System.out.println(PRODUCT_ITEM_CODE);
		
		String sql = " SELECT A.PRODUCT_ITEM_CODE,"
				+ " A.PRODUCT_ITEM_NAME,"
				+ " B.CHILD_TBL_TYPE PRODUCT_MTRL_CLSFC_NAME,\r\n"
				+ " A.PRODUCT_INFO_STND_1,\r\n"
				+ " C.CHILD_TBL_TYPE PRODUCT_UNIT_NAME\r\n"
				+ " FROM PRODUCT_INFO_TBL A\r\n"
				+ " inner join DTL_TBL B on A.PRODUCT_MTRL_CLSFC = B.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL C on A.PRODUCT_UNIT = C.CHILD_TBL_NO";
		
		String where = "";
		
		if (PRODUCT_ITEM_CODE != null && !PRODUCT_ITEM_CODE.equals("")) {
			where += " and A.PRODUCT_ITEM_CODE like '%" + PRODUCT_ITEM_CODE + "%'";
		}
		System.out.println("Item_Type = " + Item_Type);
		if(Item_Type == null || Item_Type.equals("all")) {
    	   //all 일경우 그냥 넘어감
		}else {
    	   //그외 일경우
    	   where += " and PRODUCT_MTRL_CLSFC in ("+Item_Type+")";
	   }
		
		sql += where;
		System.out.println("BOMitemList =" + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int i = 0;
		
		while (rs.next()) {
			PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();
			
			i++;
			data.setId(i);
			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_MTRL_CLSFC_NAME(rs.getString("PRODUCT_MTRL_CLSFC_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setPRODUCT_UNIT_NAME(rs.getString("PRODUCT_UNIT_NAME"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//BOMBOMList
	@RequestMapping(value = "/BOMBOMList",method = RequestMethod.GET)
	public List<BOM_tbl> BOMBOMList(
			@RequestParam(value = "BOM_ItemCode", required = false) String BOM_ItemCode) throws SQLException{
		
		String sql = " SELECT\r\n"
				+ " A.BOM_ID,\r\n"
				+ " A.BOM_Parent_ItemCode,\r\n"
				+ " A.BOM_ItemCode,\r\n"
				+ " B.PRODUCT_ITEM_NAME,\r\n"
				+ " B.PRODUCT_INFO_STND_1,\r\n"
				+ " C.CHILD_TBL_TYPE BOM_State,\r\n"
				+ " A.BOM_Qty,\r\n"
				+ " D.CHILD_TBL_TYPE BOM_Unit_Name,\r\n"
				+ " A.BOM_ChildExist,\r\n"
				+ " A.BOM_Modifier,\r\n"
				+ " A.BOM_Modify_Date\r\n"
				+ " FROM BOM_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.BOM_ItemCode = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join DTL_TBL C on B.PRODUCT_MTRL_CLSFC = C.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL D on B.PRODUCT_UNIT = D.CHILD_TBL_NO\r\n"
				+ " where BOM_Parent_ItemCode = '"+BOM_ItemCode+"'";
		
		System.out.println("BOMitemList =" + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<BOM_tbl> list = new ArrayList<BOM_tbl>();
		
		while (rs.next()) {
			BOM_tbl data = new BOM_tbl();

			data.setBOM_ID(rs.getInt("BOM_ID"));
			data.setBOM_Parent_ItemCode(rs.getString("BOM_Parent_ItemCode"));
			data.setBOM_ItemCode(rs.getString("BOM_ItemCode"));
			data.setBOM_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
			data.setBOM_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setBOM_Qty(rs.getFloat("BOM_Qty"));
			data.setBOM_Unit_Name(rs.getString("BOM_Unit_Name"));
			data.setBOM_State(rs.getString("BOM_State"));
			data.setBOM_ChildExist(rs.getString("BOM_ChildExist"));
			data.setBOM_Modifier(rs.getString("BOM_Modifier"));
			data.setBOM_Modify_Date(rs.getString("BOM_Modify_Date"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//BOMImpList
	@RequestMapping(value = "/BOMImpList",method = RequestMethod.GET)
	public List<BOM_tbl> BOMImpList(
			@RequestParam(value = "BOM_ItemCode", required = false) String BOM_ItemCode) throws SQLException{
		
		String sql = " SELECT\r\n"
				+ " A.BOM_ItemCode BOM_Parent_ItemCode,\r\n"
				+ " A.BOM_ID,\r\n"
				+ " B.PRODUCT_ITEM_CODE BOM_ItemCode,\r\n"
				+ " B.PRODUCT_ITEM_NAME,\r\n"
				+ " B.PRODUCT_INFO_STND_1,\r\n"
				+ " D.CHILD_TBL_TYPE,\r\n"
				+ " A.BOM_Modifier,\r\n"
				+ " A.BOM_Modify_Date\r\n"
				+ " FROM BOM_tbl A\r\n"
				+ " inner join PRODUCT_INFO_TBL B on A.BOM_Parent_ItemCode = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join DTL_TBL D on B.PRODUCT_MTRL_CLSFC = D.CHILD_TBL_NO\r\n"
				+ " where A.BOM_ItemCode = '"+BOM_ItemCode+"'";
		
		System.out.println("BOMImpList =" + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<BOM_tbl> list = new ArrayList<BOM_tbl>();
		
		while (rs.next()) {
			BOM_tbl data = new BOM_tbl();
			data.setBOM_Parent_ItemCode(rs.getString("BOM_Parent_ItemCode"));
			data.setBOM_ID(rs.getInt("BOM_ID"));
			data.setBOM_ItemCode(rs.getString("BOM_ItemCode"));
			data.setBOM_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
			data.setBOM_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setBOM_State(rs.getString("CHILD_TBL_TYPE"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//BOMItemInform
	@RequestMapping(value = "/BOMItemInform",method = RequestMethod.GET)
	public List<BOM_tbl> BOMItemInform(
			@RequestParam(value = "BOM_ItemCode", required = false) String BOM_ItemCode) throws SQLException{
		
		String sql = "SELECT A.PRODUCT_ITEM_CODE BOM_ItemCode, \r\n"
				+ "A.PRODUCT_ITEM_NAME, \r\n"
				+ "A.PRODUCT_INFO_STND_1, \r\n"
				+ "B.CHILD_TBL_TYPE BOM_Unit_Name,\r\n"
				+ "C.CHILD_TBL_TYPE BOM_State\r\n"
				+ "FROM PRODUCT_INFO_TBL A\r\n"
				+ "inner join DTL_TBL B on A.PRODUCT_UNIT = B.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL C on A.PRODUCT_MTRL_CLSFC = C.CHILD_TBL_NO\r\n"
				+ "where A.PRODUCT_ITEM_CODE = '"+BOM_ItemCode+"'";
		
		System.out.println("BOMItemInform =" + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<BOM_tbl> list = new ArrayList<BOM_tbl>();
		
		while (rs.next()) {
			BOM_tbl data = new BOM_tbl();
			
			data.setBOM_ItemCode(rs.getString("BOM_ItemCode"));
			data.setBOM_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
			data.setBOM_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setBOM_Unit_Name(rs.getString("BOM_Unit_Name"));
			data.setBOM_State(rs.getString("BOM_State"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//BBL_Save
	@RequestMapping(value = "/BBL_Save", method = RequestMethod.POST)
	public String BBL_Save(HttpServletRequest request, Model model)
			throws ParseException, SQLException, UnknownHostException, ClassNotFoundException, org.json.simple.parser.ParseException {
		String originData = request.getParameter("data");
		
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		
		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");
		
		String BBL_Save_sql = null;
		String BBL_update_sql1 = null;
		String BBL_update_sql2 = null;
		
		Connection conn = null;                                    

		PreparedStatement pstmt = null;

		String sql_result = null;
		
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				//BOM_tbl에 insert or update
				BBL_Save_sql = " insert into BOM_tbl(\r\n"
				+ "BOM_ID,\r\n"
				+ "BOM_Parent_ItemCode,\r\n"
				+ "BOM_ItemCode,\r\n"
				+ "BOM_Qty,\r\n"
				+ "BOM_Modifier,\r\n"
				+ "BOM_Modify_Date\r\n"
				+ ") values(\r\n"
				+ ""+obj.get("bom_ID")+",\r\n"
				+ "'"+obj.get("bom_Parent_ItemCode")+"',\r\n"
				+ "'"+obj.get("bom_ItemCode")+"',\r\n"
				+ ""+obj.get("bom_Qty")+",\r\n"
				+ "'"+modifier+"',\r\n"
				+ "now()\r\n"
				+ ") on duplicate key update\r\n"
				+ "BOM_Qty ="+obj.get("bom_Qty");
				
				System.out.println("BBL_Save_sql = " + BBL_Save_sql);
				pstmt = conn.prepareStatement(BBL_Save_sql);
				pstmt.executeUpdate();
				
				//BOM_tbl에 BOM_ChildExist를 업데이트
				BBL_update_sql1 = " update BOM_tbl set BOM_ChildExist = 'Y'"
							+ " where BOM_ItemCode = '"+obj.get("bom_Parent_ItemCode")+"'";
				
				System.out.println("BBL_update_sql1 =" + BBL_update_sql1);
				pstmt = conn.prepareStatement(BBL_update_sql1);
				pstmt.executeUpdate();
				
				//BOM_tbl에 BOM_ChildExist를 업데이트
				BBL_update_sql2 = "update BOM_tbl set BOM_ChildExist = 'Y'\r\n"
							+ " where BOM_ItemCode = '"+obj.get("bom_ItemCode")+"'\r\n"
							+ "and exists (select * from (select * from BOM_tbl where BOM_Parent_ItemCode = '"+obj.get("bom_ItemCode")+"') A)";
								
				System.out.println("BBL_update_sql2 =" + BBL_update_sql2);
				pstmt = conn.prepareStatement(BBL_update_sql2);
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
	
	//BBL_Delete
	@RequestMapping(value = "/BBL_Delete", method = RequestMethod.POST)
	public String BBL_Delete(HttpServletRequest request, Model model)
			throws ParseException, SQLException, UnknownHostException, ClassNotFoundException, org.json.simple.parser.ParseException {
		String originData = request.getParameter("data");
		
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		System.out.println(arr);

		String BBL_Delete_sql = null;
		String BBL_Update_sql = null;
		
		Connection conn = null;                                    

		PreparedStatement pstmt = null;

		String sql_result = null;
		
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				//BOM_tbl에 delete
				BBL_Delete_sql = " delete from BOM_tbl"
						+ " where bom_Parent_ItemCode = '" + obj.get("bom_Parent_ItemCode")+"'"
						+ " and bom_ItemCode = '" + obj.get("bom_ItemCode")+"'";
				
				System.out.println("BBL_Delete_sql =" + BBL_Delete_sql);
				
				pstmt = conn.prepareStatement(BBL_Delete_sql);
				pstmt.executeUpdate();
				
				//BOM_tbl에 BOM_ChildExist를 업데이트
				BBL_Update_sql = " update BOM_tbl set BOM_ChildExist = 'N'\r\n"
						+ " where BOM_ItemCode = '"+obj.get("bom_Parent_ItemCode")+"'\r\n"
						+ " and NOT EXISTS (select * from (select * from BOM_tbl where BOM_Parent_ItemCode = '"+obj.get("bom_Parent_ItemCode")+"') A)";
				
				System.out.println("BBL_Update_sql =" + BBL_Update_sql);
				
				pstmt = conn.prepareStatement(BBL_Update_sql);
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