<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Insert Modal -->
<jsp:include page="../modal/standard/userRegisterModal.jsp"></jsp:include>

<!-- Update Modal -->
<jsp:include page="../modal/standard/userModifyModal.jsp"></jsp:include>

<!-- Delete Modal -->
<jsp:include page="../modal/message/deleteYesNo.jsp"></jsp:include>

<!-- Modify Modal -->
<jsp:include page="../modal/message/modifyYesNo.jsp"></jsp:include>

<!-- Insert Modal -->
<jsp:include page="../modal/message/insertYesNo.jsp"></jsp:include>

	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<div class="input-button">
				
					<img src="/images/button/ADD.png" id="registerModal"/>
					
					<img src="/images/button/Update.png" id="modifyModal"/>
				</div>
			</div>

			<div id="userManageTable"></div>
		</div>
		<!-- END MAIN -->
	</div>

<script src="/js/standard/userManage.js"></script>