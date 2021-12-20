<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Insert Modal -->
<jsp:include page="../modal/standard/defectManageModal.jsp"></jsp:include>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<img src="/images/button/ADD.png" id="defectADDBtn"/>
				
				<img src="/images/button/Update.png" id="defectUpdateBtn"/>
				
				<img src="/images/button/Delete.png" id="defectDeleteBtn"/>
				
				<img src="/images/button/Excel.png" id="download_xlsx"/>
			</div>
		</div>
		<div id="defectManageTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/standard/defectManage.js"></script>
