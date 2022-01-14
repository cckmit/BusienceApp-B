<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<!-- matOrder -->
			<div class="master">
				<div class="top-var">
					<!-- 버튼 -->
					<div class="input-button">
						<img src="images/button/ADD.png" id="SO_AddBtn"/>
						<img src="images/button/Search.png" id="SO_SearchBtn"/>
					</div>
					<!-- 버튼 -->
					<div class="input-box">
						<div>
							<span>수주일</span>
							<input id="startDate" class="today" type="date">
							<span style="text-align: center">~</span>
							<input id="endDate" class="tomorrow" type="date">		
						</div>
						<div>
							<span>거래처코드</span>
							<input id="Sales_InMat_Client_Code" class="Client_Code1" type="text" disabled>
							<span>거래처명</span>
							<input id="Sales_InMat_Client_Name" class="Client_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','1','out')}">
						</div>
					</div>
				</div>
				<!-- 그리드 생성 장소 -->
				<div id="salesOrderTable"></div>
			</div>
			<!-- matOrder -->
			<!-- matOrderSub -->
			<div class="sub">
				<div class="top-var">
					<!-- 버튼 -->
					<div class="input-button">
						<img src="images/button/ADD.png" id="SOL_AddBtn" class="unUseBtn BtnStatus"/>
						<img src="images/button/Delete.png" id="SOL_DeleteBtn" class="unUseBtn BtnStatus"/>
						<img src="images/button/Save.png" id="SOL_SaveBtn" class="unUseBtn BtnStatus"/>
					</div>
					<!-- 버튼 -->
					<div class="input-box">
						<span><strong>수주번호</strong></span>
						<input id="sales_Order_lCus_No" type="text" disabled>
					</div>
				</div>
				<!-- 그리드 생성 장소 -->
				<div id="salesOrderSubTable"></div>
				<div id="salesOrderStockTable"></div>
			</div>
		   	<!-- matOrderSub -->
		</div>
		<!-- END MAIN -->
	</div>
<!-- Javascript -->
<script src="/js/salesLX/salesOrderLX.js?v=<%=System.currentTimeMillis() %>"></script>
</body>
</html>