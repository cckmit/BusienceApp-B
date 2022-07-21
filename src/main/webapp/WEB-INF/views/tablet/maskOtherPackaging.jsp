<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/tablet/maskOtherPackaging.css?v=<%=System.currentTimeMillis()%>">
<div class="container-bs">	
	<header class="global-header">
		<div class="header-barcode tablet-border">
			<label for="barcodeInput">바 코 드</label>
			<input type="text" id="barcodeInput" pattern="[A-Za-z]+" autofocus>
		</div>	
		<div class="header-left">	
			<input id="selectedMachine" type="hidden"  value="${workOrderInfo.equip_WorkOrder_Code}" readonly>		
			<input id="machineCode" type="hidden" value="${workOrderInfo.equip_WorkOrder_SubCode}">
			<input id="itemCode" type="hidden" value="${workOrderInfo.equip_WorkOrder_ItemCode}">
			<span id="itemName" style="font-size: calc(0.9vh + 0.9vw);">${workOrderInfo.equip_WorkOrder_ItemName}</span>
		</div>
		<div class="title tablet-border">
			<span>작업 관리 (마스크 개별 포장)</span>
		</div>
		<div class="header-right">
			<span id="fullScreenBtn">전체화면</span>
		</div>
	</header>
	<div class="main-f">		
		<div class="main-box tablet-border">
			<div class="machine-module">
				<div class="m-header m-font">
					<p><strong>${inputInfo.equip_WorkOrder_Name}</strong></p>
					<p><strong>${inputInfo.equip_WorkOrder_ItemName}</strong></p>
				</div>
				<div class="m-main m-font">
					<input id="inputItemCode" type="hidden" value="${inputInfo.equip_WorkOrder_Old_ItemCode}">
					
					<p><strong>${inputInfo.equip_WorkOrder_ItemCode}</strong></p>
					<p><strong>${inputInfo.equip_WorkOrder_INFO_STND_1}</strong></p>
					<p><strong>${inputInfo.equip_WorkOrder_INFO_STND_2}</strong></p>
					<p><strong>${inputInfo.equip_WorkOrder_Material_Name}</strong></p>
					<p><strong>${inputInfo.equip_WorkOrder_Item_CLSFC_1_Name}</strong></p>
					<p><strong>${inputInfo.equip_WorkOrder_Item_CLSFC_2_Name}</strong></p>
				</div>
				<div class="table-container">
					<div id="inputTable" class="tablet-Table"></div>
				</div>
			</div>				
		</div>
	</div>
	<div class="main-a">
		<div class="main-box tablet-border">
			<div class="item">
				<ul id="bundle-list">
					<li>${workOrderInfo.equip_WorkOrder_Name}</li>
				</ul>
				<button type="button" id="packagingBtn" class="btn btn-default">포장 발행</button>
				<select id="selected_device" onchange=onDeviceSelected(this); style="display:none"></select>
			</div>
		</div>
	</div>
	<div class="main-b">
		<div class="main-box tablet-border">
			<div class="item">
				<label for="packaging-Count">소포장 수량</label>
				<input type="number" id="packaging-Count" disabled>
			</div>
			<div class="item">
				<label for="waiting-Qty">포장 대기 수량</label>
				<input type="number" id="waiting-Qty" readonly>
			</div>
		</div>
	</div>
	
	<div class="main-c">
		<div class="main-box tablet-border">
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
				<input id="packaging-No" style="width:10%;" value="${packagingInfo.packaging_No}" disabled>
				<label for="packaging-small">소포장 규격</label>
				<input type="number" id="packaging-small" value="${packagingInfo.packaging_Small}" disabled>
				<label for="packaging-large">대포장 규격</label>
				<input type="number" id="packaging-large" value="${packagingInfo.packaging_Large}" disabled>
			</div>
			<div class="item">
				<label for="packaging-Item">포장명</label>
				<input id="packaging-Item" style="width:80%;" value="${packagingInfo.packaging_Item}" disabled>
			</div>
		</div>
	</div>
</div>