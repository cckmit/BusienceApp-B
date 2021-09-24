<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link href="/css/tabMenu.css" rel="stylesheet" />

<div class="soloView">
	<div class="tabs-wrap">
		<ul id="navigation">
			<li class="one"><a href="#div2">현재고현황(품목)</a></li>
			<li class="shadow"></li>
		</ul>
		<div id="content">
			<ol>
				<li><div id="div2"><jsp:include
							page="matStockItemListLX.jsp" /></div></li>
			</ol>
		</div>

		<!-- .demo-nav -->

	</div>
	<!-- inline wrapper -->
</div>

<!-- Javascript -->
<script src="/js/materialLX/matStockMasterLX.js"></script>
<script src="/js/tabMenu.js"></script>
