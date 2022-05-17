var tempStorageTable = new Tabulator("#tempStorageTable", {
	height: "calc(50% - 85px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData: true,
	//행클릭 이벤트
	rowFormatter: function(row) {
	},
	rowClick: function(e, row) {
		tempStorageTable.deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		MIForm_Search(row.getData().ts_ItemName, row.getData().ts_Qty, row.getData().ts_Client_Name);
		UseBtn();
		//$("#").focus();
	},
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "발주번호", field: "ts_OrderNo", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목코드", field: "ts_ItemCode" },
		{ title: "품목명", field: "ts_ItemName", headerHozAlign: "center" },
		{ title: "수량", field: "ts_Qty", hozAlign: "right" },
		{ title: "단가", field: "ts_Unit_Price", headerHozAlign: "center", hozAlign: "right" },
		{ title: "금액", field: "ts_Price", headerHozAlign: "center", hozAlign: "right" },
		{ title: "거래처코드", field: "ts_Client_Code", headerHozAlign: "center" },
		{ title: "거래처명", field: "ts_Client_Name", headerHozAlign: "center" },
		{ title: "가입고일", field: "ts_Date", headerHozAlign: "center" },
		{ title: "구분", field: "ts_Classfy", headerHozAlign: "center", hozAlign: "right", visible: false },
		{ title: "구분", field: "ts_Classfy_Name", headerHozAlign: "center", hozAlign: "right" }
	],
});

//matRequest 검색버튼
function MII_Search() {

	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		ItemCode: $("#PRODUCT_ITEM_CODE1").val(),
		ClientCode: $("#Temp_InMat_Client_Code").val(),
		ItemSendClsfc: "all",
		Condition: "N"
	}

	tempStorageTable.setData("matInputInspectionRest/MII_Search", datas)
		.then(function() {
			console.log(tempStorageTable);
			//list와 stock의 데이터를 없에준다
			matOutputSubTable.clearData();
			LotMasterTable.clearData();

			MOM_total = 0;
			LotMasterTable.redraw();
			ResetBtn()

			document.getElementById("Request_lName").value = "";
			document.getElementById("Request_lCode").value = "";
		})
}

$("#MII_SearchBtn").click(function() {
	MII_Search();
	MIS_Search();
})

// 출고구분 select를 구성하기위한 ajax
var output_dtl = dtlSelectList(18);

var matInputTable = new Tabulator("#matInputTable", {
	layoutColumnsOnNewData: true,
	height: "calc(50% - 90px)",
	rowFormatter: function(row) {
		//미출고가 없으면 빨간색으로
		if (row.getData().rs_Not_Stocked == 0) {
			row.getElement().style.color = "red";
		}
	},
	//행을 클릭하면 matLotMasterTable에 리스트가 나타남
	rowClick: function(e, row) {
		//미출고재고가 있다면
		if (row.getData().rs_Not_Stocked != 0) {
			matOutputSubTable.deselectRow();
			row.select();
			add_mode = false;
		}
	},
	rowSelected: function(row) {
		$("#Request_lCode").val(row.getData().rs_ItemCode);
		$("#Request_lName").val(row.getData().rs_ItemName);

		//LotMaster 품목코드로 검색
		LM_Search(row.getData().rs_ItemCode, row.getData().rs_RequestNo);
		matOutMatTable.clearData();

		ResetBtn();
	},
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "발주번호", field: "ts_OrderNo" },
		{ title: "품목코드", field: "ts_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "ts_ItemName", headerHozAlign: "center" },
		{ title: "수량", field: "ts_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "단가", field: "ts_Unit_Price", headerHozAlign: "center", hozAlign: "right" },
		{ title: "금액", field: "ts_Price", headerHozAlign: "center", hozAlign: "right" },
		{ title: "거래처코드", field: "ts_Client_Code", headerHozAlign: "center" },
		{ title: "거래처명", field: "ts_Client_Name", headerHozAlign: "center" },
		{ title: "입고일", field: "ts_Date", headerHozAlign: "center" },
		{ title: "구분", field: "ts_Classfy", headerHozAlign: "center" }]
});

function MIS_Search() {
	
	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		ItemCode: $("#PRODUCT_ITEM_CODE1").val(),
		ClientCode: $("#Temp_InMat_Client_Code").val(),
		ItemSendClsfc: "all",
		Condition: "Y"
	}
	
	matInputTable.setData("matInputInspectionRest/MII_Search", datas);
}

