<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="SearchBtn" src="images/button/Search.png"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>일자별</strong></span>
					<input id="startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">		
					<span><strong>제품코드</strong></span>
					<input id="PRODUCT_ITEM_CODE" class="Item_Code" type="text" disabled>
					<span><strong>제품명</strong></span>
					<input id="PRODUCT_ITEM_NAME" class="Item_Name clearInput" type="text" autofocus>
				</div>
			</div>
			<div id="proItemTable"></div>
		</div>
		<!-- END MAIN -->
	</div>
<!-- Javascript -->
<script src="/js/productionLX/proItemSumlXX.js"></script>
</body>
</html>