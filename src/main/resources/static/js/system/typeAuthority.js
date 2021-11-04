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
		$('#TA_UpdateBtn').removeClass('unUseBtn');
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
		{ title: "순번", field: "rownum", formatter:"rownum", hozAlign: "right"},
		{ title: "프로그램 명", field: "rights_Program_Name"},
		{ title: "사용유무", field: "rights_MGMT_Use_Status", hozAlign: "center", formatter: "tickCross", editor:'select', editorParams:{true : "사용", false : "미사용"}}
	]
});


function TAS_Search(value){
	typeAuthoritySubTable.setData("typeAuthorityRest/TAS_Search",{userType: value})
}

$('#TA_UpdateBtn').click(function(){
	var selectedRow	= typeAuthoritySubTable.getData('selected');
	if (selectedRow.length == 0) {
		alert("수정할 행을 선택한 후에 수정을 시도하여 주십시오.");
	} else{
		if(confirm("수정 하시겠습니까?")){
			authorityModify(selectedRow);
		}
	}
})

function authorityModify(selectedData) {
	
	$.ajax({
		method: "put",
		url: "typeAuthorityRest/TA_Update",
		data: JSON.stringify(selectedData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			if(data){
				alert("수정 되었습니다.");
				TAS_Search(typeAuthorityMasterTable.getData('selected')[0].child_TBL_NO)	
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});	
}