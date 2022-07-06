<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<img id="SIS_SearchBtn" src="/images/button/Search.png"/>
			</div>
			<div class="input-box">
				<div>
					<span><strong>소포장일</strong></span>
					<input id="startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
				</div>
			</div>
		</div>
		<!-- <div id="largePackTable" style="width: calc(25% - 10px); float:left; margin-right: 10px;"></div> -->
		<div id="smallPackTable" style="width: calc(33% - 10px); float:left; margin-right: 10px;"></div>
		<div id="crateTable" style="width: calc(33% - 10px); float:left; margin-right: 10px;"></div>
		<div id="rawTable" style="width: calc(34% - 1px); float:left;"></div>
	</div>
	<!-- END MAIN -->
	
</div>
<script src="/js/production/matInputSum.js?v=<%=System.currentTimeMillis() %>"></script>