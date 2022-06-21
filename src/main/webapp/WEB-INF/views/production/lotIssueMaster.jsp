<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img id="lotIssue_SearchBtn" src="images/button/Search.png"/>
				<img src="/images/button/Excel.png" onclick="excel_download(lotIssueMasterTable)"/>
			</div>
			<!-- 버튼 -->
			<div class="input-box">
				<div>
					<span><strong>일자별</strong></span>
					<input id="startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
					<span><strong>LotNo</strong></span>
					<input id="lotIssueLotNo" class="lotIssueLotNo" type="text" style="width: 230px;" onclick="$(this).val('')" onkeypress="javascript:if(event.keyCode==13) {InspectionWorkONo()}" autofocus>
				</div>
				<div>
					<span><strong>제품코드</strong></span>
					<input id="PRODUCT_ITEM_CODE" class="Item_Code" type="text" disabled>
					<span><strong>제품명</strong></span>
					<input id="PRODUCT_ITEM_NAME" class="Item_Name clearInput" type="text" autofocus>
					<span><strong>구분</strong></span>
					<select id="lotTypeListBox">
						<option value="mask">마스크</option>
						<option value="largeBox">대박스</option>	
						<option value="smallBox">소박스</option>
					</select>
				</div>
			</div>
		</div>
		<div id="lotIssueMasterTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/production/lotIssueMaster.js"></script>