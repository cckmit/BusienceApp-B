<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="nowDate" class="java.util.Date" />
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" onclick="MI_searchBtn1()"/>
				<img src="/images/button/Excel.png" onclick="excel_download(WorkOrder_tbl)"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div class="col-sm-14 col-md-14">
					<span><strong>작업지시일</strong></span> 
					<input id="startDate" class="today" type="date"> 
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
					<span></span>
					<strong style="color: red;">기준일 : <fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd" /></strong>&nbsp;
					<span></span>
					<input type="button" id="MI_restartSearch" value="오늘 기준 조회" />
				</div>
				
				<div class="col-sm-14 col-md-14">
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
<script src="/js/productionLX/workdList.js"></script>