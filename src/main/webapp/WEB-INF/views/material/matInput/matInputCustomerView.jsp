<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" id="MICL_SearchBtn"/>
			<img src="/images/button/Excel.png" onclick="excel_download(matInputCustomerViewTable)"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>입고일</strong></span> 
				<input id="matInputCustomerView_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="matInputCustomerView_endDate" class="tomorrow" type="date">
				<span><strong>입고구분</strong></span>
				<select id="inMatTypeCustomerViewSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${InMatType}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
			</div>
			<div>
				<span><strong>거래처코드</strong></span> 
				<input id="InMat_Client_Code2" class="Client_Code2 clearInput" type="text" disabled> 
				<span><strong>거래처명</strong></span> 
				<input id="InMat_Client_Name2" class="Client_Name2 clearInput" type="text"
				onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','2','in')}">
			</div>
		</div>
	</div>
	<div id="matInputCustomerViewTable"></div>
</div>
<!-- END MAIN -->