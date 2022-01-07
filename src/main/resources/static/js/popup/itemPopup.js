//주소에서 파라미터값을 가져오기 위한 기반
//(input_value,type_value,tab_value,search_value) 값이 들어옴
const url = new URL(window.location.href);
const urlParams = url.searchParams;

$(document).keyup(function(e) {
	if (e.keyCode == 27) {
    	exitfrn()
    }
});

// 팝업 종료
function exitfrn() {
	window.close();
}
var itemPopupTable = new Tabulator("#itemPopupTable", {
	layoutColumnsOnNewData : true,
	keybindings:{
        "navUp" : "38",
		"navDown" : "40"
    },
	rowClick:function(e, row){
		row.getTable().deselectRow();
		row.select();
		//팝업창 검색란으로 값이 들어감
		$('#Item_Word').val(row.getData().product_ITEM_CODE);
	},
	rowDblClick:function(e, row){
		//e - the click event object
		//row - row component
		list_select(row)
	},
	height:"100%",
	columns:[ 
	{title:"", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"품목코드", field:"product_ITEM_CODE", headerHozAlign:"center"},
	{title:"품목이름", field:"product_ITEM_NAME", headerHozAlign:"center"},
	{title:"규격1", field:"product_INFO_STND_1", headerHozAlign:"center"}]
	
})

function list_select(row){
	if(urlParams.get('type_value') == 'input'){
		//클래스 이름+tab_value로 데이터를 보낸다.
		$(opener.document).find('.Item_Code'+urlParams.get('tab_value')).val(row.getData().product_ITEM_CODE)
		$(opener.document).find('.Item_Name'+urlParams.get('tab_value')).val(row.getData().product_ITEM_NAME)
		
	}else if(urlParams.get('type_value') == 'grid'){
				//코드 네임 규격 가격
		window.opener.item_gridInit(row.getData().product_ITEM_CODE,
									row.getData().product_ITEM_NAME, 
									row.getData().product_INFO_STND_1,
									row.getData().product_UNIT_PRICE,
									urlParams.get('save_value'));
	}	
	exitfrn()
}

//검색
function search(){
	itemPopupTable.setData("itemPopupSelect",
		{item_Word:$('#Item_Word').val(),search_value:urlParams.get('search_value')})
	.then(function(){
		if(itemPopupTable.getDataCount()>0){
			itemPopupTable.getRows()[0].select();
			$("#itemPopupTable").focus();
		}
	});
}

$('#searchBtn').click(function(){
	search()
})

$("#itemPopupTable").keypress(function(e){
	if(e.keyCode == 13){
		list_select(itemPopupTable.getSelectedRows()[0]);
	}
})

$("#Item_Word").keypress(function(e){
	if(e.keyCode == 13){
		search();
	}
})

$(document).ready(function(){
	// 팝업창이 뜨면 데이터 받음
	$('#Item_Word').val(urlParams.get('input_value'));
	
	// 팝업이 뜨자마자 바로 검색
	search();
})