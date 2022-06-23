var salesOutputListTable = new Tabulator("#salesOutputListTable", { 
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"rownum", headerHozAlign: "center", headerFilter:true, hozAlign: "center", formatter:"rownum"},
	{title:"LotNo", field:"sales_OutMat_Lot_No", headerHozAlign:"center", headerFilter:true, hozAlign:"center", width:130},
	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:130, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"sales_OutMat_Send_Clsfc", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"거래처명", field:"sales_OutMat_Client_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품목코드", field:"sales_OutMat_Code", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:170},
 	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"분류1", field:"sales_OutMat_Item_Clsfc_Name_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"},
	{title:"등록자", field:"sales_OutMat_Modifier", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"등록일자", field:"sales_OutMat_dInsert_Time", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:140, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	],
});

function SOL_Search() {
	datas = {
		startDate : $("#salesOutputList_startDate").val(),
		endDate : $("#salesOutputList_endDate").val(),
		Sales_OutMat_Code : $("#PRODUCT_ITEM_CODE1").val(),
		Sales_OutMat_Client_Code : $(".Client_Code1").val(),
		Sales_OutMat_Send_Clsfc : $("#outMatTypeListSelectBox option:selected").val(),
		Sales_OutMat_Lot_No : $("#Sales_OutMat_Lot_No").val()
	}
	
	salesOutputListTable.setData("salesOutputRest/SOL_Search", datas);
	
	console.log(salesOutputListTable);

}

$('#SOL_SearchBtn').click(function(){
	SOL_Search();
})

var salesOutputItemViewTable = new Tabulator("#salesOutputItemViewTable", { 
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().sales_OutMat_Lot_No == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
	{title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", formatter:"rownum"},
	{title:"LotNo", field:"sales_OutMat_Lot_No", headerHozAlign:"center",hozAlign:"center"},
	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center",hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"sales_OutMat_Send_Clsfc", headerHozAlign:"center" ,hozAlign:"left"},
 	{title:"품목코드", field:"sales_OutMat_Code", headerHozAlign:"center",hozAlign:"left"},
 	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:80},
 	{title:"분류1", field:"sales_OutMat_Item_Clsfc_Name_1", headerHozAlign:"center", hozAlign:"left", width:80},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center",hozAlign:"right"}
 	],
});

function SOIV_Search() {
	datas = {
		startDate : $("#salesOutputItemView_startDate").val(),
		endDate : $("#salesOutputItemView_endDate").val(),
		Sales_OutMat_Code : $("#PRODUCT_ITEM_CODE2").val(),
		Sales_OutMat_Send_Clsfc : $("#outMatTypeItemViewSelectBox option:selected").val()
	}
	
	salesOutputItemViewTable.setData("salesOutputRest/SOIV_Search", datas);
	
	console.log(salesOutputItemViewTable);

}


$('#SOIV_SearchBtn').click(function(){
	SOIV_Search();
})

//fgoodsInputList의 검색 버튼에 기능 추가
$('#sales_OutMat_Lot_No').keypress(function(e){
	if(e.keyCode == 13){
		SOIV_Search();
	}
})