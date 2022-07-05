<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="soloView" style="width: 100%; height: 100%;">
	<ul id="navigation">
		<c:forEach var="data" items="${machineList}">
			<li class="tab_${data.equip_WorkOrder_Code}">
		    	<a href="/tablet/maskPackagingMaster?machineCode=${data.equip_WorkOrder_Code}">${data.equip_WorkOrder_Name}</a>
		    	<div class="light_point"></div>
		    </li>
		</c:forEach>
	</ul>	
	<div id="content">
		<jsp:include page="maskPackagingTablet.jsp?v=<%=System.currentTimeMillis() %>"/>
	</div>
</div>
	<!-- inline wrapper -->
<!-- Javascript -->
<script>
$(".tab_${machineCode}").addClass("active")
</script>

<script src="/js/tabMenu.js"></script>
<script src="/js/tablet/maskPackagingTablet.js?v=<%=System.currentTimeMillis() %>"></script>
<script src="/js/labelPrint/paldangLabelPrint.js?v=<%=System.currentTimeMillis() %>"></script>