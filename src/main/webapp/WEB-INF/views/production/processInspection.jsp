<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/css/production/processInspectForm.css" rel="stylesheet" />

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="master-inspect">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="CI_SearchBtn" /> 
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>생산일</strong></span>
						<input id="startDate" class="today" type="date">
						<span style="text-align: center"><strong>~</strong></span>
						<input id="endDate" class="tomorrow" type="date">	
					</div>
					<div>
						<span><strong>품목코드</strong></span>
						<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
						<span><strong>품목명</strong></span>
						<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
						<span><strong>LotNo</strong></span>
						<input id="processLotNo" type="text">	
						<br>
						<span><strong>설비코드</strong></span>
						<input id="EQUIPMENT_INFO_CODE" class="Machine_Code" type="text" disabled>
						<span><strong>설비명</strong></span>
						<input id="EQUIPMENT_INFO_NAME" class="Machine_Name clearInput" type="text" autofocus>
					</div>
				</div>
			</div>
			<div id="crateInspectTable"></div>
			<div id="processInspectTable"></div>
		</div>
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Save.png" id="PI_SaveBtn" class="unUseBtn BtnStatus" /> 
					<img src="/images/button/Print.png" id="PI_PrintBtn" />
				</div>
			</div>
			<div id="matInputInspection"><jsp:include page="processInspectForm.jsp" /></div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script
	src="/js/production/processInspection.js?v=<%=System.currentTimeMillis()%>"></script>
