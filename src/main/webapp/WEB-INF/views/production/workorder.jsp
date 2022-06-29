<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/css/production/workorder.css" rel="stylesheet" />

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOutput -->
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="/images/button/Save.png" id="WO_SaveBtn" />
			</div>
			<div class="input-box">
				<span><strong>제품코드</strong></span>
				<input id="PRODUCT_ITEM_CODE" class="Item_Code" type="text" disabled>
				<span><strong>제품명</strong></span>
				<input id="PRODUCT_ITEM_NAME" class="Item_Name clearInput" type="text" autofocus>
				<span><strong>규격 2</strong></span>
				<input id="PRODUCT_ITEM_CODE2" class="Item_Code2" type="text" disabled>
				<span><strong>규격코드명</strong></span>
				<input id="PRODUCT_ITEM_NAME2" class="Item_Name2 clearInput" type="text" autofocus>
			</div>
		</div>
		<!-- 그리드 생성 장소 -->
		<div id="maskEquipTable" class="machineTable"></div>
		<div id="packEquipTable" class="machineTable"></div>
		<div id="labelEquipTable" class="machineTable"></div>
		<!-- 그리드 생성 장소 -->
		<!-- matOutput -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/production/workOrder.js?v=<%=System.currentTimeMillis()%>"></script>