<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="soloView">
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" onclick="MI_searchBtn1()" />
				<img src="images/button/Save.png" onclick="MI_saveBtn1()"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div>
					<input id="currentDate" type="hidden" disabled
						value="${SM_Prcs_Date}"> <span><strong>작업완료일</strong></span>
					<input id="startDate" class="today" type="date"> <span
						style="text-align: center"><strong>~</strong></span> <input
						id="endDate" class="tomorrow" type="date">
				</div>
			</div>
		</div>
		<div id="WorkOrder_tbl" style="float:left; width: 70%;"></div>
		<div id="defectPerformance" style="float:left; border-left: solid; width: 30%;"></div>

		<script src="/js/qc/defectInsert.js"></script>
	</div>
</div>