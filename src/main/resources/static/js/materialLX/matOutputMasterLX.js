var matOutputListTable = new Tabulator("#matOutputListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
 	columns:[
 	{title:"순번", field:"rownum", headerHozAlign: "center", headerFilter:true, hozAlign: "center", formatter:"rownum"},
	{title:"출고일자", field:"om_OutDate", headerHozAlign:"center", headerFilter:true, hozAlign:"left", 
		formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"om_Send_Clsfc_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"부서명", field:"om_DeptName", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"Lot번호", field:"om_LotNo", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품목코드", field:"om_ItemCode", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"품명", field:"om_ItemName", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"규격1", field:"om_Item_Stnd_1", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"규격2", field:"om_Item_Stnd_2", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"분류1", field:"om_Item_CLSFC_1_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
	{title:"분류2", field:"om_Item_CLSFC_2_Name", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"단위", field:"om_UNIT", headerHozAlign:"center", headerFilter:true, hozAlign:"left"},
 	{title:"수량", field:"om_Qty", headerHozAlign:"center", headerFilter:true, hozAlign:"right"}
	]
});

$("#MOL_SearchBtn").click(function(){
	MOL_Search();
})

function MOL_Search(){
	var data = {
		startDate : $("#matOutputList_startDate").val(),
		endDate : $("#matOutputList_endDate").val(),
		itemCode : $("#PRODUCT_ITEM_CODE1").val(),
		ItemSendClsfc : $("#outMatTypeListSelectBox option:selected").val(),
		DeptCode : $("#outMatDeptListSelectBox option:selected").val()
	}
	matOutputListTable.setData("matOutputLXRest/MOL_Search", data);
}

var matOutputItemViewTable = new Tabulator("#matOutputItemViewTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().om_Send_Clsfc_Name == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
    	}
    },
	height:"calc(100% - 175px)",
 	columns:[
	{title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", formatter:"rownum"},
	{title:"출고일자", field:"om_OutDate", headerHozAlign:"center", hozAlign:"left", 
		formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"om_Send_Clsfc_Name", headerHozAlign:"center", hozAlign:"left"},
 	{title:"부서명", field:"om_DeptName", headerHozAlign:"center", hozAlign:"left"},
	{title:"Lot번호", field:"om_LotNo", headerHozAlign:"center", hozAlign:"left"},
 	{title:"품목코드", field:"om_ItemCode", headerHozAlign:"center", hozAlign:"left"},
 	{title:"품명", field:"om_ItemName", headerHozAlign:"center", hozAlign:"left"},
 	{title:"규격1", field:"om_Item_Stnd_1", headerHozAlign:"center", hozAlign:"left"},
	{title:"규격2", field:"om_Item_Stnd_2", headerHozAlign:"center", hozAlign:"left"},
	{title:"분류1", field:"om_Item_CLSFC_1_Name", headerHozAlign:"center", hozAlign:"left"},
	{title:"분류2", field:"om_Item_CLSFC_2_Name", headerHozAlign:"center", hozAlign:"left"},
 	{title:"단위", field:"om_UNIT", headerHozAlign:"center", hozAlign:"left"},
 	{title:"수량", field:"om_Qty", headerHozAlign:"center", hozAlign:"right"}
 	]
});

$("#MOIL_SearchBtn").click(function(){
	MOIL_Search();
})

function MOIL_Search(){
	var data = {
		startDate : $("#matOutputItemView_startDate").val(),
		endDate : $("#matOutputItemView_endDate").val(),
		itemCode : $("#PRODUCT_ITEM_CODE2").val(),
		ItemSendClsfc : $("#outMatTypeItemViewSelectBox option:selected").val()
	}
	matOutputItemViewTable.setData("matOutputLXRest/MOOL_Search", data);
}

