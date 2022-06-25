var crateTable = new Tabulator("#crateTable",{
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {			
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
		RLM_Search(row.getData().cl_LotNo)
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "생산 LotNo", field: "cl_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "cl_ItemCode", headerHozAlign: "center"},
		{ title: "제품명", field: "cl_ItemName", headerHozAlign: "center"},
		{ title: "규격1", field: "cl_STND_1", headerHozAlign: "center"},
		{ title: "품목분류1", field: "cl_Item_Clsfc_Name_1", headerHozAlign: "center"},
		{ title: "품목분류2", field: "cl_Item_Clsfc_Name_2", headerHozAlign: "center"},
		{ title: "재질", field: "cl_Item_Material", headerHozAlign: "center"},
		{ title: "수량",	field: "cl_Qty", headerHozAlign: "center", hozAlign:"right"}
	]
});

var rawTable = new Tabulator("#rawTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "원자재 LotNo", field: "material_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "material_ItemCode", headerHozAlign: "center"},
		{ title: "제품명", field: "material_ItemName", headerHozAlign: "center"},
		{ title: "규격1", field: "material_Item_Stnd_1", headerHozAlign: "center"},
		{ title: "품목분류1", field: "material_Item_Clsfc_1", headerHozAlign: "center"},
		{ title: "품목분류2", field: "material_Item_Clsfc_2", headerHozAlign: "center"},
		{ title: "재질", field: "material_Item_Material", headerHozAlign: "center"},
		{ title: "수량",	field: "qty", headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "createDate", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }
		}
	]
});

function RLM_Search(LotNo){
	
	rawTable.setData("proListRest/rawLotList",{LotNo: LotNo})
	console.log(rawTable);
}

$('#LLM_SearchBtn').click(function(){
	
	LLM_Search();	
})

function LLM_Search() {
	
	datas = {
		LotNo: $('#itemPackinLotNo').val(),
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val(),
		itemCode : $('#PRODUCT_ITEM_CODE').val()
	}
	
	crateTable.setData("proListRest/crateLotList", datas);
	console.log(crateTable);
	rawTable.clearData();

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
					itemPopup(value, 'input', '', 'halfItem');
				}
			}
		})
	}
})

$(document).ready(function() {
	LLM_Search();
});
