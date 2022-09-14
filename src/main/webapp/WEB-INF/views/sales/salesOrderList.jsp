<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOrder -->
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="images/button/Search.png" id="SOL_SearchBtn"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>납기일</strong></span>
						<input id="startDate" class="today"	type="date">
						<span style="text-align: center"><strong>~</strong></span>
						<input id="endDate" class="tomorrow" type="date">
					</div>
					<div>
						<span><strong>거래처코드</strong></span> 
						<input id="Sales_InMat_Client_Code" class="Client_Code1" type="text" disabled> 
						<span><strong>거래처명</strong></span>
						<input id="Sales_InMat_Client_Name" class="Client_Name1 clearInput" type="text" autofocus
							onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','1','out')}">
						<span><strong>납기구분</strong></span>
						<select id="saleOrderTypeListSelectBox">
								<option></option>
								<option value="N">납품미완</option>
								<option value="Y">납품완료</option>
						</select> 
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesOrderListTable"></div>
		</div>
		<!-- matOrder -->
		<!-- matOrderSub -->
		<div class="sub-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button" style="height: 73.13px">
					<div>
						<span><strong>수주일 기준</strong></span>
					</div>
					<div>
						<span style="background-color: green; color: white;"><strong>3개월 이상</strong></span>&nbsp;&nbsp;
						<span style="background-color: orange; color: white;"><strong>6개월 이상</strong></span>&nbsp;&nbsp;
						<span style="background-color: red; color: white;"><strong>1년 이상</strong></span>
					</div>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>수주번호</strong></span> 
					<input id="sales_Order_lCus_No" type="text" disabled>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="salesOrderListSubTable"></div>
			<div id="salesOrderListStockTable"></div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/sales/salesOrderList.js?v=<%=System.currentTimeMillis() %>"></script>