<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<img id="LLM_SearchBtn" src="/images/button/Search.png"/>
			</div>
			<div class="input-box">
				<div>
					<span><strong>일자별</strong></span>
					<input id="startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
					<span><strong>LotNo</strong></span>
					<input id="itemPackinLotNo" class="itemPackinLotNo" type="text" style="width: 230px;" onclick="$(this).val('')" onkeypress="javascript:if(event.keyCode==13) {InspectionWorkONo()}" autofocus>
				</div>
				<div>
					<span><strong>제품코드</strong></span>
					<input id="PRODUCT_ITEM_CODE" class="Item_Code" type="text" disabled>
					<span><strong>제품명</strong></span>
					<input id="PRODUCT_ITEM_NAME" class="Item_Name clearInput" type="text" autofocus>
				</div>
			</div>
		</div>
		
		<div id="crateTable" style="width: calc(50% - 5px); float:left; margin-right: 10px;"></div>
		<div id="rawTable" style="width: calc(50% - 5px); float:left;"></div>
	</div>
	<!-- END MAIN -->
	
</div>
<script src="/js/production/lotListMaster.js"></script>