<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOutput -->
		<div class="master">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/ADD.png" id="WO_AddBtn" /> <img
						src="/images/button/Save.png" id="WO_SaveBtn" /> <img
						src="/images/button/Delete.png" id="WO_DeleteBtn" />
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="workOrderTable"></div>
		</div>
		<div class="sub">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button"></div>
				<!-- 버튼 -->
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="maskEquipTable" style="float: left; width: 49%;"></div>
			<div id="packEquipTable" style="float: right; width: 50%;"></div>
		</div>
		<!-- matOutput -->
		<!-- matOutputSub -->

		<!-- matOutputSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/productionLX/workOrder.js?v=<%=System.currentTimeMillis() %>"></script>