<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOrder -->
		<div class="master" style="width: calc(50% - 5px);">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="Product_SearchBtn"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>제품코드</strong></span>
						<input id="salesCode" class="Item_Code" type="text" disabled>
						<span><strong>제품명</strong></span>
						<input id="salesName" class="Item_Name clearInput" style="width: 200px;" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','','sales')}">
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
					<img src="/images/button/Save.png" id="SOL_SaveBtn"/>
					<img src="/images/button/Delete.png" id="SOL_DeleteBtn"/>
					
				</div>
				<!-- 버튼 -->
				<div class="input-box" style="visibility: hidden;">
					<span><strong>수주번호</strong></span>
					<input id="sales_Order_lCus_No" type="text" disabled>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div style="float: left; width: 20%; margin-right: 30px;" id="Routing_tbl"></div>
			<div style="float: clear; width: 70%;" id="Material_Model_Mapping_tbl"></div>
			
			<div id="BOMListTable"></div>
		</div>
	   	<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>

<!-- Javascript -->
<script src="/js/standard/matmodelmapping.js?v=<%=System.currentTimeMillis() %>"></script>
