var workStatus_dtl = dtlSelectList(29);

var workListTable = new Tabulator("#workListTable", {
	height: "calc(100% - 175px)",
	rowFormatter: function(row){
		if(row.getData().workOrder_WorkStatus == "작업시작"){
    		row.getElement().style.color = "blue";
    	}
    },
	layoutColumnsOnNewData : true,
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "/workListRest/workListSearch",
	ajaxParams : {itemCode: $(".itemCode").val()},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	groupBy:"workOrder_EquipName",
	columns: [
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", visible:false},
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", visible:false},	
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격1", field: "workOrder_INFO_STND_1", headerHozAlign: "center"},
		{ title: "규격2", field: "workOrder_INFO_STND_2", headerHozAlign: "center"},	
		{ title: "지시수량", field: "workOrder_AllottedQty", headerHozAlign: "center", hozAlign:"right",
			formatter:"money", formatterParams: {precision: false}},
		{ title: "생산수량", field: "workOrder_ProductionQty", headerHozAlign: "center", hozAlign:"right",
			formatter:"money", formatterParams: {precision: false}},
		{ title: "작업등록일", field: "workOrder_RegisterTime", hozAlign: "right", headerHozAlign: "center"},
		{ title: "작업시작일", field: "workOrder_StartTime", hozAlign: "right", headerHozAlign: "center"},
		{ title: "작업상태", field: "workOrder_WorkStatus", headerHozAlign: "center", hozAlign:"center", editor: "select",
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (workStatus_dtl[value] != null) {
					value = workStatus_dtl[value];
				} else {
					value = workStatus_dtl[0];
				}
				return value;
			},
			editorParams: { values: workStatus_dtl },
			cellEdited:function(cell){
				var rowData = cell.getRow().getData();
				
				if(rowData.workOrder_ProductionQty == 0 && rowData.workOrder_WorkStatus_Name == "작업시작" && workStatus_dtl[rowData.workOrder_WorkStatus] == "작업완료"){
					if(confirm("생산량 0으로 작업완료하면 작업지시가 삭제됩니다. 삭제하시겠습니까?")){
						workListSave(cell.getRow().getData());
					}else{
						cell.getRow().update({workOrder_WorkStatus : cell.getInitialValue()})
					}
				}else{
					workListSave(cell.getRow().getData());
					cell.getRow().update({workOrder_WorkStatus : cell.getInitialValue()})
				}				
			}
		},
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center" }
	]
});

$(".searchBtn").click(function(){
	workListSearch();
})

function workListSearch(){
	workListTable.setData("/workListRest/workListSearch", {itemCode: $(".itemCode").val()});
}

function workListSave(selectedData){
	var resultData = $.ajax({
		method: "post",
		url: "/workListRest/workListSave",
		data : selectedData,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if(result < 0){
				alert("전 단계로 되돌릴 수 없습니다.")
			}else if(result == 1){
				workListSearch();
				alert("다음 단계로 저장되었습니다.")
			}else if(result == 2){
				alert("접수완료에서 작업완료로 변경할 수 없습니다.")
			}else if(result == 3) {
				workListSearch();
				alert("삭제되었습니다.")
			}else {
				alert("해당 설비는 이미 작업 중 이거나, 에러가 발생하여 저장 할 수 없습니다.")
			}
		}
	});
	return resultData
}
