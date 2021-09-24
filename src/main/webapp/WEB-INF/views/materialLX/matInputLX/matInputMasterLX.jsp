<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- css -->
<link href="/css/tabMenu.css" rel="stylesheet" />

<div class="soloView">
	<div class="tabs-wrap">
		<ul id="navigation">
			<li class="one selected"><a href="#div1">입고조회</a></li>
			<li class="two"><a href="#div2">입고현황(품목)</a></li>
			<li class="three"><a href="#div3">입고현황(거래처)</a></li>
			<li class="four"><a href="#div4">납품명세서(당월)</a></li>
			<li class="five"><a href="#div5">납품명세서(전월)</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">
			<ol>
				<li><div id="div1"><jsp:include page="matInputListLX.jsp" /></div></li>
				<li><div id="div2"><jsp:include page="matInputItemViewLX.jsp" /></div></li>
				<li><div id="div3"><jsp:include page="matInputCustomerViewLX.jsp" /></div></li>
				<li><div id="div4"><jsp:include page="matInputDeliveryListLX.jsp" /></div></li>
				<li><div id="div5"><jsp:include page="matInputDeliveryLastListLX.jsp" /></div></li>
			</ol>
		</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>

<!-- Javascript -->
<script src="/js/materialLX/matInputMasterLX.js"></script>
<script src="/js/tabMenu.js"></script>
