<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" id="SOCV_SearchBtn"/>
			<img src="/images/button/Excel.png" onclick="excel_download(salesOutputCustomerViewTable)"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>출고일</strong></span> 
				<input id="salesOutputCustomerView_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="salesOutputCustomerView_endDate" class="tomorrow" type="date">
				<span><strong>출고구분</strong></span>
				<select id="outMatTypeCustomerViewSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${OutputType}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
			</div>
			<div>
				<span><strong>거래처코드</strong></span> <input
							id="Sales_InMat_Client_Code" class="Client_Code" type="text"
							disabled> <span><strong>거래처명</strong></span> <input
							id="Sales_InMat_Client_Name" class="Client_Name clearInput"
							type="text" autofocus
							onkeypress="javascript:if(event.keyCode==13) {customerPopup($(this).val(),'input','','out')}">
			</div>
		</div>
	</div>
	<div id="salesOutputCustomerViewTable"></div>
</div>
<!-- END MAIN -->