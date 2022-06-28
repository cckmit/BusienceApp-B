<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/tablet/maskPackagingTablet.css?v=<%=System.currentTimeMillis() %>">
<div class="container-bs">
	<header class="global-header">
		<div class="header-left">
			<input id="machineCode" type="hidden" value="${workOrderInfo.equip_WorkOrder_Code}">
			<span id="machineName">${workOrderInfo.equip_WorkOrder_Name}</span>
			<input id="itemCode" type="hidden" value="${workOrderInfo.equip_WorkOrder_ItemCode}">
			<span id="itemName">${workOrderInfo.equip_WorkOrder_ItemName}</span>
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
				<ul id="bundle-list">
				</ul>
				<button type="button" id="packagingBtn" class="btn btn-default">포장 발행</button>
				<select id="selected_device" onchange=onDeviceSelected(this); style="display:none"></select>
			</div>
		</div>
	</div>
	<div class="main-b">
		<div class="main-box tablet-border">
			<div class="item">
				<label for="crate-Count">소포장 카운트</label>
				<input id="crate-Count" disabled>
			</div>
			<div class="item">
				<label for="smallPackaging-Qty">당일 소포장 수량</label>
				<input id="smallPackaging-Qty" readonly>
				<input type="hidden" id="crateCode">
			</div>
			<div class="item">
				<label for="largePackaging-Qty">당일 대포장 수량</label>
				<input id="largePackaging-Qty" disabled>
			</div>
		</div>
	</div>
	
	<div class="main-c">
		<div class="main-box tablet-border">
			<div class="item">
				<button type="button" id="largePackagingBtn" class="btn btn-success">대포장 출력</button>
			</div>
			<div class="item">
				<input id="selectedItem" placeholder="목록을 선택하세요." style="width: 60%" disabled>
				<button type="button" id="rePrintBtn" class="btn btn-primary" style="width: 30%">소포장 재 발행</button>
			</div>
		</div>
	</div>
	<div class="main-d">
		<div id="packagingTable" class="tablet-border tablet-Table"></div>
	</div>
	<div class="main-e">
		<div class="main-box tablet-border">
			<div class="item">
				<label for="packaging-No" >No</label>
				<input id="packaging-No" disabled>
				<label for="packaging-Item">포장명</label>
				<input id="packaging-Item" style="width:auto;" disabled>
			</div>
			<div class="item">
				<label for="packaging-small">소포장 규격</label>
				<input id="packaging-small" disabled>
				<label for="packaging-large">대포장 규격</label>
				<input id="packaging-large" disabled>
			</div>
		</div>
	</div>
</div>
<script src="/js/tablet/maskPackagingTablet.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint/paldangLabelPrint.js?v=<%=System.currentTimeMillis() %>"></script>