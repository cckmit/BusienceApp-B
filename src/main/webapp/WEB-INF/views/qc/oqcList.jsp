<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/css/qc/oqcInspectForm.css" rel="stylesheet" />

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="master-inspect">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="SOIL_SearchBtn" /> 
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>등록일</strong></span>
						<input id="startDate" class="today" type="date">
						<span style="text-align: center"><strong>~</strong></span>
						<input id="endDate" class="tomorrow" type="date">	
					</div>
					<div>
						<span><strong>품목코드</strong></span>
						<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
						<span><strong>품목명</strong></span>
						<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">
						<span><strong>LotNo</strong></span>
						<input id="oqcLotNo" type="text">	
						<span><strong>거래처코드</strong></span> 
						<input id="InMat_Client_Code1" class="Client_Code1" type="text" disabled> 
						<span><strong>거래처명</strong></span> 
						<input id="InMat_Client_Name1" class="Client_Name1 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','1','out')}">
					</div>
				</div>
			</div>
			<!-- <div id="salesOutMatTable"></div> -->
			<div id="oqcInspection"><jsp:include page="oqcInspectHeadForm.jsp"/></div>
			<div id="oqcInspectTable"></div>
		</div>
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button"  style="width: 550px;">
					<!-- <img src="/images/button/Save.png" id="SOI_SaveBtn" class="unUseBtn BtnStatus" /> --> 
					<!-- <img src="/images/button/Print.png" id="SOI_PrintBtn" /> -->
				</div>
				<div style="float: right; margin-left: 20px !important; margin-top: 20px; !important;" id="oqcInspection"><jsp:include page="oqcAppearanceInspectForm.jsp" /></div>
			</div>
			<div id="oqcInspection"><jsp:include page="oqcOutputInspectForm.jsp" /></div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/qc/oqcList.js?v=<%=System.currentTimeMillis()%>"></script>
