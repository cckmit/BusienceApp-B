
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

var ajaxResult = null;


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
			url: "salesPackingRest/SmallLot_Search",
			data: datas,
			dataType: "json",
			success: function(sim_data) {
				console.log(sim_data);
				if (sim_data.length > 0) {
					//검색어와 일치하는값이 있는 경우
					// salesCratePackingTable.clearData();
					salesSmallPackingTable.setData(sim_data);
					var rows = new Array();

					rows = salesSmallPackingTable.getRows("selected");

				} else {
					// salesCratePackingTable.clearData();
					alert("일치하는 데이터가 없습니다.");
					$('#fgoodsLotNo').val("");
					return;
				}
			}
		})

	}
})

var salesSmallPackingTable = new Tabulator("#salesSmallPackingTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//order_lNo를 인덱스로 설정
	index: "sales_InMat_No",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	headerFilterPlaceholder: null,
	rowClick: function(e, row) {
		row.getTable().deselectRow();
		// salesCratePackingTable.clearData();
		row.select();
	},
	columns: [
		{ title:"순번", field:"rownum", headerHozAlign: "center", hozAlign: "right", formatter: "rownum", width: 65 },
		{ title: "LotNo", field: "small_Packaging_LotNo", headerHozAlign: "center", headerFilter: true, width: 150 },
		{ title: "CrateLotNo", field: "production_LotNo", headerHozAlign: "center", width: 150, visible: false },
		{ title: "제품코드", field: "itemCode", headerHozAlign: "center" , headerFilter: true },
		{ title: "제품명", field: "itemName", headerHozAlign: "center", headerFilter: true, width: 160 },
		{ title: "규격1", field: "itemSTND1", headerHozAlign: "center", headerFilter: true,width: 85},
		{ title: "규격2", field: "itemSTND2", headerHozAlign: "center", headerFilter: true,width: 85 },
		{ title: "분류1", field: "itemClsfc1_Name", headerHozAlign: "center", headerFilter: true,width: 85 },
		{ title: "분류2", field: "itemClsfc2_Name", headerHozAlign: "center", headerFilter: true,width: 85 },
		{ title: "재질", field: "itemMaterial_Name", headerHozAlign: "center", headerFilter: true, width: 85 },
		{ title: "설비코드", field: "machineCode", headerHozAlign: "center", headerFilter: true,width: 85 },
		{ title: "설비명", field: "machineName", headerHozAlign: "center", headerFilter: true,width: 85 },
		{ title: "수량", field: "qty", headerHozAlign: "center", hozAlign: "right", width: 85 },
		{
			title: "입고일자", field: "create_Date", headerHozAlign: "center", hozAlign: "right", width: 130,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm" }
		},
		{ title: "작업자", field: "modifier", headerHozAlign: "center", width: 85 }
	]
});

/* var salesCratePackingTable = new Tabulator("#salesCratePackingTable", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//order_lNo를 인덱스로 설정
	index: "sales_InMat_No",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	headerFilterPlaceholder: null,
	/*ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "salesInputRest/SI_Search",*/
/*	columns: [
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "right", formatter: "rownum", width: 65 },
		{ title: "LotNo", field: "cl_LotNo", headerHozAlign: "center", width: 150 ,headerFilter: true},
		{ title: "제품코드", field: "cl_ItemCode", headerHozAlign: "center" ,headerFilter: true},
		{ title: "제품명", field: "cl_ItemName", headerHozAlign: "center", width: 130 ,headerFilter: true},
		{ title: "수량", field: "cl_Qty", headerHozAlign: "center", hozAlign: "right", width: 85},
		{
			title: "등록일자", field: "cl_Create_Date", headerHozAlign: "center", hozAlign: "right", width: 130,
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }
		}
	] 
}); */

function SI_InfoSearch() {
	datas = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val(),
		LotNo: $("#fgoodsLotNo").val()
	}
	
	// salesCratePackingTable.clearData();
	salesSmallPackingTable.setData("salesPackingRest/SmallLot_Search", datas);
}

/*function SI_InfoSubSearch(LotNo) {
	
	datas = {
		LotNo : LotNo
	}
	salesCratePackingTable.setData("/tablet/maskProductionRest/crateLotSelect", datas);
}*/

$(document).ready(function() {
	SI_InfoSearch();
});

function SI_Print() {
	//프린트
	productionPrinter(salesSmallPackingTable.getData("selected")[0])
	$("#selectedItem").val("")
	salesSmallPackingTable.deselectRow();
}

$("#SI_PrintBtn").click(function() {
	if(salesSmallPackingTable.getData("selected").length == 0) {
		alert("출력할 행을 선택하세요.");
	} else {
		SI_Print();
	}
})

$("#SI_SearchBtn").click(function() {
	SI_InfoSearch();
})


