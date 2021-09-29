<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="FI_NewBtn" class=""src="images/button/New.png"/>				
					<img id="FI_SearchBtn" src="images/button/Search.png"/>
					<img id="FI_SaveBtn" src="images/button/Save.png"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>등록일</strong></span> 
						<input id="startDate" class="today" type="date"> 
						<span style="text-align: center"><strong>~</strong></span> 
						<input id="endDate" class="tomorrow" type="date">
					</div>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="WorkOrder_tbl"></div>
			<div id="Sales_OrderMaster_tbl"></div>
		</div>
		<!-- END MAIN -->
	</div>
<!-- END WRAPPER -->
<!-- Javascript -->
<script src="/js/today.js"></script>
<script src="/js/productionLX/workOrder.js"></script>
<script src="/js/tabMenu.js"></script>

</body>
</html>