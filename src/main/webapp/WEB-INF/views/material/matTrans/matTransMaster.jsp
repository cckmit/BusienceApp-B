<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/css/tabMenu.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet" />

<div class="soloView">

	<div class="tabs-wrap">

		<ul id="navigation">
			<li class="one selected"><a href="#div1">자재이동입력</a></li>
			<li class="two"><a href="#div2">자재이동조회</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">

			<ol>
				<li>
					<div id="div1">
						<jsp:include page="matTransInsert.jsp" />
					</div>
				</li>
				<li>
					<div id="div2">
						<jsp:include page="matTransSearch.jsp" />
					</div>
				</li>
				<li>
					<div id="div3">
					</div>
				</li>
			</ol>
		</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>

<script src="/js/material/matTransMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/tabMenu.js?v=<%=System.currentTimeMillis() %>"></script>