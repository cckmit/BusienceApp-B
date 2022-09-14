var salesStockItemListTable = new Tabulator("#salesStockItemListTable", { 
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"rownum", headerHozAlign: "center", headerFilter:true, hozAlign: "center", formatter: "rownum"},
 	{title:"품목코드", field:"s_ItemCode", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"s_ItemName", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"규격1", field:"s_Item_Standard_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"규격2", field:"s_Item_Standard_2", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"분류1", field:"s_Item_Classfy_1_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"분류2", field:"s_Item_Classfy_2_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"재질", field:"s_Item_Material", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"단위", field:"s_Item_Unit", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"재고수량", field:"s_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"}
 	]
});

function FS_ItemListViewSearchBtn() {
	var datas = {
		itemCode : $("#PRODUCT_ITEM_CODE2").val(),
		Check : document.getElementById("Sales_Stock_Qty_Checked").checked
	}
	
	salesStockItemListTable.setData("salesStockRest/salesStockSelect",datas)
	
}

var salesStockLotListTable = new Tabulator("#salesStockLotListTable", { 
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"rownum", headerHozAlign: "center", headerFilter:true, hozAlign: "center", formatter: "rownum"},
 	{title:"LotNo", field:"s_LotNo", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품목코드", field:"s_ItemCode", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"s_ItemName", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"규격1", field:"s_Item_Standard_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"분류1", field:"s_Item_Classfy_1_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"분류2", field:"s_Item_Classfy_2_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"재질", field:"s_Item_Material", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"단위", field:"s_Item_Unit", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"재고수량", field:"s_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"}
 	]
});

function FS_LotListViewSearchBtn() {
	var datas = {
		itemCode : $("#PRODUCT_ITEM_CODE1").val(),
		LotNo: $('#sales_lmaster_LotNo').val(),
		check : document.getElementById("Stock_Qty_Checked1").checked
	}

	salesStockLotListTable.setData("salesStockRest/salesStockLotSelect", datas);
}