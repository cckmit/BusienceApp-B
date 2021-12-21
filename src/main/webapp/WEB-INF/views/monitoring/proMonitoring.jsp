<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<head>
	<title>생산 모니터링</title>
</head>

<body style="background: rgb(42,44,57); color: white;">
	
	<%
		String CHILD_TBL_TYPE = (String)request.getAttribute("CHILD_TBL_TYPE");
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("1"))
		{
	%>
		<jsp:include page="proMonitoring1.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("2"))
		{
	%>
		<jsp:include page="proMonitoring2.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("4"))
		{
	%>
		<jsp:include page="proMonitoring4.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("6"))
		{
	%>
		<jsp:include page="proMonitoring6.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("8"))
		{
	%>
		<jsp:include page="proMonitoring8.jsp"></jsp:include>
	<%
		}
	%>
	
</body>