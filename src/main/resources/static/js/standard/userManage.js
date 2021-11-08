function nextFocus(next) {
	if (event.keyCode == 13) {
		$('#' + next).focus();
	}
}

//입력 및 업데이트 할 리스트
var pickValue = ["user_CODE", "user_NAME", "company", "user_USE_STATUS", "user_TYPE", "dept_CODE"];

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
	}, 
	rowDblClick: function(e, row) {
		//모달창 띄움
		modifyModalShow();
	},
	rowSelected:function(row){
    	var jsonData = fromRowToJson(row, pickValue);
		modalInputBox(jsonData);
    },
	columns: [
		{title: "순번", field: "rownum", hozAlign: "center", headerHozAlign: "center", formatter:"rownum"},
		{title: "사용자코드", field: "user_CODE", headerHozAlign: "center", headerFilter: "input"},
		{title: "사용자명", field: "user_NAME", headerHozAlign: "center", headerFilter: "input"},
		{title: "사용자타입", field: "user_TYPE_NAME", headerHozAlign: "center", headerFilter: "input"},
		{title: "사업장", field: "company_NAME", headerHozAlign: "center", headerFilter: "input"},
		{title: "부서", field: "dept_NAME", headerHozAlign: "center", headerFilter: "input"},
		{title: "사용유무", field: "user_USE_STATUS", headerHozAlign: "center", hozAlign: "center",
			formatter: "tickCross", editorParams: {values: {"true": "사용", "false": "미사용"}},
			headerFilter: true,	headerFilterParams: {values: {"true": "true", "false": "false"}}},
		{title: "수정일자", field: "user_MODIFY_D", headerHozAlign: "center", headerFilter: "input", hozAlign: "right"},
		{title: "수정자", field: "user_MODIFIER", headerHozAlign: "center", headerFilter: "input"}
	]
});

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#userADDBtn").click(function() {
	userManageTable.deselectRow();
	registerModalShow()
});

function registerModalShow(){
	
	$('.modify').addClass('none');
	
	if ($('.insert').hasClass('none')) {
		$('.insert').removeClass('none');
	}	
	$("#userManageModal").find('form')[0].reset();
	$("#user_CODE").removeAttr('readonly');
	
	$("#userManageModal").modal("show").on("shown.bs.modal", function () {
		$("#user_CODE").focus();
	});
}

//모달창내 등록버튼
$("#userRegisterBtn").click(function(){
	if(confirm("등록 하시겠습니까?")){
		userRegister();
	}	
})

function userRegister() {
	
	var datas = {USER_CODE : $("#user_CODE").val(),
			USER_PASSWORD : '1234',
			USER_NAME : $("#user_NAME").val(),
			COMPANY : $("#company").val(),
			USER_USE_STATUS : "true",
			USER_TYPE : $("#user_TYPE").val(),
			DEPT_CODE : $("#dept_CODE").val(),
			USER_MODIFIER : $("#loginUser").val()}
	
	if (datas.USER_CODE.length < 4 || datas.USER_CODE.length > 10) {
		alert("아이디는 4글자 이상 10글자 이하만 입력할 수 있습니다.");
		return $("#user_CODE").focus();
	}
	
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
			if (data) {
				alert("저장 되었습니다.");
				userManageTable.replaceData();
				
				$("#userManageModal").modal("hide");
			}else{
				alert("중복된 아이디 입니다.");
			}
		}
	});
}

// update버튼을 클릭을 할때 모달창을 여는 이벤트
$("#userUpdateBtn").click(function() {
	var selectedRow = userManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("수정할 행을 선택하세요.");
	}else{
		modifyModalShow();
	}
	
});

function modifyModalShow(){	
	
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}
	$("#user_CODE").attr('readonly', 'readonly');
	
	$("#userManageModal").modal("show").on("shown.bs.modal", function () {
		$("#user_NAME").focus();
	});
}

//모달창내 수정버튼
$("#userModifyBtn").click(function(){
	if(confirm("수정 하시겠습니까?")){
		userModify();
	}
})

function userModify() {
		
	var datas = {USER_CODE : $("#user_CODE").val(),
			USER_NAME : $("#user_NAME").val(),
			COMPANY : $("#company").val(),
			USER_USE_STATUS : $("#user_USE_STATUS").is(":checked"),
			USER_TYPE : $("#user_TYPE").val(),
			DEPT_CODE : $("#dept_CODE").val()}

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
			if (data) {
				alert("저장 되었습니다.");
				userManageTable.replaceData();
				
				$("#userManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}

//모달창내 비밀번호 초기화 버튼
$("#user_PASSWORD").click(function(){
	if(confirm("초기화 하시겠습니까?")){
		pwReset();
	}
})

// 비번 초기화
function pwReset() {
	
	var datas = {USER_CODE : $("#user_CODE").val(),
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
			if(data){
				alert("초기화 되었습니다.");
				$("#userManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}