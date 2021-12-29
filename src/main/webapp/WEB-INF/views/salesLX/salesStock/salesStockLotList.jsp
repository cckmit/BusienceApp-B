<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" onclick="FS_LotListViewSearchBtn()"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>LotNo</strong></span> 
				<input id="sales_lmaster_LotNo" type="text" onkeypress="javascript:if(event.keyCode==13) {FS_LotListViewSearchBtn()}">
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">
			</div>
		</div>
	</div>
	<div id="salesStockLotListTable"></div>
</div>
<!-- END MAIN -->
