
$('#itemName').keypress(function(e) {
	if (e.keyCode == 13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "product_check?PRODUCT_ITEM_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#itemCode').val(data[0].product_ITEM_CODE);
					$('#itemName').val(data[0].production_Product_Name);
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					itemPopup(value, 'input', '', 'material');
				}
			}
		})
	}
})


//셀위치저장
var cellPos = null;

var salesPackingTable = new Tabulator("#salesPackingTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	layoutColumnsOnNewData: true,
	clipboard: true,
	height: "calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "rownum", formatter: "rownum", hozAlign: "center" },
		{ title: "대포장 LotNo", field: "sales_Large_Packing_LotNo", headerHozAlign: "center", width:150},
		{ title: "소포장 LotNo", field: "sales_Small_Packing_LotNo", headerHozAlign: "center", width:150},
		{ title: "제품코드", field: "sales_Packing_Code", headerHozAlign: "center" },
		{ title: "제품명", field: "sales_Packing_Name", headerHozAlign: "center", hozAlign: "left", width:150},
		{ title: "규격1", field: "sales_Packing_STND_1", headerHozAlign: "center", hozAlign: "left", width:100},
		{ title: "규격2", field: "sales_Packing_STND_2", headerHozAlign: "center", hozAlign: "left", width:100},
		{ title: "분퓨1", field: "sales_Packing_Item_Clsfc_1", headerHozAlign: "center", hozAlign: "left", width:100},
		{ title: "수량", field: "sales_Packing_Qty", headerHozAlign: "center", hozAlign: "right", width:100},
		{ title: "포장시간", field: "sales_Small_Create_Date", headerHozAlign: "center", width:150 },
		{ title: "상태", field: "sales_Packing_Status_Name", headerHozAlign: "center", width:100 }
	]
});

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode, PName) {
	cellPos.getRow().update({
		"production_Product_Code": PCode,
		"production_Product_Name": PName
	})
	cellPos.getElement().focus();
}

//검색기능
function SPS_Search() {
	
	var jsonData = {
		startDate : $('#packing_startDate').val(),
		endDate : $('#packing_endDate').val(),
		ItemCode : $('#itemCode').val(),
		ItemSendClsfc : $('#packingTypeSelectBox option:selected').val()
	}

	salesPackingTable.setData('salesPackingRest/SPS_Search', jsonData)
	
	console.log(salesPackingTable);
}

