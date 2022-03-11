//셀위치저장
var cellPos = null;

var matRequestListTable = new Tabulator("#matRequestListTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
	paginationAddRow : "table",
    height:"calc(100% - 175px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//커스텀 키 설정
	keybindings:{
        "navNext" : "13"
    },
	//request_lNo를 인덱스로 설정
	index:"id",	
	rowFormatter:function(row){
		//request_mCheck가 Y면 빨간색 I면 파란색으로 바꿔준다
        if(row.getData().request_mCheck == "Y"){
            row.getElement().style.color = "red";
        }else if(row.getData().request_mCheck == "I"){
            row.getElement().style.color = "blue";
		}
    },
	//행클릭 이벤트
	rowClick:function(e, row){
		matRequestListTable.deselectRow();
		row.select();
		MRLS_Search(row.getData().request_mReqNo);
		matRequestListStockTable.clearData();
    },
 	columns:[ 
 		{title:"요청No", field:"request_mReqNo", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
		{title:"요청자", field:"request_mUser", visible:false},
 		{title:"요청자", field:"user_Name", headerHozAlign:"center", headerFilter:true},
		{title:"부서", field:"dept_NAME", headerHozAlign:"center", headerFilter:true},
 		{title:"요청일", field:"request_mDate", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"특이사항", field:"request_mRemarks", headerHozAlign:"center", headerFilter:true},
 		{title:"수정자", field:"request_mModifier", headerHozAlign:"center", headerFilter:true},
 		{title:"수정일자", field:"request_mModify_Date", headerHozAlign:"center",  headerFilter:true},
 		{title:"목록확인", field:"request_mCheck", visible:false},
		{title:"id", field:"id", visible:false}
 	],
});

//orderMaster 검색버튼
function MRL_searchBtn(){
	MRL_select()
	//list와 stock의 데이터를 없에준다
	matRequestListSubTable.clearData();
	matRequestListStockTable.clearData();
}
//orderMaster 목록검색
function MRL_select(){
	data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		request_mCode : $("#Dept_Code").val()
	}
	$.ajax({
		method : "GET",
		dataType : "json",
		async: false,
		url : "requestReportRest/MRL_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(datas) {
			TableSetData(matRequestListTable,datas);
		}
	});
}

// 출고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
	method : "GET",
	async: false,
	url : "dtl_tbl_select?NEW_TBL_CODE=18",
	success : function(datas) {
		for(i=0;i<datas.length;i++){
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});

var matRequestListSubTable = new Tabulator("#matRequestListSubTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
	height:"calc(90% - 175px)",
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	tabEndNewRow: true,
	//커스텀 키 설정
	keybindings:{
        "navNext" : "13"
    },
	//행을 클릭하면 matRequestStockTable에 리스트가 나타남
	rowClick:function(e, row){
		console.log("행클릭")
		MRLSS_Search(row.getData().request_lCode);
    },
	rowDblClick:function(e, row){
		row.toggleSelect();
		
    },
	//id를 인덱스로 설정
	index:"id",
	//행이 추가될때마다 인덱스 부여
 	columns:[
	{title:"순번", field:"request_lNo", headerHozAlign:"center", hozAlign:"center"},
	{title:"요청No", field:"request_lReqNo", visible:false},
 	{title:"코드", field:"request_lCode", headerHozAlign:"center"},
 	{title:"제품명", field:"request_lName", headerHozAlign:"center"},
 	{title:"수량", field:"request_lQty", headerHozAlign:"center", hozAlign:"right"},
	{title:"비고", field:"request_lInfo_Remark", headerHozAlign:"center"},
	{title:"구분", field:"request_Send_Clsfc", headerHozAlign:"center",
		formatter:function(cell, formatterParams){
		    var value = cell.getValue();
			if(dtl_arr[value] != null){
					value = dtl_arr[value];	
				}else{
					value = "";
				}
		    return value;}},
 	{title:"id", field:"id", visible:false}
 	]
});


//OrderSub 목록검색
function MRLS_Search(request_mReqNo){
	$("#request_lReqNo").val(request_mReqNo)
	//발주넘버
	$.ajax({
		method : "GET",
		async: false,
		url : "requestReportRest/MRLS_Search?request_mReqNo="+ request_mReqNo,
		success : function(data) {
			console.log(data)
			TableSetData(matRequestListSubTable,data);
		}
	});
}


var matRequestListStockTable = new Tabulator("#matRequestListStockTable", {
	selectable:1,
	height:"10%",
	layoutColumnsOnNewData : true,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
 	columns:[
 	{title:"제품코드", field:"sm_Code", headerHozAlign:"center"},
 	{title:"제품명", field:"sm_Name", headerHozAlign:"center"},
	{title:"규격1", field:"sm_STND_1", headerHozAlign:"center"},
 	{title:"수량", field:"sm_Qty", headerHozAlign:"center", hozAlign:"right", formatter:"money", formatterParams: {precision: false}}
 	]
});

//orderStock 목록검색
function MRLSS_Search(request_lCode){
	//발주넘버
	$.ajax({
		method : "GET",
		url : "requestReportRest/MRLSS_Search?request_lCode="+ request_lCode,
		async: false,
		success : function(datas) {
			console.log(datas);
			TableSetData(matRequestListStockTable,datas);
		}
	});
}