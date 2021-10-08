<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<div class="soloView">
			<!-- MAIN -->
			<div class="main">
				<div class="top-var">
					<!-- 버튼 -->
					<div class="input-button">
						<img src="/images/button/Search.png" onclick="BIL_Search()"/> 
					</div>
					<!-- 버튼 -->
					<div class="input-box">
						<div>
							<span><strong>품목종류</strong></span>
							<select class="Item_Type" onchange="BIL_Search()">
								<option value="all">all</option>
								<c:forEach var="data" items="${mtrlClsfcList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select> 
						</div>
						<div>
							<span><strong>품목명</strong></span>
							<input class="Item_Code" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','',$('.Item_Type').val())}">
						</div>
					</div>
					<div class="input-button" style="margin-left: 7%">
						<img id="BBL_AddBtn" src="/images/button/ADD.png"/>
						<img id="BBL_SaveBtn" src="/images/button/Save.png"/>
						<img id="BBL_DeleteBtn" src="/images/button/Delete.png"/>
					</div>
					<span style="color: red; margin-left: 30px"><strong>level 1 데이터만 저장, 삭제 할 수 있습니다.</strong></span>
				</div>
				<div id="BOMitemListTable" class="itemMaster"></div>
				<div id="BOMBOMListTable" class="itemSub"></div>
			</div>
			<!-- END MAIN -->
		</div>
<script src="js/standard/BOM.js"></script>
