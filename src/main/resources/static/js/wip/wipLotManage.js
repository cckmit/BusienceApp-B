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
	{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"접두사", field:"wip_Prefix", visible: false},
	{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center",
		formatter: function(cell){
			var prefix = cell.getRow().getData().wip_Prefix
			return prefix + cell.getValue();
		}},
	{title:"현재공정", field:"wip_Process_Name", headerHozAlign:"center", hozAlign:"center"},
 	{title:"생성시간", field:"wip_InputDate", headerHozAlign:"center"},
	{title:"재출력", field: "something", align: "center",
		formatter: function(){return "<i class='fa fa-print'></i>"},
		cellClick:function(e, cell){
		    if(confirm("기존 LotNo "+cell.getRow().getData().wip_LotNo+"를 재출력하시겠습니까?")){
				CustomLabelPrinter(cell.getRow().getData())
			}
	    }}
	]
});

$('#WLMS_LabelPrintBtn').click(function(){
	var selectedRow = wipLotManageSubTable.getData("selected");
	
	//행 클릭 여부에 따라 출력, 재출력이 갈림
	if(selectedRow.length != 0){
		if(confirm(selectedRow[0].product_ITEM_CODE +"를 입고 하고, 라벨를 출력하시겠습니까?")){
			labelPrint(selectedRow[0]);
			WLM_Search()
		}
	}else{
		alert("품목을 선택해주세요.")
	}
})

function labelPrint(selectedRow){
	var datas = {
		PRODUCT_ITEM_CODE : selectedRow.product_ITEM_CODE,
		PRODUCT_OLD_ITEM_CODE : selectedRow.product_OLD_ITEM_CODE,
		PRODUCT_ITEM_CLSFC_1 : selectedRow.product_ITEM_CLSFC_1
	}
	$.ajax({
		method: "get",
		url: "wipLotManageRest/wipLabelPrint",
		data: datas,
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

var wipLotManageSubTable = new Tabulator("#wipLotManageSubTable", {
	selectable: 1, 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
	ajaxURL:"wipLotManageRest/wipLotManageSubSelect",
    ajaxConfig:"get",
    ajaxContentType:"json",
	height:"calc(100% - 175px)",
 	columns:[
		{ formatter:"rowSelection", align:"center", headerSort:false},
		{ title: "순번",	field: "rownum", hozAlign: "center", formatter:"rownum"},
		{ title: "품목코드", field: "product_ITEM_CODE", headerHozAlign: "center"},
		{ title: "구품목코드", field: "product_OLD_ITEM_CODE", headerHozAlign: "center"},
		{ title: "품명", field: "product_ITEM_NAME",	headerHozAlign: "center"},
		{ title: "규격1", field: "product_INFO_STND_1", headerHozAlign: "center"},
		{ title: "규격2", field: "product_INFO_STND_2", headerHozAlign: "center"},
		{ title: "품목분류1", field: "product_ITEM_CLSFC_1_NAME", headerHozAlign: "center"},
		{ title: "품목분류2", field: "product_ITEM_CLSFC_2_NAME", headerHozAlign: "center"},
	]
});

//공통
function routingSelectList(){
	var result = new Object();
	
	$.ajax({
		method : "GET",
		async: false,
		url : "routingManageRest/routingManageSelect",
		success : function(datas) {
			result = datas
		}
	});
	
	return result;
}