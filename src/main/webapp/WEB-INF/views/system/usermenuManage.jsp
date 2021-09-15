<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%
	String Name = "'"+(String)session.getAttribute("name")+"'";

	String sql = "select * from MENU_MGMT_TBL where MENU_USER_CODE = 'admin' and MENU_PROGRAM_CODE = '13101'";

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
<div class="modal fade" id="insertModal"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="exampleModalLabel">
					<strong>입력</strong>
				</h4>
			</div>
			<div class="modal-body">
				<form>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">타입명</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext"
							id="CHILD_TBL_TYPE" style="width: 50%;" placeholder="타입명 입력"
							onkeydown="javascript:if(event.keyCode==13) {document.getElementById('CHILD_TBL_RMARK').focus()}">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">비고</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext"
							id="CHILD_TBL_RMARK" placeholder="비고 입력"
							onkeydown="javascript:if(event.keyCode==13) {document.getElementById('CHILD_TBL_USE_STATUS').focus()}">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">사용유무</label>
						<div class="col-sm-10">
							<input type="checkbox" class="form-control-plaintext"
							id="CHILD_TBL_USE_STATUS"
							onkeydown="javascript:if(event.keyCode==13) {document.getElementById('insertModalInitbtn').focus()}">
						</div>
					</div>
					
					<!-- data-toggle="modal" data-target="#insertModal2" -->
					<button type="button" class="btn btn-primary" id="insertModalInitbtn" 
					onclick="insertModal2()">
					저장
					</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
					onclick="insResetBtn()">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- Update Modal -->
<div class="modal fade" id="updateModal"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="exampleModalLabel">
					<strong>수정</strong>
				</h4>
			</div>
			<div class="modal-body">
				<form>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">타입명</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext"
							id="mCHILD_TBL_TYPE" style="width: 50%;" placeholder="타입명 입력"
							onkeydown="javascript:if(event.keyCode==13) {document.getElementById('mCHILD_TBL_RMARK').focus()}">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">비고</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext"
							id="mCHILD_TBL_RMARK" placeholder="비고 입력"
							onkeydown="javascript:if(event.keyCode==13) {document.getElementById('mCHILD_TBL_USE_STATUS').focus()}">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">사용유무</label>
						<div class="col-sm-10">
							<input type="checkbox" class="form-control-plaintext"
							id="mCHILD_TBL_USE_STATUS">
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
					<button type="button" class="btn btn-danger" data-dismiss="modal"
					onclick="modResetBtn()">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>

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
