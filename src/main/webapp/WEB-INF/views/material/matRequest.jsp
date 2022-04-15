<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matRequest -->
		<div class="master">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="MRM_SearchBtn"/>
					<img src="/images/button/ADD.png" id="MRM_AddBtn"/>
					<img src="/images/button/print.png" id="MR_PrintBtn"/>
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
						<input id="Dept_Code" type="text" value="${userInfo.dept_Code}" disabled>
						<span><strong>부서명</strong></span>
						<input id="Dept_Name" type="text" value="${userInfo.dept_Name}" disabled>
						<input id="User_Code" type="hidden" value="${userInfo.user_Code}">
						<input id="User_Name" type="hidden" value="${userInfo.user_Name}">
					</div>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="matRequestTable"></div>
		</div>
		<!-- matRequest -->
		<!-- matRequestSub -->
		<div class="sub">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/ADD.png" id="MRL_AddBtn" class="unUseBtn BtnStatus"/>
					<img src="/images/button/Delete.png" id="MR_DeleteBtn" class="unUseBtn BtnStatus"/>
					<img src="/images/button/Save.png" id="MR_SaveBtn" class="unUseBtn BtnStatus"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>요청번호</strong></span>
					<input id="RS_RequestNo" type="text" disabled>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="matRequestSubTable"></div>
			<div id="matRequestStockTable"></div>
		</div>
	   	<!-- matRequestSub -->
	</div>
	<!-- END MAIN -->
</div>
<!-- END WRAPPER -->
<!-- Javascript -->
<script src="/js/material/matRequest.js?v=<%=System.currentTimeMillis() %>"></script>