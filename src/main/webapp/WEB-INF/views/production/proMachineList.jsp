<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img id="SearchBtn" src="images/button/Search.png" />
				<img src="/images/button/Excel.png" onclick="excel_download(proMachineTable)"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<span><strong>일자별</strong></span>
				<input id="startDate" class="today" type="date">
				<span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date">
				<span><strong>설비코드</strong></span>
				<input id="EQUIPMENT_INFO_CODE" class="Machine_Code" type="text" disabled>
				<span><strong>설비명</strong></span>
				<input id="EQUIPMENT_INFO_NAME" class="Machine_Name clearInput" type="text" autofocus>
			</div>
		</div>
		<div id="proMachineTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/production/proMachineSum.js"></script>