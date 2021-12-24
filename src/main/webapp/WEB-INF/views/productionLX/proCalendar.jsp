<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href='/css/core/main.css' rel="stylesheet" />
<link href='/css/daygrid/main.css' rel="stylesheet" />
<script src='/js/core/main.js'></script>
<script src='/js/interaction/main.js'></script>
<script src='/js/daygrid/main.js'></script>

<!-- Update Modal -->
<jsp:include page="proCalendarModal.jsp"></jsp:include>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOutput -->
		<div class="master-in">
			<!-- <button type="button" class="btn btn-warning" id="reMonthSearchBtn" style="float: right;">월 재선택</button> -->
			<!-- 그리드 생성 장소 -->
			<div id="calendar"></div>
		</div>

	</div>
	<!-- END MAIN -->
</div>


<style>
body {
	padding: 0;
	font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
	font-size: 14px;
}

#calendar {
	width: 1000px;
	height: 200px;
	margin: 0 auto;
}
</style>


<script src="/js/productionLX/proCalendar.js"></script>