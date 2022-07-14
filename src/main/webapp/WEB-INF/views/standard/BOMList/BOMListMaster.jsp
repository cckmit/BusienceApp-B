<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="${contextPath}/resources/css/tabMenu.css" rel="stylesheet"/>

	<div class="soloView">
		<div class="tabs-wrap">
			<ul id="navigation">
				<li class="one selected"><a href="#div1">정전개</a></li>
				<li class="two"><a href="#div2">역전개</a></li>
				<li class="shadow"></li>
			</ul>

			<div id="content">
				<ol>
					<li>
						<div id="div1">
							<jsp:include page="BOMExpList.jsp"/>
						</div>
					</li>
					<li>
						<div id="div2">
							<jsp:include page="BOMImpList.jsp"/>
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
<script src="js/standard/BOMListMaster.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="js/tabMenu.js?v=<%=System.currentTimeMillis() %>"></script>
