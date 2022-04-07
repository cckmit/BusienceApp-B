<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" onclick="MI_searchBtn1()"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div class="col-sm-12 col-md-12">
					<span><strong>등록일</strong></span> 
					<input id="startDate1" class="today" type="date"> 
					<span style="text-align: center"><strong>~</strong></span> 
					<input id="endDate1" class="tomorrow" type="date">
				</div>
				
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
		<div id="WorkOrder_tbl"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/productionLX/workList.js?v=<%=System.currentTimeMillis() %>"></script>