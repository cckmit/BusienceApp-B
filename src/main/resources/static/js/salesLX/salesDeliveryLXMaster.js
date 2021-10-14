var salesDeliveryCustomerViewTable = new Tabulator("#salesDeliveryCustomerViewTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
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
 	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center",hozAlign:"right"},
 	{title:"금액", field:"sales_OutMat_Price", headerHozAlign:"center",hozAlign:"right", formatter : "money", formatterParams: {precision: false}}
 	],
});

function SOCL_SearchBtn() {
	data = {
		startDate : $("#sgoodsOutputCustomerView_startDate").val(),
		endDate : $("#sgoodsOutputCustomerView_endDate").val(),
		sales_OutMat_Client_Code : $("#Sales_InMat_Client_Code").val(),
		sales_OutMat_Send_Clsfc : $("#outMatTypeCustomerViewSelectBox option:selected").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesDeliveryReportLXRest/SOCL_Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(SOCL_data) {
			console.log(SOCL_data);
			TableSetData(salesDeliveryCustomerViewTable,SOCL_data);
		}
	});
}

var salesDeliveryListTable = new Tabulator("#salesDeliveryListTable", { 
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
		//SOC_DeliveryCustomer(row.getData().sales_OutMat_Client_Code);
		SOC_DeliveryCustomer(row.getData().sales_OutMat_Client_Code);
	},
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center"},
	{title:"거래처코드", field:"sales_OutMat_Client_Code", headerHozAlign:"center",hozAlign:"center"},
	{title:"거래처명", field:"sales_OutMat_Client_Name", headerHozAlign:"center",hozAlign:"left", width:100},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center" ,hozAlign:"right", formatter : "money", formatterParams: {precision: false}}
 	],
});

function SOC_DeliveryListSearchBtn() {


	// 처리연월
var date = new Date();
var year = date.getFullYear();
var rawDate = $("#PrcsDatest").val();
//alert(rawDate);
var years = year.toString().substring(0,2);
var PrcsYear = rawDate.substring(0,2);
var PrcsMonth = rawDate.substring(2,4);
var LastDay = document.getElementById('LastDay').value;
//alert("years : " + years + " PrcsYear : " + PrcsYear + " PrcsMonth : " + PrcsMonth + '-' + LastDay);
var PrcsDate = years + PrcsYear + '-' + PrcsMonth + '-';
	datas = {
		PrcsDate : PrcsDate,
		RawDate : rawDate,
		LastDay : LastDay
	}
	
	$.ajax({
		method : "GET",
		url : "salesDeliveryReportLXRest/SOC_DeliverySearch?data="+ encodeURI(JSON.stringify(datas)),
		success : function(data) {
			
			if(data == "DateFormat") {
				alert("해당하는 데이터 정보가 없습니다.");
				
			} else if(data == "Success") {
				$.ajax({
					method : "GET",
					url : "salesDeliveryReportLXRest/SOC_DeliveryView?data="+ encodeURI(JSON.stringify(datas)),
					success : function(data) {
						console.log(data);
						TableSetData(salesDeliveryListTable,data);
						salesDeliveryCustomerTable.clearData();
					}
				});
			}
		}, error:function(request,status,error){
        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       }
		
	});
}

var salesDeliveryLastListTable = new Tabulator("#salesDeliveryLastListTable", { 
	 //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().sales_OutMat_Client_Code == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		salesDeliveryLastListTable.deselectRow();
		row.select();
		//console.log(row.getData().sales_OutMat_Client_Code);
		//SOC_DeliveryCustomer(row.getData().sales_OutMat_Client_Code);
		SOC_DeliveryLastCustomer(row.getData().sales_OutMat_Client_Code);
	},
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center"},
	{title:"거래처코드", field:"sales_OutMat_Client_Code", headerHozAlign:"center",hozAlign:"center"},
	{title:"거래처명", field:"sales_OutMat_Client_Name", headerHozAlign:"center",hozAlign:"left", width:100},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center" ,hozAlign:"right", formatter : "money", formatterParams: {precision: false}}
 	],
});

