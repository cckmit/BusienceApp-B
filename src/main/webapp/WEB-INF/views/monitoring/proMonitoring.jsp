<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="main monitor_main">
	<div class="monitor_topvar">
		<p style="font-size: 50px">생산 현황 <font style="color: rgb(88,221,178);">모니터링</font></p>
		<p id="curentTime" style="font-size: 40px">0000-00-00 00:00:00</p>
	</div>
	<div id="monitor_content" class="row">
	</div>
</div>

<script src="js/monitoring/proMonitoring.js?v=<%=System.currentTimeMillis() %>"></script>