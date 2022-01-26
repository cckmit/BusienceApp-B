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
	{title:"수취인", field:"outMat_Consignee_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left", width:100},
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
	{title:"수취인", field:"outMat_Consignee_Name", headerHozAlign:"center", hozAlign:"left", width:100},
 	{title:"품목코드", field:"outMat_Code", headerHozAlign:"center",hozAlign:"left"},
 	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center",hozAlign:"right"}
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
	{title:"수취인", field:"outMat_Consignee_Name", headerHozAlign:"center", hozAlign:"left", width:100},
 	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
 	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center",hozAlign:"left", width:60},
 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center",hozAlign:"right"}
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

$("#MO_DeliveryListSearchBtn").click(function() {
	matOutputDeliveryItemTable.clearData();
	MO_DeliveryListSearchBtn();
})

function MO_DeliveryListSearchBtn() {

	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth
	}
	matOutputDeliveryListTable.setData("matOutputReportLXRest/MO_DeliverySearch", datas);
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

function MO_DeliveryItem(deptCode) {

    var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth,
		deptCode: deptCode
	}
	matOutputDeliveryItemTable.setData("matOutputReportLXRest/MO_DeliveryItem", datas);
}