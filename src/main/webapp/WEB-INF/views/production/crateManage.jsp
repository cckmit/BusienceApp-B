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
					<span style="color: red; margin-left: 30px"><strong>상태 - 0: 미사용, 1: 마스크 투입, 2: 마스크 생산완료, 3: 포장 투입</strong></span>
				</div>
			</div>

			<div id="crateManageTable"></div>
		</div>
		<!-- END MAIN -->
	</div>

<script src="/js/production/crateManage.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint/paldangLabelPrint.js?v=<%=System.currentTimeMillis() %>"></script>