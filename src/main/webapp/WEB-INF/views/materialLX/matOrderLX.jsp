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
					<img src="/images/button/Delete.png" id="MOM_DeleteBtn"/>
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
						<input id="InMat_Client_Code" class="Client_Code" type="text" disabled>
						<span><strong>거래처명</strong></span>
						<input id="InMat_Client_Name" class="clearInput Client_Name" type="text" autofocus
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
<!-- 프린트페이지에 보낼 데이터 -->
<form id="cus_frm" name="cus_frm" method="get" action="matOrderPrint" target="orderprint">
	<input type="hidden" id="mCus_No" name="mCus_No" value="">
	<input type="hidden" id="mCode" name="mCode" value="">
	<input type="hidden" id="mName" name="mName" value="">
	<input type="hidden" id="mDate" name="mDate" value="">
	<input type="hidden" id="mDlvry_Date" name="mDlvry_Date" value="">
	<input type="hidden" id="MyCom_Rgstr_Nr" name="MyCom_Rgstr_Nr" value="">
	<input type="hidden" id="mCusCo" name="mCusCo" value="">
	<input type="hidden" id="mCusMng" name="mCusMng" value="">
	<input type="hidden" id="mCusRprsn" name="mCusRprsn" value="">
	<input type="hidden" id="mCusRprsnPhNr" name="mCusRprsnPhNr" value="">
	<input type="hidden" id="mCusAdr" name="mCusAdr" value="">
	<input type="hidden" id="mCusMngPhNr" name="mCusMngPhNr" value="">
	<input type="hidden" id="mTotal" name="mTotal" value="">
	<!-- 우리 회사 정보 -->
	<input type="hidden" id="mOurCoName" name="mOurCoName" value="">
	<input type="hidden" id="mOurCoAdr" name="mOurCoAdr" value="">
	<input type="hidden" id="mOurCoRprsn" name="mOurCoRprsn" value="">
	<input type="hidden" id="mOurCoRprsnPhNr" name="mOurCoRprsnPhNr" value="">
	<input type="hidden" id="mOurCoMng" name="mOurCoMng" value="">
	<input type="hidden" id="mOurCoMngPhNr" name="mOurCoMngPhNr" value="">
	<input type="hidden" id="mOurCoEmail" name="mOurCoEmail" value="">
	<!-- Sub 테이블 정보 -->
	<input type="text" id="mSubNo" name="mSubNo[]" value="">
	<input type="hidden" id="mSubCode" name="mSub" value="">
	<input type="hidden" id="mSubName" name="mSub" value="">
	<input type="hidden" id="mSubSTND" name="mSub" value="">
	<input type="hidden" id="mSubQty" name="mSub" value="">
	<input type="hidden" id="mSubUnitPrice" name="mSub" value="">
	<input type="hidden" id="mSubPrice" name="mSub" value="">
	<input type="hidden" id="mSubRemark" name="mSub" value="">
	
	
</form>
<!-- END WRAPPER -->

<!-- Javascript -->
<script src="/js/materialLX/matOrderLX.js?v=<%=System.currentTimeMillis() %>"></script>