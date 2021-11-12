<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Insert Modal -->
<div class="modal fade" id="customerManageModal" tabindex="-1" role="dialog"
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
						<label class="col-sm-3 control-label" style="color: red;">거&nbsp;래&nbsp;처&nbsp;코&nbsp;드</label>
						<div class="col-sm-2">
							<input type="text" class="form-control-plaintext"
								id="cus_Code"
								style="border: 0.5px solid #858585; width: 170%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Name').focus()}">
						</div>

						<label class="col-sm-3 control-label">거&nbsp;래&nbsp;처&nbsp;이&nbsp;름</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="cus_Name"
								style="border: 0.5px solid #858585; width: 95%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Status').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">납&nbsp;&nbsp;품&nbsp;&nbsp;조&nbsp;&nbsp;건</label>
						<div class="col-sm-2">
							<select id="cus_Status"
								class="form-select form-select-sm"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Rprsn').focus()}">
								<c:forEach var="data" items="${paymentMethodList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>

						<label class="col-sm-3 control-label">대&nbsp;&nbsp;표&nbsp;&nbsp;자&nbsp;&nbsp;명</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="cus_Rprsn"
								style="border: 0.5px solid #858585; width: 95%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Mng').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">담&nbsp;&nbsp;당&nbsp;&nbsp;자&nbsp;&nbsp;명</label>
						<div class="col-sm-2">
							<input type="text" class="form-control-plaintext"
								id="cus_Mng"
								style="border: 0.5px solid #858585; width: 170%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Co').focus()}">
						</div>

						<label class="col-sm-3 control-label">회&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="cus_Co"
								style="border: 0.5px solid #858585; width: 95%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Pymn_Date').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">결&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일</label>
						<div class="col-sm-2">
							<select id="cus_Pymn_Date"
								class="form-select form-select-sm"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Rprsn_PhNr').focus()}">
								<c:forEach var="data" items="${paymentDateList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>

						<label class="col-sm-3 control-label">대표전화번호</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="cus_Rprsn_PhNr"
								style="border: 0.5px solid #858585; width: 95%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Mng_PhNr').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">담당자전화번호</label>
						<div class="col-sm-2">
							<input type="text" class="form-control-plaintext"
								id="cus_Mng_PhNr"
								style="border: 0.5px solid #858585; width: 170%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Mng_Email').focus()}">
						</div>

						<label class="col-sm-3 control-label">담당자이메일</label>
						<div class="col-sm-3">
							<input type="text" class="form-control-plaintext"
								id="cus_Mng_Email"
								style="border: 0.5px solid #858585; width: 95%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Adr').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</label>
						<div class="col-sm-8">
							<input type="text" class="form-control-plaintext"
								id="cus_Adr"
								style="border: 0.5px solid #858585; width: 100%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Rgstr_Nr').focus()}">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">사업자등록번호</label>
						<div class="col-sm-8">
							<input type="text" class="form-control-plaintext"
								id="cus_Rgstr_Nr"
								style="border: 0.5px solid #858585; width: 100%;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Clsfc').focus()}">
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">거&nbsp;래&nbsp;처&nbsp;분&nbsp;류</label>
						<div class="col-sm-2">
							<select id="cus_Clsfc" class="form-select form-select-sm"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('cus_Co_EstYr').focus()}">
								<c:forEach var="data" items="${clsfcMethodList}">
									<c:choose>
										<c:when test="${data.CHILD_TBL_TYPE eq '모든거래처'}">
										</c:when>
										<c:otherwise>
											<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 control-label">회사설립연도</label>
						<div class="col-sm-2">
							<input type="date" class="form-control-plaintext today"
								id="cus_Co_EstYr" style="border: 0.5px solid #858585;"
								onkeydown="javascript:if(event.keyCode==13) {document.getElementById('insertModalInitbtn').focus()}">
						</div>
					</div>
					<button type="button" class="btn btn-primary insert" id="customerRegisterBtn">저장</button>
					
					<button type="button" class="btn btn-primary modify" id="customerModifyBtn">수정</button>
					<button type="button" class="btn btn-warning modify" id="customerRemoveBtn">삭제</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</form>

			</div>
		</div>
	</div>
</div>