var salesPackingMasterTable = new Tabulator("#salesPackingMasterTable",{
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
		MMS_Search(row.getData().user_Code)
		$('#MM_UpdateBtn').removeClass('unUseBtn');
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "대포장 LotNo", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "제품코드", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "제품명", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "규격1", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "품목분류1", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "품목분류2", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "재질", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "수량",	field: "user_Name",	headerHozAlign: "center", headerFilter:"input"},
		{ title: "포장시간", field: "user_Type_Name", hozAlign: "right",	headerHozAlign: "center", headerFilter:"input"},
		{ title: "구분", field: "user_Use_Status", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center",
			formatter: "tickCross",	sorter:"boolean"}
	]
});

var salesPackingSubTable = new Tabulator("#salesPackingSubTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	selectable: true,
	height: "calc(100% - 175px)",
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "소포장 LotNo", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "제품코드", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "제품명", field: "user_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "수량",	field: "user_Name",	headerHozAlign: "center", headerFilter:"input"},
		{ title: "등록일자", field: "user_Use_Status", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"}
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