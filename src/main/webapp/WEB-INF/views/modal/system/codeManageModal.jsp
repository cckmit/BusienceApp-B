<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="modal fade" id="codeManageModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img src="/images/modal/inputHeader.jpg" class="insert" style="width: 40%;">
				
				<img src="/images/modal/updateDeleteHeader.jpg" class="modify" style="width: 40%;">
			</div>
			<div class="modal-body">
				<form>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">타입명</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext" id="CHILD_TBL_TYPE" style="width: 50%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('CHILD_TBL_RMARK').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 col-form-label">비고</label>
						<div class="col-sm-10">
							<input type="text" class="form-control-plaintext" id="CHILD_TBL_RMARK"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('CHILD_TBL_USE_STATUS').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 col-form-label">사용유무</label>
						<div class="col-sm-10">
							<input type="checkbox" class="form-control-plaintext" id="CHILD_TBL_USE_STATUS"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('insertModalInitbtn').focus()}" checked>
						</div>
					</div>

					<button type="button" id="codeRegisterBtn" class="btn btn-primary insert">저장</button>

					<button type="button" id="codeModifyBtn" class="btn btn-primary modify">수정</button>	
					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>