<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="/images/button/Search.png"
					id="spareTypeSelectBtn" onclick="spareTypeSelect()" />
			</div>
			<div class="input-button">
				<img id="spareTypeUpdateBtn"
					src="/images/button/Update.png"
					onclick="spareTypeUpdate()" />
			</div>

			<div class="input-box">
					<span><strong>품목코드</strong></span>
					<input id="PRODUCT_ITEM_CODE1" class="Item_Code1" type="text" disabled>
					<span><strong>품목명</strong></span>
					<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
			</div>
		</div>
		<div id="spareTypeManageTable"></div>

	</div>

	<!-- END MAIN -->
</div>
<!-- Insert Modal -->
<div class="modal fade" id="spareImginsertModal" tabindex="-1"
	role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"
	style="line-height: 17px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3>사진 업로드</h3>
				<%-- <img src="${contextPath}/resources/assets/img/inputHeader.jpg"
					alt="" style="width: 40%; height: auto;" loading="lazy"
					style="position: relative;"> --%>
			</div>
			<div class="modal-body">
				<form id="formSubmit" action="spareTypeRest/spareImgUpload"
					method="post" enctype="multipart/form-data">
					<fieldset>
						<div
							style="height: 250px; width: 100%; border: 3px dotted #6600ff;">
							<br> <br>
							<div id="img-box">
								<img src="/images/button/Upload.png"
									alt=""
									style="width: 70px; height: 70px; margin-left: 230px; margin-top: 40px;"
									loading="lazy" style="position: relative;">
							</div>
							<br>
							<div id="image" class="col-sm-9" style="margin-left: 190px;">
								<p>사진을 선택하세요</p>
								첨부파일명 :
								<div id="holder"></div>
								<input type="file" name="uploadFile" id="uploadFile"
									style="display: none;" onchange="loadFile(this)">
							</div>
						</div>
					</fieldset>
					<br> <br>
					<button type="button" class="btn btn-primary"
						id="insertModalInitbtn">저장</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="insResetBtn()">취소</button>
					<!-- <button class="btn btn-info">Save Changes</button>
					<button class="btn btn-link">Cancel</button> -->
				</form>
			</div>
		</div>
	</div>
</div>
<!-- update, delete Modal -->
<div class="modal fade" id="spareImgUpdateModal" tabindex="-1"
	role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"
	style="line-height: 17px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3>사진 수정·삭제</h3>
				<%-- <img src="${contextPath}/resources/assets/img/inputHeader.jpg"
					alt="" style="width: 40%; height: auto;" loading="lazy"
					style="position: relative;"> --%>
			</div>
			<div class="modal-body">
				<form id="updateformSubmit" action="spareTypeRest/spareImgUpdate"
					method="post" enctype="multipart/form-data">
					<fieldset>
						<div
							style="height: 250px; width: 100%; border: 3px dotted #6600ff;">
							<br> <br>
							<div id="img-box">
								<img src="/images/button/Upload.png"
									alt=""
									style="width: 70px; height: 70px; margin-left: 230px; margin-top: 40px;"
									loading="lazy" style="position: relative;">
							</div>
							<br>
							<div id="image" class="col-sm-9" style="margin-left: 190px;">
								<p>사진을 선택하세요</p>
								첨부파일명 :
								<div id="holder"></div>
								<input type="file" name="updateFile" id="updateFile"
									style="display: none;" onchange="loadFile(this)">
								${machineFile.Mfile_Save_Path}
							</div>
						</div>
					</fieldset>
					<br> <br>
					<button type="button" class="btn btn-primary"
						id="updateModalInitbtn">수정</button>
					<button type="button" class="btn btn-primary"
						id="deleteModalInitbtn">삭제</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="insResetBtn()">취소</button>
					<!-- <button class="btn btn-info">Save Changes</button>
					<button class="btn btn-link">Cancel</button> -->
				</form>
			</div>
		</div>
	</div>
</div>
<!-- 프린트페이지에 보낼 데이터 -->
<input type="hidden" id="comProducer" name="comProducer">
<input type="hidden" id="comModel" name="comModel" value="">
<input type="hidden" id="comMachine" name="comMachine" value="">
<input type="hidden" id="comUse_Date" name="comUse_Date" value="">
<input type="hidden" id="comUse_Status" name="comUse_Status" value="">
<input type="hidden" id="comPicture" name="comPicture" value="">
<input type="hidden" id="comInfo_Remark" name="comInfo_Remark" value="">
<!-- END WRAPPER -->
<script src="/js/standard/spareTypeManage.js"></script>
