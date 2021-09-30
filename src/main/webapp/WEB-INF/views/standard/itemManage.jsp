<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Insert Modal -->
<jsp:include page="../modal/standard/itemRegisterModal.jsp"></jsp:include>

<!-- Update Modal -->
<jsp:include page="../modal/standard/itemModifyModal.jsp"></jsp:include>

<!-- Delete Massage Modal -->
<jsp:include page="../modal/message/deleteYesNo.jsp"></jsp:include>

<!-- Modify Message Modal -->
<jsp:include page="../modal/message/modifyYesNo.jsp"></jsp:include>

<!-- Insert Message Modal -->
<jsp:include page="../modal/message/insertYesNo.jsp"></jsp:include>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<img src="/images/button/ADD.png" id="registerModal"/>
					
				<img src="/images/button/Update.png" id="modifyModal" onclick="updatedeleteView()"/>
					
			 	<img src="/images/button/Delete.png" id="modifyModal" onclick="updatedeleteView()"/>
			</div>
		</div>

		<div id="itemManageTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/standard/itemManage.js"></script>