<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/tablet/maskPackagingTablet.css?v=<%=System.currentTimeMillis() %>">
<div class="container-bs">
	<header class="global-header">
		<div class="header-left">
			<input id="machineCode" type="hidden" value="${machineInfo.EQUIPMENT_INFO_CODE}">
		</div>
		<div class="title tablet-border">
			<span>작업 관리 (마스크 포장)</span>
		</div>
		<div class="header-right">
			<span id="fullScreenBtn">전체화면</span>
		</div>
	</header>
	<div class="main-a">
		<div class="main-box tablet-border">
			<div class="item">
				<button type="button" id="packagingBtn" class="btn btn-default">포장 발행</button>
			</div>
		</div>
	</div>
	<div class="main-b">
		<div class="main-box tablet-border">
			<div class="item">
				<label for="crate-LotNo">당일 발행 수량</label>
				<input id="crate-LotNo" disabled>
				<input type="hidden" id="crateCode">
			</div>
			<div class="item">
				<label for="crate-Qty">당일 포장 수량</label>
				<input id="crate-Qty" readonly>
			</div>
		</div>
	</div>
	
	<div class="main-c">
		<div class="main-box tablet-border">
			<div class="item">
				<input id="selectedItem" placeholder="목록을 선택하세요." disabled>
				<button type="button" id="rePrintBtn" class="btn btn-primary">재발행</button>
			</div>
		</div>
	</div>
	<div class="main-d">
		<div id="packagingTable" class="tablet-border tablet-Table"></div>
	</div>
</div>
<script src="/js/tablet/maskPackagingTablet.js?v=<%=System.currentTimeMillis() %>"></script>