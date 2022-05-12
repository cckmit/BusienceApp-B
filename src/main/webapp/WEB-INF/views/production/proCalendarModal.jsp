<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="monthRegisterModal" tabindex="-1"
	role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style="width: 350px; height: 150px;">
			<div class="modal-header" style="font-weight: bold;">
				<h4 class="modal-title" id="exampleModalLabel">검색 월 선택</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group row">
						<label class="col-sm-3 control-label"
							style="size: 14px; font-weight: bold;">월 선택 : </label> <input
							type="month" id="selectedMonth" class="this_month">&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" id="monthSearchBtn">검색</button>
						<!-- <button type="reset" class="btn btn-danger" data-dismiss="modal">취소</button> -->
					</div>
				</form>

			</div>
		</div>
	</div>
</div>

<style>
.modal {
	text-align: center;
}

@media screen and (min-width: 768px) {
	.modal:before {
		content: " ";
		height: 100%;
	}
}

.modal-dialog {
	display: inline-block;
	text-align: left;
}
</style>