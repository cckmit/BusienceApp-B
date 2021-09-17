<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%
	String sql = "select * from MENU_MGMT_TBL where MENU_USER_CODE = 'admin' and MENU_PROGRAM_CODE = '13211'";

Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://busience2.cafe24.com:3306/busience2", "busience2",
		"business12!!");
PreparedStatement pstmt = con.prepareStatement(sql);
ResultSet rs = pstmt.executeQuery(sql);

boolean MENU_WRITE_USE_STATUS = false;
boolean MENU_DEL_USE_STATUS = false;

while (rs.next()) {
	if (rs.getString("MENU_WRITE_USE_STATUS").equals("true"))
		MENU_WRITE_USE_STATUS = true;

	if (rs.getString("MENU_DEL_USE_STATUS").equals("true"))
		MENU_DEL_USE_STATUS = true;
}
%>

<!-- Insert Modal -->
<div class="modal fade" id="insertModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true" style="line-height: 17px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<img src="/images/modal/inputHeader.jpg"
					alt="" style="width: 40%; height: auto;" loading="lazy"
					style="position: relative;">
			</div>
			<div class="modal-body">
				<form>
					<fieldset>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;사&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;업&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;장</label>
							<div class="col-sm-9">
								<select id="insert_item_COMPANY"
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
								<input type="text" class="form-control-plaintext"
									id="insert_item_CODE" style="width: 50%; height: 25px;"
									placeholder="품목코드 입력"
									onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_olditem_CODE').focus()}">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;구&nbsp;품&nbsp;목&nbsp;코&nbsp;드</label>
							<div class="col-sm-9">
								<input type="text" style="width: 50%; height: 25px;"
									class="form-control-plaintext" id="insert_olditem_CODE"
									placeholder="구품목코드 입력"
									onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_item_NAME').focus()}">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;품&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</label>
							<div class="col-sm-9">
								<input type="text" style="width: 70%; height: 25px;"
									class="form-control-plaintext" id="insert_item_NAME"
									placeholder="품명 입력"
									onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_item_STND1').focus()}">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1</label>
							<div class="col-sm-9">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="insert_item_STND1"
									placeholder="규격1 입력"
									onkeypress="javascript:if(event.keyCode==13) {document.getElementById('insert_item_STND2').focus()}">
							</div>
						</div>
						<br> <br>
						<div>
							<label class="col-sm-3 control-label">&nbsp;&nbsp;규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2</label>
							<div class="col-sm-9">
								<input type="text" style="width: 80%; height: 25px;"
									class="form-control-plaintext" id="insert_item_STND2"
									placeholder="규격2 입력" OnKeyDown="nextFocus('insert_item_UNIT')">
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
								<select id="insert_item_UNIT" style="width: 80%; height: 25px;"
									OnKeyDown="nextFocus('insert_item_MAT')">
									<c:forEach var="data" items="${unitList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<select id="insert_item_MAT" style="width: 80%; height: 25px;"
									OnKeyDown="nextFocus('insert_item_mat_CLSFC')">
									<c:forEach var="data" items="${materialList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<select id="insert_item_mat_CLSFC"
									style="width: 80%; height: 27px;"
									OnKeyDown="nextFocus('insert_item_CLSFC1')">
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
								<select id="insert_item_CLSFC1"
									OnKeyDown="nextFocus('insert_item_CLSFC2')"
									style="width: 100%; height: 25px;">
									<c:forEach var="data" items="${itemClsfc1List}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="insert_item_CLSFC2"
									OnKeyDown="nextFocus('insert_item_STATUS')"
									style="width: 100%; height: 25px;">
									<c:forEach var="data" items="${itemClsfc2List}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="insert_item_STATUS"
									OnKeyDown="nextFocus('insert_item_WAREHOUSE')"
									style="width: 100%; height: 25px;">
									<c:forEach var="data" items="${itemStatusList}">
										<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select id="insert_item_WAREHOUSE"
									OnKeyDown="nextFocus('insert_item_subsid_mat_MGMT')"
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
									id="insert_item_subsid_mat_MGMT"
									OnKeyDown="nextFocus('insert_item_USE_STATUS')">
							</div>
							<label class="col-sm-2 control-label">사&nbsp;용&nbsp;유&nbsp;무&nbsp;</label>
							<div class="col-sm-1">
								<input type="checkbox" class="form-control-plaintext"
									id="insert_item_USE_STATUS"
									OnKeyDown="nextFocus('insert_item_WRHSN')">
							</div>
							<label class="col-sm-2 control-label">입&nbsp;고&nbsp;검&nbsp;사&nbsp;</label>
							<div class="col-sm-1">
								<input type="checkbox" class="form-control-plaintext"
									id="insert_item_WRHSN"
									OnKeyDown="nextFocus('insert_item_min_ORDERS')">
							</div>
							<div class="col-sm-2"></div>
						</div>
					</fieldset>
					<br>
					<fieldset>
						<label class="col-sm-3 control-label">&nbsp;&nbsp;안&nbsp;&nbsp;&nbsp;전&nbsp;&nbsp;&nbsp;재&nbsp;&nbsp;&nbsp;고</label>
						<div class="col-sm-3">
							<input id="insert_item_stfy_STOCK" type="number"
								OnKeyDown="nextFocus('insert_item_BUYER')"
								style="width: 80%; height: 25px;" autocomplete="off">
						</div>
						<label class="col-sm-3 control-label">&nbsp;&nbsp;구&nbsp;&nbsp;&nbsp;매&nbsp;&nbsp;&nbsp;담&nbsp;&nbsp;&nbsp;당</label>
						<div class="col-sm-3">
							<input type="text" style="width: 110%; height: 25px;"
								class="form-control-plaintext" id="insert_item_BUYER"
								placeholder="담당자명 입력"
								OnKeyDown="nextFocus('insertModalInitbtn')">
						</div>
					</fieldset>
					<br>
					<button type="button" class="btn btn-primary"
						id="insertModalInitbtn" onclick="insertModal()">저장</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="insResetBtn()">취소</button>
					<!-- <button class="btn btn-info">Save Changes</button>
						<button class="btn btn-link">Cancel</button> -->
				</form>
			</div>
		</div>
	</div>
