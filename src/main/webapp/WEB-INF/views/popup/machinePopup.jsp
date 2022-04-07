<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/css/popup/popup.css" rel="stylesheet" />
<div class="main">
	<div class="top-var" style="height: 59px">
		<div class="popup-box">
			<span>검색</span>
			<input type="text" id="Machine_Word"/>
			<select id="machine_Type">
				<option value="">설비종류</option>
				<c:forEach var="data" items="${machineTypeList}">
					<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
				</c:forEach>
			</select>
		</div>
		<div class="popup-button">
			<button id="searchBtn">검색</button>
		</div>
	</div>
	<div class="popup-table">
		<div id="machinePopupTable" tabindex=-1></div>
	</div>
</div>

<!-- js -->
<script src="/js/popup/popup.js"></script>
<script src="/js/popup/machinePopup.js"></script>
