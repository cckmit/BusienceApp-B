var salesStockLotListTable = new Tabulator("#salesStockLotListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	rowFormatter: function(row){
		if(row.getData().sales_LMaster_LotNo == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", headerFilter:true, hozAlign: "center"},
 	{title:"품목유형", field:"sales_LMaster_ItemClsfc_1", headerHozAlign:"center", headerFilter:true, hozAlign:"center", width:90},
 	{title:"품목코드", field:"sales_LMaster_ItemCode", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"sales_LMaster_ItemName", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:170},
 	{title:"규격", field:"sales_LMaster_ItemSTND_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"단위", field:"sales_LMaster_ItemUNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"총수량", field:"sales_LMaster_SLMQty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"},
	{title:"LotNo", field:"sales_LMaster_LotNo", headerHozAlign:"center", headerFilter:true, hozAlign:"center", width:130},
 	{title:"재고수량", field:"sales_LMaster_InQty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"}
 	],
});

function FS_LotListViewSearchBtn() {
	data = {
		sales_LMaster_ItemCode : $("#PRODUCT_ITEM_CODE1").val(),
		sales_LMaster_LotNo : $("#sales_lmaster_LotNo").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesStockReportLXRest/FS_ListView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(salesStockLotListTable,data);
			//matInputListTable.setData(data);
		}
	});
}

var salesStockItemListTable = new Tabulator("#salesStockItemListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	height:"73.6vh",
 	//height:650, // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", headerFilter:true, hozAlign: "center"},
	{title:"품목유형", field:"sales_LMaster_ItemClsfc_1", headerHozAlign:"center", headerFilter:true, hozAlign:"center", width:90},
 	{title:"품목코드", field:"sales_LMaster_ItemCode", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"sales_LMaster_ItemName", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:170},
 	{title:"규격", field:"sales_LMaster_ItemSTND_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"단위", field:"sales_LMaster_ItemUNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"재고수량", field:"sales_LMaster_InQty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"}
 	],
});

function FS_ItemListViewSearchBtn() {

	data = {
		sales_LMaster_ItemCode : $("#PRODUCT_ITEM_CODE2").val(),
		sales_stockCheck : document.getElementById("Sales_Stock_Qty_Checked").checked
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesStockReportLXRest/FS_ItemListView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(salesStockItemListTable,data);
		}
	});
}
