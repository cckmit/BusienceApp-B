//fgoodsName
$('#fgoodsName').keydown(function(e) {
	//엔터키를 눌렀을떄
	if (e.keyCode == 13) {
		//제품팝업창을 띄우고
		product_check($('#fgoodsName').val());
		//다음 input으로 포커스
		$('#fgoodsQty').focus();
	}
})

//fgoodsQty
$('#fgoodsQty').keydown(function(e) {
	//엔터키를 눌렀을때
	if (e.keyCode == 13) {


		//제품코드가 선택이 되었고 수량이 입력되어있으면 
		/*		if ($('#fgoodsCode').val().length > 0 && $('#fgoodsQty').val() > 0) {
					//입력한 데이터를 기반으로 행 추가
		
					//fgoodsbundle 값이 존재하는경우
					//for(j=0;j<$('#fgoodsbundle').val();j++){} 으로 행추가 여러번 실행
					salesInputTable.addRow(
						{
							"sales_InMat_No": salesInputTable.getDataCount('active') + 1,
							"sales_InMat_Code": $('#fgoodsCode').val(),
							"sales_InMat_Name": $('#fgoodsName').val(),
							"sales_InMat_Qty": $('#fgoodsQty').val(),
							"sales_InMat_Date": moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),
							"sales_InMat_Rcv_Clsfc": $('#fgoodsRcv_Clsfc').val(),
							"sales_InMat_Rcv_Clsfc_Name": $('#fgoodsRcv_Clsfc option:selected').text()
						}
					);
					//그 후 수량이 빈칸이 되고 제품명input으로 포커스
					$('#fgoodsQty').val("");
					$('#fgoodsRcv_Clsfc').val("171").prop("selected", true)
					$('#fgoodsName').focus();
				}*/
	}
})

function product_check(value) {
	//쿼리실행
	$.ajax({
		method: "GET",
		async: false,
		url: "product_check?PRODUCT_ITEM_CODE=" + value,
		dataType: "json",
		success: function(pc_data) {
			console.log(pc_data);
			if (pc_data.length == 1) {
				//검색어와 일치하는값이 있는경우
				$('#fgoodsCode').val(pc_data[0].product_ITEM_CODE);
				$('#fgoodsName').val(pc_data[0].product_ITEM_NAME);
			} else {
				//검색어와 일치하는값이 없는경우, 팝업창
				itemFinishedPopup(value, 'input', '');
			}
		}
	})
}

//add버튼
$('#SI_NewBtn').click(function() {
	salesInputTable.clearData();
	SI_buttonUse();
	trans_input_use()
	$('#fgoodsName').focus();
})

var ajaxResult = null;
//save버튼
$('#SI_SaveBtn').click(function() {
	//저장하는기능
	SI_Save();
})

//저장하는기능
function SI_Save() {
	rowCount = salesInputTable.getDataCount("active");
	var sales_Packing_Qty = 0;
	var sales_Small_Packing_LotNo = null;
	var sales_Packing_Status = null;


	var totalQty = $('#fgoodsTotal').val();

	var rowArray = new Array();

	for (i = 0; i < rowCount; i++) {

		rowArray.push({
			sales_Packing_Qty: salesInputTable.getData()[i].sales_InMat_Qty,
			sales_Small_Packing_LotNo: salesInputTable.getData()[i].sales_InMat_Lot_No,
			sales_Packing_Status: '203'
		})
	}

	//쿼리실행
	$.ajax({
		method: "post",
		async: false,
		url: "salesInputRest/SI_Save",
		data: { salesinmatData: JSON.stringify(salesInputTable.getData()[0]), packData: JSON.stringify(rowArray), totalQty: parseInt(totalQty) },
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(save_data) {
			console.log(save_data);
			if (save_data) {
				alert("저장되었습니다.");
				location.reload();
			} else {
				alert("오류가 발생했습니다. 다시 시도해주십시오.");
			}
		}
	})
}


// 총 수량
var fgoodsQty = 0;
//랏번호 입력시 (바코드 리드) 작동하는 쿼리예시
//fgoodsLotNo

