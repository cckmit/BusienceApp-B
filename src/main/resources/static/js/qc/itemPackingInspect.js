var salesItemTable = new Tabulator("#salesItemTable", {
	pagination: "local",
	paginationSize: 20,
	clipboard: true,
	height: "calc(100% - 175px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData: true,
	//행클릭 이벤트
	rowFormatter: function(row) {
		// 검사 결과가 존재하면 글자 색을 다르게 넣어줌
		if(row.getData().lm_Inspect_Code != null) {
			row.getElement().style.color = "red";
		}
	},
	rowClick: function(e, row) {
		salesItemTable.deselectRow();
		row.select();
	},
	rowSelected: function(row) {
		formClearFunc();
		row.select();
		// LotNo, 제품명, 생산일자
		SILForm_Search(row.getData().lm_LotNo, row.getData().lm_ItemName, row.getData().lm_Create_Date);
		UseBtn();
		if(row.getData().lm_Inspect_Code != null) {
			ResetBtn();
			// LotNo 조회
			IP_Search(row.getData().lm_LotNo);
		}
		$("#itemInspectQty").focus();
	},
	columns: [
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "LotNo", field: "lm_LotNo", headerHozAlign: "center" },
		{ title: "품목 코드", field: "lm_ItemCode", headerHozAlign: "center" },
		{ title: "품목 명", field: "lm_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "lm_STND_1", headerHozAlign: "center" },
		{ title: "품목분류1", field: "lm_Item_CLSFC_1", headerHozAlign: "center" },
		{ title: "제품수량", field: "lm_Qty", headerHozAlign: "center", hozAlign: "right" },
		{ title: "제품포장일", field: "lm_Create_Date", headerHozAlign: "center" },
		{ title: "검서결과코드", field:"lm_Inspect_Code", headerHozAlign: "center", visible: false }
	],
});

//matRequest 검색버튼
function SIL_Search() {

	var datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		itemCode: $("#PRODUCT_ITEM_CODE1").val(),
		LotNo: $("#itemPackLotNo").val()
	}

	salesItemTable.setData("itemPackingInspectRest/SIL_Search", datas) 
		.then(function() {
			//list와 stock의 데이터를 없에준다
			formClearFunc();
			console.log(salesItemTable);
		})
}

$("#SIL_SearchBtn").click(function() {
	SIL_Search();
	//PI_Search();
})

//matInputInspect 정보 삽입
function SILForm_Search(LotNo, ItemName, packingDate) {
	var now = moment();
	$("#itemInspectLotNo").val(LotNo);
	$("#itemInspectItemName").val(ItemName);
	$("#itemPackingDate").val(moment(packingDate).format("YYYY-MM-DD"));
	$("#itemInspectDate").val(now.format("YYYY-MM-DD"));

}

function IP_Search(LotNo) {
	
	var datas = {
		LotNo: LotNo
	}
	
	$.ajax({
		method: "get",
		url: "itemPackingInspectRest/IP_Search",
		data: datas,
		success: function(IP_datas) {
			console.log(IP_datas);
			
			for(var i=0; i < IP_datas.length; i++) {
				
				if(i < 5) {
					document.querySelectorAll('#value1')[i].value = IP_datas[i].itemPack_Inspect_Value_1;
					document.querySelectorAll('#value2')[i].value = IP_datas[i].itemPack_Inspect_Value_2;
					document.querySelectorAll('#value3')[i].value = IP_datas[i].itemPack_Inspect_Value_3;
					document.querySelectorAll('#value4')[i].value = IP_datas[i].itemPack_Inspect_Value_4;
					document.querySelectorAll('#value5')[i].value = IP_datas[i].itemPack_Inspect_Value_5;
					
					if (IP_datas[i].itemPack_Inspect_STND_1 != "" && IP_datas[i].itemPack_Inspect_STND_2 != "") {
						document.querySelectorAll('#stnd1')[i].value = IP_datas[i].itemPack_Inspect_STND_1;
						document.querySelectorAll('#stnd2')[i].value = IP_datas[i].itemPack_Inspect_STND_2;
					}
				}
				
				document.querySelectorAll('#status')[i].value = IP_datas[i].itemPack_Inspect_Status;
				document.querySelectorAll('#result')[0].value = IP_datas[0].itemPack_Inspect_Result;	
				document.querySelector('#itemPackingWorkerList').value = IP_datas[0].itemPack_Inspect_Worker;
				document.querySelector('#itemColorType').value = IP_datas[0].itemPack_Inspect_Color;
				document.querySelector('#unit1').value = IP_datas[0].itemPack_Inspect_Unit_1;
				
				$("#itemInspectQty").val(IP_datas[0].itemPack_Inspect_Qty);
				$("#itemPackgeStatus").val(IP_datas[0].itemPack_Inspect_Package_Status);
				$("#itemBoxStatus").val(IP_datas[0].itemPack_Inspect_Box_Status);
				$("#itemPacking").val(IP_datas[0].itemPack_Inspect_Packing_Unit);
				
			}	
		}
	})

	
}

