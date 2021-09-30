<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Insert Modal -->
<jsp:include page="../modal/standard/machineModal.jsp"></jsp:include>

	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/ADD.png" data-toggle="modal" data-target="#machineModal" onclick="insert()"/>
					<img src="/images/button/Update.png" onclick="modify()"/>
					<img src="/images/button/Delete.png" onclick="modify()"/>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="machineManageTable"></div>
		</div>
		<!-- END MAIN -->
	</div>
	
	<!-- Delete Message Modal -->
	<jsp:include page="../modal/message/deleteYesNo.jsp"></jsp:include>
	
	<!-- Modify Message Modal -->
	<jsp:include page="../modal/message/modifyYesNo.jsp"></jsp:include>
	
	<!-- Insert Message Modal -->
	<jsp:include page="../modal/message/insertYesNo.jsp"></jsp:include>

<script src="/js/standard/machineManage.js"></script>
