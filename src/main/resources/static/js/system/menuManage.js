var table = new Tabulator("#example-table1",{
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
		$('#modifyinitbtn').removeClass('unUseBtn');
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


var table2 = new Tabulator("#example-table2", {
		layoutColumnsOnNewData : true,
		headerFilterPlaceholder: null,
		height: "calc(100% - 175px)",
		columns: [
			{ formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},
			{ title: "순번", field: "rownum", formatter: "rownum", hozAlign:"right"},
			{ title: "메뉴코드", field: "menu_PROGRAM_Code"},
			{ title: "메뉴명", field: "menu_PROGRAM_NAME", hozAlign: "left", headerHozAlign: "center", headerFilter:"input" },
			{ title: "읽기", field: "menu_READ_USE_STATUS", headerHozAlign: "center", hozAlign: "center",
				formatter: "tickCross",	sorter:"boolean", editor:true},
			{ title: "쓰기", field: "menu_WRITE_USE_STATUS",	headerHozAlign: "center", hozAlign: "center",
				formatter: "tickCross",	sorter:"boolean", editor:true},
			{ title: "삭제",	field: "menu_DEL_USE_STATUS", headerHozAlign: "center", hozAlign: "center",
				formatter: "tickCross",	sorter:"boolean", editor:true},
			{ title: "사용유무", field: "menu_MGMT_USE_STATU", headerHozAlign: "center", hozAlign: "center",
				formatter: "tickCross",	sorter:"boolean", editor:true}
		]
	});

function MMS_Search(user_CODE){
	table2.setData("menuManageRest/MMS_Search",{MENU_USER_CODE: user_CODE})
}


function modifyModalShow() {
	$("#modifyYesNo").modal("show");
}

function modBtn() {
	
	var selectedRow	= table2.getData('selected');
	
	if (selectedRow.length == 0) {
		alert("수정할 행을 선택한 후에 수정을 시도하여 주십시오.")
		return;
	}
	
	console.log(selectedRow);
	
	$.ajax({
		method: "get",
		url: "menuManageRest/MM_Update?data=" + encodeURI(JSON.stringify(selectedRow)),
		success: function(data) {
			
			alert("수정 완료 하였습니다.");
			TAS_Search(table.getData('selected')[0].user_CODE)
		}
	});
}
