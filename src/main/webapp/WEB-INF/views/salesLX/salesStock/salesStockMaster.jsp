<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div class="soloView">
		<div class="tabs-wrap">
			<ul id="navigation">
			<!-- <li class="one selected">
				<a href="#div1">현재고현황(LOT)</a>
			</li> -->
			<li class="two selected">
				<a href="#div2">현재고현황(품목)</a>
			</li>
			<li class="shadow"></li>
		</ul>
		<div id="content">
			<ol>
				<%-- <li>
					<div id="div1">
						<jsp:include page="salesStockLotList.jsp" />
					</div>
				</li> --%>
				<li>
					<div id="div2">
						<jsp:include page="salesStockItemList.jsp" />
					</div>
				</li>
				<li>
					<div id="div3"></div>
				</li>
			</ol>
		</div>
			<!-- .demo-nav -->
		</div>
		<!-- inline wrapper -->
	</div>
<!-- Javascript -->
<script src="/js/tabMenu.js"></script>
<script src="/js/salesLX/salesStockLXMaster.js"></script>

</body>
</html>