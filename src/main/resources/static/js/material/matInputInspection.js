var tempStorageTable = new Tabulator("#tempStorageTable", {
	height: "calc(50% - 85px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData: true,
	//행클릭 이벤트
	rowFormatter: function(row) {
	},
	rowClick: function(e, row) {
		tempStorageTable.deselectRow();
		row.select();s
	},
	rowSelected: function(row) {
		formClearFunc();
		row.select();
		MIForm_Search(row.getData().inMat_Name, row.getData().inMat_Qty, row.getData().inMat_Client_Name);
		UseBtn();
		$("#matInspectWorker").focus();
	},
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "입고순번", field: "inMat_No", headerHozAlign: "center", hozAlign: "right", visible: false },
		{ title: "LotNo", field: "inMat_Lot_No", headerHozAlign: "center", hozAlign: "right", visible: false },
		{ title: "발주번호", field: "inMat_Order_No", headerHozAlign: "center", hozAlign: "right" },
		{ title: "품목코드", field: "inMat_Code" },
		{ title: "품목명", field: "inMat_Name", headerHozAlign: "center" },
		{ title: "수량", field: "inMat_Qty", hozAlign: "right" },
		{ title: "단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right" },
		{ title: "금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right", formatter : "money", formatterParams:{ precision:false}, },
		{ title: "거래처코드", field: "inMat_Client_Code", headerHozAlign: "center" },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center" },
		{ title: "가입고일", field: "inMat_Date", headerHozAlign: "center"},
		{ title: "구분", field: "inMat_Rcv_Clsfc", headerHozAlign: "center", hozAlign: "right", visible: false },
		{ title: "구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center", hozAlign: "right" }
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
			//list와 stock의 데이터를 없에준다
			formClearFunc();
			console.log(tempStorageTable);
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
	//행을 클릭하면 matLotMasterTable에 리스트가 나타남
	rowClick: function(e, row) {
		matInputTable.deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		formClearFunc();
		row.select();
		//발주번호, 품목코드로 검색
		MIF_Search(row.getData().inMat_Order_No, row.getData().inMat_Code);
		ResetBtn();
	},
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "발주번호", field: "inMat_Order_No" },
		{ title: "품목코드", field: "inMat_Code", headerHozAlign: "center" },
		{ title: "품목명", field: "inMat_Name", headerHozAlign: "center" },
		{ title: "수량", field: "inMat_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "단가", field: "inMat_Unit_Price", headerHozAlign: "center", hozAlign: "right" },
		{ title: "금액", field: "inMat_Price", headerHozAlign: "center", hozAlign: "right" },
		{ title: "거래처코드", field: "inMat_Client_Code", headerHozAlign: "center" },
		{ title: "거래처명", field: "inMat_Client_Name", headerHozAlign: "center" },
		{ title: "입고일", field: "inMat_Date", headerHozAlign: "center" },
		{ title: "구분", field: "inMat_Rcv_Clsfc", headerHozAlign: "center", visible: false },
		{ title: "구분", field: "inMat_Rcv_Clsfc_Name", headerHozAlign: "center", hozAlign: "right" }]
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
	console.log(matInputTable);
}

//matInputInspect 정보 삽입
function MIForm_Search(ItemName, Qty, ClientName) {
	var now = moment();
	$("#matInspectItemName").val(ItemName);
	$("#matInspectDate").val(now.format("YYYY-MM-DD"));
	$("#matInspectQty").val(Qty);
	$("#matInspectCustomer").val(ClientName);
}