$('#fgoodsLotNo').keydown(function(e) {
	//엔터키를 눌렀을떄
	if (e.keyCode == 13) {
		
		datas = {
			startDate: $("#startDate").val(),
			endDate: $("#endDate").val(),
			LotNo: $("#fgoodsLotNo").val()
		}

		$.ajax({
			method: "GET",
			url: "salesPackingRest/LargeLot_Search",
			data: datas,
			dataType: "json",
			success: function(sim_data) {
				console.log(sim_data);
				if (sim_data.length == 1) {
					//검색어와 일치하는값이 있는 경우
					salesSmallPackingTable.clearData();
					salesLargePackingTable.setData(sim_data);
					var rows = new Array();

					rows = salesLargePackingTable.getRows("selected");
					console.log(rows);

				} else {
					salesSmallPackingTable.clearData();
					alert("일치하는 데이터가 없습니다.");
					$('#fgoodsLotNo').val("");
					return;
				}
			}
		})

	}
})

var salesLargePackingTable = new Tabulator("#salesLargePackingTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//order_lNo를 인덱스로 설정
	index: "sales_InMat_No",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		salesSmallPackingTable.clearData();
		row.select();
	},
	rowSelected: function(row) {
		SI_InfoSubSearch(row.getData().sales_Large_Packing_LotNo);
	},
	columns: [
		{ title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "right", formatter: "rownum", width: 65 },
		{ title: "LotNo", field: "sales_Large_Packing_LotNo", headerHozAlign: "center", width: 150 },
		{ title: "제품코드", field: "sales_Packing_Code", headerHozAlign: "center" },
		{ title: "제품명", field: "sales_Packing_Name", headerHozAlign: "center", width: 130 },
		{ title: "규격1", field: "sales_Packing_STND_1", headerHozAlign: "center", width: 85 },
		{ title: "규격2", field: "sales_Packing_STND_2", headerHozAlign: "center", width: 85 },
		{ title: "분류1", field: "sales_Packing_Item_Clsfc_1", headerHozAlign: "center", width: 85 },
		{ title: "분류2", field: "sales_Packing_Item_Clsfc_2", headerHozAlign: "center", width: 85 },
		{ title: "재질", field: "sales_Packing_Material", headerHozAlign: "center", width: 85 },
		{ title: "수량", field: "sales_Packing_Qty", headerHozAlign: "center", hozAlign: "right", width: 85 },
		{
			title: "입고일자", field: "sales_Large_Packing_Time", headerHozAlign: "center", hozAlign: "right", width: 130,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm" }
		},
		{ title: "구분", field: "sales_Packing_Status_Name", width: 80 }
	]
});

var salesSmallPackingTable = new Tabulator("#salesSmallPackingTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//order_lNo를 인덱스로 설정
	index: "sales_InMat_No",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	/*ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "salesInputRest/SI_Search",*/
	columns: [
		{ title: "순번", field: "sales_Packing_No", headerHozAlign: "center", hozAlign: "right", width: 65 },
		{ title: "LotNo", field: "sales_Small_Packing_LotNo", headerHozAlign: "center", width: 150 },
		{ title: "제품코드", field: "sales_Packing_Code", headerHozAlign: "center" },
		{ title: "제품명", field: "sales_Packing_Name", headerHozAlign: "center", width: 130 },
		{ title: "수량", field: "sales_Packing_Qty", headerHozAlign: "center", hozAlign: "right", width: 85 },
		{
			title: "등록일자", field: "sales_Small_Create_Date", headerHozAlign: "center", hozAlign: "right", width: 130,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }
		}
	]
});

function SI_InfoSearch() {
	datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		LotNo: $("#fgoodsLotNo").val()
	}
	
	salesLargePackingTable.setData("salesPackingRest/LargeLot_Search", datas);
	console.log(salesLargePackingTable);
}

function SI_InfoSubSearch(LotNo) {
	console.log(LotNo);
	
	datas = {
		LotNo : LotNo
	}
	salesSmallPackingTable.setData("salesPackingRest/SmallLot_Search", datas);
	console.log(salesSmallPackingTable);
}

$(document).ready(function() {
	SI_InfoSearch();
});

function SI_Print() {
	
}

$("#SI_PrintBtn").click(function() {
	SI_Print();
})

$("#SI_SearchBtn").click(function() {
	SI_InfoSearch();
})


