<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" onclick="FS_LotListViewSearchBtn()"/>
			<img src="/images/button/Excel.png" onclick="excel_download(salesStockLotListTable)"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">
				<span><strong>LotNo</strong></span> 
				<input id="sales_lmaster_LotNo" type="text" style="width: 230px;" onkeypress="javascript:if(event.keyCode==13) {FS_LotListViewSearchBtn()}">
				<span style="color:red;"><strong>&nbsp;수량&nbsp;&nbsp;>&nbsp;&nbsp;0</strong></span>
				<input type="checkbox" id="Stock_Qty_Checked1" checked="checked" style="width:20px;">
			</div>
		</div>
	</div>
	<div id="salesStockLotListTable"></div>
</div>
<!-- END MAIN -->
