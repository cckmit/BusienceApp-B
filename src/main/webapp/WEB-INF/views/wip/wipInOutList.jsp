<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<div class="soloView">
	   	<!-- matOrderSub -->
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
					<!-- 버튼 -->
				<div class="input-button">
					<img id="WIOL_SearchBtn" src="images/button/Search.png" />
					<img src="/images/button/Excel.png" onclick="excel_download(wipInOutListTable)"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>품목분류</strong></span>
					<select id="wip_Process_Type">
						<c:forEach var="data" items="${routingList}">
							<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
						</c:forEach>
					</select>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="wipInOutListTable"></div>
		</div>
		<!-- END MAIN -->
	</div>
<!-- Javascript -->
<script src="/js/wip/wipInOutList.js?v=<%=System.currentTimeMillis() %>"></script>
</body>
</html>