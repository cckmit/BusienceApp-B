<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- Insert Modal -->
<jsp:include page="../modal/standard/machineManageModal.jsp"></jsp:include>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<img src="/images/button/ADD.png" id="machineADDBtn"/>
				
				<img src="/images/button/Update.png" id="machineUpdateBtn"/>
				
				<img src="/images/button/Delete.png" id="machineDeleteBtn"/>
				
				<img src="/images/button/Excel.png" onclick="excel_download(machineManageTable)"/>
			</div>
		</div>
			<!-- 그리드 생성 장소 -->
		<div id="machineManageTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/standard/machineManage.js?v=<%=System.currentTimeMillis() %>"></script>