function MIF_Search(OrderNo, ItemCode) {
	var datas = {
		OrderNo: OrderNo,
		ItemCode: ItemCode
	}
	
	console.log(datas);

	$.ajax({
		method: "GET",
		url: "matInputInspectionRest/MIF_Search",
		data: datas,
		success: function(MIF_datas) {
			console.log(MIF_datas);
			$("#matInspectItemName").val(MIF_datas[0].inMat_Inspect_ItemName);
			$("#matInspectDate").val(moment(MIF_datas[0].inMat_Inspect_Date).format("YYYY-MM-DD"));
			$("#matInspectQty").val(MIF_datas[0].inMat_Inspect_Qty);
			$("#matInspectWorker").val(MIF_datas[0].inMat_Inspect_Worker);
			$("#matInspectCustomer").val(MIF_datas[0].inMat_Inspect_Customer_Name);
			$("#inspectionText").val(MIF_datas[0].inMat_Inspect_Text);

			for (var i = 0; i < MIF_datas.length; i++) {
				//console.log(MIF_datas[i].inMat_Inspect_Value_1);
				$("input[name='Inspect_Value_1[]']")[i].value = MIF_datas[i].inMat_Inspect_Value_1;
				$("input[name='Inspect_Value_2[]']")[i].value = MIF_datas[i].inMat_Inspect_Value_2;
				$("input[name='Inspect_Value_3[]']")[i].value = MIF_datas[i].inMat_Inspect_Value_3;
				$("input[name='Inspect_Value_4[]']")[i].value = MIF_datas[i].inMat_Inspect_Value_4;
				$("input[name='Inspect_Value_5[]']")[i].value = MIF_datas[i].inMat_Inspect_Value_5;
				if (i == 7) {
					$("input[name='Inspect_STND_1[]']")[0].value = MIF_datas[7].inMat_Inspect_STND_1;
					$("input[name='Inspect_STND_2[]']")[0].value = MIF_datas[7].inMat_Inspect_STND_2;
				}

				if (i == 8) {
					$("input[name='Inspect_STND_1[]']")[1].value = MIF_datas[8].inMat_Inspect_STND_1;
					$("input[name='Inspect_STND_2[]']")[1].value = MIF_datas[8].inMat_Inspect_STND_2;
				}

				if (i == 9) {
					$("input[name='Inspect_STND_1[]']")[2].value = MIF_datas[9].inMat_Inspect_STND_1;
					$("input[name='Inspect_STND_2[]']")[2].value = MIF_datas[9].inMat_Inspect_STND_2;
				}
				// status 
				$("select[name='status[]']")[i].value = MIF_datas[i].inMat_Inspect_Status;
			}
		}
	});
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

	if ($("#matInspectItemName").val() == "") {
		alert("검사할 행을 선택해주세요.");
		return false;
	}

	if ($("#matInspectWorker").val() == "") {
		alert("검사자를 입력해주세요.");
		return false;
	}

	for (var i = 1; i < 10; i++) {
		standardDatas = {
			inMat_Inspect_Order_No: tempStorageTable.getData()[0].inMat_Order_No,
			inMat_Inspect_Number: i,
			inMat_Inspect_ItemCode: tempStorageTable.getData()[0].inMat_Code,
			inMat_Inspect_Qty: tempStorageTable.getData()[0].inMat_Qty,
			inMat_Inspect_UnitPrice: tempStorageTable.getData()[0].inMat_Unit_Price,
			inMat_Inspect_Worker: $("#matInspectWorker").val(),
			inMat_Inspect_Customer: tempStorageTable.getData()[0].inMat_Client_Code,
			inMat_Inspect_Text: $("#inspectionText").val(),
			inMat_Inspect_Classfy: tempStorageTable.getData()[0].inMat_Rcv_Clsfc,
			inMat_Inspect_Remark: $("#inspectionRemark").val()
		}
	}

	console.log(standardDatas);
	
	inMatDatas = {
		inMat_No: tempStorageTable.getData()[0].inMat_No,
		inMat_Lot_No: tempStorageTable.getData()[0].inMat_Lot_No
	}

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
			status: JSON.stringify(status), inMatData: JSON.stringify(inMatDatas)
		},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				alert("저장되었습니다.");
				formClearFunc();
				MII_Search();
				MIS_Search();
			} else {
				alert("중복된 값이 존재합니다.")
			}
		}
	});
}

// 입력 폼 clear
function formClearFunc() {
	$("#matInspectItemName").val("");
	$("#matInspectDate").val("");
	$("#matInspectQty").val("");
	$("#matInspectWorker").val("");
	$("#matInspectCustomer").val("");
	$("#inspectionText").val("");
	$("input[name='Inspect_Value_1[]']").each(function(index, item) {
		$(item).val("");
	});
	$("input[name='Inspect_Value_2[]']").each(function(index, item) {
		$(item).val("");
	});
	$("input[name='Inspect_Value_3[]']").each(function(index, item) {
		$(item).val("");
	});
	$("input[name='Inspect_Value_4[]']").each(function(index, item) {
		$(item).val("");
	});
	$("input[name='Inspect_Value_5[]']").each(function(index, item) {
		$(item).val("");
	});
	$("input[name='Inspect_STND_1[]']").each(function(index, item) {
		$(item).val("");
	});
	$("input[name='Inspect_STND_2[]']").each(function(index, item) {
		$(item).val("");
	});
	// status check1 기본 값
	$('#check1_val6').val('true').prop("selected", true);
	$('#check2_val6').val('true').prop("selected", true);
	$('#check3_val6').val('true').prop("selected", true);
	$('#check4_val6').val('true').prop("selected", true);
	$('#check5_val6').val('true').prop("selected", true);
	$('#check6_val6').val('true').prop("selected", true);
	$('#check7_val6').val('true').prop("selected", true);
	$('#check8_val6').val('true').prop("selected", true);
	$('#check9_val6').val('true').prop("selected", true);
	$('#check10_val6').val('true').prop("selected", true);
}

//SOM_SaveBtn
$('#MIF_SaveBtn').click(function() {
	MIF_Save();
})

//MO_PrintBtn
$('#MII_PrintBtn').click(function() {
	MII_print();
})

//orderprint
function MII_print() {
	//창의 주소
	var url = "matInputInspectionPrint";
	//창의 이름
	var name = "matInputInspectionPrint";
	//창의 css
	var option = "width = 1000, height = 800, top = 50, left = 440, location = top";

	openWin = window.open(url, name, option);

	$("#inspect_frm").submit();
}

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

$(document).ready(function(){
	MII_Search();
	MIS_Search();
})

