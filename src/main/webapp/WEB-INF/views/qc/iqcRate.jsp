<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img id="SearchBtn" src="images/button/Search.png" />
				<!-- <img src="/images/button/Excel.png" onclick="excel_download(proItemListTable)"/> -->
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<span><strong>검사완료일</strong></span>
				<input id="startDate" class="today" type="date">
				<span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date">
				<span><strong>제품코드</strong></span>
				<input id="itemCode" class="Item_Code1" type="text" disabled>
				<span><strong>제품명</strong></span>
				<input id="itemName" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
			</div>
		</div>
		<div id="iqcRateTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/qc/iqcRate.js?v=<%=System.currentTimeMillis() %>"></script>