function SOC_DeliveryLastListSearchBtn() {


	// 처리연월
var date = new Date();
var year = date.getFullYear();
var rawDate = $("#LastMonthst").val();
//alert(rawDate);
var years = year.toString().substring(0,2);
var PrcsYear = rawDate.substring(0,2);
var LastMonth = rawDate.substring(2,4);
var LastDay = document.getElementById('LastDay').value;
//alert("years : " + years + " PrcsYear : " + PrcsYear + " LastMonth : " + LastMonth + '-' + LastDay);
var PrcsDate = years + PrcsYear + '-' + LastMonth + '-';

	datas = {
		PrcsDate : PrcsDate,
		RawDate : rawDate,
		LastDay : LastDay
	}
	
	$.ajax({
		method : "GET",
		url : "salesDeliveryReportLXRest/SOC_LastMonthSearch?data="+ encodeURI(JSON.stringify(datas)),
		success : function(data) {
			
			if(data == "DateFormat") {
				alert("해당하는 데이터 정보가 없습니다.");
				
			} else if(data == "Success") {
				$.ajax({
					method : "GET",
					url : "salesDeliveryReportLXRest/SOC_DeliveryView?data="+ encodeURI(JSON.stringify(datas)),
					success : function(data) {
						console.log(data);
						TableSetData(salesDeliveryLastListTable,data);
						salesDeliveryLastCustomerTable.clearData();
					}
				});
			}
		}, error:function(request,status,error){
        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       }
		
	});
}

var salesDeliveryCustomerTable = new Tabulator("#salesDeliveryCustomerTable", { 
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
 	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center",hozAlign:"center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"sales_OutMat_Send_Clsfc_Name", headerHozAlign:"center" ,hozAlign:"left"},
	{title:"품목코드", field:"sales_OutMat_Code", headerHozAlign:"center",hozAlign:"left"},
	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center",hozAlign:"left", width:155},
	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center",hozAlign:"left"},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center" ,hozAlign:"right", width:80},
 	{title:"금액", field:"sales_OutMat_Price", headerHozAlign:"center",hozAlign:"right", width:90, formatter : "money", formatterParams: {precision: false}}
 	],
});

function SOC_DeliveryCustomer(sales_OutMat_Client_Code) {

		// 처리연월
var date = new Date();
var year = date.getFullYear();
var rawDate = $("#PrcsDatest").val();
//alert(rawDate);
var years = year.toString().substring(0,2);
var PrcsYear = rawDate.substring(0,2);
var PrcsMonth = rawDate.substring(2,4);
var LastDay = document.getElementById('LastDay').value;
//alert("years : " + years + " PrcsYear : " + PrcsYear + " PrcsMonth : " + PrcsMonth + '-' + LastDay);
var PrcsDate = years + PrcsYear + '-' + PrcsMonth + '-';

	data = {
		PrcsDate : PrcsDate,
		LastDay : LastDay,
		sales_OutMat_Client_Code : sales_OutMat_Client_Code
	}
	
	
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesDeliveryReportLXRest/SOC_DeliveryCustomer?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(salesDeliveryCustomerTable,data);			
		}
	});
}

var salesDeliveryLastCustomerTable = new Tabulator("#salesDeliveryLastCustomerTable", { 
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
 	{title:"출고일자", field:"sales_OutMat_Date", headerHozAlign:"center",hozAlign:"center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"sales_OutMat_Send_Clsfc_Name", headerHozAlign:"center" ,hozAlign:"left"},
	{title:"품목코드", field:"sales_OutMat_Code", headerHozAlign:"center",hozAlign:"left"},
	{title:"품명", field:"sales_OutMat_Name", headerHozAlign:"center",hozAlign:"left", width:155},
	{title:"규격", field:"sales_OutMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"sales_OutMat_UNIT", headerHozAlign:"center",hozAlign:"left"},
 	{title:"수량", field:"sales_OutMat_Qty", headerHozAlign:"center" ,hozAlign:"right", width:80},
 	{title:"금액", field:"sales_OutMat_Price", headerHozAlign:"center",hozAlign:"right", width:90, formatter : "money", formatterParams: {precision: false}}
 	],
});

function SOC_DeliveryLastCustomer(sales_OutMat_Client_Code) {
 console.log("실행")
		// 처리연월
var date = new Date();
var year = date.getFullYear();
var rawDate = $("#LastMonthst").val();
//alert(rawDate);
var years = year.toString().substring(0,2);
var PrcsYear = rawDate.substring(0,2);
var LastMonth = rawDate.substring(2,4);
var LastDay = document.getElementById('LastDay').value;
//alert("years : " + years + " PrcsYear : " + PrcsYear + " LastMonth : " + LastMonth + '-' + LastDay);
var PrcsDate = years + PrcsYear + '-' + LastMonth + '-';

	data = {
		PrcsDate : PrcsDate,
		LastDay : LastDay,
		sales_OutMat_Client_Code : sales_OutMat_Client_Code
	}
	
	
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "salesDeliveryReportLXRest/SOC_DeliveryLastCustomer?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(salesDeliveryLastCustomerTable,data);			
		}
	});
}
