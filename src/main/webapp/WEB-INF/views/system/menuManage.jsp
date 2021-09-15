<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	String Name = "'"+(String)session.getAttribute("name")+"'";

	String sql = "select * from MENU_MGMT_TBL where MENU_USER_CODE = 'admin' and MENU_PROGRAM_CODE = '13106'";

	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://busience2.cafe24.com:3306/busience2","busience2","business12!!");
	PreparedStatement pstmt = con.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery(sql);
	
	boolean MENU_WRITE_USE_STATUS = false;
	boolean MENU_DEL_USE_STATUS = false;
	
	while(rs.next())
	{
		if(rs.getString("MENU_WRITE_USE_STATUS").equals("true"))
			MENU_WRITE_USE_STATUS = true;
		
		if(rs.getString("MENU_DEL_USE_STATUS").equals("true"))
			MENU_DEL_USE_STATUS = true;
	}
%>

<!-- Modify Message Modal -->
<jsp:include page="../modal/message/modifyYesNo.jsp"></jsp:include>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<%
					if(MENU_WRITE_USE_STATUS)
					{
				%>
					<img id="modifyinitbtn"
						src="/images/button/Update.png"
						style="opacity: 0.5;"/>
				<%	
					}
					else
					{
				%>
					<img
						src="/images/button/Update.png"
						style="opacity: 0.5;"/>
				<%		
					}
				%>
			</div>
		</div>
		
		<div id="example-table1" style="width: calc(50% - 5px); float:left; margin-right: 10px;"></div>
		<div id="example-table2" style="width: calc(50% - 5px); float:left;"></div>
	</div>
	<!-- END MAIN -->
	
</div>
<script src="/js/system/menuManage.js"></script>