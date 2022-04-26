<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOrder -->
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="SO_SearchBtn" src="images/button/Search.png" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span style="width: 208px"></span> <span><strong>납기일</strong></span>
						<input id="startDate" class="today" type="date"> <span
							style="text-align: center">~</span> <input id="endDate"
							class="tomorrow" type="date">
					</div>
					<div>
						<span><strong>지시번호</strong></span> <input
							id="Sales_Order_mOrder_No" type="text"> <span><strong>거래처코드</strong></span>
						<input id="Sales_InMat_Client_Code" class="Client_Code"
							type="text" disabled> <span><strong>거래처명</strong></span>
						<input id="Sales_InMat_Client_Name" class="Client_Name clearInput"
							type="text" autofocus
							onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','','out')}">
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesOutputOrderTable"></div>
			<div id="salesOutputOrderSubTable"></div>
		</div>
		<!-- matOrder -->
		<!-- matOrderSub -->
		<div class="sub-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="#" style="height: 73.13px; visibility: hidden;" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>품목코드</strong></span> <input id="Sales_Order_lCode"
						type="text" disabled> <span><strong>품목명</strong></span> <input
						id="Sales_Order_lName" type="text" disabled>
				</div>
				<!-- 버튼 -->
				<div class="input-button" style="margin-left:5%">
					<button id="allOutput" class="btn btn-primary">자동선택</button>
				</div>
				<!-- 버튼 -->
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesLotMasterTable"></div>
			<div class="mid-var">
				<div class="input-button">
					<img src="images/button/Save.png" id="SOM_SaveBtn" class="unUseBtn BtnStatus" />
				</div>
			</div>
			<div id="salesOutMatTable"></div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/salesLX/salesOutput.js?v=<%=System.currentTimeMillis() %>"></script>