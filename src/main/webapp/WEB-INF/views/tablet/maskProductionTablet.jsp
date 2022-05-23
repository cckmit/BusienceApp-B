<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/tablet/maskProductionTablet.css?v=<%=System.currentTimeMillis() %>">
<div class="container-bs">
	<header class="global-header">
		<div class="header-left">
			<input id="machineCode" type="hidden" value="${machineInfo.EQUIPMENT_INFO_CODE}">
			<span id="machineName">${machineInfo.EQUIPMENT_INFO_NAME}</span>
			<span id="itemName">마스크A-53</span>
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
				<label for="crate-LotNo">상자 LOTNO</label>
				<input id="crate-LotNo" disabled>
				<input type="hidden" id="crateCode">
			</div>
			<div class="item">
				<label for="crate-Qty">상자 내 생산량</label>
				<input id="crate-Qty" readonly>
			</div>
			<div class="item">
				<label for="production-ID">자재 식별 코드</label>
				<input id="production-ID" disabled>
			</div>
			<div class="item">
				<label for="production-Qty">생 산 량</label>
				<input id="production-Qty" readonly>
			</div>
		</div>
	</div>
	<div class="main-c">
		<div class="main-box tablet-border">
			<div class="item">
				<span class="LotNo_Name">원자재 명</span>
				<span class="LotNo">원자재 LOTNO</span>
				<span class="LotNo_Qty">수량</span>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<input class="LotNo_Qty" disabled>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<input class="LotNo_Qty" disabled>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<input class="LotNo_Qty" disabled>
			</div>
			<div class="item">
				<input type="hidden" class="LotNo_Code">
				<input class="LotNo_Name" disabled>
				<input class="LotNo" readonly>
				<input class="LotNo_Qty" disabled>
			</div>
		</div>
	</div>
	<div class="main-d">
		<div id="itemTable" class="tablet-border tablet-Table"></div>
	</div>
	<footer class="global-footer">
		<div class="footer-box tablet-border">
			<div class="table-container">
				<p id="nextItemName">다음 제품 :</p>
				<button id="nextWorkBtn">
					<i class="fas fa-angle-double-right"></i>
				</button>
			</div>
			<div class="table-container">
				<div id="crateTable" class="tablet-Table"></div>
			</div>
			<div class="table-container">
				<div id="rawMaterialTable" class="tablet-Table"></div>
			</div>
		</div>
	</footer>
</div>
<script src="/js/tablet/maskProductionTablet.js?v=<%=System.currentTimeMillis() %>"></script>