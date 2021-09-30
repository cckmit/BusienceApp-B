<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Update Modal -->
<div class="modal fade" id="itemModifyModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true"  style="line-height: 17px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img
					src="/images/modal/updateDeleteHeader.jpg"
					alt="" style="width: 40%; height: auto;" loading="lazy"
					style="position: relative;">
			</div>
			<div class="modal-body">
				<form>
					<fieldset>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장</label>
							<div class="col-sm-9">
								<select id="update_item_COMPANY"
									style="width: 50%; height: 25px;">
									<c:forEach var="data" items="${companyList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label" style="color: red;">&nbsp;&nbsp;품&nbsp;&nbsp;&nbsp;목&nbsp;&nbsp;&nbsp;코&nbsp;&nbsp;&nbsp;드</label>
							<div class="col-sm-9">
								<input type="text" class="form-control-plaintext" readonly="readonly"
									id="update_item_CODE" style="width: 50%; height: 25px;"
									placeholder="품목코드 입력"
									onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_olditem_CODE').focus()}">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;구&nbsp;품&nbsp;목&nbsp;코&nbsp;드</label>
							<div class="col-sm-9">
								<input type="text" style="width: 50%; height: 25px;"
									class="form-control-plaintext" id="update_olditem_CODE"
									placeholder="구품목코드 입력"
									onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_item_NAME').focus()}">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;품&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</label>
							<div class="col-sm-9">
								<input type="text" style="width: 70%; height: 25px;"
									class="form-control-plaintext" id="update_item_NAME"
									placeholder="품명 입력"
									onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_item_STND1').focus()}">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1</label>
							<div class="col-sm-9">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="update_item_STND1"
									placeholder="규격1 입력"
									onkeypress="javascript:if(event.keyCode==13) {document.getElementById('update_item_STND2').focus()}">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2</label>
							<div class="col-sm-9">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="update_item_STND2"
									placeholder="규격2 입력" OnKeyDown="nextFocus('update_item_UNIT')">
							</div>
						</div>
					</fieldset>
					<br>
					<fieldset>
						<div>
							<label class="col-sm-4 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;단&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;위</label>
							<label class="col-sm-4 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;재&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</label>
							<label class="col-sm-4 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자&nbsp;재&nbsp;분&nbsp;류</label>
						</div>
						<br>
						<div style="">
							<div class="col-sm-4">
								<select id="update_item_UNIT" style="width: 80%; height: 25px;"
									OnKeyDown="nextFocus('update_item_MAT')">
									<c:forEach var="data" items="${unitList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<select id="update_item_MAT" style="width: 80%; height: 25px;"
									OnKeyDown="nextFocus('update_item_mat_CLSFC')">
									<c:forEach var="data" items="${materialList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<select id="update_item_mat_CLSFC"
									style="width: 80%; height: 27px;"
									OnKeyDown="nextFocus('update_item_CLSFC1')">
									<c:forEach var="data" items="${mtrlClsfcList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<br> <br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;품&nbsp;목&nbsp;분&nbsp;류1</label>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;품&nbsp;목&nbsp;분&nbsp;류2</label>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;품&nbsp;목&nbsp;상&nbsp;태</label>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;기&nbsp;본&nbsp;창&nbsp;고</label>

						</div>
						<br>
						<div style="padding: auto; margin: 0px;">
							<div class="col-sm-3">
								<select id="update_item_CLSFC1"
									OnKeyDown="nextFocus('update_item_CLSFC2')"
									style="width: 100%; height: 25px;">
									<c:forEach var="data" items="${itemClsfc1List}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="update_item_CLSFC2"
									OnKeyDown="nextFocus('update_item_STATUS')"
									style="width: 100%; height: 25px;">
									<c:forEach var="data" items="${itemClsfc2List}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="update_item_STATUS"
									OnKeyDown="nextFocus('update_item_WAREHOUSE')"
									style="width: 100%; height: 25px;">
									<c:forEach var="data" items="${itemStatusList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="update_item_WAREHOUSE"
									OnKeyDown="nextFocus('update_item_subsid_mat_MGMT')"
									style="width: 100%; height: 25px;">
									<c:forEach var="data" items="${basicWarehouseList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</fieldset>
					<br>
					<fieldset>
						<div>
							<div class="col-sm-1"></div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;부&nbsp;자&nbsp;재&nbsp;관&nbsp;리</label>
							<div class="col-sm-1">
								<input type="checkbox" class="form-control-plaintext"
									id="update_item_subsid_mat_MGMT"
									OnKeyDown="nextFocus('update_item_USE_STATUS')">
							</div>
							<label class="col-sm-2 control-label">사&nbsp;용&nbsp;유&nbsp;무&nbsp;</label>
							<div class="col-sm-1">
								<input type="checkbox" class="form-control-plaintext"
									id="update_item_USE_STATUS"
									OnKeyDown="nextFocus('update_item_WRHSN')">
							</div>
							<label class="col-sm-2 control-label">입&nbsp;고&nbsp;검&nbsp;사&nbsp;</label>
							<div class="col-sm-1">
								<input type="checkbox" class="form-control-plaintext"
									id="update_item_WRHSN"
									OnKeyDown="nextFocus('update_item_min_ORDERS')">
							</div>
							<div class="col-sm-2"></div>
						</div>
					</fieldset>
					<br>
					<fieldset>
						<label class="col-sm-3 control-label">&nbsp;&nbsp;안&nbsp;&nbsp;&nbsp;전&nbsp;&nbsp;&nbsp;재&nbsp;&nbsp;&nbsp;고</label>
						<div class="col-sm-3">
							<input id="update_item_stfy_STOCK" type="number"
								OnKeyDown="nextFocus('update_item_BUYER')"
								style="width: 80%; height: 25px;" autocomplete="off">
						</div>
						<label class="col-sm-3 control-label">&nbsp;&nbsp;구&nbsp;&nbsp;&nbsp;매&nbsp;&nbsp;&nbsp;담&nbsp;&nbsp;&nbsp;당</label>
						<div class="col-sm-3">
							<input type="text" style="width: 110%; height: 25px;"
								class="form-control-plaintext" id="update_item_BUYER"
								placeholder="담당자명 입력"
								OnKeyDown="nextFocus('updateModalInitbtn')">
						</div>
					</fieldset>
					<br>
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modifyYesNo" id="updateModalInitbtn">수정</button>
					<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#deleteYesNo">삭제</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>