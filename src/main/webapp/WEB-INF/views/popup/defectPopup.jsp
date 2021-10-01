<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/css/popup/popup.css" rel="stylesheet" />
	<div class="main">
		<div class="top-var">
			<div class="popup-box">
				<span>검색</span>
				<input type="text" id="Defect_Word"/>
			</div>
			<div class="popup-button">
				<button id="searchBtn">검색</button>
			</div>
		</div>
		<div class="popup-table">
			<div id="defectPopupTable" tabindex=-1></div>
		</div>
	</div>
<!-- js -->
<script src="/js/popup/popup.js"></script>
<script src="/js/popup/defectPopup.js"></script>