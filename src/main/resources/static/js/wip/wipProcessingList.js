var wipProcessingListTable = new Tabulator("#wipProcessingListTable", {
	placeholder:"창고 내 제공품",
	layoutColumnsOnNewData : true,
	height:"calc(100% - 175px)",
	ajaxURL:"wipLotManageRest/wipProcessingListSelect",
    ajaxConfig:"get",
    ajaxContentType:"json",
 	columns:[
		{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{title:"Lot번호", field:"wip_LotNo", headerHozAlign:"center", hozAlign:"center"}
 	]
});


// 공정 갯수에 따라 컬럼 추가
var dtl_arr = new Object();

$.ajax({
	method: "GET",
	async: false,
	url: "dtlTrueSelect",
	data: {"NEW_TBL_CODE" : 32},
	success: function(datas) {
		for (i = 0; i < datas.length; i++) {
			if(i>=5){
				break;
			}
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
			
			wipProcessingListTable.addColumn(
				{title:datas[i].child_TBL_TYPE, headerHozAlign:"center",
				
					columns:[
					{title:"입고시간", field:"wip_InputDate_P"+(i+1), headerHozAlign:"center",
						formatter:"datetime", formatterParams :{outputFormat :"YYYY-MM-DD HH:mm"}},
					{title:"출고시간", field:"wip_OutputDate_P"+(i+1), headerHozAlign:"center",
						formatter:"datetime", formatterParams :{outputFormat :"YYYY-MM-DD HH:mm"}},
					{title:"보관시간", field:"wip_SaveDate_P"+(i+1), headerHozAlign:"center"}
					]
				})
		}
	}
});

$("#WPL_SearchBtn").click(function(){
	wipProcessingListTable.setData("wipLotManageRest/wipProcessingListSelect");
})
