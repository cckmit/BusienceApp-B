var matRequestTable = new Tabulator("#matRequestTable", {
    height:"calc(100% - 175px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData : true,
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
		matRequestTable.deselectRow();
		row.select();
		MRS_Search(row.getData().rm_RequestNo);
		matRequestStockTable.clearData();
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

//requestMaster 목록검색
function MRM_Search(){
	var data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val()
	}
	matRequestTable.setData("matRequestRest/MRM_Search", data)
	.then(function(){
		//list와 stock의 데이터를 없에준다
		matRequestSubTable.clearData();
		matRequestStockTable.clearData();
	})
}

//MRM_SearchBtn
$('#MRM_SearchBtn').click(function(){
	MRM_Search();
})

var matRequestSubTable = new Tabulator("#matRequestSubTable", {
	height:"calc(90% - 175px)",
	layoutColumnsOnNewData : true,
	selectable: true,
	//행을 클릭하면 matRequestStockTable에 리스트가 나타남
	rowClick:function(e, row){
		MRLSS_Search(row.getData().rs_ItemCode);
    },
	//행이 추가될때마다 인덱스 부여
 	columns:[
	{title:"순번", field:"rownum", headerHozAlign:"center", hozAlign:"center", formatter: "rownum"},
	{title:"요청No", field:"rs_RequestNo", visible:false},
 	{title:"코드", field:"rs_ItemCode", headerHozAlign:"center"},
 	{title:"제품명", field:"rs_ItemName", headerHozAlign:"center"},
 	{title:"수량", field:"rs_Qty", headerHozAlign:"center", hozAlign:"right"},
	{title:"비고", field:"rs_Remark", headerHozAlign:"center"},
	{title:"구분", field:"rs_Send_Clsfc_Name", headerHozAlign:"center"}
 	]
});


//OrderSub 목록검색
function MRS_Search(RequestNo){
	$("#RS_RequestNo").val(RequestNo)
	
	var data = {
		OrderNo : RequestNo
	}
	//발주넘버
	matRequestSubTable.setData("matRequestRest/MRS_Search", data);
}

var matRequestStockTable = new Tabulator("#matRequestStockTable", {
	selectable:1,
	height:"10%",
	layoutColumnsOnNewData : true,
 	columns:[
 	{ title: "제품코드", field: "s_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "s_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "s_Item_Standard_1", headerHozAlign: "center" },
		{ title: "규격2", field: "s_Item_Standard_2", headerHozAlign: "center" },
		{ title: "분류1", field: "s_Item_Classfy_1_Name", headerHozAlign: "center" },
		{ title: "분류2", field: "s_Item_Classfy_2_Name", headerHozAlign: "center" },
		{ title: "수량", field: "s_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
 	]
});

//orderStock 목록검색
function MRLSS_Search(itemCode){
	matRequestStockTable.setData("matStockRest/matStockSelect", { ItemCode: itemCode });
}
