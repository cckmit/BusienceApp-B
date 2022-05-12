<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOutput -->
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="/images/button/Save.png" id="WO_SaveBtn" />
			</div>
		</div>
		<!-- 그리드 생성 장소 -->
		<div id="workOrderTable" style="float: left; width: calc(60% - 16px);"></div>
		<div id="maskEquipTable" style="float: left; margin-left: 8px; width: 20%;"></div>
		<div id="packEquipTable" style="float: left; margin-left: 8px; width: 20%;"></div>
		<!-- 그리드 생성 장소 -->
		<!-- matOutput -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/production/workOrder2.js?v=<%=System.currentTimeMillis()%>"></script>