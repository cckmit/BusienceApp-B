<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
	<div class="allView">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="images/button/Search.png" id="SI_SearchBtn"  />
				<img src="images/button/Print.png" id="SI_PrintBtn"/>
					<%-- <img id="SOO_Print" src="${contextPath}/resources/assets/img/Print.png"/> --%>
			</div>
			<!-- 버튼 -->				
			<div class="input-box">
				<div>
					<span><strong>입고일</strong></span> 
					<input id="startDate" class="today" type="date"> 
					<span style="text-align: center"><strong>~</strong></span> 
					<input id="endDate" class="tomorrow" type="date"> <span></span>
					<span><strong>LotNo</strong></span>
					<input id="fgoodsLotNo" class="fgoodsLotNo" type="text" style="width: 230px;" onclick="$(this).val('')" onkeypress="javascript:if(event.keyCode==13) {InspectionWorkONo()}" autofocus>
				</div>
			</div>
		</div>
		<div id="salesLargePackingTable" style="float:left; width: calc(60% - 10px)"></div>
		<div id="salesSmallPackingTable" style="float:left; width: 40%; margin-left: 10px;"></div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/sales/salesInput.js?v=<%=System.currentTimeMillis()%>"></script>
<%-- <script src="/js/labelPrint.js?v=<%=System.currentTimeMillis() %>"></script> --%>