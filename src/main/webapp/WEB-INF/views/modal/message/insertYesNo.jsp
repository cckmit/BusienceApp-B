<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="modal fade" id="insertYesNo" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalCenterTitle" aria-hidden="true"
	style="margin-top: 180px;">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">확인창</h5>
			</div>
			<div class="modal-body">
				<p>해당 데이터를 저장하시겠습니까?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					data-dismiss="modal" onclick="insBtn()">YES</button>
				<button type="button" class="btn btn-danger" data-dismiss="modal">NO</button>
			</div>
		</div>
	</div>
</div>