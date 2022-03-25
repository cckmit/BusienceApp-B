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
	rowFormatter:function(row){
		//request_mCheck가 Y면 빨간색 I면 파란색으로 바꿔준다
        if(row.getData().rm_Check == "Y"){
            row.getElement().style.color = "red";
        }else if(row.getData().rm_Check == "I"){
            row.getElement().style.color = "blue";
		}
    },
	//행클릭 이벤트
	rowClick:function(e, row){
		matRequestListTable.deselectRow();
		row.select();
		MRLS_Search(row.getData().rm_RequestNo);
		matRequestListStockTable.clearData();
    },
 	columns:[ 
 		{title:"요청No", field:"rm_RequestNo", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
		{title:"요청자", field:"rm_UserCode", visible:false},
 		{title:"요청자", field:"rm_UserName", headerHozAlign:"center", headerFilter:true},
		{title:"부서", field:"rm_DeptName", headerHozAlign:"center", headerFilter:true},
 		{title:"요청일", field:"rm_Date", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"특이사항", field:"rm_Remark", headerHozAlign:"center", headerFilter:true},
 		{title:"목록확인", field:"rm_Check", visible:false},
 	],
});

//orderMaster 검색버튼
function MRL_searchBtn(){
	MRL_select()
}
//orderMaster 목록검색
function MRL_select(){
	var data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val()
	}
	matRequestListTable.setData("matRequestRest/MRM_Search", data)
	.then(function(){
		//list와 stock의 데이터를 없에준다
		matRequestSubTable.clearData();
		matRequestStockTable.clearData();
	})
}

// 출고구분 select를 구성하기위한 ajax
var output_dtl = dtlSelectList(18);

var matRequestListSubTable = new Tabulator("#matRequestListSubTable", {
	height:"calc(90% - 175px)",
	layoutColumnsOnNewData : true,
	tabEndNewRow: true,
	//커스텀 키 설정
	keybindings:{
        "navNext" : "13"
    },
	//행을 클릭하면 matRequestStockTable에 리스트가 나타남
	rowClick:function(e, row){
		MRLSS_Search(row.getData().rs_ItemCode);
    },
	rowDblClick:function(e, row){
		row.toggleSelect();		
    },
	//행이 추가될때마다 인덱스 부여
 	columns:[
	{title:"순번", field:"rownum", headerHozAlign:"center", hozAlign:"center", formatter: "rownum"},
	{title:"요청No", field:"rs_RequestNo", visible:false},
 	{title:"코드", field:"rs_ItemCode", headerHozAlign:"center"},
 	{title:"제품명", field:"rs_ItemName", headerHozAlign:"center"},
 	{title:"수량", field:"rs_Qty", headerHozAlign:"center", hozAlign:"right"},
	{title:"비고", field:"rs_Remark", headerHozAlign:"center"},
	{title:"구분", field:"rs_Send_Clsfc", headerHozAlign:"center",
		formatter:function(cell, formatterParams){
		    var value = cell.getValue();
			if(output_dtl[value] != null){
					value = output_dtl[value];	
				}else{
					value = "";
				}
		    return value;}}
 	]
});


//OrderSub 목록검색
function MRLS_Search(RequestNo){
	$("#RS_RequestNo").val(RequestNo)
	
	var data = {
		OrderNo : RequestNo
	}
	//발주넘버
	matRequestListSubTable.setData("matRequestRest/MRS_Search", data);
}

var matRequestListStockTable = new Tabulator("#matRequestListStockTable", {
	selectable:1,
	height:"10%",
	layoutColumnsOnNewData : true,
 	columns:[
 	{title:"제품코드", field:"sm_Code", headerHozAlign:"center"},
 	{title:"제품명", field:"sm_Name", headerHozAlign:"center"},
	{title:"규격1", field:"sm_STND_1", headerHozAlign:"center"},
 	{title:"수량", field:"sm_Qty", headerHozAlign:"center", hozAlign:"right", formatter:"money", formatterParams: {precision: false}}
 	]
});

//orderStock 목록검색
function MRLSS_Search(ItemCode){
	var datas = {
		ItemCode : ItemCode
	}
	matRequestListStockTable.setData("matOrderLXRest/MOS_Search", datas);
}
