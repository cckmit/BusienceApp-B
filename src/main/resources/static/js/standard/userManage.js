var pickValue = new Array();
var jsonData = new Object();

var userManageTable = new Tabulator("#userManageTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData : true,
	height: "calc(100% - 175px)",
	ajaxURL:"userManageRest/userManageRestSelect",
    ajaxConfig:"get",
    ajaxContentType:"json",
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		modalInputBox(row.getData());
	}, 
	rowDblClick: function(e, row) {
		//모달창 띄움
		$("#userModifyModal").modal("show");
	},
	columns: [
		{title: "순번", field: "rownum", hozAlign: "center", headerHozAlign: "center", formatter:"rownum"},
		{title: "사용자코드", field: "user_CODE", headerHozAlign: "center", headerFilter: "input"},
		{title: "사용자명", field: "user_NAME", headerHozAlign: "center", headerFilter: "input"},
		{title: "사용자타입", field: "user_TYPE_NAME", headerHozAlign: "center", headerFilter: "input"},
		{title: "사업장", field: "company_NAME", headerHozAlign: "center", headerFilter: "input"},
		{title: "부서", field: "dept_NAME", headerHozAlign: "center", headerFilter: "input"},
		{title: "사용유무", field: "user_USE_STATUS", headerHozAlign: "center", hozAlign: "center",
			formatter: "tickCross", editor: "text", editorParams: {values: {"true": "사용", "false": "미사용"}},
			headerFilter: true,	headerFilterParams: {values: {"true": "true", "false": "false"}}},
		{title: "수정일자", field: "user_MODIFY_D", headerHozAlign: "center", headerFilter: "input", hozAlign: "right"},
		{title: "수정자", field: "user_MODIFIER", headerHozAlign: "center", headerFilter: "input"}
	]
});

function fromRowToJson(row){
	pickValue = ["user_CODE", "user_NAME", "company", "user_USE_STATUS", "user_TYPE", "dept_CODE"];
	
	pickValue.forEach(function(item,index,arr2){
	})
}

function modalInputBox(jsonData){

	for (var key in jsonData){
		console.log(key);
		console.log(jsonData[key]);
		if($('#'+key)){
			$('#'+key).val(jsonData[key]);
		}
	}
}
function nextFocus(next) {
	if (event.keyCode == 13) {
		console.log(next);
		$('#' + next).focus();
	}
}

// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
$("#registerModal").click(function() {
	$("#userRegisterModal").modal("show").on("shown.bs.modal", function () {
		$("#insert_user_CODE").focus();
	});
});

// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
$("#modifyModal").click(function() {
	$("#userModifyModal").modal("show").on("shown.bs.modal", function () {
		$("#update_user_NAME").focus();
	});
});

// 저장할지 말지 여부를 묻는 모달창 열기
function insertModal()
{
	console.log("test");
	$("#insertYesNo").modal("show");	
}

function insBtn() {
	var userCode = $("#insert_user_CODE").val();
	
	if (!(userCode.length > 0)) {
		alert("사용자코드를 입력해주세요.");
		return $("#insert_user_CODE").focus();
	}
	if (userCode.length >= 10) {
		alert("사용자코드를 10글자 이하로 입력해주세요.");
		return $("#insert_user_CODE").focus();
	}
	var datas = {USER_CODE : $("#insert_user_CODE").val(),
			USER_PASSWORD : '1234',
			USER_NAME : $("#insert_user_NAME").val(),
			COMPANY : $("#insert_user_COMPANY").val(),
			USER_USE_STATUS : "true",
			USER_TYPE : $("#insert_user_TYPE").val(),
			DEPT_CODE : $("#insert_user_DEPT").val(),
			USER_MODIFIER : $("#loginUser").val()}

	$.ajax({
		method : "POST",
		url : "userManageRest/userManageInsert",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data == "Success") {
				alert("저장 되었습니다.");
				$("#userRegisterModal").modal("hide");
				$("#userRegisterModal").find('form')[0].reset();
			}
		}
	});
}

function modBtn() {
	
	var datas = {USER_CODE : $("#update_user_CODE").val(),
			USER_NAME : $("#update_user_NAME").val(),
			COMPANY : $("#update_user_COMPANY").val(),
			USER_USE_STATUS : "true",
			USER_TYPE : $("#update_user_TYPE").val(),
			DEPT_CODE : $("#update_user_DEPT").val()}

	$.ajax({
		method : "put",
		url : "userManageRest/userManageUpdate",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data == "Success") {
				alert("저장 되었습니다.");
				$("#userModifyModal").modal("hide");
				$("#userModifyModal").find('form')[0].reset();
			}
		}
	});
}

// 비번 초기화
function pwReset() {
	
	if (confirm("초기화 하시겠습니까?")){
		var datas = {USER_CODE : $("#update_user_CODE").val(),
					USER_PASSWORD : "1234"}

		$.ajax({
			method: "put",
			url: "userManageRest/userManagePW",
			data : datas,
			beforeSend: function (xhr) {
	           var header = $("meta[name='_csrf_header']").attr("content");
	           var token = $("meta[name='_csrf']").attr("content");
	           xhr.setRequestHeader(header, token);
			},
			success: function(data) {
				alert("초기화 되었습니다.");
				$("#userModifyModal").modal("hide");
				$("#userModifyModal").find('form')[0].reset();
			}
		});
	}
}