<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" id="searchBtn"/>
				<img src="/images/button/Excel.png" onclick="excel_download(workOrderTable)"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div class="col-sm-14 col-md-14">
					<span><strong>작업등록일</strong></span> 
					<input id="startDate" class="today" type="date"> 
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
					<span></span>
					<input type="button" id="now_SearchBtn" value="오늘 기준 조회" />
				</div>
				
				<div class="col-sm-14 col-md-14">
					<span><strong>제품코드</strong></span>
					<input class="itemCode Item_Code1" type="text" disabled>
					<span><strong>제품명</strong></span>
					<input class="itemName Item_Name1 clearInput" type="text"
						onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','sales')}">	
							
					<span><strong>설비코드</strong></span>
					<input class="machineCode Machine_Code1" type="text" disabled>
					<span><strong>설비명</strong></span>
					<input class="machineName Machine_Name1 clearInput" type="text"
						onkeypress="javascript:if(event.keyCode==13) {machinePopup($(this).val(),'input','1')}">					
				</div>
			</div>
		</div>
		<div id="workOrderTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/production/workDetailList.js?v=<%=System.currentTimeMillis()%>"></script>