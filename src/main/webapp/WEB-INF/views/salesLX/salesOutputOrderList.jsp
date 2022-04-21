<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOrder -->
		<div class="master">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="SOR_SearchBtn" src="images/button/Search.png"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>납기일</strong></span> <input id="startDate"
							class="today" type="date"> <span
							style="text-align: center"><strong>~</strong></span> <input
							id="endDate" class="tomorrow" type="date"> <span></span>
					</div>
					<div>
						<span><strong>거래처코드</strong></span> <input
							id="Sales_InMat_Client_Code" class="Client_Code1" type="text"
							disabled> <span><strong>거래처명</strong></span> <input
							id="Sales_InMat_Client_Name" class="Client_Name1 clearInput"
							type="text" autofocus
							onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','1','out')}">
						<span><strong>지시구분</strong></span> <select
							id="saleOrderTypeListSelectBox">
							<option value="all">all</option>
							<option value="N">납품미완</option>
							<option value="Y">납품완료</option>
						</select>
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesOutputOrderReportTable"></div>
		</div>
		<!-- matOrder -->
		<!-- matOrderSub -->
		<div class="sub">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="#" style="height: 73.13px; visibility: hidden;" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>지시번호</strong></span> <input
						id="sales_Output_Order_lOrder_No" type="text" disabled>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesOutputOrderReportSubTable"></div>
			<div id="salesOutputOrderReportStockTable"></div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/salesLX/salesOutputOrderList.js"></script>