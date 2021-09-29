<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String originData = request.getParameter("data");
JSONParser parset = new JSONParser();
JSONArray dataList = (JSONArray)parset.parse(originData);

Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://busience2.cafe24.com:3306/busience2","busience2","business12!!");
PreparedStatement pstmt = null;

if(dataList.size() > 0)
{
	for (int i = 0; i < dataList.size(); i++)
	{
		JSONObject datas = (JSONObject) dataList.get(i);
		out.println(datas.toJSONString());
		
		String sql = "";
		
		sql = "INSERT INTO orderitem\r\n"
				+ "(orderpk,orderno2, itemcode, costpy, costpy2, enterer, enterday)\r\n"
				+ "VALUES\r\n"
				+ "("
				+ "'"+ datas.get("orderpk") + "'\r\n"
				+ ",\r\n"
				+ "'"+ datas.get("orderno2") + "'\r\n"
				+ ",\r\n"
				+ "'"+ datas.get("itemcode") + "'\r\n"
				+ ",\r\n"
				+ "'"+ datas.get("costpy") + "'\r\n"
				+ ",\r\n"
				+ "'"+ datas.get("costpy2") + "'\r\n"
				+ ",\r\n"
				+ "'"+ datas.get("enterer") + "'\r\n"
				+ ",\r\n"
				+ "'"+ datas.get("enterday") + "'\r\n"
				+ ")";
		
		pstmt = con.prepareStatement(sql);
		pstmt.executeUpdate();
	}
}

if(pstmt != null )pstmt.close();
if(con != null ) con.close();
%>