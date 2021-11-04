var allMenuListTable = new Tabulator("#allMenuListTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	selectable: true,
	height: "calc(100% - 175px)",
	columns: [
		{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
		{ title: "메뉴코드", field: "menu_Code", visible:false},
		{ title: "그룹명", field: "menu_Parent_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:"input" },
		{ title: "프로그램명", field: "menu_Name", headerHozAlign: "center", hozAlign: "right", headerFilter:"input" }
	]
});

var userMenuListTable = new Tabulator("#userMenuListTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	placeholder: "사용자 전용 메뉴",
	selectable: true,
	height: "calc(100% - 175px)",
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
	}else{
		if(confirm("추가 하시겠습니까?")){
			UMM_ADD(selectedRow);
		}
	}
})

function UMM_ADD(selectedData){
	$.ajax({
		method: "post",
		url: "userMenuManageRest/userMenuInsert",
		data: JSON.stringify(selectedData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			if(data){
				alert("저장되었습니다.");
				UML_Search();
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}

$("#UMM_DeleteBtn").click(function(){
	var selectedRow = userMenuListTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("행을 선택한 후에 삭제버튼을 눌러주세요.");
	}else{
		if(confirm("삭제 하시겠습니까?")){
			UMM_Delete(selectedRow);
		}
	}	
})

function UMM_Delete(selectedData){
	$.ajax({
		method: "delete",
		url: "userMenuManageRest/userMenuDelete",
		data: JSON.stringify(selectedData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			if(data){
				alert("삭제되었습니다.");
				UML_Search();
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}

$(document).ready(function(){
	UML_Search();
})