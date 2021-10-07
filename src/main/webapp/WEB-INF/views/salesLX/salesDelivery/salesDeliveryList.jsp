<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" onclick="SOC_DeliveryListSearchBtn()" />
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>해당월</strong></span> 
				<c:forEach var="item" items="${PrcsDate}">
				<input id="PrcsDatest" type="text" value="${item.CHILD_TBL_RMARK}" readonly> 
				</c:forEach>
				<c:forEach var="dayitem" items="${LastDay}">
				<input id="LastDay" type="hidden" value="${dayitem.CHILD_TBL_RMARK}" disabled>
				</c:forEach>
			</div>
		</div>
	</div>
	<div id="salesDeliveryListTable" class="itemMaster"></div>
	<div id="salesDeliveryCustomerTable" class="itemSub"></div>
</div>
<!-- END MAIN -->
