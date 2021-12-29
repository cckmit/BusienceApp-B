<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="MO_DeptViewSearchBtn()"/>
			<img src="/images/button/Excel.png" onclick="excel_download(matOutputDeptViewTable)"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>출고일</strong></span> 
				<input id="matOutputDeptView_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="matOutputDeptView_endDate" class="tomorrow" type="date">
				<span><strong>출고구분</strong></span>
				<select id="outMatTypeDeptViewSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${OutMatType}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
			</div>
			<div>
				<span><strong>부서명</strong></span>
				<select id="outMatDeptViewSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${OutMatDept}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
			</div>
		</div>
	</div>
	<div id="matOutputDeptViewTable"></div>
</div>
<!-- END MAIN -->