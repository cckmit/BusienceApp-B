<%@page import="com.busience.standard.dto.Equip_Monitoring_TBL"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
</style>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<link href="//cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.4.0/css/bootstrap4-toggle.min.css" rel="stylesheet">  
<script src="//cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.4.0/js/bootstrap4-toggle.min.js"></script>

<style>
body{
}
input[type="checkbox"]{
	width: 130px;
	height: 48px;
	display:block;
	background-image:url(../images/button/Back1.png);
	-webkit-appearance:none;
	-webkit-transition:1s;
	padding:3px 4px 3px 4px;
}
input[type="checkbox"]:after{
	content:'';
	display:block;
	position:relative;
	top:0;
	left:0;
	width: 60px;
	height: 44px;
	border-radius: 8px; /* from vector shape */
	background-image:url(../images/button/Knob.png);
	color: #f9f3b6;
}
input[type="checkbox"]:checked {
    	padding-left: 67px;
    	padding-right: 0;
	background-image:url(../images/button/Back2.png);
}
input[type="checkbox"]:hover {
	opacity:1;
}

</style>

</head>
<body style="padding: 0px 30px 30px 30px;">
	<%
		List<Equip_Monitoring_TBL> list1 = (List<Equip_Monitoring_TBL>)request.getAttribute("list1");
		
		for(int i=0;i<list1.size();i++)
		{
	%>
		<div style="font-size: 50px; border: solid;">
			<%=list1.get(i).getEquip_Name()%>
			<div>
			  <%
			  		if(list1.get(i).getEquip_Status().equals("true"))
			  		{
			  %>
			  		<div class="sample">
					  	<div class="checker">
					        	<input type="checkbox" id="<%=list1.get(i).getEquip_Code()%>" checked="checked" onclick="equip_stat_change(this)"/>
					  	</div>
					</div>
			  <%
			  		}
			  		else
			  		{
			  %>
			  		<div class="sample">
					  	<div class="checker">
					        	<input type="checkbox" id="<%=list1.get(i).getEquip_Code()%>" onclick="equip_stat_change(this)"/>
					  	</div>
					</div>
			  <%
			  		}
			  %>
			  
			</div>
		</div>	
	<%
		}
	%>

	<script type="text/javascript">
	function equip_stat_change(check){
		data = {
			id : check.id,
			checked : check.checked
		};

		if(check.checked)
		{
			$.get("../tempStatusControlRest/tempStatusOnChange",data,function(){});
		}
		else
		{
			$.get("../tempStatusControlRest/tempStatusOffChange",data,function(){});
		}
	}
	</script>
</body>
</html>