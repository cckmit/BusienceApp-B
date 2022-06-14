var workOrderTable = new Tabulator("#workOrderTable", {
	clipboard: true,
	height: "calc(50% - 85px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData: true,
	//행클릭 이벤트
	rowFormatter: function(row) {
	},
	rowClick: function(e, row) {
		crateInspectTable.deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		formClearFunc();
		row.select();
		// 설비명, 제품명, 생산일자
		CIForm_Search(row.getData().cl_EquipName, row.getData().cl_ItemName, row.getData().cl_Create_Date);
		UseBtn();
		$("#processQty").focus();
	},
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "LotNo", field: "cl_LotNo", headerHozAlign: "center" },
		{ title: "작업지시번호", field: "cl_OrderNo", headerHozAlign: "center", visible: false },
		{ title: "품목 코드", field: "cl_ItemCode", headerHozAlign: "center" },
		{ title: "품목 명", field: "cl_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "cl_STND_1", headerHozAlign: "center" },
		{ title: "품목분류1", field: "cl_Item_Clsfc_Name_1", headerHozAlign: "center" },
		{ title: "생산 수량", field: "cl_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "설비 코드", field: "cl_EquipCode", headerHozAlign: "center" },
		{ title: "설비 명", field: "cl_EquipName", headerHozAlign: "center" },
		{ title: "작업지시일", field: "cl_Create_Date", headerHozAlign: "center" },
		{ title: "작업종료일", field: "cl_Create_Date", headerHozAlign: "center" },
		{ title: "검사여부", field: "cl_Create_Date", headerHozAlign: "center" }
	],
});

//matRequest 검색버튼
function CI_Search() {

	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE1").val(),
		machineCode: $('#EQUIPMENT_INFO_CODE').val(),
		LotNo: $("#processLotNo").val()
	}

	/*crateInspectTable.setData("processInspectionRest/CI_Search", datas)
		.then(function() {
			//list와 stock의 데이터를 없에준다
			formClearFunc();
			console.log(crateInspectTable);
		})*/
}

$("#CI_SearchBtn").click(function() {
	CI_Search();
	PI_Search();
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

//matInputInspect 정보 삽입
function CIForm_Search(EquipName, ItemName, ProductionDate) {
	var now = moment();
	$("#proInspectEquipName").val(EquipName);
	$("#proInspectItemName").val(ItemName);
	$("#productionDate").val(moment(ProductionDate).format("YYYY-MM-DD"));
	$("#processDate").val(now.format("YYYY-MM-DD"));

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

function PI_Save() {

	let values = 8;

	var selectedRow = crateInspectTable.getData("selected");

	if ($("#proInspectEquipName").val() == "") {
		alert("검사할 행을 선택해주세요.");
		return false;
	}

	if ($("#processQty").val() > selectedRow[0].cl_Qty) {
		alert("시료수가 생산수량보다 많습니다.");
		return false;
	}

	for (let j = 0; j < values; j++) {
		let processInspectData = new Array();
		let elements_1 = document.querySelectorAll('#value1')[j].value;
		let elements_2 = document.querySelectorAll('#value2')[j].value;
		let elements_3 = document.querySelectorAll('#value3')[j].value;
		let elements_4 = document.querySelectorAll('#value4')[j].value;
		let elements_5 = document.querySelectorAll('#value5')[j].value;
		let stndData_1 = document.querySelectorAll('#stnd1')[j].value;
		let stndData_2 = document.querySelectorAll('#stnd2')[j].value;
		let statusData = document.querySelectorAll('#status > option:checked')[j].value;

		processInspectData = {
			process_Inspect_LotNo: selectedRow[0].cl_LotNo,
			process_Inspect_Number: j + 1,
			process_Inspect_EquipCode: selectedRow[0].cl_EquipCode,
			process_Inspect_ItemCode: selectedRow[0].cl_ItemCode,
			process_Inspect_Qty: $("#processQty").val(),
			process_Inspect_Color: document.querySelector('#itemColorType > option:checked').value,
			process_Inspect_Date: $("#processDate").val(),
			process_Inspect_Worker: document.querySelector('#pqcWorkerList > option:checked').value,
			process_Inspect_Value_1: elements_1,
			process_Inspect_Value_2: elements_2,
			process_Inspect_Value_3: elements_3,
			process_Inspect_Value_4: elements_4,
			process_Inspect_Value_5: elements_5,
			process_Inspect_STND_1: stndData_1,
			process_Inspect_STND_2: stndData_2,
			process_Inspect_Status: statusData,
			process_Inspect_Result: document.querySelector('#result > option:checked').value,
			process_Inspect_Remark: $("#processRemark").val()
		}

		console.log(processInspectData);

		//OrderSub 저장부분
		$.ajax({
			method: "post",
			url: "processInspectionRest/PI_Save",
			data: processInspectData,
			beforeSend: function(xhr) {
				var header = $("meta[name='_csrf_header']").attr("content");
				var token = $("meta[name='_csrf']").attr("content");
				xhr.setRequestHeader(header, token);
			},
			success: function(result) {
				if (result) {
					$(function() {
						alert("저장되었씁니다.");
						$(this).off();
					})
					formClearFunc();
					CI_Search();
					PI_Search();
				} else {
					alert("중복된 값이 존재합니다.")
				}
			}
		});
	}
}

function PI_SaveBtn() {
	PI_Save();
}


// 입력 폼 clear
function formClearFunc() {
	$("#proInspectEquipName").val("");
	$("#proInspectItemName").val("");
	$("#productionDate").val("");
	$("#processDate").val("");
	$("#processQty").val("");

	let values = 8;

	for (var i = 0; i < values; i++) {
		document.querySelectorAll('#value1')[i].value = "";
		document.querySelectorAll('#value2')[i].value = "";
		document.querySelectorAll('#value3')[i].value = "";
		document.querySelectorAll('#value4')[i].value = "";
		document.querySelectorAll('#value5')[i].value = "";
		document.querySelectorAll('#stnd1')[i].value = "";
		document.querySelectorAll('#stnd2')[i].value = "";
		document.querySelectorAll('#status')[i].value = true;
	}

	document.querySelector('#pqcWorkerList').value = '327';
	document.querySelector('#itemColorType').value = '화이트';
	document.querySelector('#result').value = true;

	$("#processRemark").val("");

}

//SOM_SaveBtn
$('#PI_SaveBtn').click(function() {
	PI_Save();
})

//MO_PrintBtn
$('#PI_PrintBtn').click(function() {
	PI_print();
})

//orderprint
function PI_print() {
	//창의 주소
	var url = "processInspectPrint";
	//창의 이름
	var name = "processInspectPrint";
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
	CI_Search();
	//PI_Search();
})

