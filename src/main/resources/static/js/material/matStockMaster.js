var matStockItemListTable = new Tabulator("#matStockItemListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"rownum", headerHozAlign: "center", headerFilter:true, hozAlign: "center", formatter: "rownum"},
 	{title:"품목코드", field:"s_ItemCode", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"s_ItemName", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"규격1", field:"s_Item_Standard_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"분류1", field:"s_Item_Classfy_1_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"center"},
	{title:"분류2", field:"s_Item_Classfy_2_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"center"},
 	{title:"단위", field:"s_Item_Unit", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"재고수량", field:"s_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"}
 	]
});

function MS_ItemListViewSearchBtn() {
	var datas = {
		itemCode : $("#PRODUCT_ITEM_CODE").val(),
		check : document.getElementById("Stock_Qty_Checked").checked
	}

	matStockItemListTable.setData("matStockRest/matStockSelect", datas);
}

var matStockLotListTable = new Tabulator("#matStockLotListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"rownum", headerHozAlign: "center", headerFilter:true, hozAlign: "center", formatter: "rownum"},
 	{title:"LotNo", field:"s_LotNo", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품목코드", field:"s_ItemCode", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"s_ItemName", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"규격1", field:"s_Item_Standard_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"분류1", field:"s_Item_Classfy_1_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"center"},
	{title:"분류2", field:"s_Item_Classfy_2_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"center"},
 	{title:"단위", field:"s_Item_Unit", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"재고수량", field:"s_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"}
 	]
});

function MS_ItemLotSearchBtn() {
	var datas = {
		itemCode : $("#PRODUCT_ITEM_CODE2").val(),
		LotNo: $('#matStockLotNo').val(),
		check : document.getElementById("Stock_Qty_Checked1").checked
	}

	matStockLotListTable.setData("matStockRest/matStockLotSelect", datas);
	console.log(matStockLotListTable);
}