var matOutputDeptViewTable = new Tabulator("#matOutputDeptViewTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
    //Sub Total 색상
	rowFormatter: function(row){
		if(row.getData().om_Send_Clsfc_Name == "Sub Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },
	height:"calc(100% - 175px)",
 	columns:[
	{title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", formatter:"rownum"},
	{title:"출고일자", field:"om_OutDate", headerHozAlign:"center", hozAlign:"left", 
		formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"om_Send_Clsfc_Name", headerHozAlign:"center", hozAlign:"left"},
 	{title:"부서명", field:"om_DeptName", headerHozAlign:"center", hozAlign:"left"},
	{title:"Lot번호", field:"om_LotNo", headerHozAlign:"center", hozAlign:"left"},
 	{title:"품목코드", field:"om_ItemCode", headerHozAlign:"center", hozAlign:"left"},
 	{title:"품명", field:"om_ItemName", headerHozAlign:"center", hozAlign:"left"},
 	{title:"규격1", field:"om_Item_Stnd_1", headerHozAlign:"center", hozAlign:"left"},
	{title:"규격2", field:"om_Item_Stnd_2", headerHozAlign:"center", hozAlign:"left"},
	{title:"분류1", field:"om_Item_CLSFC_1_Name", headerHozAlign:"center", hozAlign:"left"},
	{title:"분류2", field:"om_Item_CLSFC_2_Name", headerHozAlign:"center", hozAlign:"left"},
 	{title:"단위", field:"om_UNIT", headerHozAlign:"center", hozAlign:"left"},
 	{title:"수량", field:"om_Qty", headerHozAlign:"center", hozAlign:"right"}
 	]
});

$("#MODL_SearchBtn").click(function(){
	MODL_Search();
})

function MODL_Search(){
	var data = {
		startDate : $("#matOutputDeptView_startDate").val(),
		endDate : $("#matOutputDeptView_endDate").val(),
		DeptCode : $("#outMatDeptViewSelectBox option:selected").val(),
		ItemSendClsfc : $("#outMatTypeDeptViewSelectBox option:selected").val()
	}
	matOutputDeptViewTable.setData("matOutputLXRest/MOOL_Search", data);
}

var matOutputDeliveryListTable = new Tabulator("#matOutputDeliveryListTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		matOutputDeliveryListTable.deselectRow();
		row.select();
		MODS_Search(row.getData().om_DeptCode);
	},
 	columns:[
 	{title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum"},
	{title:"부서코드", field:"om_DeptCode", headerHozAlign:"center",hozAlign:"center"},
	{title:"부서명", field:"om_DeptName", headerHozAlign:"center",hozAlign:"left"},
 	{title:"수량", field:"om_Qty", headerHozAlign:"center" ,hozAlign:"right",
		formatter : "money", formatterParams: {precision: false}}
 	],
});

$("#MODM_SearchBtn").click(function(){
	matOutputDeliveryItemTable.clearData();
	MODM_Search();
})

function MODM_Search() {

	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth
	}
	matOutputDeliveryListTable.setData("matOutputLXRest/MODM_Search", datas);
}

var matOutputDeliveryItemTable = new Tabulator("#matOutputDeliveryItemTable", { 
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
 	columns:[
 	{title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", formatter:"rownum"},
	{title:"출고일자", field:"om_OutDate", headerHozAlign:"center", hozAlign:"left", 
		formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 	{title:"출고구분", field:"om_Send_Clsfc_Name", headerHozAlign:"center", hozAlign:"left"},
 	{title:"품목코드", field:"om_ItemCode", headerHozAlign:"center", hozAlign:"left"},
 	{title:"품명", field:"om_ItemName", headerHozAlign:"center", hozAlign:"left"},
 	{title:"규격1", field:"om_Item_Stnd_1", headerHozAlign:"center", hozAlign:"left"},
	{title:"규격2", field:"om_Item_Stnd_2", headerHozAlign:"center", hozAlign:"left"},
	{title:"분류1", field:"om_Item_CLSFC_1_Name", headerHozAlign:"center", hozAlign:"left"},
	{title:"분류2", field:"om_Item_CLSFC_2_Name", headerHozAlign:"center", hozAlign:"left"},
 	{title:"단위", field:"om_UNIT", headerHozAlign:"center", hozAlign:"left"},
 	{title:"수량", field:"om_Qty", headerHozAlign:"center", hozAlign:"right"}
 	]
});

function MODS_Search(deptCode) {

	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth,
		DeptCode: deptCode
	}
	matOutputDeliveryItemTable.setData("matOutputLXRest/MODS_Search", datas);
}
