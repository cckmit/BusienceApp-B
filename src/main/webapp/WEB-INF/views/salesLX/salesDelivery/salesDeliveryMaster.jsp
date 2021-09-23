<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div class="soloView">
		<div class="tabs-wrap">
			<ul id="navigation">
			<li class="one selected">
				<a href="#div1">출고조회(거래처)</a>
			</li>
			<li class="two">
				<a href="#div2">거래처별명세서(당월)</a>
			</li>
			<li class="three">
				<a href="#div3">거래처별명세서(전월)</a>
			</li>
			<li class="shadow"></li>
		</ul>
		<div id="content">
			<ol>
				<li>
					<div id="div1">
						<jsp:include page="salesDeliveryCustomerView.jsp" />
					</div>
				</li>
				<li>
					<div id="div2">
						<jsp:include page="salesDeliveryList.jsp" />
					</div>
				</li>
				<li>
					<div id="div3">
						<jsp:include page="salesDeliveryLastList.jsp" />
					</div>
				</li>
			</ol>
		</div>
			<!-- .demo-nav -->
		</div>
		<!-- inline wrapper -->
	</div>
<!-- Javascript -->
<script src="/js/tabMenu.js"></script>
<script src="/js/salesLX/salesDeliveryLXMaster.js"></script>
</body>
</html>