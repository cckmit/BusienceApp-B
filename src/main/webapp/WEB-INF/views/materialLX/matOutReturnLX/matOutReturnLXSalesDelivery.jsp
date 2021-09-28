<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="MORSL_Search()"/>
			<img src="/images/button/Save.png" onclick="MORSL_Save()"/>
		</div>
		<div class="input-box">
			<span><strong>품목코드</strong></span>
			<input id="PRODUCT_ITEM_CODE3" class="Item_Code3" type="text" disabled>
			<span><strong>품목명</strong></span>
			<input id="PRODUCT_ITEM_NAME3" class="Item_Name3 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','3','material')}">
		</div>
	</div>
	<div id="matOutReturnSalesListTable"></div>
	<div id="matOutReturnSalesDeliveryTable"></div>
</div>
<!-- END MAIN -->