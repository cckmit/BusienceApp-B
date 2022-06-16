var salesOutMatTable = new Tabulator("#salesOutMatTable", {
	clipboard: true,
	height: "calc(50% - 85px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData: true,
	//행클릭 이벤트
	rowFormatter: function(row) {
	},
	rowClick: function(e, row) {
		salesOutMatTable.deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		formClearFunc();
		row.select();
		// 설비명, 제품명, 생산일자
		SOILForm_Search(row.getData().sales_OutMat_Client_Name, row.getData().sales_OutMat_Name, row.getData().sales_OutMat_Qty, row.getData().sales_OutMat_STND_1);
		UseBtn();
		$("#processQty").focus();
	},
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "LotNo", field: "sales_OutMat_Lot_No", headerHozAlign: "center" },
		{ title: "품목 코드", field: "sales_OutMat_Code", headerHozAlign: "center" },
		{ title: "품목 명", field: "sales_OutMat_Name", headerHozAlign: "center" },
		{ title: "거래처 코드", field: "sales_OutMat_Client_Code", headerHozAlign: "center" },
		{ title: "거래처 명", field: "sales_OutMat_Client_Name", headerHozAlign: "center" },
		{ title: "규격1", field: "sales_OutMat_STND_1", headerHozAlign: "center" },
		{ title: "재질", field: "sales_OutMat_Material", headerHozAlign: "center" },
		{ title: "품목분류1", field: "sales_OutMat_Item_Clsfc_Name_1", headerHozAlign: "center" },
		{ title: "품목분류2", field: "sales_OutMat_Item_Clsfc_Name_2", headerHozAlign: "center" },
		{ title: "출고 수량", field: "sales_OutMat_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "출고일", field: "sales_OutMat_Date", headerHozAlign: "center" }
	],
});

//matRequest 검색버튼
function SOIL_Search() {

	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE1").val(),
		clientCode: $("#InMat_Client_Code1").val(),
		LotNo: $("#processLotNo").val()
	}

	salesOutMatTable.setData("oqcInspectRest/SOIL_Search", datas)
		.then(function() {
			//list와 stock의 데이터를 없에준다
			//formClearFunc();
			console.log(salesOutMatTable);
		})
}

$("#SOIL_SearchBtn").click(function() {
	SOIL_Search();
	//PI_Search();
})

// 출고구분 select를 구성하기위한 ajax
var output_dtl = dtlSelectList(18);

var processInspectTable = new Tabulator("#processInspectTable", {
	layoutColumnsOnNewData: true,
	clipboard: true,
	height: "calc(35% - 80px)",
	//행을 클릭하면 matLotMasterTable에 리스트가 나타남
	rowClick: function(e, row) {
		processInspectTable.deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		formClearFunc();
		row.select();
		//LotNo 검색
		PIF_Search(row.getData().process_Inspect_LotNo);
		ResetBtn();
	},
	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "center", formatter: "rownum" },
		{ title: "LotNo", field: "process_Inspect_LotNo", headerHozAlign: "center", hozAlign: "center" },
		{ title: "품목 코드", field: "process_Inspect_ItemCode", headerHozAlign: "center" },
		{ title: "품목 명", field: "process_Inspect_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "process_Inspect_STND_NAME_1", headerHozAlign: "center" },
		{ title: "품목분류1", field: "process_Inspect_Item_Clsfc_Name_1", headerHozAlign: "center" },
		{ title: "시료수", field: "process_Inspect_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "설비 코드", field: "process_Inspect_EquipCode", headerHozAlign: "center" },
		{ title: "설비 명", field: "process_Inspect_EquipName", headerHozAlign: "center" },
		{ title: "검사 시간", field: "process_Inspect_Date", headerHozAlign: "center" },
		{ title: "작업자", field: "process_Inspect_Worker", headerHozAlign: "center" }
	]
});

/*function PI_Search() {

	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE1").val(),
		machineCode: $("#EQUIPMENT_INFO_CODE").val(),
		LotNo: $("#processLotNo").val()
	}

	processInspectTable.setData("processInspectionRest/PI_Search", datas);
	console.log(processInspectTable);
}*/

