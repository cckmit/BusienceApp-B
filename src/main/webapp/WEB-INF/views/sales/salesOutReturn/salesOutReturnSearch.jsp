<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" onclick="SORS_Search()"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>반품일</strong></span>
				<input id="SORS_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="SORS_endDate" class="tomorrow" type="date">		
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE2" class="Item_Code2"type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME2" class="Item_Name2 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','2','sales')}">
				<span><strong>거래처코드</strong></span>
				<input id="Sales_OutMat_Client_Code2" class="Client_Code2" type="text" disabled>
				<span><strong>거래처명</strong></span>
				<input id="Sales_OutMat_Client_Name2" class="Client_Name2 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','2','out')}">
			</div>
		</div>
	</div>
	<div id="salesOutReturnSearchTable"></div>
</div>