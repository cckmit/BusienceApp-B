var matInoutListTable = new Tabulator("#matInoutListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
	rowFormatter: function(row){
		if(row.getData().lt_ItemName == "Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[
 	{title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", headerSort:false, formatter: "rownum"},
 	{title:"품목코드", field:"lt_ItemCode", headerHozAlign:"center",  hozAlign:"left", headerSort:false},
 	{title:"품목명", field:"lt_ItemName", headerHozAlign:"center", hozAlign:"center", headerSort:false},
	{title:"규격1", field:"lt_Item_Stnd_1", headerHozAlign:"center", hozAlign:"center", headerSort:false},
	{title:"규격2", field:"lt_Item_Stnd_2", headerHozAlign:"center", hozAlign:"center", headerSort:false},
	{title:"분류1", field:"lt_ITEM_CLSFC_1", headerHozAlign:"center", hozAlign:"center", headerSort:false},
	{title:"분류2", field:"lt_ITEM_CLSFC_2", headerHozAlign:"center", hozAlign:"center", headerSort:false},
	{title:"입고", field:"lt_InQty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
 	{title:"입고반품", field:"lt_InReturn_Qty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
	{title:"그외 입고", field:"lt_InOther_Qty", headerHozAlign:"center", hozAlign:"right", headerSort:false}, 	
	{title:"출고", field:"lt_OutQty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
 	//{title:"출고이동", field:"lt_OutReturn_Qty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
 	{title:"그외 출고", field:"lt_OutOther_Qty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
	{title:"입출고 구분", field:"lt_Classify_Name", headerHozAlign:"center", hozAlign:"right", headerSort:false}
 	]
});

function MIO_ListViewSearchBtn() {
	var datas = {
		startDate : $("#matInoutList_startDate").val(),
		endDate : $("#matInoutList_endDate").val(),
		itemCode : $("#PRODUCT_ITEM_CODE").val()
	}
	matInoutListTable.setData("matInoutReportRest/MIO_Select", datas)
}
