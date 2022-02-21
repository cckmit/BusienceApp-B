<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img id="SearchBtn" src="images/button/Search.png" />
				<img src="/images/button/Excel.png" onclick="excel_download(tempDailyTable)"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div>
					<span><strong>설비</strong></span>
					<select id="machineCode">
						<c:forEach var="data" items="${machineList}">
							<option value="${data.EQUIPMENT_INFO_CODE}">${data.EQUIPMENT_INFO_NAME}</option>									
						</c:forEach>
					</select>
					<span><strong>조건</strong></span>
					<select id="condition">
						<option value="monthly">월별
						<option value="daily">일별
						<option value="hourly">시간별
					</select>
				</div>
				<div>
					<span><strong>검색일</strong></span>
					<input id="startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
				</div> 
			</div>
		</div>
		<div id="tempDailyTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/productionLX/tempDaily.js?v=<%=System.currentTimeMillis() %>"></script>