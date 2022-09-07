<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- css -->
<link href="/css/tabMenu.css" rel="stylesheet" />

<div class="soloView">
	<div class="tabs-wrap">
		<ul id="navigation">
			<li class="one selected"><a href="#div1">재고현황(품목)</a></li>
			<c:if test="${lotUse}">
				<li class="two"><a href="#div2">재고현황(Lot)</a></li>
			</c:if>
			<!-- <li class="five"><a href="#div5">납품명세서(전월)</a></li> -->
			<li class="shadow"></li>
		</ul>
		<div id="content">
			<ol>
				<li><div id="div1"><jsp:include page="matStockItem.jsp" /></div></li>				
				<c:if test="${lotUse}">
					<li><div id="div2"><jsp:include page="matStockLotNo.jsp" /></div></li>
				</c:if>
				<%-- <li><div id="div5"><jsp:include page="matInputDeliveryLastList.jsp" /></div></li> --%>
			</ol>
		</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>

<!-- Javascript -->
<script src="/js/material/matStockMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/tabMenu.js?v=<%=System.currentTimeMillis() %>"></script>
