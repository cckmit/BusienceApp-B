<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="/images/button/Search.png" id="PM_SearchBtn"/>
				<img src="/images/button/New.png" id="PM_NewBtn"/>
				<img src="/images/button/ADD.png" id="PM_AddBtn" class="unUseBtn BtnStatus"/>
				<img src="/images/button/Save.png" id="PM_SaveBtn"/>
				<img src="/images/button/Delete.png" id="PM_DeleteBtn"/>
			</div>
			
			
		</div>
			<!-- 그리드 생성 장소 -->
		<div id="packagingManageTable"></div>
	</div>
	<!-- END MAIN -->
</div>

<script src="/js/standard/packagingManage.js"></script>
