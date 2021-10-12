<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="nowDate" class="java.util.Date" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
html, body {
	margin: 0;
	height: 100%;
	overflow: hidden;
}
</style>

</head>
<body>
	<div
		style="margin: 10; width: 100%; height: 100%; position: absolute; border: solid;">

		<div class="soloView" style="width: 100%;">
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" onclick="MI_searchBtn1()" />
				<img src="images/button/Save.png" onclick="MI_saveBtn1()"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div>
					<input id="currentDate" type="hidden" disabled
						value="${SM_Prcs_Date}"> <span><strong>작업완료일</strong></span>
					<input id="startDate" class="today" type="date"> <span
						style="text-align: center"><strong>~</strong></span> <input
						id="endDate" class="tomorrow" type="date">
				</div>
			</div>
		</div>
		<div id="WorkOrder_tbl"></div>
		<div id="chart_div" style="height: 45%;"></div>
		<div id="defectPerformance" style="border-top: solid; visibility: hidden;"></div>

		<script src="/js/qc/defectPerformanceM.js"></script>
	</div>
</div>
		
	</div>
</body>
</html>