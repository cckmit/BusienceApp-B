<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/tablet/maskProductionTablet.css?v=<%=System.currentTimeMillis() %>">
<div class="container-bs">
	<header class="global-header">
		<div class="header-left">
			<input id="machineCode" type="hidden" value="${workOrderInfo.equip_WorkOrder_Code}">
			<span id="machineName" style="font-size: calc(0.9vh + 0.9vw);">${workOrderInfo.equip_WorkOrder_Name}</span>
			<input id="itemCode" type="hidden" value="${workOrderInfo.equip_WorkOrder_ItemCode}">
			<span id="itemName">${workOrderInfo.equip_WorkOrder_ItemName}</span>
		</div>
		<div class="title tablet-border">
			<span>작업 관리 (마스크 생산)</span>
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
				<input id="crateCode" readonly>
			</div>
			<div class="item">
				<label for="crate-LotNo">생산 LOTNO</label>
				<input id="crate-LotNo" disabled>
			</div>
			<div class="item">
				<label for="crate-Qty">생 산 량</label>
				<input type="number" id="crate-Qty" readonly>
			</div>
			<div class="item">
				<button id="completeBtn">
					작업완료
				</button>
			</div>
		</div>
	</div>
	<div class="main-c">
		<div class="main-box tablet-border">
			<div class="item">
				<span class="LotNo_Name">원자재 명</span>
				<span class="LotNo">원자재 LOTNO</span>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<span class="glyphicon glyphicon-remove removeBtn hiddenBtn"></span>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<span class="glyphicon glyphicon-remove removeBtn hiddenBtn"></span>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<span class="glyphicon glyphicon-remove removeBtn hiddenBtn"></span>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<span class="glyphicon glyphicon-remove removeBtn hiddenBtn"></span>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<span class="glyphicon glyphicon-remove removeBtn hiddenBtn"></span>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<span class="glyphicon glyphicon-remove removeBtn hiddenBtn"></span>
			</div>
		</div>
	</div>
	<div class="main-d">
		<div id="itemTable" class="tablet-border tablet-Table"></div>
	</div>
</div>
<script src="/js/tablet/maskProductionTablet.js?v=<%=System.currentTimeMillis() %>"></script>