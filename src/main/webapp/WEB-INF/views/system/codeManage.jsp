<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Insert Modal -->
<jsp:include page="../modal/system/codeManageModal.jsp"></jsp:include>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div style="width: calc(50% - 5px); height: 100%; float: left; margin-right: 10px;">
			<div class="top-var">
			</div>
			<div id="codeManageMasterTable"></div>
		</div>
		<div style="width: calc(50% - 5px); height: 100%; float: left;">
			<div class="top-var">
				<div class="input-button">			
					<img src="/images/button/ADD.png" id="CM_ADDbtn" class="unUseBtn"/>					
				</div>
			</div>
			<div id="codeManageSubTable"></div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/system/codeManage.js?v=<%=System.currentTimeMillis() %>"></script>