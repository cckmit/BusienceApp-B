<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- css -->
<link href="/css/tabMenu.css" rel="stylesheet" />

<div class="soloView">
	<div class="tabs-wrap">
		<ul id="navigation">
			<li class="one selected"><a href="#div1">재고현황(품목)</a></li>
			<li class="two"><a href="#div2">재고현황(Lot)</a></li>
			<!-- <li class="five"><a href="#div5">납품명세서(전월)</a></li> -->
			<li class="shadow"></li>
		</ul>

		<div id="content">
			<ol>
				<li><div id="div1"><jsp:include page="salesStockItemList.jsp" /></div></li>
				<li><div id="div2"><jsp:include page="salesStockLotList.jsp" /></div></li>
				<%-- <li><div id="div5"><jsp:include page="matInputDeliveryLastList.jsp" /></div></li> --%>
			</ol>
		</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>

<!-- Javascript -->
<script src="/js/sales/salesStockList.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/tabMenu.js?v=<%=System.currentTimeMillis() %>"></script>
