<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" onclick="FIL_SearchBtn()"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>입고일</strong></span> 
				<input id="matInputList_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="matInputList_endDate" class="tomorrow" type="date">			
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE1" class="Item_Code1"type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">
				<span><strong>입고구분</strong></span>
				<select id="inMatTypeListSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${InputType}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div id="salesInputListTable"></div>
</div>