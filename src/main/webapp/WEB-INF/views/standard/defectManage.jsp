<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%
	String sql = "select * from MENU_MGMT_TBL where MENU_USER_CODE = 'admin' and MENU_PROGRAM_CODE = '13251'";

	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://busience2.cafe24.com:3306/busience2","busience2","business12!!");
	PreparedStatement pstmt = con.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery(sql);
	
	boolean MENU_WRITE_USE_STATUS = false;
	boolean MENU_DEL_USE_STATUS = false;
	
	while(rs.next())
	{
		if(rs.getString("MENU_WRITE_USE_STATUS").equals("true"))
			MENU_WRITE_USE_STATUS = true;
		
		if(rs.getString("MENU_DEL_USE_STATUS").equals("true"))
			MENU_DEL_USE_STATUS = true;
	}
%>

<!-- Insert Modal -->
<div class="modal fade" id="insertModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="exampleModalLabel">
					<strong>입력</strong>
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group row">
						<label class="col-sm-2 control-label" style="color: red;">불&nbsp;량&nbsp;코&nbsp;드</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext"
								id="insert_defect_CODE" style="width: 50%; border:0.5px solid #858585;"
								placeholder="불량코드 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_defect_NAME').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 control-label">불&nbsp;&nbsp;&nbsp;&nbsp;량&nbsp;&nbsp;&nbsp;&nbsp;명</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext"
								id="insert_defect_NAME" style="width: 50%;"
								placeholder="불량명 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_defect_ABR').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 control-label">약&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext"
								id="insert_defect_ABR" style="width: 25%;" placeholder="약자 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_defect_RMRKS').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 control-label">사&nbsp;용&nbsp;유&nbsp;무</label>
						<div class="col-sm-10">
							<input type="checkbox" class="form-control-plaintext"
								id="insert_defect_USE_STATUS" placeholder="약자 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_defect_RMRKS').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 control-label">비&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext"
								id="insert_defect_RMRKS" style="width: 100%;"
								placeholder="비고 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insertModalInitbtn').focus()}">
						</div>
					</div>

					<button type="button" class="btn btn-primary"
						id="insertModalInitbtn" onclick="insertModal()">저장</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="insResetBtn()">취소</button>
				</form>

			</div>
		</div>
	</div>
</div>

<!-- Update Modal -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="exampleModalLabel">
					<strong>수정 - 삭제</strong>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group row">
					<label class="col-sm-2 col-form-label" style="color: red;">불&nbsp;량&nbsp;코&nbsp;드</label>
					<div class="col-sm-10">
						<input type="text" class="form-control-plaintext"
							id="update_defect_CODE" style="width: 50%;"
							placeholder="불량코드 입력"
							onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_defect_NAME').focus()}"
							readonly="readonly">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label">불&nbsp;&nbsp;&nbsp;&nbsp;량&nbsp;&nbsp;&nbsp;&nbsp;명</label>
					<div class="col-sm-10">
						<input type="text" class="form-control-plaintext"
							id="update_defect_NAME" style="width: 50%;" placeholder="불량명 입력"
							onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_defect_ABR').focus()}">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label">약&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자</label>
					<div class="col-sm-10">
						<input type="text" class="form-control-plaintext"
							id="update_defect_ABR" style="width: 25%;" placeholder="약자 입력"
							onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_defect_RMRKS').focus()}">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label">사&nbsp;용&nbsp;유&nbsp;무</label>
					<div class="col-sm-10">
						<input type="checkbox" class="form-control-plaintext"
							id="update_defect_USE_STATUS"
							onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_defect_RMRKS').focus()}">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label">비&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고</label>
					<div class="col-sm-10">
						<input type="text" class="form-control-plaintext"
							id="update_defect_RMRKS" style="width: 100%;">
					</div>
				</div>

				<%
						if(MENU_WRITE_USE_STATUS)
						{
					%>
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#modifyYesNo">수정</button>
				<%
						}
						else
						{
					%>
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#modifyYesNo" disabled="disabled">수정</button>
				<%
						}
					%>

				<%
					if(MENU_DEL_USE_STATUS)
						{
					%>
				<button type="button" class="btn btn-warning" data-toggle="modal"
					data-target="#deleteYesNo">삭제</button>
				<%		
						}
						else
						{
					%>
				<button type="button" class="btn btn-warning" data-toggle="modal"
					data-target="#deleteYesNo" disabled="disabled">삭제</button>
				<%		
						}
					%>

				<button type="button" class="btn btn-danger" data-dismiss="modal"
					onclick="modResetBtn()">취소</button>
			</div>
		</div>
	</div>
</div>

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
					<!-- data-toggle="modal" data-target="#insertModal" -->
					<%
						if(MENU_WRITE_USE_STATUS)
						{
						%>
					<img id="custom-btn-default"
						src="/images/button/ADD.png"/>
					<img src="/images/button/Update.png" onclick="updatedeleteView()" />
					<%	
						}
						else
						{
						%>
					<img id="custom-btn-defaultd"
						src="/images/button/ADD.png" style="opacity: 0.5;" />
					<img src="/images/button/Update.png"/>
					<%
						}
					%>

					<%
						if(MENU_DEL_USE_STATUS)
						{
						%>
					<img src="/images/button/Delete.png" onclick="updatedeleteView()" />
					<%	
						}
						else
						{
						%>
					<img src="/images/button/Delete.png" style="opacity: 0.5;" />
					<%
						}
					%>
				</div>
			</div>
			<div id="example-table"></div>
		</div>
		<!-- END MAIN -->
	</div>

<script src="/js/standard/defectManage.js"></script>
