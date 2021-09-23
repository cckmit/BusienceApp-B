<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" onclick="SOC_DeliveryLastListSearchBtn()" />
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>해당월</strong></span> 
				<input id="LastMonthst" type="text" value="${LastMonth}"> 
				<input id="LastDay" type="hidden" value="${LastDay}" disabled>
			</div>
		</div>
	</div>
	<div id="salesDeliveryLastListTable" class="itemMaster"></div>
	<div id="salesDeliveryLastCustomerTable" class="itemSub"></div>
</div>
<!-- END MAIN -->
