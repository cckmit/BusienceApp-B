<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="MI_DeliveryListSearchBtn()" />
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>해당월</strong></span> 
				<input id="PrcsDatest" type="text" value="${PrcsDate}" readonly> 
				<input id="LastDay" type="hidden" value="${LastDay}">
			</div>
		</div>
	</div>
	<!-- 그리드 생성 장소 -->
	<div id="matInputDeliveryListTable" class="itemMaster"></div>
	<div id="matInputDeliveryItemTable" class="itemSub"></div>
</div>
<!-- END MAIN -->
