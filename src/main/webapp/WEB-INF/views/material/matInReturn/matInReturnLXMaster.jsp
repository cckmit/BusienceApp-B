<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/css/tabMenu.css" rel="stylesheet" />

<div class="soloView">
	<div class="tabs-wrap">
		<ul id="navigation">
			<li class="one selected"><a href="#div1">입고반품입력</a></li>
			<li class="two"><a href="#div2">입고반품조회</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">
			<ol>
				<li>
					<div id="div1">
						<jsp:include page="matInReturnInsert.jsp" />
					</div>
				</li>
				<li>
					<div id="div2">
						<jsp:include page="matInReturnSearch.jsp" />
					</div>
				</li>
				<li><div id="div3"></div></li>
			</ol>
		</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>

<!-- Javascript -->
<script src="/js/material/matInReturnLXMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/tabMenu.js?v=<%=System.currentTimeMillis() %>"></script>	