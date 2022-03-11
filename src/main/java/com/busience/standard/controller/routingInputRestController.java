package com.busience.standard.controller;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.dto.PRODUCT_INFO_TBL;
import com.busience.standard.dto.Routing_tbl;

@RestController("routingInputRestController")
@RequestMapping("routingInputRest")
public class routingInputRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@RequestMapping(value = "/Product_Search", method = RequestMethod.GET)
	public List<PRODUCT_INFO_TBL> Product_Search(HttpServletRequest request) throws SQLException
	{
		String sql = "SELECT \r\n"
				+ "			A.PRODUCT_ITEM_CODE, \r\n"
				+ "			A.PRODUCT_ITEM_NAME, \r\n"
				+ "			A.PRODUCT_MTRL_CLSFC,\r\n"
				+ "			B.CHILD_TBL_TYPE PRODUCT_MTRL_CLSFC_NAME,\r\n"
				+ " 			A.PRODUCT_INFO_STND_1,\r\n"
				+ " 			IFNULL((SELECT BOM_Parent_ItemCode FROM BOM_tbl WHERE BOM_Parent_ItemCode=A.PRODUCT_ITEM_CODE LIMIT 1),'FALSE') BOM_Registered\r\n"
				+ "from Product_Info_tbl A\r\n"
				+ "inner join DTL_TBL B on A.PRODUCT_MTRL_CLSFC = B.CHILD_TBL_NO";
		
		if(!request.getParameter("PRODUCT_ITEM_CODE").equals(""))
			sql += " WHERE A.PRODUCT_ITEM_CODE='"+request.getParameter("PRODUCT_ITEM_CODE")+"'";
		else
			sql += " WHERE A.PRODUCT_MTRL_CLSFC=55";
		
		return jdbctemplate.query(sql, new RowMapper<PRODUCT_INFO_TBL>() {
			@Override
			public PRODUCT_INFO_TBL mapRow(ResultSet rs, int rowNum) throws SQLException {
				PRODUCT_INFO_TBL data = new PRODUCT_INFO_TBL();
				data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
				data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_MTRL_CLSFC_NAME(rs.getString("PRODUCT_MTRL_CLSFC_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				
				if(!rs.getString("BOM_Registered").equals("FALSE"))
					data.setBOM_Registered(true);
				else
					data.setBOM_Registered(false);
				return data;
			}
		});
	}
	
	@RequestMapping(value = "/Routing_tbl_Search", method = RequestMethod.GET)
	public List<Routing_tbl> Routing_tbl_Search(HttpServletRequest request) throws SQLException{
		String Routing_ItemCode = request.getParameter("Routing_ItemCode");
		String sql = "SELECT * FROM Routing_tbl WHERE Routing_ItemCode=?\r\n"
				+ "order by Routing_Order asc";
		
		return jdbctemplate.query(sql, new RowMapper<Routing_tbl>() {
			@Override
			public Routing_tbl mapRow(ResultSet rs, int rowNum) throws SQLException {
				Routing_tbl data = new Routing_tbl();
				
				data.setRouting_ItemCode(rs.getString("Routing_ItemCode"));
				data.setRouting_Order(rs.getString("Routing_Order"));
				data.setRouting_FairCode(rs.getString("Routing_FairCode"));
				data.setRouting_HowTest(rs.getString("Routing_HowTest"));
				data.setRouting_BsnsRcvng(rs.getString("Routing_BsnsRcvng"));
				data.setRouting_Remarks(rs.getString("Routing_Remarks"));
				return data;
			}
		},Routing_ItemCode);
	}
	
	@RequestMapping(value = "/Routing_tbl_delete", method = RequestMethod.GET)
	public String Routing_tbl_delete(HttpServletRequest request) throws ParseException, SQLException
	{
		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj  = (JSONObject)parser.parse(data);
		JSONArray dataList = (JSONArray) obj.get("data");
		
		Connection conn = dataSource.getConnection();
		
		try
		{
			if(dataList.size() > 0)
			{
				conn.setAutoCommit(false); //트랜잭션 처리를 위해서 AutoCommit을 중지한다.
				
				for (int i = 0; i < dataList.size(); i++)
				{
					JSONObject datas = (JSONObject) dataList.get(i);
					jdbctemplate.update("delete from Routing_tbl where Routing_ItemCode=? and Routing_FairCode=?",
							datas.get("routing_ItemCode"),datas.get("routing_FairCodev"));
				}
			}
		}
		catch(Exception ex)
		{
			conn.rollback();
			conn.setAutoCommit(true); //트랜잭션 처리를 기본상태로 되돌린다.
			System.out.println(ex.getMessage());
			
			return "foreign";
		}
		
		conn.setAutoCommit(true); //트랜잭션 처리를 기본상태로 되돌린다.
		
		return "ok";
	}
	
	@RequestMapping(value = "/Routing_tbl_Insert", method = RequestMethod.GET)
	public String Routing_tbl_Insert(HttpServletRequest request) throws ParseException, SQLException{
		String data = request.getParameter("data");
		JSONParser parser = new JSONParser();
		JSONObject obj  = (JSONObject)parser.parse(data);
		JSONArray dataList = (JSONArray) obj.get("data");
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = null;
		
		String sql = "";
		
		String routing_ItemCode = "";
		
		try
		{
			if(dataList.size() > 0)
			{
				conn.setAutoCommit(false); //트랜잭션 처리를 위해서 AutoCommit을 중지한다.
				
				for (int i = 0; i < dataList.size(); i++)
				{
					JSONObject datas = (JSONObject) dataList.get(i);
					System.out.println(datas.toJSONString());
					
					/*
					sql = "SELECT * FROM Routing_tbl WHERE Routing_ItemCode=? AND Routing_Order=?";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, (String)datas.get("routing_ItemCode"));
					
					if(datas.get("routing_Order").getClass().getName().equals("java.lang.String"))
						pstmt.setString(2, (String)datas.get("routing_Order"));
					else
						pstmt.setLong(2, (Long)datas.get("routing_Order"));
					
					ResultSet rs = pstmt.executeQuery();
					if(rs.next())
					{
						rs.close();
						pstmt.close();
						conn.close();
						
						return "no";
					}
					*/
					
					routing_ItemCode = "'"+datas.get("routing_ItemCode")+"'";
					String routing_Remarks = (String)datas.get("routing_Remarks");
					if(routing_Remarks == null)
						routing_Remarks = "";
					
					if(datas.get("routing_FairCodev") == null)
					{
						sql = "INSERT INTO Routing_tbl\r\n"
								+ "(Routing_ItemCode, Routing_Order, Routing_FairCode, Routing_HowTest, Routing_BsnsRcvng, Routing_Remarks)\r\n"
								+ "VALUES("
								+ routing_ItemCode+","
								+ "'"+datas.get("routing_Order")+"',"
								+ "'"+datas.get("routing_FairCode")+"',"
								+ "'"+datas.get("routing_HowTest")+"',"
								+ "'"+datas.get("routing_BsnsRcvng")+"',"
								+ "'"+routing_Remarks+"'"
								+ ")";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();
					}
					else
					{
						sql = "UPDATE Routing_tbl\r\n"
								+ "SET "
								+ "Routing_ItemCode="+ routing_ItemCode+","
								+ "Routing_Order="+"'"+datas.get("routing_Order")+"',"
								+ "Routing_FairCode="+"'"+datas.get("routing_FairCode")+"',"
								+ "Routing_HowTest="+"'"+datas.get("routing_HowTest")+"',"
								+ "Routing_BsnsRcvng="+"'"+datas.get("routing_BsnsRcvng")+"',"
								+ "Routing_Remarks="+"'"+routing_Remarks+"'"
								+ "\r\n"
								+ "WHERE "
								+ "Routing_ItemCode="+ routing_ItemCode+"\r\n"
								+ "AND "
								+ "Routing_Order="+"'"+datas.get("routing_Orderv")+"'\r\n"
								+ "AND "
								+ "Routing_FairCode="+"'"+datas.get("routing_FairCodev")+"'";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();
					}
				}
			}
			
			sql = "UPDATE Routing_tbl\r\n"
					+ "SET Routing_BsnsRcvng='N'\r\n"
					+ "WHERE  	Routing_ItemCode=\r\n"
					+ routing_ItemCode;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "UPDATE Routing_tbl\r\n"
					+ "SET Routing_BsnsRcvng='Y'\r\n"
					+ "WHERE  	Routing_ItemCode=\r\n"
					+ routing_ItemCode+" "
					+ "AND		Routing_Order=(SELECT * FROM (SELECT MAX(Routing_Order) FROM Routing_tbl WHERE Routing_ItemCode=\r\n"
					+ routing_ItemCode+" "
					+ ") A)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			conn.rollback();
			
			conn.setAutoCommit(true); //트랜잭션 처리를 기본상태로 되돌린다.

	        if( pstmt != null ) pstmt.close();
	        if( conn != null ) conn.close();
			
			return "no";
		}
		
		conn.setAutoCommit(true); //트랜잭션 처리를 기본상태로 되돌린다.

        if( pstmt != null ) pstmt.close();
        if( conn != null ) conn.close();
		
		return "ok";
	}
	
}
