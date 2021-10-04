<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="userModifyModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img
					src="/images/modal/updateDeleteHeader.jpg"
					alt="" style="width: 40%; height: auto;"
					style="position: relative;">
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group row">
						<label class="col-sm-3 control-label" style="color: red;">사&nbsp;용&nbsp;자&nbsp;&nbsp;코&nbsp;드</label>
						<div class="col-sm-9">
							<input style="width: 50%;" type="text"
								class="form-control-plaintext" id="update_user_CODE" readonly="readonly">
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
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;자&nbsp;&nbsp;&nbsp;명</label>
						<div class="col-sm-9">
							<input type="text" style="width: 50%;"
								class="form-control-plaintext" id="update_user_NAME"
								OnKeyDown="nextFocus('update_user_COMPANY')">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장</label>
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
						<label for="update_user_TYPE" class="col-sm-3 control-label">사&nbsp;용&nbsp;자&nbsp;&nbsp;타&nbsp;입</label>
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
						<label for="update_user_DEPT" class="col-sm-3 control-label">부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서</label>
						<div class="col-sm-9">
							<select id="update_user_DEPT" style="width: 50%; height: 27px;"
								OnKeyDown="nextFocus('updateModalInitbtn')">
								<c:forEach var="data" items="${deptList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<button type="button" class="btn btn-primary" data-toggle="modal"
						id="updateModalInitbtn" onclick="updateTestBtn()">수정</button>
						
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="modResetBtn()">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>