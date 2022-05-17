<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="/images/button/Print.png" id="SLP_PrintBtn"/>
				<span><strong>프린터 :</strong></span>
				<select id="selected_device" onchange=onDeviceSelected(this);></select>
			</div>
			<!-- 버튼 -->
		</div>
		<!-- 그리드 생성 장소 -->
		<div id="salesInputMasterTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/sales/salesLabelPrint.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint.js?v=<%=System.currentTimeMillis() %>"></script>
