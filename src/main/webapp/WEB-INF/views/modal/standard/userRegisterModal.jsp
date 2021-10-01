<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="userRegisterModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img src="/images/modal/inputHeader.jpg" style="width: 40%; height: auto;" style="position: relative;">
			</div>
			<div class="modal-body">
				<form class="form-horizontal" method="post">
					<div class="form-group row">
						<label class="col-sm-3 control-label" style="color: red;">사&nbsp;용&nbsp;자&nbsp;&nbsp;코&nbsp;드</label>
						<div class="col-sm-9">
							<input type="text" class="form-control-plaintext" id="insert_user_CODE" name="USER_CODE" style="width: 50%;"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_user_NAME').focus()}">
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">비&nbsp;&nbsp;&nbsp;밀&nbsp;&nbsp;&nbsp;번&nbsp;&nbsp;&nbsp;호</label>
						<div class="col-sm-9">
							<input id="insert_user_PASSWORD" value="기본값 '1234'" OnKeyDown="nextFocus('insertModalInitbtn')" style="width: 50%" disabled/>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;자&nbsp;&nbsp;&nbsp;명</label>
						<div class="col-sm-9">
							<input type="text" style="width: 50%;" class="form-control-plaintext" id="insert_user_NAME" name="USER_NAME"
								OnKeyDown="nextFocus('insert_user_COMPANY')">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장</label>
						<div class="col-sm-9">
							<select id="insert_user_COMPANY" name="COMPANY" OnKeyDown="nextFocus('insert_user_USE_STATUS')" style="width: 50%; height: 27px;">
								<c:forEach var="data" items="${companyList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;유&nbsp;&nbsp;&nbsp;무</label>
						<div class="col-sm-9">
							<input type="checkbox" class="form-control-plaintext" id="insert_user_USE_STATUS"
								OnKeyDown="nextFocus('insert_user_TYPE')">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;용&nbsp;자&nbsp;&nbsp;타&nbsp;입</label>
						<div class="col-sm-9">
							<select id="insert_user_TYPE" name="USER_TYPE" OnKeyDown="nextFocus('insert_user_DEPT')" style="width: 50%; height: 27px;">
								<c:forEach var="data" items="${userTypeList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-3 control-label">부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서</label>
						<div class="col-sm-9">
							<select id="insert_user_DEPT" name="DEPT_CODE" OnKeyDown="nextFocus('insert_user_PASSWORD')" style="width: 50%; height: 27px;">
								<c:forEach var="data" items="${deptList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<button type="button" class="btn btn-primary" id="insertModalInitbtn" onclick="insBtn()">저장</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="insResetBtn()">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>