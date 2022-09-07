<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" id="MIRI_SearchBtn"/>
			<img src="/images/button/Save.png" id="MIRI_SaveBtn"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<span><strong>품목코드</strong></span>
			<input id="PRODUCT_ITEM_CODE1" class="itemCode Item_Code1" name="Item_Code1" type="text" disabled>
			<span><strong>품목명</strong></span>
			<input id="PRODUCT_ITEM_NAME1" class="itemName Item_Name1 clearInput" type="text" autofocus
				onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
			<span><strong>거래처코드</strong></span>
			<input id="Client_Code1" class="ClientCode Client_Code1" type="text" disabled>
			<span><strong>거래처명</strong></span>
			<input id="Client_Name1" class="ClientName Client_Name1 clearInput" type="text"
				onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','1','in')}">
		</div>
	</div>
	<div id="matInReturnInsertTable"></div>
</div>
<!-- END MAIN -->