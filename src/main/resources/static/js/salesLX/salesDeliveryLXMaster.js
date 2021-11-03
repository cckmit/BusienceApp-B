var salesDeliveryCustomerViewTable = new Tabulator("#salesDeliveryCustomerViewTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().sales_OutMat_Send_Clsfc == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center"},
	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center",hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"sales_OutMat_Send_Clsfc", headerHozAlign:"center" ,hozAlign:"left"},
 	{title:"거래처코드", field:"sales_OutMat_Client_Code", headerHozAlign:"center",hozAlign:"left"},
 	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center",hozAlign:"left"},
 	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center",hozAlign:"left"},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center",hozAlign:"left"},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center",hozAlign:"right"},
 	{title:"금액", field:"sales_OutMat_Price", headerHozAlign:"center",hozAlign:"right", formatter : "money", formatterParams: {precision: false}}
 	],
});

$("#SOCL_SearchBtn").click(function(){
	SOCL_Search()
})

function SOCL_Search() {
	datas = {
		startDate : $("#sgoodsOutputCustomerView_startDate").val(),
		endDate : $("#sgoodsOutputCustomerView_endDate").val(),
		clientCode : $(".Client_Code1").val(),
		itemSendClsfc : $("#outMatTypeCustomerViewSelectBox option:selected").val()
	}
	
	salesDeliveryCustomerViewTable.setData("salesDeliveryReportLXRest/SOCL_Search",datas);
}

var salesDeliveryListTable = new Tabulator("#salesDeliveryListTable", {
	layoutColumnsOnNewData : true,
	 //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().sales_OutMat_Client_Code == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		salesDeliveryListTable.deselectRow();
		row.select();
		SDC_Search(row.getData().sales_OutMat_Client_Code)
	},
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", hozAlign:"right"},
	{title:"거래처코드", field:"sales_OutMat_Client_Code", headerHozAlign:"center", hozAlign:"center"},
	{title:"거래처명", field:"sales_OutMat_Client_Name", headerHozAlign:"center"},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center" , hozAlign:"right", formatter : "money", formatterParams: {precision: false}}
 	]
});

$("#SDL_SearchBtn").click(function(){
	SDL_Search();
})

function SDL_Search(){
	var thisMonth = $("#selectedMonth").val()+"-01";
	var nextMontth = new Date($("#selectedMonth").val()+"-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth()+1)).toISOString().substring(0, 10);

	var datas = {
		startDate : thisMonth,
		endDate : nextMontth
	}
	salesDeliveryListTable.setData("salesDeliveryReportLXRest/SDL_Search",datas);
}

var salesDeliveryCustomerTable = new Tabulator("#salesDeliveryCustomerTable", {
	layoutColumnsOnNewData : true,
	 //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().sales_OutMat_Send_Clsfc_Name == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
         }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"sales_OutMat_No", headerHozAlign: "center", hozAlign: "center"},
	{title:"수주번호", field:"sales_OutMat_Cus_No", headerHozAlign: "center", hozAlign: "center"},
 	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center", hozAlign:"center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"sales_OutMat_Send_Clsfc_Name", headerHozAlign:"center"},
	{title:"품목코드", field:"sales_OutMat_Code", headerHozAlign:"center"},
	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center"},
	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center"},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center"},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center" , hozAlign:"right"},
 	{title:"금액", field:"sales_OutMat_Price", headerHozAlign:"center", hozAlign:"right", formatter : "money", formatterParams: {precision: false}}
 	],
});

function SDC_Search(clientCode){
	var thisMonth = $("#selectedMonth").val()+"-01";
	var nextMontth = new Date($("#selectedMonth").val()+"-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth()+1)).toISOString().substring(0, 10);
	
	var datas = {
		startDate : thisMonth,
		endDate : nextMontth,
		clientCode : clientCode
	}
	salesDeliveryCustomerTable.setData("salesDeliveryReportLXRest/SDC_Search", datas);
}
