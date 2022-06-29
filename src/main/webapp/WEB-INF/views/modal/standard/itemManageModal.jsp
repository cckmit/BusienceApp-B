<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Update Modal -->
<div class="modal fade" id="itemManageModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true"  style="line-height: 17px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img src="/images/modal/inputHeader.JPG" class="insert" style="width: 40%;">
				
				<img src="/images/modal/updateDeleteHeader.JPG" class="modify" style="width: 40%;">
				
				<img src="/images/modal/copyHeader.JPG" class="copy" style="width: 40%;">
			</div>
			<div class="modal-body">
				<form>
					<fieldset>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장</label>
							<div class="col-sm-5">
								<select id="product_BUSINESS_PLACE"
									style="width: 80%; height: 25px;">
									<c:forEach var="data" items="${companyList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label" style="color: red;">&nbsp;&nbsp;품&nbsp;&nbsp;&nbsp;목&nbsp;&nbsp;&nbsp;&nbsp;코&nbsp;&nbsp;&nbsp;드</label>
							<div class="col-sm-5">
								<input type="text" class="form-control-plaintext" readonly="readonly"
									id="product_ITEM_CODE" style="width: 80%; height: 25px;"
									OnKeyDown="nextFocus('product_OLD_ITEM_CODE')">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;구&nbsp;&nbsp;품&nbsp;목&nbsp;&nbsp;코&nbsp;드</label>
							<div class="col-sm-5">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="product_OLD_ITEM_CODE"
									OnKeyDown="nextFocus('product_ITEM_NAME')">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;품&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</label>
							<div class="col-sm-5">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="product_ITEM_NAME"
									OnKeyDown="nextFocus('product_INFO_STND_1')">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1</label>
							<div class="col-sm-5">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="product_INFO_STND_1"
									OnKeyDown="nextFocus('product_INFO_STND_2')">
							</div>
						</div>
						<br> <br>
						<div id="standard">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2</label>
							<div class="col-sm-5">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="product_INFO_STND_2"
									OnKeyDown="nextFocus('product_UNIT_PRICE')">
							</div>
						</div>
						<div id="copyStnd">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2</label>
							<div class="col-sm-5">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="product_INFO_SUB_STND_2"
									OnKeyDown="nextFocus('product_UNIT_PRICE')">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;단&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;가</label>
							<div class="col-sm-5">
								<input type="number" style="width: 80%; height: 25px;" value="0"
									class="form-control-plaintext" id="product_UNIT_PRICE"
									OnKeyDown="nextFocus('product_MULTIPLE')">
							</div>
						</div>
					</fieldset>
					<br>
					<fieldset>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;배&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;수</label>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;단&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;위</label>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;재&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</label>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자&nbsp;재&nbsp;분&nbsp;류</label>
						</div>
						<br>
						<div style="">
							<div class="col-sm-3">
								<input type="number" value="1" id="product_MULTIPLE" style="width: 100%; height: 25px;" OnKeyDown="nextFocus('product_UNIT')">
							</div>
							<div class="col-sm-3">
								<select id="product_UNIT" style="width: 100%; height: 25px;"
								OnKeyDown="nextFocus('product_MATERIAL')">
									<c:forEach var="data" items="${unitList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="product_MATERIAL" style="width: 100%; height: 25px;"
								OnKeyDown="nextFocus('product_MTRL_CLSFC')">
									<c:forEach var="data" items="${materialList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="product_MTRL_CLSFC" style="width: 100%; height: 25px;"
								OnKeyDown="nextFocus('product_ITEM_CLSFC_1')">
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
								<select id="product_ITEM_CLSFC_1" style="width: 100%; height: 25px;"
								OnKeyDown="nextFocus('product_ITEM_CLSFC_2')">
									<c:forEach var="data" items="${itemClsfc1List}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="product_ITEM_CLSFC_2" style="width: 100%; height: 25px;"
								OnKeyDown="nextFocus('product_ITEM_STTS')">
									<c:forEach var="data" items="${itemClsfc2List}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="product_ITEM_STTS" style="width: 100%; height: 25px;"
								OnKeyDown="nextFocus('product_BASIC_WAREHOUSE')">
									<c:forEach var="data" items="${itemStatusList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="product_BASIC_WAREHOUSE" style="width: 100%; height: 25px;"
								OnKeyDown="nextFocus('product_SUBSID_MATL_MGMT')">
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
							<label class="col-sm-3 control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;부&nbsp;자&nbsp;재&nbsp;관&nbsp;리</label>
							<div class="col-sm-1">
								<input type="checkbox" class="form-control-plaintext" id="product_SUBSID_MATL_MGMT" checked
								OnKeyDown="nextFocus('product_USE_STATUS')">
							</div>
							<label class="col-sm-2 control-label">사&nbsp;용&nbsp;유&nbsp;무&nbsp;</label>
							<div class="col-sm-1">
								<input type="checkbox" class="form-control-plaintext" id="product_USE_STATUS" checked
								OnKeyDown="nextFocus('product_WRHSN_INSPC')">
							</div>
							<label class="col-sm-2 control-label">입&nbsp;고&nbsp;검&nbsp;사&nbsp;</label>
							<div class="col-sm-1">
								<input type="checkbox" class="form-control-plaintext" id="product_WRHSN_INSPC"
								OnKeyDown="nextFocus('product_SFTY_STOCK')">
							</div>
							<div class="col-sm-2"></div>
						</div>
					</fieldset>
					<br>
					<fieldset>
						<label class="col-sm-2 control-label">&nbsp;&nbsp;안&nbsp;전&nbsp;재&nbsp;고</label>
						<div class="col-sm-4">
							<input id="product_SFTY_STOCK" type="number"
								style="width: 80%; height: 25px;" value="0" autocomplete="off"
								OnKeyDown="nextFocus('product_BUYER')">
						</div>
						<label class="col-sm-2 control-label">&nbsp;&nbsp;구&nbsp;매&nbsp;담&nbsp;당</label>
						<div class="col-sm-4">
							<input type="text" style="width: 80%; height: 25px;"
								class="form-control-plaintext" id="product_BUYER"
								OnKeyDown="nextFocus('itemRegisterBtn')">
						</div>
					</fieldset>
					<br>
					<button type="button" class="btn btn-primary insert" id="itemRegisterBtn">저장</button>
					<button type="button" class="btn btn-primary copy" id="itemCopyBtn">저장</button>
					<button type="button" class="btn btn-primary modify" id="itemModifyBtn">수정</button>
					<button type="button" class="btn btn-warning modify" id="itemRemoveBtn">삭제</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>