<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!-- Insert Modal -->
<jsp:include page="../modal/standard/customerManageModal.jsp"></jsp:include>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
			
				<img src="/images/button/ADD.png" id="customerADDBtn"/>
				
				<img src="/images/button/Update.png" id="customerUpdateBtn"/>
				
				<img src="/images/button/Delete.png" id="customerDeleteBtn"/>
				
				<img src="/images/button/Excel.png" onclick="excel_download(customerManageTable)"/>
			</div>
		</div>
		<div id="customerManageTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/standard/customerManage.js"></script>