//matInputInspect 정보 삽입
function MIForm_Search(ItemName, Qty, ClientName) {
	var now = moment();
	$("#matInspectItemName").val(ItemName);
	$("#matInspectDate").val(now.format("YYYY-MM-DD"));
	$("#matInspectQty").val(Qty);
	$("#matInspectCustomer").val(ClientName);
}

function MOM_Search(requestNo, itemCode) {
	var datas = {
		om_RequestNo: requestNo,
		om_ItemCode: itemCode
	}
	$.ajax({
		method: "GET",
		async: false,
		url: "matOutputRest/MOM_Search",
		data: datas,
		success: function(MOM_datas) {
			matOutMatTable.setData(MOM_datas);
		}
	});
}

//MIF_Save
function MIF_Save() {
	// 배열 선언
	var value1 = new Array();
	var value2 = new Array();
	var value3 = new Array();
	var value4 = new Array();
	var value5 = new Array();
	var stnd1 = new Array();
	var stnd2 = new Array();
	var status = new Array();
	var value = 10;
	
	if($("#matInspectWorker").val() == "") {
		alert("검사자를 입력해주세요.");
		return false;
	}
	
	for (var i = 1; i < 10; i++) {
		standardDatas = {
			inMat_Inspect_Order_No: tempStorageTable.getData()[0].ts_OrderNo,
			inMat_Inspect_Number: i,
			inMat_Inspect_ItemCode: tempStorageTable.getData()[0].ts_ItemCode,
			inMat_Inspect_Qty: tempStorageTable.getData()[0].ts_Qty,
			inMat_Inspect_Worker: $("#matInspectWorker").val(),
			inMat_Inspect_Customer: tempStorageTable.getData()[0].ts_Client_Code,
			inMat_Inspect_Text: $("#inspectionText").val(),
			inMat_Inspect_Classfy: tempStorageTable.getData()[0].ts_Classfy
		}
	}

	console.log(standardDatas);

	// 측정 데이터
	for (var j = 0; j < value; j++) {

		value1.push({ inMat_Inspect_Value_1: $("input[name='Inspect_Value_1[]']")[j].value });

		value2.push({ inMat_Inspect_Value_2: $("input[name='Inspect_Value_2[]']")[j].value });

		value3.push({ inMat_Inspect_Value_3: $("input[name='Inspect_Value_3[]']")[j].value });

		value4.push({ inMat_Inspect_Value_4: $("input[name='Inspect_Value_4[]']")[j].value });

		value5.push({ inMat_Inspect_Value_5: $("input[name='Inspect_Value_5[]']")[j].value });
		
	}

	// 규격 데이터 
	var stndLength = 3;
	for (var i = 0; i < stndLength; i++) {
		stnd1.push({ inMat_Inspect_STND_1: $("input[name='Inspect_STND_1[]']")[i].value });
		stnd2.push({ inMat_Inspect_STND_2: $("input[name='Inspect_STND_2[]']")[i].value });
	}

	// 판정 데이터
	var statusLength = 10;

	for (var i = 0; i < statusLength; i++) {
		status.push({ inMat_Inspect_Status: $("select[name='status[]'] option:selected")[i].value })
	};
	
	//OrderSub 저장부분
	$.ajax({
		method: "post",
		url: "matInputInspectionRest/MII_Save",
		data: {
			standard: JSON.stringify(standardDatas), value1: JSON.stringify(value1), value2: JSON.stringify(value2), value3: JSON.stringify(value3),
			value4: JSON.stringify(value4), value5: JSON.stringify(value5), stnd1: JSON.stringify(stnd1), stnd2: JSON.stringify(stnd2),
			status: JSON.stringify(status)
		},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				alert("저장되었습니다.");
			} else {
				alert("중복된 값이 존재합니다.")
			}
		}
	});
}

//SOM_SaveBtn
$('#MIF_SaveBtn').click(function() {
	MIF_Save();
})

//품목코드로 matOutputSubTable 선택하는 코드
function lCode_select(value) {
	var rowCount = matOutputTable.getDataCount("active");
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다. 
	for (i = 0; i < rowCount; i++) {
		var lCode = matOutputTable.getData()[i].rm_RequestNo;
		//발주번호가 입력내용을 포함하면 코드 실행
		if (lCode == value) {
			//발주번호가 같은 행 선택
			matOutputTable.deselectRow();
			matOutputTable.getRows()[i].select();
			break;
		}
	}
}
