<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="main ">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" id="MOSL_SearchBtn" />
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<span><strong>해당월</strong></span>
			<input type="month" id="selectedMonth" class="this_month">
			<button id="preView-xlsx" class="btn btn-primary BtnStatus unUseBtn" style="margin-left: 50px">세금계산서 미리보기</button>
		</div>
	</div>
	<!-- 그리드 생성 장소 -->
	<div id="matOutputSalesCustomerTable" class="itemMaster"></div>
	<div id="matOutputSalesListTable" class="itemSub"></div>
</div>
<!-- END MAIN -->
