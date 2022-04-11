<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matInput -->
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="MI_SearchBtn"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span style="width:208px"></span>
						<span><strong>납기일</strong></span>
						<input id="startDate" class="today" type="date">
						<span style="text-align: center"><strong>~</strong></span>
						<input id="endDate" class="tomorrow" type="date">		
					</div>
					<div>
						<span><strong>발주번호</strong></span>
						<input id="InMat_Order_No" type="text">
						<span><strong>거래처코드</strong></span>
						<input id="InMat_Client_Code" class="Client_Code" type="text" disabled>
						<span><strong>거래처명</strong></span>
						<input id="InMat_Client_Name" class="Client_Name clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','','in')}">
					</div>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="matInputTable"></div>
		</div>
		<!-- matInput -->
		<!-- matInputSub -->
		<div class="sub-in">
			<div class="top-var" style="display: flex; align-items: center;">
				<div class="input-box">
					<span><strong>발주번호</strong></span>
					<input id="Order_lCus_No" type="text" disabled>
				</div>
				<!-- 버튼 -->
				<div class="input-button" style="margin-left:5%">
					<button id="allInput" class="btn btn-primary">전체입고</button>
				</div>
				<!-- 버튼 -->
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="matInputSubTable"></div>
			<div class="mid-var">
				<div class="input-button">
					<img src="/images/button/Save.png" id="MIM_SaveBtn" class="unUseBtn BtnStatus"/>
				</div>
			</div>
			<div id="inMatTable"></div>
		</div>
	   	<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>

<!-- Javascript -->
<script src="/js/materialLX/matInputLX.js"></script>
