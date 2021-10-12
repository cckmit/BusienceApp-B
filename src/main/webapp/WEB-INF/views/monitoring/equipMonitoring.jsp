<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="en">

<head>
	<title>Dashboard | Klorofil - Free Bootstrap Dashboard Template</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

	<style type="text/css">
	html, body {
	    margin: 0;
	    height: 100%;
	    overflow: hidden;
	}
	</style>

</head>

<body>
	
	<div class="row h-50" style="padding: 10px;">
	
		<%
			for(int i=0;i<2;i++)
			{
		%>
		<div class="col-lg-6">
		
			<div class="card border-dark h-100" id="<%=i+1%>_card">
			  <div class="card-header bg-transparent border-dark">
			  	<table style="width: 100%;">
			  		<tr>
			  			<td style="width: 20%;"><h1><%=i+1%>호기</h1></td>
			  			<td style="text-align: right; width: 20%;"><h1 id="<%=i+1%>h">ON</h1></td>
			  			<td class="time" style="text-align: right; font-size: 40px; width: 40%;"></td>
			  		</tr>
			  	</table>
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">
			    	<table style="width: 88%;">
			    		<tr>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_temp">0°</p>
			    			</td>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_humi">0°</p>
			    			</td>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_speed">0m/s</p>
			    			</td>
			    		</tr>
			    	</table>
			    </h5>
			    <p class="card-text">
			    	<div id="chart_div<%=i+1%>" style="width: 400px; height: 120px; text-align: center;"></div>
			    </p>
			    
			  </div>
			</div>
			
		</div>
		<%
			}
		%>
	
	</div>
	
	<div class="row h-50" style="padding: 10px;">
	
		<%
			for(int i=2;i<4;i++)
			{
		%>
		<div class="col-lg-6">
		
			<div class="card border-dark h-100" id="<%=i+1%>_card">
			  <div class="card-header bg-transparent border-dark">
			  	
			  	<table style="width: 100%;">
			  		<tr>
			  			<td style="width: 20%;"><h1><%=i+1%>호기</h1></td>
			  			<td style="text-align: right; width: 20%;"><h1 id="<%=i+1%>h">ON</h1></td>
			  			<td class="time" style="text-align: right; font-size: 40px; width: 40%;"></td>
			  		</tr>
			  	</table>
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">
			    	<table style="width: 88%;">
			    		<tr>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_temp">0°</p>
			    			</td>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_humi">0°</p>
			    			</td>
			    			<td style="width: 33%;">
			    				<p style="font-size: 40px; text-align: center;" id="<%=i+1%>_speed">0m/s</p>
			    			</td>
			    		</tr>
			    	</table>
			    </h5>
			    <p class="card-text">
			    	<div id="chart_div<%=i+1%>" style="width: 400px; height: 120px; text-align: center;"></div>
			    </p>
			  </div>
			</div>
			
		</div>
		<%
			}
		%>
	
	</div>
	
	<script src="js/monitoring/equipMonitoring.js"></script>
</body> 

</html>