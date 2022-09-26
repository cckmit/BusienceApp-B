<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" onclick="SORI_Search()"/>
			<img src="images/button/Save.png" onclick="SORI_Save()"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>출고일</strong></span> 
				<input id="SORI_startDate" class="startDate today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="SORI_endDate" class="endDate tomorrow" type="date">
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE1" class="itemCode Item_Code1" type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME1" class="itemName Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">
				<span><strong>거래처코드</strong></span>
				<input id="Sales_OutMat_Client_Code1" class="clientCode Client_Code1" type="text" disabled>
				<span><strong>거래처명</strong></span>
				<input id="Sales_OutMat_Client_Name1" class="clientName Client_Name1 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','1','out')}">
			</div>
		</div>
	</div>
	<div id="salesOutReturnInsertTable"></div>
</div>
<!-- END MAIN -->