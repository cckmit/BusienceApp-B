<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Insert Modal -->
<jsp:include page="../modal/system/codeRegisterModal.jsp"></jsp:include>
<!-- Update Modal -->
<jsp:include page="../modal/system/codeModifyModal.jsp"></jsp:include>
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
					
			</div>
		</div>
		<div id="example-table1" style="width: calc(50% - 5px); float: left; margin-right: 10px;"></div>
		<div id="example-table2" style="width: calc(50% - 5px); float: left;"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/system/codeManage.js"></script>