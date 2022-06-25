var salesPackingMasterTable = new Tabulator("#salesPackingMasterTable",{
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {			
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
		IPS_Search(row.getData().sales_Large_Packing_LotNo)
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "대포장 LotNo", field: "sales_Large_Packing_LotNo", headerHozAlign: "center", headerFilter:"input"},
		{ title: "제품코드", field: "sales_Packing_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "제품명", field: "sales_Packing_Name", headerHozAlign: "center", headerFilter:"input"},
		{ title: "규격1", field: "sales_Packing_STND_1", headerHozAlign: "center", headerFilter:"input"},
		{ title: "품목분류1", field: "sales_Packing_Item_Clsfc_1", headerHozAlign: "center", headerFilter:"input"},
		{ title: "품목분류2", field: "sales_Packing_Item_Clsfc_2", headerHozAlign: "center", headerFilter:"input"},
		{ title: "재질", field: "sales_Packing_Material", headerHozAlign: "center", headerFilter:"input"},
		{ title: "수량",	field: "sales_Packing_Qty",	headerHozAlign: "center", hozAlign:"right"},
		{ title: "구분", field: "sales_Packing_Status_Name", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"}
	]
});

var salesPackingSubTable = new Tabulator("#salesPackingSubTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "소포장 LotNo", field: "sales_Small_Packing_LotNo", headerHozAlign: "center", headerFilter:"input"},
		{ title: "제품코드", field: "sales_Packing_Code", headerHozAlign: "center", headerFilter:"input"},
		{ title: "제품명", field: "sales_Packing_Name", headerHozAlign: "center", headerFilter:"input"},
		{ title: "수량",	field: "sales_Packing_Qty",	headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "sales_Small_Create_Date", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
	]
});

function IPS_Search(largeLotNo){
	
	salesPackingSubTable.setData("salesPackingRest/SmallLot_Search",{LotNo: largeLotNo})
	
}

$('#IPL_SearchBtn').click(function(){
	
	IPL_Search();	
})

function IPL_Search() {
	
	datas = {
		LotNo: $('#itemPackinLotNo').val(),
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val(),
		itemCode : $('#PRODUCT_ITEM_CODE').val()
	}
	
	salesPackingMasterTable.setData("salesPackingRest/LargeLot_Search", datas);
	salesPackingSubTable.clearData();

}

$('#PRODUCT_ITEM_NAME').keypress(function(e) {
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
					$('#PRODUCT_ITEM_CODE').val(data[0].product_ITEM_CODE);
					$('#PRODUCT_ITEM_NAME').val(data[0].product_ITEM_NAME);
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					itemPopup(value, 'input', '', 'sales');
				}
			}
		})
	}
})

$(document).ready(function() {
	IPL_Search();
});
