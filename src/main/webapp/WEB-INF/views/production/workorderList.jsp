<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="FI_SearchBtn1" src="images/button/Search.png"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div class="col-sm-12 col-md-12">
						<input id="currentDate" type="hidden" disabled value="${SM_Prcs_Date}">
						<span><strong>등록일</strong></span> 
						<input id="startDate" class="today" type="date"> 
						<span style="text-align: center"><strong>~</strong></span> 
						<input id="endDate" class="tomorrow" type="date">
					</div>
					<br><br>
					<div class="col-sm-12 col-md-12">
						<span><strong>제품코드</strong></span>
						<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
						<span><strong>제품명</strong></span>
						<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">	
						
						<span><strong>설비코드</strong></span>
						<input id="Machine_Code1" class="Machine_Code1" type="text" disabled>
						<span><strong>설비명</strong></span>
						<input id="Machine_Name1" class="Machine_Name1 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {machinePopup($(this).val(),'input','1')}">					
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="WorkOrder_tbl" style="border-bottom: solid 0.5px;"></div>
			
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="FI_SearchBtn2" src="images/button/Search.png"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div class="col-sm-12 col-md-12">
						<span><strong>접수일</strong></span> 
						<input id="startDate2" class="today" type="date"> 
						<span style="text-align: center"><strong>~</strong></span> 
						<input id="endDate2" class="tomorrow" type="date">
					</div>
					<br><br>
					<div class="col-sm-12 col-md-12">
						<span><strong>제품코드</strong></span>
						<input id="PRODUCT_ITEM_CODE2" class="Item_Code2" type="text" disabled>
						<span><strong>제품명</strong></span>
						<input id="PRODUCT_ITEM_NAME2" class="Item_Name2 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','2','sales')}">	
						
						<span><strong>설비코드</strong></span>
						<input id="Machine_Code2" class="Machine_Code2" type="text" disabled>
						<span><strong>설비명</strong></span>
						<input id="Machine_Name2" class="Machine_Name2 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {machinePopup($(this).val(),'input','2')}">							
					</div>
				</div>
			</div>
			
			<div id="Sub_WorkOrder_tbl"></div>
		</div>
		<!-- END MAIN -->
	</div>
<script src="/js/today.js"></script>
<script src="/js/production/workOrderList.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/tabMenu.js"></script>

</body>
</html>