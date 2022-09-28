<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOrder -->
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="MO_SearchBtn"/>
					<img src="/images/button/ADD.png" id="MO_AddBtn"/>
					<img src="/images/button/Print.png" id="MO_PrintBtn"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>발주일</strong></span>
						<input id="startDate" class="today" type="date">
						<span style="text-align: center">~</span>
						<input id="endDate" class="tomorrow" type="date">
					</div>
					<div>
						<span><strong>거래처코드</strong></span>
						<input class="clientCode Client_Code" type="text" disabled>
						<span><strong>거래처명</strong></span>
						<input class="clientName clearInput Client_Name" type="text" autofocus
							onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','','in')}">
					</div>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="matOrderTable"></div>
		</div>
		<!-- matOrder -->
		<!-- matOrderSub -->
		<div class="sub-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/ADD.png" id="MOL_AddBtn" class="unUseBtn BtnStatus"/>
					<img src="/images/button/Delete.png" id="MOL_DeleteBtn" class="unUseBtn BtnStatus"/>
					<img src="/images/button/Save.png" id="MOL_SaveBtn" class="unUseBtn BtnStatus"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>발주번호</strong></span>
					<input id="order_lCus_No" type="text" disabled>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="matOrderSubTable"></div>
			<div id="matOrderStockTable"></div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- END WRAPPER -->

<!-- Javascript -->
<script src="/js/material/matOrder.js?v=<%=System.currentTimeMillis() %>"></script>