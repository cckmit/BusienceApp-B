<%@page import="com.busience.standard.Dto.DTL_TBL"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<!-- MAIN -->
		<div class="soloView">
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
					</div>
				</div>
				<div style="float:none;" id="OQCInspect_tbl"></div>	
			</div>
		</div>
		<!-- END MAIN -->
	<script src="/js/qc/oqcOutputList.js"></script>
	<script src="/js/tabMenu.js"></script>
	<script src="/js/today.js"></script>
	 
</body>
</html>