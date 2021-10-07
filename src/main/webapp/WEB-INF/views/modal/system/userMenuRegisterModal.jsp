<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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