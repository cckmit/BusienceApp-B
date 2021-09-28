<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="MS_ItemListViewSearchBtn()"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE2" class="Item_Code2" type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME2" class="Item_Name2 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','2','material')}">
				<span style="color:red;"><strong>&nbsp;수량&nbsp;&nbsp;>&nbsp;&nbsp;0</strong></span>
				<input type="checkbox" id="Stock_Qty_Checked" checked="checked" style="width:20px;">
			</div>
		</div>
	</div>
	<div id="matStockItemListTable"></div>
</div>
<!-- END MAIN -->