</div>

<!-- Update Modal -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
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

					<%
						if (MENU_WRITE_USE_STATUS) {
					%>
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#modifyYesNo" id="updateModalInitbtn">수정</button>
					<%
						} else {
					%>
					<button type="button" class="btn btn-primary" data-toggle="modal"
						id="updateModalInitbtn" data-target="#modifyYesNo"
						disabled="disabled">수정</button>
					<%
						}
					%>

					<%
						if (MENU_DEL_USE_STATUS) {
					%>
					<button type="button" class="btn btn-warning" data-toggle="modal"
						data-target="#deleteYesNo">삭제</button>
					<%
						} else {
					%>
					<button type="button" class="btn btn-warning" data-toggle="modal"
						data-target="#deleteYesNo" disabled="disabled">삭제</button>
					<%
						}
					%>


					<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- Delete Modal -->
<jsp:include page="../modal/message/deleteYesNo.jsp"></jsp:include>

<!-- Modify Modal -->
<jsp:include page="../modal/message/modifyYesNo.jsp"></jsp:include>

<!-- Insert Modal -->
<jsp:include page="../modal/message/insertYesNo.jsp"></jsp:include>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<div class="input-button">
				<!-- data-toggle="modal" data-target="#insertModal" -->
				<%
					if (MENU_WRITE_USE_STATUS) {
				%>
				<img id="custom-btn-default"
					src="/images/button/ADD.png"/>
					<img
						src="/images/button/Update.png" onclick="updatedeleteView()" />
				<%
					} else {
				%>
				<img id="custom-btn-defaultd"
					src="/images/button/ADD.png"
					style="opacity: 0.5;" />
					<img
						src="/images/button/Update.png"/>
				<%
					}
				%>
				
				<%
					if (MENU_DEL_USE_STATUS) {
				%>
				<img src="/images/button/Delete.png" onclick="updatedeleteView()" />
				<%
					} else {
				%>
				<img src="/images/button/Delete.png" style="opacity: 0.5;" />
				<%
					}
				%>

			</div>
		</div>

		<div id="itemManageTable"></div>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/standard/itemManage.js"></script>