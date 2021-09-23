package com.busience.system.controller;

import java.net.UnknownHostException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busience.standard.Dto.DTL_TBL;
import com.busience.system.Dto.User_Menu_Tbl;

@RestController("usermenuManageRestController")
@RequestMapping("usermenuManageRest")
public class usermenuManageRestController {

	@Autowired
	DataSource dataSource;
	
	@RequestMapping(value = "/usermenuManageView1", method = RequestMethod.GET)
	public List<DTL_TBL> usermenuManageView1(HttpServletRequest request, Principal principal) throws SQLException {

		String sql = "select \r\n"
				+ "		ifnull(t1.MENU_MGMT_USE_STATUS,'true') CHILD_TBL_USE_STATUS,\r\n"
				+ "        t2.CHILD_TBL_TYPE MENU_PROGRAM_NAME,\r\n"
				+ "		t2.CHILD_TBL_RMARK,\r\n"
				+ "		right(t2.CHILD_TBL_RMARK,1) order2,\r\n"	
				+ "     (select CHILD_TBL_TYPE from DTL_TBL where NEW_TBL_CODE = '16' and CHILD_TBL_NUM=right(t2.CHILD_TBL_RMARK,1)) CHILD_TBL_RMARK2,\r\n"
				+ "		t2.CHILD_TBL_TYPE,\r\n"	
				+ "		t2.CHILD_TBL_NO\r\n"	
				+ "from	\r\n"
				+ "		(\r\n"
				+ "			select\r\n"
				+ "					*\r\n"
				+ "			from\r\n"
				+ "					MENU_MGMT_TBL\r\n"
				+ "			where MENU_USER_CODE = '"+principal.getName()+"'\r\n"
				+ "        ) t1\r\n"
				+ "right outer join \r\n"
				+ "		(\r\n"
				+ "			select \r\n"
				+ "					* \r\n"
				+ "			from DTL_TBL where NEW_TBL_CODE='13'\r\n"
				+ "        ) t2\r\n"
				+ "on t1.MENU_PROGRAM_CODE = t2.CHILD_TBL_NO"
				+ " order by order2";
		
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<DTL_TBL> list = new ArrayList<DTL_TBL>();
		
		while (rs.next()) 
		{
			DTL_TBL inputdata = new DTL_TBL();
			
			if(rs.getString("CHILD_TBL_RMARK2") == null)
				continue;
			
			String sql2 = "select * from User_Menu_Tbl where User_Code = '"+principal.getName()+"' and Program_Code = '"+rs.getString("CHILD_TBL_NO")+"'";			
			System.out.println(sql2);
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			ResultSet rs2 = pstmt2.executeQuery();
			if(rs2.next())
			{
				rs2.close();
				pstmt2.close();
				continue;
			}
			
			inputdata.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_RMARK2"));
			//inputdata.setCHILD_TBL_TYPE(rs.getString("CHILD_TBL_TYPE"));
			//inputdata.setCHILD_TBL_NUM(rs.getString("CHILD_TBL_NUM"));
			inputdata.setCHILD_TBL_NO(rs.getString("CHILD_TBL_NO"));
			String[] array = rs.getString("CHILD_TBL_RMARK").split("/");
			inputdata.setURL(array[0]);
			inputdata.setPARENT("16"+array[1]);
			inputdata.setCHILD_TBL_USE_STATUS(rs.getString("CHILD_TBL_USE_STATUS"));
			inputdata.setNEW_TBL_CODE(rs.getString("CHILD_TBL_TYPE"));
			list.add(inputdata);
		}
		
		rs.close();
		pstmt.close();
		
		return list;
	}
	
	@RequestMapping(value = "/usermenuManageView2", method = RequestMethod.GET)
	public List<User_Menu_Tbl> usermenuManageView2(HttpServletRequest request, Principal principal) throws SQLException
	{
		List<User_Menu_Tbl> list = new ArrayList<User_Menu_Tbl>();
		
		String sql = "select * from User_Menu_Tbl "
				+ "'"+principal.getName()+"'";
		
		sql = "select t1.*,t2.CHILD_TBL_TYPE PROGRAM_NAME,t3.CHILD_TBL_TYPE GROUP_NAME from \r\n"
				+ "(\r\n"
				+ "	select * from User_Menu_Tbl where User_Code = '"+principal.getName()+"'\r\n"
				+ ")	t1\r\n"
				+ "inner join \r\n"
				+ "(\r\n"
				+ "	select * from DTL_TBL where NEW_TBL_CODE = '13'\r\n"
				+ ")	t2\r\n"
				+ "on	t1.Program_Code = t2.CHILD_TBL_NO\r\n"
				+ "inner join \r\n"
				+ "(\r\n"
				+ "	select * from DTL_TBL where NEW_TBL_CODE = '16'\r\n"
				+ ")	t3\r\n"
				+ "on	t1.Group_Code = t3.CHILD_TBL_NO";
		
		System.out.println(sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) 
		{
			User_Menu_Tbl data = new User_Menu_Tbl();
			
			data.setUser_Code(rs.getString("User_Code"));
			data.setGroup_Code(rs.getString("Group_Code"));
			data.setProgram_Code(rs.getString("Program_Code"));
			data.setURL(rs.getString("URL"));
			data.setGroup_Name(rs.getString("GROUP_NAME"));
			data.setProgram_Name(rs.getString("PROGRAM_NAME"));
			
			list.add(data);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	@RequestMapping(value = "/insert",method = {RequestMethod.GET,RequestMethod.POST})
	public String insert(HttpServletRequest request) throws SQLException, UnknownHostException, ClassNotFoundException, ParseException
	{
		HttpSession session = request.getSession();
		
		String data = request.getParameter("dataList");
		System.out.println(data);
		JSONParser parser = new JSONParser();
		JSONObject obj  = (JSONObject)parser.parse(data);
		System.out.println(obj);
		JSONArray dataList = (JSONArray) obj.get("data");
		
		if(dataList.size() > 0)
		{
			for (int i = 0; i < dataList.size(); i++)
			{
				JSONObject datas = (JSONObject) dataList.get(i);
				//System.out.println(datas.toJSONString());
				
				String sql = "insert into User_Menu_Tbl values(";
				sql += "'"+ session.getAttribute("id") + "',";
				sql += "'"+ datas.get("parent") + "',";
				sql += "'"+ datas.get("child_TBL_NO") + "',";
				sql += "'"+ datas.get("url") + "'";
				sql += ")";
				
				System.out.println(sql);
				
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}
		}
		
		return "redirect:/usermenuManage";
	}
	
	@RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
	public String delete(HttpServletRequest request) throws SQLException, UnknownHostException, ClassNotFoundException, ParseException
	{
		HttpSession session = request.getSession();
		
		String data = request.getParameter("dataList");
		
		JSONParser parser = new JSONParser();
		JSONObject obj  = (JSONObject)parser.parse(data);
		JSONArray dataList = (JSONArray) obj.get("data");
		
		
		if(dataList.size() > 0)
		{
			for (int i = 0; i < dataList.size(); i++)
			{
				JSONObject datas = (JSONObject) dataList.get(i);
				//System.out.println(datas.toJSONString());
				
				String sql = "delete from User_Menu_Tbl where User_Code = '" + session.getAttribute("id") + "' and ";
				sql += "Group_Code = '"+datas.get("group_Code")+"' and ";
				sql += "Program_Code = '"+datas.get("program_Code")+"'";
				
				System.out.println(sql);
				
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}
		}
		
		
		return "redirect:/usermenuManage";
	}
	
	
}
