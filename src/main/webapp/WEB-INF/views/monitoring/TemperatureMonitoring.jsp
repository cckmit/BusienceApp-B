<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
.progress-bar{
	background-color: #B0DEFC;
}

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

<div class="main" style="display: flex; flex-direction: column; align-items: center;">
	<div>
		<select id="machineCode" style="position: absolute; left: 100px; top: 10px; height: 40px; width: 220px; font-size: 30px">
			<c:forEach var="data" items="${machineList}">
				<c:choose>
					<c:when test="${data.EQUIPMENT_INFO_CODE == machineCode}">
						<option value="${data.EQUIPMENT_INFO_CODE}" selected>${data.EQUIPMENT_INFO_NAME}</option>
					</c:when>									
					<c:otherwise>									
						<option value="${data.EQUIPMENT_INFO_CODE}">${data.EQUIPMENT_INFO_NAME}</option>									
					</c:otherwise>								
				</c:choose>
			</c:forEach>
		</select>
		<h1>온도 모니터링</h1>
	</div>
  	<div class="checker">
    	<input id="Temperature_switch" type="checkbox" checked/>
  	</div>
	<h1>현재 온도</h1>
	<div style="height: 9vh; width: 100%; padding:0px 100px;">
		<div class="progress" style="height: 100%; width: 100%; border: solid;">
			<div id="progressb" class="progress-bar" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"
				style="color: black; line-height: normal; font-size: 6vh;">
				50°C
			</div>
		</div>
	</div>
	<h1>온도 그래프</h1>
	<div style="height: 70%; width: 100%; padding:0px 100px 0px 100px;">		
		<div id="chart_div" style="height: 80%; border: solid;">잠시만 기다려주세요.</div>
	</div>
</div>

<script src="/js/monitoring/TemperatureMonitoring.js?v=<%=System.currentTimeMillis() %>"></script>