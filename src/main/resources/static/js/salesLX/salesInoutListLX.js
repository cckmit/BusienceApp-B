var salesInoutListTable = new Tabulator("#salesInoutListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
	rowFormatter: function(row){
		if(row.getData().sales_LTranse_LotNo == "Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center", headerSort:false},
 	{title:"품목코드", field:"sales_LTranse_ItemCode", headerHozAlign:"center",  hozAlign:"left", headerSort:false},
 	{title:"품목명", field:"sales_LTranse_ItemName", headerHozAlign:"center", hozAlign:"left", headerSort:false},
	{title:"입고", field:"sales_LTranse_InQty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
 	{title:"입고반품", field:"sales_LTranse_InReturnQty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
	{title:"그외 입고", field:"sales_LTranse_InOtherQty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
 	{title:"출하", field:"sales_LTranse_OutQty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
 	{title:"출하반품", field:"sales_LTranse_OutReturnQty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
 	{title:"그외 출하", field:"sales_LTranse_OutOtherQty", headerHozAlign:"center", hozAlign:"right", headerSort:false},
 	{title:"처리일자", field:"sales_LTranse_dCreate_Time", headerHozAlign:"center", hozAlign:"right", headerSort:false, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"비고", field:"sales_LTranse_Remark", headerHozAlign:"center", hozAlign:"left", headerSort:false}
 	],
});

function FIO_ListViewSearchBtn() {
	data = {
		startDate : $("#fgoodsInoutList_startDate").val(),
		endDate : $("#fgoodsInoutList_endDate").val(),
		sales_ItemCode : $("#PRODUCT_ITEM_CODE").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesInoutReportLXRest/FIO_ListView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(salesInoutListTable,data);
		}
	});
}
