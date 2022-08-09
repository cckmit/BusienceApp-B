<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOrder -->
		<div class="master" style="width: calc(50% - 5px);">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="routingItemSearchBtn"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>제품코드</strong></span>
						<input id="salesCode" class="Item_Code" type="text" disabled>
						<span><strong>제품명</strong></span>
						<input id="salesName" class="Item_Name clearInput" style="width: 200px;" type="text" autofocus
							onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','','sales')}">
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="PRODUCT_INFO_TBL"></div>
		</div>
		<!-- matOrder -->
		<!-- matOrderSub -->
		<div class="sub" style="width: calc(50% - 5px);">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Save.png" id="Bom_Save"/>
					<img src="/images/button/Delete.png" id="Bom_Delete"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>수주번호</strong></span>
					<input id="sales_Order_lCus_No" type="text" disabled>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="Routing_tbl"></div>
			<div id="BOMListTable"></div>
		</div>
	   	<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>

<!-- Javascript -->
<script src="js/standard/routingInput.js?v=<%=System.currentTimeMillis() %>"></script>
