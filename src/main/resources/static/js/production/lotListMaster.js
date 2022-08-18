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
		CLS_Search(row.getData().small_Packaging_LotNo)
    },
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "소포장 LotNo", field: "small_Packaging_LotNo", headerHozAlign: "center"},
		{ title: "생산 LotNo", field: "production_LotNo", headerHozAlign: "center", visible:false},
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
		RIS_Search(row.getData().production_LotNo)
    },
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{ title: "생산 LotNo", field: "production_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "itemCode", headerHozAlign: "center"},
		{ title: "제품명", field: "itemName", headerHozAlign: "center"},
		{ title: "수량",	field: "qty", headerHozAlign: "center", hozAlign:"right"},
		{ title: "등록일자", field: "create_Date", headerHozAlign: "center", headerHozAlign: "center", hozAlign: "center"
		, formatter:"datetime", formatterParams:{
    		outputFormat:"YYYY-MM-DD HH:mm" }}
	]
});

var rawTable = new Tabulator("#rawTable", {
	layoutColumnsOnNewData : true,
	headerFilterPlaceholder: null,
	height: "calc(100% - 175px)",
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
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

function SIS_Search(){
	
	datas = {
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val()
	}
	
	smallPackTable.setData("smallPackagingRest/smallPackagingSelect", datas);
	crateTable.clearData();
	rawTable.clearData();
	
}

function CLS_Search(smallLotNo){
	crateTable.setData("smallPackagingRest/smallPackagingOneSelect",{LotNo: smallLotNo})
	rawTable.clearData();
}

function RIS_Search(LotNo){
	rawTable.setData("proListRest/rawLotList",{LotNo: LotNo})
}

$('#SIS_SearchBtn').click(function(){
	SIS_Search();	
})

$(document).ready(function() {
	SIS_Search();
});
