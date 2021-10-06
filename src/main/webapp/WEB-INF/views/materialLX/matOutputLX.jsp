<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOutput -->
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png"
						id="MR_SearchBtn" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>품목코드</strong></span> 
						<input id="outmatLX_itemCode" type="text" class="Item_Code1"> 
						<span><strong>품목명</strong></span>
						<input id="outmatLX_itemName" type="text" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="outputTable"></div>
		</div>
		<!-- matOutput -->
		<!-- matOutputSub -->
		<div class="sub-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Save.png" id="MOM_SaveBtn"
						class="unUseBtn BtnStatus" />
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="outputSubTable"></div>
			<div class="mid-var">
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="matOutputTable"></div>
		</div>
		<!-- matOrderList -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/materialLX/matOutputLX.js"></script>
