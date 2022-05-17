var salesOutputListTable = new Tabulator("#salesOutputListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", headerFilter:true, hozAlign: "center"},
	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:130, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"sales_OutMat_Send_Clsfc", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"거래처명", field:"sales_OutMat_Client_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품목코드", field:"sales_OutMat_Code", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:170},
 	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"},
	{title:"등록자", field:"sales_OutMat_Modifier", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"등록일자", field:"sales_OutMat_dInsert_Time", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:140, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	]
});

function SOL_Search() {
	data = {
		startDate : $("#salesOutputList_startDate").val(),
		endDate : $("#salesOutputList_endDate").val(),
		sales_OutMat_Code : $("#PRODUCT_ITEM_CODE1").val(),
		sales_OutMat_Client_Code : $(".Client_Code1").val(),
		sales_OutMat_Send_Clsfc : $("#outMatTypeListSelectBox option:selected").val()
	}
	

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesOutputReportRest/SOL_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(SOL_data) {
			console.log(SOL_data);
			
			TableSetData(salesOutputListTable,SOL_data);
		}
	});
}

$('#SOL_SearchBtn').click(function(){
	SOL_Search();
})

var salesOutputItemViewTable = new Tabulator("#salesOutputItemViewTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
	rowFormatter: function(row){
		if(row.getData().sales_OutMat_Send_Clsfc == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
 	columns:[ //Define Table Columns
	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center"},
	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center",hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"sales_OutMat_Send_Clsfc", headerHozAlign:"center" ,hozAlign:"left"},
 	{title:"품목코드", field:"sales_OutMat_Code", headerHozAlign:"center",hozAlign:"left"},
 	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:80},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center",hozAlign:"right"}
 	],
});

function SOIV_Search() {
	data = {
		startDate : $("#salesOutputItemView_startDate").val(),
		endDate : $("#salesOutputItemView_endDate").val(),
		sales_OutMat_Code : $("#PRODUCT_ITEM_CODE2").val(),
		sales_OutMat_Send_Clsfc : $("#outMatTypeItemViewSelectBox option:selected").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesOutputReportRest/SOIV_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(SOIV_data) {
			console.log(SOIV_data);
			TableSetData(salesOutputItemViewTable,SOIV_data);
		}
	});
}


$('#SOIV_SearchBtn').click(function(){
	SOIV_Search();
})