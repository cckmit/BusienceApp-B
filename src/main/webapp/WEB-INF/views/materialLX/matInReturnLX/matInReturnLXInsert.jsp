<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="MIRI_Search()" />
			<img src="/images/button/Save.png" onclick="MIRI_Save()" />
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong style="color: red;">재고수량</strong></span>
				<input id="MIRI_stockQty" name="MIRI_stockQty" type="text" disabled>
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" name="Item_Code1" type="text" disabled>
				<span><strong style="color: red;">품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
				<span><strong>거래처코드</strong></span>
				<input id="Client_Code1" class="Client_Code1" type="text" disabled>
				<span><strong style="color: red;">거래처명</strong></span>
				<input id="Client_Name1" class="Client_Name1 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','1','in')}">
			</div>
		</div>
	</div>
	<div id="matInReturnInsertTable"></div>
</div>
<!-- END MAIN -->