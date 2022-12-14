<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/css/tabMenu.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet" />

<div class="soloView">

	<div class="tabs-wrap">

		<ul id="navigation">
			<li class="one selected"><a href="#div1">출고반품입력</a></li>
			<li class="two"><a href="#div2">출고반품조회</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">

			<ol>
				<li>
					<div id="div1">
						<jsp:include page="matOutReturnInsert.jsp" />
					</div>
				</li>
				<li>
					<div id="div2">
						<jsp:include page="matOutReturnSearch.jsp" />
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

<script src="/js/material/matOutReturnMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/tabMenu.js?v=<%=System.currentTimeMillis() %>"></script>