<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/css/bs_tabMenu.css?v=<%=System.currentTimeMillis() %>"/>

<div class="soloView" style="width: 100%; height: 100%;">
	<div class="tabs-wrap">
		<ul id="navigation">
			<c:forEach var="data" items="${machineList}">
				<li class="tab_${data.EQUIPMENT_INFO_CODE}">
			    	<a href="/tablet/maskPackagingMaster?machineCode=${data.EQUIPMENT_INFO_CODE}">${data.EQUIPMENT_INFO_NAME}</a>
			    </li>
			</c:forEach>
		</ul>	
		<div id="content">
			<jsp:include page="maskPackagingTablet.jsp?v=<%=System.currentTimeMillis() %>"/>
		</div>
	</div>
</div>
	<!-- inline wrapper -->
<!-- Javascript -->
<script>
$(".tab_${machineCode}").addClass("selected")
</script>
<!-- <script src="/js/tabMenu.js"></script> -->
<script src="/js/tablet/maskPackagingTablet.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint/paldangLabelPrint.js?v=<%=System.currentTimeMillis() %>"></script>
