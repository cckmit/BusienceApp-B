<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.modal input {
	border: 0.5px solid #858585;
	width: 170%;
}
</style>

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
						<label class="col-sm-3 control-label" style="color: red;">거 래 처 코 드</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Code">
						</div>

						<label class="col-sm-3 control-label">거 래 처 이 름</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Name">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">거 래 처 분 류</label>
						<div class="col-sm-2">
							<select id="cus_Clsfc">
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
						
						<label class="col-sm-3 control-label">대&emsp;&ensp;표&emsp;&ensp;자</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Rprsn">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-3 control-label">대표전화번호</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Rprsn_PhNr">
						</div>
						
						<label class="col-sm-3 control-label">대 표 이 메 일</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Rprsn_Email">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">담&emsp;&ensp;당&emsp;&ensp;자</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Mng">
						</div>
						
						<label class="col-sm-3 control-label">담당전화번호</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Mng_PhNr">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">담 당 이 메 일</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Mng_Email">
						</div>
						
						<label class="col-sm-3 control-label">상&emsp;&ensp;호&emsp;&ensp;명</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Co">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-3 control-label">설&ensp;립&ensp;연&ensp;도</label>
						<div class="col-sm-2">
							<input type="date" class="today" id="cus_Co_EstYr">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-3 control-label">주&emsp;&emsp;&emsp;&emsp;소</label>
						<div class="col-sm-5">
							<input type="text" id="cus_Adr">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-3 control-label">결&emsp;&ensp;제&emsp;&ensp;일</label>
						<div class="col-sm-2">
							<select id="cus_Pymn_Date"
								class="form-select form-select-sm">
								<c:forEach var="data" items="${paymentDateList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
							
						<label class="col-sm-3 control-label">결&ensp;제&ensp;방&ensp;법</label>
						<div class="col-sm-2">
							<select id="cus_Status">
								<c:forEach var="data" items="${paymentMethodList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-3 control-label">결&ensp;제&ensp;은&ensp;행</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Bank">
						</div>
						
						<label class="col-sm-3 control-label">계&ensp;좌&ensp;번&ensp;호</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Account_No">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-3 control-label">사업자등록번호</label>
						<div class="col-sm-2">
							<input type="text" id="cus_Rgstr_Nr">
						</div>
					</div>
					
					<button type="button" class="btn btn-primary insert focusinsert" id="customerRegisterBtn">저장</button>
					
					<button type="button" class="btn btn-primary modify focusmodify" id="customerModifyBtn">수정</button>
					<button type="button" class="btn btn-warning modify" id="customerRemoveBtn">삭제</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</form>

			</div>
		</div>
	</div>
</div>