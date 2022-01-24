<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/css/tabMenu.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet" />

<div class="soloView">

	<div class="tabs-wrap">

		<ul id="navigation">
			<li class="one selected"><a href="#div1">출고반품입력</a></li>
			<li class="two"><a href="#div2">출고반품조회</a></li>
			<li class="three"><a href="#div3">판매출고</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">

			<ol>
				<li>
					<div id="div1">
						<jsp:include page="matOutReturnLXInsert.jsp" />
					</div>
				</li>
				<li>
					<div id="div2">
						<jsp:include page="matOutReturnLXSearch.jsp" />
					</div>
				</li>
				<li>
					<div id="div3">
						<jsp:include page="matOutReturnLXSalesDelivery.jsp" />
					</div>
				</li>
				<li>
					<div id="div4">
					</div>
				</li>
			</ol>
		</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>

<script src="/js/materialLX/matOutReturnLXMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/tabMenu.js?v=<%=System.currentTimeMillis() %>"></script>