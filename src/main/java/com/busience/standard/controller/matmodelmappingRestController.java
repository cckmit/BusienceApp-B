package com.busience.standard.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.Mat_Model_Mapping_tbl;
import com.busience.standard.dto.PRODUCT_INFO_TBL;
import com.busience.standard.dto.Routing_tbl;

@RestController("matmodelmappingRestController")
@RequestMapping("matmodelmappingRest")
public class matmodelmappingRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/Product_Search", method = RequestMethod.GET)
	public List<PRODUCT_INFO_TBL> Product_Search(HttpServletRequest request) throws SQLException
	{
		List<PRODUCT_INFO_TBL> list = new ArrayList<PRODUCT_INFO_TBL>();
		
		String sql = "SELECT \r\n"
				+ "			Distinct A.PRODUCT_ITEM_CODE, \r\n"
				+ "			A.PRODUCT_ITEM_NAME, \r\n"
				+ "			A.PRODUCT_MTRL_CLSFC,\r\n"
				+ "			B.CHILD_TBL_TYPE PRODUCT_MTRL_CLSFC_NAME,\r\n"
				+ " 			A.PRODUCT_INFO_STND_1,\r\n"
				+ " 			IFNULL((SELECT BOM_Parent_ItemCode FROM BOM_tbl WHERE BOM_Parent_ItemCode=A.PRODUCT_ITEM_CODE LIMIT 1),'FALSE') BOM_Registered\r\n"
				+ "from Product_Info_tbl A\r\n"
				+ "inner join DTL_TBL B on A.PRODUCT_MTRL_CLSFC = B.CHILD_TBL_NO\r\n"
				+ "INNER JOIN Routing_tbl C ON A.PRODUCT_ITEM_CODE = C.Routing_ItemCode";
		
		if(!request.getParameter("PRODUCT_ITEM_CODE").equals(""))
			sql += " WHERE A.PRODUCT_ITEM_CODE='"+request.getParameter("PRODUCT_ITEM_CODE")+"'";
		else
			sql += " WHERE A.PRODUCT_MTRL_CLSFC=55";
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		PRODUCT_INFO_TBL data = null;
		while (rs.next()) {
			data = new PRODUCT_INFO_TBL();
			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_MTRL_CLSFC_NAME(rs.getString("PRODUCT_MTRL_CLSFC_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			
			if(!rs.getString("BOM_Registered").equals("FALSE"))
				data.setBOM_Registered(true);
			else
				data.setBOM_Registered(false);
			list.add(data);
		}
		
		return list;
	}
	
	@RequestMapping(value = "/Routing_tbl_Search", method = RequestMethod.GET)
	public List<Routing_tbl> Routing_tbl_Search(HttpServletRequest request) throws SQLException{
		List<Routing_tbl> list = new ArrayList<Routing_tbl>();
		String Routing_ItemCode = request.getParameter("Routing_ItemCode");
		String sql = "SELECT * FROM Routing_tbl WHERE Routing_ItemCode='"+Routing_ItemCode+"'\r\n"
				+ "order by Routing_Order asc";
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		Routing_tbl data = null;
		while (rs.next()) {
			data = new Routing_tbl();
			
			data.setRouting_ItemCode(rs.getString("Routing_ItemCode"));
			data.setRouting_Order(rs.getString("Routing_Order"));
			data.setRouting_FairCode(rs.getString("Routing_FairCode"));
			data.setRouting_HowTest(rs.getString("Routing_HowTest"));
			data.setRouting_BsnsRcvng(rs.getString("Routing_BsnsRcvng"));
			data.setRouting_Remarks(rs.getString("Routing_Remarks"));
			
			list.add(data);
		}
		
		return list;
	}
	
	@RequestMapping(value = "/matmodelmapping_LevelCheck", method = RequestMethod.GET)
	public int matmodelmapping_LevelCheck(HttpServletRequest request){
		String Mat_Model_ItemCode = request.getParameter("Mat_Model_ItemCode");
		String Mat_Model_FairCode = request.getParameter("Mat_Model_FairCode");
		
		int result = jdbctemplate.queryForObject(
				"SELECT IFNULL(MAX(t2.BOM_Qty),0) BOM_Qty FROM Mat_Model_Mapping_tbl t1 \r\n"
				+ "INNER JOIN BOM_tbl t2 ON t1.Mat_Model_ItemBCode = t2.BOM_ItemCode\r\n"
				+ "where Mat_Model_ItemCode='"+Mat_Model_ItemCode+"' and Mat_Model_FairCode='"+Mat_Model_FairCode+"'",
				Integer.class);
		
		return result;
	}
	
	@RequestMapping(value = "/matmodelmapping_AddRow", method = RequestMethod.GET)
	public List<Mat_Model_Mapping_tbl> matmodelmapping_AddRow(HttpServletRequest request) throws ParseException, SQLException{
		String Mat_Model_ItemCode = request.getParameter("Mat_Model_ItemCode");
		String bom_ItemCode = request.getParameter("bom_ItemCode");
		String bom_Parent_ItemCode = request.getParameter("bom_Parent_ItemCode");
		
		List<Mat_Model_Mapping_tbl> list = jdbctemplate.query("SELECT * FROM Mat_Model_Mapping_tbl t1 "
				+ "inner join PRODUCT_INFO_TBL t2 on t1.Mat_Model_ItemBCode=t2.PRODUCT_ITEM_CODE "
				+ "where Mat_Model_ItemCode=? and Mat_Model_ItemBCode=? and Mat_Model_ItemFCode=?", new RowMapper<Mat_Model_Mapping_tbl>() {
			@Override
			public Mat_Model_Mapping_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				Mat_Model_Mapping_tbl data = new Mat_Model_Mapping_tbl();
				data.setMat_Model_FairCode(rs.getString("Mat_Model_FairCode"));
				data.setMat_Model_ItemCode(rs.getString("Mat_Model_ItemCode"));
				data.setMat_Model_Num(rs.getString("Mat_Model_Num"));
				data.setMat_Model_ItemBCode(rs.getString("Mat_Model_ItemBCode"));
				data.setMat_Model_ItemBName(rs.getString("PRODUCT_ITEM_NAME"));
				return data;
			}
		},Mat_Model_ItemCode,bom_ItemCode,bom_Parent_ItemCode);
		
		return list;
	}
	
	@RequestMapping(value = "/matmodelmapping_Insert", method = RequestMethod.GET)
	public String Routing_tbl_Insert(HttpServletRequest request) throws ParseException, SQLException{
		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj  = (JSONObject)parser.parse(data);
		JSONArray dataList = (JSONArray) obj.get("data");
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		
		try
		{
			if(dataList.size() > 0)
			{
				conn.setAutoCommit(false); //트랜잭션 처리를 위해서 AutoCommit을 중지한다.
				
				for (int i = 0; i < dataList.size(); i++)
				{
					JSONObject datas = (JSONObject) dataList.get(i);
					
					String product_ITEM_CODE = (String)datas.get("product_ITEM_CODE");
					String routing_FairCode = (String)datas.get("routing_FairCode");
					String workOrder_ItemCode = (String)datas.get("workOrder_ItemCode");
					String mat_Model_ItemCode = (String)datas.get("mat_Model_ItemCode");
					
					if((String)datas.get("mat_Model_ItemBCode") == null)
					{
						conn.rollback();
						conn.setAutoCommit(true); //트랜잭션 처리를 기본상태로 되돌린다.
						if( pstmt != null ) pstmt.close();
				        if( conn != null ) conn.close();
						return "workOrder_ItemCode_empty";
					}
					
					if(mat_Model_ItemCode == null)
						jdbctemplate.update("insert into Mat_Model_Mapping_tbl values(?,?,?,?,?,?)", 
								product_ITEM_CODE,
								jdbctemplate.queryForObject(
								"SELECT IFNULL(MAX(Mat_Model_Num)+1,1) "
								+ "Mat_Model_Num FROM Mat_Model_Mapping_tbl "
								+ "WHERE Mat_Model_ItemCode='"+product_ITEM_CODE+"' "
								+ "AND Mat_Model_FairCode='"+routing_FairCode+"'",
								Integer.class),
								routing_FairCode
								,(String)datas.get("mat_Model_ItemFCode")
								,(String)datas.get("mat_Model_ItemBCode")
								,datas.get("bom_level"));
					else
						jdbctemplate.update("update Mat_Model_Mapping_tbl set Mat_Model_ItemBCode=?"
								+ " where Mat_Model_ItemCode=? and Mat_Model_Num=? and Mat_Model_FairCode=?",
								(String)datas.get("mat_Model_ItemBCode"),
								mat_Model_ItemCode,
								(String)datas.get("mat_Model_Num"),
								(String)datas.get("mat_Model_FairCode"));
				}
			}
			else
				return "empty";
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			
			conn.rollback();
			conn.setAutoCommit(true); //트랜잭션 처리를 기본상태로 되돌린다.
			if( pstmt != null ) pstmt.close();
	        if( conn != null ) conn.close();
			return "workOrder_ItemCode_fail";
		}
		
		conn.setAutoCommit(true); //트랜잭션 처리를 기본상태로 되돌린다.
		if( pstmt != null ) pstmt.close();
        if( conn != null ) conn.close();
		
		return "ok";
	}
	
	@RequestMapping(value = "/matmodelmapping_delete", method = RequestMethod.GET)
	public String Routing_tbl_delete(HttpServletRequest request) throws ParseException
	{
		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj  = (JSONObject)parser.parse(data);
		JSONArray dataList = (JSONArray) obj.get("data");
		
		if(dataList.size() > 0)
		{
			for (int i = 0; i < dataList.size(); i++)
			{
				JSONObject datas = (JSONObject) dataList.get(i);
				
				String product_ITEM_CODE = (String)datas.get("product_ITEM_CODE");
				String routing_FairCode = (String)datas.get("routing_FairCode");
				String workOrder_ItemCode = (String)datas.get("workOrder_ItemCode");
				String mat_Model_ItemCode = (String)datas.get("mat_Model_ItemCode");
				
				System.out.println(datas);
				
				jdbctemplate.update("delete from Mat_Model_Mapping_tbl where Mat_Model_ItemCode=? and mat_Model_Num=?"
						,(String)datas.get("mat_Model_ItemCode"),(String)datas.get("mat_Model_Num"));
			}
		}
		
		return "ok";
	}
	
	@RequestMapping(value = "/Mat_Model_Mapping_Select", method = RequestMethod.GET)
	public List<Mat_Model_Mapping_tbl> Mat_Model_Mapping_Select(HttpServletRequest request)
	{
		return jdbctemplate.query("SELECT * FROM Mat_Model_Mapping_tbl t1 "
				+ "inner join PRODUCT_INFO_TBL t2 on t1.Mat_Model_ItemBCode=t2.PRODUCT_ITEM_CODE "
				+ "where Mat_Model_ItemCode=? and Mat_Model_FairCode=?", new RowMapper<Mat_Model_Mapping_tbl>() {
			@Override
			public Mat_Model_Mapping_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				Mat_Model_Mapping_tbl data = new Mat_Model_Mapping_tbl();
				data.setMat_Model_FairCode(rs.getString("Mat_Model_FairCode"));
				data.setMat_Model_ItemCode(rs.getString("Mat_Model_ItemCode"));
				data.setMat_Model_Num(rs.getString("Mat_Model_Num"));
				data.setMat_Model_ItemFCode(rs.getString("Mat_Model_ItemFCode"));
				data.setMat_Model_ItemBCode(rs.getString("Mat_Model_ItemBCode"));
				data.setMat_Model_ItemBName(rs.getString("PRODUCT_ITEM_NAME"));
				data.setBom_level(rs.getString("Mat_Model_BOMLevel"));
				return data;
			}
		},request.getParameter("Mat_Model_ItemCode"),request.getParameter("Mat_Model_FairCode"));
	}
	
}
