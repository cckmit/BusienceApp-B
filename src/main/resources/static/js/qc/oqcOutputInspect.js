let oqcInspectTable;

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
		oqcInspectTable.deselectRow();
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
		LotNo: $("#oqcLotNo").val()
	}

	salesOutMatTable.setData("oqcInspectRest/SOIL_Search", datas)
		.then(function() {
			//list와 stock의 데이터를 없에준다
			//formClearFunc();
		})
}

$("#SOIL_SearchBtn").click(function() {
	SOIL_Search();
	OIL_Search();
})

// 출고구분 select를 구성하기위한 ajax
var output_dtl = dtlSelectList(18);

oqcInspectTable = new Tabulator("#oqcInspectTable", {
	layoutColumnsOnNewData: true,
	clipboard: true,
	height: "calc(35% - 80px)",
	//행을 클릭하면 matLotMasterTable에 리스트가 나타남
	rowClick: function(e, row) {
		salesOutMatTable.deselectRow();
		oqcInspectTable.deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		formClearFunc();
		row.select();
		SOILForm_subSearch(row.getData().oqc_Inspect_Customer, row.getData().oqc_Inspect_ItemName, row.getData().sales_OutMat_Qty, row.getData().oqc_Inspect_STND_1, row.getData().oqc_Inspect_Qty, row.getData().oqc_Inspect_Date);
		//LotNo 검색
		OIF_Search(row.getData().oqc_Inspect_LotNo);
		UseBtn();
	},
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "LotNo", field: "oqc_Inspect_LotNo", headerHozAlign: "center" },
		{ title: "품목 코드", field: "oqc_Inspect_ItemCode", headerHozAlign: "center" },
		{ title: "품목 명", field: "oqc_Inspect_ItemName", headerHozAlign: "center" },
		{ title: "거래처 코드", field: "oqc_Inspect_Customer", headerHozAlign: "center" },
		{ title: "거래처 명", field: "oqc_Inspect_Customer_Name", headerHozAlign: "center" },
		{ title: "규격1", field: "oqc_Inspect_STND_1", headerHozAlign: "center" },
		{ title: "재질", field: "oqc_Inspect_Material", headerHozAlign: "center" },
		{ title: "품목분류1", field: "oqc_Inspect_Item_STND_1", headerHozAlign: "center" },
		{ title: "품목분류2", field: "oqc_Inspect_Item_STND_2", headerHozAlign: "center" },
		{ title: "검사 수량", field: "oqc_Inspect_Qty", headerHozAlign: "center", hozAlign: "right", formatter: "money", formatterParams: { precision: false } },
		{ title: "검사일", field: "oqc_Inspect_Date", headerHozAlign: "center" },
		{ title: "검사자", field: "oqc_Inspect_Worker", headerHozAlign: "center" },
		{ title: "검사결과", field:"oqc_Inspect_Result", headerHozAlign: "center", 
			formatter: function(cell, formatterParams) {
				var value = cell.getValue();
				if (value == 'true') {
					value = "적합";
				} else if(value == 'false') {
					value = "부적합";
				}
				return value;
			}}
	]
});

function OIL_Search() {

	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE1").val(),
		clientCode: $("#InMat_Client_Code1").val(),
		LotNo: $("#oqcLotNo").val()
	}

	oqcInspectTable.setData("oqcInspectRest/OIL_Search", datas);
}

