var salesOutReturnInsertTable = new Tabulator("#salesOutReturnInsertTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowDblClick: function(e, row) {
		row.toggleSelect();
	},
	columns: [
		{ formatter: "rowSelection", titleFormatter: "rowSelection", headerHozAlign: "center", hozAlign: "center", headerSort: false },
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", align: "center" },
		{ title: "수주No", field: "sales_OutMat_Cus_No", headerHozAlign: "center" },
		{ title: "LotNo", field: "sales_OutMat_Lot_No", headerHozAlign: "center" },
		{ title: "품목코드", field: "sales_OutMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "sales_OutMat_Name", headerHozAlign: "center" },
		{ title: "출고수량", field: "sales_OutMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "반품수량", field: "sales_OutMat_Qty", align: "right", headerHozAlign: "center" },
		{ title: "단가", field: "sales_OutMat_Unit_Price", align: "right", headerHozAlign: "center" },
		{ title: "금액", field: "sales_OutMat_Price", align: "right", headerHozAlign: "center", formatter: "money", formatterParams: { precision: false }, width: 100 },
		{ title: "거래처코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center" },
		{ title: "거래처명", field: "sales_OutMat_Client_Name", headerHozAlign: "center" },
		{ title: "출고구분", field: "sales_OutMat_Send_Clsfc_Name", headerHozAlign: "center" },
		{ title: "출고일", field: "sales_OutMat_Date", headerHozAlign: "center" },
		{ title: "데이터삽입시간", field: "sales_OutMat_dInsert_Time", headerHozAlign: "center" },
		{ title: "작업자명", field: "sales_OutMat_Modifier", headerHozAlign: "center" }
	]
});

// fgoodsInReturn 목록 검색
function SORI_Search() {
	datas = {
		startDate: $("#SORI_startDate").val(),
		endDate: $("#SORI_endDate").val(),
		ItemCode: $('#PRODUCT_ITEM_CODE1').val(),
		ClientCode: $('#Sales_OutMat_Client_Code1').val()
	}

	salesOutReturnInsertTable.setData("salesOutputRest/SORI_Search", datas);

}

//SORI_Save
function SORI_Save() {
	//선택된 행만 저장
	//만약 선택된행에서 반품수량이 0 이면 저장 안함
	selectedData = salesOutReturnInsertTable.getData("selected");
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
		url: "salesOutReturnRest/SORI_Save",
		data: { dataList: JSON.stringify(dataList) },
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(datas) {
			if (datas) {
				alert("저장되었습니다.");
				//SORI_Search();
			} else if (datas) {
				alert("중복된 값이 있습니다.");
			}
		}
	});
}

var salesOutReturnSearchTable = new Tabulator("#salesOutReturnSearchTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", headerHozAlign: "center", align: "center" },
		{ title: "수주No", field: "sales_OutMat_Cus_No", headerHozAlign: "center" },
		{ title: "LotNo", field: "sales_OutMat_Lot_No", headerHozAlign: "center" },
		{ title: "품목코드", field: "sales_OutMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "sales_OutMat_Name", headerHozAlign: "center" },
		{ title: "반품수량", field: "sales_OutReturn_Qty", align: "right", headerHozAlign: "center" },
		{ title: "단가", field: "sales_OutMat_Unit_Price", align: "right", headerHozAlign: "center" },
		{ title: "금액", field: "sales_OutMat_Price", align: "right", headerHozAlign: "center", formatter: "money", formatterParams: { precision: false }, width: 100  },
		{ title: "거래처코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center" },
		{ title: "거래처명", field: "sales_OutMat_Client_Name", headerHozAlign: "center" },
		{ title: "출고구분", field: "sales_OutMat_Send_Clsfc_Name", headerHozAlign: "center" },
		{ title: "출고일", field: "sales_OutMat_Date", headerHozAlign: "center" },
		{ title: "반품일", field: "sales_OutMat_dInsert_Time", headerHozAlign: "center" },
		{ title: "작업자명", field: "sales_OutMat_Modifier", headerHozAlign: "center" }
	]
});

// fgoodsInReturn 목록 검색
function SORS_Search() {
	datas = {
		startDate: $("#SORS_startDate").val(),
		endDate: $("#SORS_endDate").val(),
		ItemCode: $('#PRODUCT_ITEM_CODE2').val(),
		ClientCode: $('#Sales_OutMat_Client_Code2').val()
	}
	
	salesOutReturnSearchTable.setData("salesOutputRest/SORS_Search", datas)

}