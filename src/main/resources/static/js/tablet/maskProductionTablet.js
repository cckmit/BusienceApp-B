var itemTable = new Tabulator("#itemTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	ajaxURL:"maskProductionRest/workingByMachine",
	ajaxParams: {machineCode : $("#machineCode").val()},
    ajaxConfig:"get",
    ajaxContentType:"json",
	columns: [	
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격1", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
		{ title: "규격2", field: "workOrder_Item_STND_2", headerHozAlign: "center"},		
		{ title: "지시수량", field: "workOrder_AllottedQty", headerHozAlign: "center", align:"right",
			formatter:"money", formatterParams: {precision: false}},
		{ title: "작업등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center"},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center"},
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center"},
	]
});

$("#fullScreenBtn").click(function(){
	toggleFullScreen();
})

function toggleFullScreen() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
    }
  }
}