<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="master">
			<!-- matRequest -->
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" onclick="MRL_searchBtn()" /> 
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>요청일</strong></span> 
						<input id="startDate" class="today" type="date"> 
						<span style="text-align: center"><strong>~</strong></span> 
						<input id="endDate" class="tomorrow" type="date">
					</div>
					<div>
						<span><strong>부서코드</strong></span> 
						<input id="Dept_Code" type="text" value="${DEPT_CODE}" disabled> 
						<span><strong>부서명</strong></span> 
						<input id="Dept_Name" type="text"  value="${DEPT_NAME}" disabled>
						<input id="User_Code" type="hidden" value="${User_ID}">
						<input id="User_Name" type="hidden" value="${User_Name}">
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="matRequestListTable"></div>
			<!-- matRequest -->
		</div>
		<!-- matRequestSub -->
		<div class="sub">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="#" style="height: 73.13px; visibility: hidden;"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>요청번호</strong></span>
					<input id="request_lReqNo" type="text" disabled>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="matRequestListSubTable"></div>
			<div id="matRequestListStockTable"></div>
		</div>
		<!-- matRequestSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- END WRAPPER -->
<!-- Javascript -->
<script src="/js/materialLX/matRequestList.js"></script>