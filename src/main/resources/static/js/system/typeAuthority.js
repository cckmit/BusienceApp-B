var typeAuthorityMasterTable = new Tabulator("#typeAuthorityMasterTable",{
	layoutColumnsOnNewData : true,
	height: "calc(100% - 175px)",
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "typeAuthorityRest/TAM_Search",
	//행클릭 이벤트
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
    },
	rowSelected:function(row){
		TAS_Search(row.getData().child_TBL_NO)
		$('#modifyinitbtn').removeClass('unUseBtn');
    },
	columns: [ //Define Table Columns
		{ title: "번호", field: "child_TBL_NO", visible:false},
		{ title: "사용자타입", field: "child_TBL_NUM", hozAlign: "right"},
		{ title: "타입명", field: "child_TBL_TYPE"}
	]
});

var typeAuthoritySubTable = new Tabulator("#typeAuthoritySubTable", {
	layoutColumnsOnNewData : true,
	selectable: true,
	height: "calc(100% - 175px)",
	columns: [
		{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
		{ title: "코드", field: "rights_PROGRAM_CODE", hozAlign: "right"},
		{ title: "프로그램 명", field: "child_TBL_TYPE"},
		{ title: "사용유무", field: "rights_MGMT_USE_STATUS", headerHozAlign: "center", hozAlign: "center", formatter: "tickCross",
			editor: "select", editorParams: { values: { "true": "사용", "false": "미사용" }}}
	]
});


function TAS_Search(value){
	typeAuthoritySubTable.setData("typeAuthorityRest/TAS_Search",{RIGHTS_USER_TYPE: value})
}

function modifyModalShow() {
	$("#modifyYesNo").modal("show");
}

$('#modifyinitbtn').click(function(){
	modifyModalShow();
})

function modBtn() {
	var selectedRow	= typeAuthoritySubTable.getData('selected');
	if (selectedRow.length == 0) {
		alert("수정할 행을 선택한 후에 수정을 시도하여 주십시오.")
		return;
	}
	
	console.log(selectedRow);
	
	$.ajax({
		method: "get",
		url: "typeAuthorityRest/TA_Update?data=" + encodeURI(JSON.stringify(selectedRow)),
		success: function(data) {
			console.log(data);
			alert("수정 완료 하였습니다.");
			TAS_Search(typeAuthorityMasterTable.getData('selected')[0].child_TBL_NO)
		}
	});
}