<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="WLM_SearchBtn" src="images/button/Search.png" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>일자별</strong></span>
						<input id="startDate" class="today" type="date">
						<span style="text-align: center"><strong>~</strong></span>
						<input id="endDate" class="tomorrow" type="date">
					</div>
				</div>
			</div>
			<div id="wipLotManageTable"></div>
		</div>
		<div class="sub-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button" style="margin-left: 20px">
					<img id="WLMS_LabelPrintBtn" src="images/button/Print.png"/>
				</div>
				<div class="input-box">
					<span><strong>프린터</strong></span>
					<select id="selected_device" onchange=onDeviceSelected(this);></select>
				</div>
			</div>
			<div id="wipLotManageSubTable"></div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/wip/wipLotManage.js?v=<%=System.currentTimeMillis() %>"></script>

<script src="/js/labelPrint.js?v=<%=System.currentTimeMillis() %>"></script>