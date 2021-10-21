<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- input Modal -->
<div class="modal fade" id="machineModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true"
	style="line-height: 17px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img class="insert" src="/images/modal/inputHeader.jpg" alt=""
					style="width: 40%; height: auto;" loading="lazy"
					style="position: relative;"> <img class="modify"
					src="/images/modal/updateDeleteHeader.jpg" alt=""
					style="width: 40%; height: auto;" loading="lazy"
					style="position: relative;">
			</div>
			<div class="modal-body">
				<form>
					<fieldset>
						<div>
							<label class="col-sm-2 control-label"
								for="EQUIPMENT_BUSINESS_PLACE">&nbsp;&nbsp;사&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;장&nbsp;</label>
							<div class="col-sm-9">
								<select id="EQUIPMENT_BUSINESS_PLACE"
									style="width: 50%; height: 25px;"
									OnKeyDown="nextFocus('EQUIPMENT_INFO_CODE')">
									<c:forEach var="data" items="${companyList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-2 control-label" for="EQUIPMENT_INFO_CODE"
								style="color: red;">&nbsp;&nbsp;설&nbsp;비&nbsp;코&nbsp;드</label>
							<div class="col-sm-9">
								<input type="text" id="EQUIPMENT_INFO_CODE"
									style="width: 50%; height: 25px;"
									OnKeyDown="nextFocus('EQUIPMENT_INFO_NAME')">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-2 control-label" for="EQUIPMENT_INFO_NAME">&nbsp;&nbsp;설&nbsp;&nbsp;&nbsp;&nbsp;비&nbsp;&nbsp;&nbsp;&nbsp;명&nbsp;</label>
							<div class="col-sm-9">
								<input type="text" id="EQUIPMENT_INFO_NAME"
									style="width: 50%; height: 25px;"
									OnKeyDown="nextFocus('EQUIPMENT_INFO_ABR')">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-2 control-label" for="EQUIPMENT_INFO_ABR">&nbsp;&nbsp;설&nbsp;비&nbsp;호&nbsp;기</label>
							<div class="col-sm-9">
								<input type="text" id="EQUIPMENT_INFO_ABR"
									style="width: 50%; height: 25px;"
									OnKeyDown="nextFocus('EQUIPMENT_HEIGHT')">
							</div>
						</div>
					</fieldset>
					<br>
					<fieldset>
						<label class="col-sm-2 control-label" for="EQUIPMENT_HEIGHT">&nbsp;&nbsp;설&nbsp;비&nbsp;높&nbsp;이</label>
						<div class="col-sm-3">
							<input type="text" id="EQUIPMENT_HEIGHT"
								style="width: 80%; height: 25px;"
								OnKeyDown="nextFocus('EQUIPMENT_WIDTH')">
						</div>
						<div class="col-sm-1"></div>
						<div>
							<label class="col-sm-2 control-label" for="EQUIPMENT_SERIAL_NUM">&nbsp;&nbsp;설&nbsp;비&nbsp;&nbsp;(S/N)</label>
							<div class="col-sm-3">
								<input type="text" id="EQUIPMENT_SERIAL_NUM"
									style="width: 80%; height: 25px;"
									OnKeyDown="nextFocus('EQUIPMENT_WEIGHT')">
							</div>
						</div>
						<br> <br> <label class="col-sm-2 control-label"
							for="EQUIPMENT_WIDTH">&nbsp;&nbsp;설&nbsp;비&nbsp;&nbsp;(폭)</label>
						<div class="col-sm-3">
							<input type="text" id="EQUIPMENT_WIDTH"
								style="width: 80%; height: 25px;"
								OnKeyDown="nextFocus('EQUIPMENT_DEPTH')">
						</div>
						<div class="col-sm-1"></div>
						<label class="col-sm-2 control-label" for="EQUIPMENT_WEIGHT">&nbsp;&nbsp;설&nbsp;비&nbsp;무&nbsp;게</label>
						<div class="col-sm-3">
							<input type="text" id="EQUIPMENT_WEIGHT"
								style="width: 80%; height: 25px;"
								OnKeyDown="nextFocus('EQUIPMENT_RECEIVED_D')">
						</div>
						<br> <br> <label class="col-sm-2 control-label"
							for="EQUIPMENT_DEPTH">&nbsp;&nbsp;설&nbsp;비&nbsp;깊&nbsp;이</label>
						<div class="col-sm-3">
							<input type="text" id="EQUIPMENT_DEPTH"
								style="width: 80%; height: 25px;"
								OnKeyDown="nextFocus('EQUIPMENT_SERIAL_NUM')">
						</div>
					</fieldset>
					<br>
					<fieldset>
						<label class="col-sm-2 control-label" for="EQUIPMENT_RECEIVED_D">&nbsp;&nbsp;구&nbsp;입&nbsp;일&nbsp;자</label>
						<div class="col-sm-3">
							<input type="date" onchange='date_change(this)'
								id="EQUIPMENT_RECEIVED_D" style="width: 120%; height: 25px;"
								OnKeyDown="nextFocus('EQUIPMENT_MODEL_YEAR')">
						</div>
						<div class="col-sm-1"></div>
						<label class="col-sm-2 control-label" for="EQUIPMENT_STATUS">&nbsp;&nbsp;설&nbsp;비&nbsp;상&nbsp;태</label>
						<div class="col-sm-3">
							<select id="EQUIPMENT_STATUS" style="width: 80%; height: 25px;"
								OnKeyDown="nextFocus('EQUIPMENT_USE_STATUS')">
								<c:forEach var="data" items="${equipmentStatusList}">
									<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
								</c:forEach>
							</select>
						</div>
						<br> <br> <label class="col-sm-2 control-label"
							for="EQUIPMENT_MODEL_YEAR">&nbsp;&nbsp;설&nbsp;비&nbsp;연&nbsp;식</label>
						<div class="col-sm-3">
							<input type="text" id="EQUIPMENT_MODEL_YEAR"
								style="width: 80%; height: 25px;"
								OnKeyDown="nextFocus('EQUIPMENT_MANUFACTURER')">
						</div>
						<div class="col-sm-1"></div>
						<label class="col-sm-2 control-label" for="EQUIPMENT_USE_STATUS">&nbsp;&nbsp;사&nbsp;용&nbsp;유&nbsp;무</label>
						<div class="col-sm-3">
							<input type="checkbox" id="EQUIPMENT_USE_STATUS"
								OnKeyDown="nextFocus('EQUIPMENT_INFO_RMARK')">
						</div>
						<br> <br> <label class="col-sm-2 control-label"
							for="EQUIPMENT_MANUFACTURER">&nbsp;&nbsp;제&nbsp;작&nbsp;업&nbsp;체</label>
						<div class="col-sm-3">
							<input type="text" id="EQUIPMENT_MANUFACTURER"
								style="width: 120%; height: 25px;"
								OnKeyDown="nextFocus('EQUIPMENT_STATUS')">
						</div>
						<div class="col-sm-1"></div>
						<label class="col-sm-2 control-label" for="EQUIPMENT_INFO_RMARK">&nbsp;&nbsp;비&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고</label>
						<div class="col-sm-3">
							<input type="text" id="EQUIPMENT_INFO_RMARK"
								style="width: 130%; height: 25px;"
								onkeypress="javascript:if(event.keyCode==13) {$('.focusBtn').focus()}">
						</div>
					</fieldset>
					<br>
					<div class="insert">
						<button type="button" class="btn btn-primary focusBtn"
							data-toggle="modal" data-target="#insertYesNo">저장</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
					</div>
					<div class="modify">
						<button type="button" class="btn btn-primary focusBtn"
							data-toggle="modal" data-target="#modifyYesNo">수정</button>
						<button type="button" class="btn btn-warning" data-toggle="modal"
							data-target="#deleteYesNo">삭제</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>