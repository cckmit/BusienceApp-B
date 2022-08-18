<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="MS_ItemLotSearchBtn()"/>
			<img src="/images/button/Excel.png" onclick="excel_download(matStockLotListTable)"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE2" class="Item_Code2" type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME2" class="Item_Name2 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','2','material')}">
				<span><strong>LotNo</strong></span>
				<input id="matStockLotNo" class="matstockLotNo" type="text" style="width: 230px;">
				<!-- <span style="color:red;"><strong>&nbsp;수량&nbsp;&nbsp;>&nbsp;&nbsp;0</strong></span> -->
				<input type="checkbox" id="Stock_Qty_Checked1" class="none" checked="checked" style="width:20px;">
			</div>
		</div>
	</div>
	<div id="matStockLotListTable"></div>
</div>

<!-- Javascript -->
