<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div class="soloView">
	   	<!-- matOrderSub -->
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img id="SI_NewBtn" class=""src="images/button/New.png"/>
					<img id="SI_SaveBtn" class="unusebtn" src="images/button/Save.png"/>
					<img id="SI_DeleteBtn" class="unusebtn" src="images/button/Delete.png"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<%-- <span><strong>제품코드</strong></span>
						<input id="fgoodsCode" class="Item_Code" type="text" disabled>
						<span><strong>제품명</strong></span>
						<input id="fgoodsName" class="Item_Name clearInput trans_input" type="text" disabled>
						<span><strong>수량</strong></span>
						<input id="fgoodsQty" class="trans_input" type="text" disabled>
						<span><strong>구분</strong></span>
						<select id="fgoodsRcv_Clsfc">
							<c:forEach var="data" items="${deptList}">
								<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
							</c:forEach>
						</select> --%>
						
						<!-- 묶음 갯수가 필요한경우 작동하는 쿼리 예시 
						<span><strong>묶음</strong></span>
						<input id="fgoodsbundle" type="text"> -->
						
						<!-- 랏번호 입력시 (바코드 리드) 작동하는 쿼리예시 --> 
						<span><strong>LotNo</strong></span>
						<input id="sgoodsLotNo" type="text">
					</div>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="salesInputTable"></div>
		</div>
		<!-- END MAIN -->
	</div>
<!-- Javascript -->
<script src="/js/sales/salesInput_another.js"></script>
</body>
</html>