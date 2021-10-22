<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Update Modal -->
<div class="modal fade" id="customerModifyModal" tabindex="-1" role="dialog"
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
				<form class="form-horizontal">
					<div class="form-group row">
						<label class="col-sm-3 control-label" style="color: red;">거&nbsp;래&nbsp;처&nbsp;코&nbsp;드</label>
						<div class="col-sm-2">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Code"
								style="border: 0.5px solid #858585; width: 170%;"
								placeholder="거래처코드 입력"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Name').focus()}"
								readonly="readonly">
						</div>

						<label class="col-sm-3 control-label">거&nbsp;래&nbsp;처&nbsp;이&nbsp;름</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Name"
								style="border: 0.5px solid #858585; width: 95%;"
								placeholder="거래처이름 입력"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Status').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">상&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;태</label>
						<div class="col-sm-2">
							<select id="update_cus_Status" class="form-select form-select-sm"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Rprsn').focus()}">
								<c:forEach var="data" items="${paymentMethodList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>

						<label class="col-sm-3 control-label">대&nbsp;&nbsp;표&nbsp;&nbsp;자&nbsp;&nbsp;명</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Rprsn"
								style="border: 0.5px solid #858585; width: 95%;"
								placeholder="대표자명 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Mng').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">담&nbsp;&nbsp;당&nbsp;&nbsp;자&nbsp;&nbsp;명</label>
						<div class="col-sm-2">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Mng"
								style="border: 0.5px solid #858585; width: 170%;"
								placeholder="담당자명 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Co').focus()}">
						</div>

						<label class="col-sm-3 control-label">회&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Co"
								style="border: 0.5px solid #858585; width: 95%;"
								placeholder="회사명 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Rprsn_PhNr').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<!-- 알수 없는 이유로 날짜를 선택할수 없음 : 2021-02-15 박성민
						<label class="col-sm-4 control-label">회&nbsp;사&nbsp;설&nbsp;립&nbsp;연&nbsp;도</label>
						<div class="col-sm-2">
							<input type="date" class="form-control-plaintext"
								id="insert_cus_Co_EstYr" style="border:0.5px solid #858585; width: 170%;"
								placeholder="연도 입력">
						</div>
						 -->
						<label class="col-sm-3 control-label">결&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일</label>
						<div class="col-sm-2">
							<select id="update_cus_Pymn_Date"
								class="form-select form-select-sm">
								<option value="매달 말일">매달 말일</option>
								<option value="매달 10일">매달 10일</option>
								<option value="매달 15일">매달 15일</option>
								<option value="매달 20일">매달 20일</option>
							</select>
						</div>

						<label class="col-sm-3 control-label">대표전화번호</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Rprsn_PhNr"
								style="border: 0.5px solid #858585; width: 95%;"
								placeholder="전화번호 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Mng_PhNr').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">담당자전화번호</label>
						<div class="col-sm-2">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Mng_PhNr"
								style="border: 0.5px solid #858585; width: 170%;"
								placeholder="전화번호 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Mng_Email').focus()}">
						</div>

						<label class="col-sm-3 control-label">담당자이메일</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Mng_Email"
								style="border: 0.5px solid #858585; width: 95%;"
								placeholder="이메일 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Adr').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</label>
						<div class="col-sm-8">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Adr"
								style="border: 0.5px solid #858585; width: 100%;"
								placeholder="주소 입력"
								onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Clsfc').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사업자등록번호</label>
						<div class="col-sm-8">
							<input type="text" class="form-control-plaintext"
								id="update_cus_Rgstr_Nr"
								style="border: 0.5px solid #858585; width: 100%;"
								placeholder="사업자등록번호 입력">
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">거&nbsp;래&nbsp;처&nbsp;분&nbsp;류</label>
						<div class="col-sm-2">
							<select id="update_cus_Clsfc" class="form-select form-select-sm"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('update_cus_Co_EstYr').focus()}">
								<option value="241">매출 거래처</option>
								<option value="240">매입 거래처</option>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">회사설립연도</label>
						<div class="col-sm-2">
							<input type="date" class="form-control-plaintext"
								id="update_cus_Co_EstYr" style="border: 0.5px solid #858585;"
								placeholder="연도 입력" OnKeyDown="nextFocus('updateModalInitbtn')">
						</div>
					</div>
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modifyYesNo" id="updateModalInitbtn">수정</button>
					<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#deleteYesNo">삭제</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="modResetBtn()">취소</button>
				</form>

			</div>
		</div>
	</div>
</div>