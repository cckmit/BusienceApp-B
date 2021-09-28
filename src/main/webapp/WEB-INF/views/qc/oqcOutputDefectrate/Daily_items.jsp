<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>비지언스 MES</title>

</head>
<body>
<!-- WRAPPER -->
<div id="wrapper">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" onclick="MI_searchBtn1()"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div>
					<input id="currentDate" type="hidden" disabled="disabled" value="${SM_Prcs_Date}">
					<span><strong>출하검사일</strong></span> 
					<input id="startDate" class="today" type="date"> 
					<span style="text-align: center"><strong>~</strong></span> 
					<input id="endDate" class="tomorrow" type="date">
					<span><strong>처리구분</strong></span>
					<select id="OQCInspect_Prcsn_Clsfc1">
						<option value="All">All</option>
						<c:forEach var="data" items="${rogicList}">
							<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
						</c:forEach>
					</select>
				</div>
				<div>
					<span><strong>품목코드</strong></span>
					<input id="PRODUCT_ITEM_CODE1" class="Item_Code1"type="text" disabled="disabled">
					<span><strong>품목명</strong></span>
					<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">	
				</div>
			</div>
		</div>
		<div id="OQCInspect_tbl"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- END WRAPPER -->
</body>
</html>
