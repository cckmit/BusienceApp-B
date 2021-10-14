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
				<input id="endDate" class="tomorrow" type="date"> <span><strong>설비코드</strong></span>
				<input id="EQUIPMENT_INFO_CODE" class="Machine_Code" type="text"
					disabled> <span><strong>설비명</strong></span> <input
					id="EQUIPMENT_INFO_NAME" class="Machine_Name clearInput"
					type="text" autofocus>
			</div>
		</div>
		<div id="proMachineListTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/productionLX/proMachineListLXX.js"></script>
</body>
</html>