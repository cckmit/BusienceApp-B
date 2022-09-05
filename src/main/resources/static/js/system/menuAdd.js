$("#MA_ADDbtn").click(function(){
	menuAddTable.addRow({menu_Use_Status:1});
})

//기존행은 포커스가 안생김
var editCheck = function(cell){
    //cell - the cell component for the editable cell
    //get row data
    var data = cell.getRow().getData();
    return data.menu_Code == null;
}

var menuAddTable = new Tabulator("#menuAddTable", {
	layoutColumnsOnNewData : true,
	height: "calc(100% - 175px)",
	groupBy: "menu_Parent_Name",
	groupStartOpen: false,
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "menuAddRest/MA_Search",
	//행추가시 기능
	rowAdded : function ( row ) {
	row.getTable().deselectRow();
	row.select();
	menuAddTable.scrollToRow(row, "nearest", false)
	.then(function(){
			//행이 추가되면 첫셀에 포커스
			do{
			setTimeout(function(){
				row.getCell("menu_Parent_No").edit();
				},100);
			}
			while(row.getData().menu_Code === "menu_Parent_No");
		})
	},
	columns: [
		{ title: "메뉴코드", field: "menu_Code"},
		{ title: "상위메뉴코드", field: "menu_Parent_No", editor: "input", editable:editCheck},
		{ title: "상위메뉴이름", field: "menu_Parent_Name"},
		{ title: "하위메뉴코드", field: "menu_Child_No", editor: "input", editable:editCheck},
		{ title: "하위메뉴이름", field: "menu_Name", editor: "input", editable:editCheck},
		{ title: "페이지명", field: "menu_PageName", editor: "input", editable:editCheck},
		{ title: "사용유무", field: "menu_Use_Status", editor: "select", editorParams: {values:[0,1]} , editable:editCheck}
	],
});

$("#MA_Savebtn").click(function(){
	MA_Save()
})

function MA_Save() {
	
	var selectedRow = menuAddTable.getData("selected")
	if(selectedRow[0].menu_Parent_No.length != 2 || selectedRow[0].menu_Child_No.length != 2){
		alert("상위, 하위 메뉴코드는 2자로 입력하세요.")
		return;
	}
	$.ajax({
		method : "post",
		url : "menuAddRest/MA_Save",
		data : selectedRow[0],
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if (data) {
				alert("추가 되었습니다.");
				menuAddTable.replaceData();
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}