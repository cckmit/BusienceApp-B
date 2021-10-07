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
    	var jsonData = fromRowToJson(row);
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

function fromRowToJson(row){
	var pickValue = ["user_CODE", "user_NAME", "company", "user_USE_STATUS", "user_TYPE", "dept_CODE"];
	var jsonData = new Object();
	pickValue.forEach(function(item,index,arr2){
		jsonData[item] = row.getData()[item]
	})
	return jsonData;
}

function nextFocus(next) {
	if (event.keyCode == 13) {
		$('#' + next).focus();
	}
}

// ADD버튼을 클릭을 할때 모달창을 여는 이벤트
$("#userAddBtn").click(function() {
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

$("#userRegisterBtn").click(function(){
	userRegister();
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
			if (data == "Success") {
				alert("저장 되었습니다.");
				userManageTable.replaceData();
				
				$("#userManageModal").modal("hide");
			}
		}
	});
}

// update버튼을 클릭을 할때 모달창을 여는 이벤트
$("#userUpdateBtn").click(function() {
	modifyModalShow();
});

function modifyModalShow(){
	var selectedRow = userManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("수정할 행을 선택하세요.");
		return false;
	}
	
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}
	$("#user_CODE").attr('readonly', 'readonly');
	
	$("#userManageModal").modal("show").on("shown.bs.modal", function () {
		$("#user_NAME").focus();
	});
}

$("#userModifyBtn").click(function(){
	userModify();
})

function userModify() {
		
	var datas = {USER_CODE : $("#user_CODE").val(),
			USER_NAME : $("#user_NAME").val(),
			COMPANY : $("#company").val(),
			USER_USE_STATUS : "true",
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
			if (data == "Success") {
				alert("저장 되었습니다.");
				userManageTable.replaceData();
				
				$("#userManageModal").modal("hide");
			}
		}
	});
}

// 비번 초기화
function pwReset() {
	
	if (confirm("초기화 하시겠습니까?")){
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
				alert("초기화 되었습니다.");
				$("#userManageModal").modal("hide");
			}
		});
	}
}