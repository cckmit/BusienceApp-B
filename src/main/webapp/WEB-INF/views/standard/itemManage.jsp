<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Update Modal -->
<jsp:include page="../modal/standard/itemManageModal.jsp"></jsp:include>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<img src="/images/button/ADD.png" id="itemADDBtn"/>
					
				<img src="/images/button/Update.png" id="itemUpdateBtn"/>
					
			 	<img src="/images/button/Delete.png" id="itemDeleteBtn"/>
			 	
			 	<img src="/images/button/Excel.png" onclick="excel_download(itemManageTable)"/>
			</div>
		</div>

		<div id="itemManageTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/standard/itemManage.js"></script>