//oqcInspect 정보 삽입
function SOILForm_Search(CusName, ItemName, OutmatQty, ItemStnd, inspectQty, inspectDate) {
	var now = moment();
	$("#oqcCusName").val(CusName);
	$("#oqcItemName").val(ItemName);
	$("#oqcOutMatQty").val(OutmatQty.toLocaleString('ko-KR'));
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

//oqcInspect 정보 삽입
function SOILForm_subSearch(CusName, ItemName, OutmatQty, ItemStnd, inspectQty, inspectDate) {
	var now = moment();
	$("#oqcCusName").val(CusName);
	$("#oqcItemName").val(ItemName);
	$("#oqcQty").val(inspectQty);
	$("#oqcItemSTND_1").val(ItemStnd);
	$("#oqcDate").val(inspectDate);

}

function OIF_Search(LotNo) {
	var datas = {
		LotNo: LotNo
	}

	$.ajax({
		method: "GET",
		url: "oqcInspectRest/OIF_Search",
		data: datas,
		success: function(OIF_datas) {
			$("#oqcRemark").val(OIF_datas[0].oqc_Inspect_Remark);
			let j=0;
			
			for (var i = 0; i < OIF_datas.length; i++) {
				document.querySelectorAll('#value1')[i].value = OIF_datas[i].oqc_Inspect_Value_1;
				document.querySelectorAll('#value2')[i].value = OIF_datas[i].oqc_Inspect_Value_2;
				document.querySelectorAll('#value3')[i].value = OIF_datas[i].oqc_Inspect_Value_3;
				document.querySelectorAll('#value4')[i].value = OIF_datas[i].oqc_Inspect_Value_4;
				document.querySelectorAll('#value5')[i].value = OIF_datas[i].oqc_Inspect_Value_5;
				document.querySelectorAll('#value6')[i].value = OIF_datas[i].oqc_Inspect_Value_6;
				document.querySelectorAll('#value7')[i].value = OIF_datas[i].oqc_Inspect_Value_7;
				document.querySelectorAll('#value8')[i].value = OIF_datas[i].oqc_Inspect_Value_8;
				document.querySelectorAll('#value9')[i].value = OIF_datas[i].oqc_Inspect_Value_9;
				document.querySelectorAll('#value10')[i].value = OIF_datas[i].oqc_Inspect_Value_10;

				if (OIF_datas[i].oqc_Inspect_STND_1 != "" && OIF_datas[i].oqc_Inspect_STND_2 != "") {
					document.querySelectorAll('#stnd1')[i].value = OIF_datas[i].oqc_Inspect_STND_1;
					document.querySelectorAll('#stnd2')[i].value = OIF_datas[i].oqc_Inspect_STND_2;
				}
				
				
				if (OIF_datas[i].oqc_Inspect_Packing_Unit != null) {
					
					document.querySelectorAll('#unit1')[j].value = OIF_datas[i].oqc_Inspect_Packing_Unit;
					j++;
				}

				document.querySelectorAll('#status')[i].value = OIF_datas[i].oqc_Inspect_Status;
				
				let el = document.getElementById('result');
				if(OIF_datas[0].oqc_Inspect_Result == 226) {
					el.options[1].selected = true;
				} else {
					el.options[0].selected = true;
				}
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
	
	if($("#oqcQty").val() < 1) {
		alert("검사수량을 입력하세요.");
	}

	if ($("#oqcQty").val() > selectedRow[0].sales_OutMat_Qty) {
		alert("검사수량이 출고수량보다 많습니다.");
		return false;
	}

	let packUnit;

	let dataList = new Array();

	let packUnitList = new Array();
	
	let count = 0;

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
		
		if(document.querySelector('#result > option:checked').value == "false") {
			count += 1
		}
		
		if(count > 0) {
			result = 226
		} else {
			result = 225
		}

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
			oqc_Inspect_Result: result,
			oqc_Inspect_Remark: $("#oqcRemark").val()
		}

		dataList.push(oqcInspectData);
	}

	for (let k = 0; k < unitValues; k++) {
		packUnit = document.querySelectorAll('#unit1 > option:checked')[k].value;
		packUnitList.push({ oqc_Inspect_Packing_Unit: packUnit });
	}

	//console.log(dataList);
	//console.log(packUnitList);

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
			if (result) {
				alert("저장되었습니다.");
				formClearFunc();
				SOIL_Search();
				OIL_Search();
			} else {
				alert("중복된 값이 존재합니다.")
			}
		}
	});
}

function SOI_Update() {

	let values = 16;

	let unitValues = 7;

	var selectedRow = oqcInspectTable.getData("selected");

	if ($("#oqcItemName").val() == "") {
		alert("검사할 행을 선택해주세요.");
		return false;
	}
	
	if($("#oqcQty").val() < 1) {
		alert("검사수량을 입력하세요.");
	}

	if ($("#oqcQty").val() > selectedRow[0].oqc_Inspect_Qty) {
		alert("검사수량이 출고수량보다 많습니다.");
		return false;
	}

	let packUnit;

	let dataList = new Array();

	let packUnitList = new Array();
	
	let count = 0;

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
		
		if(document.querySelector('#result > option:checked').value == "false") {
			count += 1
		}
		
		if(count > 0) {
			result = 226
		} else {
			result = 225
		}

		oqcInspectData = {
			oqc_Inspect_LotNo: selectedRow[0].oqc_Inspect_LotNo,
			oqc_Inspect_Number: j + 1,
			oqc_Inspect_ItemCode: selectedRow[0].oqc_Inspect_ItemCode,
			oqc_Inspect_Customer: selectedRow[0].oqc_Inspect_Customer,
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
			oqc_Inspect_Result: result,
			oqc_Inspect_Remark: $("#oqcRemark").val()
		}

		dataList.push(oqcInspectData);
	}

	for (let k = 0; k < unitValues; k++) {
		packUnit = document.querySelectorAll('#unit1 > option:checked')[k].value;
		packUnitList.push({ oqc_Inspect_Packing_Unit: packUnit });
	}

	//console.log(dataList);
	//console.log(packUnitList);

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
			if (result) {
				alert("저장되었습니다.");
				formClearFunc();
				SOIL_Search();
				OIL_Search();
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
	let tempValues = 7;

	for (var i = 0; i < values; i++) {
		document.querySelectorAll('#stnd1')[i].value = "";
		document.querySelectorAll('#stnd2')[i].value = "";
		document.querySelectorAll('#status')[i].value = true;
	}
	
	for (let j = 0; j < tempValues; j++) {
		document.querySelectorAll('#unit1')[j].value = "보건용";
		document.querySelectorAll('.tempValue1')[j].value = "";
		document.querySelectorAll('.tempValue2')[j].value = "";
		document.querySelectorAll('.tempValue3')[j].value = "";
		document.querySelectorAll('.tempValue4')[j].value = "";
		document.querySelectorAll('.tempValue5')[j].value = "";
		document.querySelectorAll('.tempValue6')[j].value = "";
		document.querySelectorAll('.tempValue7')[j].value = "";
		document.querySelectorAll('.tempValue8')[j].value = "";
		document.querySelectorAll('.tempValue9')[j].value = "";
		document.querySelectorAll('.tempValue10')[j].value = "";
	}

	document.querySelector('#oqcWorkerList').value = '231';
	document.querySelector('#result').value = true;
	
	
	$("#oqcRemark").val("");

}

//SOM_SaveBtn
$('#SOI_SaveBtn').click(function() {
	if(salesOutMatTable.getData("selected").length > 0) {
		SOI_Save();
	} else if(oqcInspectTable.getData("selected").length > 0) {
		SOI_Update();
	}
	
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


$(document).ready(function() {
	SOIL_Search();
	OIL_Search();
})

