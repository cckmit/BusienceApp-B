<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<head>
	<title>생산 모니터링</title>
	
	<style type="text/css">
	html, body {
	    margin: 0;
	    height: 100%;
	    overflow: hidden;
	}
	</style>
</head>

<body>
	<%
		String CHILD_TBL_TYPE = (String)request.getAttribute("CHILD_TBL_TYPE");
	
		if(CHILD_TBL_TYPE.equals("1"))
		{
	%>
		<jsp:include page="workMonitoring one.jsp"></jsp:include>
	<%
		}
		else
		{
	%>
		<jsp:include page="workMonitoring two.jsp"></jsp:include>
	<%
		}	
	%>
</body>