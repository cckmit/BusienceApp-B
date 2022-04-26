<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOrder -->
		<div class="outputOrder-Top">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="SO_SearchBtn" src="images/button/Search.png" />
					<%-- <img id="SOO_Print" src="${contextPath}/resources/assets/img/Print.png"/> --%>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span style="width: 208px"></span> <span><strong>수주일</strong></span>
						<input id="startDate" class="today" type="date"> <span
							style="text-align: center">~</span> <input id="endDate"
							class="tomorrow" type="date">
					</div>
					<div>
						<span><strong>수주번호</strong></span> <input id="Sales_Order_mCus_No"
							type="text"> <span><strong>거래처코드</strong></span> <input
							id="Sales_InMat_Client_Code" class="Client_Code" type="text"
							disabled> <span><strong>거래처명</strong></span> <input
							id="Sales_InMat_Client_Name" class="Client_Name clearInput"
							type="text" autofocus
							onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','','out')}">
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesOrderTable"></div>
			<div class="mid-var">
				<div class="input-button">
					<img id="SOO_SaveBtn" class="unUseBtn BtnStatus" src="images/button/Save.png" />
				</div>
			</div>
			<div id="salesOutputOrderTable"></div>
		</div>
		<!-- matOrder -->
		<!-- matOrderSub -->
		<div class="outputOrder-Down">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="#" style="height: 73.13px; visibility: hidden;" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>수주번호</strong></span> <input id="sales_Order_lCus_No"
						type="text" disabled>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesOrderSubTable"></div>
			<div id="salesOutputOrderSubTable"></div>
			<div id="salesOutputStockTable"></div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/sales/salesOutputOrder.js"></script>