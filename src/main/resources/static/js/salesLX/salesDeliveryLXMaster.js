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
	columns: [ //Define Table Columns
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "center" },
		{ title: "출고일자", field: "sales_OutMat_Date", headerHozAlign: "center", hozAlign: "left", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "출고구분", field: "sales_OutMat_Send_Clsfc", headerHozAlign: "center", hozAlign: "left" },
		{ title: "거래처코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품명", field: "sales_OutMat_Name", headerHozAlign: "center", hozAlign: "left" },
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
		clientCode: $(".Client_Code1").val(),
		itemSendClsfc: $("#outMatTypeCustomerViewSelectBox option:selected").val()
	}

	salesDeliveryCustomerViewTable.setData("salesDeliveryReportLXRest/SOCL_Search", datas);
}

var salesDeliveryListTable = new Tabulator("#salesDeliveryListTable", {
	layoutColumnsOnNewData: true,
	//Sub Total 색상
	rowFormatter: function(row) {
		if (row.getData().sales_OutMat_Client_Code == "Sub Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	height: "calc(100% - 175px)",
	//행클릭 이벤트
	rowClick: function(e, row) {
		salesDeliveryListTable.deselectRow();
		row.select();
		SDC_Search(row.getData().sales_OutMat_Client_Code)
	},
	columns: [ //Define Table Columns
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "right" },
		{ title: "거래처코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center", hozAlign: "center" },
		{ title: "거래처명", field: "sales_OutMat_Client_Name", headerHozAlign: "center" },
		{ title: "수량", field: "sales_OutMat_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
	]
});

$("#SDL_SearchBtn").click(function() {
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
	salesDeliveryListTable.setData("salesDeliveryReportLXRest/SDL_Search", datas);
}

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

function SDC_Search(clientCode) {
	var thisMonth = $("#selectedMonth").val() + "-01";
	var nextMontth = new Date($("#selectedMonth").val() + "-01");
	nextMontth = new Date(nextMontth.setMonth(nextMontth.getMonth() + 1)).toISOString().substring(0, 10);

	var datas = {
		startDate: thisMonth,
		endDate: nextMontth,
		clientCode: clientCode
	}
	salesDeliveryCustomerTable.setData("salesDeliveryReportLXRest/SDC_Search", datas);
}


// excel 세금계산서 양식 미리보기
var excelViewerTable = new Tabulator("#excelViewerTable", {
	layoutColumnsOnNewData: true,
	//Sub Total 색상
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "전자(세금)계산서 종류(01:일반, 02:영세율)", field: "sales_tax_bill_kind", headerHozAlign: "center", hozAlign: "center" },
		{ title: "작성일자", field: "sales_tax_bill_Date", headerHozAlign: "center", hozAlign: "center" },
		{ title: "공급자 등록번호("-" 없이 입력)", field: "supplier_Regist_Num_1", headerHozAlign: "center", hozAlign: "center", formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" } },
		{ title: "공급자 종사업장번호", field: "supplier_Regist_Num_2", headerHozAlign: "center" },
		{ title: "공급자 상호", field: "supplier_Regist_Title", headerHozAlign: "center" },
		{ title: "공급자 성명", field: "supplier_Regist_Name", headerHozAlign: "center" },
		{ title: "공급자 사업장주소", field: "supplier_Regist_Adr", headerHozAlign: "center" },
		{ title: "공급자 업태", field: "supplier_Regist_Business", headerHozAlign: "center" },
		{ title: "공급자 종목", field: "supplier_Regist_Event", headerHozAlign: "center", hozAlign: "right" },
		{ title: "공급자 이메일", field: "supplier_Regist_Email", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "공급받는자 등록번호("-" 없이 입력)", field: "supplier_Recipent_Num_1", headerHozAlign: "center" },
		{ title: "공급받는자 종사업자번호", field: "supplier_Recipent_Num2", headerHozAlign: "center" },
		{ title: "공급받는자 상호", field: "supplier_Recipent_Title", headerHozAlign: "center" },
		{ title: "공급받는자 성명", field: "supplier_Recipent_Name", headerHozAlign: "center", hozAlign: "right" },
		{ title: "공급받는자 사업장주소", field: "supplier_Recipent_Adr", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "공급받는자 업태", field: "supplier_Recipent_Business", headerHozAlign: "center" },
		{ title: "공급받는자 종목", field: "supplier_Recipent_Event", headerHozAlign: "center" },
		{ title: "공급받는자 이메일1", field: "supplier_Recipent_Email_1", headerHozAlign: "center" },
		{ title: "공급받는자 이메일2", field: "supplier_Recipent_Email_2", headerHozAlign: "center", hozAlign: "right" },
		{ title: "공급가액", field: "supply_Value", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "세액", field: "tax_Amount", headerHozAlign: "center" },
		{ title: "비고", field: "remarks", headerHozAlign: "center" },
		{ title: "일자1(2자리,작성년월 제외)", field: "date_1", headerHozAlign: "center" },
		{ title: "품목1", field: "item_1", headerHozAlign: "center", hozAlign: "right" },
		{ title: "규격1", field: "standard_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "수량1", field: "qty_1", headerHozAlign: "center" },
		{ title: "단가1", field: "unit_Price_1", headerHozAlign: "center" },
		{ title: "공급가액1", field: "supply_Value_1", headerHozAlign: "center" },
		{ title: "세액1", field: "tax_Amount_1", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목비고1", field: "item_Remarks_1", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "일자2(2자리,작성년월 제외)", field: "date_2", headerHozAlign: "center" },
		{ title: "품목2", field: "item_2", headerHozAlign: "center", hozAlign: "right" },
		{ title: "규격2", field: "standard_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "수량2", field: "qty_2", headerHozAlign: "center" },
		{ title: "단가2", field: "unit_Price_2", headerHozAlign: "center" },
		{ title: "공급가액2", field: "supply_Value_2", headerHozAlign: "center" },
		{ title: "세액2", field: "tax_Amount_2", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목비고2", field: "item_Remarks_2", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "일자3(2자리,작성년월 제외)", field: "date_3", headerHozAlign: "center" },
		{ title: "품목3", field: "item_3", headerHozAlign: "center", hozAlign: "right" },
		{ title: "규격3", field: "standard_3", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "수량3", field: "qty_3", headerHozAlign: "center" },
		{ title: "단가3", field: "unit_Price_3", headerHozAlign: "center" },
		{ title: "공급가액3", field: "supply_Value_3", headerHozAlign: "center" },
		{ title: "세액3", field: "tax_Amount_3", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목비고3", field: "item_Remarks_3", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "일자4(2자리,작성년월 제외)", field: "date_4", headerHozAlign: "center" },
		{ title: "품목4", field: "item_4", headerHozAlign: "center", hozAlign: "right" },
		{ title: "규격4", field: "standard_4", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "수량4", field: "qty_4", headerHozAlign: "center" },
		{ title: "단가4", field: "unit_Price_4", headerHozAlign: "center" },
		{ title: "공급가액4", field: "supply_Value_4", headerHozAlign: "center" },
		{ title: "세액4", field: "tax_Amount_4", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목비고4", field: "item_Remarks_4", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "현금", field: "cash", headerHozAlign: "center" },
		{ title: "수표", field: "check", headerHozAlign: "center" },
		{ title: "어음", field: "note", headerHozAlign: "center" },
		{ title: "외상미수금", field: "accounts_Receivable", headerHozAlign: "center", hozAlign: "right" },
		{ title: "영수(01),청구(02)", field: "classification", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } }
	],
});

/*function excel_PreView() {
	$("#excelWriteViewModal").modal("show");
	
	//excel_PreView_ajax()
}*/

/*function excel_PreView_ajax() {
	$.ajax({
		method: "get",
		data: datas,
		url: "customerManageRest/excel_PreView_ajax",
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(data, testStatus) {
			alert("삭제 완료 되었습니다.");
		}
	});
}*/
//trigger download of data.xlsx file
/*document.getElementById("download-xlsx").addEventListener("click", function() {
	salesDeliveryCustomerTable.download("xlsx", "data.xlsx", { sheetName: "My Data" });
});*/


