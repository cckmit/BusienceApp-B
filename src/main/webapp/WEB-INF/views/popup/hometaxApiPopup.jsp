<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/css/popup/popup.css" rel="stylesheet" />
	<div class="main">
		<div class="top-var">
			<div class="popup-box">
				<span>${year}년 ${month}월</span>
			</div>
			<div class="popup-button">
				<button id="download-xlsx" class="btn btn-primary">엑셀변환</button>
			</div>
		</div>
		<div class="popup-table">
			<div id="excelViewerTable" tabindex=-1></div>
		</div>
	</div>
<!-- js -->
<script src="/js/popup/hometaxApiPopup.js"></script>