function IPI_Save() {

	let values = 12;
	
	var selectedRow = salesItemTable.getData("selected");

	if ($("#itemInspectLotNo").val() == "") {
		alert("검사할 행을 선택해주세요.");
		return false;
	}

	if ($("#itemInspectQty").val() > selectedRow[0].lm_Qty) {
		alert("시료수가 생산수량보다 많습니다.");
		return false;
	}
	
	let dataList = new Array();

	for (let j = 0; j < values; j++) {
		let itemInspectData = new Array();
		
		let elements_1;
		let elements_2;
		let elements_3;
		let elements_4;
		let elements_5;
		let stndData_1;
		let stndData_2;
		
		if (j < 5) {
			
			elements_1 = document.querySelectorAll('#value1')[j].value;
			elements_2 = document.querySelectorAll('#value2')[j].value;
			elements_3 = document.querySelectorAll('#value3')[j].value;
			elements_4 = document.querySelectorAll('#value4')[j].value;
			elements_5 = document.querySelectorAll('#value5')[j].value;
			stndData_1 = document.querySelectorAll('#stnd1')[j].value;
			stndData_2 = document.querySelectorAll('#stnd2')[j].value;
		}
		
		let statusData = document.querySelectorAll('#status > option:checked')[j].value;

		itemInspectData = {
			itemPack_Inspect_LotNo: selectedRow[0].lm_LotNo,
			itemPack_Inspect_Number: j + 1,
			itemPack_Inspect_ItemCode: selectedRow[0].lm_ItemCode,
			itemPack_Inspect_Qty: $("#itemInspectQty").val(),
			itemPack_Inspect_Color: document.querySelector('#itemColorType > option:checked').value,
			itemPack_Inspect_Date: $("#itemInspectDate").val(),
			itemPack_Inspect_Worker: document.querySelector('#itemPackingWorkerList > option:checked').value,
			itemPack_Inspect_Value_1: elements_1,
			itemPack_Inspect_Value_2: elements_2,
			itemPack_Inspect_Value_3: elements_3,
			itemPack_Inspect_Value_4: elements_4,
			itemPack_Inspect_Value_5: elements_5,
			itemPack_Inspect_STND_1: stndData_1,
			itemPack_Inspect_STND_2: stndData_2,
			itemPack_Inspect_Status: statusData,
			itemPack_Inspect_Result: document.querySelector('#result > option:checked').value,
			itemPack_Inspect_Package_Status: $("#itemPackgeStatus").val(),
			itemPack_Inspect_Box_Status: $("#itemBoxStatus").val(),
			itemPack_Inspect_Packing_Unit: $("#itemPacking").val(),
			itemPack_Inspect_Unit_1: document.querySelector('#unit1 > option:checked').value
		}

		dataList.push(itemInspectData);
	}
	
	//console.log(dataList.length);
	
			//itemPacking 저장부분
		$.ajax({
			method: "post",
			url: "itemPackingInspectRest/IPI_Save",
			data: JSON.stringify(dataList),
			contentType: 'application/json',
			beforeSend: function(xhr) {
				var header = $("meta[name='_csrf_header']").attr("content");
				var token = $("meta[name='_csrf']").attr("content");
				xhr.setRequestHeader(header, token);
			},
			success: function(result) {
				//console.log(result);
				if (result) {
					alert("저장되었씁니다.");
					formClearFunc();
					SIL_Search();
				} else {
					alert("중복된 값이 존재합니다.")
				}
			}
		});
}

function IPI_SaveBtn() {
	IPI_Save();
}

// 입력 폼 clear
function formClearFunc() {
	$("#itemInspectLotNo").val("");
	$("#itemInspectItemName").val("");
	$("#itemPackingDate").val("");
	$("#itemInspectDate").val("");
	$("#itemInspectQty").val("");

	let values = 12;

	for (var i = 0; i < values; i++) {
		
		if(i < 5) {
			document.querySelectorAll('#value1')[i].value = "";
			document.querySelectorAll('#value2')[i].value = "";
			document.querySelectorAll('#value3')[i].value = "";
			document.querySelectorAll('#value4')[i].value = "";
			document.querySelectorAll('#value5')[i].value = "";
			document.querySelectorAll('#stnd1')[i].value = "";
			document.querySelectorAll('#stnd2')[i].value = "";
		}
		
		document.querySelectorAll('#status')[i].value = true;
	}

	document.querySelector('#itemPackingWorkerList').value = '231';
	document.querySelector('#itemColorType').value = '화이트';
	document.querySelector('#unit1').value = '보건용';
	document.querySelector('#result').value = true;

	$("#itemPackgeStatus").val("");
	$("#itemBoxStatus").val("");
	$("#itemPacking").val("");

}

//itemPacking_SaveBtn
$('#IPI_SaveBtn').click(function() {
	IPI_Save();
})

//itemPacking_PrintBtn
$('#IPI_PrintBtn').click(function() {
	IPI_print();
})

//orderprint
function IPI_print() {
	//창의 주소
	var url = "itemPackingInspectPrint";
	//창의 이름
	var name = "itemPackingInspectPrint";
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
	SIL_Search();
	//PI_Search();
})

