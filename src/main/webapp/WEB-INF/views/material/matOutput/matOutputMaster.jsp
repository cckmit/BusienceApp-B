<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- css -->
<link href="/css/tabMenu.css" rel="stylesheet" />

<div class="soloView">
	<div class="tabs-wrap">
		<ul id="navigation">
			<li class="one selected"><a href="#div1">출고조회</a></li>
			<li class="two"><a href="#div2">출고현황(품목)</a></li>
			<li class="three"><a href="#div3">출고현황(부서)</a></li>
			<li class="four"><a href="#div4">부서별명세서</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">
			<ol>
				<li>
					<div id="div1"><jsp:include page="matOutputList.jsp" /></div>
				</li>
				<li>
					<div id="div2"><jsp:include page="matOutputItemView.jsp" /></div>
				</li>
				<li>
					<div id="div3"><jsp:include	page="matOutputDeptView.jsp" /></div>
				</li>
				<li>
					<div id="div4"><jsp:include page="matOutputDeliveryList.jsp" /></div>
				</li>
			</ol>
		</div>
	</div>
	<!-- inline wrapper -->
</div>

<!-- Javascript -->
<script src="/js/material/matOutputMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/tabMenu.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint/paldangLabelPrint.js?v=<%=System.currentTimeMillis() %>"></script>
