var child_TBL_NO = 0;
var new_TBL_CODE = 0;
var child_TBL_NUM = 0;

var codeManageMasterTable = new Tabulator("#codeManageMasterTable", {
	headerFilterPlaceholder: null,
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
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
	rowDblClick: function(e, row) {
		modifyModalShow();
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
		$("#CHILD_TBL_TYPE").focus();
	});
}


//모달창내 등록버튼
$("#codeRegisterBtn").click(function(){
	codeRegister();
})

function codeRegister() {
	
	var datas = {
			NEW_TBL_CODE : codeManageMasterTable.getData("selected")[0].new_TBL_CODE,
			CHILD_TBL_TYPE : $("#CHILD_TBL_TYPE").val(),
			CHILD_TBL_RMARK : $("#CHILD_TBL_RMARK").val(),
			CHILD_TBL_USE_STATUS : $("#CHILD_TBL_USE_STATUS").is(":checked")}
	
	$.ajax({
		method : "POST",
		url : "codeManageRest/insert",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				codeManageMasterTable.replaceData();
				
				$("#codeManageModal").modal("hide");
			}
		}
	});
}

function modifyModalShow(){
	var selectedRow = codeManageSubTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("수정할 행을 선택하세요.");
		return false;
	}
	
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}
	
	$("#codeManageModal").modal("show").on("shown.bs.modal", function () {
		$("#CHILD_TBL_TYPE").focus();
	});
}

//모달창내 수정버튼
$("#codeModifyBtn").click(function(){
	codeModify();
})

function codeModify() {
	
	var datas = {
			CHILD_TBL_NO : codeManageSubTable.getData("selected")[0].child_TBL_NO,
			CHILD_TBL_TYPE : $("#CHILD_TBL_TYPE").val(),
			CHILD_TBL_RMARK : $("#CHILD_TBL_RMARK").val(),
			CHILD_TBL_USE_STATUS : $("#CHILD_TBL_USE_STATUS").is(":checked")}

	$.ajax({
		method : "put",
		url : "codeManageRest/update",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("저장 되었습니다.");
				codeManageMasterTable.replaceData();
				
				$("#codeManageModal").modal("hide");
			}
		}
	});
}