//oqcInspect 정보 삽입
function SOILForm_Search(CusName, ItemName, OutmatQty, ItemStnd) {
	var now = moment();
	$("#oqcCusName").val(CusName);
	$("#oqcItemName").val(ItemName);
	$("#oqcOutMatQty").val(OutmatQty.toLocaleString('ko-KR'));
	$("#oqcItemSTND_1").val(ItemStnd);
	$("#oqcDate").val(now.format("YYYY-MM-DD"));
	// 셋째 자리 콤마 넣기
	const input = document.querySelector('#oqcQty');
	input.addEventListener('keyup', function(e) {
		let value = e.target.value;
		value = Number(value.replaceAll(',', ''));
		if (isNaN(value)) {
			input.value = 0;
		} else {
			const formatValue = value.toLocaleString('ko-KR');
			input.value = formatValue;
		}
	})

}

function PIF_Search(LotNo) {
	var datas = {
		LotNo: LotNo
	}

	console.log(datas);

	$.ajax({
		method: "GET",
		url: "processInspectionRest/PIF_Search",
		data: datas,
		success: function(PIF_datas) {
			console.log(PIF_datas);
			$("#proInspectEquipName").val(PIF_datas[0].process_Inspect_EquipName);
			$("#proInspectItemName").val(PIF_datas[0].process_Inspect_ItemName);
			$("#productionDate").val(moment(PIF_datas[0].process_Inspect_Create_Date).format("YYYY-MM-DD"));
			$("#processDate").val(moment(PIF_datas[0].process_Inspect_Date).format("YYYY-MM-DD"));
			$("#processQty").val(PIF_datas[0].process_Inspect_Qty);
			$("#processRemark").val(PIF_datas[0].process_Inspect_Remark);
			document.querySelector('#pqcWorkerList').value = PIF_datas[0].process_Inspect_Worker;
			document.querySelector('#itemColorType').value = PIF_datas[0].process_Inspect_Color;

			for (var i = 0; i < PIF_datas.length; i++) {
				document.querySelectorAll('#value1')[i].value = PIF_datas[i].process_Inspect_Value_1;
				document.querySelectorAll('#value2')[i].value = PIF_datas[i].process_Inspect_Value_2;
				document.querySelectorAll('#value3')[i].value = PIF_datas[i].process_Inspect_Value_3;
				document.querySelectorAll('#value4')[i].value = PIF_datas[i].process_Inspect_Value_4;
				document.querySelectorAll('#value5')[i].value = PIF_datas[i].process_Inspect_Value_5;

				if (PIF_datas[i].process_Inspect_STND_1 != "" && PIF_datas[i].process_Inspect_STND_2 != "") {
					document.querySelectorAll('#stnd1')[i].value = PIF_datas[i].process_Inspect_STND_1;
					document.querySelectorAll('#stnd2')[i].value = PIF_datas[i].process_Inspect_STND_2;
				}

				document.querySelectorAll('#status')[i].value = PIF_datas[i].process_Inspect_Status;
				document.querySelectorAll('#result')[0].value = PIF_datas[0].process_Inspect_Result;
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

function SOI_Save() {

	let values = 16;

	let unitValues = 7;

	var selectedRow = salesOutMatTable.getData("selected");

	if ($("#oqcItemName").val() == "") {
		alert("검사할 행을 선택해주세요.");
		return false;
	}

	if ($("#oqcQty").val() > selectedRow[0].sales_OutMat_Qty) {
		alert("검사수량이 출고수량보다 많습니다.");
		return false;
	}

	let packUnit;

	let dataList = new Array();

	let packUnitList = new Array();

	for (let j = 0; j < values; j++) {
		let oqcInspectData = new Array();

		let elements_1;
		let elements_2;
		let elements_3;
		let elements_4;
		let elements_5;
		let elements_6;
		let elements_7;
		let elements_8;
		let elements_9;
		let elements_10;
		let stndData_1;
		let stndData_2;


		if (j < 16) {

			elements_1 = document.querySelectorAll('#value1')[j].value;
			elements_2 = document.querySelectorAll('#value2')[j].value;
			elements_3 = document.querySelectorAll('#value3')[j].value;
			elements_4 = document.querySelectorAll('#value4')[j].value;
			elements_5 = document.querySelectorAll('#value5')[j].value;
			elements_6 = document.querySelectorAll('#value6')[j].value;
			elements_7 = document.querySelectorAll('#value7')[j].value;
			elements_8 = document.querySelectorAll('#value8')[j].value;
			elements_9 = document.querySelectorAll('#value9')[j].value;
			elements_10 = document.querySelectorAll('#value10')[j].value;

			stndData_1 = document.querySelectorAll('#stnd1')[j].value;
			stndData_2 = document.querySelectorAll('#stnd2')[j].value;

		}

		let statusData = document.querySelectorAll('#status > option:checked')[j].value;

		oqcInspectData = {
			oqc_Inspect_LotNo: selectedRow[0].sales_OutMat_Lot_No,
			oqc_Inspect_Number: j + 1,
			oqc_Inspect_ItemCode: selectedRow[0].sales_OutMat_Code,
			oqc_Inspect_Customer: selectedRow[0].sales_OutMat_Client_Code,
			oqc_Inspect_Qty: $("#oqcQty").val(),
			oqc_Inspect_Date: $("#oqcDate").val(),
			oqc_Inspect_Worker: document.querySelector('#oqcWorkerList > option:checked').value,
			oqc_Inspect_Value_1: elements_1,
			oqc_Inspect_Value_2: elements_2,
			oqc_Inspect_Value_3: elements_3,
			oqc_Inspect_Value_4: elements_4,
			oqc_Inspect_Value_5: elements_5,
			oqc_Inspect_Value_6: elements_6,
			oqc_Inspect_Value_7: elements_7,
			oqc_Inspect_Value_8: elements_8,
			oqc_Inspect_Value_9: elements_9,
			oqc_Inspect_Value_10: elements_10,
			oqc_Inspect_STND_1: stndData_1,
			oqc_Inspect_STND_2: stndData_2,
			oqc_Inspect_Status: statusData,
			oqc_Inspect_Result: document.querySelector('#result > option:checked').value,
			oqc_Inspect_Remark: $("#oqcRemark").val()
		}

		dataList.push(oqcInspectData);
	}

	for (let k = 0; k < unitValues; k++) {
		packUnit = document.querySelectorAll('#unit1 > option:checked')[k].value;
		packUnitList.push({ oqc_Inspect_Packing_Unit: packUnit });
	}

	console.log(dataList);
	console.log(packUnitList);

	//itemPacking 저장부분
	$.ajax({
		method: "post",
		url: "oqcInspectRest/SOI_Save",
		data: {dataList: JSON.stringify(dataList), packList: JSON.stringify(packUnitList)},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			console.log(result);
			if (result) {
				alert("저장되었씁니다.");
				formClearFunc();
				SOIL_Search();
			} else {
				alert("중복된 값이 존재합니다.")
			}
		}
	});
}

// 입력 폼 clear
function formClearFunc() {
	$("#oqcCusName").val("");
	$("#oqcItemName").val("");
	$("#oqcOutMatQty").val("");
	$("#oqcItemSTND_1").val("");
	$("#oqcDate").val("");
	$("#oqcQty").val("");

	let values = 16;

	for (var i = 0; i < values; i++) {
		document.querySelectorAll('#stnd1')[i].value = "";
		document.querySelectorAll('#stnd2')[i].value = "";
		document.querySelectorAll('.tempValue1')[i].value = "";
		document.querySelectorAll('.tempValue2')[i].value = "";
		document.querySelectorAll('.tempValue3')[i].value = "";
		document.querySelectorAll('.tempValue4')[i].value = "";
		document.querySelectorAll('.tempValue5')[i].value = "";
		document.querySelectorAll('.tempValue6')[i].value = "";
		document.querySelectorAll('.tempValue7')[i].value = "";
		document.querySelectorAll('.tempValue8')[i].value = "";
		document.querySelectorAll('.tempValue9')[i].value = "";
		document.querySelectorAll('.tempValue10')[i].value = "";
		document.querySelectorAll('#status')[i].value = true;
	}

	document.querySelector('#oqcWorkerList').value = '231';
	document.querySelector('#result').value = true;
	
	
	$("#oqcRemark").val("");

}

//SOM_SaveBtn
$('#SOI_SaveBtn').click(function() {
	SOI_Save();
})

//MO_PrintBtn
$('#SOI_PrintBtn').click(function() {
	SOI_print();
})

//OQCprint
function SOI_print() {
	//창의 주소
	var url = "oqcOutputInspectPrint";
	//창의 이름
	var name = "oqcOutputInspectPrint";
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

$(document).ready(function() {
	SOIL_Search();
	//PI_Search();
})

