<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Update Modal -->
<jsp:include page="../modal/standard/userManageModal.jsp"></jsp:include>

	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<div class="input-button">
				
					<img src="/images/button/ADD.png" id="userADDBtn"/>
					
					<img src="/images/button/Update.png" id="userUpdateBtn"/>
					
					<img src="/images/button/Excel.png" id="download_xlsx"/>
				</div>
			</div>

			<div id="userManageTable"></div>
		</div>
		<!-- END MAIN -->
	</div>

<script src="/js/standard/userManage.js"></script>