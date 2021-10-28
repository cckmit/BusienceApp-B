<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Insert Modal -->
<jsp:include page="../modal/system/codeManageModal.jsp"></jsp:include>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">			
				<img src="/images/button/ADD.png" id="CM_ADDbtn" class="unUseBtn"/>					
			</div>
		</div>
		<div id="codeManageMasterTable" style="width: calc(50% - 5px); float: left; margin-right: 10px;"></div>
		<div id="codeManageSubTable" style="width: calc(50% - 5px); float: left;"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/system/codeManage.js"></script>