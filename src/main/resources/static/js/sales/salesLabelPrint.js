var salesInputMasterTable = new Tabulator("#salesInputMasterTable", {
    height:"calc(100% - 175px)",
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "salesLabelPrintRest/SLP_Search",
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		row.select();
	},
 	columns:[
 		{title:"순번", field:"rownum", formatter:"rownum", headerHozAlign:"center", hozAlign:"center"},
		{title:"제품코드", field:"product_ITEM_CODE", headerHozAlign:"center", headerFilter: true},
 		{title:"제품명", field:"product_ITEM_NAME", headerHozAlign:"center", headerFilter: true},
 		{title:"규격", field:"product_INFO_STND_1", headerHozAlign:"center", hozAlign:"right", headerFilter: true},
		{title:"수량", field:"product_Qty", headerHozAlign:"center", hozAlign:"right", headerFilter: true, editor: "input"}
		]
});

$("#SLP_PrintBtn").click(function(){
	var datas = salesInputMasterTable.getData("selected");
	
	if(datas.length == 1){
		var jsonDatas = [{
			sales_InMat_Code : datas[0].product_ITEM_CODE,
			sales_InMat_Name : datas[0].product_ITEM_NAME,
			sales_InMat_Qty : datas[0].product_Qty
		}]
		
		salesInputPrinter(jsonDatas)
	}else{
		alert("품목을 선택하고 수량을 입력해주세요.")
	}
	
})