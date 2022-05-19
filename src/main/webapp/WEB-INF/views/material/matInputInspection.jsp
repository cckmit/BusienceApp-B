<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOutput -->
		<div class="master-inspect">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="MII_SearchBtn"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>가입고일</strong></span>
						<input id="startDate" class="today" type="date">
						<span style="text-align: center"><strong>~</strong></span>
						<input id="endDate" class="tomorrow" type="date">		
					</div>
					<div>
						<span><strong>품목코드</strong></span>
						<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
						<span><strong>품목명</strong></span>
						<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
						<br>
						<span><strong>거래처코드</strong></span>
						<input id="Temp_InMat_Client_Code" class="Client_Code" type="text" disabled>
						<span><strong>거래처명</strong></span>
						<input id="Temp_InMat_Client_Name" class="Client_Name clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','','in')}">
					</div>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="tempStorageTable"></div>
			<div id="matInputTable"></div>
		</div>
		<!-- matOutput -->
		<!-- matOutputSub -->
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Save.png" id="MIF_SaveBtn" class="unUseBtn BtnStatus"/>
					<img src="/images/button/Print.png" id="MII_PrintBtn"/>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="matInputInspection"><jsp:include page="matInputInspectForm.jsp" /></div>
			<!-- 그리드 생성 장소 -->
		</div>
	   	<!-- matOrderList -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/material/matInputInspection.js?v=<%=System.currentTimeMillis() %>"></script>
