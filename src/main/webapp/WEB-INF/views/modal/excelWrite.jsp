<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Update Modal -->
<div class="modal fade" id="excelWriteViewModal" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="exampleModalLabel">
					<!-- <button id="download-xlsx">Download XLSX</button> -->
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group row">
						<div id="excelViewerTable"></div>
					</div>

					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modifyYesNo" id="updateModalInitbtn">수정</button>
					<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#deleteYesNo">삭제</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="modResetBtn()">취소</button>
				</form>
			</div>
		</div>
	</div>
</div>
