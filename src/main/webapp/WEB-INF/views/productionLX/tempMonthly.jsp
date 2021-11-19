<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img id="SearchBtn"
					src="images/button/Search.png" />
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<span><strong>일자별</strong></span> <input id="startDate"
					class="today" type="date"> <span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date"> 
			</div>
		</div>
		<div id="proMachineTable" style="float: left; width: 40%;"></div>
		<div id="chart_div" style="float: left; width: 60%; height: calc(100% - 175px);">잠시만 기다려주세요.</div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/productionLX/tempMonthly.js"></script>

</body>
</html>