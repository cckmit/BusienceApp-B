var matInoutListTable = new Tabulator("#matInoutListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	rowFormatter: function(row){
		if(row.getData().ltranse_ItemName == "Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center", headerSort:false},
 	{title:"품목코드", field:"ltranse_ItemCode", headerHozAlign:"center",  hozAlign:"left", headerSort:false, width:90},
 	{title:"품목명", field:"ltranse_ItemName", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:170},
	{title:"입고", field:"ltranse_InQty", headerHozAlign:"center", hozAlign:"right", headerSort:false, width:90},
 	{title:"입고반품", field:"ltranse_InReturnQty", headerHozAlign:"center", hozAlign:"right", headerSort:false, width:90},
 	{title:"출고", field:"ltranse_OutQty", headerHozAlign:"center", hozAlign:"right", headerSort:false, width:90},
 	{title:"출고반품", field:"ltranse_OutReturnQty", headerHozAlign:"center", hozAlign:"right", headerSort:false, width:90},
 	{title:"그외 출고", field:"ltranse_OutOtherQty", headerHozAlign:"center", hozAlign:"right", headerSort:false, width:90},
 	{title:"처리일자", field:"ltranse_dCreate_Time", headerHozAlign:"center", hozAlign:"right", headerSort:false, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}, width:130},
 	{title:"비고", field:"ltranse_Remark", headerHozAlign:"center", hozAlign:"left", headerSort:false, width:90}
 	]
});

function MIO_ListViewSearchBtn() {
	data = {
		startDate : $("#matInoutList_startDate").val(),
		endDate : $("#matInoutList_endDate").val(),
		mat_ItemCode : $("#PRODUCT_ITEM_CODE").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "matInoutLXReportRest/MIO_ListView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(matInoutListTable,data);
		}
	});
}
