<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="MI_DeliveryLastListSearchBtn()" />
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>해당월</strong></span> 
				<c:forEach var="item" items="${LastMonth}">
				<input id="LastMonthst" type="text" value="${item.CHILD_TBL_RMARK}"> 
				</c:forEach>
				<c:forEach var="dayitem" items="${LastDay}">
				<input id="LastDay" type="hidden" value="${dayitem.CHILD_TBL_RMARK}" disabled>
				</c:forEach>
			</div>
		</div>
	</div>
	<!-- 그리드 생성 장소 -->
	<div id="matInputDeliveryLastListTable" class="itemMaster2"></div>
	<div id="matInputDeliveryLastItemTable" class="itemSub2"></div>
</div>
<!-- END MAIN -->
