<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" id="DI_SearchBtn"/>
				<img src="images/button/Save.png" id="DI_SaveBtn"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div>
					<span><strong>작업완료일</strong></span>
					<input id="startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
					<span><strong>LotNo</strong></span>
					<input id="defectLotNo" class="itemPackinLotNo" type="text" style="width: 230px;" onclick="$(this).val('')"autofocus>
				</div>
				<div>
					<span><strong>제품코드</strong></span>
					<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
					<span><strong>제품명</strong></span>
					<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','halfItem')}">
					<span><strong>구분</strong></span>
					<select id="defectStatusList">
						<c:forEach var="data" items="${defectList}" begin="1" end="2">
							<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
						</c:forEach>
					</select>
				</div>
				
			</div>
		</div>
		<div id="crateLotTable" style="float:left; width: calc(70% - 10px)"></div>
		<div id="defectTable" style="float:left; width: 30%; margin-left: 10px;"></div>

		<script src="/js/qc/defectInsert.js?v=<%=System.currentTimeMillis() %>"></script>
	</div>
</div>