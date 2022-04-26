<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
	<div class="allView">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/New.png" onclick="FI_New()"/>
				<img src="images/button/Save.png" onclick="FI_Save()"/>
				<img src="images/button/Delete.png" onclick="FI_Delete()">
			</div>
			
			<!-- 버튼 -->				
			<div class="input-box">
				<div>
					<span><strong>LotNo</strong></span>
					<input id="fgoodsLotNo" class="fgoodsLotNo" type="text" onclick="$(this).val('')" onkeypress="javascript:if(event.keyCode==13) {InspectionWorkONo()}" autofocus>
					<span style="width:100px;"><strong>총 수량</strong></span>
					<input id="fgoodsTotal" class="fgoodsTotal" style="width: 80px;" type="text" readonly>
				</div>
			</div>
		</div>
		<div id="salesInputTable" style="float:left; width: calc(100%)"></div>
		<div id="salesInputInfoTable" style="float:left; width: calc(50% - 10px)"></div>
		<div id="salesInputInfoSubTable" style="float:left; width: 50%; margin-left: 10px;"></div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/sales/salesInput.js?v=<%=System.currentTimeMillis()%>"></script>
<%-- <script src="/js/labelPrint.js?v=<%=System.currentTimeMillis() %>"></script> --%>