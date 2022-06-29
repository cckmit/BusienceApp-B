<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/css/popup/popup.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet" />
<div class="main">
	<div class="top-var" style="height: 59px">
		<div class="popup-box">
			<span>검색</span>
			<input type="text" id="Item_Word"/>
			<input type="hidden" id="Item_STND"/>
			<select id="item_Type">
				<option value="">품목분류</option>
				<c:forEach var="data" items="${itemTypeList}">
					<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
				</c:forEach>
			</select>
		</div>
		<div class="popup-button">
			<button id="searchBtn">검색</button>
		</div>
	</div>
	<div class="popup-table">
		<div id="itemPopupTable" tabindex=-1></div>
	</div>
</div>

<!-- js -->
<script src="/js/popup/popup.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/popup/itemPopup.js?v=<%=System.currentTimeMillis() %>"></script>
