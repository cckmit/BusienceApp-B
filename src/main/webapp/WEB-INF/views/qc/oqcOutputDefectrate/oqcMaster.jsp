<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="soloView">

	<div class="tabs-wrap">

		<ul id="navigation">
			<li class="one selected"><a href="#div1">(일별) 품목</a></li>
			<li class="two"><a href="#div2">(월별) 품목</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">

			<ol>
				<li><div id="div1"><jsp:include page="Daily_items.jsp" /></div></li>
				<li><div id="div2"><jsp:include page="Monthly_items.jsp" /></div></li>
			</ol>


		</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>
<script src="/js/qc/oqcOutputDefectrate.js"></script>
<script src="/js/tabMenu.js"></script>

</body>
</html>