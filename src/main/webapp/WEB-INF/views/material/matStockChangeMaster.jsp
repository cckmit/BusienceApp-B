<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="soloView">
	<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/ADD.png" id="stockChangeAddBtn"/>
			<img src="/images/button/Save.png" id="stockChangeSaveBtn"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<span><strong>재고구분</strong></span>
				<select id="inMatTypeListSelectBox" onchange="changeFunc()">
					<c:forEach var="data" items="${stockList}" begin="0" end="1">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select>
		</div>
	</div>
	<div id="matStockChangeTable"></div>
</div>
</div>

<!-- Javascript -->
<script src="/js/material/matStockChangeMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint/paldangLabelPrint.js?v=<%=System.currentTimeMillis() %>"></script>