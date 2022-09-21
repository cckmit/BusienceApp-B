var salesInputListTable = new Tabulator("#salesInputListTable", { 
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
 	columns:[ //Define Table Columns
 	{title:"순번", field:"rownum", headerHozAlign: "center", headerFilter:true, hozAlign: "center", formatter:"rownum"},
	{title:"LotNo", field:"sales_InMat_Lot_No", headerHozAlign:"center", headerFilter:true, hozAlign:"center", width:160},
	{title:"입고일자", field:"sales_InMat_Date", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:130, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"입고구분", field:"sales_InMat_Rcv_Clsfc", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품목코드", field:"sales_InMat_Code", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"sales_InMat_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:170},
 	{title:"규격", field:"sales_InMat_STND_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"규격2", field:"sales_InMat_STND_2", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"분류1", field:"sales_InMat_Item_Clsfc_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"분류2", field:"sales_InMat_Item_Clsfc_2", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"재질", field:"sales_InMat_Item_Material", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"단위", field:"sales_InMat_UNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"수량", field:"sales_InMat_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"},
	{title:"등록자", field:"sales_InMat_Modifier", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"등록일자", field:"sales_InMat_dInsert_Time", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:140, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	],
});

function SIL_SearchBtn() {
	datas = {
		startDate : $("#salesInputList_startDate").val(),
		endDate : $("#salesInputList_endDate").val(),
		Sales_InMat_Code : $("#PRODUCT_ITEM_CODE1").val(),
		Sales_InMat_Rcv_Clsfc : $("#inMatTypeListSelectBox option:selected").val(),
		Sales_InMat_Lot_No : $("#Sales_InMat_Lot_No").val()
	}

	salesInputListTable.setData("salesInputRest/SIL_Search", datas);
	
}

var salesInputItemViewTable = new Tabulator("#salesInputItemViewTable", { 
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().sales_InMat_Lot_No == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
 	columns:[ //Define Table Columns
	{title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", formatter:"rownum"},
	{title:"입고일자", field:"sales_InMat_Date", headerHozAlign:"center",hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD hh:mm:ss"}},
 	{title:"입고구분", field:"sales_InMat_Rcv_Clsfc", headerHozAlign:"center" ,hozAlign:"left"},
 	{title:"품목코드", field:"sales_InMat_Code", headerHozAlign:"center",hozAlign:"left"},
 	{title:"품명", field:"sales_InMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"sales_InMat_STND_1", headerHozAlign:"center",  hozAlign:"left", width:80},
 	{title:"규격2", field:"sales_InMat_STND_2", headerHozAlign:"center", hozAlign:"left", width:80},
 	{title:"분류1", field:"sales_InMat_Item_Clsfc_1", headerHozAlign:"center", hozAlign:"left", width:80},
 	{title:"분류2", field:"sales_InMat_Item_Clsfc_2", headerHozAlign:"center", hozAlign:"left", width:80},
 	{title:"재질", field:"sales_InMat_Item_Material", headerHozAlign:"center", hozAlign:"left", width:80},
 	{title:"단위", field:"sales_InMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"sales_InMat_Qty", headerHozAlign:"center",hozAlign:"right"}
 	],
});

function SIIL_SearchBtn() {
	datas = {
		startDate : $("#salesInputItemView_startDate").val(),
		endDate : $("#salesInputItemView_endDate").val(),
		Sales_InMat_Code : $("#PRODUCT_ITEM_CODE2").val(),
		Sales_InMat_Rcv_Clsfc : $("#inMatTypeItemViewSelectBox option:selected").val()
	}
	
	salesInputItemViewTable.setData("salesInputRest/SIIL_Search", datas);

	console.log(salesInputItemViewTable);
	
}

//salesInputList의 검색 버튼에 기능 추가
$('#Sales_InMat_Lot_No').keypress(function(e){
	if(e.keyCode == 13){
		SIL_SearchBtn();
	}
})
