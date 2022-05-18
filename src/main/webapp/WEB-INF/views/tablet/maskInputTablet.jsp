<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/tablet/maskInputTablet.css?v=<%=System.currentTimeMillis() %>">
<div class="container-bs">
	<header class="global-header">
		<div class="header-left">
			<input id="machineCode" type="hidden" value="${machineInfo.EQUIPMENT_INFO_CODE}">
			<span id="machineName">${machineInfo.EQUIPMENT_INFO_NAME}</span>
			<span id="itemName">마스크A-53</span>
		</div>
		<div class="title tablet-border">
			<span>작업 관리 (마스크 투입)</span>
		</div>
		<div class="header-right">
			<span id="fullScreenBtn">전체화면</span>
		</div>
	</header>
	<div class="main-a">
		<div class="main-box tablet-border">
			<div class="item">
				<label for="barcodeInput">바코드 입력</label>
				<input id="barcodeInput" autofocus>
			</div>
		</div>
	</div>
	<div class="main-b">
		<div class="main-box tablet-border">
			<div class="item">
				<label for="crateCode">상 자 코 드</label>
				<input id="crateCode" disabled>
			</div>
			<div class="item">
				<label for="crate-LotNo">상자 LOTNO</label>
				<input id="crate-LotNo" disabled>
			</div>
			<div class="item">
				<label for="crate-Qty">상자 내 생산량</label>
				<input id="crate-Qty" readonly>
			</div>
			<div class="item">
				<label for="production-ID">자재 식별 코드</label>
				<input id="production-ID" disabled>
			</div>
		</div>
	</div>
	<div class="main-c">
		<div class="side-box">
			<div class="table-container">
				<div id="itemTable" class="tablet-Table"></div>
			</div>
		</div>
	</div>
</div>
<script src="/js/tablet/maskInputTablet.js?v=<%=System.currentTimeMillis() %>"></script>