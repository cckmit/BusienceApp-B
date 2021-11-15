<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="defectRegisterModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="exampleModalLabel">
					<img src="/images/modal/inputHeader.jpg"
						alt="" style="width: 35%; height: auto;">
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
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_defect_USE_STATUS').focus()}">
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
					<button type="button" class="btn btn-primary" id="insertModalInitbtn" onclick="insertModal()">저장</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="insResetBtn()">취소</button>
				</form>

			</div>
		</div>
	</div>
</div>