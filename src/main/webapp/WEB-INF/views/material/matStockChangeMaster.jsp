<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="soloView">
	<div class="main">
		<div class="master" style="width:calc(40%)">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>재고구분</strong></span>
					<select id="inMatTypeListSelectBox">
						<c:forEach var="data" items="${stockList}" begin="0" end="1">
							<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div id="stockListTable"></div>
		</div>
		<div class="sub" style="width:calc(60% - 10px)">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/ADD.png" id="stockChangeAddBtn" class="unUseBtn BtnStatus"/>
					<img src="/images/button/Save.png" id="stockChangeSaveBtn" class="unUseBtn BtnStatus"/>
					<select id="selected_device" class="none" onchange=onDeviceSelected(this);></select>
				</div>
			</div>
			<div id="stockChangeTable"></div>		
		</div>
	</div>
</div>

<!-- Javascript -->
<script src="/js/material/matStockChangeMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint/paldangLabelPrint.js?v=<%=System.currentTimeMillis() %>"></script>