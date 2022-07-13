var matInputListTable = new Tabulator("#matInputListTable", {
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData : true,
	selectable: true,
	height: "calc(100% - 175px)",
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", headerFilter: true, hozAlign: "center", formatter: "rownum"},
		{ title: "발주번호", field: "inMat_Order_No", headerHozAlign: "center", headerFilter: true, hozAlign: "left"},
		{ title: "Lot번호", field: "inMat_Lot_No", headerHozAlign: "center", headerFilter: true, hozAlign: "left"},
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "left"},
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "left"},
		{ title: "규격1", field: "inMat_STND_1", headerHozAlign: "center", headerFilter: true, hozAlign: "left"},
		{ title: "규격2", field: "inMat_STND_2", headerHozAlign: "center", headerFilter: true, hozAlign: "left"},
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", headerFilter: true, hozAlign: "right" },
		{ title: "입고단가", field: "inMat_Unit_Price", headerHozAlign: "center", headerFilter: true, hozAlign: "right",
			formatter: "money", formatterParams: { precision: false } },
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", headerFilter: true, hozAlign: "right",
			formatter: "money", formatterParams: { precision: false } },
		{ title: "일자", field: "inMat_Date", headerHozAlign: "center", headerFilter: true, hozAlign: "left",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "외부LotNo", field: "inMat_External_Lot_No", headerHozAlign: "center", headerFilter: true, hozAlign: "left" },
		{ title: "비고", field: "inMat_Info_Remark", headerHozAlign: "center", headerFilter: true, hozAlign: "right" }	
	]
});

$("#MIL_SearchBtn").click(function(){
	MIL_Search();
})

function MIL_Search(){
	var datas = {
		startDate: $("#matInputList_startDate").val(),
		endDate: $("#matInputList_endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE1").val(),
		clientCode: $("#InMat_Client_Code1").val(),
		ItemSendClsfc: $("#inMatTypeListSelectBox option:selected").val()
	}
	matInputListTable.setData("matInputRest/MIL_Search", datas)
}

$("#MIL_PrintBtn").click(function(){
	var datas = matInputListTable.getData("selected");
	var LotList = new Array();
	
	for(let i=0;i<datas.length;i++){
		LotList.push({lotNo : datas[i].inMat_Lot_No})
	}
	$.ajax({
		method : "post",
		url: "LabelPrintRest/rawMaterialLabelSelect",
		data: JSON.stringify(LotList),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(result) {
			RawMaterialPrinter(result);
		}				
	});
})

var matInputItemViewTable = new Tabulator("#matInputItemViewTable", {
	layoutColumnsOnNewData : true,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Lot_No == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum"},
		{ title: "발주번호", field: "inMat_Order_No", headerHozAlign: "center", hozAlign: "left"},
		{ title: "Lot번호", field: "inMat_Lot_No", headerHozAlign: "center", hozAlign: "left"},
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", hozAlign: "left"},
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", hozAlign: "left"},
		{ title: "규격1", field: "inMat_STND_1", headerHozAlign: "center", hozAlign: "left"},
		{ title: "규격2", field: "inMat_STND_2", headerHozAlign: "center", hozAlign: "left"},
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", hozAlign: "left" },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "입고단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false } },
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false } },
		{ title: "일자", field: "inMat_Date", headerHozAlign: "center", hozAlign: "left",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "비고", field: "inMat_Info_Remark", headerHozAlign: "center", hozAlign: "right"}
	]
});

$("#MIIL_SearchBtn").click(function(){
	MIIL_Search();
})

function MIIL_Search(){
	var datas = {
		startDate: $("#matInputItemView_startDate").val(),
		endDate: $("#matInputItemView_endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE2").val(),
		ItemSendClsfc: $("#inMatTypeItemViewSelectBox option:selected").val()
	}
	matInputItemViewTable.setData("matInputRest/MIOL_Search", datas)
}

var matInputCustomerViewTable = new Tabulator("#matInputCustomerViewTable", {
	layoutColumnsOnNewData : true,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Lot_No == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum"},
		{ title: "발주번호", field: "inMat_Order_No", headerHozAlign: "center", hozAlign: "left"},
		{ title: "Lot번호", field: "inMat_Lot_No", headerHozAlign: "center", hozAlign: "left"},
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", hozAlign: "left"},
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", hozAlign: "left"},
		{ title: "규격1", field: "inMat_STND_1", headerHozAlign: "center", hozAlign: "left"},
		{ title: "규격2", field: "inMat_STND_2", headerHozAlign: "center", hozAlign: "left"},
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", hozAlign: "left" },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "입고단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false } },
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false } },
		{ title: "일자", field: "inMat_Date", headerHozAlign: "center", hozAlign: "left",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "비고", field: "inMat_Info_Remark", headerHozAlign: "center", hozAlign: "right"}
	]
});

$("#MICL_SearchBtn").click(function(){
	MICL_Search();
})

function MICL_Search(){
	var datas = {
		startDate: $("#matInputCustomerView_startDate").val(),
		endDate: $("#matInputCustomerView_endDate").val(),
		ClientCode: $("#InMat_Client_Code2").val(),
		ItemSendClsfc: $("#inMatTypeCustomerViewSelectBox option:selected").val()
	}
	matInputCustomerViewTable.setData("matInputRest/MIOL_Search", datas)
}

var matInputDeliveryListTable = new Tabulator("#matInputDeliveryListTable", {
	layoutColumnsOnNewData : true,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().inMat_Client_Code == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		matInputDeliveryListTable.deselectRow();
		row.select();
		MIDS_Search(row.getData().inMat_Client_Code);
	},
	columns: [
		{ title: "순번", field: "rowNum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum"},
		{ title: "거래처코드", field: "inMat_Client_Code", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center", hozAlign: "left"},
		{ title: "입고금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false } }
	]
});

$("#MIDM_SearchBtn").click(function(){
	matInputDeliveryItemTable.clearData();
	MIDM_Search();
})

function MIDM_Search() {

	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth
	}
	
	matInputDeliveryListTable.setData("matInputRest/MIDM_Search", datas);
}

var matInputDeliveryItemTable = new Tabulator("#matInputDeliveryItemTable", {
	layoutColumnsOnNewData : true,
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "inMat_No", headerHozAlign: "center", hozAlign: "center" },
		{ title: "입고일자", field: "inMat_Date", headerHozAlign: "center", hozAlign: "center",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "입고구분", field: "inMat_Rcv_Clsfc", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품명", field: "inMat_Name", headerHozAlign: "center", hozAlign: "left"},
		{ title: "규격1", field: "inMat_STND_1", headerHozAlign: "center", hozAlign: "left"},
		{ title: "규격2", field: "inMat_STND_2", headerHozAlign: "center", hozAlign: "left"},
		{ title: "단위", field: "inMat_UNIT", headerHozAlign: "center", hozAlign: "left" },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", hozAlign: "right"},
		{ title: "단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false }},
		{ title: "금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right",
			formatter: "money", formatterParams: { precision: false }}
	],
});

function MIDS_Search(clientCode) {

	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth,
		clientCode: clientCode
	}
	matInputDeliveryItemTable.setData("matInputRest/MIDS_Search", datas);
}
