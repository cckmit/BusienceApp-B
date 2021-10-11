var allMenuListTable = new Tabulator("#allMenuListTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	columns: [
		{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
		{ title: "메뉴코드", field: "menu_Code", visible:false},
		{ title: "그룹명", field: "menu_Parent_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:"input" },
		{ title: "프로그램명", field: "menu_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:"input" }
	]
});

var userMenuListTable = new Tabulator("#userMenuListTable", {
	height: "calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	placeholder:"사용자 전용 메뉴",
	columns: [
		{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
		{ title: "메뉴코드", field: "menu_Code", visible:false},
		{ title: "그룹명", field: "menu_Parent_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:"input" },
		{ title: "프로그램명", field: "menu_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:"input" }
	]
});

function UML_Search(){
	$.ajax({
		method: "GET",
		url: "userMenuManageRest/userMenuSearch",
		success: function(data) {
			var allMenuList = []
			var userMenuList = []
			
			for(let i=0;i<data.length;i++){
				if(data[i].user_Code == null){
					allMenuList.push(data[i]);
				}else{
					userMenuList.push(data[i])
				}
			}
			
			allMenuListTable.setData(allMenuList);
			userMenuListTable.setData(userMenuList);			
		}
	});
}

$("#UMM_ADDBtn").click(function(){
	var selectedRow = allMenuListTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("행을 선택한 후에 추가버튼을 눌러주세요.");
		return false;
	}
	
	$.ajax({
		method: "GET",
		url: "userMenuManageRest/userMenuInsert?data="+encodeURI(JSON.stringify(selectedRow)),
		success: function(data) {
			if(data == "success"){
				alert("저장되었습니다.");
				UML_Search();
			}
		}
	});
})

$("#UMM_DeleteBtn").click(function(){
	var selectedRow = userMenuListTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("행을 선택한 후에 삭제버튼을 눌러주세요.");
		return false;
	}
	
	$.ajax({
		method: "GET",
		url: "userMenuManageRest/userMenuDelete?data="+encodeURI(JSON.stringify(selectedRow)),
		success: function(data) {
			if(data == "success"){
				alert("삭제되었습니다.");
				UML_Search();
			}
		}
	});
})



$(document).ready(function(){
	UML_Search();
})