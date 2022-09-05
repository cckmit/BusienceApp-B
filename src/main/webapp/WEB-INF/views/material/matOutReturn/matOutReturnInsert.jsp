<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" id="returnSearchBtn"/>
			<img src="/images/button/Save.png" onclick="MORI_Save()"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<span><strong>품목코드</strong></span>
			<input class="itemCode" type="text" disabled>
			<span><strong>품목명</strong></span>
			<input class="clearInput itemName" type="text" autofocus
				onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','','material')}">
		</div>
	</div>
	<div id="matOutReturnInsertTable"></div>
</div>
<!-- END MAIN -->