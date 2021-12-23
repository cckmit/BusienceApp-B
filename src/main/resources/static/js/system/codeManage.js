//입력 및 업데이트 할 리스트
var pickValue = ["child_TBL_TYPE", "child_TBL_RMARK", "child_TBL_USE_STATUS"];

var codeManageMasterTable = new Tabulator("#codeManageMasterTable", {
	layoutColumnsOnNewData : true,
	height: "calc(100% - 175px)",
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "codeManageRest/CMM_Search",
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
		CMS_Search(row.getData().new_TBL_CODE)
		$('#CM_ADDbtn').removeClass('unUseBtn');
    },
	columns: [
		{ title: "공통코드", field: "new_TBL_CODE", headerHozAlign: "center", hozAlign: "right" },
		{ title: "공통코드 명", field: "new_TBL_NAME", headerHozAlign: "center" },
		{ title: "비고", field: "new_TBL_INDEX", headerHozAlign: "center"}
	],
});

var codeManageSubTable = new Tabulator("#codeManageSubTable", {
	layoutColumnsOnNewData : true,
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	rowDblClick: function(e, row) {
		modifyModalShow();
	},
	rowSelected:function(row){
		var jsonData = fromRowToJson(row, pickValue);
		modalInputBox(jsonData);
    },
	//페이징
	columns: [
		{ title: "순번", field: "child_TBL_NUM", headerHozAlign: "center", hozAlign: "right" },
		{ title: "타입명", field: "child_TBL_TYPE", headerHozAlign: "center" },
		{ title: "비고", field: "child_TBL_RMARK", headerHozAlign: "center"},
		{ title: "사용유무", field: "child_TBL_USE_STATUS", headerHozAlign: "center",	hozAlign: "center", formatter: "tickCross"}
	]
});

function CMS_Search(dtlCode){
	codeManageSubTable.setData("codeManageRest/CMS_Search",{NEW_TBL_CODE: dtlCode})
}


$('#CM_ADDbtn').click(function(){
	registerModalShow();
})

function registerModalShow(){
	
	$('.modify').addClass('none');
	
	if ($('.insert').hasClass('none')) {
		$('.insert').removeClass('none');
	}	
	$("#codeManageModal").find('form')[0].reset();
	
	$("#codeManageModal").modal("show").on("shown.bs.modal", function () {
		$("#child_TBL_TYPE").focus();
	});
}

//모달창내 등록버튼
$("#codeRegisterBtn").click(function(){
	if(confirm("저장 하시겠습니까?")){
		codeRegister();
	}	
})

function codeRegister() {
	
	var selectedRow = codeManageMasterTable.getData("selected")
	
	var datas = {
			NEW_TBL_CODE : selectedRow[0].new_TBL_CODE,
			CHILD_TBL_TYPE : $("#child_TBL_TYPE").val(),
			CHILD_TBL_RMARK : $("#child_TBL_RMARK").val(),
			CHILD_TBL_USE_STATUS : $("#child_TBL_USE_STATUS").is(":checked")}
	
	$.ajax({
		method : "POST",
		url : "codeManageRest/codeManageInsert",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				CMS_Search(selectedRow[0].new_TBL_CODE)
				
				$("#codeManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}

function modifyModalShow(){
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}
	
	$("#codeManageModal").modal("show").on("shown.bs.modal", function () {
		$("#child_TBL_TYPE").focus();
	});
}

//모달창내 수정버튼
$("#codeModifyBtn").click(function(){
	if(confirm("수정 하시겠습니까?")){
		codeModify();
	}
})

function codeModify() {
	
	var selectedRow = codeManageSubTable.getData("selected")
	
	var datas = {
			CHILD_TBL_NO : selectedRow[0].child_TBL_NO,
			CHILD_TBL_TYPE : $("#child_TBL_TYPE").val(),
			CHILD_TBL_RMARK : $("#child_TBL_RMARK").val(),
			CHILD_TBL_USE_STATUS : $("#child_TBL_USE_STATUS").is(":checked")}

	$.ajax({
		method : "put",
		url : "codeManageRest/codeManageUpdate",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("수정 되었습니다.");
				CMS_Search(selectedRow[0].new_TBL_CODE)
				
				$("#codeManageModal").modal("hide");
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}