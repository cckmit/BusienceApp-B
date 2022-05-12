<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-box">
			<div class="col-sm-12 col-md-12">
				<span><strong>연도</strong></span> 
				<input class="this_year" id="year11" type="text">
			</div>
		</div>
	</div>
	<div id="EQUIPMENT_INFO_TBL11" style="float: left; width: 20%; border-right: solid;"></div>
	<div id="WorkOrder_tbl11" style="float: left; width: 20%; border-right: solid;"></div>
	<div id="Graph11" style="float: left; width: 60%; height: calc(100% - 137px); margin: 0 auto;"></div>
</div>
<!-- END MAIN -->