var customMenuAddReceiver = function(fromRow, toRow, toTable){
	if(confirm("메뉴를 추가하시겠습니까?")){
		UMM_ADD(fromRow.getData())
	}
}

var customMenuDeleteReceiver = function(fromRow, toRow, toTable){
	if(confirm("메뉴를 삭제하시겠습니까?")){
		UMM_Delete(fromRow.getData())		
	}
}

var allMenuListTable = new Tabulator("#allMenuListTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	groupBy: "menu_Parent_Name",
	groupStartOpen: false,
	height: "calc(100% - 175px)",
	ProgressiveLoad: "scroll",
	ajaxURL:"/userMenuManageRest/allMenuSearch",
    ajaxConfig:"get",
    ajaxContentType:"json",
	movableRows: true,
    movableRowsConnectedTables: "#userMenuListTable",
	movableRowsReceiver: customMenuDeleteReceiver,
	columns:[
	{title:"메뉴 목록", headerHozAlign:"center",
		columns: [
			{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
			{ title: "메뉴코드", field: "menu_Code", visible:false},
			{ title: "그룹명", field: "menu_Parent_Name", headerHozAlign: "center", hozAlign: "right", width: 90},
			{ title: "프로그램명", field: "menu_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:"input", width: 130}
		]
	}
	]
});

var userMenuListTable = new Tabulator("#userMenuListTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	groupBy: "menu_Parent_Name",
	groupStartOpen: true,
	height: "calc(100% - 175px)",
	ajaxURL:"/userMenuManageRest/userMenuSearch",
    ajaxConfig:"get",
    ajaxContentType:"json",
	movableRows: true,
    movableRowsConnectedTables: "#allMenuListTable",
	movableRowsReceiver: customMenuAddReceiver,
	columns:[
	{title:"사용자 메뉴 목록", headerHozAlign:"center",
		columns: [
			{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
			{ title: "메뉴코드", field: "menu_Code", visible:false},
			{ title: "그룹명", field: "menu_Parent_Name", headerHozAlign: "center", hozAlign: "right", width: 90},
			{ title: "프로그램명", field: "menu_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:"input", width: 130}
		]
	}
	]
});

function UMM_ADD(selectedData){
	$.ajax({
		method: "post",
		url: "/userMenuManageRest/userMenuInsert",
		data: selectedData,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function() {
			allMenuListTable.replaceData();
			userMenuListTable.replaceData();
		}
	});
}

function UMM_Delete(selectedData){
	$.ajax({
		method: "delete",
		url: "/userMenuManageRest/userMenuDelete",
		data: selectedData,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function() {
			allMenuListTable.replaceData();
			userMenuListTable.replaceData();
		}
	});
}
