var largePackTable = new Tabulator("#largePackTable",{
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {
		smallPackTable.clearData();	
		crateTable.clearData();
		rawTable.clearData();				
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
		SIS_Search(row.getData().sales_Small_Packing_LotNo)
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "대포장 LotNo", field: "sales_Large_Packing_LotNo", headerHozAlign: "center"},
		{ title: "소포장 LotNo", field: "sales_Small_Packing_LotNo", headerHozAlign: "center"},
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
	rowClick: function(e, row) {	
		crateTable.clearData();
		rawTable.clearData();		
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
		CLS_Search(row.getData().production_LotNo)
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "소포장 LotNo", field: "small_Packaging_LotNo", headerHozAlign: "center"},
		{ title: "생산 LotNo", field: "production_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "itemCode", headerHozAlign: "center"},
		{ title: "제품명", field: "itemName", headerHozAlign: "center"},
		{ title: "수량",	field: "qty",	headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "create_Date", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
	]
});

var crateTable = new Tabulator("#crateTable",{
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	rowClick: function(e, row) {	
		rawTable.clearData();			
		row.getTable().deselectRow();
		row.select();
	},
	rowSelected:function(row){
		RIS_Search(row.getData().cl_LotNo)
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "생산 LotNo", field: "cl_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "cl_ItemCode", headerHozAlign: "center"},
		{ title: "제품명", field: "cl_ItemName", headerHozAlign: "center"},
		{ title: "수량",	field: "cl_Qty", headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "cl_Create_Date", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
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
		{ title: "등록일자", field: "createDate", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
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
	smallPackTable.clearData();
	crateTable.clearData();
	rawTable.clearData();
}

function SIS_Search(largeLotNo){
	smallPackTable.setData("smallPackagingRest/smallPackagingSelect",{LotNo: largeLotNo})
	crateTable.clearData();
	rawTable.clearData();
	
}

function CLS_Search(smallLotNo){
	crateTable.setData("/tablet/maskProductionRest/crateLotSelect",{LotNo: smallLotNo})
	rawTable.clearData();
}

function RIS_Search(LotNo){
	rawTable.setData("proListRest/rawLotList",{LotNo: LotNo})
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
