<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div style="width: calc(50% - 5px); height: 100%; float:left; margin-right: 10px;">
			<div class="top-var">
			</div>
			<div id="typeAuthorityMasterTable" ></div>
		</div>
		<div style="width: calc(50% - 5px); height: 100%; float:left;">
			<div class="top-var">
				<div class="input-button">
					<img id="TA_UpdateBtn" class="unUseBtn" src="/images/button/Update.png"/>
				</div>
			</div>
			<div id="typeAuthoritySubTable"></div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
		
<script src="/js/system/typeAuthority.js?v=<%=System.currentTimeMillis() %>"></script>