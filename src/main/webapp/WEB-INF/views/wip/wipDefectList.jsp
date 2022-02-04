<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
   	<!-- matOrderSub -->
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" id="WD_SearchBtn"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<span><strong>불량등록일</strong></span>
				<input id="startDate" class="today" type="date">
				<span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date">
			</div>
		</div>
		<div id="wipManageTable" class="master-in"></div>
		<div id="wipDefectTable" class="sub-in"></div>

	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/wip/wipDefectList.js?v=<%=System.currentTimeMillis() %>"></script>