// 행을 더블클릭하여서 해당 행의 데이터를 저장했다가 화면에서 뿌려주는 변수
var user_CODE = null;
var user_NAME = null;
var user_TYPE_NAME = null;
var user_COMPANY_NAME = null;
var user_DEPT_NAME = null;
var user_MODIFY_D = null;
var user_MODIFIER = null;
var user_USE_STATUS = null;

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
		
		// 더블클릭할때 데이터를 저장
		user_CODE = row.getData().user_CODE;
		user_NAME = row.getData().user_NAME;
		user_TYPE_NAME = row.getData().user_TYPE_NAME;
		user_COMPANY_NAME = row.getData().company_NAME;
		user_DEPT_NAME = row.getData().dept_NAME;
		user_MODIFY_D = row.getData().user_MODIFY_D;
		user_MODIFIER = row.getData().user_MODIFIER;
		user_USE_STATUS = row.getData().user_USE_STATUS;
		
		document.getElementById("update_user_CODE").value = user_CODE;
		document.getElementById("update_user_NAME").value = user_NAME;
		
		var company = row.getData().company
		var companylast = String(company).charAt(company.length-1)-1;
		
		$("#update_user_COMPANY option:eq("+companylast+")").prop("selected", true);
		
		var user_TYPE = row.getData().user_TYPE
		var user_TYPElast = String(user_TYPE).charAt(user_TYPE.length-1)-1;
		
		$("#update_user_TYPE option:eq("+user_TYPElast+")").prop("selected", true);
		
		var dept_CODE = row.getData().dept_CODE
		var dept_CODElast = String(dept_CODE).charAt(dept_CODE.length-1);
		
		$("#update_user_DEPT option:eq("+dept_CODElast+")").prop("selected", true);
				
		if (row.getData().user_USE_STATUS == "true")
			document.getElementById("update_user_USE_STATUS").checked = true;
		else
			document.getElementById("update_user_USE_STATUS").checked = false;
	}, 
	rowDblClick: function(e, row) {
		// 더블클릭할때 데이터를 저장
		user_CODE = row.getData().user_CODE;
		user_NAME = row.getData().user_NAME;
		user_TYPE_NAME = row.getData().user_TYPE_NAME;
		user_COMPANY_NAME = row.getData().company_NAME;
		user_DEPT_NAME = row.getData().dept_NAME;
		user_MODIFY_D = row.getData().user_MODIFY_D;
		user_MODIFIER = row.getData().user_MODIFIER;
		user_USE_STATUS = row.getData().user_USE_STATUS;
		console.log(user_USE_STATUS);

		//행에 색변경		
		$("#userModifyModal").modal("show");
		
		document.getElementById("update_user_CODE").value = user_CODE;
		document.getElementById("update_user_NAME").value = user_NAME;
		
		var company = row.getData().company
		var companylast = String(company).charAt(company.length-1)-1;
		
		$("#update_user_COMPANY option:eq("+companylast+")").prop("selected", true);
		
		var user_TYPE = row.getData().user_TYPE
		var user_TYPElast = String(user_TYPE).charAt(user_TYPE.length-1)-1;
		
		$("#update_user_TYPE option:eq("+user_TYPElast+")").prop("selected", true);
		
		var dept_CODE = row.getData().dept_CODE
		var dept_CODElast = String(dept_CODE).charAt(dept_CODE.length-1);
		
		$("#update_user_DEPT option:eq("+dept_CODElast+")").prop("selected", true);
		
		if (row.getData().user_USE_STATUS == "true")
			document.getElementById("update_user_USE_STATUS").checked = true;
		else
			document.getElementById("update_user_USE_STATUS").checked = false;
			
		
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

function nextFocus(next) {
	if (event.keyCode == 13) {
		console.log(next);
		$('#' + next).focus();
	}
}

function modviewBtn() {
	console.log(user_CODE);
	console.log(user_COMPANY_NAME);

	// 수정,삭제 모달창에 더블클릭한 데이터를 렌더링함
	document.getElementById("update_user_CODE").value = user_CODE;
	document.getElementById("update_user_NAME").value = user_NAME;
	
	document.getElementById("update_user_COMPANY").options[3].selected = true;
	console.log(companywhy);
	document.getElementById("update_user_TYPE").value = user_TYPE_NAME;
	document.getElementById("update_user_DEPT").value = user_DEPT_NAME;
	
	if(user_USE_STATUS=="true")
		document.getElementById("update_user_USE_STATUS").checked = true;
	else
		document.getElementById("update_user_USE_STATUS").checked = false;
	
	var user_COMPANY_NAME = row.getData().user_COMPANY_NAME;
	console.log(user_COMPANY_NAME);
}

function modBtn() {
	datas = {
		USER_CODE : user_CODE,
		USER_NAME : document.getElementById("update_user_NAME").value,
		COMPANY: $("#update_user_COMPANY option:selected").val(),
		USER_TYPE: $("#update_user_TYPE option:selected").val(),
		USER_USE_STATUS : document.getElementById("update_user_USE_STATUS").checked,
		DEPT_CODE : $("#update_user_DEPT option:selected").val()
	};

	$.ajax({
		method : "POST",
		url : "userManageRest/userManageUpdate?data="
				+ encodeURI(JSON.stringify(datas)),
		success : function(data, testStatus) {
		}
	});

	location.reload();
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
				$("#userRegisterModal").find('form')[0].reset()
			}
		}
	});
}

function updateTestBtn() {
	
	var datas = {USER_CODE : $("#update_user_CODE").val(),
			USER_NAME : $("#update_user_NAME").val(),
			COMPANY : $("#update_user_COMPANY").val(),
			USER_USE_STATUS : "true",
			USER_TYPE : $("#update_user_TYPE").val(),
			DEPT_CODE : $("#update_user_DEPT").val()}
	console.log("수정");
	console.log(datas);
	$.ajax({
		method : "put",
		url : "userManageRest/userManageUpdateTest",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data == "Success") {
				alert("저장 되었습니다.");
				//$("#userRegisterModal").modal("hide");
				//$("#userRegisterModal").find('form')[0].reset()
			}
		}
	});
}

// 비번 초기화
function pwReset() {
	conf = confirm("초기화 하시겠습니까?");
	if (!conf)
		return;

	$.ajax({
		method: "POST",
		url: "userManageRest/userManagePW?update_user_CODE=" + document.getElementById("update_user_CODE").value,
		success: function(data, testStatus) {
			console.log(data);
			alert("초기화 성공 하였습니다.");
			location.reload();
		}
	});

}