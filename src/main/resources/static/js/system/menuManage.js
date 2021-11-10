var menuManageMasterTable = new Tabulator("#menuManageMasterTable",{
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "userManageRest/userManageRestSelect",
	rowClick: function(e, row) {			
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
		MMS_Search(row.getData().user_CODE)
		$('#MM_UpdateBtn').removeClass('unUseBtn');
    },
	columns: [
		{ title: "사용자 코드", field: "user_CODE", headerHozAlign: "center", headerFilter:"input"},
		{ title: "사용자 명",	field: "user_NAME",	headerHozAlign: "center", headerFilter:"input"},
		{ title: "부서", field: "dept_NAME",	headerHozAlign: "center", headerFilter:"input"},
		{ title: "타입", field: "user_TYPE_NAME", hozAlign: "right",	headerHozAlign: "center", headerFilter:"input"},
		{ title: "사용유무", field: "user_USE_STATUS", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center",
			formatter: "tickCross",	sorter:"boolean"}
	]
});

var menuManageSubTable = new Tabulator("#menuManageSubTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	selectable: true,
	height: "calc(100% - 175px)",
	columns: [
		{ formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign:"right"},
		{ title: "메뉴코드", field: "menu_Program_Code", visible:false},
		{ title: "메뉴명", field: "menu_Program_Name", hozAlign: "left", headerHozAlign: "center", headerFilter:"input" },
		{ title: "사용유무", field: "menu_MGMT_Use_Status", headerHozAlign: "center", hozAlign: "center",
			formatter: "tickCross", editor:'select', editorParams:{true : "사용", false : "미사용"}}
	]
});

function MMS_Search(userCode){
	menuManageSubTable.setData("menuManageRest/MMS_Search",{Menu_User_Code: userCode})
}

$('#MM_UpdateBtn').click(function(){
	var selectedRow	= menuManageSubTable.getData('selected');	
	if (selectedRow.length == 0) {
		alert("수정할 행을 선택한 후에 수정을 시도하여 주십시오.")
	}else{
		if(confirm("수정 하시겠습니까?")){
			MM_Update(selectedRow);
		}
	}	
})

function MM_Update(selectedData) {	
		
	$.ajax({
		method: "put",
		url: "menuManageRest/MM_Update",
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
				MMS_Search(menuManageMasterTable.getData('selected')[0].user_CODE)	
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}