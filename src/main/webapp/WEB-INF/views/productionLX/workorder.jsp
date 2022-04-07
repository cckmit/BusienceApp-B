<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOutput -->
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<!-- 	<img src="/images/button/ADD.png" id="WO_AddBtn" />  -->
				<img src="/images/button/Save.png" id="WO_SaveBtn" />
				<!-- 	<img src="/images/button/Delete.png" id="WO_DeleteBtn" /> -->
			</div>
		</div>
		<!-- 그리드 생성 장소 -->
		<div id="workOrderTable" style="float: left; width: 59%;"></div>
		<div id="maskEquipTable" style="float: left; margin-left: 8px; width: 20%;"></div>
		<div id="packEquipTable" style="float: right; margin-left: 8px; width: 20%;"></div>
		<!-- 그리드 생성 장소 -->
		<!-- matOutput -->
		<!-- matOutputSub -->

		<!-- matOutputSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script
	src="/js/productionLX/workOrder.js?v=<%=System.currentTimeMillis()%>"></script>