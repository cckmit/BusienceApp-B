var matOutputListTable = new Tabulator("#matOutputListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", headerFilter:true, hozAlign: "center"},
	{title:"출고일자", field:"outMat_Date", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:150, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"outMat_Send_Clsfc_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:100},
	{title:"수화인", field:"outMat_Consignee_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:100},
 	{title:"품목코드", field:"outMat_Code", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"outMat_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:170},
 	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:120},
 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"},
 	{title:"등록자", field:"outMat_Modifier", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:80},
 	{title:"등록일자", field:"outMat_dInsert_Time", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:140, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}}
	]
});

function MO_ListViewSearchBtn(){
	data = {
		startDate : $("#matOutputList_startDate").val(),
		endDate : $("#matOutputList_endDate").val(),
		outMat_Code : $("#PRODUCT_ITEM_CODE1").val(),
		outMat_Send_Clsfc_Name : $("#outMatTypeListSelectBox option:selected").val(),
		outMat_Dept_Name : $("#outMatDeptListSelectBox option:selected").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "matOutputReportLXRest/MO_ListView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(matOutputListTable,data);
		}
	});
}

var matOutputItemViewTable = new Tabulator("#matOutputItemViewTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().outMat_Send_Clsfc_Name == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
	{title:"출고일자", field:"outMat_Date", headerHozAlign:"center",hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"outMat_Send_Clsfc_Name", headerHozAlign:"center" ,hozAlign:"center"},
 	{title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center", hozAlign:"left", width:100},
	{title:"수화인", field:"outMat_Consignee_Name", headerHozAlign:"center", hozAlign:"left", width:100},
 	{title:"품목코드", field:"outMat_Code", headerHozAlign:"center",hozAlign:"left"},
 	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center",hozAlign:"right"},
    {title:"요청No", field:"outMat_ReqNo", headerHozAlign:"center", hozAlign:"left", width:130}
 	],
});

function MO_ItemViewSearchBtn() {
	data = {
		startDate : $("#matOutputItemView_startDate").val(),
		endDate : $("#matOutputItemView_endDate").val(),
		outMat_Code : $("#PRODUCT_ITEM_CODE2").val(),
		outMat_Send_Clsfc : $("#outMatTypeItemViewSelectBox option:selected").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "matOutputReportLXRest/MO_ItemView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(matOutputItemViewTable,data);
		}
	});
}

var matOutputDeptViewTable = new Tabulator("#matOutputDeptViewTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().outMat_Send_Clsfc_Name == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
	{title:"출고일자", field:"outMat_Date", headerHozAlign:"center",hozAlign:"left", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"outMat_Send_Clsfc_Name", headerHozAlign:"center" ,hozAlign:"center"},
	{title:"부서코드", field:"outMat_Dept_Code", headerHozAlign:"center",hozAlign:"left"},
 	{title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center", hozAlign:"left", width:100},
	{title:"수화인", field:"outMat_Consignee_Name", headerHozAlign:"center", hozAlign:"left", width:100},
 	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center",hozAlign:"right"},
    {title:"요청No", field:"outMat_ReqNo", headerHozAlign:"center", hozAlign:"left", width:130}
 	],
});

function MO_DeptViewSearchBtn() {
	data = {
		startDate : $("#matOutputDeptView_startDate").val(),
		endDate : $("#matOutputDeptView_endDate").val(),
		outMat_Send_Clsfc : $("#outMatTypeDeptViewSelectBox option:selected").val(),
		outMat_Dept_Name : $("#outMatDeptViewSelectBox option:selected").val()
	}

	$.ajax({
		method : "GET",
		dataType : "json",
		url : "matOutputReportLXRest/MO_DeptView?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(matOutputDeptViewTable,data);
		}
	});
}


var matOutputDeliveryListTable = new Tabulator("#matOutputDeliveryListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	 //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().outMat_Dept_Code == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		matOutputDeliveryListTable.deselectRow();
		row.select();
		MO_DeliveryItem(row.getData().outMat_Dept_Code);
	},
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center"},
	{title:"부서코드", field:"outMat_Dept_Code", headerHozAlign:"center",hozAlign:"center"},
	{title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center",hozAlign:"left", width:100},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center" ,hozAlign:"right", formatter : "money", formatterParams: {precision: false}}
 	],
});

