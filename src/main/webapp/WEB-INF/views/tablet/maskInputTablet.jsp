<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/tablet/maskInputTablet.css?v=<%=System.currentTimeMillis() %>">
<div class="container-bs">
	<header class="global-header">
		<div class="header-barcode tablet-border">
			<label for="barcodeInput">바 코 드</label>
			<input type="text" id="barcodeInput" autofocus>
		</div>
		<input type="hidden" id="selectedMachine" readonly>
		<div class="title tablet-border">
			<span>작업 관리 (마스크 투입)</span>
		</div>
		<div class="header-space">
		</div>
		<div class="title tablet-border">
			<span>작업 관리 (마스크 투입)</span>
		</div>
		<div class="header-barcode tablet-border">
			<label for="barcodeInput">바 코 드</label>
			<input type="text" id="barcodeInput-mirror" autofocus>
		</div>
	</header>
	<div class="main">
		<div id="multiTableAdd" class="main-box tablet-border">			
		</div>
	</div>
</div>
<script src="/js/tablet/maskInputTablet.js?v=<%=System.currentTimeMillis() %>"></script>