<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- userMenuRegisterModal -->
<jsp:include page="../modal/system/userMenuRegisterModal.jsp"></jsp:include>

<!-- userMenuModifyModal -->
<jsp:include page="../modal/system/userMenuModifyModal.jsp"></jsp:include>

<!-- Delete Message Modal -->
<jsp:include page="../modal/message/deleteYesNo.jsp"></jsp:include>

<!-- Insert Message Modal -->
<jsp:include page="../modal/message/insertYesNo.jsp"></jsp:include>

	<div class="soloView">
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<div class="input-button" style="width: 100%">
					<img id="custom-btn-default" style="margin-right: 46%"
						src="/images/button/ADD.png" onclick="insertModal2()"/>
						
					<img id="custom-btn-default"
						src="/images/button/Delete.png" onclick="deleteModal()"/>	
				</div>
			</div>
			
			<div id="example-table1" style="float:left; width:calc(50% - 5px); margin-right: 10px;"></div>
			<div id="example-table2" style="float:left; width:calc(50% - 5px);"></div>
		</div>
		<!-- END MAIN -->
	</div>

<script src="/js/system/usermenuManage.js"></script>
