var salesInputListTable = new Tabulator("#salesInputListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", headerFilter:true, hozAlign: "center"},
	{title:"입고일자", field:"sales_InMat_Date", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:130, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"입고구분", field:"sales_InMat_Rcv_Clsfc", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품목코드", field:"sales_InMat_Code", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"sales_InMat_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:170},
 	{title:"규격", field:"sales_InMat_STND_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"단위", field:"sales_InMat_UNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"수량", field:"sales_InMat_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"},
	{title:"등록자", field:"sales_InMat_Modifier", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"등록일자", field:"sales_InMat_dInsert_Time", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:140, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	],
});

function FIL_SearchBtn() {
	data = {
		startDate : $("#matInputList_startDate").val(),
		endDate : $("#matInputList_endDate").val(),
		sales_InMat_Code : $("#PRODUCT_ITEM_CODE1").val(),
		sales_InMat_Client_Name : $("#Sales_InMat_Client_Name1").val(),
		sales_InMat_Rcv_Clsfc : $("#inMatTypeListSelectBox option:selected").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesInputReportLXRest/FIL_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(FIL_data) {
			console.log(FIL_data);
			TableSetData(salesInputListTable,FIL_data);
		}
	});
}

var salesInputItemViewTable = new Tabulator("#salesInputItemViewTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
	rowFormatter: function(row){
		if(row.getData().sales_InMat_Rcv_Clsfc == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
 	columns:[ //Define Table Columns
	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center"},
	{title:"입고일자", field:"sales_InMat_Date", headerHozAlign:"center",hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD hh:mm:ss"}},
 	{title:"입고구분", field:"sales_InMat_Rcv_Clsfc", headerHozAlign:"center" ,hozAlign:"left"},
 	{title:"품목코드", field:"sales_InMat_Code", headerHozAlign:"center",hozAlign:"left"},
 	{title:"품명", field:"sales_InMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"sales_InMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:80},
 	{title:"단위", field:"sales_InMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"sales_InMat_Qty", headerHozAlign:"center",hozAlign:"right"}
 	],
});

function FIIL_SearchBtn() {
	data = {
		startDate : $("#matInputItemView_startDate").val(),
		endDate : $("#matInputItemView_endDate").val(),
		sales_InMat_Code : $("#PRODUCT_ITEM_CODE2").val(),
		sales_InMat_Rcv_Clsfc : $("#inMatTypeItemViewSelectBox option:selected").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesInputReportLXRest/FIIL_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(FIIL_data) {
			console.log(FIIL_data);
			TableSetData(salesInputItemViewTable,FIIL_data);
		}
	});
}
