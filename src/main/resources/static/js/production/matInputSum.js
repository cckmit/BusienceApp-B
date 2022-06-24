var largePackTable = new Tabulator("#largePackTable",{
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {			
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
		SIS_Search(row.getData().sales_Large_Packing_LotNo)
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "대포장 LotNo", field: "sales_Large_Packing_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "sales_Packing_Code", headerHozAlign: "center"},
		{ title: "제품명", field: "sales_Packing_Name", headerHozAlign: "center"},
		{ title: "수량",	field: "sales_Packing_Qty",	headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "sales_Small_Create_Date", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
	]
});

var smallPackTable = new Tabulator("#smallPackTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "소포장 LotNo", field: "sales_Small_Packing_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "sales_Packing_Code", headerHozAlign: "center"},
		{ title: "제품명", field: "sales_Packing_Name", headerHozAlign: "center"},
		{ title: "수량",	field: "sales_Packing_Qty",	headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "sales_Small_Create_Date", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
	]
});

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
		{ title: "수량",	field: "cl_Qty", headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "crateDate", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
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
		{ title: "수량",	field: "qty", headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "crateDate", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
	]
});

function LIS_Search() {
	
	datas = {
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val()
	}
	
	largePackTable.setData("salesPackingRest/LargeLot_Search", datas);
	console.log(largePackTable);
	smallPackTable.clearData();

}

function SIS_Search(largeLotNo){
	console.log(largeLotNo);
	smallPackTable.setData("salesPackingRest/SmallLot_Search",{LotNo: largeLotNo})
	console.log(smallPackTable);
}

function CLM_Search() {
	
	crateTable.setData("proListRest/crateLotList", datas);
	console.log(crateTable);
	rawTable.clearData();

}

function RIS_Search(LotNo){
	
	rawTable.setData("proListRest/rawLotList",{LotNo: LotNo})
	console.log(rawTable);
}

$('#LIS_SearchBtn').click(function(){
	
	LIS_Search();	
})

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
					itemPopup(value, 'input', '', 'material');
				}
			}
		})
	}
})

$(document).ready(function() {
	LIS_Search();
});
