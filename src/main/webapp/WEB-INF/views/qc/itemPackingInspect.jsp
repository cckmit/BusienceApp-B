<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/css/qc/itemPackingInspectForm.css" rel="stylesheet" />

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="master-inspect">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="SIL_SearchBtn" /> 
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
						<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
						<span><strong>LotNo</strong></span>
						<input id="itemPackLotNo" type="text">	
					</div>
				</div>
			</div>
			<div id="salesItemTable"></div>
			<div id="itemInspectTable"></div>
		</div>
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button" style="width: 550px;">
					<img src="/images/button/Save.png" id="IPI_SaveBtn" class="unUseBtn BtnStatus" /> 
					<img src="/images/button/Print.png" id="IPI_PrintBtn" />
					<div style="float: right; background-color: red; color: white; margin-left: 20px; margin-top: 20px; height: 27px !important;">
						<strong style="font-size: 20px;">검사 완료 데이터</strong>
					</div>
				</div>
				<div style="float: right;" id="itemPackInspection"><jsp:include page="itemPackAppearanceInspectForm.jsp" /></div>
			</div>
			<div id="itemPackingInspection"><jsp:include page="itemPackingInspectForm.jsp" /></div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/qc/itemPackingInspect.js?v=<%=System.currentTimeMillis()%>"></script>
