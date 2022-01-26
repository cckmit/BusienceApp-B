<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<img src="/images/button/Update.png" id="routingUpdateBtn"/>
							 	
			 	<img src="/images/button/Excel.png" onclick="excel_download(itemManageTable)"/>
			</div>
		</div>

		<div id="routingManageTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/standard/routingManage.js?v=<%=System.currentTimeMillis() %>"></script>