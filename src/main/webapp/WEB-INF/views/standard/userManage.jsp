<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%
	String sql = "select * from MENU_MGMT_TBL where MENU_USER_CODE = 'admin' and MENU_PROGRAM_CODE = '13201'";

Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://busience2.cafe24.com:3306/busience2", "busience2",
		"business12!!");
PreparedStatement pstmt = con.prepareStatement(sql);
ResultSet rs = pstmt.executeQuery(sql);

boolean MENU_WRITE_USE_STATUS = false;
boolean MENU_DEL_USE_STATUS = false;

while (rs.next()) {
	if (rs.getString("MENU_WRITE_USE_STATUS").equals("true"))
		MENU_WRITE_USE_STATUS = true;

	if (rs.getString("MENU_DEL_USE_STATUS").equals("true"))
		MENU_DEL_USE_STATUS = true;
}
%>

<!-- Insert Modal -->
<div class="modal fade" id="insertModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img src="${contextPath}/resources/assets/img/inputHeader.jpg"
					alt="" style="width: 40%; height: auto;" loading="lazy"
					style="position: relative;">
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group row">
						<label class="col-sm-3 control-label" style="color: red;">사&nbsp;용&nbsp;자&nbsp;코&nbsp;드</label>
						<div class="col-sm-9">
							<input type="text" class="form-control-plaintext"
								id="insert_user_CODE" style="width: 50%;"
								placeholder="사용자코드 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_user_NAME').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;자&nbsp;&nbsp;&nbsp;명</label>
						<div class="col-sm-9">
							<input type="text" style="width: 50%;"
								class="form-control-plaintext" id="insert_user_NAME"
								placeholder="사용자명 입력"
								OnKeyDown="nextFocus('insert_user_COMPANY')">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장&nbsp;</label>
						<div class="col-sm-9">
							<select id="insert_user_COMPANY"
								OnKeyDown="nextFocus('insert_user_USE_STATUS')"
								style="width: 50%; height: 27px;">
								<c:forEach var="data" items="${companyList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;유&nbsp;&nbsp;&nbsp;무</label>
						<div class="col-sm-9">
							<input type="checkbox" class="form-control-plaintext"
								id="insert_user_USE_STATUS" placeholder="약자 입력"
								OnKeyDown="nextFocus('insert_user_TYPE')">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;용&nbsp;자&nbsp;타&nbsp;입</label>
						<div class="col-sm-9">
							<select id="insert_user_TYPE"
								OnKeyDown="nextFocus('insert_user_DEPT')"
								style="width: 50%; height: 27px;">
								<c:forEach var="data" items="${userTypeList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-3 control-label">부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서</label>
						<div class="col-sm-9">
							<select id="insert_user_DEPT"
								OnKeyDown="nextFocus('insert_user_PASSWORD')"
								style="width: 50%; height: 27px;">
								<c:forEach var="data" items="${deptList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					
					<div class="form-group row">
						<label class="col-sm-3 control-label">비&nbsp;&nbsp;&nbsp;밀&nbsp;&nbsp;&nbsp;번&nbsp;&nbsp;&nbsp;호</label>
						<div class="col-sm-9">
							<input type="button" id="insert_user_PASSWORD"
								OnKeyDown="nextFocus('insertModalInitbtn')" style="width: 50%"
								value="초기화" onclick="pwReset()" />
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
				<img
					src="/images/modal/updateDeleteHeader.jpg"
					alt="" style="width: 40%; height: auto;" loading="lazy"
					style="position: relative;">
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group row">
						<label class="col-sm-3 control-label" style="color: red;">사&nbsp;용&nbsp;자&nbsp;코&nbsp;드</label>
						<div class="col-sm-9">
							<input style="width: 50%;" type="text"
								class="form-control-plaintext" id="update_user_CODE"
								placeholder="사용자코드 입력" readonly="readonly">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;자&nbsp;&nbsp;&nbsp;명</label>
						<div class="col-sm-9">
							<input type="text" style="width: 50%;"
								class="form-control-plaintext" id="update_user_NAME"
								OnKeyDown="nextFocus('update_user_COMPANY')"
								placeholder="사용자명 입력">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장&nbsp;</label>
						<div class="col-sm-9">
							<select id="update_user_COMPANY"
								OnKeyDown="nextFocus('update_user_USE_STATUS')"
								style="width: 50%; height: 27px;">
								<c:forEach var="data" items="${companyList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;유&nbsp;&nbsp;&nbsp;무</label>
						<div class="col-sm-9">
							<input type="checkbox" class="form-control-plaintext"
								OnKeyDown="nextFocus('update_user_PASSWORD')"
								id="update_user_USE_STATUS">
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">비&nbsp;&nbsp;&nbsp;밀&nbsp;&nbsp;&nbsp;번&nbsp;&nbsp;&nbsp;호</label>
						<div class="col-sm-9">
							<input type="button" id="update_user_PASSWORD"
								OnKeyDown="nextFocus('update_user_TYPE')" style="width: 50%"
								value="초기화" onclick="pwReset()" />
						</div>
					</div>
					<div class="form-group row">
						<label for="update_user_TYPE" class="col-sm-3 control-label">사&nbsp;용&nbsp;자&nbsp;타&nbsp;입</label>
						<div class="col-sm-9">
							<select id="update_user_TYPE" style="width: 50%; height: 27px;"
								OnKeyDown="nextFocus('update_user_DEPT')">
								<c:forEach var="data" items="${userTypeList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label for="update_user_DEPT" class="col-sm-3 control-label">부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서</label>
						<div class="col-sm-9">
							<select id="update_user_DEPT" style="width: 50%; height: 27px;"
								OnKeyDown="nextFocus('updateModalInitbtn')">
								<c:forEach var="data" items="${deptList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>


					<%
						if (MENU_WRITE_USE_STATUS) {
					%>
					<button type="button" class="btn btn-primary" data-toggle="modal"
						id="updateModalInitbtn" data-target="#modifyYesNo">수정</button>
					<%
						} else {
					%>
					<button type="button" class="btn btn-primary" data-toggle="modal"
						id="updateModalInitbtn" data-target="#modifyYesNo"
						disabled="disabled">수정</button>
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
					<!-- data-toggle="modal" data-target="#insertModal" -->
					<%
						if (MENU_WRITE_USE_STATUS) {
					%>
					<img id="custom-btn-default"
						src="/images/button/ADD.png"/>
						<img
							src="/images/button/Update.png" onclick="updatedeleteView()" />
					<%
						} else {
					%>
					<img id="custom-btn-defaultd"
						src="/images/button/ADD.png"
						style="background-color: red; color: red; opacity: 0.5;"/>
						<img
							src="/images/button/Update.png"/>
					<%
						}
					%>
					
					<img src="/images/button/Delete.png"
						style="visibility: collapse;"
						onclick="updatedeleteView()" />

				</div>
			</div>

			<div id="userManageTable"></div>
		</div>
		<!-- END MAIN -->
	</div>

<script src="/js/standard/userManage.js"></script>