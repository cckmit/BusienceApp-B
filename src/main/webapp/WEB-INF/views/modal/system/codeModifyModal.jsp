<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Update Modal -->
<div class="modal fade" id="codeModifyModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="exampleModalLabel">
					<img src="/images/modal/updateDeleteHeader.jpg" alt=""
						style="width: 35%; height: auto;">
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
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#modifyYesNo">수정</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="modResetBtn()">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>