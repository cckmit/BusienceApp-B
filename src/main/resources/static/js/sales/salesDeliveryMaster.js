var salesDeliveryCustomerViewTable = new Tabulator("#salesDeliveryCustomerViewTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	layoutColumnsOnNewData: true,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().sales_OutMat_Send_Clsfc == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	columns: [
		{ title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum"},
		{ title: "LotNo", field: "sales_OutMat_Lot_No", headerHozAlign: "center",hozAlign: "center"},
		{ title: "출고일자", field: "sales_OutMat_Date", headerHozAlign: "center", hozAlign: "left", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "출고구분", field: "sales_OutMat_Send_Clsfc", headerHozAlign: "center", hozAlign: "left" },
		{ title: "거래처코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "거래처명", field: "sales_OutMat_Client_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품목코드", field: "sales_OutMat_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품목명", field: "sales_OutMat_Name", headerHozAlign: "center", hozAlign: "left" },
		{ title: "규격", field: "sales_OutMat_STND_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "단위", field: "sales_OutMat_UNIT", headerHozAlign: "center", hozAlign: "left" },
		{ title: "수량", field: "sales_OutMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "금액", field: "sales_OutMat_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
	],
});

$("#SOCL_SearchBtn").click(function() {
	SOCL_Search()
})

function SOCL_Search() {
	datas = {
		startDate: $("#sgoodsOutputCustomerView_startDate").val(),
		endDate: $("#sgoodsOutputCustomerView_endDate").val(),
		ClientCode: $(".Client_Code1").val(),
		ItemSendClsfc: $("#outMatTypeCustomerViewSelectBox option:selected").val()
	}
	
	console.log(datas);

	salesDeliveryCustomerViewTable.setData("salesDeliveryReportRest/SOCL_Search", datas)
	
	console.log(salesDeliveryCustomerViewTable);
}

var salesDeliveryListTable = new Tabulator("#salesDeliveryListTable", {
	layoutColumnsOnNewData: true,
	selectable: true,
	height: "calc(100% - 175px)",
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().sales_OutMat_Client_Code == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	//행클릭 이벤트
	rowClick: function(e, row) {
		SDC_Search(row.getData().sales_OutMat_Client_Code)
	},
	columns: [
		{ formatter:"rowSelection", headerHozAlign: "center", titleFormatter:"rowSelection", align:"center", headerSort:false},
		{ title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum"},
		{ title: "거래처코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처명", field: "sales_OutMat_Client_Name", headerHozAlign: "center" },
		{ title: "수량", field: "sales_OutMat_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
	]
});

var salesDeliveryCustomerTable = new Tabulator("#salesDeliveryCustomerTable", {
	layoutColumnsOnNewData: true,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().sales_OutMat_Send_Clsfc_Name == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "sales_OutMat_No", headerHozAlign: "center", hozAlign: "center" },
		{ title: "수주번호", field: "sales_OutMat_Cus_No", headerHozAlign: "center", hozAlign: "center" },
		{ title: "출고일자", field: "sales_OutMat_Date", headerHozAlign: "center", hozAlign: "center", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "출고구분", field: "sales_OutMat_Send_Clsfc_Name", headerHozAlign: "center" },
		{ title: "품목코드", field: "sales_OutMat_Code", headerHozAlign: "center" },
		{ title: "품명", field: "sales_OutMat_Name", headerHozAlign: "center" },
		{ title: "규격", field: "sales_OutMat_STND_1", headerHozAlign: "center" },
		{ title: "단위", field: "sales_OutMat_UNIT", headerHozAlign: "center" },
		{ title: "수량", field: "sales_OutMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "금액", field: "sales_OutMat_Price", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
	],
});

$("#SDL_SearchBtn").click(function() {
	salesDeliveryCustomerTable.clearData();
	SDL_Search();
})

function SDL_Search() {
	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth
	}
	salesDeliveryListTable.setData("salesDeliveryReportRest/SDL_Search", datas)
	.then(function(){
		UseBtn();
	})
}

function SDC_Search(clientCode) {
	
	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth,
		ClientCode: clientCode
	}
	salesDeliveryCustomerTable.setData("salesDeliveryReportRest/SDC_Search", datas);
}

$("#preView-xlsx").click(function(){
	selectedRow = salesDeliveryListTable.getData("selected");
	var clientCodeArr = new Array();
	
	if (selectedRow.length == 0){
		alert("행을 선택해 주세요.");
		return false;
	} else if(selectedRow.length > 100){
		alert("100건까지 변환 가능 합니다.");
		return false;
	}
	
	for(let i=0;i<selectedRow.length;i++){
		clientCodeArr.push(selectedRow[i].sales_OutMat_Client_Code)
	}
	
	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth,
		clientCodeArr: clientCodeArr
	}
	homtaxApiListSave(datas);
})

function homtaxApiListSave(data){
	var datas = data;
	$.ajax({
		method : "post",
		url : "salesDeliveryReportRest/hometaxApiDataSave",
        data: datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token)
		},
		success : function() {
			var ym = $("#selectedMonth").val();
			hometaxApiPopup(ym)
		}
	});
}