<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img id="SearchBtn" src="images/button/Search.png" />
				<img id="SaveBtn" src="images/button/Save.png" />
				<img src="/images/button/Excel.png" onclick="excel_download(proResultTable)"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div>
					<span><strong>일자별</strong></span>
					<input id="startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
				</div>
				<div>
					<span><strong>제품코드</strong></span>
					<input id="itemCode" class="Item_Code" type="text" disabled>
					<span><strong>제품명</strong></span>
					<input id="itemName" class="Item_Name clearInput" type="text" autofocus>
					<span><strong>설비코드</strong></span>
					<input id="machineCode" class="Machine_Code" type="text" disabled>
					<span><strong>설비명</strong></span>
					<input id="machineName" class="Machine_Name clearInput" type="text">
				</div>
			</div>
		</div>
		<!-- 그리드 생성 장소 -->
		<div id="proResultTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/production/proResult.js?v=<%=System.currentTimeMillis() %>"></script>
</body>
</html>