<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<div class="soloView">

			<div class="tabs-wrap">

		<ul id="navigation">
			<li class="one selected"><a href="#div1">(월별)설비 작업현황</a></li>
			<li class="one2"><a href="#div1">(월별)설비 작업현황2</a></li>
			<li class="two"><a id="q" href="#div2">(분기별)설비 작업현황</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">

					<ol>
						<li>
							<div id="div1">
								<jsp:include page="worktdListMonth.jsp" />
							</div>
						</li>
						<li>
							<div id="div11">
								<jsp:include page="worktdListMonth2.jsp" />
							</div>
						</li>
						<li>
							<div id="div2">
								<jsp:include page="worktdListQuarter.jsp" />
							</div>
						</li>
						<li><div id="div4"></div></li>
					</ol>
					
				</div>
				<!-- .demo-nav -->
			</div>
			<!-- inline wrapper -->
			
		</div>
	<script src="/js/productionLX/worktdList.js"></script>
</body>
</html>