<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img id="SearchBtn" src="images/button/Search.png" onclick="SPS_Search()"/>
				<img src="/images/button/Excel.png" onclick="excel_download(proResultTable)"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div>
					<span><strong>일자별</strong></span>
					<input id="packing_startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="packing_endDate" class="tomorrow" type="date">
					<span><strong>구분</strong></span>
				<select id="packingTypeSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${InputType}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
				</div>
				<div>
					<span><strong>제품코드</strong></span>
					<input id="itemCode" class="Item_Code" type="text" disabled>
					<span><strong>제품명</strong></span>
					<input id="itemName" class="Item_Name clearInput" type="text" autofocus>
				</div>
			</div>
		</div>
		<!-- 그리드 생성 장소 -->
		<div id="salesPackingTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/sales/salesPackingList.js?v=<%=System.currentTimeMillis() %>"></script>
</body>
</html>