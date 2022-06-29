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
	{title:"분류1", field:"product_ITEM_CLSFC_1_NAME", headerHozAlign:"center"},
	{title:"분류2", field:"product_ITEM_CLSFC_2_NAME", headerHozAlign:"center"},
	{title:"규격1", field:"product_INFO_STND_1", headerHozAlign:"center"},
	{title:"규격2", field:"product_INFO_STND_2", headerHozAlign:"center"},
	{title:"자재분류", field:"product_MTRL_CLSFC", headerHozAlign:"center"},
	{title:"재질", field:"product_UNIT_NAME", headerHozAlign:"center"},
	{title:"단위", field:"product_MATERIAL_NAME", headerHozAlign:"center"}
	]
	
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
									row.getData().product_UNIT_NAME,
									row.getData().product_MATERIAL_NAME,
									urlParams.get('save_value'));
	}else if(urlParams.get('type_value') == 'workOrder') {
		window.opener.item_gridInit(row.getData().product_ITEM_CODE,
									row.getData().product_ITEM_NAME, 
									row.getData().product_ITEM_CLSFC_1_NAME,
									row.getData().product_ITEM_CLSFC_2_NAME,
									row.getData().product_INFO_STND_1,
									row.getData().product_INFO_STND_2,
									row.getData().product_UNIT_NAME,
									row.getData().product_MATERIAL_NAME,
									urlParams.get('save_value'));
	}
	exitfrn()
}

//검색
function search(){
	itemPopupTable.setData("itemPopupSelect",
		{item_Word:$('#Item_Word').val(),search_value:$("#item_Type").val()})
	.then(function(){
		if(itemPopupTable.getDataCount()>0){
			console.log(itemPopupTable.getRows()[0]);
			itemPopupTable.getRows()[0].select();
			$("#itemPopupTable").focus();
		}
	});
}

//규격2로 검색
function stndSearch(){
	itemPopupTable.setData("itemPopupSTND",
		{item_Word:$('#Item_Word').val(),search_value:$("#item_Type").val()})
	.then(function(){
		if(itemPopupTable.getDataCount()>0){
			console.log(itemPopupTable.getRows()[0]);
			itemPopupTable.getRows()[0].select();
			$("#itemPopupTable").focus();
		}
	});
}



$('#searchBtn').click(function(){
	if ($("#Item_STND").val() == 'stnd2') {
		stndSearch();
		return;
	} else {
		search();
	}
})

$("#itemPopupTable").keypress(function(e){
	if(e.keyCode == 13){
		list_select(itemPopupTable.getSelectedRows()[0]);
	}
})

$("#Item_Word").keypress(function(e){
	if(e.keyCode == 13){
		if ($("#Item_STND").val() == 'stnd2') {
			stndSearch();
			return;
		} else {
			search();
		}
	}
})

$("#item_Type").change(function(){
	if ($("#Item_STND").val() == 'stnd2') {
		stndSearch();
		return;
	} else {
		search();
	}
})

$(document).ready(function(){
	// 팝업창이 뜨면 데이터 받음
	$('#Item_Word').val(urlParams.get('input_value'));
	
	//search_value 가 material이면 원자재 기준, sales이면 완제품 기준
	if(urlParams.get('search_value') == "material"){
		$("#item_Type").val('24')
	}else if (urlParams.get('search_value') == "sales"){
		$("#item_Type").val('28')
	}else if(urlParams.get('search_value') == "halfItem") {
		$("#item_Type").val('27')
	}else if(urlParams.get('search_value') == "workMask") {
		$("#item_Type").val('27')
		document.getElementById("item_Type").disabled = true;
	}else if(urlParams.get('search_value') == "workNonMask") {
		$("#item_Type").val('28')
		document.getElementById("item_Type").disabled = true;
	}else if (urlParams.get('search_value') == "workLabel") {
		$("#item_Type").val('28')
		$("#Item_STND").val('stnd2');
		document.getElementById("item_Type").disabled = true;
		stndSearch();
		return;
	}
	search();
})