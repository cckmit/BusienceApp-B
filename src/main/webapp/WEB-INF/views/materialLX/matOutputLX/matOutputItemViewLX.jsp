<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" id="MOIL_SearchBtn"/>
			<img src="/images/button/Excel.png" onclick="excel_download(matOutputItemViewTable)"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>출고일</strong></span> 
				<input id="matOutputItemView_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="matOutputItemView_endDate" class="tomorrow" type="date">
				<span><strong>출고구분</strong></span>
				<select id="outMatTypeItemViewSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${OutMatType}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE2" class="Item_Code2" type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME2" class="Item_Name2 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','2','material')}">
			</div>
		</div>
	</div>
	<div id="matOutputItemViewTable"></div>
</div>
<!-- END MAIN -->