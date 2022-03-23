<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="userManageModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img src="/images/modal/inputHeader.jpg" class="insert" style="width: 40%;">
				
				<img src="/images/modal/updateDeleteHeader.jpg" class="modify" style="width: 40%;">
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group row">
						<label class="col-sm-3 control-label" style="color: red;">사&nbsp;용&nbsp;자&nbsp;&nbsp;코&nbsp;드</label>
						<div class="col-sm-9">
							<input style="width: 50%;" type="text" OnKeyDown="nextFocus('user_Name')"
								class="form-control-plaintext nextFocus" id="user_Code">
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">비&nbsp;&nbsp;&nbsp;밀&nbsp;&nbsp;&nbsp;번&nbsp;&nbsp;&nbsp;호</label>
						<div class="col-sm-9">
							<input class="insert" value="기본값 '1234'" style="width: 50%" disabled/>
							
							<input type="button" id="user_Password" class="modify" style="width: 50%" value="초기화"/>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;용&nbsp;&nbsp;&nbsp;자&nbsp;&nbsp;&nbsp;명</label>
						<div class="col-sm-9">
							<input type="text" style="width: 50%;" 
								class="form-control-plaintext nextFocus" id="user_Name"
								OnKeyDown="nextFocus('company')">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장</label>
						<div class="col-sm-9">
							<select id="company" class="nextFocus"
								OnKeyDown="nextFocus('user_Use_Status')"
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
								OnKeyDown="nextFocus('user_Type')"
								id="user_Use_Status" checked>
						</div>
					</div>
					
					<div class="form-group row">
						<label for="user_TYPE" class="col-sm-3 control-label">사&nbsp;용&nbsp;자&nbsp;&nbsp;타&nbsp;입</label>
						<div class="col-sm-9">
							<select id="user_Type" style="width: 50%; height: 27px;"
								OnKeyDown="nextFocus('dept_Code')">
								<c:forEach var="data" items="${userTypeList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label for="user_DEPT" class="col-sm-3 control-label">부&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서</label>
						<div class="col-sm-9">
							<select id="dept_Code" style="width: 50%; height: 27px;"
								OnKeyDown="javascript:if(event.keyCode==13) {$('.focusBtn').focus()}">
								<c:forEach var="data" items="${deptList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<button type="button" id="userRegisterBtn" class="btn btn-primary insert focusBtn">저장</button>

					<button type="button" id="userModifyBtn" class="btn btn-primary modify focusBtn">수정</button>	
					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>