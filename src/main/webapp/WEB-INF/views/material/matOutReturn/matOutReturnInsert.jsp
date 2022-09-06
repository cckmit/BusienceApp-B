<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Save.png" id="outReturnSaveBtn"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<span><strong>검색창고</strong></span>
			<select id="warehouseSelectBox">
				<c:forEach var="data" items="${stockList}" begin="1" end="2">
					<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div id="matOutReturnInsertTable"></div>
</div>
<!-- END MAIN -->