<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="defectManageModal" tabindex="-1" role="dialog"
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
						<label class="col-sm-2 control-label" style="color: red;">불&nbsp;량&nbsp;코&nbsp;드</label>
						<div class="col-sm-10">
							<input type="text" id="defect_Code" style="width: 50%;">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 control-label">불&ensp;&nbsp;량&ensp;&nbsp;명</label>
						<div class="col-sm-10">
							<input type="text" id="defect_Name" style="width: 50%;">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 control-label">약&emsp;&emsp;&ensp;자</label>
						<div class="col-sm-10">
							<input type="text" id="defect_Abr" style="width: 25%;">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 control-label">사&nbsp;용&nbsp;유&nbsp;무</label>
						<div class="col-sm-10">
							<input type="checkbox" id="defect_Use_Status" checked>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 control-label">비&emsp;&emsp;&ensp;고</label>
						<div class="col-sm-10">
							<input type="text" id="defect_Rmkrs" style="width: 100%">
						</div>
					</div>
					
					<button type="button" class="btn btn-primary insert focusinsert" id="defectRegisterBtn">저장</button>
					
					<button type="button" class="btn btn-primary modify focusmodify" id="defectModifyBtn">수정</button>
					<button type="button" class="btn btn-warning modify" id="defectRemoveBtn">삭제</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</form>

			</div>
		</div>
	</div>
</div>