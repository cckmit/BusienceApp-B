var matStockItemListTable = new Tabulator("#matStockItemListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", headerFilter:true, hozAlign: "center"},
	{title:"품목유형", field:"lmaster_ItemClsfc_1", headerHozAlign:"center", headerFilter:true, hozAlign:"center", width:90},
 	{title:"품목코드", field:"lmaster_ItemCode", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"lmaster_ItemName", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:170},
 	{title:"규격", field:"lmaster_ItemSTND_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:120},
 	{title:"단위", field:"lmaster_ItemUNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"재고수량", field:"lmaster_InQty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"}
 	]
});

function MS_ItemListViewSearchBtn() {
	data = {
		SM_Code : $("#PRODUCT_ITEM_CODE2").val(),
		stockCheck : document.getElementById("Stock_Qty_Checked").checked
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "matStockReportLXRest/MS_ItemListView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(matStockItemListTable,data);
		}
	});
}