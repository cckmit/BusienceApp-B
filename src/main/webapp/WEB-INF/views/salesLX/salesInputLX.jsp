<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- salesInputMaster -->
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="SI_SearchBtn" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>품목코드</strong></span> 
					<input id="itemCode" type="text" class="Item_Code1" disabled> 
					<span><strong>품목명</strong></span>
					<input id="itemName" type="text" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesInputMasterTable"></div>
		</div>
		<!-- salesInputMaster -->
		<!-- salesInputSub -->
		<div class="sub-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Save.png" id="SI_SaveBtn" class="unUseBtn BtnStatus" />
					<!-- <span><strong>프린터 :</strong></span>
					<select id="selected_device" onchange=onDeviceSelected(this);></select> -->
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesInputSubTable"></div>
			<!-- 그리드 생성 장소 -->
		</div>
		<!-- salesInputSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/salesLX/salesInputLX.js?v=<%=System.currentTimeMillis() %>"></script>
<%-- <script src="/js/labelPrint.js?v=<%=System.currentTimeMillis() %>"></script> --%>
