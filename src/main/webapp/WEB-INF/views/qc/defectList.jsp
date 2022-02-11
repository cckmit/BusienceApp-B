<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" id="DL_SearchBtn"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<span><strong>작업완료일</strong></span>
				<input id="startDate" class="today" type="date">
				<span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date">
			</div>
		</div>
		<div id="WorkOrderTable" style="float:left; width: calc(55% - 10px)"></div>
		<div id="defectTable" style="float:left; width: 45%; margin-left: 10px;"></div>

	</div>
</div>

<script src="/js/qc/defectList.js?v=<%=System.currentTimeMillis() %>"></script>