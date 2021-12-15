<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<head>
	<title>불량 모니터링</title>
</head>

<body style="background: rgb(42,44,57); color: white;">
	<%
		String CHILD_TBL_TYPE = (String)request.getAttribute("CHILD_TBL_TYPE");
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("1"))
		{
	%>
		<jsp:include page="defectMonitoring1m.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("2"))
		{
	%>
		<jsp:include page="defectMonitoring2m.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("4"))
		{
	%>
		<jsp:include page="defectMonitoring4m.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("6"))
		{
	%>
		<jsp:include page="defectMonitoring6m.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("8"))
		{
	%>
		<jsp:include page="defectMonitoring8m.jsp"></jsp:include>
	<%
		}
	%>
	
</body>