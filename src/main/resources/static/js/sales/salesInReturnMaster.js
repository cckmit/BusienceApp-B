var salesInReturnInsertTable = new Tabulator("#salesInReturnInsertTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowClick: function(e, row) {
		row.toggleSelect();
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", align: "center" },
		{ title: "Lot번호", field: "sales_InMat_Lot_No", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품목코드", field: "sales_InMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "sales_InMat_Name", headerHozAlign: "center" },
		{ title: "규격", field: "sales_InMat_STND_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "규격2", field: "sales_InMat_STND_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류1", field: "sales_InMat_Item_Clsfc_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류2", field: "sales_InMat_Item_Clsfc_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "재질", field: "sales_InMat_Item_Material", headerHozAlign: "center", hozAlign: "left" },
		{ title: "입고수량", field: "sales_InMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "반품수량", field: "sales_InMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "입고구분", field: "sales_InMat_Rcv_Clsfc", visible: false },
		{ title: "입고구분", field: "sales_InMat_Rcv_Clsfc_Name", headerHozAlign: "center" },
		{ title: "입고일", field: "sales_InMat_Date", headerHozAlign: "center" }
	]
});

// fgoodsInReturn 목록 검색
function SIRI_Search() {
	datas = {
		ItemCode: $('#PRODUCT_ITEM_CODE1').val()
	}

	salesInReturnInsertTable.setData("salesInReturnRest/SIRI_Search", datas);

	console.log(salesInReturnInsertTable);

}

//SIRI_Save
function SIRI_Save() {

	selectedData = salesInReturnInsertTable.getData("selected");
	dataList = [];

	// 선택한 행이 있을경우에 저장이 가능하다.
	//만약 선택된행에서 반품수량이 0 이면 리스트에서 제외
	for (let i = 0; i < selectedData.length; i++) {
		dataList.push(selectedData[i]);
	}
	if (dataList.length == 0) {
		alert("저장할 목록이 없습니다.");
		return false;
	}

	$.ajax({
		method: "post",
		url: "salesInReturnRest/SIRI_Save",
		data: { dataList: JSON.stringify(dataList) },
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(datas) {
			if (datas) {
				alert("저장되었습니다");
				SIRI_Search();
			} else {
				alert("오류가 발생했습니다. 다시 시도해주십시오.");
			}
		}
	});
}



var salesInReturnSearchTable = new Tabulator("#salesInReturnSearchTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", align: "center" },
		{ title: "Lot번호", field: "sales_Small_Packing_LotNo", headerHozAlign: "center", hozAlign: "left" },
		{ title: "품목코드", field: "sales_Packing_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "sales_Packing_Name", headerHozAlign: "center" },
		{ title: "규격", field: "sales_Packing_STND_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "규격2", field: "sales_Packing_STND_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류1", field: "sales_Packing_Item_Clsfc_1", headerHozAlign: "center", hozAlign: "left" },
		{ title: "분류2", field: "sales_Packing_Item_Clsfc_2", headerHozAlign: "center", hozAlign: "left" },
		{ title: "재질", field: "sales_Packing_Item_Material", headerHozAlign: "center", hozAlign: "left" },
		{ title: "단위", field: "sales_Packing_Unit", headerHozAlign: "center", hozAlign: "left" },
		{ title: "반품수량", field: "sales_Packing_Qty", align: "right", headerHozAlign: "center" },
		{ title: "입고구분", field: "sales_Packing_Status_Name", headerHozAlign: "center" },
		{ title: "입고일", field: "sales_Large_Packing_Time", headerHozAlign: "center" },
		{ title: "반품일", field: "sales_Packing_Return_Date", headerHozAlign: "center" },
		{ title: "작업자명", field: "sales_Packing_Modifier", headerHozAlign: "center" }
	]
});

// fgoodsInReturn 목록 검색
function SIRS_Search() {
	datas = {
		startDate: $("#SIRS_startDate").val(),
		endDate: $("#SIRS_endDate").val(),
		ItemCode: $('#PRODUCT_ITEM_CODE2').val(),
	}

	salesInReturnSearchTable.setData("salesPackingRest/SIL_Search", datas);

	console.log(salesInReturnSearchTable);

}