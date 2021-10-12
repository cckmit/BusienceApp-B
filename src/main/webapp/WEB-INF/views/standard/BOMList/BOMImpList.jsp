<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" onclick="BIL_Search2()"/> 
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>품목종류</strong></span>
				<select class="Item_Type2" onchange="BIL_Search2()">
					<option value="all">all</option>
					<c:forEach var="data" items="${mtrlClsfcList}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
			</div>
			<div>
				<span><strong>품목명</strong></span>
				<input class="Item_Code2" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','',$('.Item_Type').val())}">
			</div>
		</div>
	</div>
	<div id="BOMitemListTable2" class="itemMaster"></div>
	<div id="BOMImpListTable" class="itemSub"></div>
</div>
<!-- END MAIN -->