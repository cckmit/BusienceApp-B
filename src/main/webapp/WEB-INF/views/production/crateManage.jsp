<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<div class="input-button">
				
					<img src="/images/button/ADD.png" id="crateADDBtn"/>
					
					<img src="/images/button/Save.png" id="crateSaveBtn" class="unUseBtn BtnStatus"/>
					
					<img src="/images/button/Print.png" id="cratePrintBtn"/>
					<select id="selected_device" onchange=onDeviceSelected(this); style="display:none"></select>
				</div>
			</div>

			<div id="crateManageTable"></div>
		</div>
		<!-- END MAIN -->
	</div>

<script src="/js/production/crateManage.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint.js?v=<%=System.currentTimeMillis() %>"></script>