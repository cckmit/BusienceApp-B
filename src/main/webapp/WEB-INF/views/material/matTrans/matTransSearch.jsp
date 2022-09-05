<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="MORS_Search()"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>이동일</strong></span>
				<input id="MORS_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="MORS_endDate" class="tomorrow" type="date">		
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE2" class="Item_Code2" type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME2" class="Item_Name2 clearInput" type="text"
					onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','2','material')}">
			</div>
		</div>
	</div>
	<div id="matOutReturnSearchTable"></div>
</div>
<!-- END MAIN -->