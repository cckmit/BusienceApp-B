<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Insert Modal -->
<jsp:include page="../modal/standard/defectRegisterModal.jsp"></jsp:include>

<!-- Update Modal -->
<jsp:include page="../modal/standard/defectModifyModal.jsp"></jsp:include>

<!-- Delete Message Modal -->
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
					
					<img src="/images/button/Excel.png" id="download_xlsx"/>
				</div>
			</div>
			<div id="example-table"></div>
		</div>
		<!-- END MAIN -->
	</div>

<script src="/js/standard/defectManage.js"></script>
