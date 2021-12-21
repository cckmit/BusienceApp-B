function WLM_Search() {
	if ($('#startDate').val().length < 10) {
		alert("시작일은 반드시 입력하여 주십시오.");
		return;
	}
3
	if ($('#endDate').val().length < 10) {
		alert("끝일은 반드시 입력하여 주십시오.");
		return;
	}

	var jsonData = {
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val()
	}
	
	wipLotManageTable.setData('wipLotManageRest/wipLotManageSelect', jsonData);
}

$('#WLM_SearchBtn').click(function(){
	WLM_Search();
})

var wipLotManageTable = new Tabulator("#wipLotManageTable", {
	selectable: 1, 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
 	columns:[
	{formatter:"rowSelection", align:"center", headerSort:false},
	{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center"},
	{title:"현재공정", field:"wip_Process_Name", headerHozAlign:"center", hozAlign:"center"},
 	{title:"생성시간", field:"wip_InputDate_P1", headerHozAlign:"center"}
	]
});

$('#WLM_LabelPrintBtn').click(function(){
	var selectedRow = wipLotManageTable.getData("selected");
	
	//행 클릭 여부에 따라 출력, 재출력이 갈림
	if(selectedRow.length == 0){
		if(confirm("신규 LotNo를 출력하시겠습니까?")){
			labelPrint(selectedRow[0]);
		}
	}else{
		if(confirm("기존 LotNo "+selectedRow[0].wip_LotNo+"를 재출력하시겠습니까?")){
			labelPrint(selectedRow[0]);
		}
	}
})

function labelPrint(selectedRow){
	var datas = selectedRow
	
	$.ajax({
		method: "get",
		url: "wipLotManageRest/wipLabelPrint",
		data : datas,
		success: function(data) {
			if(data){
				CustomLabelPrinter(data)
				alert("LotNo "+data.wip_LotNo+"가 출력되었습니다.");
				WLM_Search();
			}else{
				alert("오류가 발생했습니다.");
			}
		}
	});
}