function MO_DeliveryListSearchBtn() {

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
		url : "matOutputReportLXRest/MO_DeliverySearch?data="+ encodeURI(JSON.stringify(datas)),
		success : function(data) {
			console.log(datas);
			
			if(data == "DateFormat") {
				console.log("이걸 타고잇어");
				alert("해당하는 데이터 정보가 없습니다.");
				
			} else if(data == "Success") {
				$.ajax({
					method : "GET",
					url : "matOutputReportLXRest/MO_DeliveryView?data="+ encodeURI(JSON.stringify(datas)),
					success : function(data) {
						console.log(data);
						TableSetData(matOutputDeliveryListTable,data);
						matOutputDeliveryItemTable.clearData();
					}
				});
			}
		}, error:function(request,status,error){
        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       }
		
	});
}

var matOutputDeliveryLastListTable = new Tabulator("#matOutputDeliveryLastListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	 //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().outMat_Dept_Code == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		matOutputDeliveryLastListTable.deselectRow();
		row.select();
		MO_DeliveryLastItem(row.getData().outMat_Dept_Code);
	},
 	columns:[ //Define Table Columns
 	{title:"순번", field:"id", headerHozAlign: "center", hozAlign: "center"},
	{title:"부서코드", field:"outMat_Dept_Code", headerHozAlign:"center",hozAlign:"center"},
	{title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center",hozAlign:"left", width:100},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center" ,hozAlign:"right", formatter : "money", formatterParams: {precision: false}}
 	],
});

function MO_DeliveryLastListSearchBtn() {

	// 처리연월
var date = new Date();
var year = date.getFullYear();
var rawDate = $("#LastDatest").val();
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
		url : "matOutputReportLXRest/MO_LastMonthSearch?data="+ encodeURI(JSON.stringify(datas)),
		success : function(data) {
			console.log(datas);
			
			if(data == "DateFormat") {
				console.log("이걸 타고잇어");
				alert("해당하는 데이터 정보가 없습니다.");
				
			} else if(data == "Success") {
				$.ajax({
					method : "GET",
					url : "matOutputReportLXRest/MO_DeliveryView?data="+ encodeURI(JSON.stringify(datas)),
					success : function(data) {
						console.log(data);
						TableSetData(matOutputDeliveryLastListTable,data);
						matOutputDeliveryLastItemTable.clearData();
					}
				});
			}
		}, error:function(request,status,error){
        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       }
		
	});
}

var matOutputDeliveryItemTable = new Tabulator("#matOutputDeliveryItemTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	 //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().outMat_Code == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
         }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"outMat_No", headerHozAlign: "center", hozAlign: "center"},
 	{title:"출고일자", field:"outMat_Date", headerHozAlign:"center",hozAlign:"center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"outMat_Send_Clsfc_Name", headerHozAlign:"center" ,hozAlign:"left"},
	{title:"품목코드", field:"outMat_Code", headerHozAlign:"center",hozAlign:"left"},
	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left", width:155},
	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center",hozAlign:"left"},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center" ,hozAlign:"right"}
 	],
});

function MO_DeliveryItem(outMat_Dept_Code) {

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
		outMat_Dept_Code : outMat_Dept_Code
	}
	
	
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "matOutputReportLXRest/MO_DeliveryItem?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(matOutputDeliveryItemTable,data);
		}
	});
}

var matOutputDeliveryLastItemTable = new Tabulator("#matOutputDeliveryLastItemTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	 //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().outMat_Code == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
         }
    },
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
 	{title:"순번", field:"outMat_No", headerHozAlign: "center", hozAlign: "center"},
 	{title:"출고일자", field:"outMat_Date", headerHozAlign:"center",hozAlign:"center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"outMat_Rcv_Clsfc", headerHozAlign:"center" ,hozAlign:"left"},
	{title:"품목코드", field:"outMat_Code", headerHozAlign:"center",hozAlign:"left"},
	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left", width:155},
	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center",hozAlign:"left"},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center" ,hozAlign:"right"}
 	],
});

function MO_DeliveryLastItem(outMat_Dept_Code) {

		// 처리연월
var date = new Date();
var year = date.getFullYear();
var rawDate = $("#LastDatest").val();
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
		outMat_Dept_Code : outMat_Dept_Code
	}
	
	
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "matOutputReportLXRest/MO_DeliveryLastItem?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			console.log(data);
			TableSetData(matOutputDeliveryLastItemTable,data);
		}
	});
}